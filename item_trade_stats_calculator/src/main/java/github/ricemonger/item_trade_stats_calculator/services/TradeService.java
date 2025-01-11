package github.ricemonger.item_trade_stats_calculator.services;

import github.ricemonger.item_trade_stats_calculator.services.DTOs.PrioritizedTrade;
import github.ricemonger.item_trade_stats_calculator.services.abstractions.TradeDatabaseService;
import github.ricemonger.utils.DTOs.personal.UbiTrade;
import github.ricemonger.utils.enums.TradeCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeService {

    private final PotentialTradeStatsService potentialTradeStatsService;

    private final TradeDatabaseService tradeDatabaseService;

    public void recalculateAndSaveUsersCurrentTradesPotentialTradeStats() {
        List<PrioritizedTrade> prioritizedTrades = new ArrayList<>();

        for (UbiTrade ubiTrade : tradeDatabaseService.findAllUbiTrades()) {
            if (ubiTrade.getCategory() == TradeCategory.Buy) {
                prioritizedTrades.add(new PrioritizedTrade(ubiTrade, potentialTradeStatsService.calculatePotentialBuyTradeStatsForExistingTrade(ubiTrade)));
            } else if (ubiTrade.getCategory() == TradeCategory.Sell) {
                prioritizedTrades.add(new PrioritizedTrade(ubiTrade, potentialTradeStatsService.calculatePotentialSellTradeStatsForExistingTrade(ubiTrade)));
            }
        }

        tradeDatabaseService.prioritizeAllTrades(prioritizedTrades);
    }
}
