package github.ricemonger.marketplace.databases.neo4j.services;

import github.ricemonger.marketplace.UbiServiceConfiguration;
import github.ricemonger.marketplace.databases.neo4j.entities.*;
import github.ricemonger.marketplace.databases.neo4j.repositories.ItemRepository;
import github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems.Node;
import github.ricemonger.utils.exceptions.UbiUserEntityDoesntExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private final UbiServiceConfiguration ubiServiceConfiguration;

    private final DTOsToEntityMapper mapper;

    public void saveAll(List<Node> nodeDTOs) {
        List<ItemEntity> entities = mapper.NodesDTOToItemEntities(nodeDTOs);
        itemRepository.saveAll(entities);
    }

    public List<ItemEntity> getSpeculativeItems(int minProfitAbsolute, int minProfitPercentOfBuyPrice, int minSellPrice, int maxSellPrice) throws UbiUserEntityDoesntExistException {
        log.info("Getting speculative items");
        List<ItemEntity> allItems = itemRepository.findAll();

        List<ItemEntity> speculativeItems = new ArrayList<>();

        for (ItemEntity item : allItems) {

            LastSoldAtEntity lastSoldAt = item.getLastSoldAt();
            SellStatsEntity sellStats = item.getSellStats();

            int lastSoldPrice = lastSoldAt == null ? 0 : lastSoldAt.getPrice();
            int sellPrice = sellStats == null ? lastSoldPrice : sellStats.getLowestPrice();

            if(sellPrice < minSellPrice || sellPrice > maxSellPrice){
                continue;
            }

            BuyStatsEntity buyStats = item.getBuyStats();

            int buyPrice = buyStats == null ? 0 : buyStats.getHighestPrice();
            buyPrice = getNextFancyBuyPrice(buyPrice);

            int profit = (int)(sellPrice * ubiServiceConfiguration.getMarketplaceProfitPercent()) - buyPrice;

            if(profit < minProfitAbsolute){
                continue;
            }

            if(profit < minProfitPercentOfBuyPrice * buyPrice / 100){
                continue;
            }

            speculativeItems.add(item);
        }
        return speculativeItems;
    }

    private int getNextFancyBuyPrice(int buyPrice) {
        if (buyPrice == 0) {
            return 120;
        } else if (buyPrice < 200) {
            return ((buyPrice + 10) / 10) * 10;
        } else if (buyPrice < 1000) {
            return ((buyPrice + 50) / 50) * 50;
        } else if (buyPrice < 3000) {
            return ((buyPrice + 100) / 100) * 100;
        } else if (buyPrice < 10000) {
            return ((buyPrice + 500) / 500) * 500;
        } else if (buyPrice < 50000) {
            return ((buyPrice + 1000) / 1000) * 1000;
        } else {
            return ((buyPrice + 5000) / 5000) * 5000;
        }
    }
}
