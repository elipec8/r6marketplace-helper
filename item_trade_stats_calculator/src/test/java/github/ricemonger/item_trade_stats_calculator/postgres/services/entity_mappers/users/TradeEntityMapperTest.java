package github.ricemonger.item_trade_stats_calculator.postgres.services.entity_mappers.users;

import github.ricemonger.item_trade_stats_calculator.postgres.entities.item.ItemEntity;
import github.ricemonger.item_trade_stats_calculator.postgres.entities.item.PrioritizedTradeEntity;
import github.ricemonger.item_trade_stats_calculator.postgres.entities.item.UbiTradeEntity;
import github.ricemonger.item_trade_stats_calculator.postgres.services.entity_mappers.item.ItemEntitiesMapper;
import github.ricemonger.item_trade_stats_calculator.services.DTOs.PrioritizedTrade;
import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.DTOs.personal.UbiTrade;
import github.ricemonger.utils.enums.TradeCategory;
import github.ricemonger.utils.enums.TradeState;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class TradeEntityMapperTest {
    @Autowired
    private TradeEntityMapper tradeEntityMapper;
    @MockBean
    private ItemEntitiesMapper itemEntitiesMapper;

    @Test
    public void createUbiTrade_should_return_expected_result(){

        ItemEntity itemEntity = Mockito.mock(ItemEntity.class);

        Item item = Mockito.mock(Item.class);

        when(itemEntitiesMapper.createItem(itemEntity)).thenReturn(item);

        UbiTradeEntity entity = new UbiTradeEntity();
        entity.setTradeId("tradeId");
        entity.setState(TradeState.Created);
        entity.setCategory(TradeCategory.Buy);
        entity.setExpiresAt(LocalDateTime.of(2021, 1, 1, 0, 0));
        entity.setLastModifiedAt(LocalDateTime.of(2022, 1, 1, 0, 0));
        entity.setItem(itemEntity);
        entity.setSuccessPaymentPrice(100);
        entity.setSuccessPaymentFee(10);
        entity.setProposedPaymentPrice(200);
        entity.setProposedPaymentFee(20);

        UbiTrade trade = tradeEntityMapper.createUbiTrade(entity);

        assertEquals("tradeId", trade.getTradeId());
        assertEquals(TradeState.Created, trade.getState());
        assertEquals(TradeCategory.Buy, trade.getCategory());
        assertEquals(LocalDateTime.of(2021, 1, 1, 0, 0), trade.getExpiresAt());
        assertEquals(LocalDateTime.of(2022, 1, 1, 0, 0), trade.getLastModifiedAt());
        assertEquals(item, trade.getItem());
        assertEquals(100, trade.getSuccessPaymentPrice());
        assertEquals(10, trade.getSuccessPaymentFee());
        assertEquals(200, trade.getProposedPaymentPrice());
        assertEquals(20, trade.getProposedPaymentFee());
    }

    @Test
    public void createPrioritizedTradeEntity_should_return_expected_result(){
        PrioritizedTrade dto = new PrioritizedTrade();
        dto.setTradeId("tradeId");
        dto.setMinutesToTrade(1);
        dto.setTradePriority(2L);

        PrioritizedTradeEntity entity = tradeEntityMapper.createPrioritizedTradeEntity(dto);

        assertEquals("tradeId", entity.getTradeId());
        assertEquals(1, entity.getMinutesToTrade());
        assertEquals(2L, entity.getTradePriority());
    }
}