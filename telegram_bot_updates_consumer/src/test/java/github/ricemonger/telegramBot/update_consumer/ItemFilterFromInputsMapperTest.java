package github.ricemonger.telegramBot.update_consumer;

import github.ricemonger.marketplace.services.CommonValuesService;
import github.ricemonger.marketplace.services.DTOs.TelegramUserInput;
import github.ricemonger.marketplace.services.TagService;
import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.utils.DTOs.common.Tag;
import github.ricemonger.utils.DTOs.personal.ItemFilter;
import github.ricemonger.utils.enums.FilterType;
import github.ricemonger.utils.enums.InputState;
import github.ricemonger.utils.enums.ItemType;
import github.ricemonger.utils.enums.TagGroup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class ItemFilterFromInputsMapperTest {
    @Autowired
    private ItemFilterFromInputsMapper itemFilterFromInputsMapper;
    @MockBean
    private CommonValuesService commonValuesService;
    @MockBean
    private TagService tagService;

    @Test
    public void mapToItemFilter_should_map_inputs_to_filter_with_valid_inputs() {
        Tag rarity1 = new Tag("rarity_value_1", "rarity_name_1", TagGroup.Rarity);
        Tag rarity2 = new Tag("rarity_value_2", "rarity_name_2", TagGroup.Rarity);

        List<Tag> tags = List.of(rarity1, rarity2);
        List<String> tagNames = List.of(rarity1.getName(), rarity2.getName());
        when(tagService.getTagsByNames(argThat(a -> a.containsAll(tagNames)))).thenReturn(List.of(rarity1, rarity2));

        when(commonValuesService.getMinimumMarketplacePrice()).thenReturn(120);
        when(commonValuesService.getMaximumMarketplacePrice()).thenReturn(150_000);

        String chatId = "chatId";
        String rarities = rarity1.getName() + "," + rarity2.getName();
        List<TelegramUserInput> inputs = new ArrayList<>();

        inputs.add(new TelegramUserInput(chatId, InputState.ITEM_FILTER_NAME, "name"));
        inputs.add(new TelegramUserInput(chatId, InputState.ITEM_FILTER_TYPE, Callbacks.ITEM_FILTER_TYPE_DENY));
        inputs.add(new TelegramUserInput(chatId, InputState.ITEM_FILTER_ITEM_NAME_PATTERNS, "pattern1,pattern2"));
        inputs.add(new TelegramUserInput(chatId, InputState.ITEM_FILTER_ITEM_TYPES, ItemType.WeaponSkin.name() + "," + ItemType.DroneSkin.name()));
        inputs.add(new TelegramUserInput(chatId, InputState.ITEM_FILTER_ITEM_TAGS_RARITY, rarities));
        inputs.add(new TelegramUserInput(chatId, InputState.ITEM_FILTER_ITEM_TAGS_SEASONS, Callbacks.EMPTY));
        inputs.add(new TelegramUserInput(chatId, InputState.ITEM_FILTER_ITEM_TAGS_OPERATORS, Callbacks.EMPTY));
        inputs.add(new TelegramUserInput(chatId, InputState.ITEM_FILTER_ITEM_TAGS_WEAPONS, Callbacks.EMPTY));
        inputs.add(new TelegramUserInput(chatId, InputState.ITEM_FILTER_ITEM_TAGS_EVENTS, Callbacks.EMPTY));
        inputs.add(new TelegramUserInput(chatId, InputState.ITEM_FILTER_ITEM_TAGS_ESPORTS, Callbacks.EMPTY));
        inputs.add(new TelegramUserInput(chatId, InputState.ITEM_FILTER_ITEM_TAGS_OTHER, Callbacks.EMPTY));
        inputs.add(new TelegramUserInput(chatId, InputState.ITEM_FILTER_MIN_PRICE, "125"));
        inputs.add(new TelegramUserInput(chatId, InputState.ITEM_FILTER_MAX_PRICE, "1250"));

        ItemFilter expectedFilter = getDefaultFilter(tags);

        ItemFilter actualFilter = itemFilterFromInputsMapper.generateItemFilterByUserInput(inputs);

        assertEquals(expectedFilter, actualFilter);
    }

    @Test
    public void mapToItemFilter_should_map_inputs_to_filter_with_valid_inputs_high_price() {
        Tag season1 = new Tag("season_value_1", "season_name_1", TagGroup.Season);
        Tag season2 = new Tag("season_value_2", "season_name_2", TagGroup.Season);

        List<Tag> tags = List.of(season1, season2);
        List<String> tagNames = List.of(season1.getName(), season2.getName());
        when(tagService.getTagsByNames(argThat(a -> a.containsAll(tagNames)))).thenReturn(List.of(season1, season2));

        when(commonValuesService.getMinimumMarketplacePrice()).thenReturn(120);
        when(commonValuesService.getMaximumMarketplacePrice()).thenReturn(150_000);

        String chatId = "chatId";
        String seasons = season1.getName() + "," + season2.getName();
        List<TelegramUserInput> inputs = new ArrayList<>();

        inputs.add(new TelegramUserInput(chatId, InputState.ITEM_FILTER_NAME, "name"));
        inputs.add(new TelegramUserInput(chatId, InputState.ITEM_FILTER_TYPE, Callbacks.ITEM_FILTER_TYPE_ALLOW));
        inputs.add(new TelegramUserInput(chatId, InputState.ITEM_FILTER_ITEM_NAME_PATTERNS, "pattern1,pattern2"));
        inputs.add(new TelegramUserInput(chatId, InputState.ITEM_FILTER_ITEM_TYPES, ItemType.WeaponSkin.name() + "," + ItemType.DroneSkin.name()));
        inputs.add(new TelegramUserInput(chatId, InputState.ITEM_FILTER_ITEM_TAGS_SEASONS, seasons));
        inputs.add(new TelegramUserInput(chatId, InputState.ITEM_FILTER_MIN_PRICE, "150001"));
        inputs.add(new TelegramUserInput(chatId, InputState.ITEM_FILTER_MAX_PRICE, "150001"));

        ItemFilter expectedFilter = getDefaultFilter(tags);
        expectedFilter.setFilterType(FilterType.ALLOW);
        expectedFilter.setMinSellPrice(150_000);
        expectedFilter.setMaxBuyPrice(150_000);

        ItemFilter actualFilter = itemFilterFromInputsMapper.generateItemFilterByUserInput(inputs);

        assertEquals(expectedFilter, actualFilter);
    }

    @Test
    public void mapToItemFilter_should_map_inputs_to_filter_with_invalid_inputs_low_price() {
        Tag operator1 = new Tag("operator_value_1", "operator_name_1", TagGroup.Operator);
        Tag operator2 = new Tag("operator_value_2", "operator_name_2", TagGroup.Operator);

        List<Tag> tags = List.of(operator1, operator2);
        List<String> tagNames = List.of(operator1.getName(), operator2.getName());
        when(tagService.getTagsByNames(argThat(a -> a.containsAll(tagNames)))).thenReturn(List.of(operator1, operator2));

        when(commonValuesService.getMinimumMarketplacePrice()).thenReturn(120);
        when(commonValuesService.getMaximumMarketplacePrice()).thenReturn(150_000);

        String chatId = "chatId";
        String operators = operator1.getName() + "," + operator2.getName();
        List<TelegramUserInput> inputs = new ArrayList<>();

        inputs.add(new TelegramUserInput(chatId, InputState.ITEM_FILTER_ITEM_NAME_PATTERNS, "pattern1"));
        inputs.add(new TelegramUserInput(chatId, InputState.ITEM_FILTER_ITEM_TYPES, ItemType.WeaponSkin.name() + "," + ItemType.DroneSkin.name()));
        inputs.add(new TelegramUserInput(chatId, InputState.ITEM_FILTER_ITEM_TAGS_OPERATORS, operators));
        inputs.add(new TelegramUserInput(chatId, InputState.ITEM_FILTER_MIN_PRICE, "119"));
        inputs.add(new TelegramUserInput(chatId, InputState.ITEM_FILTER_MAX_PRICE, "119"));

        ItemFilter expectedFilter = getDefaultFilter(tags);
        expectedFilter.setName("");
        expectedFilter.setFilterType(FilterType.ALLOW);
        expectedFilter.setItemNamePatternsFromString("pattern1");
        expectedFilter.setMinSellPrice(120);
        expectedFilter.setMaxBuyPrice(120);

        ItemFilter actualFilter = itemFilterFromInputsMapper.generateItemFilterByUserInput(inputs);

        assertEquals(expectedFilter, actualFilter);
    }

    private ItemFilter getDefaultFilter(List<Tag> tags) {
        ItemFilter itemFilter = new ItemFilter();
        itemFilter.setName("name");
        itemFilter.setFilterType(FilterType.DENY);
        itemFilter.setItemNamePatternsFromString("pattern1,pattern2");
        itemFilter.setItemTypesFromString(ItemType.WeaponSkin.name() + "," + ItemType.DroneSkin.name());
        itemFilter.addTags(tags);
        itemFilter.setMinSellPrice(125);
        itemFilter.setMaxBuyPrice(1250);

        return itemFilter;
    }
}