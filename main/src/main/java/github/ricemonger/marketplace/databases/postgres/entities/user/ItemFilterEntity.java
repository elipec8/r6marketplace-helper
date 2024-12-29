package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.marketplace.databases.postgres.entities.item.TagEntity;
import github.ricemonger.utils.enums.FilterType;
import github.ricemonger.utils.enums.IsOwnedFilter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Table(name = "item_filter")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ItemFilterEntityId.class)
public class ItemFilterEntity {
    @MapsId
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @Id
    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "filter_type")
    private FilterType filterType;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "is_owned")
    private IsOwnedFilter isOwned;

    @Column(columnDefinition = "TEXT", name = "item_name_patterns")
    private String itemNamePatterns;

    @Column(columnDefinition = "TEXT", name = "item_types")
    private String itemTypes;

    @ManyToMany
    @JoinTable(name = "item_filter_tags",
            joinColumns = {@JoinColumn(name = "item_filter_user_id", referencedColumnName = "user_id"),
                    @JoinColumn(name = "item_filter_name", referencedColumnName = "name")},
            inverseJoinColumns = @JoinColumn(name = "tag_name", referencedColumnName = "name"))
    private Set<TagEntity> tags = new HashSet<>();

    @Column(name = "min_sell_price")
    private Integer minSellPrice;
    @Column(name = "max_buy_price")
    private Integer maxBuyPrice;

    @Override
    public int hashCode() {
        return Objects.hash(user, name);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ItemFilterEntity itemFilterEntity)) {
            return false;
        }
        return Objects.equals(this.user, itemFilterEntity.user) &&
               Objects.equals(this.name, itemFilterEntity.name);
    }
}
