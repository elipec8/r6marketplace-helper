package github.ricemonger.marketplace.graphQl.mappers;

import github.ricemonger.marketplace.graphQl.DTOs.common_query_items_sale_stats.marketableItems.Node;
import github.ricemonger.marketplace.graphQl.DTOs.common_query_items_sale_stats.marketableItems.node.Item;
import github.ricemonger.marketplace.graphQl.DTOs.common_query_items_sale_stats.marketableItems.node.PriceHistory;
import github.ricemonger.marketplace.services.CommonValuesService;
import github.ricemonger.utils.DTOs.items.GroupedItemDaySalesUbiStats;
import github.ricemonger.utils.DTOs.items.ItemDaySalesUbiStats;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getItemSaleStatsDateFormat());

        Node node1 = new Node();
        node1.setItem(new Item("1"));
        node1.setPriceHistory(List.of());
        Node node2 = new Node();
        node2.setItem(new Item("2"));
        node2.setPriceHistory(List.of(new PriceHistory(dtf.format(LocalDateTime.of(1970, 1, 1, 0, 0, 0)), 100, 200, 500, 6)));
        List<Node> nodes = List.of(node1, node2);

        GroupedItemDaySalesUbiStats expected1 = new GroupedItemDaySalesUbiStats("1", List.of());
        GroupedItemDaySalesUbiStats expected2 = new GroupedItemDaySalesUbiStats("2",
                List.of(new ItemDaySalesUbiStats("2", LocalDate.of(1970, 1, 1), 100, 200, 500, 6)));
        List<GroupedItemDaySalesUbiStats> expected = List.of(expected1, expected2);

        List<GroupedItemDaySalesUbiStats> result = commonQueryItemsSaleStatsMapper.mapAllItemsSaleStats(nodes);

        assertTrue(result.containsAll(expected) && expected.containsAll(result));

        verify(commonQueryItemsSaleStatsMapper).mapItemSaleStats(node1);
        verify(commonQueryItemsSaleStatsMapper).mapItemSaleStats(node2);
    }

    @Test
    public void mapItemSaleStats_should_set_itemId_and_call_mapAllItemDaySales() throws ParseException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getItemSaleStatsDateFormat());

        Node node = new Node();
        node.setItem(new Item("1"));
        node.setPriceHistory(List.of(new PriceHistory(dtf.format(LocalDateTime.of(1970, 1, 1, 0, 0, 0)), 100, 200, 500, 6)));

        GroupedItemDaySalesUbiStats expected = new GroupedItemDaySalesUbiStats("1", List.of(new ItemDaySalesUbiStats("1", LocalDate.of(1970, 1, 1), 100, 200,
                500, 6)));

        GroupedItemDaySalesUbiStats result = commonQueryItemsSaleStatsMapper.mapItemSaleStats(node);

        assertEquals(expected, result);

        verify(commonQueryItemsSaleStatsMapper).mapAllItemDaySales("1", node.getPriceHistory());
    }

    @Test
    public void mapAllItemDaySales_should_call_mapItemDaySales_for_each_priceHistory() throws ParseException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getItemSaleStatsDateFormat());

        PriceHistory priceHistory1 = new PriceHistory(dtf.format(LocalDateTime.of(1970, 1, 1, 0, 0, 0)), 100, 200, 500, 6);
        PriceHistory priceHistory2 = new PriceHistory(dtf.format(LocalDateTime.of(1970, 1, 1, 0, 0, 0).plusDays(1)), 200, 300, 600, 7);
        List<PriceHistory> priceHistories = List.of(priceHistory1, priceHistory2);

        ItemDaySalesUbiStats expected1 = new ItemDaySalesUbiStats("itemId", LocalDate.of(1970, 1, 1), 100, 200, 500, 6);
        ItemDaySalesUbiStats expected2 = new ItemDaySalesUbiStats("itemId", LocalDate.of(1970, 1, 1).plusDays(1), 200, 300, 600, 7);
        List<ItemDaySalesUbiStats> expected = List.of(expected1, expected2);

        List<ItemDaySalesUbiStats> result = commonQueryItemsSaleStatsMapper.mapAllItemDaySales("itemId", priceHistories);

        assertTrue(result.containsAll(expected) && expected.containsAll(result));

        verify(commonQueryItemsSaleStatsMapper).mapItemDaySales("itemId", priceHistory1);
        verify(commonQueryItemsSaleStatsMapper).mapItemDaySales("itemId", priceHistory2);
    }

    @Test
    public void mapItemDaySales_should_be_mapped_from_priceHistory() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getItemSaleStatsDateFormat());

        PriceHistory priceHistory1 = new PriceHistory(dtf.format(LocalDateTime.of(1970, 1, 1, 0, 0, 0)), 100, 200, 500, 6);
        ItemDaySalesUbiStats expected1 = new ItemDaySalesUbiStats("itemId", LocalDate.of(1970, 1, 1), 100, 200, 500, 6);
        ItemDaySalesUbiStats result1 = commonQueryItemsSaleStatsMapper.mapItemDaySales("itemId", priceHistory1);
        assertEquals(expected1, result1);

        PriceHistory priceHistory2 = new PriceHistory(dtf.format(LocalDateTime.of(1970, 1, 1, 0, 0, 0).plusDays(1)), 200, 300, 600, 7);
        ItemDaySalesUbiStats expected2 = new ItemDaySalesUbiStats("itemId", LocalDate.of(1970, 1, 1).plusDays(1), 200, 300, 600, 7);
        ItemDaySalesUbiStats result2 = commonQueryItemsSaleStatsMapper.mapItemDaySales("itemId", priceHistory2);
        assertEquals(expected2, result2);
    }
}