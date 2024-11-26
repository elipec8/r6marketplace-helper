package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.telegramBot.InputState;
import github.ricemonger.utils.DTOs.TelegramUserInput;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TelegramUserInputEntityTest {
    @Test
    public void toTelegramUserInput_should_properly_map_with_default_fields() {
        TelegramUserEntity user = new TelegramUserEntity();
        user.setChatId("chatId");

        TelegramUserInputEntity entity = new TelegramUserInputEntity();
        entity.setTelegramUser(user);
        entity.setInputState(InputState.BASE);
        entity.setValue("inputValue");

        TelegramUserInput expected = new TelegramUserInput();
        expected.setChatId("chatId");
        expected.setInputState(InputState.BASE);
        expected.setValue("inputValue");

        TelegramUserInput actual = entity.toTelegramUserInput();

        assertEquals(expected, actual);
    }

    @Test
    public void toTelegramUserInput_should_properly_map_with_altered_fields() {
        TelegramUserEntity user = new TelegramUserEntity();
        user.setChatId("1");

        TelegramUserInputEntity entity = new TelegramUserInputEntity();
        entity.setTelegramUser(user);
        entity.setInputState(InputState.ITEM_FILTER_ITEM_TAGS_ESPORTS);
        entity.setValue("inputValue1");

        TelegramUserInput expected = new TelegramUserInput();
        expected.setChatId("1");
        expected.setInputState(InputState.ITEM_FILTER_ITEM_TAGS_ESPORTS);
        expected.setValue("inputValue1");

        TelegramUserInput actual = entity.toTelegramUserInput();

        assertEquals(expected, actual);
    }

    @Test
    public void constructor_should_properly_initialize_default_fields() {
        TelegramUserEntity user = new TelegramUserEntity();
        user.setChatId("chatId");

        TelegramUserInputEntity entity = new TelegramUserInputEntity(user, InputState.BASE);

        assertEquals(user, entity.getTelegramUser());
        assertEquals(InputState.BASE, entity.getInputState());
    }

    @Test
    public void constructor_should_properly_initialize_altered_fields() {
        TelegramUserEntity user = new TelegramUserEntity();
        user.setChatId("chatId");

        TelegramUserInputEntity entity = new TelegramUserInputEntity(user, InputState.ITEM_FILTER_ITEM_NAME_PATTERNS);

        assertEquals(user, entity.getTelegramUser());
        assertEquals(InputState.ITEM_FILTER_ITEM_NAME_PATTERNS, entity.getInputState());
    }
}