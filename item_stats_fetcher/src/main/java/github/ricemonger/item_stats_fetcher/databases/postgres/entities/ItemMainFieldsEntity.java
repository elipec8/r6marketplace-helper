package github.ricemonger.item_stats_fetcher.databases.postgres.entities;

import github.ricemonger.utils.enums.ItemRarity;
import github.ricemonger.utils.enums.ItemType;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Entity(name = "item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemMainFieldsEntity {
    @Id
    private String itemId;
    private String assetUrl;
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "item_tags",
            joinColumns = {@JoinColumn(name = "itemId", referencedColumnName = "itemId")},
            inverseJoinColumns = @JoinColumn(name = "tagValue", referencedColumnName = "tag_value"))
    private List<TagValueEntity> tags;

    private ItemRarity rarity;

    private ItemType type;

    private Integer maxBuyPrice;
    private Integer buyOrdersCount;

    private Integer minSellPrice;
    private Integer sellOrdersCount;

    private LocalDateTime lastSoldAt;
    private Integer lastSoldPrice;

    public ItemMainFieldsEntity(String itemId) {
        this.itemId = itemId;
    }

    public boolean isEqual(Object o) {
        if (this == o) return true;
        if (o instanceof ItemMainFieldsEntity itemMainFieldsEntity) {
            return Objects.equals(itemId, itemMainFieldsEntity.itemId);
        }
        return false;
    }

    public boolean isFullyEqual(Object o) {
        if (this == o) return true;
        if (o instanceof ItemMainFieldsEntity itemMainFieldsEntity) {

            boolean tagsAreEqual = tags == null && itemMainFieldsEntity.tags == null || (
                    tags != null && itemMainFieldsEntity.tags != null &&
                    this.tags.size() == itemMainFieldsEntity.tags.size() &&
                    this.tags.stream().allMatch(tst -> itemMainFieldsEntity.tags.stream().anyMatch(tst::isEqual)));

            return isEqual(itemMainFieldsEntity) &&
                   Objects.equals(assetUrl, itemMainFieldsEntity.assetUrl) &&
                   Objects.equals(name, itemMainFieldsEntity.name) &&
                   tagsAreEqual &&
                   rarity == itemMainFieldsEntity.rarity &&
                   type == itemMainFieldsEntity.type &&
                   Objects.equals(maxBuyPrice, itemMainFieldsEntity.maxBuyPrice) &&
                   Objects.equals(buyOrdersCount, itemMainFieldsEntity.buyOrdersCount) &&
                   Objects.equals(minSellPrice, itemMainFieldsEntity.minSellPrice) &&
                   Objects.equals(sellOrdersCount, itemMainFieldsEntity.sellOrdersCount) &&
                   Objects.equals(lastSoldAt, itemMainFieldsEntity.lastSoldAt) &&
                   Objects.equals(lastSoldPrice, itemMainFieldsEntity.lastSoldPrice);
        }
        return false;
    }
}
