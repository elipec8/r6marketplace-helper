package github.ricemonger.marketplace.graphQl.personal_query_owned_items_prices;

import github.ricemonger.marketplace.graphQl.personal_query_owned_items_prices.DTO.marketableItems.Node;
import github.ricemonger.marketplace.graphQl.personal_query_owned_items_prices.DTO.marketableItems.node.Item;
import github.ricemonger.marketplace.graphQl.personal_query_owned_items_prices.DTO.marketableItems.node.MarketData;
import github.ricemonger.marketplace.graphQl.personal_query_owned_items_prices.DTO.marketableItems.node.marketData.BuyStats;
import github.ricemonger.marketplace.graphQl.personal_query_owned_items_prices.DTO.marketableItems.node.marketData.SellStats;
import github.ricemonger.utils.DTOs.common.ItemCurrentPrices;
import github.ricemonger.utils.exceptions.server.GraphQlPersonalOwnedItemPricesMappingException;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class PersonalQueryOwnedItemsPricesMapperTest {

    private PersonalQueryOwnedItemsPricesMapper personalQueryOwnedItemsPricesMapper = spy(PersonalQueryOwnedItemsPricesMapper.class);

    @Test
    public void mapOwnedItems_should_map_each_item() {
        Collection<Node> nodes = new ArrayList<>();

        Node node1 = createNode();
        node1.getItem().setItemId("1");
        nodes.add(node1);

        Node node2 = createNode();
        node2.getItem().setItemId("2");
        nodes.add(node2);

        Node node3 = createNode();
        node3.getItem().setItemId("3");
        nodes.add(node3);

        List<ItemCurrentPrices> itemMainFields = personalQueryOwnedItemsPricesMapper.mapItems(nodes);

        assertEquals(3, itemMainFields.size());
        verify(personalQueryOwnedItemsPricesMapper).mapItem(node1);
        verify(personalQueryOwnedItemsPricesMapper).mapItem(node2);
        verify(personalQueryOwnedItemsPricesMapper).mapItem(node3);
    }

    @Test
    public void mapItem_should_map_item_with_valid_fields() throws ParseException {
        Node node = createNode();

        ItemCurrentPrices expectedItem = new ItemCurrentPrices();
        expectedItem.setItemId("1");
        expectedItem.setMaxBuyPrice(100);
        expectedItem.setMinSellPrice(50);

        ItemCurrentPrices resultItem = personalQueryOwnedItemsPricesMapper.mapItem(node);

        assertEquals(resultItem.getItemId(), expectedItem.getItemId());
        assertEquals(resultItem.getMaxBuyPrice(), expectedItem.getMaxBuyPrice());
        assertEquals(resultItem.getMinSellPrice(), expectedItem.getMinSellPrice());
    }

    @Test
    public void mapItem_should_map_item_with_null_BuyStats() throws ParseException {
        Node node = createNode();
        node.getMarketData().setBuyStats(null);

        ItemCurrentPrices expectedItem = new ItemCurrentPrices();
        expectedItem.setItemId("1");
        expectedItem.setMaxBuyPrice(null);
        expectedItem.setMinSellPrice(50);

        ItemCurrentPrices resultItem = personalQueryOwnedItemsPricesMapper.mapItem(node);

        assertEquals(resultItem.getItemId(), expectedItem.getItemId());
        assertEquals(resultItem.getMaxBuyPrice(), expectedItem.getMaxBuyPrice());
        assertEquals(resultItem.getMinSellPrice(), expectedItem.getMinSellPrice());
    }

    @Test
    public void mapItem_should_map_item_with_null_SellStats() throws ParseException {
        Node node = createNode();
        node.getMarketData().setSellStats(null);

        ItemCurrentPrices expectedItem = new ItemCurrentPrices();
        expectedItem.setItemId("1");
        expectedItem.setMaxBuyPrice(100);
        expectedItem.setMinSellPrice(null);

        ItemCurrentPrices resultItem = personalQueryOwnedItemsPricesMapper.mapItem(node);

        assertEquals(resultItem.getItemId(), expectedItem.getItemId());
        assertEquals(resultItem.getMaxBuyPrice(), expectedItem.getMaxBuyPrice());
        assertEquals(resultItem.getMinSellPrice(), expectedItem.getMinSellPrice());
    }

    @Test
    public void mapItem_should_throw_exception_when_node_is_null() {
        assertThrows(GraphQlPersonalOwnedItemPricesMappingException.class, () -> personalQueryOwnedItemsPricesMapper.mapItem(null));
    }

    @Test
    public void mapItem_should_throw_exception_when_item_is_null() {
        Node node = new Node(null, new MarketData());
        assertThrows(GraphQlPersonalOwnedItemPricesMappingException.class, () -> personalQueryOwnedItemsPricesMapper.mapItem(node));
    }

    @Test
    public void mapItem_should_throw_exception_when_item_id_is_null() {
        Node node = createNode();
        node.getItem().setItemId(null);

        assertThrows(GraphQlPersonalOwnedItemPricesMappingException.class, () -> personalQueryOwnedItemsPricesMapper.mapItem(node));
    }

    @Test
    public void mapItem_should_throw_exception_when_market_data_is_null() {
        Node node = createNode();
        node.setMarketData(null);

        assertThrows(GraphQlPersonalOwnedItemPricesMappingException.class, () -> personalQueryOwnedItemsPricesMapper.mapItem(node));
    }

    @Test
    public void mapItem_should_throw_exception_when_buyStats_highestPrice_is_null() {
        Node node = createNode();
        node.getMarketData().getBuyStats()[0].setHighestPrice(null);

        assertThrows(GraphQlPersonalOwnedItemPricesMappingException.class, () -> personalQueryOwnedItemsPricesMapper.mapItem(node));
    }

    @Test
    public void mapItem_should_throw_exception_when_sellStats_lowestPrice_is_null() {
        Node node = createNode();
        node.getMarketData().getSellStats()[0].setLowestPrice(null);

        assertThrows(GraphQlPersonalOwnedItemPricesMappingException.class, () -> personalQueryOwnedItemsPricesMapper.mapItem(node));
    }

    private Node createNode() {
        Item nodeItem = new Item();

        BuyStats buyStats = new BuyStats();
        buyStats.setHighestPrice(100);

        SellStats sellStats = new SellStats();
        sellStats.setLowestPrice(50);

        MarketData nodeMarketData = new MarketData(new SellStats[]{sellStats}, new BuyStats[]{buyStats});

        nodeItem.setItemId("1");

        return new Node(nodeItem, nodeMarketData);
    }

}