package github.ricemonger.telegramBot.executors.itemFilters.edit;

import github.ricemonger.telegramBot.executors.MockUpdateInfos;
import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.telegramBot.update_consumer.executors.itemFilters.edit.FilterEditStage4AskItemNamePatternsInput;
import github.ricemonger.utils.enums.InputState;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class FilterEditStage4AskItemNamePatternsInputTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_process_middle_input_with_skip_button() {
        FilterEditStage4AskItemNamePatternsInput filterEditStage4AskItemNamePatternsInput = new FilterEditStage4AskItemNamePatternsInput();
        filterEditStage4AskItemNamePatternsInput.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService, times(0)).sendText(any(), anyString());

        verify(botInnerService).saveUserInput(MockUpdateInfos.UPDATE_INFO);
        verify(botInnerService).setUserInputState(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.ITEM_FILTER_ITEM_NAME_PATTERNS);

        verify(botInnerService).askFromInlineKeyboard(
                eq(MockUpdateInfos.UPDATE_INFO),
                anyString(),
                eq(1),
                eq(MockUpdateInfos.SKIP_CALLBACK_BUTTON_SINGLE_ARRAY));
    }
}