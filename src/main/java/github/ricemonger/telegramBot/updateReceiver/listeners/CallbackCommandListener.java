package github.ricemonger.telegramBot.updateReceiver.listeners;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.executors.ExecutorsService;
import github.ricemonger.telegramBot.executors.cancel.Cancel;
import github.ricemonger.telegramBot.executors.cancel.SilentCancel;
import github.ricemonger.telegramBot.executors.itemFilters.edit.FilterEditStage16FinishConfirmedFinishCallback;
import github.ricemonger.telegramBot.executors.itemFilters.edit.FilterEditStage1AskNameCallback;
import github.ricemonger.telegramBot.executors.itemFilters.showOrRemove.FilterRemoveStage3ConfirmedFinishCallback;
import github.ricemonger.telegramBot.executors.itemFilters.showOrRemove.FiltersShowAllNamesStage1AskNameCallback;
import github.ricemonger.telegramBot.executors.items.settings.ItemsShowSettingsCallback;
import github.ricemonger.telegramBot.executors.items.settings.appliedFilters.ItemsShowSettingsChangeAppliedFiltersStage1AskFilterNameCallback;
import github.ricemonger.telegramBot.executors.items.settings.itemsInMessage.ItemsShowSettingsChangeItemsInMessageCallback;
import github.ricemonger.telegramBot.executors.items.settings.itemsInMessage.ItemsShowSettingsChangeItemsInMessageNoCallback;
import github.ricemonger.telegramBot.executors.items.settings.itemsInMessage.ItemsShowSettingsChangeItemsInMessageYesCallback;
import github.ricemonger.telegramBot.executors.items.settings.messageLimit.ItemsShowSettingsChangeMessageLimitCallback;
import github.ricemonger.telegramBot.executors.items.settings.shownFields.ItemsShowSettingsChangeShownFieldsStage1AskNameFlagCallback;
import github.ricemonger.telegramBot.executors.items.show.ItemsShowStage1AskOffsetCallback;
import github.ricemonger.telegramBot.executors.start.startYes.StartYesCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.edit.TradeManagersEditAskManagerTypeCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.edit.itemFilter.TradeByFiltersManagerEditStage1AskNameCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.edit.itemFilter.TradeByFiltersManagerEditStage8ConfirmedFinishCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.edit.oneItem.TradeByItemIdManagerEditAskTradeTypeCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.edit.oneItem.buy.TradeByItemIdManagerBuyEditStage1AskItemIdCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.edit.oneItem.buy.TradeByItemIdManagerBuyEditStage5ConfirmedFinishCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.edit.oneItem.buyAndSell.TradeByItemIdManagerBuyAndSellEditStage1AskItemIdCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.edit.oneItem.buyAndSell.TradeByItemIdManagerBuyAndSellEditStage6ConfirmedFinishCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.edit.oneItem.sell.TradeByItemIdManagerSellEditStage1AskItemIdCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.edit.oneItem.sell.TradeByItemIdManagerSellEditStage5ConfirmedFinishCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.settings.TradeManagersSettingsCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.settings.managingEnabledFlag.TradeManagersSettingsChangeManagingEnabledFlagAskFlagCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.settings.managingEnabledFlag.TradeManagersSettingsChangeManagingEnabledFlagNoCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.settings.managingEnabledFlag.TradeManagersSettingsChangeManagingEnabledFlagYesCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.settings.newManagersAreActiveFlag.TradeManagersSettingsChangeNewManagersAreActiveFlagAskFlagCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.settings.newManagersAreActiveFlag.TradeManagersSettingsChangeNewManagersAreActiveFlagNoCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.settings.newManagersAreActiveFlag.TradeManagersSettingsChangeNewManagersAreActiveFlagYesCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.showRemoveChangeEnabled.TradeByFiltersManagersShowAllCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.showRemoveChangeEnabled.TradeByItemIdManagersShowAllCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.showRemoveChangeEnabled.TradeManagersChooseTypeCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.showRemoveChangeEnabled.remove_or_change_enabled.itemFilters.TradeByFiltersManagerChangeEnabledStage3ConfirmedFinishCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.showRemoveChangeEnabled.remove_or_change_enabled.itemFilters.TradeByFiltersManagerRemoveOrChangeEnabledStage1AskNameCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.showRemoveChangeEnabled.remove_or_change_enabled.itemFilters.TradeByFiltersManagerRemoveStage3ConfirmedFinishCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.showRemoveChangeEnabled.remove_or_change_enabled.itemId.TradeByItemIdManagerChangeEnabledStage3ConfirmedFinishCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.showRemoveChangeEnabled.remove_or_change_enabled.itemId.TradeByItemIdManagerRemoveOrChangeEnabledStage1AskItemIdCallback;
import github.ricemonger.telegramBot.executors.tradeManagers.showRemoveChangeEnabled.remove_or_change_enabled.itemId.TradeByItemIdManagerRemoveStage3ConfirmedFinishCallback;
import github.ricemonger.telegramBot.executors.ubi_account_entry.link.UbiAccountEntryAuthorizeStage1AskEmailCallback;
import github.ricemonger.telegramBot.executors.ubi_account_entry.reauth_two_fa_code.UbiAccountEntryReauthorizeEnter2FACodeStage1Ask2FACodeCallback;
import github.ricemonger.telegramBot.executors.ubi_account_entry.show.UbiAccountEntryShowCallback;
import github.ricemonger.telegramBot.executors.ubi_account_entry.unlink.UbiAccountEntryUnlinkConfirmedFinishCallback;
import github.ricemonger.telegramBot.executors.ubi_account_entry.unlink.UbiAccountEntryUnlinkRequestCallback;
import github.ricemonger.utils.exceptions.server.UnexpectedCallbackCommandException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CallbackCommandListener {

    private final ExecutorsService executorsService;

    public void handleUpdate(UpdateInfo updateInfo) {

        String data = updateInfo.getCallbackQueryData();

        switch (data) {
            case Callbacks.CANCEL -> executorsService.execute(Cancel.class, updateInfo);

            case Callbacks.CANCEL_SILENT -> executorsService.execute(SilentCancel.class, updateInfo);

//--------------------------------------------------------------------------------------------------

            case Callbacks.ITEM_FILTER_EDIT -> executorsService.execute(FilterEditStage1AskNameCallback.class, updateInfo);

            case Callbacks.ITEM_FILTER_EDIT_FINISH_CONFIRMED ->
                    executorsService.execute(FilterEditStage16FinishConfirmedFinishCallback.class, updateInfo);

            case Callbacks.ITEM_FILTERS_SHOW_ALL -> executorsService.execute(FiltersShowAllNamesStage1AskNameCallback.class, updateInfo);

            case Callbacks.ITEM_FILTER_REMOVE_FINISH_CONFIRMED ->
                    executorsService.execute(FilterRemoveStage3ConfirmedFinishCallback.class, updateInfo);

//--------------------------------------------------------------------------------------------------

            case Callbacks.ITEMS_SHOW_SETTINGS -> executorsService.execute(ItemsShowSettingsCallback.class, updateInfo);

            case Callbacks.ITEMS_SHOW_SETTINGS_CHANGE_APPLIED_FILTERS ->
                    executorsService.execute(ItemsShowSettingsChangeAppliedFiltersStage1AskFilterNameCallback.class, updateInfo);

            case Callbacks.ITEMS_SHOW_SETTINGS_CHANGE_FEW_ITEMS_IN_MESSAGE ->
                    executorsService.execute(ItemsShowSettingsChangeItemsInMessageCallback.class, updateInfo);

            case Callbacks.ITEMS_SHOW_SETTINGS_CHANGE_FEW_ITEMS_IN_MESSAGE_CALLBACK_NO ->
                    executorsService.execute(ItemsShowSettingsChangeItemsInMessageNoCallback.class,
                            updateInfo);

            case Callbacks.ITEMS_SHOW_SETTINGS_CHANGE_FEW_ITEMS_IN_MESSAGE_CALLBACK_YES ->
                    executorsService.execute(ItemsShowSettingsChangeItemsInMessageYesCallback.class, updateInfo);

            case Callbacks.ITEMS_SHOW_SETTINGS_CHANGE_MESSAGE_LIMIT ->
                    executorsService.execute(ItemsShowSettingsChangeMessageLimitCallback.class, updateInfo);

            case Callbacks.ITEMS_SHOW_SETTINGS_CHANGE_SHOWN_FIELDS ->
                    executorsService.execute(ItemsShowSettingsChangeShownFieldsStage1AskNameFlagCallback.class,
                            updateInfo);


            case Callbacks.ITEMS_SHOW -> executorsService.execute(ItemsShowStage1AskOffsetCallback.class, updateInfo);

//--------------------------------------------------------------------------------------------------

            case Callbacks.START_YES -> executorsService.execute(StartYesCallback.class, updateInfo);

//--------------------------------------------------------------------------------------------------

            case Callbacks.TRADE_MANAGERS_EDIT -> executorsService.execute(TradeManagersEditAskManagerTypeCallback.class, updateInfo);


            case Callbacks.TRADE_BY_FILTERS_MANAGER_EDIT ->
                    executorsService.execute(TradeByFiltersManagerEditStage1AskNameCallback.class, updateInfo);

            case Callbacks.TRADE_BY_FILTERS_MANAGER_EDIT_FINISH_CONFIRMED ->
                    executorsService.execute(TradeByFiltersManagerEditStage8ConfirmedFinishCallback.class, updateInfo);


            case Callbacks.TRADE_BY_ITEM_ID_MANAGER_EDIT -> executorsService.execute(TradeByItemIdManagerEditAskTradeTypeCallback.class, updateInfo);

            case Callbacks.TRADE_BY_ITEM_ID_MANAGER_TYPE_BUY_EDIT ->
                    executorsService.execute(TradeByItemIdManagerBuyEditStage1AskItemIdCallback.class, updateInfo);

            case Callbacks.TRADE_BY_ITEM_ID_MANAGER_TYPE_BUY_EDIT_FINISH_CONFIRMED ->
                    executorsService.execute(TradeByItemIdManagerBuyEditStage5ConfirmedFinishCallback.class, updateInfo);

            case Callbacks.TRADE_BY_ITEM_ID_MANAGER_TYPE_SELL_EDIT ->
                    executorsService.execute(TradeByItemIdManagerSellEditStage1AskItemIdCallback.class, updateInfo);

            case Callbacks.TRADE_BY_ITEM_ID_MANAGER_TYPE_SELL_EDIT_FINISH_CONFIRMED ->
                    executorsService.execute(TradeByItemIdManagerSellEditStage5ConfirmedFinishCallback.class, updateInfo);

            case Callbacks.TRADE_BY_ITEM_ID_MANAGER_TYPE_BUY_AND_SELL_EDIT ->
                    executorsService.execute(TradeByItemIdManagerBuyAndSellEditStage1AskItemIdCallback.class, updateInfo);

            case Callbacks.TRADE_BY_ITEM_ID_MANAGER_TYPE_BUY_AND_SELL_EDIT_FINISH_CONFIRMED ->
                    executorsService.execute(TradeByItemIdManagerBuyAndSellEditStage6ConfirmedFinishCallback.class,
                            updateInfo);


            case Callbacks.TRADE_MANAGERS_SETTINGS -> executorsService.execute(TradeManagersSettingsCallback.class, updateInfo);


            case Callbacks.TRADE_MANAGERS_SETTINGS_CHANGE_NEW_MANAGERS_ARE_ACTIVE_FLAG ->
                    executorsService.execute(TradeManagersSettingsChangeNewManagersAreActiveFlagAskFlagCallback.class, updateInfo);

            case Callbacks.TRADE_MANAGERS_SETTINGS_CHANGE_NEW_MANAGERS_ARE_ACTIVE_FLAG_NO ->
                    executorsService.execute(TradeManagersSettingsChangeNewManagersAreActiveFlagNoCallback.class, updateInfo);

            case Callbacks.TRADE_MANAGERS_SETTINGS_CHANGE_NEW_MANAGERS_ARE_ACTIVE_FLAG_YES ->
                    executorsService.execute(TradeManagersSettingsChangeNewManagersAreActiveFlagYesCallback.class, updateInfo);


            case Callbacks.TRADE_MANAGERS_SETTINGS_CHANGE_MANAGING_ENABLED_FLAG ->
                    executorsService.execute(TradeManagersSettingsChangeManagingEnabledFlagAskFlagCallback.class, updateInfo);

            case Callbacks.TRADE_MANAGERS_SETTINGS_CHANGE_MANAGING_ENABLED_FLAG_NO ->
                    executorsService.execute(TradeManagersSettingsChangeManagingEnabledFlagNoCallback.class, updateInfo);

            case Callbacks.TRADE_MANAGERS_SETTINGS_CHANGE_MANAGING_ENABLED_FLAG_YES ->
                    executorsService.execute(TradeManagersSettingsChangeManagingEnabledFlagYesCallback.class, updateInfo);


            case Callbacks.TRADE_MANAGERS_SHOW_CHOOSE_TYPE -> executorsService.execute(TradeManagersChooseTypeCallback.class, updateInfo);

            case Callbacks.TRADE_BY_FILTERS_MANAGERS_SHOW_ALL -> executorsService.execute(TradeByFiltersManagersShowAllCallback.class, updateInfo);

            case Callbacks.TRADE_BY_ITEM_ID_MANAGERS_SHOW_ALL -> executorsService.execute(TradeByItemIdManagersShowAllCallback.class, updateInfo);


            case Callbacks.TRADE_BY_FILTERS_MANAGER_REMOVE_OR_ENABLED_CHANGE ->
                    executorsService.execute(TradeByFiltersManagerRemoveOrChangeEnabledStage1AskNameCallback.class, updateInfo);

            case Callbacks.TRADE_BY_FILTERS_MANAGER_CHANGE_ENABLED_FINISH_CONFIRMED ->
                    executorsService.execute(TradeByFiltersManagerChangeEnabledStage3ConfirmedFinishCallback.class, updateInfo);

            case Callbacks.TRADE_BY_FILTERS_MANAGER_REMOVE_FINISH_CONFIRMED ->
                    executorsService.execute(TradeByFiltersManagerRemoveStage3ConfirmedFinishCallback.class, updateInfo);


            case Callbacks.TRADE_BY_ITEM_ID_MANAGER_REMOVE_OR_ENABLED_CHANGE ->
                    executorsService.execute(TradeByItemIdManagerRemoveOrChangeEnabledStage1AskItemIdCallback.class, updateInfo);

            case Callbacks.TRADE_BY_ITEM_ID_MANAGER_CHANGE_ENABLED_FINISH_CONFIRMED ->
                    executorsService.execute(TradeByItemIdManagerChangeEnabledStage3ConfirmedFinishCallback.class, updateInfo);

            case Callbacks.TRADE_BY_ITEM_ID_MANAGER_REMOVE_FINISH_CONFIRMED ->
                    executorsService.execute(TradeByItemIdManagerRemoveStage3ConfirmedFinishCallback.class, updateInfo);

//--------------------------------------------------------------------------------------------------

            case Callbacks.UBI_ACCOUNT_ENTRY_LINK -> executorsService.execute(UbiAccountEntryAuthorizeStage1AskEmailCallback.class, updateInfo);

            case Callbacks.UBI_ACCOUNT_ENTRY_SHOW -> executorsService.execute(UbiAccountEntryShowCallback.class, updateInfo);

            case Callbacks.UBI_ACCOUNT_ENTRY_UNLINK_REQUEST -> executorsService.execute(UbiAccountEntryUnlinkRequestCallback.class, updateInfo);

            case Callbacks.UBI_ACCOUNT_ENTRY_UNLINK_FINISH_CONFIRMED ->
                    executorsService.execute(UbiAccountEntryUnlinkConfirmedFinishCallback.class, updateInfo);

            case Callbacks.UBI_ACCOUNT_ENTRY_REAUTHORIZE_2FA_CODE ->
                    executorsService.execute(UbiAccountEntryReauthorizeEnter2FACodeStage1Ask2FACodeCallback.class, updateInfo);

            default -> throw new UnexpectedCallbackCommandException("Unexpected callback data: " + data);
        }
    }
}
