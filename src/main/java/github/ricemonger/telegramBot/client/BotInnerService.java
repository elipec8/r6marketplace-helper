package github.ricemonger.telegramBot.client;

import github.ricemonger.marketplace.services.*;
import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.utils.dtos.*;
import github.ricemonger.utils.enums.ItemType;
import github.ricemonger.utils.enums.TagGroup;
import github.ricemonger.utils.enums.TradeManagerTradeType;
import github.ricemonger.utils.exceptions.InvalidTelegramUserInput;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.TelegramUserInputDoesntExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BotInnerService {

    private final CommonValuesService commonValuesService;

    private final TelegramBotClientService telegramBotClientService;

    private final TelegramUserService telegramUserService;

    private final ItemStatsService itemStatsService;

    private final ItemFilterService itemFilterService;

    private final TradeManagerService tradeManagerService;

    private final ItemFilterFromInputsMapper itemFilterFromInputsMapper;

    private final TradeManagerFromInputsMapper tradeManagerFromInputsMapper;

    private final TagService tagService;

    public void askFromInlineKeyboard(UpdateInfo updateInfo, String text, int buttonsInLine, CallbackButton[] buttons) {
        telegramBotClientService.askFromInlineKeyboard(updateInfo, text, buttonsInLine, buttons);
    }

    public void sendText(UpdateInfo updateInfo, String answer) {
        telegramBotClientService.sendText(updateInfo, answer);
    }

    public boolean isRegistered(Long chatId) {
        return telegramUserService.isTelegramUserRegistered(chatId);
    }

    public void registerUser(Long chatId) {
        telegramUserService.registerTelegramUserWithDefaultSettings(chatId);
    }

    public void addCredentialsFromUserInputs(Long chatId) throws TelegramUserDoesntExistException {
        String fullOrEmail = getUserInputByState(chatId, InputState.CREDENTIALS_FULL_OR_EMAIL);

        if (fullOrEmail.contains(":")) {
            String email = fullOrEmail.substring(0, fullOrEmail.indexOf(":"));
            String password = fullOrEmail.substring(fullOrEmail.indexOf(":") + 1);
            telegramUserService.addCredentialsIfValidOrThrowException(chatId, email, password);
        } else {
            String password = getUserInputByState(chatId, InputState.CREDENTIALS_PASSWORD);
            telegramUserService.addCredentialsIfValidOrThrowException(chatId, fullOrEmail, password);
        }
    }

    public void removeCredentialsByUserInputs(Long chatId) throws TelegramUserDoesntExistException {
        telegramUserService.removeCredentialsByUserInputs(chatId);
    }

    public void saveUserInputOrThrow(UpdateInfo updateInfo) throws TelegramUserDoesntExistException {
        String userInput;

        if (updateInfo.isHasMessage()) {
            userInput = updateInfo.getMessageText();
        } else if (updateInfo.isHasCallBackQuery()) {
            userInput = updateInfo.getCallbackQueryData();
        } else {
            throw new InvalidTelegramUserInput("UpdateInfo has no message or callback query");
        }
        telegramUserService.saveUserInput(updateInfo.getChatId(), updateInfo.getInputState(), userInput);
    }

    public void clearUserInputs(Long chatId) throws TelegramUserDoesntExistException {
        telegramUserService.clearUserInputs(chatId);
    }

    public void setUserNextInputState(Long chatId, InputState inputState) throws TelegramUserDoesntExistException {
        telegramUserService.setUserNextInputState(chatId, inputState);
    }

    public void setUserNextInputGroup(Long chatId, InputGroup inputGroup) throws TelegramUserDoesntExistException {
        telegramUserService.setUserNextInputGroup(chatId, inputGroup);
    }

    public String getUserInputByState(Long chatId, InputState inputState) throws TelegramUserDoesntExistException, TelegramUserInputDoesntExistException {
        return telegramUserService.getUserInputByState(chatId, inputState);
    }

    public void removeUserAllCredentials(Long chatId) throws TelegramUserDoesntExistException {
        telegramUserService.removeAllCredentials(chatId);
    }

    public List<String> getCredentialsEmailsList(Long chatId) throws TelegramUserDoesntExistException {
        return List.of(telegramUserService.getCredentialsEmailsList(chatId).getEmail());
    }

    public String getItemTypesString() {
        return Arrays.stream(ItemType.values()).map(Enum::name).reduce((s, s2) -> s + "," + s2).orElse("");
    }

    public String getAllTagsNamesStringByGroup(TagGroup tagGroup) {
        return tagService.getAllTags().stream()
                .filter(tag -> tag.getTagGroup().equals(tagGroup))
                .map(Tag::getName)
                .reduce((s, s2) -> s + "," + s2)
                .orElse("");
    }

    public void saveFilterFromInput(Long chatId) {
        itemFilterService.saveItemFilter(String.valueOf(chatId), getItemFilterByUserInput(chatId));
    }

    public Collection<String> getAllFilterNamesForUser(Long chatId) {
        return itemFilterService.getAllItemFilterNamesForUser(String.valueOf(chatId));
    }

    public ItemFilter getFilterFromDatabaseByUserInputCallback(Long chatId) {
        return itemFilterService.getItemFilterById(String.valueOf(chatId), getInputValueFromCallbackData(chatId, InputState.FILTER_NAME));
    }

    public void removeFilterByUserInputCallback(Long chatId) {
        itemFilterService.deleteItemFilterById(String.valueOf(chatId), getInputValueFromCallbackData(chatId, InputState.FILTER_NAME));
    }

    public ItemFilter getItemFilterByUserInput(Long chatId) {
        Collection<TelegramUserInput> inputs = telegramUserService.getAllUserInputs(chatId);

        return itemFilterFromInputsMapper.mapToItemFilter(inputs);
    }

    public ItemShowSettings getItemShowSettingsForUser(Long chatId) {
        return telegramUserService.getItemShowSettings(chatId);
    }

    public void showItemsByUserSettingsAndInputtedOffset(Long chatId) {
        ItemShowSettings settings = telegramUserService.getItemShowSettings(chatId);
        int offset = telegramUserService.getItemOffsetByUserInput(chatId);
        List<Item> items = new ArrayList<>(itemStatsService.getAllItemsByFilters(settings.getItemShowAppliedFilters()));

        if (offset >= items.size()) {
            telegramBotClientService.sendText(String.valueOf(chatId), "Too big offset, no more items to show");
        } else {
            int itemsInMessage = settings.isItemShowFewInMessageFlag() ? commonValuesService.getMaximumTelegramMessageHeight() / settings.getActiveFieldsCount() : 1;

            int currentCount = 0;

            messageCycle:
            for (int i = 0; i < settings.getItemShowMessagesLimit(); i++) {
                StringBuilder nextMessage = new StringBuilder();
                for (int j = 0; j < itemsInMessage; j++) {
                    if (offset + currentCount >= items.size()) {
                        telegramBotClientService.sendText(String.valueOf(chatId), nextMessage.toString());
                        telegramBotClientService.sendText(String.valueOf(chatId), "No more items to show");
                        break messageCycle;
                    }
                    nextMessage.append(items.get(offset + currentCount).toStringBySettings(settings.getShownFieldsSettings())).append("\n");
                    currentCount++;
                }
                telegramBotClientService.sendText(String.valueOf(chatId), nextMessage.toString());
            }
        }
    }

    public void setItemShowSettingsUserFewItemsInMessage(Long chatId, boolean flag) {
        telegramUserService.setItemShowFewItemsInMessageFlag(chatId, flag);
    }

    public void setItemShowSettingsMessageLimitByUserInput(Long chatId) {
        Integer limit = commonValuesService.getMaximumTelegramMessageLimit();
        try {
            limit = Integer.parseInt(getUserInputByState(chatId, InputState.ITEMS_SHOW_SETTING_MESSAGE_LIMIT));
            if (limit < 1) {
                limit = 1;
            } else if (limit > commonValuesService.getMaximumTelegramMessageLimit()) {
                limit = commonValuesService.getMaximumTelegramMessageLimit();
            }
        } catch (TelegramUserInputDoesntExistException | NumberFormatException e) {
            if (limit == null) {
                limit = commonValuesService.getMaximumTelegramMessageLimit();
            }
        }
        telegramUserService.setItemShowMessagesLimit(chatId, limit);
    }

    public void setItemShowSettingsShownFieldsByUserInput(Long chatId) {
        telegramUserService.setItemShowSettingsByUserInput(chatId, Callbacks.INPUT_CALLBACK_TRUE, Callbacks.INPUT_CALLBACK_FALSE);
    }

    public void changeItemShowSettingsAppliedFiltersByUserInput(Long chatId) {
        String filterName = getInputValueFromCallbackData(chatId, InputState.FILTER_NAME);
        boolean addOrRemove = Callbacks.INPUT_CALLBACK_TRUE.equals(getUserInputByState(chatId, InputState.ITEMS_SHOW_SETTINGS_APPLIED_FILTER_ADD_OR_REMOVE));

        List<String> appliedFilters = telegramUserService.getItemShowSettings(chatId).getItemShowAppliedFilters().stream().map(ItemFilter::getName).toList();

        if (addOrRemove && appliedFilters.contains(filterName)) {
            telegramUserService.removeItemShowAppliedFilter(chatId, filterName);
        } else if (addOrRemove && !appliedFilters.contains(filterName)) {
            ItemFilter filter = itemFilterService.getItemFilterById(String.valueOf(chatId), filterName);
            telegramUserService.addItemShowAppliedFilter(chatId, filter);
        }
    }

    public void savePlannedOneItemTradeByUserInput(Long chatId, TradeManagerTradeType tradeType) {
        tradeManagerService.saveTradeManagerByItemId(String.valueOf(chatId), getPlannedOneItemTradeByUserInput(chatId, tradeType));
    }

    public TradeManagerByItemId getPlannedOneItemTradeByUserInput(Long chatId, TradeManagerTradeType tradeType) {
        Collection<TelegramUserInput> inputs = telegramUserService.getAllUserInputs(chatId);

        return tradeManagerFromInputsMapper.mapToTradeManagerByItemId(
                String.valueOf(chatId),
                inputs,
                tradeType,
                getItemByPlannedOneItemTradeEditUserInput(chatId));
    }

    public Item getItemByPlannedOneItemTradeEditUserInput(Long chatId) {
        return itemStatsService.getItemById(getUserInputByState(chatId, InputState.TRADES_EDIT_ONE_ITEM_ITEM_ID));
    }

    private String getInputValueFromCallbackData(Long chatId, InputState inputState) {
        String callback = telegramUserService.getUserInputByState(chatId, inputState);
        return callback.substring(Callbacks.INPUT_CALLBACK_PREFIX.length());
    }

    public Collection<TradeManagerByItemId> getTradeManagersByItemId(Long chatId) {
        return tradeManagerService.getAllTradeManagersByItemId(String.valueOf(chatId));
    }

    public Collection<TradeManagerByItemFilters> getTradeManagersByItemFilters(Long chatId) {
        return tradeManagerService.getAllTradeManagersByItemFilters(String.valueOf(chatId));
    }

    public void sendMultipleObjectsFewInMessage(Collection<?> objects, int objectStringHeight, Long chatId) {
        int tradeManagersInMessage = commonValuesService.getMaximumTelegramMessageHeight() / objectStringHeight;

        int currentCount = 0;
        StringBuilder nextMessage = new StringBuilder();

        for (Object object : objects) {
            nextMessage.append(object.toString()).append("\n");
            currentCount++;
            if (currentCount >= tradeManagersInMessage) {
                telegramBotClientService.sendText(String.valueOf(chatId), nextMessage.toString());
                currentCount = 0;
                nextMessage = new StringBuilder();
            }
        }
        if (currentCount > 0) {
            telegramBotClientService.sendText(String.valueOf(chatId), nextMessage.toString());
        }
    }

    public TradeManagerByItemId getTradeManagerByItemIdByUserInput(Long chatId) {
        return tradeManagerService.getTradeManagerByItemIdById(String.valueOf(chatId), getUserInputByState(chatId,
                InputState.TRADES_EDIT_ONE_ITEM_ITEM_ID));
    }

    public void removeTradeManagerByItemIdByUserInput(Long chatId) {
        tradeManagerService.deleteTradeManagerByItemIdById(String.valueOf(chatId), getUserInputByState(chatId, InputState.TRADES_EDIT_ONE_ITEM_ITEM_ID));
    }
}
