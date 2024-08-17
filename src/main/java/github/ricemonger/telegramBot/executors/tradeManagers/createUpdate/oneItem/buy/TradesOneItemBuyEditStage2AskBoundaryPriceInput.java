package github.ricemonger.telegramBot.executors.tradeManagers.createUpdate.oneItem.buy;

import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.exceptions.ItemNotFoundException;

public class TradesOneItemBuyEditStage2AskBoundaryPriceInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.TRADES_EDIT_ONE_ITEM_BOUNDARY_BUY_PRICE);

        try {
            sendText("Chosen item is:\n" + botInnerService.getItemByUserInputTradeByItemIdManagerEdit(updateInfo.getChatId()));
        } catch (ItemNotFoundException e) {
            sendText("Item not found. Please enter correct item id.");
            cancel();
            return;
        }

        sendText("Please enter boundary price to buy item(If value is invalid, next rounded buy price will be used):");
    }
}
