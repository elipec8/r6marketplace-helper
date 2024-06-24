package github.ricemonger.telegramBot.client;

import github.ricemonger.marketplace.services.ProfitAndPriorityCalculator;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.utils.dtos.Item;
import github.ricemonger.utils.dtos.PlannedOneItemTrade;
import github.ricemonger.utils.enums.PlannedTradeType;
import github.ricemonger.utils.dtos.TelegramUserInput;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class PlannedTradeFromInputsMapper {

    public ProfitAndPriorityCalculator profitAndPriorityCalculator;

    public PlannedOneItemTrade mapToPlannedOneItemTrade(String chatId,Collection<TelegramUserInput> inputs, PlannedTradeType tradeType, Item item) {
        String itemId = getValueByState(inputs, InputState.TRADES_EDIT_ONE_ITEM_ITEM_ID);
        String startingSellPrice = getValueByState(inputs, InputState.TRADES_EDIT_ONE_ITEM_STARTING_SELL_PRICE);
        String boundarySellPrice = getValueByState(inputs, InputState.TRADES_EDIT_ONE_ITEM_BOUNDARY_SELL_PRICE);
        String startingBuyPrice = getValueByState(inputs, InputState.TRADES_EDIT_ONE_ITEM_STARTING_BUY_PRICE);
        String boundaryBuyPrice = getValueByState(inputs, InputState.TRADES_EDIT_ONE_ITEM_BOUNDARY_BUY_PRICE);

        int startSellPrice;
        int boundSellPrice;
        int startBuyPrice;
        int boundBuyPrice;

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

        return new PlannedOneItemTrade(chatId,tradeType, itemId, startSellPrice, boundSellPrice, startBuyPrice, boundBuyPrice);
    }

    private String getValueByState(Collection<TelegramUserInput> inputs, InputState inputState) {
        return inputs.stream().filter(input -> input.getInputState().equals(inputState)).findFirst().orElse(new TelegramUserInput("",
                InputState.BASE, "")).getValue();
    }
}
