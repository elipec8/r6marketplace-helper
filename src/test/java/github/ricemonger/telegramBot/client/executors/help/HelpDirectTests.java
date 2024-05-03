package github.ricemonger.telegramBot.client.executors.help;

import github.ricemonger.telegramBot.client.BotInnerService;
import github.ricemonger.telegramBot.client.PublicBotCommands;
import github.ricemonger.telegramBot.client.executors.MockUpdateInfos;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@SpringBootTest
class HelpDirectTests {

    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecuteShouldSendHelpText() {
        HelpDirect helpDirect = new HelpDirect();
        helpDirect.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).sendText(any(), eq(PublicBotCommands.getHelpText()));
    }

}