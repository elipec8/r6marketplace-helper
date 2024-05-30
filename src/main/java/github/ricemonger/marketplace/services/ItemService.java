package github.ricemonger.marketplace.services;

import github.ricemonger.utils.dtos.Item;
import github.ricemonger.utils.dtos.ItemSale;
import github.ricemonger.utils.dtos.ItemSaleHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepositoryService itemRepositoryService;

    public void saveAll(Collection<Item> items) {
        itemRepositoryService.saveAllItemsAndItemSales(items);
    }

    public void calculateItemsSaleHistoryStats() {
        List<ItemSaleHistory> histories = new ArrayList<>();
        Collection<Item> items = itemRepositoryService.findAllItems();
        Collection<ItemSale> sales = itemRepositoryService.findAllItemSales();

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
        itemRepositoryService.saveAllItemSaleHistoryStats(histories);
    }

    public Collection<Item> getAllSpeculativeItemsByExpectedProfit(int i, int i1, int i2, int i3) {
        return null;
    }

    public Collection<Item> getOwnedSpeculativeItemsByExpectedProfit(Collection<String> ownedItems, int i, int i1, int i2, int i3) {
        return null;
    }
}
