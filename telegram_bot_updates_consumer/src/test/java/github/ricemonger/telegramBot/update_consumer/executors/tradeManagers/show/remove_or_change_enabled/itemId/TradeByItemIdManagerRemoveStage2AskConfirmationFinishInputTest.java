package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.show.remove_or_change_enabled.itemId;

import github.ricemonger.telegramBot.update_consumer.executors.MockUpdateInfos;
import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.utils.DTOs.personal.TradeByItemIdManager;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TradeByItemIdManagerRemoveStage2AskConfirmationFinishInputTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_process_last_input_and_show_manager_ask_if_remove() {
        TradeByItemIdManager manager = new TradeByItemIdManager();
        manager.setEnabled(true);

        when(botInnerService.getUserTradeByItemIdManagerByUserInputItemId(MockUpdateInfos.UPDATE_INFO.getChatId())).thenReturn(manager);

        TradeByItemIdManagerRemoveStage2AskConfirmationFinishInput commandExecutor = new TradeByItemIdManagerRemoveStage2AskConfirmationFinishInput();
        commandExecutor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).getUserTradeByItemIdManagerByUserInputItemId(MockUpdateInfos.UPDATE_INFO.getChatId());

        verify(botInnerService).saveUserInput(MockUpdateInfos.UPDATE_INFO);
        verify(botInnerService).setUserInputStateAndGroup(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.BASE, InputGroup.BASE);

        verify(botInnerService).getUserTradeByItemIdManagerByUserInputItemId(MockUpdateInfos.UPDATE_INFO.getChatId());
    }
}