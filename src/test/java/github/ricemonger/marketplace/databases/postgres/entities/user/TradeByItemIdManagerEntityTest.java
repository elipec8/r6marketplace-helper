package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.utils.DTOs.TradeByItemIdManager;
import github.ricemonger.utils.enums.TradeOperationType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TradeByItemIdManagerEntityTest {
    @Test
    public void toTradeByItemIdManager_should_map_fields() {
        TradeByItemIdManagerEntity entity = new TradeByItemIdManagerEntity();
        ItemEntity item = new ItemEntity();
        item.setItemId("1");
        entity.setItem(item);
        entity.setEnabled(true);
        entity.setTradeOperationType(TradeOperationType.BUY);
        entity.setSellStartingPrice(100);
        entity.setSellBoundaryPrice(200);
        entity.setBuyStartingPrice(300);
        entity.setBuyBoundaryPrice(400);
        entity.setPriority(1);

        TradeByItemIdManager expected = new TradeByItemIdManager();
        expected.setItemId("1");
        expected.setEnabled(true);
        expected.setTradeOperationType(TradeOperationType.BUY);
        expected.setSellStartingPrice(100);
        expected.setSellBoundaryPrice(200);
        expected.setBuyStartingPrice(300);
        expected.setBuyBoundaryPrice(400);
        expected.setPriority(1);

        TradeByItemIdManager actual = entity.toTradeByItemIdManager();

        assertEquals(expected, actual);
    }

    @Test
    public void constructor_should_map_fields() {
        TradeByItemIdManager manager = new TradeByItemIdManager();
        manager.setEnabled(true);
        manager.setTradeOperationType(TradeOperationType.BUY);
        manager.setSellStartingPrice(100);
        manager.setSellBoundaryPrice(200);
        manager.setBuyStartingPrice(300);
        manager.setBuyBoundaryPrice(400);
        manager.setPriority(1);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setItemId("2");

        TradeByItemIdManagerEntity expected = new TradeByItemIdManagerEntity();
        expected.setUser(userEntity);
        expected.setItem(itemEntity);
        expected.setEnabled(false);
        expected.setTradeOperationType(TradeOperationType.SELL);
        expected.setSellStartingPrice(200);
        expected.setSellBoundaryPrice(400);
        expected.setBuyStartingPrice(600);
        expected.setBuyBoundaryPrice(800);
        expected.setPriority(2);

        TradeByItemIdManagerEntity actual = new TradeByItemIdManagerEntity(userEntity, itemEntity, manager);

        assertEquals(expected.getUser().getId(), actual.getUser().getId());
        assertEquals(expected.getItem().getItemId(), actual.getItem().getItemId());
        assertEquals(expected.isEnabled(), actual.isEnabled());
        assertEquals(expected.getTradeOperationType(), actual.getTradeOperationType());
        assertEquals(expected.getSellStartingPrice(), actual.getSellStartingPrice());
        assertEquals(expected.getSellBoundaryPrice(), actual.getSellBoundaryPrice());
        assertEquals(expected.getBuyStartingPrice(), actual.getBuyStartingPrice());
        assertEquals(expected.getBuyBoundaryPrice(), actual.getBuyBoundaryPrice());
        assertEquals(expected.getPriority(), actual.getPriority());
    }
}