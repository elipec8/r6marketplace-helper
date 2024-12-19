package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.marketplace.databases.postgres.entities.item.TagEntity;
import github.ricemonger.utils.enums.FilterType;
import github.ricemonger.utils.enums.IsOwnedFilter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Entity(name = "item_filter")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ItemFilterEntityId.class)
public class ItemFilterEntity {
    @MapsId
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private UserEntity user;

    @Id
    private String name;

    @Enumerated(EnumType.ORDINAL)
    private FilterType filterType;

    @Enumerated(EnumType.ORDINAL)
    private IsOwnedFilter isOwned;

    @Column(columnDefinition = "TEXT")
    private String itemNamePatterns;

    @Column(columnDefinition = "TEXT")
    private String itemTypes;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "item_filter_tags",
            joinColumns = {@JoinColumn(name = "itemFilterUserId", referencedColumnName = "userId"),
                    @JoinColumn(name = "itemFilterName", referencedColumnName = "name")},
            inverseJoinColumns = @JoinColumn(name = "tagName", referencedColumnName = "name"))
    private Set<TagEntity> tags = new HashSet<>();

    private Integer minSellPrice;
    private Integer maxBuyPrice;

    public Long getUserId() {
        return user.getId();
    }

    public boolean isFullyEqualExceptUser(Object o) {
        if (this == o) return true;
        if (o instanceof ItemFilterEntity entity) {

            boolean tagsAreEqual = this.tags.size() == entity.tags.size() &&
                                   this.tags.stream().allMatch(tag -> entity.tags.stream().anyMatch(tag::isFullyEqual));

            return Objects.equals(getUserId(), entity.getUserId()) &&
                   Objects.equals(name, entity.name) &&
                   filterType == entity.filterType &&
                   isOwned == entity.isOwned &&
                   Objects.equals(itemNamePatterns, entity.itemNamePatterns) &&
                   Objects.equals(itemTypes, entity.itemTypes) &&
                   tagsAreEqual &&
                   Objects.equals(minSellPrice, entity.minSellPrice) &&
                   Objects.equals(maxBuyPrice, entity.maxBuyPrice);
        }
        return false;
    }
}
