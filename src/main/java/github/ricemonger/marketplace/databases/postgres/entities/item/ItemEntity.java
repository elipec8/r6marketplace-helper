package github.ricemonger.marketplace.databases.postgres.entities.item;

import github.ricemonger.utils.dtos.Item;
import github.ricemonger.utils.dtos.Tag;
import github.ricemonger.utils.enums.ItemType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "item_tags",
            joinColumns = {@JoinColumn(name = "itemId", referencedColumnName = "itemId")},
            inverseJoinColumns = @JoinColumn(name = "tagValue", referencedColumnName = "tag_value"))
    private List<TagEntity> tags;

    private ItemType type;

    private int maxBuyPrice;
    private int buyOrdersCount;

    private int minSellPrice;
    private int sellOrdersCount;

    private Date lastSoldAt;
    private int lastSoldPrice;

    private int limitMinPrice;
    private int limitMaxPrice;

    public ItemEntity(String itemId){
        this.itemId = itemId;
    }

    public ItemEntity(Item item, Set<TagEntity> allTagsEntities) {
        List<TagEntity> tagEntities = new ArrayList<>();
        if (item.getTags() != null && !item.getTags().isEmpty()) {
            for(String tag: item.getTags()){
                TagEntity tagEntity = getTagEntityByValueFromSet(allTagsEntities, tag);
                if(tagEntity != null){
                    tagEntities.add(tagEntity);
                } else {
                    log.error("Tag {} not found in allTagsEntities set", tag);
                }
            }
        }
        this.tags = tagEntities;
        this.itemId = item.getItemId();
        this.assetUrl = item.getAssetUrl();
        this.name = item.getName();
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
        List<String> tags = new ArrayList<>();
        if (this.tags != null && !this.tags.isEmpty()) {
            tags = List.of(this.tags.stream().map(TagEntity::getValue).toArray(String[]::new));
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

    private TagEntity getTagEntityByValueFromSet(Set<TagEntity> tagEntities, String tagValue){
        for(TagEntity tagEntity: tagEntities){
            if(tagEntity.getValue().equals(tagValue)){
                return tagEntity;
            }
        }
        return null;
    }
}
