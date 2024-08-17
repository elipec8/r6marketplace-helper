package github.ricemonger.marketplace.graphQl.mappers;

import github.ricemonger.marketplace.graphQl.dtos.common_query_items.marketableItems.Node;
import github.ricemonger.marketplace.graphQl.dtos.common_query_items.marketableItems.node.MarketData;
import github.ricemonger.marketplace.graphQl.dtos.common_query_items.marketableItems.node.marketData.BuyStats;
import github.ricemonger.marketplace.graphQl.dtos.common_query_items.marketableItems.node.marketData.LastSoldAt;
import github.ricemonger.marketplace.graphQl.dtos.common_query_items.marketableItems.node.marketData.SellStats;
import github.ricemonger.marketplace.services.CommonValuesService;
import github.ricemonger.utils.dtos.Item;
import github.ricemonger.utils.enums.ItemType;
import github.ricemonger.utils.exceptions.GraphQlCommonItemMappingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class CommonQueryItemsMapperTest {
    @SpyBean
    private CommonQueryItemsMapper commonQueryItemsMapper;
    @Autowired
    private CommonValuesService commonValuesService;

    @Test
    public void mapItems_should_map_each_item() {
        Collection<Node> nodes = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();

        Node node1 = createNode(sdf, date);
        node1.getItem().setItemId("1");
        nodes.add(node1);

        Node node2 = createNode(sdf, date);
        node2.getItem().setItemId("2");
        nodes.add(node2);

        Node node3 = createNode(sdf, date);
        node3.getItem().setItemId("3");
        nodes.add(node3);

        List<Item> items = commonQueryItemsMapper.mapItems(nodes);

        assertEquals(3, items.size());
        verify(commonQueryItemsMapper, times(3)).mapItem(any());
    }

    @Test
    public void mapItem_should_map_item_with_valid_fields() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();

        Node node = createNode(sdf, date);

        Item expectedItem = new Item();
        expectedItem.setItemId("1");
        expectedItem.setAssetUrl("assetUrl");
        expectedItem.setName("name");
        expectedItem.setTags(List.of("Y2S1", "Y2S2"));
        expectedItem.setType(ItemType.WeaponSkin);
        expectedItem.setMaxBuyPrice(100);
        expectedItem.setBuyOrdersCount(10);
        expectedItem.setMinSellPrice(50);
        expectedItem.setSellOrdersCount(5);
        expectedItem.setLastSoldPrice(75);
        expectedItem.setLastSoldAt(date);

        Item resultItem = commonQueryItemsMapper.mapItem(node);

        assertEquals(expectedItem, resultItem);
    }

    @Test
    public void mapItem_should_map_item_with_invalid_item_type() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();

        Node node = createNode(sdf, date);
        node.getItem().setType("invalidType");

        Item expectedItem = new Item();
        expectedItem.setItemId("1");
        expectedItem.setAssetUrl("assetUrl");
        expectedItem.setName("name");
        expectedItem.setTags(List.of("Y2S1", "Y2S2"));
        expectedItem.setType(ItemType.Unknown);
        expectedItem.setMaxBuyPrice(100);
        expectedItem.setBuyOrdersCount(10);
        expectedItem.setMinSellPrice(50);
        expectedItem.setSellOrdersCount(5);
        expectedItem.setLastSoldPrice(75);
        expectedItem.setLastSoldAt(date);

        Item resultItem = commonQueryItemsMapper.mapItem(node);

        assertEquals(expectedItem, resultItem);
    }

    @Test
    public void mapItem_should_map_item_with_null_BuyStats() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();

        Node node = createNode(sdf, date);
        node.getMarketData().setBuyStats(null);

        Item expectedItem = new Item();
        expectedItem.setItemId("1");
        expectedItem.setAssetUrl("assetUrl");
        expectedItem.setName("name");
        expectedItem.setTags(List.of("Y2S1", "Y2S2"));
        expectedItem.setType(ItemType.WeaponSkin);
        expectedItem.setMaxBuyPrice(0);
        expectedItem.setBuyOrdersCount(0);
        expectedItem.setMinSellPrice(50);
        expectedItem.setSellOrdersCount(5);
        expectedItem.setLastSoldPrice(75);
        expectedItem.setLastSoldAt(date);

        Item resultItem = commonQueryItemsMapper.mapItem(node);

        assertEquals(expectedItem, resultItem);
    }

    @Test
    public void mapItem_should_map_item_with_null_SellStats() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();

        Node node = createNode(sdf, date);
        node.getMarketData().setSellStats(null);

        Item expectedItem = new Item();
        expectedItem.setItemId("1");
        expectedItem.setAssetUrl("assetUrl");
        expectedItem.setName("name");
        expectedItem.setTags(List.of("Y2S1", "Y2S2"));
        expectedItem.setType(ItemType.WeaponSkin);
        expectedItem.setMaxBuyPrice(100);
        expectedItem.setBuyOrdersCount(10);
        expectedItem.setMinSellPrice(0);
        expectedItem.setSellOrdersCount(0);
        expectedItem.setLastSoldPrice(75);
        expectedItem.setLastSoldAt(date);

        Item resultItem = commonQueryItemsMapper.mapItem(node);

        assertEquals(expectedItem, resultItem);
    }

    @Test
    public void mapItem_should_map_item_with_null_LastSoldAt() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();

        Node node = createNode(sdf, date);
        node.getMarketData().setLastSoldAt(null);

        Item expectedItem = new Item();
        expectedItem.setItemId("1");
        expectedItem.setAssetUrl("assetUrl");
        expectedItem.setName("name");
        expectedItem.setTags(List.of("Y2S1", "Y2S2"));
        expectedItem.setType(ItemType.WeaponSkin);
        expectedItem.setMaxBuyPrice(100);
        expectedItem.setBuyOrdersCount(10);
        expectedItem.setMinSellPrice(50);
        expectedItem.setSellOrdersCount(5);
        expectedItem.setLastSoldPrice(0);
        expectedItem.setLastSoldAt(new Date(0));

        Item resultItem = commonQueryItemsMapper.mapItem(node);

        assertEquals(expectedItem, resultItem);
    }

    @Test
    public void mapItem_should_throw_exception_when_node_is_null() {
        assertThrows(GraphQlCommonItemMappingException.class, () -> commonQueryItemsMapper.mapItem(null));
    }

    @Test
    public void mapItem_should_throw_exception_when_item_is_null() {
        Node node = new Node(null, new MarketData());
        assertThrows(GraphQlCommonItemMappingException.class, () -> commonQueryItemsMapper.mapItem(node));
    }

    @Test
    public void mapItem_should_throw_exception_when_item_id_is_null() {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();

        Node node = createNode(sdf, date);
        node.getItem().setItemId(null);

        assertThrows(GraphQlCommonItemMappingException.class, () -> commonQueryItemsMapper.mapItem(node));
    }

    @Test
    public void mapItem_should_throw_exception_when_item_assetUrl_is_null() {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();

        Node node = createNode(sdf, date);
        node.getItem().setAssetUrl(null);

        assertThrows(GraphQlCommonItemMappingException.class, () -> commonQueryItemsMapper.mapItem(node));
    }

    @Test
    public void mapItem_should_throw_exception_when_item_name_is_null() {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();

        Node node = createNode(sdf, date);
        node.getItem().setName(null);

        assertThrows(GraphQlCommonItemMappingException.class, () -> commonQueryItemsMapper.mapItem(node));
    }

    @Test
    public void mapItem_should_throw_exception_when_item_tags_is_null() {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();

        Node node = createNode(sdf, date);
        node.getItem().setTags(null);

        assertThrows(GraphQlCommonItemMappingException.class, () -> commonQueryItemsMapper.mapItem(node));
    }


    @Test
    public void mapItem_should_throw_exception_when_item_type_is_null() {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();

        Node node = createNode(sdf, date);
        node.getItem().setType(null);

        assertThrows(GraphQlCommonItemMappingException.class, () -> commonQueryItemsMapper.mapItem(node));
    }

    @Test
    public void mapItem_should_throw_exception_when_market_data_is_null() {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();

        Node node = createNode(sdf, date);
        node.setMarketData(null);

        assertThrows(GraphQlCommonItemMappingException.class, () -> commonQueryItemsMapper.mapItem(node));
    }

    @Test
    public void mapItem_should_throw_exception_when_buyStats_activeCount_is_null() {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();

        Node node = createNode(sdf, date);
        node.getMarketData().getBuyStats()[0].setActiveCount(null);

        assertThrows(GraphQlCommonItemMappingException.class, () -> commonQueryItemsMapper.mapItem(node));
    }

    @Test
    public void mapItem_should_throw_exception_when_buyStats_highestPrice_is_null() {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();

        Node node = createNode(sdf, date);
        node.getMarketData().getBuyStats()[0].setHighestPrice(null);

        assertThrows(GraphQlCommonItemMappingException.class, () -> commonQueryItemsMapper.mapItem(node));
    }

    @Test
    public void mapItem_should_throw_exception_when_sellStats_activeCount_is_null() {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();

        Node node = createNode(sdf, date);
        node.getMarketData().getSellStats()[0].setActiveCount(null);

        assertThrows(GraphQlCommonItemMappingException.class, () -> commonQueryItemsMapper.mapItem(node));
    }

    @Test
    public void mapItem_should_throw_exception_when_sellStats_lowestPrice_is_null() {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();

        Node node = createNode(sdf, date);
        node.getMarketData().getSellStats()[0].setLowestPrice(null);

        assertThrows(GraphQlCommonItemMappingException.class, () -> commonQueryItemsMapper.mapItem(node));
    }

    @Test
    public void mapItem_should_throw_exception_when_lastSoldAt_performedAt_is_null() {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();

        Node node = createNode(sdf, date);
        node.getMarketData().getLastSoldAt()[0].setPerformedAt(null);

        assertThrows(GraphQlCommonItemMappingException.class, () -> commonQueryItemsMapper.mapItem(node));
    }

    @Test
    public void mapItem_should_throw_exception_when_lastSoldAt_price_is_null() {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();

        Node node = createNode(sdf, date);
        node.getMarketData().getLastSoldAt()[0].setPrice(null);

        assertThrows(GraphQlCommonItemMappingException.class, () -> commonQueryItemsMapper.mapItem(node));
    }

    private Node createNode(SimpleDateFormat sdf, Date date) {
        github.ricemonger.marketplace.graphQl.dtos.common_query_items.marketableItems.node.Item nodeItem =
                new github.ricemonger.marketplace.graphQl.dtos.common_query_items.marketableItems.node.Item();

        BuyStats buyStats = new BuyStats();
        buyStats.setHighestPrice(100);
        buyStats.setActiveCount(10);

        SellStats sellStats = new SellStats();
        sellStats.setLowestPrice(50);
        sellStats.setActiveCount(5);

        LastSoldAt lastSoldAt = new LastSoldAt();
        lastSoldAt.setPrice(75);
        lastSoldAt.setPerformedAt(sdf.format(date));

        MarketData nodeMarketData = new MarketData(new SellStats[]{sellStats}, new BuyStats[]{buyStats}, new LastSoldAt[]{lastSoldAt});

        nodeItem.setItemId("1");
        nodeItem.setAssetUrl("assetUrl");
        nodeItem.setName("name");
        nodeItem.setTags(List.of("Y2S1", "Y2S2"));
        nodeItem.setType(ItemType.WeaponSkin.name());

        return new Node(nodeItem, nodeMarketData);
    }
}