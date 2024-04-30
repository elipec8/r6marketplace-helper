package github.ricemonger.telegramBot.client;

import github.ricemonger.marketplace.graphs.database.neo4j.services.UserService;
import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.client.executors.InputGroup;
import github.ricemonger.telegramBot.client.executors.InputState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BotService {

    private final TelegramBotClientService telegramBotClientService;

    private final UserService userService;

    public void askFromInlineKeyboard(UpdateInfo updateInfo, String text, int buttonsInLine, CallbackButton[] buttons) {
        telegramBotClientService.askFromInlineKeyboard(updateInfo, text, buttonsInLine, buttons);
    }

    public void sendText(UpdateInfo updateInfo, String answer) {
        telegramBotClientService.sendText(updateInfo, answer);
    }



    public boolean isRegistered(Long chatId) {
        return userService.isTelegramUserRegistered(chatId);
    }

    public void registerUser(Long chatId) {
        userService.registerTelegramUser(chatId);
    }

    public void addCredentialsFromUserInputs(Long chatId) {
        String fullOrEmail = getUserInputByState(chatId, InputState.CREDENTIALS_FULL_OR_EMAIL);

        if (fullOrEmail.contains(":")) {
            String email = fullOrEmail.substring(0, fullOrEmail.indexOf(":"));
            String password = fullOrEmail.substring(fullOrEmail.indexOf(":") + 1);
            userService.addCredentials(chatId, email, password);
        }
        else{
            String password = getUserInputByState(chatId, InputState.CREDENTIALS_PASSWORD);
            userService.addCredentials(chatId, fullOrEmail, password);
        }

        clearUserInputs(chatId);
    }

    public void saveUserInputOrThrow(UpdateInfo updateInfo) {
        String userInput;

        if (updateInfo.isHasMessage()) {
            userInput = updateInfo.getMessageText();
        } else if (updateInfo.isHasCallBackQuery()) {
            userInput = updateInfo.getCallbackQueryData();
        } else {
            throw new IllegalStateException("UpdateInfo has no message or callback query");
        }
        userService.saveUserInput(updateInfo.getChatId(),updateInfo.getInputState(), userInput);
    }

    public void clearUserInputs(Long chatId) {
        userService.clearUserInputs(chatId);
    }

    public void setUserNextInputState(Long chatId, InputState inputState) {
        userService.setUserNextInput(chatId, inputState);
    }

    public void setUserNextInputGroup(Long chatId, InputGroup inputGroup) {
        userService.setUserNextInputGroup(chatId, inputGroup);
    }

    public String getUserInputByState(Long chatId, InputState inputState){
        return userService.getUserInputByState(chatId, inputState);
    }

    public void removeCredentialsByUserInputs(Long chatId) {
        userService.removeCredentialsByUserInputs(chatId);
    }

    public void removeUserAllCredentials(Long chatId) {
        userService.removeAllCredentials(chatId);
    }

    public List<String> getCredentialsEmailsList(Long chatId) {
        return userService.getCredentialsEmailsList(chatId);
    }
}
