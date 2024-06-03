package github.ricemonger.marketplace.graphQl.mappers;

import github.ricemonger.marketplace.graphQl.dtos.common_query_items.marketableItems.Node;
import github.ricemonger.marketplace.graphQl.dtos.common_query_items.marketableItems.node.MarketData;
import github.ricemonger.marketplace.graphQl.dtos.common_query_items.marketableItems.node.marketData.BuyStats;
import github.ricemonger.marketplace.graphQl.dtos.common_query_items.marketableItems.node.marketData.LastSoldAt;
import github.ricemonger.marketplace.graphQl.dtos.common_query_items.marketableItems.node.marketData.SellStats;
import github.ricemonger.marketplace.services.CommonValuesService;
import github.ricemonger.utils.dtos.Item;
import github.ricemonger.utils.enums.ItemType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommonQueryItemsMapper {

    private final CommonValuesService commonValuesService;

    public Collection<Item> mapItems(Collection<Node> nodes) {
        return nodes.stream().map(this::mapItem).collect(Collectors.toList());
    }

    public Item mapItem(Node node) {
        github.ricemonger.marketplace.graphQl.dtos.common_query_items.marketableItems.node.Item item = node.getItem();

        Item result = new Item();
        result.setItemId(item.getItemId());
        result.setAssetUrl(item.getAssetUrl());
        result.setName(item.getName());
        result.setTags(item.getTags());

        try {
            result.setType(ItemType.valueOf(item.getType()));
        } catch (IllegalArgumentException e) {
            log.error("Unknown item type: {}", item.getType());
            result.setType(ItemType.Unknown);
        }

        MarketData marketData = node.getMarketData();
        BuyStats buyStats = marketData.getBuyStats() == null ? null : marketData.getBuyStats()[0];
        SellStats sellStats = marketData.getSellStats() == null ? null : marketData.getSellStats()[0];
        LastSoldAt lastSoldAt = marketData.getLastSoldAt() == null ? null : marketData.getLastSoldAt()[0];

        if (buyStats != null) {
            result.setMaxBuyPrice(buyStats.getHighestPrice());
            result.setBuyOrdersCount(buyStats.getActiveCount());
            ;
        } else {
            result.setMaxBuyPrice(0);
            result.setBuyOrdersCount(0);
        }

        if (sellStats != null) {
            result.setMinSellPrice(sellStats.getLowestPrice());
            result.setSellOrdersCount(sellStats.getActiveCount());
        } else {
            result.setMinSellPrice(0);
            result.setSellOrdersCount(0);
        }

        if (lastSoldAt != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
                result.setLastSoldAt(sdf.parse(lastSoldAt.getPerformedAt()));
            } catch (ParseException e) {
                result.setLastSoldAt(new Date(0));
                log.error("Error parsing date: {}", lastSoldAt.getPerformedAt());
            }
            result.setLastSoldPrice(lastSoldAt.getPrice());
        } else {
            result.setLastSoldAt(new Date(0));
            result.setLastSoldPrice(0);
        }

        return result;
    }
}
