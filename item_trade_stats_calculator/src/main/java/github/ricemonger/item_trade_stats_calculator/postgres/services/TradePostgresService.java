package github.ricemonger.item_trade_stats_calculator.postgres.services;

import github.ricemonger.item_trade_stats_calculator.postgres.repositories.PrioritizedTradePostgresRepository;
import github.ricemonger.item_trade_stats_calculator.postgres.repositories.UbiTradePostgresRepository;
import github.ricemonger.item_trade_stats_calculator.postgres.services.entity_mappers.users.TradeEntityMapper;
import github.ricemonger.item_trade_stats_calculator.services.abstractions.TradeDatabaseService;
import github.ricemonger.item_trade_stats_calculator.services.DTOs.PrioritizedTrade;
import github.ricemonger.utils.DTOs.personal.UbiTrade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TradePostgresService implements TradeDatabaseService {

    private final TradeEntityMapper tradesEntityMapper;

    private final UbiTradePostgresRepository ubiTradeRepository;

    private final PrioritizedTradePostgresRepository prioritizedTradeRepository;

    @Override
    public List<UbiTrade> findAllUbiTrades() {
        return ubiTradeRepository.findAll().stream().map(tradesEntityMapper::createUbiTrade).toList();
    }

    @Override
    public void saveAllPrioritizedTrades(List<PrioritizedTrade> trades) {
        prioritizedTradeRepository.saveAll(trades.stream().map(tradesEntityMapper::createPrioritizedTradeEntity).toList());
    }
}
