package github.ricemonger.marketplace.graphQl.common_query_items_prices;

import github.ricemonger.marketplace.graphQl.common_query_items_prices.DTO.MarketableItems;
import github.ricemonger.marketplace.graphQl.common_query_items_prices.DTO.marketableItems.Node;
import github.ricemonger.marketplace.graphQl.common_query_items_prices.DTO.marketableItems.node.Item;
import github.ricemonger.marketplace.graphQl.common_query_items_prices.DTO.marketableItems.node.MarketData;
import github.ricemonger.marketplace.graphQl.common_query_items_prices.DTO.marketableItems.node.marketData.BuyStats;
import github.ricemonger.marketplace.graphQl.common_query_items_prices.DTO.marketableItems.node.marketData.SellStats;
import github.ricemonger.utils.DTOs.common.ItemCurrentPrices;
import github.ricemonger.utils.exceptions.server.GraphQlCommonItemPricesMappingException;
import org.junit.jupiter.api.Test;
import org.springframework.graphql.client.ClientGraphQlResponse;
import org.springframework.graphql.client.ClientResponseField;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CommonQueryItemsPricesMapperTest {
    private CommonQueryItemsPricesMapper mapper = spy(new CommonQueryItemsPricesMapper());

    @Test
    public void mapLimitedItemsStats_should_throw_if_empty_aliases_and_empty_marketableItems() {
        ClientGraphQlResponse response = mock(ClientGraphQlResponse.class);

        ClientResponseField responseField1 = mock(ClientResponseField.class);
        ClientResponseField responseField2 = mock(ClientResponseField.class);
        ClientResponseField responseField3 = mock(ClientResponseField.class);
        ClientResponseField marketableItemsResponseField = mock(ClientResponseField.class);

        when(response.field("game.alias1.nodes")).thenReturn(responseField1);
        when(response.field("game.alias2.nodes")).thenReturn(responseField2);
        when(response.field("game.alias3.nodes")).thenReturn(responseField3);
        when(response.field("game.marketableItems")).thenReturn(marketableItemsResponseField);

        when(marketableItemsResponseField.toEntity(MarketableItems.class)).thenReturn(new MarketableItems());

        Map<String, String> aliasesToFields = Map.of(

        );

        assertThrows(GraphQlCommonItemPricesMappingException.class, () -> mapper.mapLimitedItemsStats(response, aliasesToFields));
    }

    @Test
    public void mapLimitedItemsStats_should_throw_if_empty_aliases_and_null_marketableItems() {
        ClientGraphQlResponse response = mock(ClientGraphQlResponse.class);

        ClientResponseField responseField1 = mock(ClientResponseField.class);
        ClientResponseField responseField2 = mock(ClientResponseField.class);
        ClientResponseField responseField3 = mock(ClientResponseField.class);
        ClientResponseField marketableItemsResponseField = mock(ClientResponseField.class);

        when(response.field("game.alias1.nodes")).thenReturn(responseField1);
        when(response.field("game.alias2.nodes")).thenReturn(responseField2);
        when(response.field("game.alias3.nodes")).thenReturn(responseField3);
        when(response.field("game.marketableItems")).thenReturn(marketableItemsResponseField);

        when(marketableItemsResponseField.toEntity(MarketableItems.class)).thenReturn(null);

        Map<String, String> aliasesToFields = Map.of(

        );

        assertThrows(GraphQlCommonItemPricesMappingException.class, () -> mapper.mapLimitedItemsStats(response, aliasesToFields));
    }

    @Test
    public void mapLimitedItemsStats_should_throw_if_null_aliases_and_empty_marketable_items() {
        ClientGraphQlResponse response = mock(ClientGraphQlResponse.class);

        ClientResponseField responseField1 = mock(ClientResponseField.class);
        ClientResponseField responseField2 = mock(ClientResponseField.class);
        ClientResponseField responseField3 = mock(ClientResponseField.class);
        ClientResponseField marketableItemsResponseField = mock(ClientResponseField.class);

        when(response.field("game.alias1.nodes")).thenReturn(responseField1);
        when(response.field("game.alias2.nodes")).thenReturn(responseField2);
        when(response.field("game.alias3.nodes")).thenReturn(responseField3);
        when(response.field("game.marketableItems")).thenReturn(marketableItemsResponseField);

        when(marketableItemsResponseField.toEntity(MarketableItems.class)).thenReturn(new MarketableItems());

        Map<String, String> aliasesToFields = null;

        assertThrows(GraphQlCommonItemPricesMappingException.class, () -> mapper.mapLimitedItemsStats(response, aliasesToFields));
    }

    @Test
    public void mapLimitedItemsStats_should_throw_if_null_aliases_and_marketable_items() {
        ClientGraphQlResponse response = mock(ClientGraphQlResponse.class);

        ClientResponseField responseField1 = mock(ClientResponseField.class);
        ClientResponseField responseField2 = mock(ClientResponseField.class);
        ClientResponseField responseField3 = mock(ClientResponseField.class);
        ClientResponseField marketableItemsResponseField = mock(ClientResponseField.class);

        when(response.field("game.alias1.nodes")).thenReturn(responseField1);
        when(response.field("game.alias2.nodes")).thenReturn(responseField2);
        when(response.field("game.alias3.nodes")).thenReturn(responseField3);
        when(response.field("game.marketableItems")).thenReturn(marketableItemsResponseField);

        when(marketableItemsResponseField.toEntity(MarketableItems.class)).thenReturn(null);

        Map<String, String> aliasesToFields = null;

        assertThrows(GraphQlCommonItemPricesMappingException.class, () -> mapper.mapLimitedItemsStats(response, aliasesToFields));
    }

    @Test
    public void mapLimitedItemsStats_should_map_items_if_null_marketable_items() {
        ClientGraphQlResponse response = mock(ClientGraphQlResponse.class);

        ClientResponseField responseField1 = mock(ClientResponseField.class);
        ClientResponseField responseField2 = mock(ClientResponseField.class);
        ClientResponseField responseField3 = mock(ClientResponseField.class);
        ClientResponseField marketableItemsResponseField = mock(ClientResponseField.class);

        when(response.field("game.alias1.nodes")).thenReturn(responseField1);
        when(response.field("game.alias2.nodes")).thenReturn(responseField2);
        when(response.field("game.alias3.nodes")).thenReturn(responseField3);
        when(response.field("game.marketableItems")).thenReturn(marketableItemsResponseField);

        when(marketableItemsResponseField.toEntity(MarketableItems.class)).thenReturn(null);

        Map<String, String> aliasesToFields = Map.of(
                "alias1", "marketableItems",
                "alias2", "marketableItems",
                "alias3", "marketableItems"
        );

        List<Node> list1 = new ArrayList<>();
        list1.add(new Node(new Item("itemId1"), new MarketData(new SellStats[]{new SellStats(50)}, new BuyStats[]{new BuyStats(100)})));

        List<Node> list2 = new ArrayList<>();

        List<Node> list3 = new ArrayList<>();
        list3.add(new Node(new Item("itemId3"), new MarketData(new SellStats[]{new SellStats(150)}, new BuyStats[]{new BuyStats(200)})));
        list3.add(new Node(new Item("itemId4"), new MarketData(new SellStats[]{new SellStats(250)}, new BuyStats[]{new BuyStats(300)})));

        when(responseField1.toEntityList(Node.class)).thenReturn(list1);
        when(responseField2.toEntityList(Node.class)).thenReturn(list2);
        when(responseField3.toEntityList(Node.class)).thenReturn(list3);

        List<ItemCurrentPrices> expected = new ArrayList<>();
        expected.add(new ItemCurrentPrices("itemId1", 50, 100));
        expected.add(new ItemCurrentPrices("itemId3", 150, 200));
        expected.add(new ItemCurrentPrices("itemId4", 250, 300));

        List<ItemCurrentPrices> result = mapper.mapLimitedItemsStats(response, aliasesToFields);

        assertEquals(expected.size(), result.size());
        assertTrue(result.containsAll(expected));
    }

    @Test
    public void mapLimitedItemsStats_should_map_items_if_not_null_marketable_items() {
        ClientGraphQlResponse response = mock(ClientGraphQlResponse.class);

        ClientResponseField responseField1 = mock(ClientResponseField.class);
        ClientResponseField responseField2 = mock(ClientResponseField.class);
        ClientResponseField responseField3 = mock(ClientResponseField.class);
        ClientResponseField marketableItemsResponseField = mock(ClientResponseField.class);

        when(response.field("game.alias1.nodes")).thenReturn(responseField1);
        when(response.field("game.alias2.nodes")).thenReturn(responseField2);
        when(response.field("game.alias3.nodes")).thenReturn(responseField3);
        when(response.field("game.marketableItems")).thenReturn(marketableItemsResponseField);

        Map<String, String> aliasesToFields = Map.of(
                "alias1", "marketableItems",
                "alias2", "marketableItems",
                "alias3", "marketableItems"
        );

        List<Node> list1 = new ArrayList<>();
        list1.add(new Node(new Item("itemId1"), new MarketData(new SellStats[]{new SellStats(50)}, new BuyStats[]{new BuyStats(100)})));

        List<Node> list2 = new ArrayList<>();

        List<Node> list3 = new ArrayList<>();
        list3.add(new Node(new Item("itemId3"), new MarketData(new SellStats[]{new SellStats(150)}, new BuyStats[]{new BuyStats(200)})));
        list3.add(new Node(new Item("itemId4"), new MarketData(new SellStats[]{new SellStats(250)}, new BuyStats[]{new BuyStats(300)})));

        when(responseField1.toEntityList(Node.class)).thenReturn(list1);
        when(responseField2.toEntityList(Node.class)).thenReturn(list2);
        when(responseField3.toEntityList(Node.class)).thenReturn(list3);

        List<Node> marketableItemsNodes = new ArrayList<>();
        marketableItemsNodes.add(new Node(new Item("itemId5"), new MarketData(new SellStats[]{new SellStats(500)}, new BuyStats[]{new BuyStats(1000)})));
        marketableItemsNodes.add(new Node(new Item("itemId6"), new MarketData(new SellStats[]{new SellStats(600)},
                new BuyStats[]{new BuyStats(2000)})));

        when(marketableItemsResponseField.toEntity(MarketableItems.class)).thenReturn(new MarketableItems(marketableItemsNodes, 5));

        List<ItemCurrentPrices> expected = new ArrayList<>();
        expected.add(new ItemCurrentPrices("itemId5", 500, 1000));
        expected.add(new ItemCurrentPrices("itemId6", 600, 2000));

        List<ItemCurrentPrices> result = mapper.mapLimitedItemsStats(response, aliasesToFields);

        assertEquals(expected.size(), result.size());
        assertTrue(result.containsAll(expected));
    }


    @Test
    public void mapItems_should_map_each_item() {
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

        List<ItemCurrentPrices> itemMainFields = mapper.mapItems(nodes);

        assertEquals(3, itemMainFields.size());
        verify(mapper, times(3)).mapItem(any());
    }

    @Test
    public void mapItem_should_map_item_with_valid_fields() throws ParseException {
        Node node = createNode();

        ItemCurrentPrices expectedItem = new ItemCurrentPrices();
        expectedItem.setItemId("1");
        expectedItem.setMaxBuyPrice(100);
        expectedItem.setMinSellPrice(50);

        ItemCurrentPrices resultItem = mapper.mapItem(node);

        assertEquals(expectedItem, resultItem);
    }


    @Test
    public void mapItem_should_map_item_with_null_BuyStats() throws ParseException {
        Node node = createNode();
        node.getMarketData().setBuyStats(null);

        ItemCurrentPrices expectedItem = new ItemCurrentPrices();
        expectedItem.setItemId("1");
        expectedItem.setMaxBuyPrice(0);
        expectedItem.setMinSellPrice(50);

        ItemCurrentPrices resultItem = mapper.mapItem(node);

        assertEquals(expectedItem, resultItem);
    }

    @Test
    public void mapItem_should_map_item_with_null_SellStats() throws ParseException {
        Node node = createNode();
        node.getMarketData().setSellStats(null);

        ItemCurrentPrices expectedItem = new ItemCurrentPrices();
        expectedItem.setItemId("1");
        expectedItem.setMaxBuyPrice(100);
        expectedItem.setMinSellPrice(0);

        ItemCurrentPrices resultItem = mapper.mapItem(node);

        assertEquals(expectedItem, resultItem);
    }

    @Test
    public void mapItem_should_throw_exception_when_node_is_null() {
        assertThrows(GraphQlCommonItemPricesMappingException.class, () -> mapper.mapItem(null));
    }

    @Test
    public void mapItem_should_throw_exception_when_item_is_null() {
        Node node = new Node(null, new MarketData());
        assertThrows(GraphQlCommonItemPricesMappingException.class, () -> mapper.mapItem(node));
    }

    @Test
    public void mapItem_should_throw_exception_when_item_id_is_null() {
        Node node = createNode();
        node.getItem().setItemId(null);

        assertThrows(GraphQlCommonItemPricesMappingException.class, () -> mapper.mapItem(node));
    }

    @Test
    public void mapItem_should_throw_exception_when_market_data_is_null() {


        Node node = createNode();
        node.setMarketData(null);

        assertThrows(GraphQlCommonItemPricesMappingException.class, () -> mapper.mapItem(node));
    }

    @Test
    public void mapItem_should_throw_exception_when_buyStats_highestPrice_is_null() {
        Node node = createNode();
        node.getMarketData().getBuyStats()[0].setHighestPrice(null);

        assertThrows(GraphQlCommonItemPricesMappingException.class, () -> mapper.mapItem(node));
    }

    @Test
    public void mapItem_should_throw_exception_when_sellStats_lowestPrice_is_null() {
        Node node = createNode();
        node.getMarketData().getSellStats()[0].setLowestPrice(null);

        assertThrows(GraphQlCommonItemPricesMappingException.class, () -> mapper.mapItem(node));
    }

    private Node createNode() {
        Item nodeItem = new Item();

        BuyStats buyStats = new BuyStats();
        buyStats.setHighestPrice(100);
        ;

        SellStats sellStats = new SellStats();
        sellStats.setLowestPrice(50);

        MarketData nodeMarketData = new MarketData(new SellStats[]{sellStats}, new BuyStats[]{buyStats});

        nodeItem.setItemId("1");

        return new Node(nodeItem, nodeMarketData);
    }
}