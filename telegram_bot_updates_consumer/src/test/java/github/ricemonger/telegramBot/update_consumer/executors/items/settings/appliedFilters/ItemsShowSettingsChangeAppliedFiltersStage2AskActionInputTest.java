package github.ricemonger.telegramBot.update_consumer.executors.items.settings.appliedFilters;

import github.ricemonger.marketplace.services.DTOs.ItemShowSettings;
import github.ricemonger.telegramBot.update_consumer.executors.MockUpdateInfos;
import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.utils.DTOs.personal.ItemFilter;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ItemsShowSettingsChangeAppliedFiltersStage2AskActionInputTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_process_last_input_with_keyboard_and_get_current_filter_and_get_user_applied_filters() {
        when(botInnerService.getItemShowAppliedFiltersNames(any())).thenReturn(List.of());
        when(botInnerService.getUserItemFilterByUserCurrentInputCallbackFilterName(any())).thenReturn(new ItemFilter());

        ItemsShowSettingsChangeAppliedFiltersStage2AskActionInput commandExecutor = new ItemsShowSettingsChangeAppliedFiltersStage2AskActionInput();
        commandExecutor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).saveUserInput(MockUpdateInfos.UPDATE_INFO);
        verify(botInnerService).setUserInputStateAndGroup(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.BASE, InputGroup.BASE);

        verify(botInnerService).getUserItemFilterByUserCurrentInputCallbackFilterName(MockUpdateInfos.UPDATE_INFO);

        verify(botInnerService).getItemShowAppliedFiltersNames(MockUpdateInfos.UPDATE_INFO.getChatId());

        verify(botInnerService).askFromInlineKeyboard(eq(MockUpdateInfos.UPDATE_INFO), anyString(), anyInt(), any());
    }
}