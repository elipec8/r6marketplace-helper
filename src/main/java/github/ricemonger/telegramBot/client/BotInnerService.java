package github.ricemonger.telegramBot.client;

import github.ricemonger.marketplace.services.*;
import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.utils.DTOs.*;
import github.ricemonger.utils.DTOs.items.Item;
import github.ricemonger.utils.DTOs.items.ItemFilter;
import github.ricemonger.utils.DTOs.items.Tag;
import github.ricemonger.utils.enums.TagGroup;
import github.ricemonger.utils.enums.TradeOperationType;
import github.ricemonger.utils.exceptions.client.*;
import github.ricemonger.utils.exceptions.server.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BotInnerService {

    private final TelegramBotClientService telegramBotClientService;

    private final CommonValuesService commonValuesService;

    private final ItemService itemService;

    private final TagService tagService;

    private final TelegramUserService telegramUserService;

    private final TelegramUserItemFilterService telegramUserItemFilterService;

    private final TelegramUserTradeManagerService telegramUserTradeManagerService;

    private final ItemFilterFromInputsMapper itemFilterFromInputsMapper;

    private final TradeManagerFromInputsMapper tradeManagerFromInputsMapper;

    public void sendText(UpdateInfo updateInfo, String text) throws TelegramApiRuntimeException {
        telegramBotClientService.sendText(updateInfo, text);
    }

    public void askFromInlineKeyboard(UpdateInfo updateInfo, String text, int buttonsInLine, CallbackButton[] buttons) throws TelegramApiRuntimeException {
        telegramBotClientService.askFromInlineKeyboard(updateInfo, text, buttonsInLine, buttons);
    }

    public void sendMultipleObjectStringsGroupedInMessages(Collection<?> objects, int objectStringHeight, Long chatId) throws TelegramApiRuntimeException {
        int maxObjectsInMessage = commonValuesService.getMaximumTelegramMessageHeight() / objectStringHeight;

        int objectsInCurrentMessageCount = 0;
        StringBuilder currentMessage = new StringBuilder();

        for (Object object : objects) {
            currentMessage.append(object.toString()).append("\n");
            objectsInCurrentMessageCount++;
            if (objectsInCurrentMessageCount >= maxObjectsInMessage) {
                telegramBotClientService.sendText(String.valueOf(chatId), currentMessage.toString());
                objectsInCurrentMessageCount = 0;
                currentMessage = new StringBuilder();
            }
        }
        if (objectsInCurrentMessageCount > 0) {
            telegramBotClientService.sendText(String.valueOf(chatId), currentMessage.toString());
        }
    }

    public void sendItemsByUserItemShowSettingsAndUserInputOffset(Long chatId) throws TelegramUserDoesntExistException, TelegramApiRuntimeException {
        ItemShowSettings settings = telegramUserService.getItemShowSettings(chatId);

        int offset = getItemOffsetOrZeroByUserInput(chatId);

        List<Item> itemMainFields = itemService.getAllItemsByFilters(settings.getItemShowAppliedFilters());
        try {
            if (offset >= itemMainFields.size()) {
                throw new IllegalArgumentException("Offset is bigger or equals than items size");
            }
            itemMainFields = itemMainFields.subList(offset, itemMainFields.size());
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            telegramBotClientService.sendText(String.valueOf(chatId), "Too big offset or strict filters, no items to show");
            return;
        }

        int maxItemsInMessage = settings.isItemShowFewInMessageFlag() ? commonValuesService.getMaximumTelegramMessageHeight() / settings.getActiveFieldsCount() : 1;
        int messageLimit = settings.getItemShowMessagesLimit();
        int messageCount = 0;

        int itemsInCurrentMessageCount = 0;
        StringBuilder currentMessage = new StringBuilder();

        for (Item item : itemMainFields) {
            if (messageCount >= messageLimit) {
                break;
            }
            currentMessage.append(item.toStringBySettings(settings.getShownFieldsSettings())).append("\n");
            itemsInCurrentMessageCount++;
            if (itemsInCurrentMessageCount >= maxItemsInMessage) {
                telegramBotClientService.sendText(String.valueOf(chatId), currentMessage.toString());
                itemsInCurrentMessageCount = 0;
                currentMessage = new StringBuilder();
                messageCount++;
            }
        }
        if (itemsInCurrentMessageCount > 0) {
            telegramBotClientService.sendText(String.valueOf(chatId), currentMessage.toString());
        }
        if (messageCount < messageLimit) {
            telegramBotClientService.sendText(String.valueOf(chatId), "No more items to show");
        }
    }

    private int getItemOffsetOrZeroByUserInput(Long chatId) {
        String offsetInput;
        int offset;
        try {
            offsetInput = telegramUserService.getUserInputByState(chatId, InputState.ITEMS_SHOW_OFFSET);
        } catch (TelegramUserInputDoesntExistException e) {
            offsetInput = "0";
            log.error("Offset input doesn't exist for chatId-{}", chatId);
        }

        try {
            offset = offsetInput == null ? 0 : Integer.parseInt(offsetInput);
        } catch (NumberFormatException e) {
            offset = 0;
            log.error("Offset input is invalid for chatId-{}", chatId);
        }

        return offset;
    }

    public void registerUser(Long chatId) throws TelegramUserAlreadyExistsException {
        telegramUserService.registerTelegramUser(chatId);
    }

    public boolean isUserRegistered(Long chatId) {
        return telegramUserService.isTelegramUserRegistered(chatId);
    }

    public void setUserInputState(Long chatId, InputState inputState) throws TelegramUserDoesntExistException {
        telegramUserService.setUserInputState(chatId, inputState);
    }

    public void setUserInputGroup(Long chatId, InputGroup inputGroup) throws TelegramUserDoesntExistException {
        telegramUserService.setUserInputGroup(chatId, inputGroup);
    }

    public void addUserUbiAccountEntryByUserInput(Long chatId)
            throws TelegramUserDoesntExistException,
            UbiUserAuthorizationClientErrorException,
            UbiUserAuthorizationServerErrorException {
        String fullOrEmail = getUserInputByState(chatId, InputState.UBI_ACCOUNT_ENTRY_FULL_OR_EMAIL);

        if (fullOrEmail.contains(":")) {
            String email = fullOrEmail.substring(0, fullOrEmail.indexOf(":"));
            String password = fullOrEmail.substring(fullOrEmail.indexOf(":") + 1);
            telegramUserService.addUserUbiAccountEntryIfValidCredentialsOrThrow(chatId, email, password);
        } else {
            String password = getUserInputByState(chatId, InputState.UBI_ACCOUNT_ENTRY_PASSWORD);
            telegramUserService.addUserUbiAccountEntryIfValidCredentialsOrThrow(chatId, fullOrEmail, password);
        }
    }

    public void removeUserUbiAccountEntry(Long chatId) throws TelegramUserDoesntExistException {
        telegramUserService.removeUserUbiAccountEntry(chatId);
    }

    public String getUserUbiAccountEntryEmail(Long chatId) throws TelegramUserDoesntExistException {
        return telegramUserService.getUserUbiAccountEntry(chatId).getEmail();
    }

    public void saveUserInput(UpdateInfo updateInfo) throws TelegramUserDoesntExistException {
        String userInput;

        if (updateInfo.isHasMessage()) {
            userInput = updateInfo.getMessageText();
        } else if (updateInfo.isHasCallBackQuery()) {
            userInput = updateInfo.getCallbackQueryData();
        } else {
            throw new InvalidTelegramUserInputException("UpdateInfo has no message or callback query");
        }
        telegramUserService.saveUserInput(updateInfo.getChatId(), updateInfo.getInputState(), userInput);
    }

    public void clearUserInputs(Long chatId) throws TelegramUserDoesntExistException {
        telegramUserService.clearUserInputs(chatId);
    }

    public String getStringOfAllTagsNamesByTagGroup(TagGroup tagGroup) {
        return tagService.getAllTags().stream()
                .filter(tag -> tag.getTagGroup().equals(tagGroup))
                .map(Tag::getName)
                .reduce((s, s2) -> s + "," + s2)
                .orElse("");
    }

    public void saveUserItemFilterByUserInput(Long chatId) throws TelegramUserDoesntExistException {
        telegramUserItemFilterService.saveItemFilter(String.valueOf(chatId), generateItemFilterByUserInput(chatId));
    }

    public ItemFilter generateItemFilterByUserInput(Long chatId) throws TelegramUserDoesntExistException {
        Collection<TelegramUserInput> inputs = telegramUserService.getAllUserInputs(chatId);

        return itemFilterFromInputsMapper.mapToItemFilter(inputs);
    }

    public List<String> getAllUserItemFiltersNames(Long chatId) throws TelegramUserDoesntExistException {
        return telegramUserItemFilterService.getAllUserItemFiltersNames(String.valueOf(chatId));
    }

    public ItemFilter getUserItemFilterByUserInputCallbackFilterName(Long chatId)
            throws TelegramUserDoesntExistException,
            TelegramUserInputDoesntExistException,
            ItemFilterDoesntExistException,
            MissingCallbackPrefixInUserInputException {
        return telegramUserItemFilterService.getItemFilterById(String.valueOf(chatId), getUserInputValueWithoutCallbackPrefix(chatId, InputState.ITEM_FILTER_NAME));
    }

    public void removeUserItemFilterByUserInputCallbackFilterName(Long chatId)
            throws TelegramUserDoesntExistException,
            TelegramUserInputDoesntExistException,
            MissingCallbackPrefixInUserInputException {
        telegramUserItemFilterService.deleteItemFilterById(String.valueOf(chatId), getUserInputValueWithoutCallbackPrefix(chatId, InputState.ITEM_FILTER_NAME));
    }

    public ItemShowSettings getUserItemShowSettings(Long chatId) throws TelegramUserDoesntExistException {
        return telegramUserService.getItemShowSettings(chatId);
    }

    public void setUserItemShowSettingsFewItemsInMessageFlag(Long chatId, boolean flag) throws TelegramUserDoesntExistException {
        telegramUserService.setItemShowFewItemsInMessageFlag(chatId, flag);
    }

    public void setUserItemShowSettingsMessageLimitOrEdgeValueByUserInput(Long chatId) throws TelegramUserDoesntExistException {
        Integer limit;
        try {
            limit = Integer.parseInt(getUserInputByState(chatId, InputState.ITEMS_SHOW_SETTING_MESSAGE_LIMIT));
            if (limit < 1) {
                limit = 1;
            } else if (limit > commonValuesService.getMaximumTelegramMessageLimit()) {
                limit = commonValuesService.getMaximumTelegramMessageLimit();
            }
        } catch (TelegramUserInputDoesntExistException | NumberFormatException e) {
            limit = commonValuesService.getMaximumTelegramMessageLimit();
        }
        telegramUserService.setItemShowMessagesLimit(chatId, limit);
    }

    public void setUserItemShownFieldsSettingByUserInput(Long chatId) throws TelegramUserDoesntExistException, TelegramUserInputDoesntExistException {
        telegramUserService.setItemShownFieldsSettingsByUserInput(chatId, Callbacks.INPUT_CALLBACK_TRUE, Callbacks.INPUT_CALLBACK_FALSE);
    }

    public void updateUserItemShowAppliedFiltersSettingsByUserInput(Long chatId) throws TelegramUserDoesntExistException, TelegramUserInputDoesntExistException, MissingCallbackPrefixInUserInputException {
        String filterName = getUserInputValueWithoutCallbackPrefix(chatId, InputState.ITEM_FILTER_NAME);
        boolean addOrRemove = Callbacks.INPUT_CALLBACK_TRUE.equals(getUserInputByState(chatId, InputState.ITEMS_SHOW_SETTINGS_APPLIED_FILTER_ADD_OR_REMOVE));

        List<String> appliedFilters = telegramUserService.getItemShowSettings(chatId).getItemShowAppliedFilters().stream().map(ItemFilter::getName).toList();

        if (!addOrRemove && appliedFilters.contains(filterName)) {
            telegramUserService.removeItemShowAppliedFilter(chatId, filterName);
        } else if (addOrRemove && !appliedFilters.contains(filterName)) {
            ItemFilter filter = telegramUserItemFilterService.getItemFilterById(String.valueOf(chatId), filterName);
            telegramUserService.addItemShowAppliedFilter(chatId, filter);
        }
    }

    public void saveUserTradeByItemIdManagerByUserInput(Long chatId, TradeOperationType tradeOperationType) throws TelegramUserDoesntExistException,
            TelegramUserInputDoesntExistException {
        telegramUserTradeManagerService.saveUserTradeByItemIdManager(String.valueOf(chatId), generateTradeByItemIdManagerByUserInput(chatId, tradeOperationType));
    }

    public void saveUserTradeByFiltersManagerByUserInput(Long chatId) throws TelegramUserDoesntExistException, TelegramUserInputDoesntExistException {
        telegramUserTradeManagerService.saveUserTradeByFiltersManager(String.valueOf(chatId), generateTradeByFiltersManagerByUserInput(chatId));
    }

    public TradeByItemIdManager generateTradeByItemIdManagerByUserInput(Long chatId, TradeOperationType tradeOperationType) throws TelegramUserDoesntExistException, TelegramUserInputDoesntExistException {
        Collection<TelegramUserInput> inputs = telegramUserService.getAllUserInputs(chatId);

        return tradeManagerFromInputsMapper.mapToTradeByItemIdManager(
                inputs,
                tradeOperationType,
                getItemByUserInputItemId(chatId),
                telegramUserService.getTradeManagersSettings(chatId).isNewManagersAreActiveFlag());
    }

    public TradeByFiltersManager generateTradeByFiltersManagerByUserInput(Long chatId) throws TelegramUserDoesntExistException,
            TelegramUserInputDoesntExistException {
        Collection<TelegramUserInput> inputs = telegramUserService.getAllUserInputs(chatId);

        String appliedFiltersNamesString = inputs.stream().filter(input -> input.getInputState().equals(InputState.TRADE_BY_FILTERS_MANAGER_FILTERS_NAMES))
                .map(TelegramUserInput::getValue)
                .findFirst()
                .orElse("");

        List<ItemFilter> appliedFilters = telegramUserItemFilterService.getAllUserItemFilters(String.valueOf(chatId)).stream()
                .filter(itemFilter -> appliedFiltersNamesString.contains(itemFilter.getName()))
                .toList();

        return tradeManagerFromInputsMapper.mapToTradeByFiltersManager(inputs,
                commonValuesService.getMaximumMarketplacePrice(),
                appliedFilters,
                telegramUserService.getTradeManagersSettings(chatId).isNewManagersAreActiveFlag());
    }

    public Item getItemByUserInputItemId(Long chatId) throws TelegramUserDoesntExistException, TelegramUserInputDoesntExistException {
        return itemService.getItemById(getUserInputByState(chatId, InputState.TRADE_BY_ITEM_ID_MANAGER_ITEM_ID));
    }

    public void invertUserTradeByFiltersManagerEnabledByUserInput(Long chatId) throws TelegramUserDoesntExistException,
            TelegramUserInputDoesntExistException, TradeByFiltersManagerDoesntExistException {
        telegramUserTradeManagerService.invertUserTradeByFiltersManagerEnabledFlagById(String.valueOf(chatId), getUserInputByState(chatId, InputState.TRADE_BY_FILTERS_MANAGER_NAME));
    }

    public void invertUserTradeByItemIdManagerEnabledByUserInput(Long chatId) throws TelegramUserDoesntExistException,
            TelegramUserInputDoesntExistException, TradeByItemIdManagerDoesntExistException {
        telegramUserTradeManagerService.invertUserTradeByItemIdManagerEnabledFlagById(String.valueOf(chatId), getUserInputByState(chatId, InputState.TRADE_BY_ITEM_ID_MANAGER_ITEM_ID));
    }

    public void removeUserTradeByItemIdManagerByUserInput(Long chatId) throws TelegramUserDoesntExistException, TelegramUserInputDoesntExistException {
        telegramUserTradeManagerService.deleteUserTradeByItemIdManagerById(String.valueOf(chatId), getUserInputByState(chatId, InputState.TRADE_BY_ITEM_ID_MANAGER_ITEM_ID));
    }

    public void removeUserTradeByFiltersManagerByUserInput(Long chatId) throws TelegramUserDoesntExistException, TelegramUserInputDoesntExistException {
        telegramUserTradeManagerService.deleteUserTradeByFiltersManagerById(String.valueOf(chatId), getUserInputByState(chatId, InputState.TRADE_BY_FILTERS_MANAGER_NAME));
    }

    public TradeByItemIdManager getUserTradeByItemIdManagerByUserInputItemId(Long chatId) throws TelegramUserDoesntExistException, TelegramUserInputDoesntExistException, TradeByItemIdManagerDoesntExistException {
        return telegramUserTradeManagerService.getUserTradeByItemIdManagerById(String.valueOf(chatId), getUserInputByState(chatId, InputState.TRADE_BY_ITEM_ID_MANAGER_ITEM_ID));
    }

    public TradeByFiltersManager getUserTradeByFiltersManagerByUserInputName(Long chatId) throws TelegramUserDoesntExistException,
            TelegramUserInputDoesntExistException, TradeByFiltersManagerDoesntExistException {
        return telegramUserTradeManagerService.getUserTradeByFiltersManagerById(String.valueOf(chatId), getUserInputByState(chatId, InputState.TRADE_BY_FILTERS_MANAGER_NAME));
    }

    public List<TradeByItemIdManager> getAllUserTradeByItemIdManagers(Long chatId) throws TelegramUserDoesntExistException {
        return telegramUserTradeManagerService.getAllUserTradeByItemIdManagers(String.valueOf(chatId));
    }

    public List<TradeByFiltersManager> getAllUserTradeByFiltersManagers(Long chatId) throws TelegramUserDoesntExistException {
        return telegramUserTradeManagerService.getAllUserTradeByFiltersManagers(String.valueOf(chatId));
    }

    public TradeManagersSettings getUserTradeManagersSettings(Long chatId) throws TelegramUserDoesntExistException {
        return telegramUserService.getTradeManagersSettings(chatId);
    }

    public void setUserTradeManagersSettingsNewManagersAreActiveFlag(Long chatId, boolean flag) throws TelegramUserDoesntExistException {
        telegramUserService.setTradeManagersSettingsNewManagersAreActiveFlag(chatId, flag);
    }

    public void setUserTradeManagersSettingsManagingEnabledFlag(Long chatId, boolean flag) throws TelegramUserDoesntExistException {
        telegramUserService.setTradeManagersSettingsManagingEnabledFlag(chatId, flag);
    }

    public String getUserInputByState(Long chatId, InputState inputState) throws TelegramUserDoesntExistException, TelegramUserInputDoesntExistException {
        return telegramUserService.getUserInputByState(chatId, inputState);
    }

    private String getUserInputValueWithoutCallbackPrefix(Long chatId, InputState inputState)
            throws TelegramUserDoesntExistException,
            TelegramUserInputDoesntExistException,
            MissingCallbackPrefixInUserInputException {
        String callback = telegramUserService.getUserInputByState(chatId, inputState);
        try {
            return callback.substring(Callbacks.INPUT_CALLBACK_PREFIX.length());
        } catch (StringIndexOutOfBoundsException e) {
            throw new MissingCallbackPrefixInUserInputException("Callback prefix is missing in user input: " + callback);
        }
    }
}
