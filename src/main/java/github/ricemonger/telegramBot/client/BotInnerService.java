package github.ricemonger.telegramBot.client;

import github.ricemonger.marketplace.services.*;
import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.executors.InputGroup;
import github.ricemonger.telegramBot.executors.InputState;
import github.ricemonger.utils.dtos.Item;
import github.ricemonger.utils.dtos.ItemFilter;
import github.ricemonger.utils.dtos.Tag;
import github.ricemonger.utils.dtos.TelegramUserInput;
import github.ricemonger.utils.enums.FilterType;
import github.ricemonger.utils.enums.ItemType;
import github.ricemonger.utils.enums.TagGroup;
import github.ricemonger.utils.exceptions.InvalidTelegramUserInput;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.TelegramUserInputDoesntExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class BotInnerService {

    private final CommonValuesService commonValuesService;

    private final TelegramBotClientService telegramBotClientService;

    private final TelegramUserService telegramUserService;

    private final UbiUserService ubiUserService;

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
            telegramBotClientService.sendText(String.valueOf(chatId), getItemString(item));
        }
    }

    public void sendItemsAsMessageByUserInputFilter(Long chatId) {
        List<Item> items = new ArrayList<>(itemStatsService.getAllItems());

        System.out.println("All Items amount: " + items.size());

        ItemFilter itemFilter = getItemFilterByUserInput(chatId);

        List<String> rarityTags = itemFilter.getRarityTags().stream().map(tagService::getValueByName).toList();
        List<String> seasonTags = itemFilter.getSeasonTags().stream().map(tagService::getValueByName).toList();
        List<String> operatorTags = itemFilter.getOperatorTags().stream().map(tagService::getValueByName).toList();
        List<String> weaponTags = itemFilter.getWeaponTags().stream().map(tagService::getValueByName).toList();
        List<String> eventTags = itemFilter.getEventTags().stream().map(tagService::getValueByName).toList();
        List<String> esportsTags = itemFilter.getEsportsTags().stream().map(tagService::getValueByName).toList();
        List<String> otherTags = itemFilter.getOtherTags().stream().map(tagService::getValueByName).toList();

        List<String> filterTags = new ArrayList<>();
        filterTags.addAll(rarityTags);
        filterTags.addAll(seasonTags);
        filterTags.addAll(operatorTags);
        filterTags.addAll(weaponTags);
        filterTags.addAll(eventTags);
        filterTags.addAll(esportsTags);
        filterTags.addAll(otherTags);

        System.out.println("Filter tags: " + filterTags);
        System.out.println(itemFilter);

        List<Item> filtered;
        if (itemFilter.getFilterType().equals(FilterType.ALLOW)) {
            filtered = items.stream()
                    .filter(item -> itemFilter.getItemNamePatterns().stream().anyMatch(s -> item.getName().toLowerCase().contains(s.toLowerCase())))
                    .filter(item -> itemFilter.getItemTypes().isEmpty() || itemFilter.getItemTypes().contains(item.getType()))
                    .filter(item -> filterTags.isEmpty() || item.getTags().stream().anyMatch(tag -> filterTags.contains(tagService.getValueByName(tag))))
                    .filter(item -> itemFilter.getMinPrice() == null || item.getMinSellPrice() >= itemFilter.getMinPrice())
                    .filter(item -> itemFilter.getMaxPrice() == null || item.getMaxBuyPrice() <= itemFilter.getMaxPrice())
                    .filter(item -> itemFilter.getMinLastSoldPrice() == null || item.getLastSoldPrice() >= itemFilter.getMinLastSoldPrice())
                    .filter(item -> itemFilter.getMaxLastSoldPrice() == null || item.getLastSoldPrice() <= itemFilter.getMaxLastSoldPrice())
                    .toList();
        } else {
            filtered = items.stream()
                    .filter(item -> itemFilter.getItemNamePatterns().stream().noneMatch(s -> item.getName().toLowerCase().contains(s.toLowerCase())))
                    .filter(item -> !itemFilter.getItemTypes().contains(item.getType()))
                    .filter(item -> filterTags.isEmpty() || item.getTags().stream().noneMatch(tag -> filterTags.contains(tagService.getValueByName(tag))))
                    .filter(item -> itemFilter.getMinPrice() == null || item.getMinSellPrice() < itemFilter.getMinPrice())
                    .filter(item -> itemFilter.getMaxPrice() == null || item.getMaxBuyPrice() > itemFilter.getMaxPrice())
                    .filter(item -> itemFilter.getMinLastSoldPrice() == null || item.getLastSoldPrice() < itemFilter.getMinLastSoldPrice())
                    .filter(item -> itemFilter.getMaxLastSoldPrice() == null || item.getLastSoldPrice() > itemFilter.getMaxLastSoldPrice())
                    .toList();
        }

        log.debug("Items amount: {}", filtered.size());

        for (Item item : filtered) {
            telegramBotClientService.sendText(String.valueOf(chatId), getItemString(item));
        }
    }

    public void sendDefaultOwnedSpeculativeItemsAsMessagesForUser(Long chatId, String email) {
        Set<String> ownedItemsIds = new HashSet<>(ubiUserService.getOwnedItemsIds(String.valueOf(chatId), email));

        List<Item> speculativeItems = new ArrayList<>(itemStatsService.getAllSpeculativeItemsByExpectedProfit(50, 40, 0, 15000));
        for (Item item : speculativeItems) {
            if (ownedItemsIds.contains(item.getName())) {
                telegramBotClientService.sendText(String.valueOf(chatId), getItemString(item));
            }
        }
    }

    public void sendDefaultNotOwnedSpeculativeItemsAsMessagesForUser(Long chatId, String email) {
        Set<String> ownedItemsIds = new HashSet<>(ubiUserService.getOwnedItemsIds(String.valueOf(chatId), email));

        List<Item> speculativeItems = new ArrayList<>(itemStatsService.getAllSpeculativeItemsByExpectedProfit(50, 40, 0, 15000));
        for (Item item : speculativeItems) {
            if (!ownedItemsIds.contains(item.getName())) {
                telegramBotClientService.sendText(String.valueOf(chatId), getItemString(item));
            }
        }
    }

    public String getItemTypesString() {
        return Arrays.stream(ItemType.values()).map(Enum::name).reduce((s, s2) -> s + "," + s2).orElse("");
    }

    public String getTagsStringByGroup(TagGroup tagGroup) {
        return tagService.getAllTags().stream()
                .filter(tag -> tag.getTagGroup().equals(tagGroup))
                .map(Tag::getName)
                .reduce((s, s2) -> s + "," + s2)
                .orElse("");
    }

    public void saveFilterFromInput(Long chatId) {
        itemFilterService.saveItemFilter(getItemFilterByUserInput(chatId));
    }

    public Collection<String> getFilterNamesForUser(Long chatId) {
        return itemFilterService.getAllItemFilterNamesForUser(String.valueOf(chatId));
    }

    public String getFilterStringFromDatabaseByUserCallback(Long chatId) {
        String name = telegramUserService.getUserInputByState(chatId, InputState.FILTER_NAME);

        name = name.substring(Callbacks.FILTER_CALLBACK_PREFIX.length());

        return getItemFilterString(itemFilterService.getItemFilterById(String.valueOf(chatId), name));
    }

    public void removeFilterByUserCallback(Long chatId) {
        String name = telegramUserService.getUserInputByState(chatId, InputState.FILTER_NAME);

        name = name.substring(Callbacks.FILTER_CALLBACK_PREFIX.length());

        itemFilterService.removeItemFilterById(String.valueOf(chatId), name);
    }

    public String getFilterStringByUserInput(Long chatId) {
        return getItemFilterString(getItemFilterByUserInput(chatId));
    }

    private ItemFilter getItemFilterByUserInput(Long chatId) {
        Collection<TelegramUserInput> inputs = telegramUserService.getAllUserInputs(chatId);

        return itemFilterFromInputsMapper.mapToItemFilter(inputs);
    }

    public String getItemShowSettingsForUser(Long chatId) {

    }

    private String getItemFilterString(ItemFilter itemFilter) {
        String name = itemFilter.getName();
        String filterType = itemFilter.getFilterType().name();
        String isOwned = String.valueOf(itemFilter.getIsOwned());
        String itemNamePatterns = itemFilter.getItemNamePatternsAsString();
        String itemTypes = itemFilter.getItemTypesAsString();
        String rarityTags = itemFilter.getRarityTagsAsString();
        String seasonTags = itemFilter.getSeasonTagsAsString();
        String operatorTags = itemFilter.getOperatorTagsAsString();
        String weaponTags = itemFilter.getWeaponTagsAsString();
        String eventTags = itemFilter.getEventTagsAsString();
        String esportsTags = itemFilter.getEsportsTagsAsString();
        String otherTags = itemFilter.getOtherTagsAsString();
        String minPrice = String.valueOf(itemFilter.getMinPrice());
        String maxPrice = String.valueOf(itemFilter.getMaxPrice());
        String minLastSoldPrice = String.valueOf(itemFilter.getMinLastSoldPrice());
        String maxLastSoldPrice = String.valueOf(itemFilter.getMaxLastSoldPrice());

        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name).append("\n")
                .append("Filter type: ").append(filterType).append("\n")
                .append("Is owned: ").append(isOwned).append("\n")
                .append("Item name pattern: ").append(itemNamePatterns).append("\n")
                .append("Item types: ").append(itemTypes).append("\n")
                .append("Rarity tags: ").append(rarityTags).append("\n")
                .append("Season tags: ").append(seasonTags).append("\n")
                .append("Operator tags: ").append(operatorTags).append("\n")
                .append("Weapon tags: ").append(weaponTags).append("\n")
                .append("Event tags: ").append(eventTags).append("\n")
                .append("Esports tags: ").append(esportsTags).append("\n")
                .append("Other tags: ").append(otherTags).append("\n")
                .append("Min price: ").append(minPrice).append("\n")
                .append("Max price: ").append(maxPrice).append("\n")
                .append("Min last sold price: ").append(minLastSoldPrice).append("\n")
                .append("Max last sold price: ").append(maxLastSoldPrice).append("\n");
        return sb.toString();
    }

    private String getItemString(Item item) {
        String name = item.getName();
        String maxBuyPrice = String.valueOf(item.getMaxBuyPrice());
        String buyOrders = String.valueOf(item.getBuyOrdersCount());
        String minSellPrice = String.valueOf(item.getMinSellPrice());
        String sellOrders = String.valueOf(item.getSellOrdersCount());
        String lastSoldAt;
        if (item.getLastSoldAt() != null) {
            lastSoldAt = item.getLastSoldAt().toString();
        } else {
            lastSoldAt = "null";
        }
        String lastSoldPrice = String.valueOf(item.getLastSoldPrice());
        String pictureUrl = item.getAssetUrl();

        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name).append("\n")
                .append("Min sell price: ").append(minSellPrice).append("\n")
                .append("Sell orders: ").append(sellOrders).append("\n")
                .append("Max buy price: ").append(maxBuyPrice).append("\n")
                .append("Buy orders: ").append(buyOrders).append("\n")
                .append("Last sold price: ").append(lastSoldPrice).append("\n")
                .append("Last sold at: ").append(lastSoldAt).append("\n")
                .append("Picture: ").append(pictureUrl).append("\n");

        return sb.toString();
    }
}
