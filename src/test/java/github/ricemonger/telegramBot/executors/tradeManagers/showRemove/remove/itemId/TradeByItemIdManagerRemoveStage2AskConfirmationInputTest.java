package github.ricemonger.telegramBot.executors.tradeManagers.showRemove.remove.itemId;

import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.client.BotInnerService;
import github.ricemonger.telegramBot.executors.MockUpdateInfos;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TradeByItemIdManagerRemoveStage2AskConfirmationInputTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_process_last_input_and_show_manager_ask_if_remove() {
        when(botInnerService.getAllUserTradeByItemIdManagers(any())).thenReturn(new ArrayList<>());

        TradeByItemIdManagerRemoveStage2AskConfirmationInput commandExecutor = new TradeByItemIdManagerRemoveStage2AskConfirmationInput();
        commandExecutor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).saveUserInput(MockUpdateInfos.UPDATE_INFO);
        verify(botInnerService).setUserInputState(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.BASE);
        verify(botInnerService).setUserInputGroup(MockUpdateInfos.UPDATE_INFO.getChatId(), InputGroup.BASE);

        verify(botInnerService).getUserTradeByItemIdManagerByUserInputItemId(MockUpdateInfos.UPDATE_INFO.getChatId());
    }
}