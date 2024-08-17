package github.ricemonger.telegramBot.executors.credentials.show;

import github.ricemonger.telegramBot.client.BotInnerService;
import github.ricemonger.telegramBot.executors.MockUpdateInfos;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@SpringBootTest
class CredentialsShowCallbackTest {

    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecuteShouldSendListOfCredentials() {

    }
}