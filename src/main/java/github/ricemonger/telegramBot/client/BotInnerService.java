package github.ricemonger.telegramBot.client;

import github.ricemonger.marketplace.services.*;
import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.executors.InputGroup;
import github.ricemonger.telegramBot.executors.InputState;
import github.ricemonger.utils.dtos.*;
import github.ricemonger.utils.enums.ItemType;
import github.ricemonger.utils.enums.TagGroup;
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

    private final ItemFilterFromInputsMapper itemFilterFromInputsMapper;

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
        telegramUserService.registerTelegramUser(chatId);
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
        return telegramUserService.getCredentialsEmailsList(chatId);
    }

    public void sendDefaultSpeculativeItemsAsMessages(Long chatId) {
        List<Item> speculativeItems = new ArrayList<>(itemStatsService.getAllSpeculativeItemsByExpectedProfit(50, 40, 0, 15000));
        log.debug("Speculative items amount: {}", speculativeItems.size());
        for (Item item : speculativeItems) {
            telegramBotClientService.sendText(String.valueOf(chatId), item.toString());
        }
    }

    public String getItemTypesString() {
        return Arrays.stream(ItemType.values()).map(Enum::name).reduce((s, s2) -> s + "," + s2).orElse("");
    }

    public String getAllTagsStringByGroup(TagGroup tagGroup) {
        return tagService.getAllTags().stream()
                .filter(tag -> tag.getTagGroup().equals(tagGroup))
                .map(Tag::getName)
                .reduce((s, s2) -> s + "," + s2)
                .orElse("");
    }

    public void saveFilterFromInput(Long chatId) {
        itemFilterService.saveItemFilter(getItemFilterByUserInput(chatId));
    }

    public Collection<String> getAllFilterNamesForUser(Long chatId) {
        return itemFilterService.getAllItemFilterNamesForUser(String.valueOf(chatId));
    }

    public ItemFilter getFilterFromDatabaseByUserInputCallback(Long chatId) {
        return itemFilterService.getItemFilterById(String.valueOf(chatId), getInputValueFromCallbackData(chatId, InputState.FILTER_NAME));
    }

    public void removeFilterByUserCallback(Long chatId) {
        itemFilterService.removeItemFilterById(String.valueOf(chatId), getInputValueFromCallbackData(chatId, InputState.FILTER_NAME));
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
            }
        }
    }

    private String getInputValueFromCallbackData(Long chatId, InputState inputState) {
        String callback = telegramUserService.getUserInputByState(chatId, inputState);
        return callback.substring(Callbacks.INPUT_CALLBACK_PREFIX.length());
    }

    public void setUserFewItemsInMessage(Long chatId, boolean flag) {
        telegramUserService.setItemShowFewItemsInMessageFlag(chatId, flag);
    }

    public void setUserMessageLimitByInput(Long chatId) {
        Integer limit = commonValuesService.getMaximumTelegramMessageLimit();
        try {
            limit = Integer.parseInt(getUserInputByState(chatId, InputState.ITEMS_SHOW_SETTING_MESSAGE_LIMIT));
        } catch (TelegramUserInputDoesntExistException | NumberFormatException e) {
            if (limit == null) {
                limit = 50;
            }
        }
        telegramUserService.setItemShowMessagesLimit(chatId, limit);
    }

    public void setUserShownFieldsByInput(Long chatId) {
        telegramUserService.setItemShowSettingsByUserInput(chatId, Callbacks.INPUT_CALLBACK_TRUE, Callbacks.INPUT_CALLBACK_FALSE);
    }

    public void changeAppliedFiltersByUserInput(Long chatId) {
        String filterName = getUserInputByState(chatId, InputState.FILTER_NAME);
        boolean addOrRemove = Callbacks.INPUT_CALLBACK_TRUE.equals(getUserInputByState(chatId,InputState.ITEMS_SHOW_SETTINGS_APPLIED_FILTER_ADD_OR_REMOVE));

        List<String> appliedFilters = telegramUserService.getItemShowSettings(chatId).getItemShowAppliedFilters().stream().map(ItemFilter::getName).toList();

        if (addOrRemove && appliedFilters.contains(filterName)) {
            telegramUserService.removeItemShowAppliedFilter(chatId, filterName);
        }
        else if (addOrRemove && !appliedFilters.contains(filterName)){
            telegramUserService.addItemShowAppliedFilter(chatId, filterName);
        }
    }
}
