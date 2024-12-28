package github.ricemonger.item_trade_stats_calculator.services.DTOs;

import github.ricemonger.utils.DTOs.common.ItemDaySalesUbiStats;
import github.ricemonger.utils.DTOs.common.ItemSale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDaySalesStatsByItemId {
    private String itemId;
    private LocalDate date;
    private Map<Integer, Integer> priceAndQuantity = new HashMap<>();

    public ItemDaySalesStatsByItemId(String itemId, LocalDate day, Collection<ItemSale> itemSales) {
        this.itemId = itemId;
        this.date = day;
        List<ItemSale> daySales =
                itemSales.stream().filter(itemSale -> day.equals(itemSale.getLastSoldAt().toLocalDate()) && itemId.equals(itemSale.getItemId())).toList();
        HashMap<Integer, Integer> priceAndQuantity = new HashMap<>();
        for (ItemSale itemSale : daySales) {
            priceAndQuantity.put(itemSale.getPrice(), priceAndQuantity.getOrDefault(itemSale.getPrice(), 0) + 1);
        }
        this.priceAndQuantity = priceAndQuantity;
    }

    public ItemDaySalesStatsByItemId(LocalDate day, String itemId, Collection<ItemDaySalesUbiStats> ubiSaleStats) {
        this.itemId = itemId;
        this.date = day;

        ItemDaySalesUbiStats ubiDaySaleStats =
                ubiSaleStats.stream().filter(ubiStats -> ubiStats.getDate().equals(day) && itemId.equals(ubiStats.getItemId())).findFirst().orElse(null);
        HashMap<Integer, Integer> priceAndQuantity = new HashMap<>();

        if (ubiDaySaleStats == null) {
            this.priceAndQuantity = priceAndQuantity;
        } else {

            if (ubiDaySaleStats.getItemsCount() == 1) {
                priceAndQuantity.put(ubiDaySaleStats.getAveragePrice(), 1);
            } else if (ubiDaySaleStats.getItemsCount() == 2) {
                priceAndQuantity.put(ubiDaySaleStats.getLowestPrice(), 1);
                priceAndQuantity.put(ubiDaySaleStats.getHighestPrice(), priceAndQuantity.getOrDefault(ubiDaySaleStats.getHighestPrice(), 0) + 1);
            } else if (ubiDaySaleStats.getItemsCount() > 2) {
                priceAndQuantity.put(ubiDaySaleStats.getLowestPrice(), 1);
                int averageNoEdgesPrice =
                        ((ubiDaySaleStats.getAveragePrice() * ubiDaySaleStats.getItemsCount()) - (ubiDaySaleStats.getLowestPrice() + ubiDaySaleStats.getHighestPrice())) / (ubiDaySaleStats.getItemsCount() - 2);
                if (averageNoEdgesPrice < ubiDaySaleStats.getLowestPrice()) {
                    averageNoEdgesPrice = ubiDaySaleStats.getLowestPrice();
                }
                priceAndQuantity.put(averageNoEdgesPrice, priceAndQuantity.getOrDefault(averageNoEdgesPrice, 0) + ubiDaySaleStats.getItemsCount() - 2);
                priceAndQuantity.put(ubiDaySaleStats.getHighestPrice(), priceAndQuantity.getOrDefault(ubiDaySaleStats.getHighestPrice(), 0) + 1);
            }

            this.priceAndQuantity = priceAndQuantity;
        }
    }

    public int getQuantity() {
        return priceAndQuantity == null || priceAndQuantity.isEmpty() ? 0 : priceAndQuantity.values().stream().mapToInt(Integer::intValue).sum();
    }
}
