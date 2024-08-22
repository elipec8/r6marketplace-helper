package github.ricemonger.telegramBot.client;

import github.ricemonger.marketplace.services.ProfitAndPriorityCalculator;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.utils.dtos.Item;
import github.ricemonger.utils.dtos.TelegramUserInput;
import github.ricemonger.utils.dtos.TradeByItemIdManager;
import github.ricemonger.utils.enums.TradeManagerTradeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class TradeManagerFromInputsMapper {

    private final ProfitAndPriorityCalculator profitAndPriorityCalculator;

    public TradeByItemIdManager mapToTradeManagerByItemId(Collection<TelegramUserInput> inputs, TradeManagerTradeType tradeType, Item item) {
        String itemId = getValueByState(inputs, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_ITEM_ID);
        String startingSellPrice = getValueByState(inputs, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_STARTING_SELL_PRICE);
        String boundarySellPrice = getValueByState(inputs, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_BOUNDARY_SELL_PRICE);
        String startingBuyPrice = getValueByState(inputs, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_STARTING_BUY_PRICE);
        String boundaryBuyPrice = getValueByState(inputs, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_BOUNDARY_BUY_PRICE);
        String priority = getValueByState(inputs, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_PRIORITY);

        int limitMinPrice = item.getLimitMinPrice();
        int limitMaxPrice = item.getLimitMaxPrice();
        int minSellPrice = item.getMinSellPrice() <= limitMinPrice ? limitMinPrice : item.getMinSellPrice() - 1;

        int boundSellPrice = parsePrice(boundarySellPrice, limitMinPrice, limitMaxPrice, minSellPrice);
        int startSellPrice = parsePrice(startingSellPrice, boundSellPrice, limitMaxPrice, boundSellPrice);
        int boundBuyPrice = parsePrice(boundaryBuyPrice, limitMinPrice, limitMaxPrice, profitAndPriorityCalculator.calculateNextBuyPrice(item));
        int startBuyPrice = parsePrice(startingBuyPrice, limitMinPrice, boundBuyPrice, boundBuyPrice);

        int prior;
        try {
            prior = Integer.parseInt(priority);
        } catch (NumberFormatException | NullPointerException e) {
            prior = 1;
        }

        TradeByItemIdManager tradeByItemIdManager = new TradeByItemIdManager();
        tradeByItemIdManager.setTradeType(tradeType);
        tradeByItemIdManager.setItemId(itemId);
        tradeByItemIdManager.setSellStartingPrice(startSellPrice);
        tradeByItemIdManager.setSellBoundaryPrice(boundSellPrice);
        tradeByItemIdManager.setBuyStartingPrice(startBuyPrice);
        tradeByItemIdManager.setBuyBoundaryPrice(boundBuyPrice);
        tradeByItemIdManager.setPriority(prior);

        return tradeByItemIdManager;
    }

    private int parsePrice(String value, int minPrice, int maxPrice, int invalidCasePrice) {
        int price;
        try {
            price = Integer.parseInt(value);
        } catch (NumberFormatException | NullPointerException e) {
            return invalidCasePrice;
        }

        if (price < minPrice) {
            price = minPrice;
        } else if (price > maxPrice) {
            price = maxPrice;
        }

        return price;
    }

    private String getValueByState(Collection<TelegramUserInput> inputs, InputState inputState) {
        return inputs.stream().filter(input -> input.getInputState().equals(inputState)).findFirst().orElse(new TelegramUserInput("",
                InputState.BASE, "")).getValue();
    }
}
