package github.ricemonger.utilspostgresschema.full_entities.user;

import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TelegramUserEntityTest {

    @Test
    public void hashCode_should_return_equal_hash_for_equal_ids() {
        TelegramUserEntity telegramUser1 = new TelegramUserEntity();
        telegramUser1.setUser(new UserEntity());
        telegramUser1.getUser().setId(1L);
        telegramUser1.setChatId("chatId");
        telegramUser1.setInputState(InputState.BASE);
        telegramUser1.setInputGroup(InputGroup.BASE);
        telegramUser1.setItemShowMessagesLimit(50);
        telegramUser1.setItemShowFewInMessageFlag(false);
        telegramUser1.setTelegramUserInputs(List.of(new TelegramUserInputEntity(telegramUser1, InputState.BASE, "value")));

        TelegramUserEntity telegramUser2 = new TelegramUserEntity();
        telegramUser2.setUser(new UserEntity());
        telegramUser2.getUser().setId(1L);
        telegramUser2.setChatId("chatId");
        telegramUser2.setInputState(InputState.UBI_ACCOUNT_ENTRY_2FA_CODE);
        telegramUser2.setInputGroup(InputGroup.ITEM_FILTER_EDIT);
        telegramUser2.setItemShowMessagesLimit(51);
        telegramUser2.setItemShowFewInMessageFlag(true);
        telegramUser2.setTelegramUserInputs(List.of());

        assertEquals(telegramUser1.hashCode(), telegramUser2.hashCode());
    }

    @Test
    public void equals_should_return_true_if_same() {
        TelegramUserEntity telegramUser = new TelegramUserEntity();
        assertEquals(telegramUser, telegramUser);
    }

    @Test
    public void equals_should_return_true_if_equal_ids() {
        TelegramUserEntity telegramUser1 = new TelegramUserEntity();
        telegramUser1.setUser(new UserEntity());
        telegramUser1.getUser().setId(1L);
        telegramUser1.setChatId("chatId");
        telegramUser1.setInputState(InputState.BASE);
        telegramUser1.setInputGroup(InputGroup.BASE);
        telegramUser1.setItemShowMessagesLimit(50);
        telegramUser1.setItemShowFewInMessageFlag(false);
        telegramUser1.setTelegramUserInputs(List.of(new TelegramUserInputEntity(telegramUser1, InputState.BASE, "value")));

        TelegramUserEntity telegramUser2 = new TelegramUserEntity();
        telegramUser2.setUser(new UserEntity());
        telegramUser2.getUser().setId(1L);
        telegramUser2.setChatId("chatId");
        telegramUser2.setInputState(InputState.UBI_ACCOUNT_ENTRY_2FA_CODE);
        telegramUser2.setInputGroup(InputGroup.ITEM_FILTER_EDIT);
        telegramUser2.setItemShowMessagesLimit(51);
        telegramUser2.setItemShowFewInMessageFlag(true);
        telegramUser2.setTelegramUserInputs(List.of());

        assertEquals(telegramUser1, telegramUser2);
    }

    @Test
    public void equals_should_return_false_for_null() {
        TelegramUserEntity telegramUser = new TelegramUserEntity();
        assertNotEquals(null, telegramUser);
    }

    @Test
    public void equals_should_return_false_if_different_ids() {
        TelegramUserEntity telegramUser1 = new TelegramUserEntity();
        telegramUser1.setChatId("chatId");

        TelegramUserEntity telegramUser2 = new TelegramUserEntity();
        telegramUser2.setChatId("chatId1");

        assertNotEquals(telegramUser1, telegramUser2);
    }
}