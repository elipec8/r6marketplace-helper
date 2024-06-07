package github.ricemonger.telegramBot.executors.marketplace.filters.create;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.telegramBot.executors.InputState;
import github.ricemonger.utils.enums.TagGroup;

public class FilterCreateStage9AskItemTagsWeaponsInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.FILTER_ITEM_TAGS_WEAPONS);
        String tags = botInnerService.getTagsStringByGroup(TagGroup.Weapon);
        askFromInlineKeyboardOrSkip("Please enter weapons, dived by \",\" or \"|\". Weapons list:\n" + tags,1);
    }
}
