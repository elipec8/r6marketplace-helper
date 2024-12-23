package github.ricemonger.telegramBot.executors.itemFilters.edit;

import github.ricemonger.telegramBot.client.BotInnerService;
import github.ricemonger.telegramBot.executors.MockUpdateInfos;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@SpringBootTest
class FilterEditStage16FinishConfirmedFinishCallbackTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_save_item_filter_by_user_inputs_and_notify_user() {
        FilterEditStage16FinishConfirmedFinishCallback filterEditStage16FinishConfirmedFinishCallback = new FilterEditStage16FinishConfirmedFinishCallback();
        filterEditStage16FinishConfirmedFinishCallback.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).saveUserItemFilterByUserInput(MockUpdateInfos.UPDATE_INFO.getChatId());

        verify(botInnerService).sendText(eq(MockUpdateInfos.UPDATE_INFO), anyString());
    }
}