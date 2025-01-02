package github.ricemonger.telegramBot.update_consumer;

import github.ricemonger.marketplace.services.CommonValuesService;
import github.ricemonger.marketplace.services.DTOs.TelegramUserInput;
import github.ricemonger.marketplace.services.DTOs.TradeByFiltersManager;
import github.ricemonger.marketplace.services.ItemService;
import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.DTOs.personal.TradeByItemIdManager;
import github.ricemonger.utils.enums.InputState;
import github.ricemonger.utils.enums.TradeOperationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TradeManagerFromInputsMapper {

    private final static String SKIPPED = Callbacks.EMPTY;

    private final CommonValuesService commonValuesService;

    private final ItemService itemService;

    public TradeByItemIdManager generateTradeByItemIdManagerByUserInput(Collection<TelegramUserInput> inputs,
                                                                        TradeOperationType tradeOperationType,
                                                                        boolean enabledFlag) {
        String itemId = getValueByState(inputs, InputState.TRADE_BY_ITEM_ID_MANAGER_ITEM_ID);
        String boundarySellPrice = getValueByState(inputs, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_BOUNDARY_SELL_PRICE);
        String boundaryBuyPrice = getValueByState(inputs, InputState.TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_BUY_PRICE);
        String priority = getValueByState(inputs, InputState.TRADE_BY_ITEM_ID_MANAGER_PRIORITY);

        Item item = itemService.getItemById(itemId);

        int limitMinPrice = commonValuesService.getMinimumPriceByRarity(item.getRarity());
        int limitMaxPrice = commonValuesService.getMaximumPriceByRarity(item.getRarity());

        int boundSellPrice = parseIntValue(boundarySellPrice, limitMinPrice, limitMaxPrice, limitMinPrice);
        int boundBuyPrice = parseIntValue(boundaryBuyPrice, limitMinPrice, limitMaxPrice, limitMaxPrice);

        int prior;
        try {
            prior = Integer.parseInt(priority);
        } catch (NumberFormatException | NullPointerException e) {
            prior = 1;
        }

        TradeByItemIdManager tradeByItemIdManager = new TradeByItemIdManager();
        tradeByItemIdManager.setTradeOperationType(tradeOperationType);
        tradeByItemIdManager.setItemId(itemId);
        tradeByItemIdManager.setEnabled(enabledFlag);
        tradeByItemIdManager.setSellBoundaryPrice(boundSellPrice);
        tradeByItemIdManager.setBuyBoundaryPrice(boundBuyPrice);
        tradeByItemIdManager.setPriorityMultiplier(prior);

        return tradeByItemIdManager;
    }

    public TradeByFiltersManager generateTradeByFiltersManagerByUserInput(Collection<TelegramUserInput> inputs, boolean enabledFlag) {
        String name = getValueByState(inputs, InputState.TRADE_BY_FILTERS_MANAGER_NAME);
        String tradeType = getValueByState(inputs, InputState.TRADE_BY_FILTERS_MANAGER_TRADE_TYPE);
        String minBuySellProfit = getValueByState(inputs, InputState.TRADE_BY_FILTERS_MANAGER_MIN_BUY_SELL_PROFIT);
        String minProfitPercent = getValueByState(inputs, InputState.TRADE_BY_FILTERS_MANAGER_MIN_MEDIAN_PRICE_DIFFERENCE_PERCENT);
        String priority = getValueByState(inputs, InputState.TRADE_BY_FILTERS_MANAGER_PRIORITY);
        List<String> appliedFilters = getFiltersNamesListFromString(getValueByState(inputs, InputState.TRADE_BY_FILTERS_MANAGER_FILTERS_NAMES));

        TradeByFiltersManager tradeByFiltersManager = new TradeByFiltersManager();
        tradeByFiltersManager.setName(name);
        tradeByFiltersManager.setEnabled(enabledFlag);

        if (tradeType.equals(Callbacks.TRADE_BY_FILTERS_MANAGER_TYPE_BUY_EDIT)) {
            tradeByFiltersManager.setTradeOperationType(TradeOperationType.BUY);
        } else if (tradeType.equals(Callbacks.TRADE_BY_FILTERS_MANAGER_TYPE_SELL_EDIT)) {
            tradeByFiltersManager.setTradeOperationType(TradeOperationType.SELL);
        } else {
            tradeByFiltersManager.setTradeOperationType(TradeOperationType.BUY_AND_SELL);
        }

        int maxMarketplacePrice = commonValuesService.getMaximumMarketplacePrice();

        tradeByFiltersManager.setAppliedFilters(appliedFilters);
        tradeByFiltersManager.setMinDifferenceFromMedianPrice(parseIntValue(minBuySellProfit, -1 * maxMarketplacePrice, maxMarketplacePrice, 50));
        tradeByFiltersManager.setMinDifferenceFromMedianPricePercent(parseIntValue(minProfitPercent, Integer.MIN_VALUE, Integer.MAX_VALUE, 10));
        tradeByFiltersManager.setPriorityMultiplier(parseIntValue(priority, 1, Integer.MAX_VALUE, 1));

        return tradeByFiltersManager;
    }

    private int parseIntValue(String string, int minValue, int maxValue, int invalidCaseValue) {
        int price;
        try {
            price = Integer.parseInt(string);
        } catch (NumberFormatException | NullPointerException e) {
            return invalidCaseValue;
        }

        if (price < minValue) {
            price = minValue;
        } else if (price > maxValue) {
            price = maxValue;
        }

        return price;
    }

    private List<String> getFiltersNamesListFromString(String tags) {
        if (tags == null || tags.isEmpty() || tags.equals(SKIPPED)) {
            return new ArrayList<>();
        } else {
            return Arrays.stream(tags.split("[,|]")).map(String::trim).toList();
        }
    }

    private String getValueByState(Collection<TelegramUserInput> inputs, InputState inputState) {
        return inputs.stream().filter(input -> input.getInputState().equals(inputState)).findFirst().orElse(new TelegramUserInput("",
                InputState.BASE, "")).getValue();
    }
}
