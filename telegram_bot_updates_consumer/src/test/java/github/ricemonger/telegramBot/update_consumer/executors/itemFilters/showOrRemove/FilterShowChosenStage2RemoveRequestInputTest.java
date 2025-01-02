package github.ricemonger.telegramBot.update_consumer.executors.itemFilters.showOrRemove;

import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.telegramBot.update_consumer.executors.MockUpdateInfos;
import github.ricemonger.utils.DTOs.personal.ItemFilter;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class FilterShowChosenStage2RemoveRequestInputTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_process_last_input_and_show_filter_and_ask_from_inline_keyboard_if_item_filter_should_be_removed() {
        when(botInnerService.getUserItemFilterByUserCurrentInputCallbackFilterName(MockUpdateInfos.UPDATE_INFO)).thenReturn(new ItemFilter());

        FilterShowChosenStage2RemoveRequestInput filterShowChosenStage2RemoveRequestInput = new FilterShowChosenStage2RemoveRequestInput();
        filterShowChosenStage2RemoveRequestInput.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).saveUserInput(MockUpdateInfos.UPDATE_INFO);
        verify(botInnerService).setUserInputStateAndGroup(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.BASE, InputGroup.BASE);

        verify(botInnerService).getUserItemFilterByUserCurrentInputCallbackFilterName(MockUpdateInfos.UPDATE_INFO);

        verify(botInnerService).askFromInlineKeyboard(eq(MockUpdateInfos.UPDATE_INFO), anyString(), anyInt(), any());
    }
}