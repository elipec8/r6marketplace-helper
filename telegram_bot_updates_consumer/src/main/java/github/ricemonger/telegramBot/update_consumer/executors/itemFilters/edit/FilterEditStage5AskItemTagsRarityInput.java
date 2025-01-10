package github.ricemonger.telegramBot.update_consumer.executors.itemFilters.edit;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.InputState;
import github.ricemonger.utils.enums.TagGroup;

public class FilterEditStage5AskItemTagsRarityInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.ITEM_FILTER_ITEM_TAGS_RARITY);
        String tags = botInnerService.getStringOfAllTagsNamesByTagGroup(TagGroup.Rarity);
        askFromInlineKeyboardOrSkip("Please enter rarities, dived by \",\" or \"|\". Rarities list:\n" + tags, 1);
    }
}
