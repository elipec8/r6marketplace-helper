package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.telegramBot.InputState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TelegramUserInputEntityTest {
    @Test
    public void getChatId_should_return_chat_id() {
        TelegramUserEntity telegramUser = new TelegramUserEntity();
        telegramUser.setChatId("12345");
        TelegramUserInputEntity input = new TelegramUserInputEntity();
        input.setTelegramUser(telegramUser);
        assertEquals("12345", input.getChatId_());
    }

    @Test
    public void isFullyEqualExceptTelegramUser_should_return_true_if_equal_except_telegram_user() {
        TelegramUserInputEntity input1 = new TelegramUserInputEntity();
        input1.setTelegramUser(new TelegramUserEntity("chatId", new UserEntity(1L)));
        input1.setInputState(InputState.ITEM_FILTER_MAX_PRICE);
        input1.setValue("value1");

        TelegramUserInputEntity input2 = new TelegramUserInputEntity();
        input2.setTelegramUser(new TelegramUserEntity("chatId", new UserEntity(2L)));
        input2.setInputState(InputState.ITEM_FILTER_MAX_PRICE);
        input2.setValue("value1");

        assertTrue(input1.isFullyEqualExceptTelegramUser(input2));
    }

    @Test
    public void isFullyEqualExceptTelegramUser_should_return_false_if_not_equal_except_telegram_user() {
        TelegramUserInputEntity input1 = new TelegramUserInputEntity();
        input1.setTelegramUser(new TelegramUserEntity("chatId", new UserEntity(1L)));
        input1.setInputState(InputState.ITEM_FILTER_MAX_PRICE);
        input1.setValue("value1");

        TelegramUserInputEntity input2 = new TelegramUserInputEntity();
        input2.setTelegramUser(new TelegramUserEntity("chatId", new UserEntity(1L)));
        input2.setInputState(InputState.ITEM_FILTER_MAX_PRICE);
        input2.setValue("value1");

        input1.getTelegramUser().setChatId("chatId1");
        assertFalse(input1.isFullyEqualExceptTelegramUser(input2));
        input1.getTelegramUser().setChatId("chatId");
        input1.setInputState(InputState.ITEM_FILTER_MIN_PRICE);
        assertFalse(input1.isFullyEqualExceptTelegramUser(input2));
        input1.setInputState(InputState.ITEM_FILTER_MAX_PRICE);
        input1.setValue("value2");
        assertFalse(input1.isFullyEqualExceptTelegramUser(input2));
    }
}