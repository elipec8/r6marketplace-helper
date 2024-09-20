package github.ricemonger.marketplace.graphQl.mappers;

import github.ricemonger.marketplace.graphQl.dtos.common_query_items_sale_stats.marketableItems.Node;
import github.ricemonger.marketplace.graphQl.dtos.common_query_items_sale_stats.marketableItems.node.Item;
import github.ricemonger.marketplace.graphQl.dtos.common_query_items_sale_stats.marketableItems.node.PriceHistory;
import github.ricemonger.marketplace.services.CommonValuesService;
import github.ricemonger.utils.dtos.ItemDaySales;
import github.ricemonger.utils.dtos.ItemSaleUbiStats;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@SpringBootTest
class CommonQueryItemsSaleStatsMapperTest {
    @SpyBean
    private CommonQueryItemsSaleStatsMapper commonQueryItemsSaleStatsMapper;
    @SpyBean
    private CommonValuesService commonValuesService;

    @Test
    public void mapItemsSaleStats_should_call_map_itemSaleStats_for_each_node() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getItemSaleStatsDateFormat());

        Node node1 = new Node();
        node1.setItem(new Item("1"));
        node1.setPriceHistory(List.of());
        Node node2 = new Node();
        node2.setItem(new Item("2"));
        node2.setPriceHistory(List.of(new PriceHistory(sdf.format(new Date(0)), 100, 200, 500, 6)));
        List<Node> nodes = List.of(node1, node2);

        ItemSaleUbiStats expected1 = new ItemSaleUbiStats("1", List.of());
        ItemSaleUbiStats expected2 = new ItemSaleUbiStats("2", List.of(new ItemDaySales(sdf.parse(sdf.format(new Date(0))), 100, 200, 500, 6, 150)));
        List<ItemSaleUbiStats> expected = List.of(expected1, expected2);

        List<ItemSaleUbiStats> result = commonQueryItemsSaleStatsMapper.mapItemsSaleStats(nodes);

        assertTrue(result.containsAll(expected) && expected.containsAll(result));

        verify(commonQueryItemsSaleStatsMapper).mapItemSaleStats(node1);
        verify(commonQueryItemsSaleStatsMapper).mapItemSaleStats(node2);
    }

    @Test
    public void mapItemSaleStats_should_get_itemId_and_call_mapAllItemDaySales() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getItemSaleStatsDateFormat());

        Node node = new Node();
        node.setItem(new Item("1"));
        node.setPriceHistory(List.of(new PriceHistory(sdf.format(new Date(0)), 100, 200, 500, 6)));

        ItemSaleUbiStats expected = new ItemSaleUbiStats("1", List.of(new ItemDaySales(sdf.parse(sdf.format(new Date(0))), 100, 200, 500, 6, 150)));

        ItemSaleUbiStats result = commonQueryItemsSaleStatsMapper.mapItemSaleStats(node);

        assertEquals(expected, result);

        verify(commonQueryItemsSaleStatsMapper).mapAllItemDaySales(node.getPriceHistory());
    }

    @Test
    public void mapAllItemDaySales_should_cal_mapItemDaySales_for_each_priceHistory() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getItemSaleStatsDateFormat());

        PriceHistory priceHistory1 = new PriceHistory(sdf.format(new Date(0)), 100, 200, 500, 6);
        PriceHistory priceHistory2 = new PriceHistory(sdf.format(new Date(1)), 200, 300, 600, 7);
        List<PriceHistory> priceHistories = List.of(priceHistory1, priceHistory2);

        ItemDaySales expected1 = new ItemDaySales(sdf.parse(sdf.format(new Date(0))), 100, 200, 500, 6, 150);
        ItemDaySales expected2 = new ItemDaySales(sdf.parse(sdf.format(new Date(1))), 200, 300, 600, 7, 260);
        List<ItemDaySales> expected = List.of(expected1, expected2);

        List<ItemDaySales> result = commonQueryItemsSaleStatsMapper.mapAllItemDaySales(priceHistories);

        assertTrue(result.containsAll(expected) && expected.containsAll(result));

        verify(commonQueryItemsSaleStatsMapper).mapItemDaySales(priceHistory1);
        verify(commonQueryItemsSaleStatsMapper).mapItemDaySales(priceHistory2);
    }

    @Test
    public void mapItemDaySales_should_get_date_and_calculate_averageNoEdgesPrice() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getItemSaleStatsDateFormat());

        PriceHistory priceHistory = new PriceHistory(sdf.format(new Date(10)), 100, 200, 500, 6);

        ItemDaySales expected = new ItemDaySales(sdf.parse(sdf.format(new Date(10))), 100, 200, 500, 6, 150);

        ItemDaySales result = commonQueryItemsSaleStatsMapper.mapItemDaySales(priceHistory);

        assertEquals(expected, result);
    }

    @Test
    public void mapItemDaySales_should_get_date_and_dont_calculate_averageNoEdgesPrice_if_small_item_count() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getItemSaleStatsDateFormat());

        PriceHistory priceHistory = new PriceHistory(sdf.format(new Date(100000000)), 100, 200, 500, 2);

        ItemDaySales expected = new ItemDaySales(sdf.parse(sdf.format(new Date(100000000))), 100, 200, 500, 2, 0);

        ItemDaySales result = commonQueryItemsSaleStatsMapper.mapItemDaySales(priceHistory);

        assertEquals(expected, result);

        PriceHistory priceHistory1 = new PriceHistory(sdf.format(new Date(1000000000)), 100, 200, 500, 1);

        ItemDaySales expected1 = new ItemDaySales(sdf.parse(sdf.format(new Date(1000000000))), 100, 200, 500, 1, 0);

        ItemDaySales result1 = commonQueryItemsSaleStatsMapper.mapItemDaySales(priceHistory1);

        assertEquals(expected1, result1);

        PriceHistory priceHistory2 = new PriceHistory(sdf.format(new Date(1000)), 100, 200, 500, -222);

        ItemDaySales expected2 = new ItemDaySales(sdf.parse(sdf.format(new Date(1000))), 100, 200, 500, -222, 0);

        ItemDaySales result2 = commonQueryItemsSaleStatsMapper.mapItemDaySales(priceHistory2);

        assertEquals(expected2, result2);
    }
}