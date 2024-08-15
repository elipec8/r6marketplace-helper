package github.ricemonger.telegramBot.executors.marketplace.filters.edit;

import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.TagGroup;

public class FilterEditStage6AskItemTagsRarityInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.FILTER_ITEM_TAGS_RARITY);
        String tags = botInnerService.getStringOfAllTagsNamesByTagGroup(TagGroup.Rarity);
        askFromInlineKeyboardOrSkip("Please enter rarities, dived by \",\" or \"|\". Rarities list:\n" + tags, 1);
    }
}
