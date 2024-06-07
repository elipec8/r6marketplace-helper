package github.ricemonger.telegramBot.executors.marketplace.filters.create;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.telegramBot.executors.InputState;
import github.ricemonger.utils.enums.TagGroup;

public class FilterCreateStage12AskItemTagsOtherInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.FILTER_ITEM_TAGS_OTHER);
        String tags = botInnerService.getTagsStringByGroup(TagGroup.Other);
        askFromInlineKeyboardOrSkip("Please enter tags, dived by \",\" or \"|\". Tags' list:\n" + tags,1);
    }
}
