package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.ItemDatabaseService;
import github.ricemonger.utils.dtos.Item;
import github.ricemonger.utils.dtos.ItemSale;
import github.ricemonger.utils.dtos.ItemSaleHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class ItemService {

    private final ItemDatabaseService itemDatabaseService;

    private final ProfitAndPriorityCalculator profitAndPriorityCalculator;

    public void saveAll(Collection<Item> items) {
        itemDatabaseService.saveAllItemsAndItemSales(items);
    }

    public void calculateItemsSaleHistoryStats() {
        List<ItemSaleHistory> histories = new ArrayList<>();
        Collection<Item> items = itemDatabaseService.findAllItems();
        Collection<ItemSale> sales = itemDatabaseService.findAllItemSales();

        for (Item item : items) {

            ItemSaleHistory history = new ItemSaleHistory();
            history.setItemId(item.getItemId());

            List<ItemSale> itemSales = sales.stream().filter(sale -> sale.getItemId().equals(item.getItemId())).toList();

            if (itemSales.isEmpty()) {
                histories.add(history);
                continue;
            }

            List<ItemSale> monthSales =
                    itemSales.stream().filter(sale -> sale.getSoldAt().after(new Date(System.currentTimeMillis() - 30 * 24 * 60 * 60 * 1000L))).toList();

            int monthAveragePrice = 0;
            int monthMedianPrice = 0;
            int monthMaxPrice = 0;
            int monthMinPrice = 0;
            int monthSalesPerDay = 0;

            if (!monthSales.isEmpty()) {
                monthAveragePrice = (int) monthSales.stream().mapToInt(ItemSale::getPrice).average().orElse(0);
                monthMedianPrice = monthSales.stream().mapToInt(ItemSale::getPrice).sorted().boxed().toList().get(monthSales.size() / 2);
                monthMaxPrice = monthSales.stream().mapToInt(ItemSale::getPrice).max().orElse(0);
                monthMinPrice = monthSales.stream().mapToInt(ItemSale::getPrice).min().orElse(0);
                monthSalesPerDay = monthSales.size() / 30;
            }

            List<ItemSale> daySales =
                    monthSales.stream().filter(sale -> sale.getSoldAt().after(new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000L))).toList();

            int dayAveragePrice = 0;
            int dayMaxPrice = 0;
            int dayMinPrice = 0;
            int dayMedianPrice = 0;
            int daySalesCount = 0;

            if (!daySales.isEmpty()) {
                dayAveragePrice = (int) daySales.stream().mapToInt(ItemSale::getPrice).average().orElse(0);
                dayMaxPrice = daySales.stream().mapToInt(ItemSale::getPrice).max().orElse(0);
                dayMinPrice = daySales.stream().mapToInt(ItemSale::getPrice).min().orElse(0);
                dayMedianPrice = daySales.stream().mapToInt(ItemSale::getPrice).sorted().boxed().toList().get(daySales.size() / 2);
                daySalesCount = daySales.size();
            }

            history.setMonthAveragePrice(monthAveragePrice);
            history.setMonthMedianPrice(monthMedianPrice);
            history.setMonthMaxPrice(monthMaxPrice);
            history.setMonthMinPrice(monthMinPrice);
            history.setMonthSalesPerDay(monthSalesPerDay);

            history.setDayAveragePrice(dayAveragePrice);
            history.setDayMedianPrice(dayMedianPrice);
            history.setDayMaxPrice(dayMaxPrice);
            history.setDayMinPrice(dayMinPrice);
            history.setDaySales(daySalesCount);

            histories.add(history);
        }
        itemDatabaseService.saveAllItemSaleHistoryStats(histories);
    }

    public Collection<Item> getAllSpeculativeItemsByExpectedProfit(int minProfit, int minProfitPercents, int minBuyPrice, int maxBuyPrice) {
        return itemDatabaseService.findAllItems().stream()
                .filter(item -> profitAndPriorityCalculator.calculateItemProfit(item) > minProfit)
                .filter(item -> profitAndPriorityCalculator.calculateItemProfitPercents(item) > minProfitPercents)
                .filter(item -> profitAndPriorityCalculator.calculateNextBuyPrice(item) >= minBuyPrice)
                .filter(item -> profitAndPriorityCalculator.calculateNextBuyPrice(item) <= maxBuyPrice)
                .sorted((o1, o2) -> (profitAndPriorityCalculator.calculateItemProfit(o2) * profitAndPriorityCalculator.calculateItemProfitPercents(o2) * o2.getSellOrdersCount()) - (profitAndPriorityCalculator.calculateItemProfit(o1) * profitAndPriorityCalculator.calculateItemProfitPercents(o1) * o1.getSellOrdersCount()))
                .toList();
    }
}
