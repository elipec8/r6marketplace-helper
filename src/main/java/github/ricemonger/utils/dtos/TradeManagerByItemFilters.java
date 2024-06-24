package github.ricemonger.utils.dtos;

import github.ricemonger.utils.enums.TradeManagerTradeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeManagerByItemFilters {
    private String chatId;

    private String name;

    private TradeManagerTradeType tradeType;

    private List<String> appliedFilters;

    private Integer maxBuyHours;
    private Integer maxSellHours;

    private Integer minProfit;
    private Integer minProfitPercent;

    private Integer priority;

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Planned item filter trade: \n");
        sb.append("Name: ").append(name).append("\n");
        sb.append("Trade type: ").append(tradeType).append("\n");
        sb.append("Applied filters: ").append(appliedFilters.stream().reduce((a, b) -> a + ", " + b).orElse("")).append("\n");
        sb.append("Max buy hours: ").append(maxBuyHours).append("\n");
        sb.append("Max sell hours: ").append(maxSellHours).append("\n");
        sb.append("Min profit: ").append(minProfit).append("\n");
        sb.append("Min profit in percentages: ").append(minProfitPercent).append("\n");
        sb.append("Priority: ").append(priority).append("\n");
        return sb.toString();
    }
}
