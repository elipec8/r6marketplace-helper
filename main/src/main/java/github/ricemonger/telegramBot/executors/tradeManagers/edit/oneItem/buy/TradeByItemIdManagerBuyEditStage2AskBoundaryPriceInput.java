package github.ricemonger.telegramBot.executors.tradeManagers.edit.oneItem.buy;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.InputState;
import github.ricemonger.utils.exceptions.client.ItemDoesntExistException;

public class TradeByItemIdManagerBuyEditStage2AskBoundaryPriceInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_BUY_PRICE);

        try {
            sendText("Chosen item is:\n" + botInnerService.getItemByUserInputItemId(updateInfo.getChatId()));
        } catch (ItemDoesntExistException e) {
            sendText("Item not found. Please enter correct item id.");
            cancel();
            return;
        }

        sendText("Please enter boundary price to buy item(If value is invalid, next rounded buy price will be used):");
    }
}
