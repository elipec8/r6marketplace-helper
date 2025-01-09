package github.ricemonger.item_trade_stats_calculator.postgres.services;

import github.ricemonger.item_trade_stats_calculator.postgres.dto_projections.PrioritizedTradeProjection;
import github.ricemonger.item_trade_stats_calculator.postgres.dto_projections.UbiTradeProjection;
import github.ricemonger.item_trade_stats_calculator.postgres.repositories.TradePostgresRepository;
import github.ricemonger.item_trade_stats_calculator.postgres.services.entity_mappers.users.TradeEntityMapper;
import github.ricemonger.item_trade_stats_calculator.services.DTOs.PrioritizedTrade;
import github.ricemonger.utils.DTOs.personal.UbiTrade;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TradePostgresServiceTest {
    @Autowired
    private TradePostgresService tradePostgresService;
    @MockBean
    private TradeEntityMapper tradesEntityMapper;
    @MockBean
    private TradePostgresRepository ubiTradeRepository;
    @Autowired
    private TradePostgresRepository tradePostgresRepository;

    @Test
    public void findAllUbiTrades_should_return_mapped_entities() {
        UbiTradeProjection projection1 = Mockito.mock(UbiTradeProjection.class);
        UbiTradeProjection projection2 = Mockito.mock(UbiTradeProjection.class);

        when(ubiTradeRepository.findAllUbiTrades()).thenReturn(List.of(projection1, projection2));

        UbiTrade dto1 = Mockito.mock(UbiTrade.class);
        UbiTrade dto2 = Mockito.mock(UbiTrade.class);

        when(tradesEntityMapper.createUbiTrade(projection1)).thenReturn(dto1);
        when(tradesEntityMapper.createUbiTrade(projection2)).thenReturn(dto2);

        List<UbiTrade> result = tradePostgresService.findAllUbiTrades();

        assertEquals(2, result.size());
        assertTrue(result.contains(dto1));
        assertTrue(result.contains(dto2));
    }

    @Test
    public void saveAllPrioritizedTrades_should_prioritize_all_mapped_dtos() {
        PrioritizedTrade dto1 = Mockito.mock(PrioritizedTrade.class);
        PrioritizedTrade dto2 = Mockito.mock(PrioritizedTrade.class);

        PrioritizedTradeProjection projection1 = Mockito.mock(PrioritizedTradeProjection.class);
        PrioritizedTradeProjection projection2 = Mockito.mock(PrioritizedTradeProjection.class);

        when(tradesEntityMapper.createPrioritizedTradeDtoProjection(dto1)).thenReturn(projection1);
        when(tradesEntityMapper.createPrioritizedTradeDtoProjection(dto2)).thenReturn(projection2);

        tradePostgresService.prioritizeAllTrades(List.of(dto1, dto2));

        verify(tradePostgresRepository).prioritizeAllTrades(argThat(arg -> arg.stream().anyMatch(projection1::equals) && arg.stream().anyMatch(projection2::equals) && arg.size() == 2));
    }
}