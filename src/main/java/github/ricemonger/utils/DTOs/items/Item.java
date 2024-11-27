package github.ricemonger.utils.DTOs.items;

import github.ricemonger.utils.enums.ItemRarity;
import github.ricemonger.utils.enums.ItemType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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

    private Integer priceToSellIn1Hour;
    private Integer priceToSellIn6Hours;
    private Integer priceToSellIn24Hours;
    private Integer priceToSellIn168Hours;

    private Integer priceToBuyIn1Hour;
    private Integer priceToBuyIn6Hours;
    private Integer priceToBuyIn24Hours;
    private Integer priceToBuyIn168Hours;

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

    public Item(ItemForFastEquals itemForFastEquals){
        setMainFields(itemForFastEquals);
        setHistoryFields(itemForFastEquals);
    }
}
