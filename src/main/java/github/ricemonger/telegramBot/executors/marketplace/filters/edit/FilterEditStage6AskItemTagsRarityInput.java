package github.ricemonger.telegramBot.executors.marketplace.filters.edit;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.telegramBot.executors.InputState;
import github.ricemonger.utils.enums.TagGroup;

public class FilterEditStage6AskItemTagsRarityInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.FILTER_ITEM_TAGS_RARITY);
        String tags = botInnerService.getAllTagsStringByGroup(TagGroup.Rarity);
        askFromInlineKeyboardOrSkip("Please enter rarities, dived by \",\" or \"|\". Rarities list:\n" + tags,1);
    }
}
