package github.ricemonger.telegramBot.executors.marketplace.filters.edit;

import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.TagGroup;

public class FilterEditStage9AskItemTagsWeaponsInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.FILTER_ITEM_TAGS_WEAPONS);
        String tags = botInnerService.getStringOfAllTagsNamesByTagGroup(TagGroup.Weapon);
        askFromInlineKeyboardOrSkip("Please enter weapons, dived by \",\" or \"|\". Weapons list:\n" + tags, 1);
    }
}
