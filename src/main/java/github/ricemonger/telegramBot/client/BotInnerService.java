package github.ricemonger.telegramBot.client;

import github.ricemonger.marketplace.databases.neo4j.services.TelegramLinkedUserService;
import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.executors.InputGroup;
import github.ricemonger.telegramBot.executors.InputState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BotInnerService {

    private final TelegramBotClientService telegramBotClientService;

    private final TelegramLinkedUserService telegramLinkedUserService;

    public void askFromInlineKeyboard(UpdateInfo updateInfo, String text, int buttonsInLine, CallbackButton[] buttons) {
        telegramBotClientService.askFromInlineKeyboard(updateInfo, text, buttonsInLine, buttons);
    }

    public void sendText(UpdateInfo updateInfo, String answer) {
        telegramBotClientService.sendText(updateInfo, answer);
    }

    public boolean isRegistered(Long chatId) {
        return telegramLinkedUserService.isTelegramUserRegistered(chatId);
    }

    public void registerUser(Long chatId) {
        telegramLinkedUserService.registerTelegramUser(chatId);
    }

    public void addCredentialsFromUserInputs(Long chatId) {
        String fullOrEmail = getUserInputByState(chatId, InputState.CREDENTIALS_FULL_OR_EMAIL);

        if (fullOrEmail.contains(":")) {
            String email = fullOrEmail.substring(0, fullOrEmail.indexOf(":"));
            String password = fullOrEmail.substring(fullOrEmail.indexOf(":") + 1);
            telegramLinkedUserService.addCredentials(chatId, email, password);
        }
        else{
            String password = getUserInputByState(chatId, InputState.CREDENTIALS_PASSWORD);
            telegramLinkedUserService.addCredentials(chatId, fullOrEmail, password);
        }

        clearUserInputs(chatId);
    }

    public void removeCredentialsByUserInputs(Long chatId) {
        telegramLinkedUserService.removeCredentialsByUserInputs(chatId);

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
        telegramLinkedUserService.saveUserInput(updateInfo.getChatId(),updateInfo.getInputState(), userInput);
    }

    public void clearUserInputs(Long chatId) {
        telegramLinkedUserService.clearUserInputs(chatId);
    }

    public void setUserNextInputState(Long chatId, InputState inputState) {
        telegramLinkedUserService.setUserNextInputState(chatId, inputState);
    }

    public void setUserNextInputGroup(Long chatId, InputGroup inputGroup) {
        telegramLinkedUserService.setUserNextInputGroup(chatId, inputGroup);
    }

    public String getUserInputByState(Long chatId, InputState inputState){
        return telegramLinkedUserService.getUserInputByStateOrNull(chatId, inputState);
    }

    public void removeUserAllCredentials(Long chatId) {
        telegramLinkedUserService.removeAllCredentials(chatId);
    }

    public List<String> getCredentialsEmailsList(Long chatId) {
        return telegramLinkedUserService.getCredentialsEmailsList(chatId);
    }
}
