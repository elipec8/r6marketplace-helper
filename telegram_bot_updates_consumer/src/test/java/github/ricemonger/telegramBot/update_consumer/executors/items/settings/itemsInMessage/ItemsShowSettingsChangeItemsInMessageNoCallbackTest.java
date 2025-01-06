package github.ricemonger.telegramBot.update_consumer.executors.items.settings.itemsInMessage;

import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.telegramBot.update_consumer.executors.MockUpdateInfos;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ItemsShowSettingsChangeItemsInMessageNoCallbackTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_set_false_flag() {
        ItemsShowSettingsChangeItemsInMessageNoCallback commandExecutor = new ItemsShowSettingsChangeItemsInMessageNoCallback();
        commandExecutor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).setUserItemShowSettingsFewItemsInMessageFlag(eq(MockUpdateInfos.UPDATE_INFO.getChatId()), eq(false));
    }
}