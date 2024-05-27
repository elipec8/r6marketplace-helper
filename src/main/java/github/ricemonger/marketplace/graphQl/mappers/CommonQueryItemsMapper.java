package github.ricemonger.marketplace.graphQl.mappers;

import github.ricemonger.marketplace.UbiServiceConfiguration;
import github.ricemonger.marketplace.graphQl.graphsDTOs.common_query_items.MarketableItems;
import github.ricemonger.marketplace.graphQl.graphsDTOs.common_query_items.marketableItems.Node;
import github.ricemonger.marketplace.graphQl.graphsDTOs.common_query_items.marketableItems.node.MarketData;
import github.ricemonger.marketplace.graphQl.graphsDTOs.common_query_items.marketableItems.node.marketData.BuyStats;
import github.ricemonger.marketplace.graphQl.graphsDTOs.common_query_items.marketableItems.node.marketData.LastSoldAt;
import github.ricemonger.marketplace.graphQl.graphsDTOs.common_query_items.marketableItems.node.marketData.SellStats;
import github.ricemonger.utils.dtos.Item;
import github.ricemonger.utils.enums.ItemType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CommonQueryItemsMapper {

    private final SimpleDateFormat sdf;

    public CommonQueryItemsMapper(UbiServiceConfiguration ubiServiceConfiguration) {
        sdf = new SimpleDateFormat(ubiServiceConfiguration.getDateFormat());
    }

    public Collection<? extends Item> mapItems(MarketableItems marketableItems) {
        return marketableItems.getNodes().stream().map(this::mapItem).collect(Collectors.toList());
    }

    public Item mapItem(Node node) {
        github.ricemonger.marketplace.graphQl.graphsDTOs.common_query_items.marketableItems.node.Item item = node.getItem();

            Item.ItemBuilder itemBuilder = Item.builder()
                    .itemId(item.getItemId())
                    .assetUrl(item.getAssetUrl())
                    .name(item.getName())
                    .tags(item.getTags());


            try {
                itemBuilder
                    .type(ItemType.valueOf(item.getType()));
            }
            catch (IllegalArgumentException e) {
                log.error("Unknown item type: {}", item.getType());
                itemBuilder.type(ItemType.Unknown);
            }

        MarketData marketData = node.getMarketData();
        BuyStats buyStats = marketData.getBuyStats() == null ? null : marketData.getBuyStats()[0];
        SellStats sellStats = marketData.getSellStats() == null ? null : marketData.getSellStats()[0];
        LastSoldAt lastSoldAt = marketData.getLastSoldAt() == null ? null : marketData.getLastSoldAt()[0];

        if(buyStats != null) {
            itemBuilder
                .maxBuyPrice(buyStats.getHighestPrice())
                .buyOrdersCount(buyStats.getActiveCount());
        }
        else{
            itemBuilder
                .maxBuyPrice(0)
                .buyOrdersCount(0);
        }

        if(sellStats != null) {
            itemBuilder
                .minSellPrice(sellStats.getLowestPrice())
                .sellOrdersCount(sellStats.getActiveCount());
        }
        else{
            itemBuilder
                .minSellPrice(0)
                .sellOrdersCount(0);
        }

        if(lastSoldAt != null) {
            try{
                itemBuilder
                        .lastSoldAt(sdf.parse(lastSoldAt.getPerformedAt()));
            }
            catch (ParseException e){
                itemBuilder
                        .lastSoldAt(new Date(0));
                log.error("Error parsing date: {}", lastSoldAt.getPerformedAt());
            }
            itemBuilder
                .lastSoldPrice(lastSoldAt.getPrice());
        }
        else{
            itemBuilder
                .lastSoldAt(new Date(0))
                .lastSoldPrice(0);
        }

        return itemBuilder.build();
    }
}
