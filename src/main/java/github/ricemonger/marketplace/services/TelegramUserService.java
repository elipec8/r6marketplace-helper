package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.TelegramUserDatabaseService;
import github.ricemonger.telegramBot.executors.InputGroup;
import github.ricemonger.telegramBot.executors.InputState;
import github.ricemonger.utils.dtos.TelegramUser;
import github.ricemonger.utils.dtos.UbiUser;
import github.ricemonger.utils.exceptions.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramUserService {

    private final TelegramUserDatabaseService telegramUserDatabaseService;

    private final UbiUserService ubiUserService;

    public boolean isTelegramUserRegistered(Long chatId) {
        return telegramUserDatabaseService.userExistsById(String.valueOf(chatId));
    }

    public void registerTelegramUser(Long chatId) throws TelegramUserAlreadyExistsException {
        if (telegramUserDatabaseService.userExistsById(String.valueOf(chatId))) {
            throw new TelegramUserAlreadyExistsException();
        } else {
            telegramUserDatabaseService.saveUser(new TelegramUser(chatId));
        }
    }

    public void setUserNextInputState(Long chatId, InputState inputState) throws TelegramUserDoesntExistException {
        TelegramUser telegramUser = getTelegramUserOrThrow(chatId);
        telegramUser.setInputState(inputState);
        telegramUserDatabaseService.saveUser(telegramUser);
    }

    public void setUserNextInputGroup(Long chatId, InputGroup inputGroup) throws TelegramUserDoesntExistException {
        TelegramUser telegramUser = getTelegramUserOrThrow(chatId);
        telegramUser.setInputGroup(inputGroup);
        telegramUserDatabaseService.saveUser(telegramUser);
    }

    public InputState getUserInputState(Long chatId) throws TelegramUserDoesntExistException {
        TelegramUser telegramUser = getTelegramUserOrThrow(chatId);
        return telegramUser.getInputState();
    }

    public InputGroup getUserInputGroup(Long chatId) throws TelegramUserDoesntExistException {
        TelegramUser telegramUser = getTelegramUserOrThrow(chatId);
        return telegramUser.getInputGroup();
    }

    public String getUserInputByState(Long chatId, InputState inputState) throws TelegramUserDoesntExistException, TelegramUserInputDoesntExistException {
        return getInputValueByState(chatId, inputState);
    }

    public void saveUserInput(Long chatId, InputState inputState, String userInput) throws TelegramUserDoesntExistException {
        getTelegramUserOrThrow(chatId);
        telegramUserDatabaseService.saveInput(String.valueOf(chatId), inputState, userInput);
    }

    @Transactional
    public void clearUserInputs(Long chatId) throws TelegramUserDoesntExistException {
        getTelegramUserOrThrow(chatId);

        telegramUserDatabaseService.deleteAllInputsByChatId(String.valueOf(chatId));
    }

    public void addCredentialsIfValidOrThrowException(Long chatId, String email, String password)
            throws TelegramUserDoesntExistException,
            UbiUserAuthorizationClientErrorException,
            UbiUserAuthorizationServerErrorException {
        getTelegramUserOrThrow(chatId);

        ubiUserService.authorizeAndSaveUser(String.valueOf(chatId), email, password);
    }

    public void removeCredentialsByUserInputs(Long chatId) throws TelegramUserDoesntExistException {
        getTelegramUserOrThrow(chatId);

        String emailToRemove = getInputValueByState(chatId, InputState.CREDENTIALS_FULL_OR_EMAIL);

        ubiUserService.deleteByLinkedTelegramUserChatIdAndEmail(String.valueOf(chatId), emailToRemove);

        telegramUserDatabaseService.deleteAllInputsByChatId(String.valueOf(chatId));
    }

    public void removeAllCredentials(Long chatId) throws TelegramUserDoesntExistException {
        getTelegramUserOrThrow(chatId);

        ubiUserService.deleteAllByLinkedTelegramUserChatId(String.valueOf(chatId));
    }

    public List<String> getCredentialsEmailsList(Long chatId) throws TelegramUserDoesntExistException {
        getTelegramUserOrThrow(chatId);

        Collection<UbiUser> credentialsList = ubiUserService.findAllByLinkedTelegramUserChatId(String.valueOf(chatId));

        return credentialsList.stream()
                .map(UbiUser::getEmail)
                .toList();
    }

    public List<String> getAllChatIdsForNotifiableUsers() {
        return telegramUserDatabaseService.findAllUsers().stream()
                .filter(TelegramUser::isPublicNotificationsEnabledFlag)
                .map(TelegramUser::getChatId)
                .toList();
    }

    public TelegramUser getTelegramUser(Long chatId) throws TelegramUserDoesntExistException {
        return getTelegramUserOrThrow(chatId);
    }

    private String getInputValueByState(Long chatId, InputState inputState) throws TelegramUserDoesntExistException, TelegramUserInputDoesntExistException {
        getTelegramUserOrThrow(chatId);

        return telegramUserDatabaseService.findInputById(String.valueOf(chatId), inputState).getValue();
    }

    private TelegramUser getTelegramUserOrThrow(Long chatId) throws TelegramUserDoesntExistException {
        return telegramUserDatabaseService.findUserById(String.valueOf(chatId));
    }
}
