package github.ricemonger.telegramBot.executors.marketplace.tradeManagers.createUpdate.oneItem.buyAndSell;

import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.exceptions.ItemNotFoundException;

public class TradesOneItemBuyAndSellEditStage2AskBoundarySellPriceInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.TRADES_EDIT_ONE_ITEM_BOUNDARY_SELL_PRICE);

        try {
            sendText("Chosen item is:\n" + botInnerService.getItemByUserInputTradeByItemIdManagerEdit(updateInfo.getChatId()));
        } catch (ItemNotFoundException e) {
            sendText("Item not found. Please enter correct item id.");
            cancel();
            return;
        }

        sendText("Please enter boundary price to sell item(If value is invalid, (current min sell price - 1) will be used):");
    }
}
