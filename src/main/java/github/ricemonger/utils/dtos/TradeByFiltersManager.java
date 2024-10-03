package github.ricemonger.utils.dtos;

import github.ricemonger.utils.enums.TradeManagingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeByFiltersManager {
    private String name;
    private boolean enabled;
    private TradeManagingType tradeManagingType;
    private List<ItemFilter> appliedFilters;
    private Integer minBuySellProfit;
    private Integer minProfitPercent;
    private Integer priority;

    public String toString() {
        String sb = "Trade By Item Filter Manager: \n" +
                    "Name: " + name + "\n" +
                    "Enabled: " + enabled + "\n" +
                    "Trade type: " + tradeManagingType + "\n";
        if (appliedFilters != null) {
            sb = sb + "Applied filters' names: " + appliedFilters.stream().map(ItemFilter::getName).reduce((a, b) -> a + ", " + b).orElse("") + "\n";
        } else {
            sb = sb + "Applied filters: null\n";
        }
        sb = sb + "Min profit: " + minBuySellProfit + "\n" +
             "Min profit percent: " + minProfitPercent + "\n" +
             "Priority: " + priority + "\n";
        return sb;
    }
}
