package github.ricemonger.trades_manager.services.DTOs;

import github.ricemonger.utils.DTOs.personal.ItemFilter;
import github.ricemonger.utils.enums.TradeOperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeByFiltersManager {
    private String name;
    private Boolean enabled;
    private TradeOperationType tradeOperationType;
    private List<ItemFilter> appliedFilters;
    private Integer minDifferenceFromMedianPrice;
    private Integer minDifferenceFromMedianPricePercent;
    private Integer priorityMultiplier;
}
