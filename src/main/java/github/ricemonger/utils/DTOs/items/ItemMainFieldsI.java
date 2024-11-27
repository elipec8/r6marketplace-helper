package github.ricemonger.utils.DTOs.items;

import github.ricemonger.utils.DTOs.ItemShownFieldsSettings;
import github.ricemonger.utils.enums.ItemRarity;
import github.ricemonger.utils.enums.ItemType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

public interface ItemMainFieldsI extends SoldItemDetails {
    Logger log = LoggerFactory.getLogger(ItemMainFieldsI.class);

    String getItemId();

    void setItemId(String itemId);

    String getAssetUrl();

    void setAssetUrl(String assetUrl);

    String getName();

    void setName(String name);

    List<String> getTags();

    void setTags(List<String> tags);

    ItemRarity getRarity();

    void setRarity(ItemRarity rarity);

    ItemType getType();

    void setType(ItemType type);

    Integer getMaxBuyPrice();

    void setMaxBuyPrice(Integer maxBuyPrice);

    Integer getBuyOrdersCount();

    void setBuyOrdersCount(Integer buyOrdersCount);

    Integer getMinSellPrice();

    void setMinSellPrice(Integer minSellPrice);

    Integer getSellOrdersCount();

    void setSellOrdersCount(Integer sellOrdersCount);

    LocalDateTime getLastSoldAt();

    void setLastSoldAt(LocalDateTime lastSoldAt);

    Integer getLastSoldPrice();

    void setLastSoldPrice(Integer lastSoldPrice);

    default void setMainFields(ItemMainFieldsI mainFields) {
        this.setItemId(mainFields.getItemId());
        this.setAssetUrl(mainFields.getAssetUrl());
        this.setName(mainFields.getName());
        this.setTags(mainFields.getTags());
        this.setRarity(mainFields.getRarity());
        this.setType(mainFields.getType());
        this.setMaxBuyPrice(mainFields.getMaxBuyPrice());
        this.setBuyOrdersCount(mainFields.getBuyOrdersCount());
        this.setMinSellPrice(mainFields.getMinSellPrice());
        this.setSellOrdersCount(mainFields.getSellOrdersCount());
        this.setLastSoldAt(mainFields.getLastSoldAt());
        this.setLastSoldPrice(mainFields.getLastSoldPrice());
    }

    default void setRarityByTags(String uncommonTag, String rareTag, String epicTag, String legendaryTag) {
        List<String> tags = this.getTags();
        if (tags == null) {
            log.error("Tags are null for item {}", this);
            this.setRarity(ItemRarity.UNKNOWN);
        } else if (tags.contains(legendaryTag)) {
            this.setRarity(ItemRarity.LEGENDARY);
        } else if (tags.contains(epicTag)) {
            this.setRarity(ItemRarity.EPIC);
        } else if (tags.contains(rareTag)) {
            this.setRarity(ItemRarity.RARE);
        } else if (tags.contains(uncommonTag)) {
            this.setRarity(ItemRarity.UNCOMMON);
        } else {
            log.error("Unknown rarity tag for item {}", this);
            this.setRarity(ItemRarity.UNKNOWN);
        }
    }

    default String toStringBySettings(ItemShownFieldsSettings shownFieldsSettings) {
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
