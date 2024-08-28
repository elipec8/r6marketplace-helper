package github.ricemonger.telegramBot.executors.tradeManagers.edit.oneItem.buyAndSell;

import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.exceptions.ItemNotFoundException;

public class TradeByItemIdManagerBuyAndSellEditStage5AskStartingBuyPriceInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.TRADE_BY_ITEM_ID_MANAGER_STARTING_BUY_PRICE);

        try {
            sendText("Chosen item is:\n" + botInnerService.getItemByUserInputItemId(updateInfo.getChatId()));
        } catch (ItemNotFoundException e) {
            sendText("Item not found. Please enter correct item id.");
            cancel();
            return;
        }

        askFromInlineKeyboardOrSkip("Please enter starting price to buy item or skip to make it equal to boundary buy price:", 1);
    }
}
