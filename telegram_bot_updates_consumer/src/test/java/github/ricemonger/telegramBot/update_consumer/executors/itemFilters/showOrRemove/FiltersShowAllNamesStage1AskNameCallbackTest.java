package github.ricemonger.telegramBot.update_consumer.executors.itemFilters.showOrRemove;

import github.ricemonger.telegramBot.update_consumer.executors.MockUpdateInfos;
import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class FiltersShowAllNamesStage1AskNameCallbackTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_process_first_input_and_get_all_user_filters_names_and_ask_filter_to_show_if_filters_exist() {
        when(botInnerService.getAllUserItemFiltersNames(MockUpdateInfos.UPDATE_INFO.getChatId())).thenReturn(List.of("naame", "name"));

        FiltersShowAllNamesStage1AskNameCallback filtersShowAllNamesStage1AskNameCallback = new FiltersShowAllNamesStage1AskNameCallback();
        filtersShowAllNamesStage1AskNameCallback.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).clearUserInputsAndSetInputStateAndGroup(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.ITEM_FILTER_NAME, InputGroup.ITEM_FILTER_SHOW_OR_REMOVE);

        verify(botInnerService).getAllUserItemFiltersNames(MockUpdateInfos.UPDATE_INFO.getChatId());

        verify(botInnerService, times(0)).sendText(eq(MockUpdateInfos.UPDATE_INFO), anyString());

        verify(botInnerService).askFromInlineKeyboard(eq(MockUpdateInfos.UPDATE_INFO), anyString(), anyInt(), any());
    }

    @Test
    public void initAndExecute_should_notify_user_if_no_filter_names_to_show() {
        when(botInnerService.getAllUserItemFiltersNames(MockUpdateInfos.UPDATE_INFO.getChatId())).thenReturn(List.of());

        FiltersShowAllNamesStage1AskNameCallback filtersShowAllNamesStage1AskNameCallback = new FiltersShowAllNamesStage1AskNameCallback();
        filtersShowAllNamesStage1AskNameCallback.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService, times(0)).clearUserInputsAndSetInputStateAndGroup(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.ITEM_FILTER_NAME, InputGroup.ITEM_FILTER_SHOW_OR_REMOVE);

        verify(botInnerService).getAllUserItemFiltersNames(MockUpdateInfos.UPDATE_INFO.getChatId());

        verify(botInnerService).sendText(eq(MockUpdateInfos.UPDATE_INFO), anyString());

        verify(botInnerService, times(0)).askFromInlineKeyboard(eq(MockUpdateInfos.UPDATE_INFO), anyString(), anyInt(), any());
    }
}