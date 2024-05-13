package github.ricemonger.telegramBot.executors.marketplace.speculative.show;

import github.ricemonger.marketplace.databases.neo4j.entities.ItemEntity;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

import java.util.List;

public class SpeculativeItemsShowAllCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        sendText(botInnerService.getDefaultSpeculativeItemsText());
    }
}
