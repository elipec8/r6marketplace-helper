package github.ricemonger.utils.DTOs.personal;

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

    public String toString() {
        String sb = "Trade By Item Filter Manager: \n" +
                    "Name: " + name + "\n" +
                    "Enabled: " + enabled + "\n" +
                    "Trade type: " + tradeOperationType + "\n";
        if (appliedFilters != null) {
            sb = sb + "Applied filters' names: " + appliedFilters.stream().map(ItemFilter::getName).reduce((a, b) -> a + ", " + b).orElse("") + "\n";
        } else {
            sb = sb + "Applied filters: null\n";
        }
        sb = sb + "Min difference from median price: " + minDifferenceFromMedianPrice + "\n" +
             "Min difference from median price percent: " + minDifferenceFromMedianPricePercent + "\n" +
             "Priority: " + priorityMultiplier + "\n";
        return sb;
    }
}
