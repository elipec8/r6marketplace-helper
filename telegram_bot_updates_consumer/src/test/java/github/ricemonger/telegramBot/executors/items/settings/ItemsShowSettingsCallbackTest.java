package github.ricemonger.telegramBot.executors.items.settings;

import github.ricemonger.marketplace.services.DTOs.ItemShowSettings;
import github.ricemonger.telegramBot.executors.MockUpdateInfos;
import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.telegramBot.update_consumer.executors.items.settings.ItemsShowSettingsCallback;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ItemsShowSettingsCallbackTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_show_user_item_show_settings_and_ask_if_change() {
        when(botInnerService.getUserItemShowSettings(MockUpdateInfos.UPDATE_INFO.getChatId())).thenReturn(new ItemShowSettings());

        ItemsShowSettingsCallback commandExecutor = new ItemsShowSettingsCallback();
        commandExecutor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).getUserItemShowSettings(MockUpdateInfos.UPDATE_INFO.getChatId());

        verify(botInnerService).askFromInlineKeyboard(eq(MockUpdateInfos.UPDATE_INFO), anyString(), anyInt(), any());
    }
}