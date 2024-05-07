package github.ricemonger.telegramBot.updateReceiver;

import github.ricemonger.marketplace.databases.neo4j.services.TelegramLinkedUserService;
import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.executors.InputGroup;
import github.ricemonger.telegramBot.executors.InputState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UpdateToUpdateInfoMapperTests {

    @MockBean
    private TelegramLinkedUserService telegramLinkedUserService;

    @Autowired
    private UpdateToUpdateInfoMapper updateToUpdateInfoMapper;

    public static final Update UPDATE = new Update();

    public static final UpdateInfo UPDATE_INFO;

    public static final UpdateInfo UPDATE_INFO_BASE_INPUTS;

    static{
        Message message = new Message();
        message.setChat(new Chat(1L, "private"));
        message.setText("text");

        CallbackQuery callbackQuery = new CallbackQuery();
        callbackQuery.setData("data");
        callbackQuery.setMessage(message);

        UPDATE.setUpdateId(1);
        UPDATE.setMessage(message);
        UPDATE.setCallbackQuery(callbackQuery);

        UpdateInfo.UpdateInfoBuilder builder =
                UpdateInfo.builder().
                        updateId(1).
                        chatId(1L).
                        hasMessage(true).
                        messageText("text").
                        hasCallBackQuery(true).
                        callbackQueryData("data");

        UPDATE_INFO = builder.inputState(InputState.CREDENTIALS_PASSWORD).inputGroup(InputGroup.CREDENTIALS_ADD).build();

        UPDATE_INFO_BASE_INPUTS = builder.inputState(InputState.BASE).inputGroup(InputGroup.BASE).build();
    }

    @Test
    public void updateInfoShouldHaveRightFieldsFromUpdateAndUserService(){
        when(telegramLinkedUserService.getUserInputState(1L)).thenReturn(InputState.CREDENTIALS_PASSWORD);
        when(telegramLinkedUserService.getUserInputGroup(1L)).thenReturn(InputGroup.CREDENTIALS_ADD);

        assertEquals(updateToUpdateInfoMapper.map(UPDATE), UPDATE_INFO);

        verify(telegramLinkedUserService).getUserInputState(1L);
        verify(telegramLinkedUserService).getUserInputGroup(1L);
    }

    @Test
    public void updateInfoShouldGetBaseInputStateAndInputGroupIfUserServiceReturnsNull(){
        when(telegramLinkedUserService.getUserInputState(1L)).thenReturn(null);
        when(telegramLinkedUserService.getUserInputGroup(1L)).thenReturn(null);

        assertEquals(updateToUpdateInfoMapper.map(UPDATE), UPDATE_INFO_BASE_INPUTS);

        verify(telegramLinkedUserService).getUserInputState(1L);
        verify(telegramLinkedUserService).getUserInputGroup(1L);
    }
}
