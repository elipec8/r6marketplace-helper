package github.ricemonger.telegramBot.executors.marketplace.filters.edit;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.telegramBot.executors.InputState;
import github.ricemonger.utils.enums.TagGroup;

public class FilterEditStage12AskItemTagsOtherInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.FILTER_ITEM_TAGS_OTHER);
        String tags = botInnerService.getAllTagsStringByGroup(TagGroup.Other);
        askFromInlineKeyboardOrSkip("Please enter tags, dived by \",\" or \"|\". Tags' list:\n" + tags,1);
    }
}
