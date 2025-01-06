package github.ricemonger.item_trade_stats_calculator.services.DTOs;

import github.ricemonger.utils.DTOs.common.PotentialTradeStats;
import github.ricemonger.utils.DTOs.personal.UbiTrade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrioritizedTrade {
    private String tradeId;
    private Integer minutesToTrade;
    private Long tradePriority;

    public PrioritizedTrade(UbiTrade trade, PotentialTradeStats potentialTradeStats) {
        this.tradeId = trade.getTradeId();
        this.minutesToTrade = potentialTradeStats.getPrognosedTradeSuccessMinutes();
        this.tradePriority = potentialTradeStats.getTradePriority();

    }
}
