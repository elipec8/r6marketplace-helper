package github.ricemonger.utilspostgresschema.full_entities.user;

import github.ricemonger.utils.enums.FilterType;
import github.ricemonger.utils.enums.IsOwnedFilter;
import github.ricemonger.utilspostgresschema.full_entities.item.TagEntity;
import github.ricemonger.utilspostgresschema.id_entities.item.IdTagEntity;
import github.ricemonger.utilspostgresschema.id_entities.user.IdItemFilterEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemFilterEntity extends IdItemFilterEntity {
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "item_filter_tags",
            joinColumns = {@JoinColumn(name = "item_filter_user_id", referencedColumnName = "user_id"),
                    @JoinColumn(name = "item_filter_name", referencedColumnName = "name")},
            inverseJoinColumns = @JoinColumn(name = "tag_name", referencedColumnName = "name"))
    private Set<TagEntity> tags = new HashSet<>();

    @Column(name = "min_sell_price")
    private Integer minSellPrice;
    @Column(name = "max_buy_price")
    private Integer maxBuyPrice;
}
