package github.ricemonger.telegramBot.update_consumer.executors.items.settings.appliedFilters;

import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.telegramBot.update_consumer.executors.MockUpdateInfos;
import github.ricemonger.utils.DTOs.personal.ItemFilter;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ItemsShowSettingsChangeAppliedFiltersStage3AddAppliedFilterCallbackTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_addItemShowAppliedFilterByUserInput() {
        ItemsShowSettingsChangeAppliedFiltersStage3AddAppliedFilterCallback commandExecutor = new ItemsShowSettingsChangeAppliedFiltersStage3AddAppliedFilterCallback();
        commandExecutor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).addItemShowAppliedFilterByUserInput(MockUpdateInfos.UPDATE_INFO.getChatId());
        verify(botInnerService).sendText(eq(MockUpdateInfos.UPDATE_INFO), anyString());
    }
}