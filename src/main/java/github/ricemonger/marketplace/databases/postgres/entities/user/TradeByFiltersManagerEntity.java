package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.utils.DTOs.TradeByFiltersManager;
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

    private Integer minBuySellProfit;
    private Integer minProfitPercent;

    private Integer priority;

    public TradeByFiltersManagerEntity(UserEntity user, TradeByFiltersManager tradeManager) {
        this.user = user;
        this.enabled = tradeManager.isEnabled();
        this.name = tradeManager.getName();
        this.tradeOperationType = tradeManager.getTradeOperationType();
        if (tradeManager.getAppliedFilters() != null) {
            this.appliedFilters = tradeManager.getAppliedFilters().stream().map(filter -> new ItemFilterEntity(user, filter)).toList();
        }
        this.minBuySellProfit = tradeManager.getMinBuySellProfit();
        this.minProfitPercent = tradeManager.getMinProfitPercent();
        this.priority = tradeManager.getPriority();
    }

    public TradeByFiltersManager toTradeByFiltersManager() {
        TradeByFiltersManager tradeManager = new TradeByFiltersManager();
        tradeManager.setName(this.name);
        tradeManager.setEnabled(this.enabled);
        tradeManager.setTradeOperationType(this.tradeOperationType);
        if (this.appliedFilters != null) {
            tradeManager.setAppliedFilters(this.appliedFilters.stream().map(ItemFilterEntity::toItemFilter).toList());
        }
        tradeManager.setMinBuySellProfit(this.minBuySellProfit);
        tradeManager.setMinProfitPercent(this.minProfitPercent);
        tradeManager.setPriority(this.priority);
        return tradeManager;
    }
}
