package github.ricemonger.telegramBot.executors.marketplace.filters.create;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.telegramBot.executors.InputState;
import github.ricemonger.utils.enums.TagGroup;

public class FilterCreateStage10AskItemTagsEventsInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.FILTER_ITEM_TAGS_EVENTS);
        String tags = botInnerService.getTagsStringByGroup(TagGroup.Event);
        askFromInlineKeyboardOrSkip("Please enter events, dived by \",\" or \"|\". Events list:\n" + tags,1);
    }
}
