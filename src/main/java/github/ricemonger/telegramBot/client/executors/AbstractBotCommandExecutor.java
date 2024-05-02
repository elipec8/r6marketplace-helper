package github.ricemonger.telegramBot.client.executors;

import github.ricemonger.telegramBot.client.BotService;
import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.client.CallbackButton;
import github.ricemonger.telegramBot.client.Callbacks;

import java.util.Arrays;

public abstract class AbstractBotCommandExecutor {

    protected UpdateInfo updateInfo;

    protected BotService botService;

    public final void initAndExecute(UpdateInfo updateInfo, BotService botService) {
        this.updateInfo = updateInfo;
        this.botService = botService;
        executeCommand();
    }

    protected abstract void executeCommand();

    protected final void processFirstInput(InputState nextInputState, InputGroup nextInputGroup, String question) {
        botService.setUserNextInputState(updateInfo.getChatId(), nextInputState);
        botService.setUserNextInputGroup(updateInfo.getChatId(), nextInputGroup);

        sendText(question);
    }
    protected final void processMiddleInput(InputState nextInputState, String question) {
        saveCurrentInputAndSetNextState(nextInputState);

        sendText(question);
    }

    protected final void processLastInput(UpdateInfo updateInfo, String text) {
        saveCurrentInputAndSetNextState(InputState.BASE);
        botService.setUserNextInputGroup(updateInfo.getChatId(), InputGroup.BASE);
        sendText(text);
    }

    protected final void saveCurrentInputAndSetNextState(InputState nextState) {
        botService.saveUserInputOrThrow(updateInfo);
        botService.setUserNextInputState(updateInfo.getChatId(), nextState);
    }

    protected final String getUserCurrentInput(){
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

    protected final void askFromInlineKeyboardOrCancel(String text, int buttonsInLine, CallbackButton... buttons) {

        CallbackButton cancelButton = new CallbackButton("Cancel", Callbacks.CANCEL);

        CallbackButton[] buttonsAndCancelButton = Arrays.copyOf(buttons, buttons.length+1);
        buttonsAndCancelButton[buttons.length] = cancelButton;

        askFromInlineKeyboard(text, buttonsInLine, buttonsAndCancelButton);
    }

    protected final void askFromInlineKeyboard(String text, int buttonsInLine, CallbackButton... buttons) {
        botService.askFromInlineKeyboard(updateInfo, text, buttonsInLine, buttons);
    }

    protected final void cancel(){
        silentCancel();
        sendText("Operation cancelled.");
    }

    protected final void silentCancel(){
        botService.setUserNextInputState(updateInfo.getChatId(), InputState.BASE);

        botService.setUserNextInputGroup(updateInfo.getChatId(), InputGroup.BASE);

        botService.clearUserInputs(updateInfo.getChatId());
    }

    protected final boolean isRegistered() {
        return botService.isRegistered(updateInfo.getChatId());
    }

    protected final void sendText(String answer) {
        botService.sendText(updateInfo, answer);
    }

    protected final void executeCommandOrAskToRegister(MyFunctionalInterface command) {
        if (isRegistered()) {
            command.executeCommand();
        } else {
            sendText("You are not registered. Please use /start to register.");
        }
    }

    @Override
    public String toString() {
        return String.format("%s(updateInfo=%s,botService=%s)", this.getClass().getSimpleName(),updateInfo, botService);
    }

    @FunctionalInterface
    protected interface MyFunctionalInterface {
        void executeCommand();
    }
    
}
