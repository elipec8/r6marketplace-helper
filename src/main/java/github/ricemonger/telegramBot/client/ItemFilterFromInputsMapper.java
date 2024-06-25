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

        itemFilter.addTags(getTagsFromNames(getTagNamesListFromString(rarityTagsString)));

        itemFilter.addTags(getTagsFromNames(getTagNamesListFromString(seasonTagsString)));

        itemFilter.addTags(getTagsFromNames(getTagNamesListFromString(operatorTagsString)));

        itemFilter.addTags(getTagsFromNames(getTagNamesListFromString(weaponTagsString)));

        itemFilter.addTags(getTagsFromNames(getTagNamesListFromString(eventTagsString)));

        itemFilter.addTags(getTagsFromNames(getTagNamesListFromString(esportsTagsString)));

        itemFilter.addTags(getTagsFromNames(getTagNamesListFromString(otherTagsString)));

        if (minPriceString.equals(SKIPPED)) {
            itemFilter.setMinPrice(commonValuesService.getMinimumMarketplacePrice());
        } else {
            try {
                int price = Integer.parseInt(minPriceString);
                itemFilter.setMinPrice(Math.max(price, commonValuesService.getMinimumMarketplacePrice()));
            } catch (NumberFormatException e) {
                log.error("Invalid minPrice: " + minPriceString);
                itemFilter.setMinPrice(commonValuesService.getMinimumMarketplacePrice());
            }
        }

        if (maxPriceString.equals(SKIPPED)) {
            itemFilter.setMaxPrice(commonValuesService.getMaximumMarketplacePrice());
        } else {
            try {
                int price = Integer.parseInt(maxPriceString);
                itemFilter.setMaxPrice(Math.min(price, commonValuesService.getMaximumMarketplacePrice()));
            } catch (NumberFormatException e) {
                log.error("Invalid maxPrice: " + maxPriceString);
                itemFilter.setMaxPrice(commonValuesService.getMaximumMarketplacePrice());
            }
        }

        if (minLastSoldPriceString.equals(SKIPPED)) {
            itemFilter.setMinLastSoldPrice(commonValuesService.getMinimumMarketplacePrice());
        } else {
            try {
                int price = Integer.parseInt(minLastSoldPriceString);
                itemFilter.setMinLastSoldPrice(Math.max(price, commonValuesService.getMinimumMarketplacePrice()));
            } catch (NumberFormatException e) {
                log.error("Invalid minLastSoldPrice: " + minLastSoldPriceString);
                itemFilter.setMinLastSoldPrice(commonValuesService.getMinimumMarketplacePrice());
            }
        }

        if (maxLastSoldPriceString.equals(SKIPPED)) {
            itemFilter.setMaxLastSoldPrice(commonValuesService.getMaximumMarketplacePrice());
        } else {
            try {
                int price = Integer.parseInt(maxLastSoldPriceString);
                itemFilter.setMaxLastSoldPrice(Math.min(price, commonValuesService.getMaximumMarketplacePrice()));
            } catch (NumberFormatException e) {
                log.error("Invalid maxLastSoldPrice: " + maxLastSoldPriceString);
                itemFilter.setMaxLastSoldPrice(commonValuesService.getMaximumMarketplacePrice());
            }
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
