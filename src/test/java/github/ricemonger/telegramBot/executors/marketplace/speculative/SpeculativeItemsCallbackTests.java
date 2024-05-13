package github.ricemonger.telegramBot.executors.marketplace.speculative;

import github.ricemonger.telegramBot.client.BotInnerService;
import github.ricemonger.telegramBot.executors.MockUpdateInfos;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
class SpeculativeItemsCallbackTests {

    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecuteShouldAskFromKeyboardForAction(){
        SpeculativeItemsCallback speculativeItemsCallback = new SpeculativeItemsCallback();

        speculativeItemsCallback.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).askFromInlineKeyboard(same(MockUpdateInfos.UPDATE_INFO),anyString(),anyInt(),any());
    }
}