package github.ricemonger.marketplace.databases.postgres.entities.item;

import github.ricemonger.utils.dtos.Item;
import github.ricemonger.utils.enums.ItemType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

@Slf4j
@Entity(name = "item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemEntity {
    @Id
    private String itemId;
    private String assetUrl;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String tags;
    private ItemType type;

    private int maxBuyPrice;
    private int buyOrdersCount;

    private int minSellPrice;
    private int sellOrdersCount;

    private Date lastSoldAt;
    private int lastSoldPrice;

    private int limitMinPrice;
    private int limitMaxPrice;

    public ItemEntity(Item item) {
        StringBuilder tags = new StringBuilder();
        if (item.getTags() != null) {
            for (String tag : item.getTags()) {
                tags.append(tag).append(",");
            }
            try {
                tags.deleteCharAt(tags.length() - 1);
            } catch (StringIndexOutOfBoundsException e) {
                log.error("Tags list is empty");
            }
        }
        this.itemId = item.getItemId();
        this.assetUrl = item.getAssetUrl();
        this.name = item.getName();
        this.tags = tags.toString();
        this.type = item.getType();
        this.maxBuyPrice = item.getMaxBuyPrice();
        this.buyOrdersCount = item.getBuyOrdersCount();
        this.minSellPrice = item.getMinSellPrice();
        this.sellOrdersCount = item.getSellOrdersCount();
        this.lastSoldAt = item.getLastSoldAt();
        this.lastSoldPrice = item.getLastSoldPrice();
        this.limitMinPrice = item.getLimitMinPrice();
        this.limitMaxPrice = item.getLimitMaxPrice();
    }

    public Item toItem() {
        List<String> tags;
        if (this.getTags() != null) {
            tags = List.of(this.getTags().split(","));
        } else {
            tags = List.of();
        }
        Item item = new Item();
        item.setItemId(this.itemId);
        item.setAssetUrl(this.assetUrl);
        item.setName(this.name);
        item.setTags(tags);
        item.setType(this.type);
        item.setMaxBuyPrice(this.maxBuyPrice);
        item.setBuyOrdersCount(this.buyOrdersCount);
        item.setMinSellPrice(this.minSellPrice);
        item.setSellOrdersCount(this.sellOrdersCount);
        item.setLastSoldAt(this.lastSoldAt);
        item.setLastSoldPrice(this.lastSoldPrice);
        item.setLimitMinPrice(this.limitMinPrice);
        item.setLimitMaxPrice(this.limitMaxPrice);
        return item;
    }
}
