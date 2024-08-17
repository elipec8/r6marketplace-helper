package github.ricemonger.telegramBot.executors.itemFilters.edit;

import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.TagGroup;

public class FilterEditStage10AskItemTagsEventsInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.FILTER_ITEM_TAGS_EVENTS);
        String tags = botInnerService.getStringOfAllTagsNamesByTagGroup(TagGroup.Event);
        askFromInlineKeyboardOrSkip("Please enter events, dived by \",\" or \"|\". Events list:\n" + tags, 1);
    }
}
