package github.ricemonger.telegramBot.executors.marketplace.filters.create;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.telegramBot.executors.InputState;
import github.ricemonger.utils.enums.TagGroup;

public class FilterCreateStage6AskItemTagsRarityInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.FILTER_ITEM_TAGS_RARITY);
        String tags = botInnerService.getTagsStringByGroup(TagGroup.Rarity);
        askFromInlineKeyboardOrSkip("Please enter rarities, dived by \",\" or \"|\". Rarities list:\n" + tags,1);
    }
}
