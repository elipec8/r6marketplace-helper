package github.ricemonger.marketplace.graphQl.mappers;

import github.ricemonger.marketplace.graphQl.dtos.common_query_items_sale_stats.marketableItems.Node;
import github.ricemonger.marketplace.graphQl.dtos.common_query_items_sale_stats.marketableItems.node.Item;
import github.ricemonger.marketplace.graphQl.dtos.common_query_items_sale_stats.marketableItems.node.PriceHistory;
import github.ricemonger.marketplace.services.CommonValuesService;
import github.ricemonger.utils.dtos.ItemDaySales;
import github.ricemonger.utils.dtos.ItemSaleUbiStats;
import github.ricemonger.utils.exceptions.server.GraphQlCommonItemsSaleStatsMappingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommonQueryItemsSaleStatsMapper {

    private final CommonValuesService commonValuesService;

    public List<ItemSaleUbiStats> mapItemsSaleStats(Collection<Node> nodes) throws GraphQlCommonItemsSaleStatsMappingException {
        return nodes.stream().map(this::mapItemSaleStats).collect(Collectors.toList());
    }

    public ItemSaleUbiStats mapItemSaleStats(Node node) throws GraphQlCommonItemsSaleStatsMappingException {
        if (node == null) {
            throw new GraphQlCommonItemsSaleStatsMappingException("Node is null");
        }
        ItemSaleUbiStats result = new ItemSaleUbiStats();

        Item item = node.getItem();
        if (item == null || item.getItemId() == null) {
            throw new GraphQlCommonItemsSaleStatsMappingException("Item or it's id is null, node-" + node);
        }
        result.setItemId(item.getItemId());

        if(node.getPriceHistory() == null) {
            throw new GraphQlCommonItemsSaleStatsMappingException("PriceHistory is null, node-" + node);
        }
        List<ItemDaySales> itemDaySales = mapAllItemDaySales(node.getPriceHistory());
        result.setLast30DaysSales(itemDaySales);

        return result;
    }

    public List<ItemDaySales> mapAllItemDaySales(List<PriceHistory> priceHistories) throws GraphQlCommonItemsSaleStatsMappingException {
        return priceHistories.stream().map(this::mapItemDaySales).collect(Collectors.toList());
    }

    public ItemDaySales mapItemDaySales(PriceHistory priceHistory) throws GraphQlCommonItemsSaleStatsMappingException {
        if (priceHistory == null) {
            throw new GraphQlCommonItemsSaleStatsMappingException("PriceHistory is null");
        }
        ItemDaySales result = new ItemDaySales();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getItemSaleStatsDateFormat());
            result.setDate(sdf.parse(priceHistory.getDate()));
        } catch (NullPointerException | ParseException e) {
            throw new GraphQlCommonItemsSaleStatsMappingException("Date is null or invalid, priceHistory-" + priceHistory);
        }

        if (priceHistory.getItemsCount() == null || priceHistory.getLowestPrice() == null || priceHistory.getHighestPrice() == null || priceHistory.getAveragePrice() == null) {
            throw new GraphQlCommonItemsSaleStatsMappingException("One of priceHistory values is null:" + priceHistory);
        }
        result.setItemsCount(priceHistory.getItemsCount());
        result.setLowestPrice(priceHistory.getLowestPrice());
        result.setHighestPrice(priceHistory.getHighestPrice());
        result.setAveragePrice(priceHistory.getAveragePrice());

        if (priceHistory.getItemsCount() < 3) {
            result.setAverageNoEdgesPrice(0);
        } else {
            result.setAverageNoEdgesPrice((priceHistory.getAveragePrice() * priceHistory.getItemsCount() - priceHistory.getLowestPrice() - priceHistory.getHighestPrice()) / (priceHistory.getItemsCount() - 2));
        }

        return result;
    }
}
