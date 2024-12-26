package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.utils.enums.InputState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TelegramUserInputEntityTest {


    @Test
    public void isEqual_should_return_true_if_same() {
        TelegramUserInputEntity input = new TelegramUserInputEntity();
        assertTrue(input.isEqual(input));
    }

    @Test
    public void isEqual_should_return_true_if_equal_ids() {
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

        assertTrue(input.isEqual(input2));
    }

    @Test
    public void isEqual_should_return_false_for_null() {
        TelegramUserInputEntity input = new TelegramUserInputEntity();
        assertFalse(input.isEqual(null));
    }

    @Test
    public void isEqual_should_return_false_for_different_ids() {
        TelegramUserEntity telegramUser1 = new TelegramUserEntity();
        telegramUser1.setUser(new UserEntity(1L));
        telegramUser1.setChatId("12345");

        TelegramUserInputEntity input = new TelegramUserInputEntity();
        input.setTelegramUser(telegramUser1);
        input.setInputState(InputState.ITEM_FILTER_MAX_PRICE);

        TelegramUserInputEntity input2 = new TelegramUserInputEntity();
        input2.setTelegramUser(telegramUser1);
        input2.setInputState(InputState.ITEM_FILTER_MAX_PRICE);

        TelegramUserEntity telegramUser2 = new TelegramUserEntity();
        telegramUser2.setUser(new UserEntity(2L));
        telegramUser2.setChatId("12345");

        input2.setTelegramUser(telegramUser2);
        assertFalse(input.isEqual(input2));
        telegramUser2.setUser(new UserEntity(1L));
        telegramUser2.setChatId("12346");
        assertFalse(input.isEqual(input2));
        telegramUser2.setChatId("12345");
        input2.setInputState(InputState.ITEM_FILTER_MIN_PRICE);
        assertFalse(input.isEqual(input2));
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
        input1.setTelegramUser(new TelegramUserEntity("chatId", new UserEntity(1L)));
        input1.setInputState(InputState.ITEM_FILTER_MAX_PRICE);
        input1.setValue("value1");

        TelegramUserInputEntity input2 = new TelegramUserInputEntity();
        input2.setTelegramUser(new TelegramUserEntity("chatId", new UserEntity(1L)));
        input2.setInputState(InputState.ITEM_FILTER_MAX_PRICE);
        input2.setValue("value1");

        assertTrue(input1.isFullyEqual(input2));
    }

    @Test
    public void isFullyEqualExceptTelegramUser_should_return_false_if_not_equal_() {
        TelegramUserInputEntity input1 = new TelegramUserInputEntity();
        input1.setTelegramUser(new TelegramUserEntity("chatId", new UserEntity(1L)));
        input1.setInputState(InputState.ITEM_FILTER_MAX_PRICE);
        input1.setValue("value1");

        TelegramUserInputEntity input2 = new TelegramUserInputEntity();
        input2.setTelegramUser(new TelegramUserEntity("chatId", new UserEntity(1L)));
        input2.setInputState(InputState.ITEM_FILTER_MAX_PRICE);
        input2.setValue("value1");

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