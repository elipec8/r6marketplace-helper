package github.ricemonger.utils.DTOs.items;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ItemForFastEquals extends Item {

    public ItemForFastEquals(Item item) {
        super(
                item.getItemId(),
                item.getAssetUrl(),
                item.getName(),
                item.getTags(),
                item.getRarity(),
                item.getType(),
                item.getMaxBuyPrice(),
                item.getBuyOrdersCount(),
                item.getMinSellPrice(),
                item.getSellOrdersCount(),
                item.getLastSoldAt(),
                item.getLastSoldPrice(),
                item.getMonthAveragePrice(),
                item.getMonthMedianPrice(),
                item.getMonthMaxPrice(),
                item.getMonthMinPrice(),
                item.getMonthSalesPerDay(),
                item.getDayAveragePrice(),
                item.getDayMedianPrice(),
                item.getDayMaxPrice(),
                item.getDayMinPrice(),
                item.getDaySales(),
                item.getPriceToSellIn1Hour(),
                item.getPriceToSellIn6Hours(),
                item.getPriceToSellIn24Hours(),
                item.getPriceToSellIn168Hours(),
                item.getPriceToBuyIn1Hour(),
                item.getPriceToBuyIn6Hours(),
                item.getPriceToBuyIn24Hours(),
                item.getPriceToBuyIn168Hours()
        );
    }

    public ItemForFastEquals(ItemMainFieldsI MainFields) {
        super(MainFields);
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
        return item.getItemId().equals(this.getItemId());
    }

    public int hashCode() {
        return this.getItemId().hashCode();
    }
}
