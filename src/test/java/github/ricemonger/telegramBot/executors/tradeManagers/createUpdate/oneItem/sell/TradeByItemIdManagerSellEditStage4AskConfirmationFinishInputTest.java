package github.ricemonger.telegramBot.executors.tradeManagers.createUpdate.oneItem.sell;

import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.client.BotInnerService;
import github.ricemonger.telegramBot.executors.MockUpdateInfos;
import github.ricemonger.telegramBot.executors.tradeManagers.edit.oneItem.sell.TradeByItemIdManagerSellEditStage4AskConfirmationFinishInput;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
class TradeByItemIdManagerSellEditStage4AskConfirmationFinishInputTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_process_last_input_and_ask_confirmation() {
        TradeByItemIdManagerSellEditStage4AskConfirmationFinishInput commandExecutor = new TradeByItemIdManagerSellEditStage4AskConfirmationFinishInput();
        commandExecutor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).saveUserInput(MockUpdateInfos.UPDATE_INFO);
        verify(botInnerService).setUserInputState(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.BASE);
        verify(botInnerService).setUserInputGroup(MockUpdateInfos.UPDATE_INFO.getChatId(), InputGroup.BASE);

        verify(botInnerService).askFromInlineKeyboard(eq(MockUpdateInfos.UPDATE_INFO), anyString(), anyInt(), any());
    }
}