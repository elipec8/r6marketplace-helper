package github.ricemonger.telegramBot.executors.marketplace.speculative.show;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.telegramBot.executors.InputState;

public class SpeculativeItemsShowOwnedFinishInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {

        processLastInput(updateInfo, "Email was provided, getting items...");

        String email = botInnerService.getUserInputByState(updateInfo.getChatId(), InputState.CREDENTIALS_FULL_OR_EMAIL);

        botInnerService.clearUserInputs(updateInfo.getChatId());

        botInnerService.sendOwnedSpeculativeItemsAsMessages(updateInfo.getChatId(), email);
    }
}
