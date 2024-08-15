package github.ricemonger.telegramBot.client;

import github.ricemonger.marketplace.services.CommonValuesService;
import github.ricemonger.marketplace.services.TagService;
import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.utils.dtos.ItemFilter;
import github.ricemonger.utils.dtos.Tag;
import github.ricemonger.utils.dtos.TelegramUserInput;
import github.ricemonger.utils.enums.FilterType;
import github.ricemonger.utils.enums.IsOwnedFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemFilterFromInputsMapper {

    private final static String SKIPPED = Callbacks.EMPTY;

    private final CommonValuesService commonValuesService;

    private final TagService tagService;

    public ItemFilter mapToItemFilter(Collection<TelegramUserInput> inputs) {
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

        ItemFilter itemFilter = new ItemFilter();

        itemFilter.setName(name);

        if (filterTypeString.equals(Callbacks.FILTER_TYPE_DENY)) {
            itemFilter.setFilterType(FilterType.DENY);
        } else {
            itemFilter.setFilterType(FilterType.ALLOW);
        }

        if (isOwnedString.equals(Callbacks.FILTER_ITEM_IS_OWNED)) {
            itemFilter.setIsOwned(IsOwnedFilter.OWNED);
        } else if (isOwnedString.equals(Callbacks.FILTER_ITEM_IS_NOT_OWNED)) {
            itemFilter.setIsOwned(IsOwnedFilter.NOT_OWNED);
        } else {
            itemFilter.setIsOwned(IsOwnedFilter.ANY);
        }

        if (itemNamePatternsString.equals(SKIPPED) || itemNamePatternsString.isBlank()) {
            itemFilter.setItemNamePatternsFromString("");
        } else {
            itemFilter.setItemNamePatternsFromString(itemNamePatternsString);
        }

        if (itemTypesString.equals(SKIPPED) || itemTypesString.isBlank()) {
            itemFilter.setItemTypesFromString("");
        } else {
            itemFilter.setItemTypesFromString(itemTypesString);
        }

        itemFilter.addTags(getTagsFromNames(getTagNamesListFromString(rarityTagsString)));
        itemFilter.addTags(getTagsFromNames(getTagNamesListFromString(seasonTagsString)));
        itemFilter.addTags(getTagsFromNames(getTagNamesListFromString(operatorTagsString)));
        itemFilter.addTags(getTagsFromNames(getTagNamesListFromString(weaponTagsString)));
        itemFilter.addTags(getTagsFromNames(getTagNamesListFromString(eventTagsString)));
        itemFilter.addTags(getTagsFromNames(getTagNamesListFromString(esportsTagsString)));
        itemFilter.addTags(getTagsFromNames(getTagNamesListFromString(otherTagsString)));

        try {
            int price = Integer.parseInt(minPriceString);

            if (price > commonValuesService.getMaximumMarketplacePrice()) {
                itemFilter.setMinPrice(commonValuesService.getMaximumMarketplacePrice());
            } else if (price < commonValuesService.getMinimumMarketplacePrice()) {
                itemFilter.setMinPrice(commonValuesService.getMinimumMarketplacePrice());
            } else {
                itemFilter.setMinPrice(price);
            }

        } catch (NumberFormatException e) {
            itemFilter.setMinPrice(commonValuesService.getMinimumMarketplacePrice());
        }

        try {
            int price = Integer.parseInt(maxPriceString);

            if (price > commonValuesService.getMaximumMarketplacePrice()) {
                itemFilter.setMaxPrice(commonValuesService.getMaximumMarketplacePrice());
            } else if (price < commonValuesService.getMinimumMarketplacePrice()) {
                itemFilter.setMaxPrice(commonValuesService.getMinimumMarketplacePrice());
            } else {
                itemFilter.setMaxPrice(price);
            }

        } catch (NumberFormatException e) {
            itemFilter.setMaxPrice(commonValuesService.getMaximumMarketplacePrice());
        }

        try {
            int price = Integer.parseInt(minLastSoldPriceString);

            if (price > commonValuesService.getMaximumMarketplacePrice()) {
                itemFilter.setMinLastSoldPrice(commonValuesService.getMaximumMarketplacePrice());
            } else if (price < commonValuesService.getMinimumMarketplacePrice()) {
                itemFilter.setMinLastSoldPrice(commonValuesService.getMinimumMarketplacePrice());
            } else {
                itemFilter.setMinLastSoldPrice(price);
            }

        } catch (NumberFormatException e) {
            itemFilter.setMinLastSoldPrice(commonValuesService.getMinimumMarketplacePrice());
        }

        try {
            int price = Integer.parseInt(maxLastSoldPriceString);

            if (price > commonValuesService.getMaximumMarketplacePrice()) {
                itemFilter.setMaxLastSoldPrice(commonValuesService.getMaximumMarketplacePrice());
            } else if (price < commonValuesService.getMinimumMarketplacePrice()) {
                itemFilter.setMaxLastSoldPrice(commonValuesService.getMinimumMarketplacePrice());
            } else {
                itemFilter.setMaxLastSoldPrice(price);
            }

        } catch (NumberFormatException e) {
            itemFilter.setMaxLastSoldPrice(commonValuesService.getMaximumMarketplacePrice());
        }

        return itemFilter;
    }

    private String getValueByState(Collection<TelegramUserInput> inputs, InputState inputState) {
        return inputs.stream().filter(input -> input.getInputState().equals(inputState)).findFirst().orElse(new TelegramUserInput("",
                InputState.BASE, "")).getValue();
    }

    private Collection<Tag> getTagsFromNames(Collection<String> tagNames) {
        return tagService.getTagsByNames(tagNames);
    }

    private List<String> getTagNamesListFromString(String tags) {
        if (tags == null || tags.isEmpty() || tags.equals(SKIPPED)) {
            return new ArrayList<>();
        } else {
            return Arrays.stream(tags.split("[,|]")).map(String::trim).toList();
        }
    }
}
