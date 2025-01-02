package github.ricemonger.telegramBot.executors.tradeManagers.show.remove_or_change_enabled.itemFilters;

import github.ricemonger.telegramBot.executors.MockUpdateInfos;
import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.show.remove_or_change_enabled.itemFilters.TradeByFiltersManagerRemoveStage2AskConfirmationFinishInput;
import github.ricemonger.utils.DTOs.personal.TradeByFiltersManager;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TradeByFiltersManagerRemoveStage2AskConfirmationFinishInputTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_process_last_input_and_ask_confirmation() {
        TradeByFiltersManager manager = new TradeByFiltersManager();
        manager.setEnabled(true);

        when(botInnerService.getUserTradeByFiltersManagerByUserInputName(MockUpdateInfos.UPDATE_INFO.getChatId())).thenReturn(manager);

        TradeByFiltersManagerRemoveStage2AskConfirmationFinishInput commandExecutor = new TradeByFiltersManagerRemoveStage2AskConfirmationFinishInput();
        commandExecutor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).getUserTradeByFiltersManagerByUserInputName(MockUpdateInfos.UPDATE_INFO.getChatId());

        verify(botInnerService).saveUserInput(MockUpdateInfos.UPDATE_INFO);
        verify(botInnerService).setUserInputState(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.BASE);
        verify(botInnerService).setUserInputGroup(MockUpdateInfos.UPDATE_INFO.getChatId(), InputGroup.BASE);

        verify(botInnerService).askFromInlineKeyboard(eq(MockUpdateInfos.UPDATE_INFO), anyString(), anyInt(), any());
    }
}