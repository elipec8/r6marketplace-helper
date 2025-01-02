package github.ricemonger.telegramBot.update_consumer.executors.itemFilters.edit;

import github.ricemonger.telegramBot.update_consumer.executors.MockUpdateInfos;
import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.utils.enums.InputState;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class FilterEditStage3AskIsOwnedInputTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_process_middle_input_with_button() {
        FilterEditStage3AskIsOwnedInput filterEditStage3AskIsOwnedInput = new FilterEditStage3AskIsOwnedInput();
        filterEditStage3AskIsOwnedInput.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService, times(0)).sendText(any(), anyString());

        verify(botInnerService).saveUserInput(MockUpdateInfos.UPDATE_INFO);
        verify(botInnerService).setUserInputState(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.ITEM_FILTER_IS_OWNED);

        verify(botInnerService).askFromInlineKeyboard(eq(MockUpdateInfos.UPDATE_INFO), anyString(), anyInt(), any());
    }
}