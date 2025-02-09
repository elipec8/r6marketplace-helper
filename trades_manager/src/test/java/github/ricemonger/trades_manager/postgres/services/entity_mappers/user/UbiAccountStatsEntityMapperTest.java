package github.ricemonger.trades_manager.postgres.services.entity_mappers.user;


import github.ricemonger.trades_manager.services.DTOs.UbiAccountStats;
import github.ricemonger.utils.DTOs.personal.ItemResaleLock;
import github.ricemonger.utils.DTOs.personal.UbiTrade;
import github.ricemonger.utilspostgresschema.full_entities.item.ItemEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.ItemResaleLockEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.TradeEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.UbiAccountStatsEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UbiAccountStatsEntityMapperTest {
    @Autowired
    private UbiAccountStatsEntityMapper ubiAccountStatsEntityMapper;
    @MockBean
    private ItemResaleLockEntityMapper itemResaleLockEntityMapper;
    @MockBean
    private TradeEntityMapper tradeEntityMapper;

    @Test
    public void createDTO_should_return_expected_result() {
        ItemResaleLockEntity resaleLockEntity1 = Mockito.mock(ItemResaleLockEntity.class);
        ItemResaleLockEntity resaleLockEntity2 = Mockito.mock(ItemResaleLockEntity.class);

        ItemResaleLock resaleLock1 = Mockito.mock(ItemResaleLock.class);
        ItemResaleLock resaleLock2 = Mockito.mock(ItemResaleLock.class);

        Mockito.when(itemResaleLockEntityMapper.createDTO(resaleLockEntity1)).thenReturn(resaleLock1);
        Mockito.when(itemResaleLockEntityMapper.createDTO(resaleLockEntity2)).thenReturn(resaleLock2);

        TradeEntity buyTradeEntity1 = Mockito.mock(TradeEntity.class);
        TradeEntity buyTradeEntity2 = Mockito.mock(TradeEntity.class);

        UbiTrade buyUbiTrade1 = Mockito.mock(UbiTrade.class);
        UbiTrade buyUbiTrade2 = Mockito.mock(UbiTrade.class);

        Mockito.when(tradeEntityMapper.createDTO(buyTradeEntity1)).thenReturn(buyUbiTrade1);
        Mockito.when(tradeEntityMapper.createDTO(buyTradeEntity2)).thenReturn(buyUbiTrade2);

        TradeEntity sellTradeEntity1 = Mockito.mock(TradeEntity.class);
        TradeEntity sellTradeEntity2 = Mockito.mock(TradeEntity.class);

        UbiTrade sellUbiTrade1 = Mockito.mock(UbiTrade.class);
        UbiTrade sellUbiTrade2 = Mockito.mock(UbiTrade.class);

        Mockito.when(tradeEntityMapper.createDTO(sellTradeEntity1)).thenReturn(sellUbiTrade1);
        Mockito.when(tradeEntityMapper.createDTO(sellTradeEntity2)).thenReturn(sellUbiTrade2);

        UbiAccountStatsEntity entity = new UbiAccountStatsEntity();
        entity.setUbiProfileId("ubiProfileId");
        entity.setSoldIn24h(1);
        entity.setBoughtIn24h(2);
        entity.setCreditAmount(3);
        entity.setOwnedItems(List.of(new ItemEntity("itemId1"), new ItemEntity("itemId2")));
        entity.setResaleLocks(List.of(resaleLockEntity1, resaleLockEntity2));
        entity.setCurrentSellTrades(List.of(sellTradeEntity1, sellTradeEntity2));
        entity.setCurrentBuyTrades(List.of(buyTradeEntity1, buyTradeEntity2));

        UbiAccountStats result = ubiAccountStatsEntityMapper.createDTO(entity);

        assertEquals("ubiProfileId", result.getUbiProfileId());
        assertEquals(1, result.getSoldIn24h());
        assertEquals(2, result.getBoughtIn24h());
        assertEquals(3, result.getCreditAmount());

        assertEquals(2, result.getOwnedItemsIds().size());
        assertTrue(result.getOwnedItemsIds().contains("itemId1"));
        assertTrue(result.getOwnedItemsIds().contains("itemId2"));

        assertEquals(2, result.getResaleLocks().size());
        assertTrue(result.getResaleLocks().stream().anyMatch(lock -> lock == resaleLock1));
        assertTrue(result.getResaleLocks().stream().anyMatch(lock -> lock == resaleLock2));

        assertEquals(2, result.getCurrentSellTrades().size());
        assertTrue(result.getCurrentSellTrades().stream().anyMatch(trade -> trade == sellUbiTrade1));
        assertTrue(result.getCurrentSellTrades().stream().anyMatch(trade -> trade == sellUbiTrade2));

        assertEquals(2, result.getCurrentBuyTrades().size());
        assertTrue(result.getCurrentBuyTrades().stream().anyMatch(trade -> trade == buyUbiTrade1));
        assertTrue(result.getCurrentBuyTrades().stream().anyMatch(trade -> trade == buyUbiTrade2));
    }
}