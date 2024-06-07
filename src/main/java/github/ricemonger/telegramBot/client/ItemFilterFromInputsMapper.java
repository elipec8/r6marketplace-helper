package github.ricemonger.telegramBot.client;

import github.ricemonger.marketplace.services.CommonValuesService;
import github.ricemonger.telegramBot.executors.InputState;
import github.ricemonger.utils.dtos.ItemFilter;
import github.ricemonger.utils.dtos.TelegramUserInput;
import github.ricemonger.utils.enums.FilterType;
import github.ricemonger.utils.enums.IsOwnedFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemFilterFromInputsMapper {

    private final static int MIN_PRICE = 120;
    private final static int MAX_PRICE = 150000;
    private final static String SKIPPED = Callbacks.EMPTY;
    private final CommonValuesService commonValuesService;

    public ItemFilter mapToItemFilter(Collection<TelegramUserInput> inputs) {
        String chatId = new ArrayList<>(inputs).get(0).getChatId();
        String name = getValueByState(inputs, InputState.FILTER_NAME);
        String filterTypeString = getValueByState(inputs, InputState.FILTER_TYPE);
        String isOwnedString = getValueByState(inputs, InputState.FILTER_IS_OWNED);
        String itemNamePatternsString = getValueByState(inputs, InputState.FILTER_ITEM_NAME_PATTERNS);
        String itemTypesString = getValueByState(inputs, InputState.FILTER_ITEM_TYPES);
        String rarityTagsString = getValueByState(inputs, InputState.FILTER_ITEM_TAGS_RARITY);
        String seasonTagsString = getValueByState(inputs, InputState.FILTER_ITEM_TAGS_SEASONS);
        String operatorTagsString = getValueByState(inputs, InputState.FILTER_ITEM_TAGS_OPERATORS);
        String weaponTagsString = getValueByState(inputs, InputState.FILTER_ITEM_TAGS_WEAPONS);
        String eventTagsString = getValueByState(inputs, InputState.FILTER_ITEM_TAGS_EVENTS);
        String esportsTagsString = getValueByState(inputs, InputState.FILTER_ITEM_TAGS_ESPORTS);
        String otherTagsString = getValueByState(inputs, InputState.FILTER_ITEM_TAGS_OTHER);
        String minPriceString = getValueByState(inputs, InputState.FILTER_MIN_PRICE);
        String maxPriceString = getValueByState(inputs, InputState.FILTER_MAX_PRICE);
        String minLastSoldPriceString = getValueByState(inputs, InputState.FILTER_MIN_LAST_SOLD_PRICE);
        String maxLastSoldPriceString = getValueByState(inputs, InputState.FILTER_MAX_LAST_SOLD_PRICE);
        String boundaryLastSoldDateString = getValueByState(inputs, InputState.FILTER_LAST_SOLD_DATE);

        ItemFilter itemFilter = new ItemFilter();
        itemFilter.setChatId(chatId);
        itemFilter.setName(name);

        if (filterTypeString.equals(Callbacks.FILTER_TYPE_ALLOW)) {
            itemFilter.setFilterType(FilterType.ALLOW);
        } else if (filterTypeString.equals(Callbacks.FILTER_TYPE_DENY)) {
            itemFilter.setFilterType(FilterType.DENY);
        } else {
            log.error("Invalid filter type: " + filterTypeString);
            itemFilter.setFilterType(FilterType.ALLOW);
        }

        if (isOwnedString.equals(Callbacks.FILTER_ITEM_IS_OWNED)) {
            itemFilter.setIsOwned(IsOwnedFilter.OWNED);
        } else if (isOwnedString.equals(Callbacks.FILTER_ITEM_IS_NOT_OWNED)) {
            itemFilter.setIsOwned(IsOwnedFilter.NOT_OWNED);
        } else if (isOwnedString.equals(Callbacks.FILTER_ITEM_IS_OWNED_ANY) || isOwnedString.equals(SKIPPED)) {
            itemFilter.setIsOwned(IsOwnedFilter.ANY);
        } else {
            log.error("Invalid isOwned: " + isOwnedString);
            itemFilter.setIsOwned(IsOwnedFilter.ANY);
        }

        if (itemNamePatternsString.equals(SKIPPED)) {
            itemFilter.setItemNamePatternsFromString("");
        } else {
            itemFilter.setItemNamePatternsFromString(itemNamePatternsString);
        }

        if (itemTypesString.equals(SKIPPED)) {
            itemFilter.setItemTypesFromString("");
        } else {
            itemFilter.setItemTypesFromString(itemTypesString);
        }

        if (rarityTagsString.equals(SKIPPED)) {
            itemFilter.setRarityTagsFromString("");
        } else {
            itemFilter.setRarityTagsFromString(rarityTagsString);
        }

        if (seasonTagsString.equals(SKIPPED)) {
            itemFilter.setSeasonTagsFromString("");
        } else {
            itemFilter.setSeasonTagsFromString(seasonTagsString);
        }

        if (operatorTagsString.equals(SKIPPED)) {
            itemFilter.setOperatorTagsFromString("");
        } else {
            itemFilter.setOperatorTagsFromString(operatorTagsString);
        }

        if (weaponTagsString.equals(SKIPPED)) {
            itemFilter.setWeaponTagsFromString("");
        } else {
            itemFilter.setWeaponTagsFromString(weaponTagsString);
        }

        if (eventTagsString.equals(SKIPPED)) {
            itemFilter.setEventTagsFromString("");
        } else {
            itemFilter.setEventTagsFromString(eventTagsString);
        }

        if (esportsTagsString.equals(SKIPPED)) {
            itemFilter.setEsportsTagsFromString("");
        } else {
            itemFilter.setEsportsTagsFromString(esportsTagsString);
        }

        if (otherTagsString.equals(SKIPPED)) {
            itemFilter.setOtherTagsFromString("");
        } else {
            itemFilter.setOtherTagsFromString(otherTagsString);
        }

        if (minPriceString.equals(SKIPPED)) {
            itemFilter.setMinPrice(MIN_PRICE);
        } else {
            try {
                int price = Integer.parseInt(minPriceString);
                itemFilter.setMinPrice(Math.max(price, MIN_PRICE));
            } catch (NumberFormatException e) {
                log.error("Invalid minPrice: " + minPriceString);
                itemFilter.setMinPrice(MIN_PRICE);
            }
        }

        if (maxPriceString.equals(SKIPPED)) {
            itemFilter.setMaxPrice(MAX_PRICE);
        } else {
            try {
                int price = Integer.parseInt(maxPriceString);
                itemFilter.setMaxPrice(Math.min(price, MAX_PRICE));
            } catch (NumberFormatException e) {
                log.error("Invalid maxPrice: " + maxPriceString);
                itemFilter.setMaxPrice(MAX_PRICE);
            }
        }

        if (minLastSoldPriceString.equals(SKIPPED)) {
            itemFilter.setMinLastSoldPrice(MIN_PRICE);
        } else {
            try {
                int price = Integer.parseInt(minLastSoldPriceString);
                itemFilter.setMinLastSoldPrice(Math.max(price, MIN_PRICE));
            } catch (NumberFormatException e) {
                log.error("Invalid minLastSoldPrice: " + minLastSoldPriceString);
                itemFilter.setMinLastSoldPrice(MIN_PRICE);
            }
        }

        if (maxLastSoldPriceString.equals(SKIPPED)) {
            itemFilter.setMaxLastSoldPrice(MAX_PRICE);
        } else {
            try {
                int price = Integer.parseInt(maxLastSoldPriceString);
                itemFilter.setMaxLastSoldPrice(Math.min(price, MAX_PRICE));
            } catch (NumberFormatException e) {
                log.error("Invalid maxLastSoldPrice: " + maxLastSoldPriceString);
                itemFilter.setMaxLastSoldPrice(MAX_PRICE);
            }
        }

        return itemFilter;
    }

    private String getValueByState(Collection<TelegramUserInput> inputs, InputState inputState) {
        return inputs.stream().filter(input -> input.getInputState().equals(inputState)).findFirst().orElse(new TelegramUserInput("",
                InputState.BASE, "")).getValue();
    }
}
