package github.ricemonger.utilspostgresschema.full_entities.user;

import github.ricemonger.utils.enums.InputState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TelegramUserInputEntityTest {

    @Test
    public void hashCode_should_return_equal_hash_for_equal_ids(){
        TelegramUserEntity telegramUser = new TelegramUserEntity();
        telegramUser.setChatId("12345");

        TelegramUserInputEntity input = new TelegramUserInputEntity();
        input.setTelegramUser(telegramUser);
        input.setInputState(InputState.ITEM_FILTER_MAX_PRICE);
        input.setValue("value1");

        TelegramUserInputEntity input2 = new TelegramUserInputEntity();
        input2.setTelegramUser(telegramUser);
        input2.setInputState(InputState.ITEM_FILTER_MAX_PRICE);
        input2.setValue("value2");

        assertEquals(input.hashCode(), input2.hashCode());
    }


    @Test
    public void equals_should_return_true_if_same() {
        TelegramUserInputEntity input = new TelegramUserInputEntity();
        assertEquals(input, input);
    }

    @Test
    public void equals_should_return_true_if_equal_ids() {
        TelegramUserEntity telegramUser = new TelegramUserEntity();
        telegramUser.setChatId("12345");

        TelegramUserInputEntity input = new TelegramUserInputEntity();
        input.setTelegramUser(telegramUser);
        input.setInputState(InputState.ITEM_FILTER_MAX_PRICE);
        input.setValue("value1");

        TelegramUserInputEntity input2 = new TelegramUserInputEntity();
        input2.setTelegramUser(telegramUser);
        input2.setInputState(InputState.ITEM_FILTER_MAX_PRICE);
        input2.setValue("value2");

        assertEquals(input, input2);
    }

    @Test
    public void equals_should_return_false_for_null() {
        TelegramUserInputEntity input = new TelegramUserInputEntity();
        assertNotEquals(null, input);
    }

    @Test
    public void equals_should_return_false_for_different_ids() {
        TelegramUserEntity telegramUser1 = new TelegramUserEntity();
        telegramUser1.setChatId("12345");

        TelegramUserInputEntity input = new TelegramUserInputEntity();
        input.setTelegramUser(telegramUser1);
        input.setInputState(InputState.ITEM_FILTER_MAX_PRICE);

        TelegramUserEntity telegramUser2 = new TelegramUserEntity();
        telegramUser2.setChatId("12345");

        TelegramUserInputEntity input2 = new TelegramUserInputEntity();
        input2.setTelegramUser(telegramUser2);
        input2.setInputState(InputState.ITEM_FILTER_MAX_PRICE);

        telegramUser2.setChatId("12346");
        assertNotEquals(input, input2);
        telegramUser2.setChatId("12345");
        input2.setInputState(InputState.ITEM_FILTER_MIN_PRICE);
        assertNotEquals(input, input2);
    }

    @Test
    public void getChatId_should_return_chat_id() {
        TelegramUserEntity telegramUser = new TelegramUserEntity();
        telegramUser.setChatId("12345");
        TelegramUserInputEntity input = new TelegramUserInputEntity();
        input.setTelegramUser(telegramUser);
        assertEquals("12345", input.getChatId_());
    }

    @Test
    public void isFullyEqualExceptTelegramUser_should_return_true_if_equal_() {
        TelegramUserInputEntity input1 = new TelegramUserInputEntity();
        input1.setTelegramUser(new TelegramUserEntity());
        input1.getTelegramUser().setChatId("chatId");
        input1.getTelegramUser().setUser(new UserEntity());
        input1.getTelegramUser().getUser().setId(1L);
        input1.setInputState(InputState.ITEM_FILTER_MAX_PRICE);
        input1.setValue("value1");

        TelegramUserInputEntity input2 = new TelegramUserInputEntity();
        input2.setTelegramUser(new TelegramUserEntity());
        input2.getTelegramUser().setChatId("chatId");
        input2.getTelegramUser().setUser(new UserEntity());
        input2.getTelegramUser().getUser().setId(1L);
        input2.setInputState(InputState.ITEM_FILTER_MAX_PRICE);
        input2.setValue("value1");

        assertTrue(input1.isFullyEqual(input2));
    }

    @Test
    public void isFullyEqualExceptTelegramUser_should_return_false_if_not_equal_() {
        TelegramUserInputEntity input1 = new TelegramUserInputEntity();
        input1.setTelegramUser(new TelegramUserEntity());
        input1.getTelegramUser().setChatId("chatId");
        input1.setInputState(InputState.ITEM_FILTER_MAX_PRICE);
        input1.setValue("value");

        TelegramUserInputEntity input2 = new TelegramUserInputEntity();
        input2.setTelegramUser(new TelegramUserEntity());
        input2.getTelegramUser().setChatId("chatId");
        input2.setInputState(InputState.ITEM_FILTER_MAX_PRICE);
        input2.setValue("value");

        input1.getTelegramUser().setChatId("chatId1");
        assertFalse(input1.isFullyEqual(input2));
        input1.getTelegramUser().setChatId("chatId");
        input1.setInputState(InputState.ITEM_FILTER_MIN_PRICE);
        assertFalse(input1.isFullyEqual(input2));
        input1.setInputState(InputState.ITEM_FILTER_MAX_PRICE);
        input1.setValue("value2");
        assertFalse(input1.isFullyEqual(input2));
    }
}