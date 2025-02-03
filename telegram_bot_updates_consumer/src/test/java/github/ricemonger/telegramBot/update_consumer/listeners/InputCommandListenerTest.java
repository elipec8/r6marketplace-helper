package github.ricemonger.telegramBot.update_consumer.listeners;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.update_consumer.executors.ExecutorsService;
import github.ricemonger.telegramBot.update_consumer.executors.cancel.Cancel;
import github.ricemonger.telegramBot.update_consumer.executors.cancel.SilentCancel;
import github.ricemonger.telegramBot.update_consumer.executors.itemFilters.edit.*;
import github.ricemonger.telegramBot.update_consumer.executors.itemFilters.showOrRemove.FilterShowChosenStage2RemoveRequestInput;
import github.ricemonger.telegramBot.update_consumer.executors.items.settings.appliedFilters.ItemsShowSettingsChangeAppliedFiltersStage2AskActionInput;
import github.ricemonger.telegramBot.update_consumer.executors.items.settings.messageLimit.ItemsShowSettingsChangeMessageLimitFinishInput;
import github.ricemonger.telegramBot.update_consumer.executors.items.settings.shownFields.*;
import github.ricemonger.telegramBot.update_consumer.executors.items.show.ItemsShowStage2FinishInput;
import github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.edit.itemFilter.*;
import github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.edit.oneItem.buy.TradeByItemIdManagerBuyEditStage2AskBoundaryPriceInput;
import github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.edit.oneItem.buy.TradeByItemIdManagerBuyEditStage3AskPriorityInput;
import github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.edit.oneItem.buy.TradeByItemIdManagerBuyEditStage4AskConfirmationFinishInput;
import github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.edit.oneItem.buyAndSell.TradeByItemIdManagerBuyAndSellEditStage2AskBoundarySellPriceInput;
import github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.edit.oneItem.buyAndSell.TradeByItemIdManagerBuyAndSellEditStage3AskBoundaryBuyPriceInput;
import github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.edit.oneItem.buyAndSell.TradeByItemIdManagerBuyAndSellEditStage4AskPriorityInput;
import github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.edit.oneItem.buyAndSell.TradeByItemIdManagerBuyAndSellEditStage5AskConfirmationFinishInput;
import github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.edit.oneItem.sell.TradeByItemIdManagerSellEditStage2AskBoundaryPriceInput;
import github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.edit.oneItem.sell.TradeByItemIdManagerSellEditStage3AskPriorityInput;
import github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.edit.oneItem.sell.TradeByItemIdManagerSellEditStage4AskConfirmationFinishInput;
import github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.settings.buyTradePriorityExpression.TradeManagersSettingsChangeBuyTradePriorityExpressionAskConfirmInput;
import github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.settings.sellTradePriorityExpression.TradeManagersSettingsChangeSellTradePriorityExpressionAskConfirmInput;
import github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.show.remove_or_change_enabled.itemFilters.TradeByFiltersManagerRemoveStage2AskConfirmationFinishInput;
import github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.show.remove_or_change_enabled.itemId.TradeByItemIdManagerRemoveStage2AskConfirmationFinishInput;
import github.ricemonger.telegramBot.update_consumer.executors.ubi_account_entry.link.UbiAccountEntryAuthorizeStage2AskPasswordInput;
import github.ricemonger.telegramBot.update_consumer.executors.ubi_account_entry.link.UbiAccountEntryAuthorizeStage3Ask2FaCodeInput;
import github.ricemonger.telegramBot.update_consumer.executors.ubi_account_entry.link.UbiAccountEntryAuthorizeStage4FinishInput;
import github.ricemonger.telegramBot.update_consumer.executors.ubi_account_entry.reauth.UbiAccountEntryReauthorizeEnter2FACodeStage2ExceptionOrSuccessFinishInput;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;
import github.ricemonger.utils.exceptions.server.UnexpectedUserInputStateAndGroupConjunctionException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@SpringBootTest
class InputCommandListenerTest {
    @Autowired
    private InputCommandListener inputCommandListener;
    @MockBean
    private ExecutorsService executorsService;

    @Test
    public void handleUpdate_should_cancel_if_has_message() {
        UpdateInfo updateInfo = updateInfo(InputGroup.UBI_ACCOUNT_ENTRY_LINK, InputState.UBI_ACCOUNT_ENTRY_EMAIL);
        updateInfo.setHasMessage(true);
        updateInfo.setMessageText("/cancel");

        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(Cancel.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_cancel_if_has_callback_query() {
        UpdateInfo updateInfo = updateInfo(InputGroup.UBI_ACCOUNT_ENTRY_LINK, InputState.UBI_ACCOUNT_ENTRY_EMAIL);
        updateInfo.setHasCallBackQuery(true);
        updateInfo.setCallbackQueryData(Callbacks.CANCEL);

        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(Cancel.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_silent_cancel_if_has_message() {
        UpdateInfo updateInfo = updateInfo(InputGroup.UBI_ACCOUNT_ENTRY_LINK, InputState.UBI_ACCOUNT_ENTRY_EMAIL);
        updateInfo.setHasMessage(true);
        updateInfo.setMessageText("/silentCancel");

        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(SilentCancel.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_silent_cancel_if_has_callback_query() {
        UpdateInfo updateInfo = updateInfo(InputGroup.UBI_ACCOUNT_ENTRY_LINK, InputState.UBI_ACCOUNT_ENTRY_EMAIL);
        updateInfo.setHasCallBackQuery(true);
        updateInfo.setCallbackQueryData(Callbacks.CANCEL_SILENT);

        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(SilentCancel.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_item_filter_edit_name() {
        UpdateInfo updateInfo = updateInfo(InputGroup.ITEM_FILTER_EDIT, InputState.ITEM_FILTER_NAME);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(FilterEditStage2AskFilterTypeInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_item_filter_edit_type() {
        UpdateInfo updateInfo = updateInfo(InputGroup.ITEM_FILTER_EDIT, InputState.ITEM_FILTER_TYPE);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(FilterEditStage3AskItemNamePatternsInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_item_filter_edit_item_name_patterns() {
        UpdateInfo updateInfo = updateInfo(InputGroup.ITEM_FILTER_EDIT, InputState.ITEM_FILTER_ITEM_NAME_PATTERNS);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(FilterEditStage4AskItemTypesInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_item_filter_edit_item_types() {
        UpdateInfo updateInfo = updateInfo(InputGroup.ITEM_FILTER_EDIT, InputState.ITEM_FILTER_ITEM_TYPES);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(FilterEditStage5AskItemTagsRarityInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_item_filter_edit_item_tags_rarity() {
        UpdateInfo updateInfo = updateInfo(InputGroup.ITEM_FILTER_EDIT, InputState.ITEM_FILTER_ITEM_TAGS_RARITY);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(FilterEditStage6AskItemTagsSeasonsInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_item_filter_edit_item_tags_seasons() {
        UpdateInfo updateInfo = updateInfo(InputGroup.ITEM_FILTER_EDIT, InputState.ITEM_FILTER_ITEM_TAGS_SEASONS);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(FilterEditStage7AskItemTagsOperatorsInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_item_filter_edit_item_tags_operators() {
        UpdateInfo updateInfo = updateInfo(InputGroup.ITEM_FILTER_EDIT, InputState.ITEM_FILTER_ITEM_TAGS_OPERATORS);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(FilterEditStage8AskItemTagsWeaponsInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_item_filter_edit_item_tags_weapons() {
        UpdateInfo updateInfo = updateInfo(InputGroup.ITEM_FILTER_EDIT, InputState.ITEM_FILTER_ITEM_TAGS_WEAPONS);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(FilterEditStage9AskItemTagsEventsInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_item_filter_edit_item_tags_events() {
        UpdateInfo updateInfo = updateInfo(InputGroup.ITEM_FILTER_EDIT, InputState.ITEM_FILTER_ITEM_TAGS_EVENTS);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(FilterEditStage10AskItemTagsEsportsInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_item_filter_edit_item_tags_esports() {
        UpdateInfo updateInfo = updateInfo(InputGroup.ITEM_FILTER_EDIT, InputState.ITEM_FILTER_ITEM_TAGS_ESPORTS);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(FilterEditStage11AskItemTagsOtherInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_item_filter_edit_item_tags_other() {
        UpdateInfo updateInfo = updateInfo(InputGroup.ITEM_FILTER_EDIT, InputState.ITEM_FILTER_ITEM_TAGS_OTHER);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(FilterEditStage12AskMinPriceInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_item_filter_edit_min_price() {
        UpdateInfo updateInfo = updateInfo(InputGroup.ITEM_FILTER_EDIT, InputState.ITEM_FILTER_MIN_PRICE);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(FilterEditStage13AskMaxPriceInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_item_filter_edit_throw() {
        assertThrows(UnexpectedUserInputStateAndGroupConjunctionException.class, () -> {
            UpdateInfo updateInfo = updateInfo(InputGroup.ITEM_FILTER_EDIT, InputState.TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_SELL_PRICE);
            inputCommandListener.handleUpdate(updateInfo);
        });
    }

    @Test
    public void handleUpdate_should_item_filter_show_or_remove_name() {
        UpdateInfo updateInfo = updateInfo(InputGroup.ITEM_FILTER_SHOW_OR_REMOVE, InputState.ITEM_FILTER_NAME);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(FilterShowChosenStage2RemoveRequestInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_item_filter_show_or_remove_throw() {
        assertThrows(UnexpectedUserInputStateAndGroupConjunctionException.class, () -> {
            UpdateInfo updateInfo = updateInfo(InputGroup.ITEM_FILTER_SHOW_OR_REMOVE, InputState.TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_SELL_PRICE);
            inputCommandListener.handleUpdate(updateInfo);
        });
    }

    @Test
    public void handleUpdate_should_item_show_settings_change_applied_filters_filter_name() {
        UpdateInfo updateInfo = updateInfo(InputGroup.ITEMS_SHOW_SETTING_CHANGE_APPLIED_FILTERS, InputState.ITEM_FILTER_NAME);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(ItemsShowSettingsChangeAppliedFiltersStage2AskActionInput.class, updateInfo);
    }


    @Test
    public void handleUpdate_should_item_show_settings_change_applied_filters_throw() {
        assertThrows(UnexpectedUserInputStateAndGroupConjunctionException.class, () -> {
            UpdateInfo updateInfo = updateInfo(InputGroup.ITEMS_SHOW_SETTING_CHANGE_APPLIED_FILTERS, InputState.TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_SELL_PRICE);
            inputCommandListener.handleUpdate(updateInfo);
        });
    }

    @Test
    public void handleUpdate_should_item_show_settings_change_message_limit_message_limit() {
        UpdateInfo updateInfo = updateInfo(InputGroup.ITEMS_SHOW_SETTINGS_CHANGE_MESSAGE_LIMIT, InputState.ITEMS_SHOW_SETTING_MESSAGE_LIMIT);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(ItemsShowSettingsChangeMessageLimitFinishInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_item_show_settings_change_message_limit_throw() {
        assertThrows(UnexpectedUserInputStateAndGroupConjunctionException.class, () -> {
            UpdateInfo updateInfo = updateInfo(InputGroup.ITEMS_SHOW_SETTINGS_CHANGE_MESSAGE_LIMIT, InputState.TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_SELL_PRICE);
            inputCommandListener.handleUpdate(updateInfo);
        });
    }

    @Test
    public void handleUpdate_should_item_show_settings_change_shown_fields_item_name() {
        UpdateInfo updateInfo = updateInfo(InputGroup.ITEMS_SHOW_SETTING_CHANGE_SHOWN_FIELDS, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_ITEM_NAME);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(ItemsShowSettingsChangeShownFieldsStage2AskItemTypeFlagInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_item_show_settings_change_shown_fields_item_type() {
        UpdateInfo updateInfo = updateInfo(InputGroup.ITEMS_SHOW_SETTING_CHANGE_SHOWN_FIELDS, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_ITEM_TYPE);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(ItemsShowSettingsChangeShownFieldsStage3AskMaxBuyPriceFlagInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_item_show_settings_change_shown_fields_max_buy_price() {
        UpdateInfo updateInfo = updateInfo(InputGroup.ITEMS_SHOW_SETTING_CHANGE_SHOWN_FIELDS, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_MAX_BUY_PRICE);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(ItemsShowSettingsChangeShownFieldsStage4AskBuyOrdersCountFlagInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_item_show_settings_change_shown_fields_buy_orders_count() {
        UpdateInfo updateInfo = updateInfo(InputGroup.ITEMS_SHOW_SETTING_CHANGE_SHOWN_FIELDS, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_BUY_ORDERS_COUNT);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(ItemsShowSettingsChangeShownFieldsStage5AskMinSellPriceFlagInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_item_show_settings_change_shown_fields_min_sell_price() {
        UpdateInfo updateInfo = updateInfo(InputGroup.ITEMS_SHOW_SETTING_CHANGE_SHOWN_FIELDS, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_MIN_SELL_PRICE);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(ItemsShowSettingsChangeShownFieldsStage6AskSellOrdersCountFlagInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_item_show_settings_change_shown_fields_sell_orders_count() {
        UpdateInfo updateInfo = updateInfo(InputGroup.ITEMS_SHOW_SETTING_CHANGE_SHOWN_FIELDS, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_SELL_ORDERS_COUNT);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(ItemsShowSettingsChangeShownFieldsStage7AskPictureFlagInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_item_show_settings_change_shown_fields_picture() {
        UpdateInfo updateInfo = updateInfo(InputGroup.ITEMS_SHOW_SETTING_CHANGE_SHOWN_FIELDS, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_PICTURE);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(ItemsShowSettingsChangeShownFieldsStage8FinishInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_item_show_settings_change_shown_fields_throw() {
        assertThrows(UnexpectedUserInputStateAndGroupConjunctionException.class, () -> {
            UpdateInfo updateInfo = updateInfo(InputGroup.ITEMS_SHOW_SETTING_CHANGE_SHOWN_FIELDS, InputState.TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_SELL_PRICE);
            inputCommandListener.handleUpdate(updateInfo);
        });
    }

    @Test
    public void handleUpdate_should_item_show_offset() {
        UpdateInfo updateInfo = updateInfo(InputGroup.ITEMS_SHOW, InputState.ITEMS_SHOW_OFFSET);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(ItemsShowStage2FinishInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_item_show_throw() {
        assertThrows(UnexpectedUserInputStateAndGroupConjunctionException.class, () -> {
            UpdateInfo updateInfo = updateInfo(InputGroup.ITEMS_SHOW, InputState.TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_SELL_PRICE);
            inputCommandListener.handleUpdate(updateInfo);
        });
    }

    @Test
    public void handleUpdate_should_trade_by_filters_manager_edit_name() {
        UpdateInfo updateInfo = updateInfo(InputGroup.TRADE_BY_FILTERS_MANAGER_EDIT, InputState.TRADE_BY_FILTERS_MANAGER_NAME);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(TradeByFiltersManagerEditStage2AskTypeInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_trade_by_filters_manager_edit_trade_type() {
        UpdateInfo updateInfo = updateInfo(InputGroup.TRADE_BY_FILTERS_MANAGER_EDIT, InputState.TRADE_BY_FILTERS_MANAGER_TRADE_TYPE);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(TradeByFiltersManagerEditStage3AskFiltersInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_trade_by_filters_manager_edit_filters_names() {
        UpdateInfo updateInfo = updateInfo(InputGroup.TRADE_BY_FILTERS_MANAGER_EDIT, InputState.TRADE_BY_FILTERS_MANAGER_FILTERS_NAMES);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(TradeByFiltersManagerEditStage4AskMinBuySellProfitInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_trade_by_filters_manager_edit_min_buy_sell_profit() {
        UpdateInfo updateInfo = updateInfo(InputGroup.TRADE_BY_FILTERS_MANAGER_EDIT, InputState.TRADE_BY_FILTERS_MANAGER_MIN_BUY_SELL_PROFIT);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(TradeByFiltersManagerEditStage5AskMinProfitPercentInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_trade_by_filters_manager_edit_min_profit_percent() {
        UpdateInfo updateInfo = updateInfo(InputGroup.TRADE_BY_FILTERS_MANAGER_EDIT, InputState.TRADE_BY_FILTERS_MANAGER_MIN_MEDIAN_PRICE_DIFFERENCE_PERCENT);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(TradeByFiltersManagerEditStage6AskPriorityInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_trade_by_filters_manager_edit_priority() {
        UpdateInfo updateInfo = updateInfo(InputGroup.TRADE_BY_FILTERS_MANAGER_EDIT, InputState.TRADE_BY_FILTERS_MANAGER_PRIORITY);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(TradeByFiltersManagerEditStage7AskConfirmationFinishInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_trade_by_filters_manager_edit_throw() {
        assertThrows(UnexpectedUserInputStateAndGroupConjunctionException.class, () -> {
            UpdateInfo updateInfo = updateInfo(InputGroup.TRADE_BY_FILTERS_MANAGER_EDIT, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_PICTURE);
            inputCommandListener.handleUpdate(updateInfo);
        });
    }

    @Test
    public void handleUpdate_should_trade_by_item_id_manager_type_buy_edit_item_id() {
        UpdateInfo updateInfo = updateInfo(InputGroup.TRADE_BY_ITEM_ID_MANAGER_TYPE_BUY_EDIT, InputState.TRADE_BY_ITEM_ID_MANAGER_ITEM_ID);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(TradeByItemIdManagerBuyEditStage2AskBoundaryPriceInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_trade_by_item_id_manager_type_buy_edit_boundary_buy_price() {
        UpdateInfo updateInfo = updateInfo(InputGroup.TRADE_BY_ITEM_ID_MANAGER_TYPE_BUY_EDIT, InputState.TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_BUY_PRICE);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(TradeByItemIdManagerBuyEditStage3AskPriorityInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_trade_by_item_id_manager_type_buy_edit_priority() {
        UpdateInfo updateInfo = updateInfo(InputGroup.TRADE_BY_ITEM_ID_MANAGER_TYPE_BUY_EDIT, InputState.TRADE_BY_ITEM_ID_MANAGER_PRIORITY);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(TradeByItemIdManagerBuyEditStage4AskConfirmationFinishInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_trade_by_item_id_manager_type_buy_edit_throw() {
        assertThrows(UnexpectedUserInputStateAndGroupConjunctionException.class, () -> {
            UpdateInfo updateInfo = updateInfo(InputGroup.TRADE_BY_ITEM_ID_MANAGER_TYPE_BUY_EDIT, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_PICTURE);
            inputCommandListener.handleUpdate(updateInfo);
        });
    }

    @Test
    public void handleUpdate_should_trade_by_item_id_manager_type_sell_edit_item_id() {
        UpdateInfo updateInfo = updateInfo(InputGroup.TRADE_BY_ITEM_ID_MANAGER_TYPE_SELL_EDIT, InputState.TRADE_BY_ITEM_ID_MANAGER_ITEM_ID);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(TradeByItemIdManagerSellEditStage2AskBoundaryPriceInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_trade_by_item_id_manager_type_sell_edit_boundary_sell_price() {
        UpdateInfo updateInfo = updateInfo(InputGroup.TRADE_BY_ITEM_ID_MANAGER_TYPE_SELL_EDIT, InputState.TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_SELL_PRICE);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(TradeByItemIdManagerSellEditStage3AskPriorityInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_trade_by_item_id_manager_type_sell_edit_priority() {
        UpdateInfo updateInfo = updateInfo(InputGroup.TRADE_BY_ITEM_ID_MANAGER_TYPE_SELL_EDIT, InputState.TRADE_BY_ITEM_ID_MANAGER_PRIORITY);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(TradeByItemIdManagerSellEditStage4AskConfirmationFinishInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_trade_by_item_id_manager_type_sell_edit_throw() {
        assertThrows(UnexpectedUserInputStateAndGroupConjunctionException.class, () -> {
            UpdateInfo updateInfo = updateInfo(InputGroup.TRADE_BY_ITEM_ID_MANAGER_TYPE_SELL_EDIT, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_PICTURE);
            inputCommandListener.handleUpdate(updateInfo);
        });
    }

    @Test
    public void handleUpdate_should_trade_by_item_id_manager_type_buy_and_sell_edit_item_id() {
        UpdateInfo updateInfo = updateInfo(InputGroup.TRADE_BY_ITEM_ID_MANAGER_TYPE_BUY_AND_SELL_EDIT, InputState.TRADE_BY_ITEM_ID_MANAGER_ITEM_ID);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(TradeByItemIdManagerBuyAndSellEditStage2AskBoundarySellPriceInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_trade_by_item_id_manager_type_buy_and_sell_edit_boundary_sell_price() {
        UpdateInfo updateInfo = updateInfo(InputGroup.TRADE_BY_ITEM_ID_MANAGER_TYPE_BUY_AND_SELL_EDIT, InputState.TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_SELL_PRICE);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(TradeByItemIdManagerBuyAndSellEditStage3AskBoundaryBuyPriceInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_trade_by_item_id_manager_type_buy_and_sell_edit_boundary_buy_price() {
        UpdateInfo updateInfo = updateInfo(InputGroup.TRADE_BY_ITEM_ID_MANAGER_TYPE_BUY_AND_SELL_EDIT, InputState.TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_BUY_PRICE);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(TradeByItemIdManagerBuyAndSellEditStage4AskPriorityInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_trade_by_item_id_manager_type_buy_and_sell_edit_priority() {
        UpdateInfo updateInfo = updateInfo(InputGroup.TRADE_BY_ITEM_ID_MANAGER_TYPE_BUY_AND_SELL_EDIT, InputState.TRADE_BY_ITEM_ID_MANAGER_PRIORITY);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(TradeByItemIdManagerBuyAndSellEditStage5AskConfirmationFinishInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_trade_by_item_id_manager_type_buy_and_sell_edit_throw() {
        assertThrows(UnexpectedUserInputStateAndGroupConjunctionException.class, () -> {
            UpdateInfo updateInfo = updateInfo(InputGroup.TRADE_BY_ITEM_ID_MANAGER_TYPE_BUY_AND_SELL_EDIT, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_PICTURE);
            inputCommandListener.handleUpdate(updateInfo);
        });
    }

    @Test
    public void handleUpdate_should_trade_by_filters_manager_remove_name() {
        UpdateInfo updateInfo = updateInfo(InputGroup.TRADE_BY_FILTERS_MANAGER_SHOW_OR_REMOVE, InputState.TRADE_BY_FILTERS_MANAGER_NAME);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(TradeByFiltersManagerRemoveStage2AskConfirmationFinishInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_trade_by_filters_manager_remove_throw() {
        assertThrows(UnexpectedUserInputStateAndGroupConjunctionException.class, () -> {
            UpdateInfo updateInfo = updateInfo(InputGroup.TRADE_BY_FILTERS_MANAGER_SHOW_OR_REMOVE, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_PICTURE);
            inputCommandListener.handleUpdate(updateInfo);
        });
    }

    @Test
    public void handleUpdate_should_trade_by_item_id_manager_remove_item_id() {
        UpdateInfo updateInfo = updateInfo(InputGroup.TRADE_BY_ITEM_ID_MANAGER_SHOW_OR_REMOVE, InputState.TRADE_BY_ITEM_ID_MANAGER_ITEM_ID);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(TradeByItemIdManagerRemoveStage2AskConfirmationFinishInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_trade_by_item_id_manager_remove_throw() {
        assertThrows(UnexpectedUserInputStateAndGroupConjunctionException.class, () -> {
            UpdateInfo updateInfo = updateInfo(InputGroup.TRADE_BY_ITEM_ID_MANAGER_SHOW_OR_REMOVE, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_PICTURE);
            inputCommandListener.handleUpdate(updateInfo);
        });
    }

    @Test
    public void handleUpdate_should_sell_trade_priority_expression() {
        UpdateInfo updateInfo = updateInfo(InputGroup.TRADE_MANAGERS_SETTINGS_CHANGE_SELL_TRADE_PRIORITY_EXPRESSION, InputState.TRADE_MANAGERS_SETTINGS_TRADE_PRIORITY_EXPRESSION);

        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(TradeManagersSettingsChangeSellTradePriorityExpressionAskConfirmInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_sell_trade_priority_expression_throw() {
        assertThrows(UnexpectedUserInputStateAndGroupConjunctionException.class, () -> {
            UpdateInfo updateInfo = updateInfo(InputGroup.TRADE_MANAGERS_SETTINGS_CHANGE_SELL_TRADE_PRIORITY_EXPRESSION, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_PICTURE);
            inputCommandListener.handleUpdate(updateInfo);
        });
    }

    @Test
    public void handleUpdate_should_buy_trade_priority_expression() {
        UpdateInfo updateInfo = updateInfo(InputGroup.TRADE_MANAGERS_SETTINGS_CHANGE_BUY_TRADE_PRIORITY_EXPRESSION, InputState.TRADE_MANAGERS_SETTINGS_TRADE_PRIORITY_EXPRESSION);

        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(TradeManagersSettingsChangeBuyTradePriorityExpressionAskConfirmInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_buy_trade_priority_expression_throw() {
        assertThrows(UnexpectedUserInputStateAndGroupConjunctionException.class, () -> {
            UpdateInfo updateInfo = updateInfo(InputGroup.TRADE_MANAGERS_SETTINGS_CHANGE_BUY_TRADE_PRIORITY_EXPRESSION, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_PICTURE);
            inputCommandListener.handleUpdate(updateInfo);
        });
    }

    @Test
    public void handleUpdate_should_ubi_account_entry_link_full_or_email() {
        UpdateInfo updateInfo = updateInfo(InputGroup.UBI_ACCOUNT_ENTRY_LINK, InputState.UBI_ACCOUNT_ENTRY_EMAIL);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(UbiAccountEntryAuthorizeStage2AskPasswordInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_ubi_account_entry_link_password() {
        UpdateInfo updateInfo = updateInfo(InputGroup.UBI_ACCOUNT_ENTRY_LINK, InputState.UBI_ACCOUNT_ENTRY_PASSWORD);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(UbiAccountEntryAuthorizeStage3Ask2FaCodeInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_ubi_account_entry_link_2FaCode() {
        UpdateInfo updateInfo = updateInfo(InputGroup.UBI_ACCOUNT_ENTRY_LINK, InputState.UBI_ACCOUNT_ENTRY_2FA_CODE);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(UbiAccountEntryAuthorizeStage4FinishInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_ubi_account_entry_link_throw() {
        assertThrows(UnexpectedUserInputStateAndGroupConjunctionException.class, () -> {
            UpdateInfo updateInfo = updateInfo(InputGroup.UBI_ACCOUNT_ENTRY_LINK, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_PICTURE);
            inputCommandListener.handleUpdate(updateInfo);
        });
    }

    @Test
    public void handleUpdate_should_ubi_account_entry_reauthorize_2FaCode() {
        UpdateInfo updateInfo = updateInfo(InputGroup.UBI_ACCOUNT_ENTRY_REAUTHORIZE_2FA_CODE, InputState.UBI_ACCOUNT_ENTRY_2FA_CODE);
        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(UbiAccountEntryReauthorizeEnter2FACodeStage2ExceptionOrSuccessFinishInput.class, updateInfo);
    }

    @Test
    public void handleUpdate_should_ubi_account_entry_reauthorize_throw() {
        assertThrows(UnexpectedUserInputStateAndGroupConjunctionException.class, () -> {
            UpdateInfo updateInfo = updateInfo(InputGroup.UBI_ACCOUNT_ENTRY_REAUTHORIZE_2FA_CODE, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_PICTURE);
            inputCommandListener.handleUpdate(updateInfo);
        });
    }

    private UpdateInfo updateInfo(InputGroup inputGroup, InputState inputState) {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setInputGroup(inputGroup);
        updateInfo.setInputState(inputState);
        return updateInfo;
    }
}