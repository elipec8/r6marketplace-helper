package github.ricemonger.telegramBot.executors.marketplace.filters.create;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.telegramBot.executors.InputState;
import github.ricemonger.utils.enums.TagGroup;

public class FilterCreateStage11AskItemTagsEsportsInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.FILTER_ITEM_TAGS_ESPORTS);
        String tags = botInnerService.getTagsStringByGroup(TagGroup.Esports_Team);
        askFromInlineKeyboardOrSkip("Please enter esports teams tags, dived by \",\" or \"|\". Teams list:\n" + tags,1);
    }
}
