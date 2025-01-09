package github.ricemonger.item_trade_stats_calculator.postgres.services;

import github.ricemonger.item_trade_stats_calculator.postgres.repositories.TradePostgresRepository;
import github.ricemonger.item_trade_stats_calculator.postgres.services.entity_mappers.users.TradeEntityMapper;
import github.ricemonger.item_trade_stats_calculator.services.DTOs.PrioritizedTrade;
import github.ricemonger.item_trade_stats_calculator.services.abstractions.TradeDatabaseService;
import github.ricemonger.utils.DTOs.personal.UbiTrade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TradePostgresService implements TradeDatabaseService {

    private final TradePostgresRepository tradeRepository;

    private final TradeEntityMapper tradesEntityMapper;

    @Override
    @Transactional
    public void prioritizeAllTrades(List<PrioritizedTrade> trades) { //update
        tradeRepository.prioritizeAllTrades(trades.stream().map(tradesEntityMapper::createPrioritizedTradeDtoProjection).toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UbiTrade> findAllUbiTrades() {
        return tradeRepository.findAllUbiTrades().stream().map(tradesEntityMapper::createUbiTrade).toList();
    }
}
