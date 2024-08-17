package github.ricemonger.telegramBot.executors.itemFilters.edit;

import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.TagGroup;

public class FilterEditStage11AskItemTagsEsportsInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.FILTER_ITEM_TAGS_ESPORTS);
        String tags = botInnerService.getStringOfAllTagsNamesByTagGroup(TagGroup.Esports_Team);
        askFromInlineKeyboardOrSkip("Please enter esports teams tags, dived by \",\" or \"|\". Teams list:\n" + tags, 1);
    }
}
