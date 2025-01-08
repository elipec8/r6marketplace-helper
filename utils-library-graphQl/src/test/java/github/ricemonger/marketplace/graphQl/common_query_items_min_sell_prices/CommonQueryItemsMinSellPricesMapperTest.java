package github.ricemonger.marketplace.graphQl.common_query_items_min_sell_prices;

import github.ricemonger.marketplace.graphQl.common_query_items_min_sell_prices.DTO.marketableItems.Node;
import github.ricemonger.marketplace.graphQl.common_query_items_min_sell_prices.DTO.marketableItems.node.MarketData;
import github.ricemonger.marketplace.graphQl.common_query_items_min_sell_prices.DTO.marketableItems.node.marketData.SellStats;
import github.ricemonger.utils.DTOs.common.ItemMinSellPrice;
import github.ricemonger.utils.exceptions.server.GraphQlCommonItemMinSellPriceMappingException;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class CommonQueryItemsMinSellPricesMapperTest {

    private final CommonQueryItemsMinSellPricesMapper commonQueryItemsMinSellPricesMapper = new CommonQueryItemsMinSellPricesMapper();

    @Test
    public void mapItems_should_map_each_item() {
        Collection<Node> nodes = new ArrayList<>();

        Node node1 = createNode("itemId1", 10);
        nodes.add(node1);

        Node node2 = createNode("itemId2", 20);
        nodes.add(node2);

        Node node3 = createNode("itemId3", 30);
        nodes.add(node3);

        List<ItemMinSellPrice> itemMinSellPrices = commonQueryItemsMinSellPricesMapper.mapItems(nodes);

        assertEquals(3, itemMinSellPrices.size());
        assertTrue(itemMinSellPrices.stream().anyMatch(item -> item.getItemId().equals("itemId1") && item.getMinSellPrice().equals(10)));
        assertTrue(itemMinSellPrices.stream().anyMatch(item -> item.getItemId().equals("itemId2") && item.getMinSellPrice().equals(20)));
        assertTrue(itemMinSellPrices.stream().anyMatch(item -> item.getItemId().equals("itemId3") && item.getMinSellPrice().equals(30)));
    }

    @Test
    public void mapItem_should_map_item_with_valid_fields() throws ParseException {
        Node node = createNode("itemId1", 10);

        ItemMinSellPrice expectedItem = new ItemMinSellPrice();
        expectedItem.setItemId("itemId1");
        expectedItem.setMinSellPrice(10);

        ItemMinSellPrice resultItem = commonQueryItemsMinSellPricesMapper.mapItem(node);

        assertEquals(expectedItem.getMinSellPrice(), resultItem.getMinSellPrice());
        assertEquals(expectedItem.getItemId(), resultItem.getItemId());
    }

    @Test
    public void mapItem_should_map_item_with_null_SellStats() throws ParseException {
        Node node = createNode("itemId1", 10);
        node.getMarketData().setSellStats(null);

        ItemMinSellPrice expectedItem = new ItemMinSellPrice();
        expectedItem.setItemId("itemId1");
        expectedItem.setMinSellPrice(0);

        ItemMinSellPrice resultItem = commonQueryItemsMinSellPricesMapper.mapItem(node);

        assertSame(resultItem.getMinSellPrice(), expectedItem.getMinSellPrice());
        assertEquals(expectedItem.getItemId(), resultItem.getItemId());
    }

    @Test
    public void mapItem_should_throw_exception_when_node_is_null() {
        assertThrows(GraphQlCommonItemMinSellPriceMappingException.class, () -> commonQueryItemsMinSellPricesMapper.mapItem(null));
    }

    @Test
    public void mapItem_should_throw_exception_when_item_is_null() {
        Node node = new Node(null, new MarketData());
        assertThrows(GraphQlCommonItemMinSellPriceMappingException.class, () -> commonQueryItemsMinSellPricesMapper.mapItem(node));
    }

    @Test
    public void mapItem_should_throw_exception_when_item_id_is_null() {
        Node node = createNode(null, 10);
        node.getItem().setItemId(null);

        assertThrows(GraphQlCommonItemMinSellPriceMappingException.class, () -> commonQueryItemsMinSellPricesMapper.mapItem(node));
    }

    @Test
    public void mapItem_should_throw_exception_when_market_data_is_null() {
        Node node = createNode("itemId1", 10);
        node.setMarketData(null);

        assertThrows(GraphQlCommonItemMinSellPriceMappingException.class, () -> commonQueryItemsMinSellPricesMapper.mapItem(node));
    }

    @Test
    public void mapItem_should_throw_exception_when_sellStats_lowestPrice_is_null() {
        Node node = createNode("itemId1", 10);
        node.getMarketData().getSellStats()[0].setLowestPrice(null);

        assertThrows(GraphQlCommonItemMinSellPriceMappingException.class, () -> commonQueryItemsMinSellPricesMapper.mapItem(node));
    }

    private Node createNode(String itemId, Integer lowestPrice) {
        github.ricemonger.marketplace.graphQl.common_query_items_min_sell_prices.DTO.marketableItems.node.Item nodeItem =
                new github.ricemonger.marketplace.graphQl.common_query_items_min_sell_prices.DTO.marketableItems.node.Item();

        SellStats sellStats = new SellStats();
        sellStats.setLowestPrice(lowestPrice);

        MarketData nodeMarketData = new MarketData(new SellStats[]{sellStats});

        nodeItem.setItemId(itemId);

        return new Node(nodeItem, nodeMarketData);
    }
}