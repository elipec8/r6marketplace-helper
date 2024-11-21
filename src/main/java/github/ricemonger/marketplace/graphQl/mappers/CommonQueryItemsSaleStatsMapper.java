package github.ricemonger.marketplace.graphQl.mappers;

import github.ricemonger.marketplace.graphQl.DTOs.common_query_items_sale_stats.marketableItems.Node;
import github.ricemonger.marketplace.graphQl.DTOs.common_query_items_sale_stats.marketableItems.node.Item;
import github.ricemonger.marketplace.graphQl.DTOs.common_query_items_sale_stats.marketableItems.node.PriceHistory;
import github.ricemonger.marketplace.services.CommonValuesService;
import github.ricemonger.utils.DTOs.items.ItemDaySalesUbiStats;
import github.ricemonger.utils.DTOs.items.GroupedItemDaySalesUbiStats;
import github.ricemonger.utils.exceptions.server.GraphQlCommonItemsSaleStatsMappingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommonQueryItemsSaleStatsMapper {

    private final CommonValuesService commonValuesService;

    public List<GroupedItemDaySalesUbiStats> mapItemsSaleStats(Collection<Node> nodes) throws GraphQlCommonItemsSaleStatsMappingException {
        return nodes.stream().map(this::mapItemSaleStats).collect(Collectors.toList());
    }

    public GroupedItemDaySalesUbiStats mapItemSaleStats(Node node) throws GraphQlCommonItemsSaleStatsMappingException {
        if (node == null) {
            throw new GraphQlCommonItemsSaleStatsMappingException("Node is null");
        }
        GroupedItemDaySalesUbiStats result = new GroupedItemDaySalesUbiStats();

        Item item = node.getItem();
        if (item == null || item.getItemId() == null) {
            throw new GraphQlCommonItemsSaleStatsMappingException("Item or it's id is null, node-" + node);
        }
        result.setItemId(item.getItemId());

        if (node.getPriceHistory() == null) {
            throw new GraphQlCommonItemsSaleStatsMappingException("PriceHistory is null, node-" + node);
        }
        List<ItemDaySalesUbiStats> itemSaleUbiStats = mapAllItemDaySales(node.getPriceHistory());
        result.setDaySales(itemSaleUbiStats);

        return result;
    }

    public List<ItemDaySalesUbiStats> mapAllItemDaySales(List<PriceHistory> priceHistories) throws GraphQlCommonItemsSaleStatsMappingException {
        return priceHistories.stream().map(this::mapItemDaySales).collect(Collectors.toList());
    }

    public ItemDaySalesUbiStats mapItemDaySales(PriceHistory priceHistory) throws GraphQlCommonItemsSaleStatsMappingException {
        if (priceHistory == null) {
            throw new GraphQlCommonItemsSaleStatsMappingException("PriceHistory is null");
        }
        ItemDaySalesUbiStats result = new ItemDaySalesUbiStats();

        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getItemSaleStatsDateFormat());
            result.setDate(LocalDate.parse(priceHistory.getDate(), dtf));
        } catch (NullPointerException e) {
            throw new GraphQlCommonItemsSaleStatsMappingException("Date is null or invalid, priceHistory-" + priceHistory);
        }

        if (priceHistory.getItemsCount() == null || priceHistory.getLowestPrice() == null || priceHistory.getHighestPrice() == null || priceHistory.getAveragePrice() == null) {
            throw new GraphQlCommonItemsSaleStatsMappingException("One of priceHistory values is null:" + priceHistory);
        }
        result.setItemsCount(priceHistory.getItemsCount());
        result.setLowestPrice(priceHistory.getLowestPrice());
        result.setHighestPrice(priceHistory.getHighestPrice());
        result.setAveragePrice(priceHistory.getAveragePrice());

        return result;
    }
}
