package github.ricemonger.telegramBot.executors.tradeManagers.showRemoveChangeEnabled.remove_or_change_enabled.itemId;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.client.CallbackButton;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.dtos.TradeByItemIdManager;

public class TradeByItemIdManagerRemoveStage2AskConfirmationFinishInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput();

        TradeByItemIdManager tradeManager = botInnerService.getUserTradeByItemIdManagerByUserInputItemId(updateInfo.getChatId());

        String activateText = tradeManager.isEnabled() ? "Deactivate" : "Activate";

        CallbackButton removeButton = new CallbackButton("Remove", Callbacks.TRADE_BY_ITEM_ID_MANAGER_REMOVE_FINISH_CONFIRMED);
        CallbackButton changeButton = new CallbackButton(activateText, Callbacks.TRADE_BY_ITEM_ID_MANAGER_CHANGE_ENABLED_FINISH_CONFIRMED);
        CallbackButton cancelButton = new CallbackButton("Cancel", Callbacks.CANCEL_SILENT);

        askFromInlineKeyboard(
                "Do you wand to Remove chosen trade manager or " + activateText + " it?:\n\n" + tradeManager,
                2,
                removeButton,
                changeButton,
                cancelButton);
    }
}
