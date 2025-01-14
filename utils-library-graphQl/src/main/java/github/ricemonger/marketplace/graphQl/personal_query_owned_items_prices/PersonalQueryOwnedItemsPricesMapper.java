package github.ricemonger.marketplace.graphQl.personal_query_owned_items_prices;


import github.ricemonger.marketplace.graphQl.personal_query_owned_items_prices.DTO.marketableItems.Node;
import github.ricemonger.marketplace.graphQl.personal_query_owned_items_prices.DTO.marketableItems.node.Item;
import github.ricemonger.marketplace.graphQl.personal_query_owned_items_prices.DTO.marketableItems.node.MarketData;
import github.ricemonger.marketplace.graphQl.personal_query_owned_items_prices.DTO.marketableItems.node.marketData.BuyStats;
import github.ricemonger.marketplace.graphQl.personal_query_owned_items_prices.DTO.marketableItems.node.marketData.SellStats;
import github.ricemonger.utils.DTOs.common.ItemCurrentPrices;
import github.ricemonger.utils.exceptions.server.GraphQlPersonalOwnedItemPricesMappingException;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class PersonalQueryOwnedItemsPricesMapper {

    public List<ItemCurrentPrices> mapItems(Collection<Node> nodes) throws GraphQlPersonalOwnedItemPricesMappingException {
        if (nodes == null) {
            throw new GraphQlPersonalOwnedItemPricesMappingException("Nodes is null");
        }
        return nodes.stream().map(this::mapItem).collect(Collectors.toList());
    }

    public ItemCurrentPrices mapItem(Node node) throws GraphQlPersonalOwnedItemPricesMappingException {
        if (node == null) {
            throw new GraphQlPersonalOwnedItemPricesMappingException("Node is null");
        }
        Item item = node.getItem();

        if (item == null) {
            throw new GraphQlPersonalOwnedItemPricesMappingException("Item is null, node-" + node);
        }

        ItemCurrentPrices result = new ItemCurrentPrices();

        if (item.getItemId() == null) {
            throw new GraphQlPersonalOwnedItemPricesMappingException("One of Item fields is null, item-" + item);
        }

        result.setItemId(item.getItemId());


        if (node.getMarketData() == null) {
            throw new GraphQlPersonalOwnedItemPricesMappingException("Market data is null, node-" + node);
        }

        MarketData marketData = node.getMarketData();

        BuyStats buyStats = marketData.getBuyStats() == null || marketData.getBuyStats().length == 0 ? null : marketData.getBuyStats()[0];
        SellStats sellStats = marketData.getSellStats() == null || marketData.getSellStats().length == 0 ? null : marketData.getSellStats()[0];

        if (buyStats != null) {
            if (buyStats.getHighestPrice() == null) {
                throw new GraphQlPersonalOwnedItemPricesMappingException("BuyStats highest price is null, buyStats-" + buyStats);
            }
            result.setMaxBuyPrice(buyStats.getHighestPrice());
        } else {
            result.setMaxBuyPrice(null);
        }

        if (sellStats != null) {
            if (sellStats.getLowestPrice() == null) {
                throw new GraphQlPersonalOwnedItemPricesMappingException("SellStats lowest price is null, sellStats-" + sellStats);
            }
            result.setMinSellPrice(sellStats.getLowestPrice());
        } else {
            result.setMinSellPrice(null);
        }

        return result;
    }
}
