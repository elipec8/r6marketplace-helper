package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.TelegramInputValueEntity;
import github.ricemonger.marketplace.databases.postgres.entities.TelegramInputValueEntityId;
import github.ricemonger.marketplace.databases.postgres.entities.TelegramLinkedUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.UbiUserEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramInputValueEntityRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramLinkedUserEntityRepository;
import github.ricemonger.telegramBot.executors.InputGroup;
import github.ricemonger.telegramBot.executors.InputState;
import github.ricemonger.utils.exceptions.TelegramUserAlreadyExistsException;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.UbiUserAuthorizationClientErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramUserService {

    private final TelegramLinkedUserEntityRepository telegramUserRepository;

    private final TelegramInputValueEntityRepository telegramInputValuesRepository;

    private final UbiUserService ubiUserService;

    public boolean isTelegramUserRegistered(Long chatId) {
        return telegramUserRepository.existsById(String.valueOf(chatId));
    }

    public void registerTelegramUser(Long chatId) throws TelegramUserAlreadyExistsException {
        if (telegramUserRepository.existsById(String.valueOf(chatId))) {
            throw new TelegramUserAlreadyExistsException();
        } else {
            telegramUserRepository.save(new TelegramLinkedUserEntity(String.valueOf(chatId)));
        }
    }

    public void setUserNextInputState(Long chatId, InputState inputState) throws TelegramUserDoesntExistException {
        TelegramLinkedUserEntity telegramLinkedUserEntity = getTelegramUserOrThrow(chatId);
        telegramLinkedUserEntity.setInputState(inputState);
        telegramUserRepository.save(telegramLinkedUserEntity);
    }

    public void setUserNextInputGroup(Long chatId, InputGroup inputGroup) throws TelegramUserDoesntExistException {

        TelegramLinkedUserEntity telegramLinkedUserEntity = getTelegramUserOrThrow(chatId);
        telegramLinkedUserEntity.setInputGroup(inputGroup);
        telegramUserRepository.save(telegramLinkedUserEntity);

    }

    public InputState getUserInputState(Long chatId) throws TelegramUserDoesntExistException {

        TelegramLinkedUserEntity telegramLinkedUserEntity = getTelegramUserOrThrow(chatId);

        return telegramLinkedUserEntity.getInputState();

    }

    public InputGroup getUserInputGroup(Long chatId) throws TelegramUserDoesntExistException {
        TelegramLinkedUserEntity telegramLinkedUserEntity = getTelegramUserOrThrow(chatId);

        return telegramLinkedUserEntity.getInputGroup();
    }

    public String getUserInputByStateOrNull(Long chatId, InputState inputState) throws TelegramUserDoesntExistException {
        return getInputValueByState(chatId, inputState);
    }

    public void saveUserInput(Long chatId, InputState inputState, String userInput) throws TelegramUserDoesntExistException {

        getTelegramUserOrThrow(chatId);

        telegramInputValuesRepository.save(new TelegramInputValueEntity(String.valueOf(chatId), inputState, userInput));
    }

    public void clearUserInputs(Long chatId) throws TelegramUserDoesntExistException {
        getTelegramUserOrThrow(chatId);

        telegramInputValuesRepository.deleteAllByChatId(String.valueOf(chatId));
    }

    public void addCredentialsIfValidOrThrowException(Long chatId, String email, String password) throws TelegramUserDoesntExistException, UbiUserAuthorizationClientErrorException {
        getTelegramUserOrThrow(chatId);

        ubiUserService.createAndAuthorizeOrThrowForTelegramUser(String.valueOf(chatId), email, password);
    }

    public void removeCredentialsByUserInputs(Long chatId) throws TelegramUserDoesntExistException {
        getTelegramUserOrThrow(chatId);

        String emailToRemove = getInputValueByState(chatId, InputState.CREDENTIALS_FULL_OR_EMAIL);

        ubiUserService.deleteByLinkedTelegramUserChatIdAndEmail(String.valueOf(chatId), emailToRemove);

        telegramInputValuesRepository.deleteAllByChatId(String.valueOf(chatId));
    }

    public void removeAllCredentials(Long chatId) throws TelegramUserDoesntExistException {
        getTelegramUserOrThrow(chatId);

        ubiUserService.deleteAllByLinkedTelegramUserChatId(String.valueOf(chatId));
    }

    public List<String> getCredentialsEmailsList(Long chatId) throws TelegramUserDoesntExistException {
        getTelegramUserOrThrow(chatId);

        List<UbiUserEntity> credentialsList = ubiUserService.findAllByLinkedTelegramUserChatId(String.valueOf(chatId));

        return credentialsList.stream()
                .map(UbiUserEntity::getEmail)
                .toList();
    }

    public List<String> getAllChatIdsForNotifiableUsers() {
        return telegramUserRepository.findAll().stream()
                .filter(TelegramLinkedUserEntity::isPublicNotificationsEnabledFlag)
                .map(TelegramLinkedUserEntity::getChatId)
                .toList();
    }

    public TelegramLinkedUserEntity getTelegramUser(Long chatId) throws TelegramUserDoesntExistException {
        return getTelegramUserOrThrow(chatId);
    }

    private String getInputValueByState(Long chatId, InputState inputState) throws TelegramUserDoesntExistException {
        getTelegramUserOrThrow(chatId);

        return telegramInputValuesRepository.findById(new TelegramInputValueEntityId(String.valueOf(chatId), inputState))
                .map(TelegramInputValueEntity::getValue)
                .orElse(null);
    }

    private TelegramLinkedUserEntity getTelegramUserOrThrow(Long chatId) {
        try {
            return telegramUserRepository.findById(String.valueOf(chatId)).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new TelegramUserDoesntExistException(e);
        }
    }
}
