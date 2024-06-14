package github.ricemonger.telegramBot.executors.marketplace.filters.edit;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.telegramBot.executors.InputState;
import github.ricemonger.utils.enums.TagGroup;

public class FilterEditStage7AskItemTagsSeasonsInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.FILTER_ITEM_TAGS_SEASONS);
        String tags = botInnerService.getAllTagsStringByGroup(TagGroup.Season);
        askFromInlineKeyboardOrSkip("Please enter seasons, dived by \",\" or \"|\". Seasons list:\n" + tags,1);
    }
}
