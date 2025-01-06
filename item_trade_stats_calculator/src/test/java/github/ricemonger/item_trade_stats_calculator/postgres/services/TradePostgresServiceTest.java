package github.ricemonger.item_trade_stats_calculator.postgres.services;

import github.ricemonger.item_trade_stats_calculator.postgres.entities.PrioritizedTradeEntity;
import github.ricemonger.item_trade_stats_calculator.postgres.entities.UbiTradeEntity;
import github.ricemonger.item_trade_stats_calculator.postgres.repositories.PrioritizedTradePostgresRepository;
import github.ricemonger.item_trade_stats_calculator.postgres.repositories.UbiTradePostgresRepository;
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
    private UbiTradePostgresRepository ubiTradeRepository;
    @MockBean
    private PrioritizedTradePostgresRepository prioritizedTradeRepository;

    @Test
    public void findAllUbiTrades_should_return_mapped_entities() {
        UbiTradeEntity entity1 = Mockito.mock(UbiTradeEntity.class);
        UbiTradeEntity entity2 = Mockito.mock(UbiTradeEntity.class);

        when(ubiTradeRepository.findAll()).thenReturn(List.of(entity1, entity2));

        UbiTrade dto1 = Mockito.mock(UbiTrade.class);
        UbiTrade dto2 = Mockito.mock(UbiTrade.class);

        when(tradesEntityMapper.createUbiTrade(entity1)).thenReturn(dto1);
        when(tradesEntityMapper.createUbiTrade(entity2)).thenReturn(dto2);

        List<UbiTrade> result = tradePostgresService.findAllUbiTrades();

        assertEquals(2, result.size());
        assertTrue(result.contains(dto1));
        assertTrue(result.contains(dto2));
    }

    @Test
    public void saveAllPrioritizedTrades_should_save_all_mapped_dtos() {
        PrioritizedTrade dto1 = Mockito.mock(PrioritizedTrade.class);
        PrioritizedTrade dto2 = Mockito.mock(PrioritizedTrade.class);

        PrioritizedTradeEntity entity1 = Mockito.mock(PrioritizedTradeEntity.class);
        PrioritizedTradeEntity entity2 = Mockito.mock(PrioritizedTradeEntity.class);

        when(tradesEntityMapper.createPrioritizedTradeEntity(dto1)).thenReturn(entity1);
        when(tradesEntityMapper.createPrioritizedTradeEntity(dto2)).thenReturn(entity2);

        tradePostgresService.saveAllPrioritizedTrades(List.of(dto1, dto2));

        verify(prioritizedTradeRepository).saveAll(argThat(entities -> ((List<?>) entities).contains(entity1) && ((List<?>) entities).contains(entity2) && ((List<PrioritizedTradeEntity>) entities).size() == 2));
    }
}