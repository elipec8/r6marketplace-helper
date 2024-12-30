package github.ricemonger.telegramBot.executors;

import github.ricemonger.telegramBot.CallbackButton;
import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class AbstractBotCommandExecutorTest {
    private AbstractBotCommandExecutor abstractBotCommandExecutor;

    private UpdateInfo updateInfo;
    @MockBean
    private BotInnerService botInnerService;

    @BeforeEach
    void setUp() {
        updateInfo = spy(new UpdateInfo());
        updateInfo.setChatId(999L);
        AbstractBotCommandExecutor executor = new AbstractBotCommandExecutor() {
            @Override
            protected void executeCommand() {
            }
        };
        executor.updateInfo = updateInfo;
        executor.botInnerService = botInnerService;
        abstractBotCommandExecutor = spy(executor);
    }

    @Test
    public void initAndExecute_should_set_fields_and_execute_command() {
        abstractBotCommandExecutor.initAndExecute(updateInfo, botInnerService);

        assertSame(updateInfo, abstractBotCommandExecutor.updateInfo);
        assertSame(botInnerService, abstractBotCommandExecutor.botInnerService);

        verify(abstractBotCommandExecutor).executeCommand();
    }

    @Test
    public void processFirstInput_with_text_should_clear_user_inputs_and_set_state_and_group_and_send_text() {
        abstractBotCommandExecutor.updateInfo = updateInfo;
        abstractBotCommandExecutor.botInnerService = botInnerService;

        abstractBotCommandExecutor.processFirstInput(InputState.ITEM_FILTER_ITEM_NAME_PATTERNS, InputGroup.ITEMS_SHOW_SETTINGS_CHANGE_MESSAGE_LIMIT, "question");

        verify(botInnerService).clearUserInputsAndSetInputStateAndGroup(updateInfo.getChatId());
        verify(botInnerService).setUserInputState(updateInfo.getChatId(), InputState.ITEM_FILTER_ITEM_NAME_PATTERNS);
        verify(botInnerService).setUserInputGroup(updateInfo.getChatId(), InputGroup.ITEMS_SHOW_SETTINGS_CHANGE_MESSAGE_LIMIT);
        verify(abstractBotCommandExecutor).sendText("question");
    }

    @Test
    public void processFirstInput_should_clear_user_inputs_and_set_state_and_group() {
        abstractBotCommandExecutor.updateInfo = updateInfo;
        abstractBotCommandExecutor.botInnerService = botInnerService;

        abstractBotCommandExecutor.processFirstInput(InputState.ITEM_FILTER_ITEM_NAME_PATTERNS, InputGroup.ITEMS_SHOW_SETTINGS_CHANGE_MESSAGE_LIMIT);

        verify(botInnerService).clearUserInputsAndSetInputStateAndGroup(updateInfo.getChatId());
        verify(botInnerService).setUserInputState(updateInfo.getChatId(), InputState.ITEM_FILTER_ITEM_NAME_PATTERNS);
        verify(botInnerService).setUserInputGroup(updateInfo.getChatId(), InputGroup.ITEMS_SHOW_SETTINGS_CHANGE_MESSAGE_LIMIT);
    }

    @Test
    public void processMiddleInput_with_text_should_save_current_input_and_set_next_state_and_send_text() {
        abstractBotCommandExecutor.updateInfo = updateInfo;
        abstractBotCommandExecutor.botInnerService = botInnerService;

        abstractBotCommandExecutor.processMiddleInput(InputState.ITEM_FILTER_ITEM_NAME_PATTERNS, "question");

        verify(botInnerService).saveUserInput(updateInfo);
        verify(botInnerService).setUserInputState(updateInfo.getChatId(), InputState.ITEM_FILTER_ITEM_NAME_PATTERNS);
        verify(abstractBotCommandExecutor).sendText("question");
    }

    @Test
    public void processMiddleInput_should_save_current_input_and_set_next_state() {
        abstractBotCommandExecutor.updateInfo = updateInfo;
        abstractBotCommandExecutor.botInnerService = botInnerService;

        abstractBotCommandExecutor.processMiddleInput(InputState.ITEM_FILTER_ITEM_NAME_PATTERNS);

        verify(botInnerService).saveUserInput(updateInfo);
        verify(botInnerService).setUserInputState(updateInfo.getChatId(), InputState.ITEM_FILTER_ITEM_NAME_PATTERNS);
    }

    @Test
    public void processLastInput_with_text_should_save_current_input_and_set_next_state_and_set_group_and_send_text() {
        abstractBotCommandExecutor.updateInfo = updateInfo;
        abstractBotCommandExecutor.botInnerService = botInnerService;

        abstractBotCommandExecutor.processLastInput("text");

        verify(botInnerService).saveUserInput(updateInfo);
        verify(botInnerService).setUserInputState(updateInfo.getChatId(), InputState.BASE);
        verify(botInnerService).setUserInputGroup(updateInfo.getChatId(), InputGroup.BASE);
        verify(abstractBotCommandExecutor).sendText("text");
    }

    @Test
    public void processLastInput_should_save_current_input_and_set_next_state_and_set_group() {
        abstractBotCommandExecutor.updateInfo = updateInfo;
        abstractBotCommandExecutor.botInnerService = botInnerService;

        abstractBotCommandExecutor.processLastInput();

        verify(botInnerService).saveUserInput(updateInfo);
        verify(botInnerService).setUserInputState(updateInfo.getChatId(), InputState.BASE);
        verify(botInnerService).setUserInputGroup(updateInfo.getChatId(), InputGroup.BASE);
    }

    @Test
    public void getUserCurrentInput_should_return_message_text_if_has_message() {
        abstractBotCommandExecutor.updateInfo = updateInfo;
        abstractBotCommandExecutor.botInnerService = botInnerService;
        when(updateInfo.isHasMessage()).thenReturn(true);
        when(updateInfo.getMessageText()).thenReturn("message");

        String result = abstractBotCommandExecutor.getUserCurrentInput();

        assertEquals("message", result);
    }

    @Test
    public void getUserCurrentInput_should_return_callback_query_data_if_has_callback_query() {
        abstractBotCommandExecutor.updateInfo = updateInfo;
        abstractBotCommandExecutor.botInnerService = botInnerService;
        when(updateInfo.isHasMessage()).thenReturn(false);
        when(updateInfo.isHasCallBackQuery()).thenReturn(true);
        when(updateInfo.getCallbackQueryData()).thenReturn("callback");

        String result = abstractBotCommandExecutor.getUserCurrentInput();

        assertEquals("callback", result);
    }

    @Test
    public void getUserCurrentInput_should_throw_exception_if_has_no_message_or_callback_query() {
        abstractBotCommandExecutor.updateInfo = updateInfo;
        abstractBotCommandExecutor.botInnerService = botInnerService;
        when(updateInfo.isHasMessage()).thenReturn(false);
        when(updateInfo.isHasCallBackQuery()).thenReturn(false);

        assertThrows(IllegalStateException.class, () -> abstractBotCommandExecutor.getUserCurrentInput());
    }

    @Test
    public void askYesOrNoFromInlineKeyboard_should_ask_from_inline_keyboard_yes_or_no() {
        abstractBotCommandExecutor.updateInfo = updateInfo;
        abstractBotCommandExecutor.botInnerService = botInnerService;

        abstractBotCommandExecutor.askYesOrNoFromInlineKeyboard("question", "yes", "no");

        verify(abstractBotCommandExecutor).askFromInlineKeyboard("question", 2, new CallbackButton("Yes", "yes"), new CallbackButton("No", "no"));
    }

    @Test
    public void askFromInlineKeyboardOrSkip_should_ask_from_inline_keyboard_with_skip() {
        abstractBotCommandExecutor.updateInfo = updateInfo;
        abstractBotCommandExecutor.botInnerService = botInnerService;

        abstractBotCommandExecutor.askFromInlineKeyboardOrSkip("question", 3, new CallbackButton("Yes", "yes"));

        verify(abstractBotCommandExecutor).askFromInlineKeyboard("question", 3, new CallbackButton("Yes", "yes"), new CallbackButton("Skip",
                Callbacks.EMPTY));
    }

    @Test
    public void askFromInlineKeyboard_should_ask_from_inline_keyboard() {
        abstractBotCommandExecutor.updateInfo = updateInfo;
        abstractBotCommandExecutor.botInnerService = botInnerService;

        abstractBotCommandExecutor.askFromInlineKeyboard("question", 3, new CallbackButton("Yes", "yes"), new CallbackButton("No", "no"));

        verify(botInnerService).askFromInlineKeyboard(
                updateInfo,
                "question",
                3,
                new CallbackButton[]{new CallbackButton("Yes", "yes"), new CallbackButton("No", "no")});
    }

    @Test
    public void cancel_should_reset_inputs_and_send_text() {
        abstractBotCommandExecutor.updateInfo = updateInfo;
        abstractBotCommandExecutor.botInnerService = botInnerService;

        abstractBotCommandExecutor.cancel();

        verify(botInnerService).clearUserInputsAndSetInputStateAndGroup(updateInfo.getChatId());
        verify(botInnerService).setUserInputState(updateInfo.getChatId(), InputState.BASE);
        verify(botInnerService).setUserInputGroup(updateInfo.getChatId(), InputGroup.BASE);
        verify(botInnerService).sendText(same(updateInfo), any());
    }

    @Test
    public void silentCancel_should_reset_inputs_without_sending_text() {
        abstractBotCommandExecutor.updateInfo = updateInfo;
        abstractBotCommandExecutor.botInnerService = botInnerService;

        abstractBotCommandExecutor.silentCancel();

        verify(botInnerService).clearUserInputsAndSetInputStateAndGroup(updateInfo.getChatId());
        verify(botInnerService).setUserInputState(updateInfo.getChatId(), InputState.BASE);
        verify(botInnerService).setUserInputGroup(updateInfo.getChatId(), InputGroup.BASE);
        verify(botInnerService, times(0)).sendText(same(updateInfo), any());
    }

    @Test
    public void executeCommandOrAskToRegister_should_execute_command_if_registered() {
        abstractBotCommandExecutor.updateInfo = updateInfo;
        abstractBotCommandExecutor.botInnerService = botInnerService;

        when(botInnerService.isUserRegistered(any())).thenReturn(true);

        abstractBotCommandExecutor.executeCommandOrAskToRegister(() -> {
            updateInfo.setChatId(1L);
        });

        verify(updateInfo).setChatId(1L);
        verify(botInnerService).isUserRegistered(any());
    }

    @Test
    public void executeCommandOrAskToRegister_should_send_text_if_not_registered() {
        abstractBotCommandExecutor.updateInfo = updateInfo;
        abstractBotCommandExecutor.botInnerService = botInnerService;

        when(botInnerService.isUserRegistered(any())).thenReturn(false);

        abstractBotCommandExecutor.executeCommandOrAskToRegister(() -> {
            updateInfo.setChatId(1L);
        });

        verify(updateInfo, times(0)).setChatId(1L);
        verify(botInnerService).sendText(same(updateInfo), anyString());
        verify(botInnerService).isUserRegistered(any());
    }

    @Test
    public void isRegistered_should_return_service_result() {
        abstractBotCommandExecutor.updateInfo = updateInfo;
        abstractBotCommandExecutor.botInnerService = botInnerService;

        when(botInnerService.isUserRegistered(any())).thenReturn(true);
        assertTrue(abstractBotCommandExecutor.isRegistered());
        verify(botInnerService).isUserRegistered(any());

        when(botInnerService.isUserRegistered(any())).thenReturn(false);
        assertFalse(abstractBotCommandExecutor.isRegistered());
    }

    @Test
    public void sendText_should_send_text() {
        abstractBotCommandExecutor.updateInfo = updateInfo;
        abstractBotCommandExecutor.botInnerService = botInnerService;

        abstractBotCommandExecutor.sendText("text1");

        verify(botInnerService).sendText(updateInfo, "text1");
    }
}