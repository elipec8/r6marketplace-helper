package github.ricemonger.telegramBot.executors.marketplace.speculative.show;

import github.ricemonger.telegramBot.client.BotInnerService;
import github.ricemonger.telegramBot.executors.MockUpdateInfos;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class SpeculativeItemsShowAllCallbackTests {

    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecuteShouldSendDefaultSpeculativeItemsAsMessages() {
        SpeculativeItemsShowAllCallback speculativeItemsShowAllCallback = new SpeculativeItemsShowAllCallback();

        speculativeItemsShowAllCallback.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService, times(1)).sendDefaultSpeculativeItemsAsMessages(MockUpdateInfos.UPDATE_INFO.getChatId());
    }
}