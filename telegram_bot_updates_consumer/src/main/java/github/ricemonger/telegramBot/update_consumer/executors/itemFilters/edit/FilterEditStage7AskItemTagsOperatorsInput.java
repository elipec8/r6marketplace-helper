package github.ricemonger.telegramBot.update_consumer.executors.itemFilters.edit;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.InputState;
import github.ricemonger.utils.enums.TagGroup;

public class FilterEditStage7AskItemTagsOperatorsInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.ITEM_FILTER_ITEM_TAGS_OPERATORS);
        String tags = botInnerService.getStringOfAllTagsNamesByTagGroup(TagGroup.Operator);
        askFromInlineKeyboardOrSkip("Please enter operators, dived by \",\" or \"|\". Operators list:\n" + tags, 1);
    }
}
