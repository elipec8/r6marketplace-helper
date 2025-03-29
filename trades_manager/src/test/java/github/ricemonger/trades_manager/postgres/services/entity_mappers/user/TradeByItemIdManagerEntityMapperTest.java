package github.ricemonger.trades_manager.postgres.services.entity_mappers.user;

import github.ricemonger.utils.DTOs.personal.TradeByItemIdManager;
import github.ricemonger.utils.enums.TradeOperationType;
import github.ricemonger.utilspostgresschema.full_entities.item.ItemEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.TradeByItemIdManagerEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TradeByItemIdManagerEntityMapperTest {
    @Autowired
    TradeByItemIdManagerEntityMapper tradeByItemIdManagerEntityMapper;

    @Test
    public void createDTO_should_return_expected_result() {
        TradeByItemIdManagerEntity entity = new TradeByItemIdManagerEntity();
        entity.setItem(new ItemEntity("itemId"));
        entity.setEnabled(true);
        entity.setTradeOperationType(TradeOperationType.SELL);
        entity.setSellBoundaryPrice(100);
        entity.setBuyBoundaryPrice(200);
        entity.setPriorityMultiplier(300);

        TradeByItemIdManager result = tradeByItemIdManagerEntityMapper.createDTO(entity);

        assertEquals(entity.getItemId_(), result.getItemId());
        assertEquals(entity.getEnabled(), result.getEnabled());
        assertEquals(entity.getTradeOperationType(), result.getTradeOperationType());
        assertEquals(entity.getSellBoundaryPrice(), result.getSellBoundaryPrice());
        assertEquals(entity.getBuyBoundaryPrice(), result.getBuyBoundaryPrice());
    }
}