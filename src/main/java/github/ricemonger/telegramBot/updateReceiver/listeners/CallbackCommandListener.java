package github.ricemonger.telegramBot.updateReceiver.listeners;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.executors.ExecutorsService;
import github.ricemonger.telegramBot.executors.cancel.Cancel;
import github.ricemonger.telegramBot.executors.cancel.SilentCancel;
import github.ricemonger.telegramBot.executors.credentials.link.CredentialsAddCallback;
import github.ricemonger.telegramBot.executors.credentials.unlink.CredentialsUnlinkRequestCallback;
import github.ricemonger.telegramBot.executors.credentials.unlink.CredentialsUnlinkConfirmedCallback;
import github.ricemonger.telegramBot.executors.credentials.show.CredentialsShowCallback;
import github.ricemonger.telegramBot.executors.itemFilters.ItemFiltersDirect;
import github.ricemonger.telegramBot.executors.itemFilters.edit.FilterEditStage18FinishCallback;
import github.ricemonger.telegramBot.executors.itemFilters.edit.FilterEditStage1AskNameCallback;
import github.ricemonger.telegramBot.executors.itemFilters.showOrRemove.FilterRemoveConfirmedCallback;
import github.ricemonger.telegramBot.executors.itemFilters.showOrRemove.FiltersShowAllCallback;
import github.ricemonger.telegramBot.executors.items.ItemsDirect;
import github.ricemonger.telegramBot.executors.items.settings.ItemsShowSettingsCallback;
import github.ricemonger.telegramBot.executors.items.settings.appliedFilters.ItemsShowSettingsChangeAppliedFiltersStage1AskFilterNameCallback;
import github.ricemonger.telegramBot.executors.items.settings.itemsInMessage.ItemsShowSettingsChangeItemsInMessageCallback;
import github.ricemonger.telegramBot.executors.items.settings.itemsInMessage.ItemsShowSettingsChangeItemsInMessageNoCallback;
import github.ricemonger.telegramBot.executors.items.settings.itemsInMessage.ItemsShowSettingsChangeItemsInMessageYesCallback;
import github.ricemonger.telegramBot.executors.items.settings.messageLimit.ItemsShowSettingsChangeMessageLimitCallback;
import github.ricemonger.telegramBot.executors.items.settings.shownFields.ItemsShowSettingsChangeShownFieldsStage1AskNameFlagCallback;
import github.ricemonger.telegramBot.executors.items.show.ItemsShowStage1AskOffsetCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.TradeManagersDirect;
import github.ricemonger.telegramBot.executors.tradeManagers.createUpdate.TradesEditCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.createUpdate.oneItem.TradeByItemIdManagerAskTypeEditCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.createUpdate.oneItem.buy.TradeByItemIdManagerBuyEditStage1AskItemIdCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.createUpdate.oneItem.buy.TradeByItemIdManagerBuyEditStage6FinishCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.createUpdate.oneItem.buyAndSell.TradeByItemIdManagerBuyAndSellEditStage1AskItemIdCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.createUpdate.oneItem.buyAndSell.TradeByItemIdManagerBuyAndSellEditStage8FinishCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.createUpdate.oneItem.sell.TradeByItemIdManagerSellEditStage1AskItemIdCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.createUpdate.oneItem.sell.TradeByItemIdManagerSellEditStage6FinishCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.settings.TradesSettingsCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.showRemove.TradeByFiltersManagersShowAllCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.showRemove.TradeByItemIdManagersShowAllCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.showRemove.TradeManagersChooseTypeCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.showRemove.remove.itemId.TradeManagersByItemIdRemoveStage1AskItemIdCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.showRemove.remove.itemId.TradeManagersByItemIdRemoveStage3FinishCallback;
import github.ricemonger.telegramBot.executors.start.startYes.StartYesCallback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CallbackCommandListener {

    private final ExecutorsService executorsService;

    public void handleUpdate(UpdateInfo updateInfo) {

        String data = updateInfo.getCallbackQueryData();

        switch (data) {
            case Callbacks.START_YES -> executorsService.execute(StartYesCallback.class, updateInfo);

            case Callbacks.CREDENTIALS_ADD -> executorsService.execute(CredentialsAddCallback.class, updateInfo);

            case Callbacks.CREDENTIALS_REMOVE -> executorsService.execute(CredentialsUnlinkRequestCallback.class, updateInfo);

            case Callbacks.CREDENTIALS_REMOVE_ALL -> executorsService.execute(CredentialsUnlinkRequestCallback.class, updateInfo);

            case Callbacks.CREDENTIALS_REMOVE_ALL_CONFIRMED -> executorsService.execute(CredentialsUnlinkConfirmedCallback.class, updateInfo);

            //case Callbacks.CREDENTIALS_REMOVE_ONE -> executorsService.execute(CredentialsRemoveOneCallback.class, updateInfo);

            case Callbacks.CREDENTIALS_SHOW -> executorsService.execute(CredentialsShowCallback.class, updateInfo);

            case Callbacks.FILTERS -> executorsService.execute(ItemFiltersDirect.class, updateInfo);

            case Callbacks.FILTER_EDIT -> executorsService.execute(FilterEditStage1AskNameCallback.class, updateInfo);

            case Callbacks.FILTER_EDIT_FINISH -> executorsService.execute(FilterEditStage18FinishCallback.class, updateInfo);

            case Callbacks.FILTERS_SHOW -> executorsService.execute(FiltersShowAllCallback.class, updateInfo);

            case Callbacks.FILTER_REMOVE_FINISH -> executorsService.execute(FilterRemoveConfirmedCallback.class, updateInfo);

            case Callbacks.ITEMS -> executorsService.execute(ItemsDirect.class, updateInfo);

            case Callbacks.ITEMS_SHOW -> executorsService.execute(ItemsShowStage1AskOffsetCallback.class, updateInfo);

            case Callbacks.ITEMS_SHOW_SETTINGS -> executorsService.execute(ItemsShowSettingsCallback.class, updateInfo);

            case Callbacks.ITEMS_SHOW_SETTINGS_CHANGE_FEW_ITEMS_IN_MESSAGE ->
                    executorsService.execute(ItemsShowSettingsChangeItemsInMessageCallback.class, updateInfo);

            case Callbacks.ITEMS_SHOW_SETTINGS_CHANGE_FEW_ITEMS_IN_MESSAGE_CALLBACK_YES ->
                    executorsService.execute(ItemsShowSettingsChangeItemsInMessageYesCallback.class, updateInfo);

            case Callbacks.ITEMS_SHOW_SETTINGS_CHANGE_FEW_ITEMS_IN_MESSAGE_CALLBACK_NO ->
                    executorsService.execute(ItemsShowSettingsChangeItemsInMessageNoCallback.class,
                            updateInfo);

            case Callbacks.ITEMS_SHOW_SETTINGS_CHANGE_MESSAGE_LIMIT ->
                    executorsService.execute(ItemsShowSettingsChangeMessageLimitCallback.class, updateInfo);

            case Callbacks.ITEMS_SHOW_SETTINGS_CHANGE_SHOWN_FIELDS ->
                    executorsService.execute(ItemsShowSettingsChangeShownFieldsStage1AskNameFlagCallback.class,
                            updateInfo);

            case Callbacks.ITEMS_SHOW_SETTINGS_CHANGE_APPLIED_FILTERS ->
                    executorsService.execute(ItemsShowSettingsChangeAppliedFiltersStage1AskFilterNameCallback.class, updateInfo);

            case Callbacks.TRADES -> executorsService.execute(TradeManagersDirect.class, updateInfo);

            case Callbacks.TRADE_EDIT -> executorsService.execute(TradesEditCallback.class, updateInfo);

            case Callbacks.TRADES_EDIT_ONE_ITEM -> executorsService.execute(TradeByItemIdManagerAskTypeEditCallback.class, updateInfo);

            case Callbacks.TRADES_EDIT_ONE_ITEM_TYPE_BUY -> executorsService.execute(TradeByItemIdManagerBuyEditStage1AskItemIdCallback.class, updateInfo);

            case Callbacks.TRADES_EDIT_ONE_ITEM_BUY_FINISH -> executorsService.execute(TradeByItemIdManagerBuyEditStage6FinishCallback.class, updateInfo);

            case Callbacks.TRADES_EDIT_ONE_ITEM_TYPE_SELL -> executorsService.execute(TradeByItemIdManagerSellEditStage1AskItemIdCallback.class, updateInfo);

            case Callbacks.TRADES_EDIT_ONE_ITEM_SELL_FINISH -> executorsService.execute(TradeByItemIdManagerSellEditStage6FinishCallback.class, updateInfo);

            case Callbacks.TRADES_EDIT_ONE_ITEM_TYPE_BUY_AND_SELL ->
                    executorsService.execute(TradeByItemIdManagerBuyAndSellEditStage1AskItemIdCallback.class, updateInfo);

            case Callbacks.TRADES_EDIT_ONE_ITEM_BUY_AND_SELL_FINISH -> executorsService.execute(TradeByItemIdManagerBuyAndSellEditStage8FinishCallback.class,
                    updateInfo);

            case Callbacks.TRADES_SETTINGS -> executorsService.execute(TradesSettingsCallback.class, updateInfo);

            case Callbacks.TRADES_SHOW_OR_REMOVE -> executorsService.execute(TradeManagersChooseTypeCallback.class, updateInfo);

            case Callbacks.TRADES_SHOW_BY_ITEM_FILTERS -> executorsService.execute(TradeByFiltersManagersShowAllCallback.class, updateInfo);

            case Callbacks.TRADES_SHOW_BY_ITEM_ID -> executorsService.execute(TradeByItemIdManagersShowAllCallback.class, updateInfo);

            case Callbacks.TRADES_REMOVE_BY_ITEM_ID -> executorsService.execute(TradeManagersByItemIdRemoveStage1AskItemIdCallback.class, updateInfo);

            case Callbacks.TRADES_REMOVE_BY_ITEM_ID_FINISH ->
                    executorsService.execute(TradeManagersByItemIdRemoveStage3FinishCallback.class, updateInfo);

            case Callbacks.CANCEL -> executorsService.execute(Cancel.class, updateInfo);

            case Callbacks.SILENT_CANCEL -> executorsService.execute(SilentCancel.class, updateInfo);

            default -> throw new IllegalStateException("Unexpected callback value: " + data);
        }
    }
}
