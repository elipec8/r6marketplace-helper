package github.ricemonger.marketplace.graphQl.common_query_items_prices;

import github.ricemonger.marketplace.graphQl.common_query_items_prices.DTO.MarketableItems;
import github.ricemonger.marketplace.graphQl.common_query_items_prices.DTO.marketableItems.Node;
import github.ricemonger.marketplace.graphQl.common_query_items_prices.DTO.marketableItems.node.MarketData;
import github.ricemonger.marketplace.graphQl.common_query_items_prices.DTO.marketableItems.node.marketData.BuyStats;
import github.ricemonger.marketplace.graphQl.common_query_items_prices.DTO.marketableItems.node.marketData.SellStats;
import github.ricemonger.utils.DTOs.common.ItemCurrentPrices;
import github.ricemonger.utils.exceptions.server.GraphQlCommonItemPricesMappingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.client.ClientGraphQlResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class CommonQueryItemsPricesMapper {

    public List<ItemCurrentPrices> mapLimitedItemsStats(ClientGraphQlResponse response, Map<String, String> aliasesToFields) {

        List<ItemCurrentPrices> result = new ArrayList<>();

        String gameField = "game";

        MarketableItems marketableItems = response.field(gameField + ".marketableItems").toEntity(MarketableItems.class);

        if (marketableItems == null || marketableItems.getNodes() == null) {

            if (aliasesToFields == null || aliasesToFields.isEmpty()) {
                throw new GraphQlCommonItemPricesMappingException("MarketableItems and Aliases are null or empty, cant fetch items prices");
            }

            for (Map.Entry<String, String> entry : aliasesToFields.entrySet()) {
                if (entry.getValue().equals("marketableItems")) {
                    List<Node> nodes = response.field(gameField + "." + entry.getKey() + ".nodes").toEntityList(Node.class);

                    if (nodes.isEmpty()) {
                        log.info("No items found by alias: " + entry.getKey());
                    } else {
                        result.addAll(mapItems(nodes));
                    }
                }
            }
        } else {
            log.info("Extracting items from marketableItems");
            result = mapItems(marketableItems.getNodes());
        }

        return result;
    }

    public List<ItemCurrentPrices> mapItems(Collection<Node> nodes) throws GraphQlCommonItemPricesMappingException {
        return nodes.stream().map(this::mapItem).collect(Collectors.toList());
    }

    public ItemCurrentPrices mapItem(Node node) throws GraphQlCommonItemPricesMappingException {
        if (node == null) {
            throw new GraphQlCommonItemPricesMappingException("Node is null");
        }
        github.ricemonger.marketplace.graphQl.common_query_items_prices.DTO.marketableItems.node.Item item = node.getItem();

        if (item == null) {
            throw new GraphQlCommonItemPricesMappingException("Item is null, node-" + node);
        }

        ItemCurrentPrices result = new ItemCurrentPrices();

        if (item.getItemId() == null) {
            throw new GraphQlCommonItemPricesMappingException("One of Item fields is null, item-" + item);
        }

        result.setItemId(item.getItemId());

        if (node.getMarketData() == null) {
            throw new GraphQlCommonItemPricesMappingException("Market data is null, node-" + node);
        }

        MarketData marketData = node.getMarketData();

        BuyStats buyStats = marketData.getBuyStats() == null || marketData.getBuyStats().length == 0 ? null : marketData.getBuyStats()[0];
        SellStats sellStats = marketData.getSellStats() == null || marketData.getSellStats().length == 0 ? null : marketData.getSellStats()[0];

        if (buyStats != null) {
            if (buyStats.getHighestPrice() == null) {
                throw new GraphQlCommonItemPricesMappingException("BuyStats highest price or active count is null, buyStats-" + buyStats);
            }
            result.setMaxBuyPrice(buyStats.getHighestPrice());
        } else {
            result.setMaxBuyPrice(0);
        }

        if (sellStats != null) {
            if (sellStats.getLowestPrice() == null) {
                throw new GraphQlCommonItemPricesMappingException("SellStats lowest price or active count is null, sellStats-" + sellStats);
            }
            result.setMinSellPrice(sellStats.getLowestPrice());
        } else {
            result.setMinSellPrice(0);
        }

        return result;
    }
}
