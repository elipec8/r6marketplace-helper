package github.ricemonger.telegramBot.executors.items.settings.appliedFilters;

import github.ricemonger.telegramBot.executors.MockUpdateInfos;
import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.telegramBot.update_consumer.executors.items.settings.appliedFilters.ItemsShowSettingsChangeAppliedFiltersStage2AskActionInput;
import github.ricemonger.utils.DTOs.personal.ItemFilter;
import github.ricemonger.marketplace.services.DTOs.ItemShowSettings;
import github.ricemonger.utils.enums.InputState;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ItemsShowSettingsChangeAppliedFiltersStage2AskActionInputTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_process_middle_input_with_keyboard_and_get_current_filter_and_get_user_applied_filters() {
        when(botInnerService.getUserItemShowSettings(any())).thenReturn(new ItemShowSettings());
        when(botInnerService.getUserItemFilterByUserCurrentInputCallbackFilterName(any())).thenReturn(new ItemFilter());

        ItemsShowSettingsChangeAppliedFiltersStage2AskActionInput commandExecutor = new ItemsShowSettingsChangeAppliedFiltersStage2AskActionInput();
        commandExecutor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).saveUserInput(MockUpdateInfos.UPDATE_INFO);
        verify(botInnerService).setUserInputState(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.ITEMS_SHOW_SETTINGS_APPLIED_FILTER_ADD_OR_REMOVE);

        verify(botInnerService).getUserItemFilterByUserCurrentInputCallbackFilterName(MockUpdateInfos.UPDATE_INFO.getChatId());

        verify(botInnerService).getUserItemShowSettings(MockUpdateInfos.UPDATE_INFO.getChatId());

        verify(botInnerService).askFromInlineKeyboard(eq(MockUpdateInfos.UPDATE_INFO), anyString(), anyInt(), any());
    }
}