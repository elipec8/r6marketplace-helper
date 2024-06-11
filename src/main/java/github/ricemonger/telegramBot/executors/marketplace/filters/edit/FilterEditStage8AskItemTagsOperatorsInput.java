package github.ricemonger.telegramBot.executors.marketplace.filters.edit;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.telegramBot.executors.InputState;
import github.ricemonger.utils.enums.TagGroup;

public class FilterEditStage8AskItemTagsOperatorsInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.FILTER_ITEM_TAGS_OPERATORS);
        String tags = botInnerService.getAllTagsStringByGroup(TagGroup.Operator);
        askFromInlineKeyboardOrSkip("Please enter operators, dived by \",\" or \"|\". Operators list:\n" + tags,1);
    }
}
