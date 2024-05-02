package github.ricemonger.telegramBot.client.executors.cancel;

import github.ricemonger.telegramBot.client.BotService;
import github.ricemonger.telegramBot.client.executors.InputGroup;
import github.ricemonger.telegramBot.client.executors.InputState;
import github.ricemonger.telegramBot.client.executors.MockUpdateInfos;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;

@SpringBootTest
class CancelTests {

    @MockBean
    private BotService botService;

    @Test
    public void initAndExecuteShould() {
        Cancel cancel = new Cancel();
        cancel.initAndExecute(MockUpdateInfos.UPDATE_INFO, botService);

        verify(botService).setUserNextInputState(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.BASE);
        verify(botService).setUserNextInputGroup(MockUpdateInfos.UPDATE_INFO.getChatId(), InputGroup.BASE);
        verify(botService).clearUserInputs(MockUpdateInfos.UPDATE_INFO.getChatId());

        verify(botService).sendText(same(MockUpdateInfos.UPDATE_INFO), anyString());
    }

}