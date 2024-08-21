package github.ricemonger.telegramBot.updateReceiver.listeners;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.executors.ExecutorsService;
import github.ricemonger.telegramBot.executors.cancel.Cancel;
import github.ricemonger.telegramBot.executors.cancel.SilentCancel;
import github.ricemonger.telegramBot.executors.credentials.link.CredentialsAddFullOrEmailInput;
import github.ricemonger.telegramBot.executors.credentials.link.CredentialsAddPasswordInput;
import github.ricemonger.telegramBot.executors.itemFilters.edit.*;
import github.ricemonger.telegramBot.executors.itemFilters.showOrRemove.FilterShowChosenRemoveRequestInput;
import github.ricemonger.telegramBot.executors.items.settings.appliedFilters.ItemsShowSettingsChangeAppliedFiltersStage2AskActionInput;
import github.ricemonger.telegramBot.executors.items.settings.appliedFilters.ItemsShowSettingsChangeAppliedFiltersStage3FinishInput;
import github.ricemonger.telegramBot.executors.items.settings.messageLimit.ItemsShowSettingsChangeMessageLimitFinishInput;
import github.ricemonger.telegramBot.executors.items.settings.shownFields.*;
import github.ricemonger.telegramBot.executors.items.show.ItemsShowStage2FinishInput;
import github.ricemonger.telegramBot.executors.tradeManagers.createUpdate.oneItem.buy.TradesOneItemBuyEditStage2AskBoundaryPriceInput;
import github.ricemonger.telegramBot.executors.tradeManagers.createUpdate.oneItem.buy.TradesOneItemBuyEditStage3AskStartingPriceInput;
import github.ricemonger.telegramBot.executors.tradeManagers.createUpdate.oneItem.buy.TradesOneItemBuyEditStage4AskPriorityInput;
import github.ricemonger.telegramBot.executors.tradeManagers.createUpdate.oneItem.buy.TradesOneItemBuyEditStage5AskConfirmationInput;
import github.ricemonger.telegramBot.executors.tradeManagers.createUpdate.oneItem.buyAndSell.*;
import github.ricemonger.telegramBot.executors.tradeManagers.createUpdate.oneItem.sell.TradesOneItemSellEditStage2AskBoundaryPriceInput;
import github.ricemonger.telegramBot.executors.tradeManagers.createUpdate.oneItem.sell.TradesOneItemSellEditStage3AskStartingPriceInput;
import github.ricemonger.telegramBot.executors.tradeManagers.createUpdate.oneItem.sell.TradesOneItemSellEditStage4AskPriorityInput;
import github.ricemonger.telegramBot.executors.tradeManagers.createUpdate.oneItem.sell.TradesOneItemSellEditStage5AskConfirmationInput;
import github.ricemonger.telegramBot.executors.tradeManagers.showRemove.remove.itemId.TradeManagersByItemIdRemoveStage2AskConfirmationInput;
import github.ricemonger.utils.exceptions.InvalidUserInputGroupException;
import github.ricemonger.utils.exceptions.InvalidUserInputStateAndGroupConjunctionException;
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

                case CREDENTIALS_ADD -> credentialsAddInputGroup(updateInfo);

                case CREDENTIALS_REMOVE_ONE -> credentialsRemoveOneInputGroup(updateInfo);

                case FILTERS_EDIT -> filterEditInputGroup(updateInfo);

                case FILTERS_SHOW_REMOVE -> filterShowOrRemoveInputGroup(updateInfo);

                case ITEM_SHOW -> itemShowInputGroup(updateInfo);

                case ITEMS_SHOW_SETTINGS_CHANGE_MESSAGE_LIMIT -> itemShowSettingsChangeMessageLimitInputGroup(updateInfo);

                case ITEMS_SHOW_SETTING_CHANGE_SHOWN_FIELDS -> itemShowSettingsChangeShownFieldsInputGroup(updateInfo);

                case ITEMS_SHOW_SETTING_APPLIED_FILTERS_CHANGE -> itemShowSettingsChangeAppliedFiltersInputGroup(updateInfo);

                case TRADES_EDIT_ONE_ITEM_BUY -> tradesEditOneItemBuyInputGroup(updateInfo);

                case TRADES_EDIT_ONE_ITEM_SELL -> tradesEditOneItemSellInputGroup(updateInfo);

                case TRADES_EDIT_ONE_ITEM_BUY_AND_SELL -> tradesEditOneItemBuyAndSellInputGroup(updateInfo);

                case TRADES_REMOVE_ITEM_ID -> tradesOneItemRemoveInputGroup(updateInfo);

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
               updateInfo.isHasCallBackQuery() && updateInfo.getCallbackQueryData().equals(Callbacks.SILENT_CANCEL);
    }

    private void credentialsAddInputGroup(UpdateInfo updateInfo) {
        InputState inputState = updateInfo.getInputState();

        switch (inputState) {

            case CREDENTIALS_FULL_OR_EMAIL -> executorsService.execute(CredentialsAddFullOrEmailInput.class, updateInfo);

            case CREDENTIALS_PASSWORD -> executorsService.execute(CredentialsAddPasswordInput.class, updateInfo);

            default ->
                    throw new InvalidUserInputStateAndGroupConjunctionException(updateInfo.getInputState().name() + " - state:group - " + updateInfo.getInputGroup().name());
        }
    }

    private void credentialsRemoveOneInputGroup(UpdateInfo updateInfo) {
        InputState inputState = updateInfo.getInputState();

        switch (inputState) {

            //case CREDENTIALS_FULL_OR_EMAIL -> executorsService.execute(CredentialsRemoveOneEmailInput.class, updateInfo);

            default ->
                    throw new InvalidUserInputStateAndGroupConjunctionException(updateInfo.getInputState().name() + " - state:group - " + updateInfo.getInputGroup().name());
        }
    }

    private void filterEditInputGroup(UpdateInfo updateInfo) {
        InputState inputState = updateInfo.getInputState();

        switch (inputState) {
            case FILTER_NAME -> executorsService.execute(FilterEditStage2AskFilterTypeInput.class, updateInfo);

            case FILTER_TYPE -> executorsService.execute(FilterEditStage3AskIsOwnedInput.class, updateInfo);

            case FILTER_IS_OWNED -> executorsService.execute(FilterEditStage4AskItemNamePatternsInput.class, updateInfo);

            case FILTER_ITEM_NAME_PATTERNS -> executorsService.execute(FilterEditStage5AskItemTypesInput.class, updateInfo);

            case FILTER_ITEM_TYPES -> executorsService.execute(FilterEditStage6AskItemTagsRarityInput.class, updateInfo);

            case FILTER_ITEM_TAGS_RARITY -> executorsService.execute(FilterEditStage7AskItemTagsSeasonsInput.class, updateInfo);

            case FILTER_ITEM_TAGS_SEASONS -> executorsService.execute(FilterEditStage8AskItemTagsOperatorsInput.class, updateInfo);

            case FILTER_ITEM_TAGS_OPERATORS -> executorsService.execute(FilterEditStage9AskItemTagsWeaponsInput.class, updateInfo);

            case FILTER_ITEM_TAGS_WEAPONS -> executorsService.execute(FilterEditStage10AskItemTagsEventsInput.class, updateInfo);

            case FILTER_ITEM_TAGS_EVENTS -> executorsService.execute(FilterEditStage11AskItemTagsEsportsInput.class, updateInfo);

            case FILTER_ITEM_TAGS_ESPORTS -> executorsService.execute(FilterEditStage12AskItemTagsOtherInput.class, updateInfo);

            case FILTER_ITEM_TAGS_OTHER -> executorsService.execute(FilterEditStage13AskMinPriceInput.class, updateInfo);

            case FILTER_MIN_PRICE -> executorsService.execute(FilterEditStage14AskMaxPriceInput.class, updateInfo);

            case FILTER_MAX_PRICE -> executorsService.execute(FilterEditStage15AskMinLastSoldPriceInput.class, updateInfo);

            case FILTER_MIN_LAST_SOLD_PRICE -> executorsService.execute(FilterEditStage16AskMaxLastSoldPriceInput.class, updateInfo);

            case FILTER_MAX_LAST_SOLD_PRICE -> executorsService.execute(FilterEditStage17FinishInput.class, updateInfo);

            default ->
                    throw new InvalidUserInputStateAndGroupConjunctionException(updateInfo.getInputState().name() + " - state:group - " + updateInfo.getInputGroup().name());
        }
    }

    private void filterShowOrRemoveInputGroup(UpdateInfo updateInfo) {
        InputState inputState = updateInfo.getInputState();

        if (Objects.requireNonNull(inputState) == InputState.FILTER_NAME) {
            executorsService.execute(FilterShowChosenRemoveRequestInput.class, updateInfo);
        } else {
            throw new InvalidUserInputStateAndGroupConjunctionException(updateInfo.getInputState().name() + " - state:group - " + updateInfo.getInputGroup().name());
        }
    }

    private void itemShowInputGroup(UpdateInfo updateInfo) {
        InputState inputState = updateInfo.getInputState();

        if (Objects.requireNonNull(inputState) == InputState.ITEMS_SHOW_OFFSET) {
            executorsService.execute(ItemsShowStage2FinishInput.class, updateInfo);
        } else {
            throw new InvalidUserInputStateAndGroupConjunctionException(updateInfo.getInputState().name() + " - state:group - " + updateInfo.getInputGroup().name());
        }
    }

    private void itemShowSettingsChangeMessageLimitInputGroup(UpdateInfo updateInfo) {
        InputState inputState = updateInfo.getInputState();

        if (Objects.requireNonNull(inputState) == InputState.ITEMS_SHOW_SETTING_MESSAGE_LIMIT) {
            executorsService.execute(ItemsShowSettingsChangeMessageLimitFinishInput.class, updateInfo);
        } else {
            throw new InvalidUserInputStateAndGroupConjunctionException(updateInfo.getInputState().name() + " - state:group - " + updateInfo.getInputGroup().name());
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
                    throw new InvalidUserInputStateAndGroupConjunctionException(updateInfo.getInputState().name() + " - state:group - " + updateInfo.getInputGroup().name());
        }
    }

    private void itemShowSettingsChangeAppliedFiltersInputGroup(UpdateInfo updateInfo) {
        InputState inputState = updateInfo.getInputState();

        switch (inputState) {
            case FILTER_NAME -> executorsService.execute(ItemsShowSettingsChangeAppliedFiltersStage2AskActionInput.class, updateInfo);

            case ITEMS_SHOW_SETTINGS_APPLIED_FILTER_ADD_OR_REMOVE ->
                    executorsService.execute(ItemsShowSettingsChangeAppliedFiltersStage3FinishInput.class, updateInfo);

            default ->
                    throw new InvalidUserInputStateAndGroupConjunctionException(updateInfo.getInputState().name() + " - state:group - " + updateInfo.getInputGroup().name());
        }
    }

    private void tradesEditOneItemBuyInputGroup(UpdateInfo updateInfo) {
        InputState inputState = updateInfo.getInputState();

        switch (inputState) {
            case TRADES_EDIT_ONE_ITEM_ITEM_ID -> executorsService.execute(TradesOneItemBuyEditStage2AskBoundaryPriceInput.class, updateInfo);

            case TRADES_EDIT_ONE_ITEM_BOUNDARY_BUY_PRICE ->
                    executorsService.execute(TradesOneItemBuyEditStage3AskStartingPriceInput.class, updateInfo);

            case TRADES_EDIT_ONE_ITEM_STARTING_BUY_PRICE -> executorsService.execute(TradesOneItemBuyEditStage4AskPriorityInput.class, updateInfo);

            case TRADES_EDIT_ONE_ITEM_PRIORITY -> executorsService.execute(TradesOneItemBuyEditStage5AskConfirmationInput.class, updateInfo);

            default ->
                    throw new InvalidUserInputStateAndGroupConjunctionException(updateInfo.getInputState().name() + " - state:group - " + updateInfo.getInputGroup().name());
        }
    }

    private void tradesEditOneItemSellInputGroup(UpdateInfo updateInfo) {
        InputState inputState = updateInfo.getInputState();

        switch (inputState) {
            case TRADES_EDIT_ONE_ITEM_ITEM_ID -> executorsService.execute(TradesOneItemSellEditStage2AskBoundaryPriceInput.class, updateInfo);

            case TRADES_EDIT_ONE_ITEM_BOUNDARY_SELL_PRICE ->
                    executorsService.execute(TradesOneItemSellEditStage3AskStartingPriceInput.class, updateInfo);

            case TRADES_EDIT_ONE_ITEM_STARTING_SELL_PRICE -> executorsService.execute(TradesOneItemSellEditStage4AskPriorityInput.class, updateInfo);

            case TRADES_EDIT_ONE_ITEM_PRIORITY -> executorsService.execute(TradesOneItemSellEditStage5AskConfirmationInput.class, updateInfo);

            default ->
                    throw new InvalidUserInputStateAndGroupConjunctionException(updateInfo.getInputState().name() + " - state:group - " + updateInfo.getInputGroup().name());
        }
    }

    private void tradesEditOneItemBuyAndSellInputGroup(UpdateInfo updateInfo) {
        InputState inputState = updateInfo.getInputState();

        switch (inputState) {
            case TRADES_EDIT_ONE_ITEM_ITEM_ID ->
                    executorsService.execute(TradesOneItemBuyAndSellEditStage2AskBoundarySellPriceInput.class, updateInfo);

            case TRADES_EDIT_ONE_ITEM_BOUNDARY_SELL_PRICE ->
                    executorsService.execute(TradesOneItemBuyAndSellEditStage3AskStartingSellPriceInput.class, updateInfo);

            case TRADES_EDIT_ONE_ITEM_STARTING_SELL_PRICE ->
                    executorsService.execute(TradesOneItemBuyAndSellEditStage4AskBoundaryBuyPriceInput.class, updateInfo);

            case TRADES_EDIT_ONE_ITEM_STARTING_BUY_PRICE ->
                    executorsService.execute(TradesOneItemBuyAndSellEditStage5AskStartingBuyPriceInput.class, updateInfo);

            case TRADES_EDIT_ONE_ITEM_BOUNDARY_BUY_PRICE ->
                    executorsService.execute(TradesOneItemBuyAndSellEditStage6AskPriorityInput.class, updateInfo);

            case TRADES_EDIT_ONE_ITEM_PRIORITY -> executorsService.execute(TradesOneItemBuyAndSellEditStage7AskConfirmationInput.class, updateInfo);

            default ->
                    throw new InvalidUserInputStateAndGroupConjunctionException(updateInfo.getInputState().name() + " - state:group - " + updateInfo.getInputGroup().name());
        }
    }

    private void tradesOneItemRemoveInputGroup(UpdateInfo updateInfo) {
        InputState inputState = updateInfo.getInputState();

        switch (inputState) {
            case TRADES_EDIT_ONE_ITEM_ITEM_ID -> executorsService.execute(TradeManagersByItemIdRemoveStage2AskConfirmationInput.class, updateInfo);

            default ->
                    throw new InvalidUserInputStateAndGroupConjunctionException(updateInfo.getInputState().name() + " - state:group - " + updateInfo.getInputGroup().name());
        }
    }
}
