package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.TelegramUserDatabaseService;
import github.ricemonger.marketplace.services.abstractions.TelegramUserInputDatabaseService;
import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.utils.DTOs.personal.*;
import github.ricemonger.utils.exceptions.client.TelegramUserAlreadyExistsException;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryDoesntExistException;
import github.ricemonger.utils.exceptions.client.UbiUserAuthorizationClientErrorException;
import github.ricemonger.utils.exceptions.server.TelegramUserInputDoesntExistException;
import github.ricemonger.utils.exceptions.server.UbiUserAuthorizationServerErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramUserService {

    private final TelegramUserUbiAccountEntryService credentialsService;

    private final TelegramUserDatabaseService telegramUserDatabaseService;

    private final TelegramUserInputDatabaseService inputDatabaseService;

    public void registerTelegramUser(Long chatId) throws TelegramUserAlreadyExistsException {
        telegramUserDatabaseService.create(String.valueOf(chatId));
    }

    public void setUserInputState(Long chatId, InputState inputState) throws TelegramUserDoesntExistException {
        TelegramUser telegramUser = getTelegramUserOrThrow(chatId);
        telegramUser.setInputState(inputState);
        telegramUserDatabaseService.update(telegramUser);
    }

    public void setUserInputGroup(Long chatId, InputGroup inputGroup) throws TelegramUserDoesntExistException {
        TelegramUser telegramUser = getTelegramUserOrThrow(chatId);
        telegramUser.setInputGroup(inputGroup);
        telegramUserDatabaseService.update(telegramUser);
    }

    public void setItemShowFewItemsInMessageFlag(Long chatId, boolean flag) throws TelegramUserDoesntExistException {
        telegramUserDatabaseService.setItemShowFewItemsInMessageFlag(String.valueOf(chatId), flag);
    }

    public void setItemShowMessagesLimit(Long chatId, Integer limit) throws TelegramUserDoesntExistException {
        telegramUserDatabaseService.setItemShowMessagesLimit(String.valueOf(chatId), limit);
    }

    public void setItemShownFieldsSettingsByUserInput(Long chatId, String trueValue, String falseValue) throws TelegramUserDoesntExistException, TelegramUserInputDoesntExistException {
        boolean nameFlag = parseBooleanOrTrue(getInputValueByState(chatId, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_ITEM_NAME), falseValue);
        boolean itemTypeFlag = parseBooleanOrTrue(getInputValueByState(chatId, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_ITEM_TYPE), falseValue);
        boolean maxBuyPriceFlag = parseBooleanOrTrue(getInputValueByState(chatId, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_MAX_BUY_PRICE), falseValue);
        boolean buyOrdersCountFlag = parseBooleanOrTrue(getInputValueByState(chatId, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_BUY_ORDERS_COUNT), falseValue);
        boolean minSellPriceFlag = parseBooleanOrTrue(getInputValueByState(chatId, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_MIN_SELL_PRICE), falseValue);
        boolean sellOrdersCountFlag = parseBooleanOrTrue(getInputValueByState(chatId, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_SELL_ORDERS_COUNT), falseValue);
        boolean pictureFlag = parseBooleanOrTrue(getInputValueByState(chatId, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_PICTURE), falseValue);

        ItemShownFieldsSettings settings = new ItemShownFieldsSettings(
                nameFlag,
                itemTypeFlag,
                maxBuyPriceFlag,
                buyOrdersCountFlag,
                minSellPriceFlag,
                sellOrdersCountFlag,
                pictureFlag);

        telegramUserDatabaseService.setItemShowFieldsSettings(String.valueOf(chatId), settings);
    }

    public void addItemShowAppliedFilter(Long chatId, ItemFilter filter) throws TelegramUserDoesntExistException {
        telegramUserDatabaseService.addItemShowAppliedFilter(String.valueOf(chatId), filter);
    }

    public void removeItemShowAppliedFilter(Long chatId, String filterName) throws TelegramUserDoesntExistException {
        telegramUserDatabaseService.removeItemShowAppliedFilter(String.valueOf(chatId), filterName);
    }

    public void authorizeAndSaveUser(Long chatId, String email, String password, String twoFACode)
            throws TelegramUserDoesntExistException,
            UbiUserAuthorizationClientErrorException,
            UbiUserAuthorizationServerErrorException {
        getTelegramUserOrThrow(chatId);

        inputDatabaseService.deleteAllByChatId(String.valueOf(chatId));

        credentialsService.authorizeAndSaveUser(String.valueOf(chatId), email, password, twoFACode);
    }

    public void reauthorizeAndSaveExistingUserBy2FACode(Long chatId, String twoFACode)
            throws TelegramUserDoesntExistException,
            UbiUserAuthorizationClientErrorException,
            UbiUserAuthorizationServerErrorException {
        getTelegramUserOrThrow(chatId);

        inputDatabaseService.deleteAllByChatId(String.valueOf(chatId));

        credentialsService.reauthorizeAndSaveExistingUserBy2FACode(String.valueOf(chatId), twoFACode);
    }

    public void removeUserUbiAccountEntry(Long chatId) throws TelegramUserDoesntExistException {
        getTelegramUserOrThrow(chatId);

        credentialsService.deleteByChatId(String.valueOf(chatId));
    }

    public void saveUserInput(Long chatId, InputState inputState, String userInput) throws TelegramUserDoesntExistException {
        getTelegramUserOrThrow(chatId);
        inputDatabaseService.save(String.valueOf(chatId), inputState, userInput);
    }

    public void clearUserInputs(Long chatId) throws TelegramUserDoesntExistException {
        getTelegramUserOrThrow(chatId);

        inputDatabaseService.deleteAllByChatId(String.valueOf(chatId));
    }

    public boolean isTelegramUserRegistered(Long chatId) {
        return telegramUserDatabaseService.existsById(String.valueOf(chatId));
    }

    public TelegramUser getTelegramUser(Long chatId) throws TelegramUserDoesntExistException {
        return getTelegramUserOrThrow(chatId);
    }

    public ItemShowSettings getItemShowSettings(Long chatId) throws TelegramUserDoesntExistException {
        return telegramUserDatabaseService.findUserItemShowSettingsById(String.valueOf(chatId));
    }

    public TradeManagersSettings getTradeManagersSettings(Long chatId) throws TelegramUserDoesntExistException {
        return telegramUserDatabaseService.findUserTradeManagersSettingsById(String.valueOf(chatId));
    }

    public void setTradeManagersSettingsNewManagersAreActiveFlag(Long chatId, boolean flag) throws TelegramUserDoesntExistException {
        telegramUserDatabaseService.setTradeManagersSettingsNewManagersAreActiveFlag(String.valueOf(chatId), flag);
    }

    public void setTradeManagersSettingsManagingEnabledFlag(Long chatId, boolean flag) throws TelegramUserDoesntExistException {
        telegramUserDatabaseService.setTradeManagersSettingsManagingEnabledFlag(String.valueOf(chatId), flag);
    }

    public InputState getUserInputState(Long chatId) throws TelegramUserDoesntExistException {
        TelegramUser telegramUser = getTelegramUserOrThrow(chatId);
        return telegramUser.getInputState();
    }

    public InputGroup getUserInputGroup(Long chatId) throws TelegramUserDoesntExistException {
        TelegramUser telegramUser = getTelegramUserOrThrow(chatId);
        return telegramUser.getInputGroup();
    }

    public UbiAccountAuthorizationEntry getUserUbiAccountEntry(Long chatId) throws TelegramUserDoesntExistException, UbiAccountEntryDoesntExistException {
        getTelegramUserOrThrow(chatId);

        return credentialsService.findByChatId(String.valueOf(chatId));
    }

    public String getUserInputByState(Long chatId, InputState inputState) throws TelegramUserDoesntExistException, TelegramUserInputDoesntExistException {
        return getInputValueByState(chatId, inputState);
    }

    public List<TelegramUserInput> getAllUserInputs(Long chatId) throws TelegramUserDoesntExistException {
        getTelegramUserOrThrow(chatId);
        return inputDatabaseService.findAllByChatId(String.valueOf(chatId));
    }

    public List<String> getAllChatIdsForNotifiableUsers() {
        return telegramUserDatabaseService.findAllUsers().stream()
                .filter(TelegramUser::isPublicNotificationsEnabledFlag)
                .map(TelegramUser::getChatId)
                .toList();
    }

    private boolean parseBooleanOrTrue(String value, String falseValue) {
        return !falseValue.equalsIgnoreCase(value);
    }

    private String getInputValueByState(Long chatId, InputState inputState) throws TelegramUserDoesntExistException, TelegramUserInputDoesntExistException {
        getTelegramUserOrThrow(chatId);

        return inputDatabaseService.findById(String.valueOf(chatId), inputState).getValue();
    }

    private TelegramUser getTelegramUserOrThrow(Long chatId) throws TelegramUserDoesntExistException {
        return telegramUserDatabaseService.findUserById(String.valueOf(chatId));
    }
}
