package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.utils.enums.TradeManagerTradeType;
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
@IdClass(TradeManagerByItemFiltersEntityId.class)
public class TradeManagerByItemFiltersEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @Id
    private String name;

    private TradeManagerTradeType tradeType;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "trade_manager_by_item_filters_applied_filters",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id"),
                    @JoinColumn(name = "name", referencedColumnName = "name")},
            inverseJoinColumns = @JoinColumn(name = "trade_id", referencedColumnName = "trade_id"))
    private List<ItemFilterEntity> appliedFilters;

    private Integer maxBuyHours;
    private Integer maxSellHours;

    private Integer minProfit;
    private Integer minProfitPercent;

    private Integer priority;
}
