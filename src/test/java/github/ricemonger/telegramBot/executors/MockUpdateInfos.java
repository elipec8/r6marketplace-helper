package github.ricemonger.telegramBot.executors;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.client.CallbackButton;

public class MockUpdateInfos {

    public static final UpdateInfo UPDATE_INFO = new UpdateInfo();

    static {
        UPDATE_INFO.setUpdateId(1);
        UPDATE_INFO.setChatId(1L);
        UPDATE_INFO.setHasMessage(true);
        UPDATE_INFO.setMessageText("text");
        UPDATE_INFO.setHasCallBackQuery(true);
        UPDATE_INFO.setCallbackQueryData("data");
        UPDATE_INFO.setInputState(InputState.BASE);
        UPDATE_INFO.setInputGroup(InputGroup.BASE);
    }

    public static final UpdateInfo UPDATE_INFO_FULL_INPUT = new UpdateInfo();

    static {
        UPDATE_INFO_FULL_INPUT.setUpdateId(1);
        UPDATE_INFO_FULL_INPUT.setChatId(1L);
        UPDATE_INFO_FULL_INPUT.setHasMessage(true);
        UPDATE_INFO_FULL_INPUT.setMessageText("email:password");
        UPDATE_INFO_FULL_INPUT.setHasCallBackQuery(true);
        UPDATE_INFO_FULL_INPUT.setCallbackQueryData("data");
        UPDATE_INFO_FULL_INPUT.setInputState(InputState.CREDENTIALS_FULL_OR_EMAIL);
        UPDATE_INFO_FULL_INPUT.setInputGroup(InputGroup.CREDENTIALS_ADD);
    }

    public static final UpdateInfo UPDATE_INFO_EMAIL_INPUT = new UpdateInfo();

    static {
        UPDATE_INFO_EMAIL_INPUT.setUpdateId(1);
        UPDATE_INFO_EMAIL_INPUT.setChatId(1L);
        UPDATE_INFO_EMAIL_INPUT.setHasMessage(true);
        UPDATE_INFO_EMAIL_INPUT.setMessageText("email");
        UPDATE_INFO_EMAIL_INPUT.setHasCallBackQuery(true);
        UPDATE_INFO_EMAIL_INPUT.setCallbackQueryData("data");
        UPDATE_INFO_EMAIL_INPUT.setInputState(InputState.CREDENTIALS_FULL_OR_EMAIL);
        UPDATE_INFO_EMAIL_INPUT.setInputGroup(InputGroup.CREDENTIALS_ADD);
    }

    public static final UpdateInfo UPDATE_INFO_PASSWORD_INPUT = new UpdateInfo();

    static {
        UPDATE_INFO_PASSWORD_INPUT.setUpdateId(1);
        UPDATE_INFO_PASSWORD_INPUT.setChatId(1L);
        UPDATE_INFO_PASSWORD_INPUT.setHasMessage(true);
        UPDATE_INFO_PASSWORD_INPUT.setMessageText("password");
        UPDATE_INFO_PASSWORD_INPUT.setHasCallBackQuery(true);
        UPDATE_INFO_PASSWORD_INPUT.setCallbackQueryData("data");
        UPDATE_INFO_PASSWORD_INPUT.setInputState(InputState.CREDENTIALS_PASSWORD);
        UPDATE_INFO_PASSWORD_INPUT.setInputGroup(InputGroup.CREDENTIALS_ADD);
    }

    public static final CallbackButton[] SKIP_CALLBACK_BUTTON_SINGLE_ARRAY = new CallbackButton[]{new CallbackButton("Skip", Callbacks.EMPTY)};
}
