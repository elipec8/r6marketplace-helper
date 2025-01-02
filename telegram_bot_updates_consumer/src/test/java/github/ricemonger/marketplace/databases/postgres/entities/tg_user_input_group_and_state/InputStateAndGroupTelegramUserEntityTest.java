package github.ricemonger.marketplace.databases.postgres.entities.tg_user_input_group_and_state;

import github.ricemonger.marketplace.databases.postgres.custom.tg_user_input_group_and_state.TelegramUserEntity;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class InputStateAndGroupTelegramUserEntityTest {

    @Test
    public void hashCode_should_return_equal_hash_for_equal_ids() {
        TelegramUserEntity entity1 = new TelegramUserEntity();
        entity1.setChatId("chatId");
        entity1.setInputState(InputState.TRADE_BY_ITEM_ID_MANAGER_ITEM_ID);
        entity1.setInputGroup(InputGroup.ITEMS_SHOW);

        TelegramUserEntity entity2 = new TelegramUserEntity();
        entity2.setChatId("chatId");

        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    public void equals_should_return_true_if_same() {
        TelegramUserEntity entity = new TelegramUserEntity();
        assertEquals(entity, entity);
    }

    @Test
    public void equals_should_return_true_if_equal_id() {
        TelegramUserEntity entity1 = new TelegramUserEntity();
        entity1.setChatId("chatId");
        entity1.setInputState(InputState.TRADE_BY_ITEM_ID_MANAGER_ITEM_ID);
        entity1.setInputGroup(InputGroup.ITEMS_SHOW);

        TelegramUserEntity entity2 = new TelegramUserEntity();
        entity2.setChatId("chatId");

        assertEquals(entity1, entity2);
    }

    @Test
    public void equals_should_return_false_if_null() {
        TelegramUserEntity entity = new TelegramUserEntity();
        assertNotEquals(null, entity);
    }

    @Test
    public void equals_should_return_false_if_different_ids() {
        TelegramUserEntity entity1 = new TelegramUserEntity();
        entity1.setChatId("chatId1");

        TelegramUserEntity entity2 = new TelegramUserEntity();
        entity2.setChatId("chatId2");

        assertNotEquals(entity1, entity2);
    }
}