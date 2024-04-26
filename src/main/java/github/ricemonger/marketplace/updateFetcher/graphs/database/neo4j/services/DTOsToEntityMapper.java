package github.ricemonger.marketplace.updateFetcher.graphs.database.neo4j.services;

import github.ricemonger.marketplace.updateFetcher.graphs.database.neo4j.entities.BuyStatsEntity;
import github.ricemonger.marketplace.updateFetcher.graphs.database.neo4j.entities.ItemEntity;
import github.ricemonger.marketplace.updateFetcher.graphs.database.neo4j.entities.LastSoldAtEntity;
import github.ricemonger.marketplace.updateFetcher.graphs.database.neo4j.entities.SellStatsEntity;
import github.ricemonger.marketplace.updateFetcher.graphs.database.neo4j.enums.ItemType;
import github.ricemonger.marketplace.updateFetcher.graphs.graphsDTOs.marketableItems.Node;
import github.ricemonger.marketplace.updateFetcher.graphs.graphsDTOs.marketableItems.node.Item;
import github.ricemonger.marketplace.updateFetcher.graphs.graphsDTOs.marketableItems.node.MarketData;
import github.ricemonger.marketplace.updateFetcher.graphs.graphsDTOs.marketableItems.node.marketData.BuyStats;
import github.ricemonger.marketplace.updateFetcher.graphs.graphsDTOs.marketableItems.node.marketData.LastSoldAt;
import github.ricemonger.marketplace.updateFetcher.graphs.graphsDTOs.marketableItems.node.marketData.SellStats;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DTOsToEntityMapper {

    public final static String PERFORMED_AT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public List<ItemEntity> NodesDTOToItemEntities(List<Node> nodes) {
        return nodes.stream().map(this::nodeDTOToItemEntity).collect(Collectors.toList());
    }

    public ItemEntity nodeDTOToItemEntity(Node node) {
        Item itemDTO = node.getItem();
        MarketData marketDataDTO = node.getMarketData();

        return ItemEntity.builder()
                .itemFullId(itemDTO.getId())
                .assertUrl(itemDTO.getAssertUrl())
                .name(itemDTO.getName())
                .tags(itemDTO.getTags())
                .type(ItemType.valueOf(itemDTO.getType()))
                .buyStats(marketDataDTOToBuyStatsEntityOrNull(marketDataDTO))
                .sellStats(marketDataDTOToSellStatsEntityOrNull(marketDataDTO))
                .lastSoldAt(marketDataDTOToLastSoldAtEntityOrNull(marketDataDTO))
                .build();
    }

    public BuyStatsEntity marketDataDTOToBuyStatsEntityOrNull(MarketData marketDataDTO) {

        BuyStats buyStatsDTO = marketDataDTO.getBuyStats()==null ? null : marketDataDTO.getBuyStats()[0];

        if(buyStatsDTO == null) {
            return null;
        }
        else {
            return BuyStatsEntity.builder()
                    .buyStatsId(buyStatsDTO.getId())
                    .lowestPrice(buyStatsDTO.getLowestPrice())
                    .highestPrice(buyStatsDTO.getHighestPrice())
                    .activeCount(buyStatsDTO.getActiveCount())
                    .build();
        }
    }

    public SellStatsEntity marketDataDTOToSellStatsEntityOrNull(MarketData marketDataDTO) {

        SellStats sellStatsDTO = marketDataDTO.getSellStats()==null ? null : marketDataDTO.getSellStats()[0];

        if(sellStatsDTO == null) {
            return null;
        }
        else {
            return SellStatsEntity.builder()
                    .sellStatsId(sellStatsDTO.getId())
                    .lowestPrice(sellStatsDTO.getLowestPrice())
                    .highestPrice(sellStatsDTO.getHighestPrice())
                    .activeCount(sellStatsDTO.getActiveCount())
                    .build();
        }
    }

    public LastSoldAtEntity marketDataDTOToLastSoldAtEntityOrNull(MarketData marketDataDTO){

        LastSoldAt lastSoldAtDTO = marketDataDTO.getLastSoldAt()==null ? null : marketDataDTO.getLastSoldAt()[0];

        if(lastSoldAtDTO == null) {
            return null;
        }
        else {
            SimpleDateFormat sdf = new SimpleDateFormat(PERFORMED_AT_DATE_FORMAT);
            try {
                return LastSoldAtEntity.builder()
                        .lastSoldAtId(lastSoldAtDTO.getId())
                        .price(lastSoldAtDTO.getPrice())
                        .performedAt(sdf.parse(lastSoldAtDTO.getPerformedAt()))
                        .build();
            } catch (ParseException e) {
                log.error("Error parsing date: " + lastSoldAtDTO.getPerformedAt());
                e.printStackTrace();
                return LastSoldAtEntity.builder()
                        .price(lastSoldAtDTO.getPrice())
                        .build();
            }
        }
    }
}
