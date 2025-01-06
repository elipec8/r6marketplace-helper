package github.ricemonger.item_trade_stats_calculator.services;

import github.ricemonger.item_trade_stats_calculator.services.DTOs.PrioritizedTrade;
import github.ricemonger.item_trade_stats_calculator.services.abstractions.TradeDatabaseService;
import github.ricemonger.utils.DTOs.common.PotentialTradeStats;
import github.ricemonger.utils.DTOs.personal.UbiTrade;
import github.ricemonger.utils.enums.TradeCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TradeServiceTest {
    @Autowired
    private TradeService tradeService;
    @MockBean
    private PotentialTradeStatsCalculator potentialTradeStatsCalculator;
    @MockBean
    private TradeDatabaseService tradeDatabaseService;

    @Test
    public void recalculateAndSaveUsersCurrentTradesPotentialTradeStats_should_get_all_users_current_trades_and_recalculate_and_save_their_potential_trade_stats() {
        UbiTrade buyTrade = new UbiTrade();
        buyTrade.setTradeId("buyTradeId");
        buyTrade.setCategory(TradeCategory.Buy);
        UbiTrade sellTrade = new UbiTrade();
        sellTrade.setTradeId("sellTradeId");
        sellTrade.setCategory(TradeCategory.Sell);
        UbiTrade unknownTrade = new UbiTrade();
        unknownTrade.setTradeId("unknownTradeId");
        unknownTrade.setCategory(TradeCategory.Unknown);

        when(tradeDatabaseService.findAllUbiTrades()).thenReturn(List.of(buyTrade, sellTrade, unknownTrade));

        PotentialTradeStats buyTradeStats = new PotentialTradeStats();
        buyTradeStats.setPrognosedTradeSuccessMinutes(10);
        buyTradeStats.setTradePriority(1L);
        PotentialTradeStats sellTradeStats = new PotentialTradeStats();
        sellTradeStats.setPrognosedTradeSuccessMinutes(20);
        sellTradeStats.setTradePriority(2L);

        when(potentialTradeStatsCalculator.calculatePotentialBuyTradeStatsForExistingTrade(buyTrade)).thenReturn(buyTradeStats);
        when(potentialTradeStatsCalculator.calculatePotentialSellTradeStatsForExistingTrade(sellTrade)).thenReturn(sellTradeStats);

        PrioritizedTrade buyPrioritizedTrade = new PrioritizedTrade();
        buyPrioritizedTrade.setTradeId(buyTrade.getTradeId());
        buyPrioritizedTrade.setMinutesToTrade(buyTradeStats.getPrognosedTradeSuccessMinutes());
        buyPrioritizedTrade.setTradePriority(buyTradeStats.getTradePriority());
        PrioritizedTrade sellPrioritizedTrade = new PrioritizedTrade();
        sellPrioritizedTrade.setTradeId(sellTrade.getTradeId());
        sellPrioritizedTrade.setMinutesToTrade(sellTradeStats.getPrognosedTradeSuccessMinutes());
        sellPrioritizedTrade.setTradePriority(sellTradeStats.getTradePriority());

        tradeService.recalculateAndSaveUsersCurrentTradesPotentialTradeStats();

        verify(tradeDatabaseService).saveAllPrioritizedTrades(argThat(trades -> trades.contains(buyPrioritizedTrade) && trades.contains(sellPrioritizedTrade) && trades.size() == 2));
    }
}