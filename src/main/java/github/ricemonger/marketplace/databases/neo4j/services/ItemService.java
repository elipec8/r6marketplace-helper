package github.ricemonger.marketplace.databases.neo4j.services;

import github.ricemonger.marketplace.databases.neo4j.entities.ItemEntity;
import github.ricemonger.marketplace.databases.neo4j.repositories.ItemRepository;
import github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems.Node;
import github.ricemonger.utils.exceptions.UbiUserEntityDoesntExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private final DTOsToEntityMapper mapper;

    public void saveAll(List<Node> nodeDTOs) {
        List<ItemEntity> entities = mapper.nodesDTOToItemEntities(nodeDTOs);
        itemRepository.saveAll(entities);
    }

    public List<ItemEntity> getSpeculativeItems(int minProfitAbsolute, int minProfitPercentOfBuyPrice, int minSellPrice, int maxSellPrice) throws UbiUserEntityDoesntExistException {
        return itemRepository.findAll().stream().map(itemEntity -> {
            if (itemEntity.getExpectedProfit() >= minProfitAbsolute &&
                    itemEntity.getExpectedProfitPercentage() >= minProfitPercentOfBuyPrice &&
                    itemEntity.getMinSellPrice() >= minSellPrice &&
                    itemEntity.getMinSellPrice() <= maxSellPrice) {
                return itemEntity;
            }
            return null;
        }).filter(Objects::nonNull).toList();
    }
}
