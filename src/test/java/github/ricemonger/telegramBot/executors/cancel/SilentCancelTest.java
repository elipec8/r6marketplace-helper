package github.ricemonger.telegramBot.executors.cancel;

import github.ricemonger.telegramBot.client.BotInnerService;
import github.ricemonger.telegramBot.executors.InputGroup;
import github.ricemonger.telegramBot.executors.InputState;
import github.ricemonger.telegramBot.executors.MockUpdateInfos;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest
class SilentCancelTest {

    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecuteShould() {
        SilentCancel cancel = new SilentCancel();
        cancel.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).setUserNextInputState(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.BASE);
        verify(botInnerService).setUserNextInputGroup(MockUpdateInfos.UPDATE_INFO.getChatId(), InputGroup.BASE);
        verify(botInnerService).clearUserInputs(MockUpdateInfos.UPDATE_INFO.getChatId());

        verify(botInnerService, never()).sendText(same(MockUpdateInfos.UPDATE_INFO), anyString());
    }

}