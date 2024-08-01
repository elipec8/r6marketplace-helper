package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.marketplace.databases.postgres.entities.item.TagEntity;
import github.ricemonger.utils.dtos.ItemFilter;
import github.ricemonger.utils.enums.FilterType;
import github.ricemonger.utils.enums.IsOwnedFilter;
import github.ricemonger.utils.enums.ItemType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

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

    private FilterType filterType;

    private IsOwnedFilter isOwned;

    private String itemNamePatterns;

    private String itemTypes;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "item_filter_tags",
            joinColumns = {@JoinColumn(name = "itemFilterUserId", referencedColumnName = "userId"),
                    @JoinColumn(name = "itemFilterName", referencedColumnName = "name")},
            inverseJoinColumns = @JoinColumn(name = "tagName", referencedColumnName = "name"))
    private Set<TagEntity> tags = new HashSet<>();

    private Integer minPrice;
    private Integer maxPrice;

    private Integer minLastSoldPrice;
    private Integer maxLastSoldPrice;

    public ItemFilterEntity(UserEntity user, ItemFilter filter) {
        this(filter);
        this.user = user;
    }

    public ItemFilterEntity(ItemFilter filter) {
        this.name = filter.getName();
        this.filterType = filter.getFilterType();
        this.isOwned = filter.getIsOwned();

        if (filter.getItemNamePatterns() == null) {
            this.itemNamePatterns = "";
        } else {
            this.itemNamePatterns = String.join(",", filter.getItemNamePatterns());
        }

        if (filter.getItemTypes() == null) {
            this.itemTypes = "";
        } else {
            this.itemTypes = filter.getItemTypes().stream().map(Enum::name).collect(Collectors.joining(","));
        }

        if (filter.getTags() == null) {
            this.tags = new HashSet<>();
        } else {
            this.tags = filter.getTags().stream().map(TagEntity::new).collect(Collectors.toSet());
        }

        this.minPrice = filter.getMinPrice();
        this.maxPrice = filter.getMaxPrice();
        this.minLastSoldPrice = filter.getMinLastSoldPrice();
        this.maxLastSoldPrice = filter.getMaxLastSoldPrice();
    }

    public ItemFilter toItemFilter() {
        ItemFilter filter = new ItemFilter();
        filter.setName(this.name);
        filter.setFilterType(this.filterType);
        filter.setIsOwned(this.isOwned);

        List<String> namePatterns = new ArrayList<>();
        if (this.itemNamePatterns != null) {
            namePatterns = Arrays.stream(this.itemNamePatterns.split("[,|]")).map(String::trim).toList();
        }
        filter.setItemNamePatterns(namePatterns);

        List<ItemType> itemTypes = new ArrayList<>();
        if (this.itemTypes != null && !this.itemTypes.isEmpty()) {
            String[] split = this.itemTypes.split("[,|]");
            for (String s : split) {
                try {
                    itemTypes.add(ItemType.valueOf(s));
                } catch (IllegalArgumentException e) {
                    log.error("Unknown item type: " + s);
                }
            }
        }
        filter.setItemTypes(itemTypes);

        filter.setTags(this.tags.stream().map(TagEntity::toTag).toList());
        filter.setMinPrice(this.minPrice);
        filter.setMaxPrice(this.maxPrice);
        filter.setMinLastSoldPrice(this.minLastSoldPrice);
        filter.setMaxLastSoldPrice(this.maxLastSoldPrice);
        return filter;
    }
}
