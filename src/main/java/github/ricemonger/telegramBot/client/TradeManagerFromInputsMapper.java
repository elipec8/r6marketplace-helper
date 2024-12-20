package github.ricemonger.telegramBot.client;

import github.ricemonger.marketplace.services.CommonValuesService;
import github.ricemonger.marketplace.services.PotentialTradeStatsService;
import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.utils.DTOs.ItemFilter;
import github.ricemonger.utils.DTOs.TelegramUserInput;
import github.ricemonger.utils.DTOs.TradeByFiltersManager;
import github.ricemonger.utils.DTOs.TradeByItemIdManager;
import github.ricemonger.utils.DTOs.items.Item;
import github.ricemonger.utils.enums.TradeOperationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TradeManagerFromInputsMapper {

    private final CommonValuesService commonValuesService;

    private final PotentialTradeStatsService potentialTradeStatsService;

    public TradeByItemIdManager mapToTradeByItemIdManager(Collection<TelegramUserInput> inputs,
                                                          TradeOperationType tradeOperationType,
                                                          Item item,
                                                          boolean enabledFlag) {
        String itemId = getValueByState(inputs, InputState.TRADE_BY_ITEM_ID_MANAGER_ITEM_ID);
        String boundarySellPrice = getValueByState(inputs, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_BOUNDARY_SELL_PRICE);
        String boundaryBuyPrice = getValueByState(inputs, InputState.TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_BUY_PRICE);
        String priority = getValueByState(inputs, InputState.TRADE_BY_ITEM_ID_MANAGER_PRIORITY);

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

    public TradeByFiltersManager mapToTradeByFiltersManager(Collection<TelegramUserInput> inputs,
                                                            int maxMarketplacePrice,
                                                            List<ItemFilter> itemFilters,
                                                            boolean enabledFlag) {
        String name = getValueByState(inputs, InputState.TRADE_BY_FILTERS_MANAGER_NAME);
        String tradeType = getValueByState(inputs, InputState.TRADE_BY_FILTERS_MANAGER_TRADE_TYPE);
        String minBuySellProfit = getValueByState(inputs, InputState.TRADE_BY_FILTERS_MANAGER_MIN_BUY_SELL_PROFIT);
        String minProfitPercent = getValueByState(inputs, InputState.TRADE_BY_FILTERS_MANAGER_MIN_PROFIT_PERCENT);
        String priority = getValueByState(inputs, InputState.TRADE_BY_FILTERS_MANAGER_PRIORITY);

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

        tradeByFiltersManager.setAppliedFilters(itemFilters);
        tradeByFiltersManager.setMinDifferenceFromMedianPrice(parseIntValue(minBuySellProfit, -1 * maxMarketplacePrice,
                maxMarketplacePrice, 50));
        tradeByFiltersManager.setMinDifferenceFromMedianPricePercent(parseIntValue(minProfitPercent, Integer.MIN_VALUE, Integer.MAX_VALUE, 20));
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

    private String getValueByState(Collection<TelegramUserInput> inputs, InputState inputState) {
        return inputs.stream().filter(input -> input.getInputState().equals(inputState)).findFirst().orElse(new TelegramUserInput("",
                InputState.BASE, "")).getValue();
    }
}
