package github.ricemonger.telegramBot.update_consumer.executors;

import github.ricemonger.telegramBot.CallbackButton;
import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;

import java.util.Arrays;

public abstract class AbstractBotCommandExecutor {

    protected UpdateInfo updateInfo;

    protected BotInnerService botInnerService;

    public final void initAndExecute(UpdateInfo updateInfo, BotInnerService botInnerService) {
        this.updateInfo = updateInfo;
        this.botInnerService = botInnerService;
        executeCommand();
    }

    protected abstract void executeCommand();

    protected final void processFirstInput(InputState nextInputState, InputGroup nextInputGroup, String text) {
        processFirstInput(nextInputState, nextInputGroup);

        sendText(text);
    }

    protected final void processFirstInput(InputState nextInputState, InputGroup nextInputGroup) {
        botInnerService.clearUserInputsAndSetInputStateAndGroup(updateInfo.getChatId(), nextInputState, nextInputGroup);
    }

    protected final void processMiddleInput(InputState nextInputState, String text) {
        processMiddleInput(nextInputState);

        sendText(text);
    }

    protected final void processMiddleInput(InputState nextInputState) {
        botInnerService.saveUserInput(updateInfo);
        botInnerService.setUserInputState(updateInfo.getChatId(), nextInputState);
    }

    protected final void processLastInput(String text) {
        processLastInput();
        sendText(text);
    }

    protected final void processLastInput() {
        botInnerService.saveUserInput(updateInfo);
        botInnerService.setUserInputStateAndGroup(updateInfo.getChatId(), InputState.BASE, InputGroup.BASE);
    }

    protected final String getUserCurrentInput() {
        String userInput;

        if (updateInfo.isHasMessage()) {
            userInput = updateInfo.getMessageText();
        } else if (updateInfo.isHasCallBackQuery()) {
            userInput = updateInfo.getCallbackQueryData();
        } else {
            throw new IllegalStateException("UpdateInfo has no message or callback query");
        }

        return userInput;
    }

    protected final void askYesOrNoFromInlineKeyboard(String text, String yesCallbackData,
                                                      String noCallbackData) {
        CallbackButton yesButton = new CallbackButton("Yes", yesCallbackData);
        CallbackButton noButton = new CallbackButton("No", noCallbackData);
        askFromInlineKeyboard(text, 2, yesButton, noButton);
    }

    protected final void askFromInlineKeyboardOrSkip(String text, int buttonsInLine, CallbackButton... buttons) {
        CallbackButton skipButton = new CallbackButton("Skip", Callbacks.EMPTY);
        CallbackButton[] buttonsAndCancelButton = Arrays.copyOf(buttons, buttons.length + 1);
        buttonsAndCancelButton[buttons.length] = skipButton;

        askFromInlineKeyboard(text, buttonsInLine, buttonsAndCancelButton);
    }

    protected final void askFromInlineKeyboard(String text, int buttonsInLine, CallbackButton... buttons) {
        botInnerService.askFromInlineKeyboard(updateInfo, text, buttonsInLine, buttons);
    }

    protected final void cancel() {
        silentCancel();
        sendText("Operation cancelled.");
    }

    protected final void silentCancel() {
        botInnerService.clearUserInputsAndSetInputStateAndGroup(updateInfo.getChatId(), InputState.BASE, InputGroup.BASE);
    }

    protected final void executeCommandOrAskToRegister(MyFunctionalInterface command) {
        if (isRegistered()) {
            command.executeCommand();
        } else {
            sendText("You are not registered. Please use /start to register.");
        }
    }

    protected final boolean isRegistered() {
        return botInnerService.isUserRegistered(updateInfo.getChatId());
    }

    protected final void sendText(String answer) {
        botInnerService.sendText(updateInfo, answer);
    }

    @Override
    public String toString() {
        return String.format("%s(updateInfo=%s,botService=%s)", this.getClass().getSimpleName(), updateInfo, botInnerService);
    }

    @FunctionalInterface
    protected interface MyFunctionalInterface {
        void executeCommand();
    }
}
