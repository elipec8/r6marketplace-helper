package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.edit.oneItem.buy;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.InputState;
import github.ricemonger.utils.exceptions.client.ItemDoesntExistException;

public class TradeByItemIdManagerBuyEditStage2AskBoundaryPriceInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        try {
            sendText("Chosen item is:\n" + botInnerService.getItemByUserInputItemId(updateInfo.getChatId()));
            processMiddleInput(InputState.TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_BUY_PRICE);
            sendText("Please enter boundary price to buy item(If value is invalid, next rounded buy price will be used):");
        } catch (ItemDoesntExistException e) {
            sendText("Item not found. Please enter correct item id.");
        }
    }
}
