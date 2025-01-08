package github.ricemonger.marketplace.graphQl.common_query_items_min_sell_prices;

import github.ricemonger.marketplace.graphQl.common_query_items_min_sell_prices.DTO.marketableItems.Node;
import github.ricemonger.marketplace.graphQl.common_query_items_min_sell_prices.DTO.marketableItems.node.MarketData;
import github.ricemonger.marketplace.graphQl.common_query_items_min_sell_prices.DTO.marketableItems.node.marketData.SellStats;
import github.ricemonger.utils.DTOs.common.ItemMinSellPrice;
import github.ricemonger.utils.exceptions.server.GraphQlCommonItemMinSellPriceMappingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class CommonQueryItemsMinSellPricesMapper {

    public List<ItemMinSellPrice> mapItems(Collection<Node> nodes) throws GraphQlCommonItemMinSellPriceMappingException {
        return nodes.stream().map(this::mapItem).collect(Collectors.toList());
    }

    public ItemMinSellPrice mapItem(Node node) throws GraphQlCommonItemMinSellPriceMappingException {
        if (node == null) {
            throw new GraphQlCommonItemMinSellPriceMappingException("Node is null");
        }
        github.ricemonger.marketplace.graphQl.common_query_items_min_sell_prices.DTO.marketableItems.node.Item item = node.getItem();

        if (item == null) {
            throw new GraphQlCommonItemMinSellPriceMappingException("Item is null, node-" + node);
        }

        ItemMinSellPrice result = new ItemMinSellPrice();

        if (item.getItemId() == null) {
            throw new GraphQlCommonItemMinSellPriceMappingException("One of Item fields is null, item-" + item);
        }

        result.setItemId(item.getItemId());

        if (node.getMarketData() == null) {
            throw new GraphQlCommonItemMinSellPriceMappingException("Market data is null, node-" + node);
        }

        MarketData marketData = node.getMarketData();

        SellStats sellStats = marketData.getSellStats() == null || marketData.getSellStats().length == 0 ? null : marketData.getSellStats()[0];


        if (sellStats != null) {
            if (sellStats.getLowestPrice() == null) {
                throw new GraphQlCommonItemMinSellPriceMappingException("SellStats lowest price or active count is null, sellStats-" + sellStats);
            }
            result.setMinSellPrice(sellStats.getLowestPrice());
        } else {
            result.setMinSellPrice(0);
        }

        return result;
    }
}
