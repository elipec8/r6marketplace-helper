package github.ricemonger.telegramBot.update_consumer.executors.itemFilters.showOrRemove;

import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.telegramBot.update_consumer.executors.MockUpdateInfos;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@SpringBootTest
class FilterRemoveStage3ConfirmedFinishCallbackTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_remove_item_filter_by_inputted_name_and_notify_user() {
        FilterRemoveStage3ConfirmedFinishCallback filterRemoveStage3ConfirmedFinishCallback = new FilterRemoveStage3ConfirmedFinishCallback();
        filterRemoveStage3ConfirmedFinishCallback.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).removeUserItemFilterByUserInputCallbackFilterName(MockUpdateInfos.UPDATE_INFO.getChatId());

        verify(botInnerService).sendText(eq(MockUpdateInfos.UPDATE_INFO), anyString());
    }
}