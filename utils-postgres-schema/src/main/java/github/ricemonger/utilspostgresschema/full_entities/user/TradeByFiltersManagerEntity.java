package github.ricemonger.utilspostgresschema.full_entities.user;

import github.ricemonger.utils.enums.TradeOperationType;
import github.ricemonger.utilspostgresschema.id_entities.user.IdItemFilterEntity;
import github.ricemonger.utilspostgresschema.id_entities.user.IdTradeByFiltersManagerEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TradeByFiltersManagerEntity extends IdTradeByFiltersManagerEntity {
    @Column(name = "enabled")
    private Boolean enabled;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "trade_operation_type")
    private TradeOperationType tradeOperationType;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "trade_manager_by_item_filters_applied_filters",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id"),
                    @JoinColumn(name = "name", referencedColumnName = "name")},
            inverseJoinColumns = @JoinColumn(name = "item_filter_name", referencedColumnName = "name"))
    private List<ItemFilterEntity> appliedFilters;

    @Column(name = "min_difference_from_median_price")
    private Integer minDifferenceFromMedianPrice;
    @Column(name = "min_difference_from_median_price_percent")
    private Integer minDifferenceFromMedianPricePercent;

    @Column(name = "priority_multiplier")
    private Integer priorityMultiplier;
}
