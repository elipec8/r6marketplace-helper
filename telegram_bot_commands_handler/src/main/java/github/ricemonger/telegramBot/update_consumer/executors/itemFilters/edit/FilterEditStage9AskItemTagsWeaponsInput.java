package github.ricemonger.telegramBot.update_consumer.executors.itemFilters.edit;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.InputState;
import github.ricemonger.utils.enums.TagGroup;

public class FilterEditStage9AskItemTagsWeaponsInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.ITEM_FILTER_ITEM_TAGS_WEAPONS);
        String tags = botInnerService.getStringOfAllTagsNamesByTagGroup(TagGroup.Weapon);
        askFromInlineKeyboardOrSkip("Please enter weapons, dived by \",\" or \"|\". Weapons list:\n" + tags, 1);
    }
}
