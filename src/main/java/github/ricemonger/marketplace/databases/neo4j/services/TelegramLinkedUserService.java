package github.ricemonger.marketplace.databases.neo4j.services;

import github.ricemonger.marketplace.databases.neo4j.entities.TelegramInputValuesEntity;
import github.ricemonger.marketplace.databases.neo4j.entities.TelegramLinkedUserEntity;
import github.ricemonger.marketplace.databases.neo4j.entities.UbiUserEntity;
import github.ricemonger.marketplace.databases.neo4j.repositories.TelegramInputValuesRepository;
import github.ricemonger.marketplace.databases.neo4j.repositories.TelegramLinkedUserRepository;
import github.ricemonger.marketplace.databases.neo4j.repositories.UbiUserRepository;
import github.ricemonger.telegramBot.executors.InputGroup;
import github.ricemonger.telegramBot.executors.InputState;
import github.ricemonger.utils.exceptions.AesPasswordEncoder;
import github.ricemonger.utils.exceptions.TelegramUserAlreadyExistsException;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramLinkedUserService {

    private final TelegramLinkedUserRepository telegramLinkedUserRepository;

    private final UbiUserRepository ubiUserRepository;

    private final TelegramInputValuesRepository telegramInputValuesRepository;

    private final AesPasswordEncoder AESPasswordEncoder;

    public boolean isTelegramUserRegistered(Long chatId) {
        return telegramLinkedUserRepository.existsById(String.valueOf(chatId));
    }

    public void registerTelegramUser(Long chatId) throws TelegramUserAlreadyExistsException {
        if (telegramLinkedUserRepository.existsById(String.valueOf(chatId))) {
            throw new TelegramUserAlreadyExistsException();
        } else {
            telegramLinkedUserRepository.save(new TelegramLinkedUserEntity(String.valueOf(chatId)));
        }
    }

    public void setUserNextInputState(Long chatId, InputState inputState) throws TelegramUserDoesntExistException {
        TelegramLinkedUserEntity telegramLinkedUserEntity = getTelegramUserOrThrow(chatId);
        telegramLinkedUserEntity.setInputState(inputState);
        telegramLinkedUserRepository.save(telegramLinkedUserEntity);
    }

    public void setUserNextInputGroup(Long chatId, InputGroup inputGroup) throws TelegramUserDoesntExistException {

        TelegramLinkedUserEntity telegramLinkedUserEntity = getTelegramUserOrThrow(chatId);
        telegramLinkedUserEntity.setInputGroup(inputGroup);
        telegramLinkedUserRepository.save(telegramLinkedUserEntity);

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


    public void addCredentials(Long chatId, String email, String password) throws TelegramUserDoesntExistException {
        TelegramLinkedUserEntity telegramLinkedUserEntity = getTelegramUserOrThrow(chatId);
        List<UbiUserEntity> credentialsList = telegramLinkedUserEntity.getLinkedUbisoftAccounts();

        credentialsList.add(new UbiUserEntity(email, AESPasswordEncoder.getEncodedPassword(password)));

        telegramLinkedUserEntity.setLinkedUbisoftAccounts(credentialsList);

        telegramLinkedUserRepository.save(telegramLinkedUserEntity);
    }

    public void saveUserInput(Long chatId, InputState inputState, String userInput) throws TelegramUserDoesntExistException {

        TelegramLinkedUserEntity telegramLinkedUserEntity = getTelegramUserOrThrow(chatId);
        List<TelegramInputValuesEntity> inputValues = telegramLinkedUserEntity.getInputValues();

        for (TelegramInputValuesEntity value : inputValues) {
            if (value.getInputState().equals(inputState)) {
                value.setValue(userInput);
                log.info("Input value updated, instead of adding new one. For user-{} and state-{}", chatId, inputState);
                return;
            }
        }

        inputValues.add(new TelegramInputValuesEntity(inputState, userInput));

        telegramLinkedUserEntity.setInputValues(inputValues);
        telegramLinkedUserRepository.save(telegramLinkedUserEntity);
    }

    public void clearUserInputs(Long chatId) throws TelegramUserDoesntExistException {
        TelegramLinkedUserEntity telegramLinkedUserEntity = getTelegramUserOrThrow(chatId);

        telegramInputValuesRepository.deleteAllByOwnerChatId(String.valueOf(chatId));
    }

    public void removeCredentialsByUserInputs(Long chatId) throws TelegramUserDoesntExistException {
        TelegramLinkedUserEntity telegramLinkedUserEntity = getTelegramUserOrThrow(chatId);

        String emailToRemove = getInputValueByState(chatId, InputState.CREDENTIALS_FULL_OR_EMAIL);

        ubiUserRepository.deleteByLinkedTelegramUserChatIdAndEmail(String.valueOf(chatId), emailToRemove);
    }

    public void removeAllCredentials(Long chatId) throws TelegramUserDoesntExistException {
        TelegramLinkedUserEntity telegramLinkedUserEntity = getTelegramUserOrThrow(chatId);

        ubiUserRepository.deleteAllByLinkedTelegramUserChatId(String.valueOf(chatId));
    }

    public List<String> getCredentialsEmailsList(Long chatId) throws TelegramUserDoesntExistException {
        TelegramLinkedUserEntity telegramLinkedUserEntity = getTelegramUserOrThrow(chatId);

        List<UbiUserEntity> credentialsList = telegramLinkedUserEntity.getLinkedUbisoftAccounts();

        return credentialsList.stream()
                .map(UbiUserEntity::getEmail)
                .toList();
    }

    public List<String> getAllChatIdsForNotifiableUsers() {
        return telegramLinkedUserRepository.findAll().stream()
                .filter(TelegramLinkedUserEntity::isPublicNotificationsEnabledFlag)
                .map(TelegramLinkedUserEntity::getChatId)
                .toList();
    }

    public TelegramLinkedUserEntity getTelegramUser(Long chatId) throws TelegramUserDoesntExistException{
        return getTelegramUserOrThrow(chatId);
    }

    private String getInputValueByState(Long chatId, InputState inputState) throws TelegramUserDoesntExistException {
        TelegramLinkedUserEntity telegramLinkedUserEntity = getTelegramUserOrThrow(chatId);

        List<TelegramInputValuesEntity> inputValues = telegramLinkedUserEntity.getInputValues();

        for (TelegramInputValuesEntity value : inputValues) {
            if (value.getInputState().equals(inputState)) {
                return value.getValue();
            }
        }

        return null;
    }

    private TelegramLinkedUserEntity getTelegramUserOrThrow(Long chatId) {
        try {
            return telegramLinkedUserRepository.findById(String.valueOf(chatId)).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new TelegramUserDoesntExistException(e);
        }
    }
}
