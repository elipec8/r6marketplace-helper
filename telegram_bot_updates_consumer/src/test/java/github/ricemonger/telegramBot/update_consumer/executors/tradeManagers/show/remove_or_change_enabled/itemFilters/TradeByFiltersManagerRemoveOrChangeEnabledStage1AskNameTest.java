package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.show.remove_or_change_enabled.itemFilters;

import github.ricemonger.telegramBot.update_consumer.executors.MockUpdateInfos;
import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@SpringBootTest
class TradeByFiltersManagerRemoveOrChangeEnabledStage1AskNameTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_process_first_input_with_text() {
        TradeByFiltersManagerRemoveOrChangeEnabledStage1AskNameCallback commandExecutor = new TradeByFiltersManagerRemoveOrChangeEnabledStage1AskNameCallback();
        commandExecutor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).clearUserInputsAndSetInputStateAndGroup(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.TRADE_BY_FILTERS_MANAGER_NAME, InputGroup.TRADE_BY_FILTERS_MANAGER_SHOW_OR_REMOVE);

        verify(botInnerService).sendText(eq(MockUpdateInfos.UPDATE_INFO), anyString());
    }
}