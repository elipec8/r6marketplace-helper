package github.ricemonger.utils.DTOs.items;

import github.ricemonger.utils.enums.ItemRarity;
import github.ricemonger.utils.enums.ItemType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item implements ItemMainFieldsI, ItemHistoryFieldsI {
    private String itemId;
    private String assetUrl;
    private String name;
    private List<String> tags;

    private ItemRarity rarity;

    private ItemType type;

    private Integer maxBuyPrice;
    private Integer buyOrdersCount;

    private Integer minSellPrice;
    private Integer sellOrdersCount;

    private LocalDateTime lastSoldAt;
    private Integer lastSoldPrice;

    //history fields

    private Integer monthAveragePrice;
    private Integer monthMedianPrice;
    private Integer monthMaxPrice;
    private Integer monthMinPrice;
    private Integer monthSalesPerDay;

    private Integer dayAveragePrice;
    private Integer dayMedianPrice;
    private Integer dayMaxPrice;
    private Integer dayMinPrice;
    private Integer daySales;

    private Long priorityToSellByMaxBuyPrice; //updated with every item stats update, not recalculation
    private Long priorityToSellByNextFancySellPrice; //updated with every item stats update, not recalculation

    private Long priorityToBuyByMinSellPrice; //updated with every item stats update, not recalculation

    private Long priorityToBuyIn1Hour;
    private Long priorityToBuyIn6Hours;
    private Long priorityToBuyIn24Hours;
    private Long priorityToBuyIn168Hours;
    private Long priorityToBuyIn720Hours;

    private Integer priceToBuyIn1Hour;
    private Integer priceToBuyIn6Hours;
    private Integer priceToBuyIn24Hours;
    private Integer priceToBuyIn168Hours;
    private Integer priceToBuyIn720Hours;

    public Item(String itemId) {
        this.itemId = itemId;
    }

    public Item(Item item) {
        this.itemId = item.getItemId();
        this.assetUrl = item.getAssetUrl();
        this.name = item.getName();
        if (item.getTags() == null) {
            this.tags = null;
        } else {
            this.tags = item.getTags().subList(0, item.getTags().size());
        }
        this.rarity = item.getRarity();
        this.type = item.getType();
        this.maxBuyPrice = item.getMaxBuyPrice();
        this.buyOrdersCount = item.getBuyOrdersCount();
        this.minSellPrice = item.getMinSellPrice();
        this.sellOrdersCount = item.getSellOrdersCount();
        this.lastSoldAt = item.getLastSoldAt();
        this.lastSoldPrice = item.getLastSoldPrice();
        this.monthAveragePrice = item.getMonthAveragePrice();
        this.monthMedianPrice = item.getMonthMedianPrice();
        this.monthMaxPrice = item.getMonthMaxPrice();
        this.monthMinPrice = item.getMonthMinPrice();
        this.monthSalesPerDay = item.getMonthSalesPerDay();
        this.dayAveragePrice = item.getDayAveragePrice();
        this.dayMedianPrice = item.getDayMedianPrice();
        this.dayMaxPrice = item.getDayMaxPrice();
        this.dayMinPrice = item.getDayMinPrice();
        this.daySales = item.getDaySales();
        this.priorityToSellByMaxBuyPrice = item.getPriorityToSellByMaxBuyPrice();
        this.priorityToSellByNextFancySellPrice = item.getPriorityToSellByNextFancySellPrice();
        this.priorityToBuyByMinSellPrice = item.getPriorityToBuyByMinSellPrice();
        this.priorityToBuyIn1Hour = item.getPriorityToBuyIn1Hour();
        this.priorityToBuyIn6Hours = item.getPriorityToBuyIn6Hours();
        this.priorityToBuyIn24Hours = item.getPriorityToBuyIn24Hours();
        this.priorityToBuyIn168Hours = item.getPriorityToBuyIn168Hours();
        this.priorityToBuyIn720Hours = item.getPriorityToBuyIn720Hours();
        this.priceToBuyIn1Hour = item.getPriceToBuyIn1Hour();
        this.priceToBuyIn6Hours = item.getPriceToBuyIn6Hours();
        this.priceToBuyIn24Hours = item.getPriceToBuyIn24Hours();
        this.priceToBuyIn168Hours = item.getPriceToBuyIn168Hours();
        this.priceToBuyIn720Hours = item.getPriceToBuyIn720Hours();
    }

    public Item(ItemMainFieldsI mainFields) {
        setMainFields(mainFields);
    }

    public Item(ItemHistoryFieldsI historyFields) {
        setHistoryFields(historyFields);
    }

    public Item(ItemMainFieldsI mainFields, ItemHistoryFieldsI historyFields) {
        setMainFields(mainFields);
        setHistoryFields(historyFields);
    }

    public void updateCurrentPricesPriorities(Long priorityToSellByMaxBuyPrice,
                                              Long priorityToSellByNextFancySellPrice,
                                              Long priorityToBuyByMinSellPrice) {
        this.priorityToSellByMaxBuyPrice = priorityToSellByMaxBuyPrice;
        this.priorityToSellByNextFancySellPrice = priorityToSellByNextFancySellPrice;
        this.priorityToBuyByMinSellPrice = priorityToBuyByMinSellPrice;
    }

    public boolean isFullyEqualTo(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Item item)) {
            return false;
        }
        return itemMainFieldsAreEqual(item) && itemHistoryFieldsAreEqual(item);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Item item)) {
            return false;
        }
        if (item.getItemId() == null || this.getItemId() == null) {
            return false;
        }
        return Objects.equals(item.getItemId(), this.getItemId());
    }

    public int hashCode() {
        return Objects.hash(itemId);
    }
}
