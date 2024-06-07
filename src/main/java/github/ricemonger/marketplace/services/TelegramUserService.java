package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.TelegramUserDatabaseService;
import github.ricemonger.marketplace.services.abstractions.TelegramUserInputDatabaseService;
import github.ricemonger.telegramBot.executors.InputGroup;
import github.ricemonger.telegramBot.executors.InputState;
import github.ricemonger.utils.dtos.*;
import github.ricemonger.utils.enums.FilterType;
import github.ricemonger.utils.enums.ItemType;
import github.ricemonger.utils.exceptions.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramUserService {

    private final TelegramUserDatabaseService userService;

    private final TelegramUserInputDatabaseService inputService;

    private final UbiUserService credentialsService;

    public boolean isTelegramUserRegistered(Long chatId) {
        return userService.userExistsById(String.valueOf(chatId));
    }

    public void registerTelegramUser(Long chatId) throws TelegramUserAlreadyExistsException {
        if (userService.userExistsById(String.valueOf(chatId))) {
            throw new TelegramUserAlreadyExistsException();
        } else {
            userService.saveUser(new TelegramUser(chatId));
        }
    }

    public void setUserNextInputState(Long chatId, InputState inputState) throws TelegramUserDoesntExistException {
        TelegramUser telegramUser = getTelegramUserOrThrow(chatId);
        telegramUser.setInputState(inputState);
        userService.saveUser(telegramUser);
    }

    public void setUserNextInputGroup(Long chatId, InputGroup inputGroup) throws TelegramUserDoesntExistException {
        TelegramUser telegramUser = getTelegramUserOrThrow(chatId);
        telegramUser.setInputGroup(inputGroup);
        userService.saveUser(telegramUser);
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
        inputService.saveInput(String.valueOf(chatId), inputState, userInput);
    }

    public Collection<TelegramUserInput> getAllUserInputs(Long chatId) throws TelegramUserDoesntExistException {
        getTelegramUserOrThrow(chatId);
        return inputService.findAllInputsByChatId(String.valueOf(chatId));
    }

    @Transactional
    public void clearUserInputs(Long chatId) throws TelegramUserDoesntExistException {
        getTelegramUserOrThrow(chatId);

        inputService.deleteAllInputsByChatId(String.valueOf(chatId));
    }

    public void addCredentialsIfValidOrThrowException(Long chatId, String email, String password)
            throws TelegramUserDoesntExistException,
            UbiUserAuthorizationClientErrorException,
            UbiUserAuthorizationServerErrorException {
        getTelegramUserOrThrow(chatId);

        credentialsService.authorizeAndSaveUser(String.valueOf(chatId), email, password);
    }

    public void removeCredentialsByUserInputs(Long chatId) throws TelegramUserDoesntExistException {
        getTelegramUserOrThrow(chatId);

        String emailToRemove = getInputValueByState(chatId, InputState.CREDENTIALS_FULL_OR_EMAIL);

        credentialsService.deleteByLinkedTelegramUserChatIdAndEmail(String.valueOf(chatId), emailToRemove);

        inputService.deleteAllInputsByChatId(String.valueOf(chatId));
    }

    public void removeAllCredentials(Long chatId) throws TelegramUserDoesntExistException {
        getTelegramUserOrThrow(chatId);

        credentialsService.deleteAllByLinkedTelegramUserChatId(String.valueOf(chatId));
    }

    public List<String> getCredentialsEmailsList(Long chatId) throws TelegramUserDoesntExistException {
        getTelegramUserOrThrow(chatId);

        Collection<UbiUser> credentialsList = credentialsService.findAllByLinkedTelegramUserChatId(String.valueOf(chatId));

        return credentialsList.stream()
                .map(UbiUser::getEmail)
                .toList();
    }

    public List<String> getAllChatIdsForNotifiableUsers() {
        return userService.findAllUsers().stream()
                .filter(TelegramUser::isPublicNotificationsEnabledFlag)
                .map(TelegramUser::getChatId)
                .toList();
    }

    public TelegramUser getTelegramUser(Long chatId) throws TelegramUserDoesntExistException {
        return getTelegramUserOrThrow(chatId);
    }

    private String getInputValueByState(Long chatId, InputState inputState) throws TelegramUserDoesntExistException, TelegramUserInputDoesntExistException {
        getTelegramUserOrThrow(chatId);

        return inputService.findInputById(String.valueOf(chatId), inputState).getValue();
    }

    private TelegramUser getTelegramUserOrThrow(Long chatId) throws TelegramUserDoesntExistException {
        return userService.findUserById(String.valueOf(chatId));
    }
}
