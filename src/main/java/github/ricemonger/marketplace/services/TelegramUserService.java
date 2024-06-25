package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.TelegramUserDatabaseService;
import github.ricemonger.marketplace.services.abstractions.TelegramUserInputDatabaseService;
import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;
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

    private final TelegramLinkedUbiUserService credentialsService;

    public boolean isTelegramUserRegistered(Long chatId) {
        return userService.existsById(String.valueOf(chatId));
    }

    public void registerTelegramUserWithDefaultSettings(Long chatId) throws TelegramUserAlreadyExistsException {
        if (userService.existsById(String.valueOf(chatId))) {
            throw new TelegramUserAlreadyExistsException();
        } else {
            TelegramUser telegramUser = new TelegramUser(chatId);
            telegramUser.setInputState(InputState.BASE);
            telegramUser.setInputGroup(InputGroup.BASE);
            telegramUser.setPublicNotificationsEnabledFlag(true);
            telegramUser.setItemShowMessagesLimit(50);
            telegramUser.setItemShowFewInMessageFlag(false);
            telegramUser.setItemShowNameFlag(true);
            telegramUser.setItemShowItemTypeFlag(true);
            telegramUser.setItemShowMaxBuyPrice(true);
            telegramUser.setItemShowBuyOrdersCountFlag(true);
            telegramUser.setItemShowMinSellPriceFlag(true);
            telegramUser.setItemsShowSellOrdersCountFlag(true);
            telegramUser.setItemShowPictureFlag(true);
            userService.save(telegramUser);
        }
    }

    public void setUserNextInputState(Long chatId, InputState inputState) throws TelegramUserDoesntExistException {
        TelegramUser telegramUser = getTelegramUserOrThrow(chatId);
        telegramUser.setInputState(inputState);
        userService.save(telegramUser);
    }

    public void setUserNextInputGroup(Long chatId, InputGroup inputGroup) throws TelegramUserDoesntExistException {
        TelegramUser telegramUser = getTelegramUserOrThrow(chatId);
        telegramUser.setInputGroup(inputGroup);
        userService.save(telegramUser);
    }

    public InputState getUserInputState(Long chatId) throws TelegramUserDoesntExistException {
        TelegramUser telegramUser = getTelegramUserOrThrow(chatId);
        return telegramUser.getInputState();
    }

    public InputGroup getUserInputGroup(Long chatId) throws TelegramUserDoesntExistException {
        TelegramUser telegramUser = getTelegramUserOrThrow(chatId);
        return telegramUser.getInputGroup();
    }

    public void saveUserInput(Long chatId, InputState inputState, String userInput) throws TelegramUserDoesntExistException {
        getTelegramUserOrThrow(chatId);
        inputService.save(String.valueOf(chatId), inputState, userInput);
    }

    public String getUserInputByState(Long chatId, InputState inputState) throws TelegramUserDoesntExistException, TelegramUserInputDoesntExistException {
        return getInputValueByState(chatId, inputState);
    }

    public Collection<TelegramUserInput> getAllUserInputs(Long chatId) throws TelegramUserDoesntExistException {
        getTelegramUserOrThrow(chatId);
        return inputService.findAllByChatId(String.valueOf(chatId));
    }

    @Transactional
    public void clearUserInputs(Long chatId) throws TelegramUserDoesntExistException {
        getTelegramUserOrThrow(chatId);

        inputService.deleteAllByChatId(String.valueOf(chatId));
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

        inputService.deleteAllByChatId(String.valueOf(chatId));
    }

    public void removeAllCredentials(Long chatId) throws TelegramUserDoesntExistException {
        getTelegramUserOrThrow(chatId);

        credentialsService.deleteAllByLinkedTelegramUserChatId(String.valueOf(chatId));
    }

    public List<String> getCredentialsEmailsList(Long chatId) throws TelegramUserDoesntExistException {
        getTelegramUserOrThrow(chatId);

        Collection<UbiAccount> credentialsList = credentialsService.findAllByLinkedTelegramUserChatId(String.valueOf(chatId));

        return credentialsList.stream()
                .map(UbiAccount::getEmail)
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

    public ItemShowSettings getItemShowSettings(Long chatId) {
        return userService.findUserSettingsById(String.valueOf(chatId));
    }

    public int getItemOffsetByUserInput(Long chatId) throws TelegramUserDoesntExistException{
        try {
            return Integer.parseInt(inputService.findById(String.valueOf(chatId), InputState.ITEMS_SHOW_OFFSET).getValue());
        } catch (TelegramUserInputDoesntExistException | NumberFormatException e) {
            return 0;
        }
    }

    public void setItemShowFewItemsInMessageFlag(Long chatId, boolean flag) throws TelegramUserDoesntExistException {
        userService.setItemShowFewItemsInMessageFlag(String.valueOf(chatId), flag);
    }

    public void setItemShowMessagesLimit(Long chatId, Integer limit) throws TelegramUserDoesntExistException{
        userService.setItemShowMessagesLimit(String.valueOf(chatId), limit);
    }

    public void setItemShowSettingsByUserInput(Long chatId, String trueValue, String falseValue) throws TelegramUserDoesntExistException,TelegramUserInputDoesntExistException {
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

        userService.setItemShowFieldsSettings(String.valueOf(chatId), settings);
    }

    public void addItemShowAppliedFilter(Long chatId, ItemFilter filter) {
        userService.addItemShowAppliedFilter(String.valueOf(chatId), filter);
    }

    public void removeItemShowAppliedFilter(Long chatId, String filterName) {
        userService.removeItemShowAppliedFilter(String.valueOf(chatId), filterName);
    }

    private boolean parseBooleanOrTrue(String value, String falseValue) {
        return !falseValue.equalsIgnoreCase(value);
    }

    private String getInputValueByState(Long chatId, InputState inputState) throws TelegramUserDoesntExistException, TelegramUserInputDoesntExistException {
        getTelegramUserOrThrow(chatId);

        return inputService.findById(String.valueOf(chatId), inputState).getValue();
    }

    private TelegramUser getTelegramUserOrThrow(Long chatId) throws TelegramUserDoesntExistException {
        return userService.findUserById(String.valueOf(chatId));
    }
}
