package github.ricemonger.utils.dtos;

import github.ricemonger.utils.enums.ItemType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item implements SoldItemDetails {
    private String itemId;
    private String assetUrl;
    private String name;
    private List<String> tags;
    private ItemType type;

    private int maxBuyPrice;
    private int buyOrdersCount;

    private int minSellPrice;
    private int sellOrdersCount;

    private Date lastSoldAt;
    private int lastSoldPrice;

    private int limitMinPrice;
    private int limitMaxPrice;

    public ItemRarity getItemRarity(String uncommonTag, String rareTag, String epicTag, String legendaryTag) {
        if (tags.contains(uncommonTag)) {
            return ItemRarity.UNCOMMON;
        } else if (tags.contains(rareTag)) {
            return ItemRarity.RARE;
        } else if (tags.contains(epicTag)) {
            return ItemRarity.EPIC;
        } else if (tags.contains(legendaryTag)) {
            return ItemRarity.LEGENDARY;
        } else {
            log.error("Unknown rarity tag for item {}", this);
            return ItemRarity.UNKNOWN;
        }
    }

    public String toString() {
        String itemId = this.itemId;
        String name = this.getName();
        String maxBuyPrice = String.valueOf(this.getMaxBuyPrice());
        String buyOrders = String.valueOf(this.getBuyOrdersCount());
        String minSellPrice = String.valueOf(this.getMinSellPrice());
        String sellOrders = String.valueOf(this.getSellOrdersCount());
        String lastSoldAt;
        if (this.getLastSoldAt() != null) {
            lastSoldAt = this.getLastSoldAt().toString();
        } else {
            lastSoldAt = "null";
        }
        String lastSoldPrice = String.valueOf(this.getLastSoldPrice());
        String pictureUrl = this.getAssetUrl();

        String sb = "Id: " + itemId + "\n" +
                    "Name: " + name + "\n" +
                    "Min sell price: " + minSellPrice + "\n" +
                    "Sell orders: " + sellOrders + "\n" +
                    "Max buy price: " + maxBuyPrice + "\n" +
                    "Buy orders: " + buyOrders + "\n" +
                    "Last sold price: " + lastSoldPrice + "\n" +
                    "Last sold at: " + lastSoldAt + "\n" +
                    "Picture: " + pictureUrl + "\n";

        return sb;
    }

    public String toStringBySettings(ItemShownFieldsSettings shownFieldsSettings) {
        StringBuilder sb = new StringBuilder();

        sb.append("Id: ").append(this.getItemId()).append("\n");

        if (shownFieldsSettings.isItemShowNameFlag()) {
            sb.append("Name: ").append(this.getName()).append("\n");
        }
        if (shownFieldsSettings.isItemShowItemTypeFlag()) {
            sb.append("Type: ").append(this.getType()).append("\n");
        }
        if (shownFieldsSettings.isItemShowMaxBuyPrice()) {
            sb.append("Max buy price: ").append(this.getMaxBuyPrice()).append("\n");
        }
        if (shownFieldsSettings.isItemShowBuyOrdersCountFlag()) {
            sb.append("Buy orders: ").append(this.getBuyOrdersCount()).append("\n");
        }
        if (shownFieldsSettings.isItemShowMinSellPriceFlag()) {
            sb.append("Min sell price: ").append(this.getMinSellPrice()).append("\n");
        }
        if (shownFieldsSettings.isItemsShowSellOrdersCountFlag()) {
            sb.append("Sell orders: ").append(this.getSellOrdersCount()).append("\n");
        }
        if (shownFieldsSettings.isItemShowPictureFlag()) {
            sb.append("Picture: ").append(this.getAssetUrl()).append("\n");
        }
        return sb.toString();
    }
}
