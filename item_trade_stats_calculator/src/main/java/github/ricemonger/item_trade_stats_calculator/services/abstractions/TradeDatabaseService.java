package github.ricemonger.item_trade_stats_calculator.services.abstractions;

import github.ricemonger.item_trade_stats_calculator.services.DTOs.PrioritizedTrade;
import github.ricemonger.utils.DTOs.personal.UbiTrade;

import java.util.List;

public interface TradeDatabaseService {
    void prioritizeAllTrades(List<PrioritizedTrade> trades);

    List<UbiTrade> findAllUbiTrades();
}
