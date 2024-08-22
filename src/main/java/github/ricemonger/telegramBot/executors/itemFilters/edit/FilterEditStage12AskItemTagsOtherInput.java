package github.ricemonger.telegramBot.executors.itemFilters.edit;

import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.TagGroup;

public class FilterEditStage12AskItemTagsOtherInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.ITEM_FILTER_ITEM_TAGS_OTHER);
        String tags = botInnerService.getStringOfAllTagsNamesByTagGroup(TagGroup.Other);
        askFromInlineKeyboardOrSkip("Please enter tags, dived by \",\" or \"|\". Tags' list:\n" + tags, 1);
    }
}
