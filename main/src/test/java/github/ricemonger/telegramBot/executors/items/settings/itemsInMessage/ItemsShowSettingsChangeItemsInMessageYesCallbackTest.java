package github.ricemonger.telegramBot.executors.items.settings.itemsInMessage;

import github.ricemonger.telegramBot.executors.MockUpdateInfos;
import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.telegramBot.update_consumer.executors.items.settings.itemsInMessage.ItemsShowSettingsChangeItemsInMessageYesCallback;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ItemsShowSettingsChangeItemsInMessageYesCallbackTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_set_true_flag() {
        ItemsShowSettingsChangeItemsInMessageYesCallback commandExecutor = new ItemsShowSettingsChangeItemsInMessageYesCallback();
        commandExecutor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).setUserItemShowSettingsFewItemsInMessageFlag(eq(MockUpdateInfos.UPDATE_INFO.getChatId()), eq(true));
    }
}