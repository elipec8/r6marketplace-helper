package github.ricemonger.utils.dtos;

import github.ricemonger.utils.enums.TradeManagerTradeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeManagerByItemId {
    private String chatId;
    private TradeManagerTradeType tradeType;
    private String itemId;
    private Integer sellStartingPrice;
    private Integer sellBoundaryPrice;
    private Integer buyStartingPrice;
    private Integer buyBoundaryPrice;
    private Integer priority;

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Planned one item trade: \n");
        sb.append("Trade type: ").append(tradeType).append("\n");
        sb.append("Item id: ").append(itemId).append("\n");

        if (tradeType == TradeManagerTradeType.SELL) {
            sb.append("Starting price: ").append(sellStartingPrice).append("\n");
            sb.append("Boundary price: ").append(sellBoundaryPrice).append("\n");
        } else if (tradeType == TradeManagerTradeType.BUY) {
            sb.append("Starting price: ").append(buyStartingPrice).append("\n");
            sb.append("Boundary price: ").append(buyBoundaryPrice).append("\n");
        } else if (tradeType == TradeManagerTradeType.BUY_AND_SELL || tradeType == null) {
            sb.append("Starting Sell price: ").append(sellStartingPrice).append("\n");
            sb.append("Boundary Sell price: ").append(sellBoundaryPrice).append("\n");
            sb.append("Starting Buy price: ").append(buyStartingPrice).append("\n");
            sb.append("Boundary Buy price: ").append(buyBoundaryPrice).append("\n");
        }

        sb.append("Priority: ").append(priority).append("\n");

        return sb.toString();
    }
}
