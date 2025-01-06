package github.ricemonger.telegramBot.update_consumer.executors.cancel;

import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.telegramBot.update_consumer.executors.MockUpdateInfos;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;

@SpringBootTest
class CancelTest {

    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_reset_inputs_and_send_text() {
        Cancel cancel = new Cancel();
        cancel.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).clearUserInputsAndSetInputStateAndGroup(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.BASE, InputGroup.BASE);

        verify(botInnerService).sendText(same(MockUpdateInfos.UPDATE_INFO), anyString());
    }

}