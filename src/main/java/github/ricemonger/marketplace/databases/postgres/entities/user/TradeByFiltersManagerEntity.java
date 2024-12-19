package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.utils.enums.TradeOperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "trade_manager_by_item_filters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(TradeByFiltersManagerEntityId.class)
public class TradeByFiltersManagerEntity {
    @MapsId
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private UserEntity user;

    @Id
    private String name;

    private boolean enabled;

    @Enumerated(EnumType.ORDINAL)
    private TradeOperationType tradeOperationType;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "trade_manager_by_item_filters_applied_filters",
            joinColumns = {@JoinColumn(name = "userId", referencedColumnName = "userId"),
                    @JoinColumn(name = "name", referencedColumnName = "name")},
            inverseJoinColumns = @JoinColumn(name = "itemFilterName", referencedColumnName = "name"))
    private List<ItemFilterEntity> appliedFilters;

    private Integer minDifferenceFromMedianPrice;
    private Integer minDifferenceFromMedianPricePercent;

    private Integer priorityMultiplier;
}
