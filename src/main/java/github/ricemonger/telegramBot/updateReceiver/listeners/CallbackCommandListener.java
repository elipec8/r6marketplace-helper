package github.ricemonger.telegramBot.updateReceiver.listeners;

import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.client.Callbacks;
import github.ricemonger.telegramBot.executors.ExecutorsService;
import github.ricemonger.telegramBot.executors.cancel.Cancel;
import github.ricemonger.telegramBot.executors.cancel.SilentCancel;
import github.ricemonger.telegramBot.executors.credentials.add.CredentialsAddCallback;
import github.ricemonger.telegramBot.executors.credentials.remove.CredentialsRemoveAllCallback;
import github.ricemonger.telegramBot.executors.credentials.remove.CredentialsRemoveAllConfirmedCallback;
import github.ricemonger.telegramBot.executors.credentials.remove.CredentialsRemoveCallback;
import github.ricemonger.telegramBot.executors.credentials.remove.CredentialsRemoveOneCallback;
import github.ricemonger.telegramBot.executors.credentials.show.CredentialsShowCallback;
import github.ricemonger.telegramBot.executors.marketplace.filters.FiltersCallback;
import github.ricemonger.telegramBot.executors.marketplace.filters.edit.FilterEditStage18FinishCallback;
import github.ricemonger.telegramBot.executors.marketplace.filters.edit.FilterEditStage1AskNameCallback;
import github.ricemonger.telegramBot.executors.marketplace.filters.showOrRemove.FilterRemoveCallback;
import github.ricemonger.telegramBot.executors.marketplace.filters.showOrRemove.FiltersShowCallback;
import github.ricemonger.telegramBot.executors.marketplace.items.ItemsCallback;
import github.ricemonger.telegramBot.executors.marketplace.items.settings.ItemsShowSettingsCallback;
import github.ricemonger.telegramBot.executors.marketplace.items.settings.appliedFilters.ItemsShowSettingsChangeAppliedFiltersStage1AskFilterNameCallback;
import github.ricemonger.telegramBot.executors.marketplace.items.settings.itemsInMessage.ItemsShowSettingsChangeItemsInMessageCallback;
import github.ricemonger.telegramBot.executors.marketplace.items.settings.itemsInMessage.ItemsShowSettingsChangeItemsInMessageNoCallback;
import github.ricemonger.telegramBot.executors.marketplace.items.settings.itemsInMessage.ItemsShowSettingsChangeItemsInMessageYesCallback;
import github.ricemonger.telegramBot.executors.marketplace.items.settings.messageLimit.ItemsShowSettingsChangeMessageLimitCallback;
import github.ricemonger.telegramBot.executors.marketplace.items.settings.shownFields.ItemsShowSettingsChangeShownFieldsStage1AskNameFlagCallback;
import github.ricemonger.telegramBot.executors.marketplace.items.show.ItemsShowStage1AskOffsetCallback;
import github.ricemonger.telegramBot.executors.marketplace.trades.TradesCallback;
import github.ricemonger.telegramBot.executors.marketplace.trades.create.TradesCreateCallback;
import github.ricemonger.telegramBot.executors.marketplace.trades.settings.TradesManagementSettingsCallback;
import github.ricemonger.telegramBot.executors.marketplace.trades.settings.TradesSearchSettingsCallback;
import github.ricemonger.telegramBot.executors.marketplace.trades.showAll.TradesShowAllCallback;
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

            case Callbacks.CREDENTIALS_REMOVE -> executorsService.execute(CredentialsRemoveCallback.class, updateInfo);

            case Callbacks.CREDENTIALS_REMOVE_ALL -> executorsService.execute(CredentialsRemoveAllCallback.class, updateInfo);

            case Callbacks.CREDENTIALS_REMOVE_ALL_CONFIRMED -> executorsService.execute(CredentialsRemoveAllConfirmedCallback.class, updateInfo);

            case Callbacks.CREDENTIALS_REMOVE_ONE -> executorsService.execute(CredentialsRemoveOneCallback.class, updateInfo);

            case Callbacks.CREDENTIALS_SHOW -> executorsService.execute(CredentialsShowCallback.class, updateInfo);

            case Callbacks.FILTERS -> executorsService.execute(FiltersCallback.class, updateInfo);

            case Callbacks.FILTER_EDIT -> executorsService.execute(FilterEditStage1AskNameCallback.class, updateInfo);

            case Callbacks.FILTER_EDIT_FINISH -> executorsService.execute(FilterEditStage18FinishCallback.class, updateInfo);

            case Callbacks.FILTERS_SHOW -> executorsService.execute(FiltersShowCallback.class, updateInfo);

            case Callbacks.FILTER_REMOVE_FINISH -> executorsService.execute(FilterRemoveCallback.class, updateInfo);

            case Callbacks.ITEMS -> executorsService.execute(ItemsCallback.class, updateInfo);

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

            case Callbacks.TRADES -> executorsService.execute(TradesCallback.class, updateInfo);

            case Callbacks.TRADE_CREATE -> executorsService.execute(TradesCreateCallback.class, updateInfo);

            case Callbacks.TRADES_MANAGEMENT_SETTINGS -> executorsService.execute(TradesManagementSettingsCallback.class, updateInfo);

            case Callbacks.TRADES_SEARCH_SETTINGS -> executorsService.execute(TradesSearchSettingsCallback.class, updateInfo);

            case Callbacks.TRADES_SHOW_ALL -> executorsService.execute(TradesShowAllCallback.class, updateInfo);

            case Callbacks.CANCEL -> executorsService.execute(Cancel.class, updateInfo);

            case Callbacks.SILENT_CANCEL -> executorsService.execute(SilentCancel.class, updateInfo);

            default -> throw new IllegalStateException("Unexpected callback value: " + data);
        }
    }
}
