package github.ricemonger.telegramBot.client;

import github.ricemonger.marketplace.services.ProfitAndPriorityCalculator;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.utils.dtos.Item;
import github.ricemonger.utils.dtos.TradeManagerByItemId;
import github.ricemonger.utils.enums.TradeManagerTradeType;
import github.ricemonger.utils.dtos.TelegramUserInput;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class TradeManagerFromInputsMapper {

    public ProfitAndPriorityCalculator profitAndPriorityCalculator;

    public TradeManagerByItemId mapToTradeManagerByItemId(String chatId, Collection<TelegramUserInput> inputs, TradeManagerTradeType tradeType, Item item) {
        String itemId = getValueByState(inputs, InputState.TRADES_EDIT_ONE_ITEM_ITEM_ID);
        String startingSellPrice = getValueByState(inputs, InputState.TRADES_EDIT_ONE_ITEM_STARTING_SELL_PRICE);
        String boundarySellPrice = getValueByState(inputs, InputState.TRADES_EDIT_ONE_ITEM_BOUNDARY_SELL_PRICE);
        String startingBuyPrice = getValueByState(inputs, InputState.TRADES_EDIT_ONE_ITEM_STARTING_BUY_PRICE);
        String boundaryBuyPrice = getValueByState(inputs, InputState.TRADES_EDIT_ONE_ITEM_BOUNDARY_BUY_PRICE);
        String priority = getValueByState(inputs, InputState.TRADES_EDIT_ONE_ITEM_PRIORITY);

        int startSellPrice;
        int boundSellPrice;
        int startBuyPrice;
        int boundBuyPrice;
        int prior;

        try {
            boundSellPrice = Integer.parseInt(boundarySellPrice);
        } catch (NumberFormatException | NullPointerException e) {
            int minSellPrice = item.getMinSellPrice();
            boundSellPrice = minSellPrice == 120 ? 120 : minSellPrice - 1;
        }
        try {
            startSellPrice = Integer.parseInt(startingSellPrice);
        } catch (NumberFormatException | NullPointerException e) {
            startSellPrice = boundSellPrice;
        }

        try {
            boundBuyPrice = Integer.parseInt(boundaryBuyPrice);
        } catch (NumberFormatException | NullPointerException e) {
            boundBuyPrice = profitAndPriorityCalculator.calculateNextBuyPrice(item);
        }
        try {
            startBuyPrice = Integer.parseInt(startingBuyPrice);
        } catch (NumberFormatException | NullPointerException e) {
            startBuyPrice = boundBuyPrice;
        }

        try {
            prior = Integer.parseInt(priority);
        } catch (NumberFormatException | NullPointerException e) {
            prior = 1;
        }

        TradeManagerByItemId tradeManagerByItemId = new TradeManagerByItemId();
        tradeManagerByItemId.setChatId(chatId);
        tradeManagerByItemId.setTradeType(tradeType);
        tradeManagerByItemId.setItemId(itemId);
        tradeManagerByItemId.setSellStartingPrice(startSellPrice);
        tradeManagerByItemId.setSellBoundaryPrice(boundSellPrice);
        tradeManagerByItemId.setBuyStartingPrice(startBuyPrice);
        tradeManagerByItemId.setBuyBoundaryPrice(boundBuyPrice);
        tradeManagerByItemId.setPriority(prior);

        return tradeManagerByItemId;
    }

    private String getValueByState(Collection<TelegramUserInput> inputs, InputState inputState) {
        return inputs.stream().filter(input -> input.getInputState().equals(inputState)).findFirst().orElse(new TelegramUserInput("",
                InputState.BASE, "")).getValue();
    }
}
