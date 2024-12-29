package github.ricemonger.telegramBot.update_consumer;

import github.ricemonger.marketplace.services.TelegramUserService;
import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.utils.DTOs.personal.TelegramUser;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UpdateInfoMapperTest {
    @Autowired
    private UpdateInfoMapper updateInfoMapper;
    @MockBean
    private TelegramUserService telegramUserService;

    @Test
    public void mapToUpdateInfo_should_map_update_to_updateInfo_if_has_message_not_registered() {
        Message message = new Message();
        message.setChat(new Chat(1L, "private"));
        message.setText("message_text");
        Update update = new Update();
        update.setMessage(message);
        update.setUpdateId(88);

        doThrow(new TelegramUserDoesntExistException("")).when(telegramUserService).getTelegramUser(1L);

        UpdateInfo expected = new UpdateInfo();
        expected.setUpdateId(88);
        expected.setChatId(1L);
        expected.setHasMessage(true);
        expected.setMessageText("message_text");
        expected.setInputState(InputState.BASE);
        expected.setInputGroup(InputGroup.BASE);
        expected.setHasCallBackQuery(false);
        expected.setCallbackQueryData(null);

        UpdateInfo actual = updateInfoMapper.mapToUpdateInfo(update);

        assertEquals(expected, actual);
    }

    @Test
    public void mapToUpdateInfo_should_map_update_to_updateInfo_if_has_callback_registered_no_input_state_and_group() {
        Message message = new Message();
        message.setChat(new Chat(1L, "private"));
        message.setText("message_text");

        CallbackQuery callbackQuery = new CallbackQuery();
        callbackQuery.setData("callback_data");
        callbackQuery.setMessage(message);

        Update update = new Update();
        update.setCallbackQuery(callbackQuery);
        update.setUpdateId(88);

        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setChatId("1");
        telegramUser.setInputState(null);
        telegramUser.setInputGroup(null);

        when(telegramUserService.getTelegramUser(1L)).thenReturn(telegramUser);

        UpdateInfo expected = new UpdateInfo();
        expected.setUpdateId(88);
        expected.setChatId(1L);
        expected.setHasMessage(false);
        expected.setMessageText(null);
        expected.setInputState(InputState.BASE);
        expected.setInputGroup(InputGroup.BASE);
        expected.setHasCallBackQuery(true);
        expected.setCallbackQueryData("callback_data");

        UpdateInfo actual = updateInfoMapper.mapToUpdateInfo(update);

        assertEquals(expected, actual);
    }

    @Test
    public void mapToUpdateInfo_should_map_update_to_updateInfo_if_has_callback_and_message_registered() {
        Message message = new Message();
        message.setChat(new Chat(1L, "private"));
        message.setText("message_text");

        Message callbackMessage = new Message();
        callbackMessage.setChat(new Chat(2L, "private"));
        message.setText("callback_message_text");

        CallbackQuery callbackQuery = new CallbackQuery();
        callbackQuery.setData("callback_data");
        callbackQuery.setMessage(callbackMessage);

        Update update = new Update();
        update.setCallbackQuery(callbackQuery);
        update.setMessage(message);
        update.setUpdateId(88);

        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setChatId("1");
        telegramUser.setInputState(InputState.ITEMS_SHOW_OFFSET);
        telegramUser.setInputGroup(InputGroup.UBI_ACCOUNT_ENTRY_LINK);

        when(telegramUserService.getTelegramUser(any())).thenReturn(telegramUser);

        UpdateInfo expected = new UpdateInfo();
        expected.setUpdateId(88);
        expected.setChatId(2L);
        expected.setHasMessage(true);
        expected.setMessageText("callback_message_text");
        expected.setInputState(InputState.ITEMS_SHOW_OFFSET);
        expected.setInputGroup(InputGroup.UBI_ACCOUNT_ENTRY_LINK);
        expected.setHasCallBackQuery(true);
    }
}
