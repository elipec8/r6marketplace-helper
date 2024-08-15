package github.ricemonger.utils.dtos;

import github.ricemonger.utils.enums.TradeManagerTradeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeByFiltersManager {
    private String name;

    private TradeManagerTradeType tradeType;

    private List<String> appliedFilters;

    private Integer maxBuyHours;
    private Integer maxSellHours;

    private Integer minProfit;
    private Integer minProfitPercent;

    private Integer priority;

    public String toString() {
        String sb = "Planned item filter trade: \n" +
                    "Name: " + name + "\n" +
                    "Trade type: " + tradeType + "\n" +
                    "Applied filters: " + appliedFilters.stream().reduce((a, b) -> a + ", " + b).orElse("") + "\n" +
                    "Max buy hours: " + maxBuyHours + "\n" +
                    "Max sell hours: " + maxSellHours + "\n" +
                    "Min profit: " + minProfit + "\n" +
                    "Min profit in percentages: " + minProfitPercent + "\n" +
                    "Priority: " + priority + "\n";
        return sb;
    }
}
