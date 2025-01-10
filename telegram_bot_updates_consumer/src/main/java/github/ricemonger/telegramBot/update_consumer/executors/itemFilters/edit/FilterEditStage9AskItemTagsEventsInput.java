package github.ricemonger.telegramBot.update_consumer.executors.itemFilters.edit;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.InputState;
import github.ricemonger.utils.enums.TagGroup;

public class FilterEditStage9AskItemTagsEventsInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.ITEM_FILTER_ITEM_TAGS_EVENTS);
        String tags = botInnerService.getStringOfAllTagsNamesByTagGroup(TagGroup.Event);
        askFromInlineKeyboardOrSkip("Please enter events, dived by \",\" or \"|\". Events list:\n" + tags, 1);
    }
}
