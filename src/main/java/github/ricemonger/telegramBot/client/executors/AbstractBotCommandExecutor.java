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

    protected final void processFirstInput(UpdateInfo updateInfo, InputState nextState,InputGroup inputGroup, String question) {
        botService.setUserNextInputState(updateInfo.getChatId(), nextState);
        botService.setUserNextInputGroup(updateInfo.getChatId(), inputGroup);

        sendText(question);
    }
    protected final void processMiddleInput(UpdateInfo updateInfo, InputState nextState, String question) {
        saveCurrentInputAndSetNextState(updateInfo, nextState);

        sendText(question);
    }

    protected final void processLastInput(UpdateInfo updateInfo, String text) {
        saveCurrentInputAndSetNextState(updateInfo, InputState.BASE);
        botService.setUserNextInputGroup(updateInfo.getChatId(), InputGroup.BASE);
        sendText(text);
    }

    protected final void saveCurrentInputAndSetNextState(UpdateInfo updateInfo, InputState nextState) {
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

    protected final boolean isRegistered(Long chatId) {
        return botService.isRegistered(chatId);
    }

    protected final void sendText(String answer) {
        botService.sendText(updateInfo, answer);
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
