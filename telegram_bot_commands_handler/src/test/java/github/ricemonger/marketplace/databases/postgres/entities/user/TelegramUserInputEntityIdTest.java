package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TelegramUserInputEntityIdTest {

    @Test
    public void geChatId_should_return_chat_id() {
        TelegramUserEntity user = new TelegramUserEntity();
        user.setChatId("chat1");
        InputState state = InputState.BASE;

        TelegramUserInputEntityId id = new TelegramUserInputEntityId(user, state);

        assertEquals("chat1", id.getChatId_());
    }

    @Test
    public void getUserId_should_return_user_id() {
        TelegramUserEntity user = new TelegramUserEntity();
        user.setUser(new UserEntity(1L));
        InputState state = InputState.BASE;

        TelegramUserInputEntityId id = new TelegramUserInputEntityId(user, state);

        assertEquals(1L, id.getUserId_());
    }

    @Test
    public void constructor_with_usedId_should_set_id_fields() {
        TelegramUserInputEntityId id = new TelegramUserInputEntityId("chatId", 1L, InputState.BASE);

        assertEquals("chatId", id.getChatId_());
        assertEquals(1L, id.getUserId_());
        assertEquals(InputState.BASE, id.getInputState());
    }

    @Test
    public void constructor_with_usedEntity_should_set_id_fields() {
        TelegramUserInputEntityId id = new TelegramUserInputEntityId("chatId", new UserEntity(1L), InputState.BASE);

        assertEquals("chatId", id.getChatId_());
        assertEquals(1L, id.getUserId_());
        assertEquals(InputState.BASE, id.getInputState());
    }

    @Test
    public void hashCode_should_return_same_hash_for_equal_objects() {
        TelegramUserEntity user1 = new TelegramUserEntity();
        user1.setChatId("chat1");
        user1.setInputGroup(InputGroup.BASE);
        TelegramUserEntity user2 = new TelegramUserEntity();
        user2.setChatId("chat1");
        user2.setInputGroup(InputGroup.ITEM_FILTER_EDIT);
        InputState state = InputState.BASE;

        TelegramUserInputEntityId id1 = new TelegramUserInputEntityId(user1, state);
        TelegramUserInputEntityId id2 = new TelegramUserInputEntityId(user2, state);

        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void hashCode_should_return_different_hash_for_different_users() {
        TelegramUserEntity user1 = new TelegramUserEntity();
        user1.setChatId("chat1");
        TelegramUserEntity user2 = new TelegramUserEntity();
        user2.setChatId("chat2");
        InputState state = InputState.BASE;

        TelegramUserInputEntityId id1 = new TelegramUserInputEntityId(user1, state);
        TelegramUserInputEntityId id2 = new TelegramUserInputEntityId(user2, state);

        assertNotEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void hashCode_should_return_different_hash_for_different_input_states() {
        TelegramUserEntity user = new TelegramUserEntity();
        user.setChatId("chat1");
        InputState state1 = InputState.BASE;
        InputState state2 = InputState.ITEM_FILTER_IS_OWNED;

        TelegramUserInputEntityId id1 = new TelegramUserInputEntityId(user, state1);
        TelegramUserInputEntityId id2 = new TelegramUserInputEntityId(user, state2);

        assertNotEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void equals_should_return_true_for_equal_objects() {
        TelegramUserEntity user1 = new TelegramUserEntity();
        user1.setChatId("chat1");
        user1.setInputGroup(InputGroup.BASE);
        TelegramUserEntity user2 = new TelegramUserEntity();
        user2.setChatId("chat1");
        user2.setInputGroup(InputGroup.ITEM_FILTER_EDIT);
        InputState state = InputState.BASE;

        TelegramUserInputEntityId id1 = new TelegramUserInputEntityId(user1, state);
        TelegramUserInputEntityId id2 = new TelegramUserInputEntityId(user2, state);

        assertEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_different_objects() {
        TelegramUserEntity user1 = new TelegramUserEntity();
        user1.setChatId("chat1");
        TelegramUserEntity user2 = new TelegramUserEntity();
        user2.setChatId("chat2");
        InputState state = InputState.BASE;

        TelegramUserInputEntityId id1 = new TelegramUserInputEntityId(user1, state);
        TelegramUserInputEntityId id2 = new TelegramUserInputEntityId(user2, state);

        assertNotEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_different_input_states() {
        TelegramUserEntity user = new TelegramUserEntity();
        user.setChatId("chat1");
        InputState state1 = InputState.BASE;
        InputState state2 = InputState.ITEM_FILTER_IS_OWNED;

        TelegramUserInputEntityId id1 = new TelegramUserInputEntityId(user, state1);
        TelegramUserInputEntityId id2 = new TelegramUserInputEntityId(user, state2);

        assertNotEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_null() {
        TelegramUserEntity user = new TelegramUserEntity();
        user.setChatId("chat1");
        InputState state = InputState.BASE;

        TelegramUserInputEntityId id1 = new TelegramUserInputEntityId(user, state);

        assertNotEquals(null, id1);
    }

    @Test
    public void equals_should_return_false_for_different_class() {
        TelegramUserEntity user = new TelegramUserEntity();
        user.setChatId("chat1");
        InputState state = InputState.BASE;

        TelegramUserInputEntityId id1 = new TelegramUserInputEntityId(user, state);
        Object obj = new Object();

        assertNotEquals(id1, obj);
    }
}