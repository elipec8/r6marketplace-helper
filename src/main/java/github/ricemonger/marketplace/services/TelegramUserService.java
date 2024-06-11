package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.TelegramUserDatabaseService;
import github.ricemonger.marketplace.services.abstractions.TelegramUserInputDatabaseService;
import github.ricemonger.telegramBot.client.Callbacks;
import github.ricemonger.telegramBot.executors.InputGroup;
import github.ricemonger.telegramBot.executors.InputState;
import github.ricemonger.utils.dtos.*;
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

    public ItemShowSettings getItemShowSettings(Long chatId) {
        return userService.findUserSettingsById(String.valueOf(chatId));
    }

    public int getItemOffsetByUserInput(Long chatId) {
        try {
            return Integer.parseInt(inputService.findInputById(String.valueOf(chatId), InputState.ITEMS_SHOW_OFFSET).getValue());
        } catch (TelegramUserInputDoesntExistException | NumberFormatException e) {
            return 0;
        }
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

    public void setItemShowFewItemsInMessageFlag(Long chatId, boolean flag) {
        userService.setItemShowFewItemsInMessageFlag(String.valueOf(chatId), flag);
    }

    public void setItemShowMessagesLimit(Long chatId, Integer limit) {
        userService.setItemShowMessagesLimit(String.valueOf(chatId), limit);
    }

    public void setItemShowSettingsByUserInput(Long chatId, String trueValue, String falseValue) {
        Boolean nameFlag = Boolean.parseBoolean(getInputValueByState(chatId, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_NAME));

        Boolean itemTypeFlag = parseShowSettingsOrTrue(getInputValueByState(chatId, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_ITEM_TYPE), trueValue, falseValue);
        Boolean maxBuyPriceFlag = parseShowSettingsOrTrue(getInputValueByState(chatId, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_MAX_BUY_PRICE), trueValue, falseValue);
        Boolean buyOrdersCountFlag = parseShowSettingsOrTrue(getInputValueByState(chatId, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_BUY_ORDERS_COUNT), trueValue, falseValue);
        Boolean minSellPriceFlag = parseShowSettingsOrTrue(getInputValueByState(chatId, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_MIN_SELL_PRICE), trueValue, falseValue);
        Boolean sellOrdersCountFlag = parseShowSettingsOrTrue(getInputValueByState(chatId, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_SELL_ORDERS_COUNT), trueValue, falseValue);
        Boolean pictureFlag = parseShowSettingsOrTrue(getInputValueByState(chatId, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_PICTURE), trueValue, falseValue);

        ItemShownFieldsSettings settings = new ItemShownFieldsSettings(
                nameFlag,
                itemTypeFlag,
                maxBuyPriceFlag,
                buyOrdersCountFlag,
                minSellPriceFlag,
                sellOrdersCountFlag,
                pictureFlag);

        userService.setItemShowSettings(String.valueOf(chatId), settings);
    }

    private boolean parseShowSettingsOrTrue(String value, String trueValue, String falseValue) {
        return !falseValue.equalsIgnoreCase(value);
    }

    public void removeItemShowAppliedFilter(Long chatId, String filterName) {
        userService.removeItemShowAppliedFilter(String.valueOf(chatId), filterName);
    }

    public void addItemShowAppliedFilter(Long chatId, String filterName) {
        userService.addItemShowAppliedFilter(String.valueOf(chatId), filterName);
    }
}
