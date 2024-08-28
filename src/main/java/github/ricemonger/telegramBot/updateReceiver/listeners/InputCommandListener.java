package github.ricemonger.telegramBot.updateReceiver.listeners;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.executors.ExecutorsService;
import github.ricemonger.telegramBot.executors.cancel.Cancel;
import github.ricemonger.telegramBot.executors.cancel.SilentCancel;
import github.ricemonger.telegramBot.executors.itemFilters.edit.*;
import github.ricemonger.telegramBot.executors.itemFilters.showOrRemove.FilterShowChosenStage2RemoveRequestInput;
import github.ricemonger.telegramBot.executors.items.settings.appliedFilters.ItemsShowSettingsChangeAppliedFiltersStage2AskActionInput;
import github.ricemonger.telegramBot.executors.items.settings.appliedFilters.ItemsShowSettingsChangeAppliedFiltersStage3FinishInput;
import github.ricemonger.telegramBot.executors.items.settings.messageLimit.ItemsShowSettingsChangeMessageLimitFinishInput;
import github.ricemonger.telegramBot.executors.items.settings.shownFields.*;
import github.ricemonger.telegramBot.executors.items.show.ItemsShowStage2FinishInput;
import github.ricemonger.telegramBot.executors.tradeManagers.edit.itemFilter.*;
import github.ricemonger.telegramBot.executors.tradeManagers.edit.oneItem.buy.TradeByItemIdManagerBuyEditStage2AskBoundaryPriceInput;
import github.ricemonger.telegramBot.executors.tradeManagers.edit.oneItem.buy.TradeByItemIdManagerBuyEditStage3AskStartingPriceInput;
import github.ricemonger.telegramBot.executors.tradeManagers.edit.oneItem.buy.TradeByItemIdManagerBuyEditStage4AskPriorityInput;
import github.ricemonger.telegramBot.executors.tradeManagers.edit.oneItem.buy.TradeByItemIdManagerBuyEditStage5AskConfirmationFinishInput;
import github.ricemonger.telegramBot.executors.tradeManagers.edit.oneItem.buyAndSell.*;
import github.ricemonger.telegramBot.executors.tradeManagers.edit.oneItem.sell.TradeByItemIdManagerSellEditStage2AskBoundaryPriceInput;
import github.ricemonger.telegramBot.executors.tradeManagers.edit.oneItem.sell.TradeByItemIdManagerSellEditStage3AskStartingPriceInput;
import github.ricemonger.telegramBot.executors.tradeManagers.edit.oneItem.sell.TradeByItemIdManagerSellEditStage4AskPriorityInput;
import github.ricemonger.telegramBot.executors.tradeManagers.edit.oneItem.sell.TradeByItemIdManagerSellEditStage5AskConfirmationFinishInput;
import github.ricemonger.telegramBot.executors.tradeManagers.showRemove.remove_or_change_enabled.itemFilters.TradeByFiltersManagerRemoveStage2AskConfirmationFinishInput;
import github.ricemonger.telegramBot.executors.tradeManagers.showRemove.remove_or_change_enabled.itemId.TradeByItemIdManagerRemoveStage2AskConfirmationFinishInput;
import github.ricemonger.telegramBot.executors.ubi_account_entry.link.UbiAccountEntryLinkStage1AskFullOrEmailInput;
import github.ricemonger.telegramBot.executors.ubi_account_entry.link.UbiAccountEntryLinkStage2AskPasswordInput;
import github.ricemonger.utils.exceptions.InvalidUserInputGroupException;
import github.ricemonger.utils.exceptions.UnexpectedUserInputStateAndGroupConjunctionException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class InputCommandListener {

    private final ExecutorsService executorsService;

    public void handleUpdate(UpdateInfo updateInfo) {
        if (cancelMessageTextOrCallbackQueryText(updateInfo)) {
            executorsService.execute(Cancel.class, updateInfo);
        } else if (silentCancelMessageTextOrCallbackQueryText(updateInfo)) {
            executorsService.execute(SilentCancel.class, updateInfo);
        } else {
            switch (updateInfo.getInputGroup()) {

                case ITEM_FILTER_EDIT -> itemFilterEditInputGroup(updateInfo);

                case ITEM_FILTER_SHOW_OR_REMOVE -> itemFilterShowOrRemoveInputGroup(updateInfo);

                case ITEMS_SHOW_SETTING_CHANGE_APPLIED_FILTERS -> itemShowSettingsChangeAppliedFiltersInputGroup(updateInfo);

                case ITEMS_SHOW_SETTINGS_CHANGE_MESSAGE_LIMIT -> itemShowSettingsChangeMessageLimitInputGroup(updateInfo);

                case ITEMS_SHOW_SETTING_CHANGE_SHOWN_FIELDS -> itemShowSettingsChangeShownFieldsInputGroup(updateInfo);

                case ITEMS_SHOW -> itemShowInputGroup(updateInfo);

                case TRADE_BY_FILTERS_MANAGER_EDIT -> tradeByFiltersManagerEditInputGroup(updateInfo);

                case TRADE_BY_ITEM_ID_MANAGER_TYPE_BUY_EDIT -> tradeByItemIdManagerTypeBuyEditInputGroup(updateInfo);

                case TRADE_BY_ITEM_ID_MANAGER_TYPE_SELL_EDIT -> tradeByItemIdManagerTypeSellInputGroup(updateInfo);

                case TRADE_BY_ITEM_ID_MANAGER_TYPE_BUY_AND_SELL_EDIT -> tradeByItemIdManagerTypeBuyAndSellInputGroup(updateInfo);

                case TRADE_BY_FILTERS_MANAGER_SHOW_OR_REMOVE -> tradeByFiltersManagerRemoveInputGroup(updateInfo);

                case TRADE_BY_ITEM_ID_MANAGER_SHOW_OR_REMOVE -> tradeByItemIdManagerRemoveInputGroup(updateInfo);

                case UBI_ACCOUNT_ENTRY_LINK -> ubiAccountEntryLinkInputGroup(updateInfo);

                default -> throw new InvalidUserInputGroupException(updateInfo.getInputGroup().name());
            }
        }
    }

    private boolean cancelMessageTextOrCallbackQueryText(UpdateInfo updateInfo) {
        return (updateInfo.isHasMessage() && updateInfo.getMessageText().equals("/cancel"))
               ||
               updateInfo.isHasCallBackQuery() && updateInfo.getCallbackQueryData().equals(Callbacks.CANCEL);
    }

    private boolean silentCancelMessageTextOrCallbackQueryText(UpdateInfo updateInfo) {
        return (updateInfo.isHasMessage() && updateInfo.getMessageText().equals("/silentCancel"))
               ||
               updateInfo.isHasCallBackQuery() && updateInfo.getCallbackQueryData().equals(Callbacks.CANCEL_SILENT);
    }

    private void itemFilterEditInputGroup(UpdateInfo updateInfo) {
        InputState inputState = updateInfo.getInputState();

        switch (inputState) {
            case ITEM_FILTER_NAME -> executorsService.execute(FilterEditStage2AskFilterTypeInput.class, updateInfo);

            case ITEM_FILTER_TYPE -> executorsService.execute(FilterEditStage3AskIsOwnedInput.class, updateInfo);

            case ITEM_FILTER_IS_OWNED -> executorsService.execute(FilterEditStage4AskItemNamePatternsInput.class, updateInfo);

            case ITEM_FILTER_ITEM_NAME_PATTERNS -> executorsService.execute(FilterEditStage5AskItemTypesInput.class, updateInfo);

            case ITEM_FILTER_ITEM_TYPES -> executorsService.execute(FilterEditStage6AskItemTagsRarityInput.class, updateInfo);

            case ITEM_FILTER_ITEM_TAGS_RARITY -> executorsService.execute(FilterEditStage7AskItemTagsSeasonsInput.class, updateInfo);

            case ITEM_FILTER_ITEM_TAGS_SEASONS -> executorsService.execute(FilterEditStage8AskItemTagsOperatorsInput.class, updateInfo);

            case ITEM_FILTER_ITEM_TAGS_OPERATORS -> executorsService.execute(FilterEditStage9AskItemTagsWeaponsInput.class, updateInfo);

            case ITEM_FILTER_ITEM_TAGS_WEAPONS -> executorsService.execute(FilterEditStage10AskItemTagsEventsInput.class, updateInfo);

            case ITEM_FILTER_ITEM_TAGS_EVENTS -> executorsService.execute(FilterEditStage11AskItemTagsEsportsInput.class, updateInfo);

            case ITEM_FILTER_ITEM_TAGS_ESPORTS -> executorsService.execute(FilterEditStage12AskItemTagsOtherInput.class, updateInfo);

            case ITEM_FILTER_ITEM_TAGS_OTHER -> executorsService.execute(FilterEditStage13AskMinPriceInput.class, updateInfo);

            case ITEM_FILTER_MIN_PRICE -> executorsService.execute(FilterEditStage14AskMaxPriceInput.class, updateInfo);

            case ITEM_FILTER_MAX_PRICE -> executorsService.execute(FilterEditStage15AskMinLastSoldPriceInput.class, updateInfo);

            case ITEM_FILTER_MIN_LAST_SOLD_PRICE -> executorsService.execute(FilterEditStage16AskMaxLastSoldPriceInput.class, updateInfo);

            case ITEM_FILTER_MAX_LAST_SOLD_PRICE -> executorsService.execute(FilterEditStage17FinishRequestInput.class, updateInfo);

            default ->
                    throw new UnexpectedUserInputStateAndGroupConjunctionException(updateInfo.getInputState().name() + " - state:group - " + updateInfo.getInputGroup().name());
        }
    }

    private void itemFilterShowOrRemoveInputGroup(UpdateInfo updateInfo) {
        InputState inputState = updateInfo.getInputState();

        if (Objects.requireNonNull(inputState) == InputState.ITEM_FILTER_NAME) {
            executorsService.execute(FilterShowChosenStage2RemoveRequestInput.class, updateInfo);
        } else {
            throw new UnexpectedUserInputStateAndGroupConjunctionException(updateInfo.getInputState().name() + " - state:group - " + updateInfo.getInputGroup().name());
        }
    }

    private void itemShowSettingsChangeAppliedFiltersInputGroup(UpdateInfo updateInfo) {
        InputState inputState = updateInfo.getInputState();

        switch (inputState) {
            case ITEM_FILTER_NAME -> executorsService.execute(ItemsShowSettingsChangeAppliedFiltersStage2AskActionInput.class, updateInfo);

            case ITEMS_SHOW_SETTINGS_APPLIED_FILTER_ADD_OR_REMOVE ->
                    executorsService.execute(ItemsShowSettingsChangeAppliedFiltersStage3FinishInput.class, updateInfo);

            default ->
                    throw new UnexpectedUserInputStateAndGroupConjunctionException(updateInfo.getInputState().name() + " - state:group - " + updateInfo.getInputGroup().name());
        }
    }

    private void itemShowSettingsChangeMessageLimitInputGroup(UpdateInfo updateInfo) {
        InputState inputState = updateInfo.getInputState();

        if (Objects.requireNonNull(inputState) == InputState.ITEMS_SHOW_SETTING_MESSAGE_LIMIT) {
            executorsService.execute(ItemsShowSettingsChangeMessageLimitFinishInput.class, updateInfo);
        } else {
            throw new UnexpectedUserInputStateAndGroupConjunctionException(updateInfo.getInputState().name() + " - state:group - " + updateInfo.getInputGroup().name());
        }
    }

    private void itemShowSettingsChangeShownFieldsInputGroup(UpdateInfo updateInfo) {
        InputState inputState = updateInfo.getInputState();

        switch (inputState) {
            case ITEMS_SHOW_SETTING_SHOWN_FIELDS_ITEM_NAME ->
                    executorsService.execute(ItemsShowSettingsChangeShownFieldsStage2AskItemTypeFlagInput.class, updateInfo);

            case ITEMS_SHOW_SETTING_SHOWN_FIELDS_ITEM_TYPE ->
                    executorsService.execute(ItemsShowSettingsChangeShownFieldsStage3AskMaxBuyPriceFlagInput.class,
                            updateInfo);

            case ITEMS_SHOW_SETTING_SHOWN_FIELDS_MAX_BUY_PRICE ->
                    executorsService.execute(ItemsShowSettingsChangeShownFieldsStage4AskBuyOrdersCountFlagInput.class,
                            updateInfo);

            case ITEMS_SHOW_SETTING_SHOWN_FIELDS_BUY_ORDERS_COUNT ->
                    executorsService.execute(ItemsShowSettingsChangeShownFieldsStage5AskMinSellPriceFlagInput.class, updateInfo);

            case ITEMS_SHOW_SETTING_SHOWN_FIELDS_MIN_SELL_PRICE ->
                    executorsService.execute(ItemsShowSettingsChangeShownFieldsStage6AskSellOrdersCountFlagInput.class,
                            updateInfo);

            case ITEMS_SHOW_SETTING_SHOWN_FIELDS_SELL_ORDERS_COUNT ->
                    executorsService.execute(ItemsShowSettingsChangeShownFieldsStage7AskPictureFlagInput.class,
                            updateInfo);

            case ITEMS_SHOW_SETTING_SHOWN_FIELDS_PICTURE ->
                    executorsService.execute(ItemsShowSettingsChangeShownFieldsStage8FinishInput.class, updateInfo);

            default ->
                    throw new UnexpectedUserInputStateAndGroupConjunctionException(updateInfo.getInputState().name() + " - state:group - " + updateInfo.getInputGroup().name());
        }
    }

    private void itemShowInputGroup(UpdateInfo updateInfo) {
        InputState inputState = updateInfo.getInputState();

        if (Objects.requireNonNull(inputState) == InputState.ITEMS_SHOW_OFFSET) {
            executorsService.execute(ItemsShowStage2FinishInput.class, updateInfo);
        } else {
            throw new UnexpectedUserInputStateAndGroupConjunctionException(updateInfo.getInputState().name() + " - state:group - " + updateInfo.getInputGroup().name());
        }
    }

    private void tradeByFiltersManagerEditInputGroup(UpdateInfo updateInfo) {
        InputState inputState = updateInfo.getInputState();

        switch (inputState) {
            case TRADE_BY_FILTERS_MANAGER_NAME -> executorsService.execute(TradeByFiltersManagerEditStage2AskTypeInput.class, updateInfo);

            case TRADE_BY_FILTERS_MANAGER_TRADE_TYPE ->
                    executorsService.execute(TradeByFiltersManagerEditStage3AskFiltersInput.class, updateInfo);

            case TRADE_BY_FILTERS_MANAGER_FILTERS_NAMES ->
                    executorsService.execute(TradeByFiltersManagerEditStage4AskMinBuySellProfitInput.class, updateInfo);

            case TRADE_BY_FILTERS_MANAGER_MIN_BUY_SELL_PROFIT ->
                    executorsService.execute(TradeByFiltersManagerEditStage5AskMinProfitPercentInput.class, updateInfo);

            case TRADE_BY_FILTERS_MANAGER_MIN_PROFIT_PERCENT ->
                    executorsService.execute(TradeByFiltersManagerEditStage6AskPriorityInput.class, updateInfo);

            case TRADE_BY_FILTERS_MANAGER_PRIORITY ->
                    executorsService.execute(TradeByFiltersManagerEditStage7AskConfirmationFinishInput.class, updateInfo);

            default ->
                    throw new UnexpectedUserInputStateAndGroupConjunctionException(updateInfo.getInputState().name() + " - state:group - " + updateInfo.getInputGroup().name());
        }
    }


    private void tradeByItemIdManagerTypeBuyEditInputGroup(UpdateInfo updateInfo) {
        InputState inputState = updateInfo.getInputState();

        switch (inputState) {
            case TRADE_BY_ITEM_ID_MANAGER_ITEM_ID ->
                    executorsService.execute(TradeByItemIdManagerBuyEditStage2AskBoundaryPriceInput.class, updateInfo);

            case TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_BUY_PRICE ->
                    executorsService.execute(TradeByItemIdManagerBuyEditStage3AskStartingPriceInput.class, updateInfo);

            case TRADE_BY_ITEM_ID_MANAGER_STARTING_BUY_PRICE ->
                    executorsService.execute(TradeByItemIdManagerBuyEditStage4AskPriorityInput.class, updateInfo);

            case TRADE_BY_ITEM_ID_MANAGER_PRIORITY ->
                    executorsService.execute(TradeByItemIdManagerBuyEditStage5AskConfirmationFinishInput.class, updateInfo);

            default ->
                    throw new UnexpectedUserInputStateAndGroupConjunctionException(updateInfo.getInputState().name() + " - state:group - " + updateInfo.getInputGroup().name());
        }
    }

    private void tradeByItemIdManagerTypeSellInputGroup(UpdateInfo updateInfo) {
        InputState inputState = updateInfo.getInputState();

        switch (inputState) {
            case TRADE_BY_ITEM_ID_MANAGER_ITEM_ID ->
                    executorsService.execute(TradeByItemIdManagerSellEditStage2AskBoundaryPriceInput.class, updateInfo);

            case TRADE_BY_ITEM_ID_MANAGER_EDIT_BOUNDARY_SELL_PRICE ->
                    executorsService.execute(TradeByItemIdManagerSellEditStage3AskStartingPriceInput.class, updateInfo);

            case TRADE_BY_ITEM_ID_MANAGER_STARTING_SELL_PRICE ->
                    executorsService.execute(TradeByItemIdManagerSellEditStage4AskPriorityInput.class, updateInfo);

            case TRADE_BY_ITEM_ID_MANAGER_PRIORITY ->
                    executorsService.execute(TradeByItemIdManagerSellEditStage5AskConfirmationFinishInput.class, updateInfo);

            default ->
                    throw new UnexpectedUserInputStateAndGroupConjunctionException(updateInfo.getInputState().name() + " - state:group - " + updateInfo.getInputGroup().name());
        }
    }

    private void tradeByItemIdManagerTypeBuyAndSellInputGroup(UpdateInfo updateInfo) {
        InputState inputState = updateInfo.getInputState();

        switch (inputState) {
            case TRADE_BY_ITEM_ID_MANAGER_ITEM_ID ->
                    executorsService.execute(TradeByItemIdManagerBuyAndSellEditStage2AskBoundarySellPriceInput.class, updateInfo);

            case TRADE_BY_ITEM_ID_MANAGER_EDIT_BOUNDARY_SELL_PRICE ->
                    executorsService.execute(TradeByItemIdManagerBuyAndSellEditStage3AskStartingSellPriceInput.class, updateInfo);

            case TRADE_BY_ITEM_ID_MANAGER_STARTING_SELL_PRICE ->
                    executorsService.execute(TradeByItemIdManagerBuyAndSellEditStage4AskBoundaryBuyPriceInput.class, updateInfo);

            case TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_BUY_PRICE ->
                    executorsService.execute(TradeByItemIdManagerBuyAndSellEditStage5AskStartingBuyPriceInput.class, updateInfo);

            case TRADE_BY_ITEM_ID_MANAGER_STARTING_BUY_PRICE ->
                    executorsService.execute(TradeByItemIdManagerBuyAndSellEditStage6AskPriorityInput.class, updateInfo);

            case TRADE_BY_ITEM_ID_MANAGER_PRIORITY ->
                    executorsService.execute(TradeByItemIdManagerBuyAndSellEditStage7AskConfirmationFinishInput.class, updateInfo);

            default ->
                    throw new UnexpectedUserInputStateAndGroupConjunctionException(updateInfo.getInputState().name() + " - state:group - " + updateInfo.getInputGroup().name());
        }
    }

    private void tradeByFiltersManagerRemoveInputGroup(UpdateInfo updateInfo) {
        InputState inputState = updateInfo.getInputState();

        switch (inputState) {
            case TRADE_BY_FILTERS_MANAGER_NAME ->
                    executorsService.execute(TradeByFiltersManagerRemoveStage2AskConfirmationFinishInput.class, updateInfo);

            default ->
                    throw new UnexpectedUserInputStateAndGroupConjunctionException(updateInfo.getInputState().name() + " - state:group - " + updateInfo.getInputGroup().name());
        }
    }

    private void tradeByItemIdManagerRemoveInputGroup(UpdateInfo updateInfo) {
        InputState inputState = updateInfo.getInputState();

        switch (inputState) {
            case TRADE_BY_ITEM_ID_MANAGER_ITEM_ID ->
                    executorsService.execute(TradeByItemIdManagerRemoveStage2AskConfirmationFinishInput.class, updateInfo);

            default ->
                    throw new UnexpectedUserInputStateAndGroupConjunctionException(updateInfo.getInputState().name() + " - state:group - " + updateInfo.getInputGroup().name());
        }
    }

    private void ubiAccountEntryLinkInputGroup(UpdateInfo updateInfo) {
        InputState inputState = updateInfo.getInputState();

        switch (inputState) {

            case UBI_ACCOUNT_ENTRY_FULL_OR_EMAIL -> executorsService.execute(UbiAccountEntryLinkStage1AskFullOrEmailInput.class, updateInfo);

            case UBI_ACCOUNT_ENTRY_PASSWORD -> executorsService.execute(UbiAccountEntryLinkStage2AskPasswordInput.class, updateInfo);

            default ->
                    throw new UnexpectedUserInputStateAndGroupConjunctionException(updateInfo.getInputState().name() + " - state:group - " + updateInfo.getInputGroup().name());
        }
    }
}
