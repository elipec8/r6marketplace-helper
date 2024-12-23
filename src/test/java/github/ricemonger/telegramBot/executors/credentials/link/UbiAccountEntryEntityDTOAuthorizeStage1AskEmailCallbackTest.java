package github.ricemonger.telegramBot.executors.credentials.link;

import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.client.BotInnerService;
import github.ricemonger.telegramBot.executors.MockUpdateInfos;
import github.ricemonger.telegramBot.executors.ubi_account_entry.link.UbiAccountEntryAuthorizeStage1AskEmailCallback;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;

@SpringBootTest
class UbiAccountEntryEntityDTOAuthorizeStage1AskEmailCallbackTest {

    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_process_first_input() {
        UbiAccountEntryAuthorizeStage1AskEmailCallback ubiAccountEntryAuthorizeStage1AskEmailCallback = new UbiAccountEntryAuthorizeStage1AskEmailCallback();

        ubiAccountEntryAuthorizeStage1AskEmailCallback.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).clearUserInputs(MockUpdateInfos.UPDATE_INFO.getChatId());
        verify(botInnerService).setUserInputState(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.UBI_ACCOUNT_ENTRY_EMAIL);
        verify(botInnerService).setUserInputGroup(MockUpdateInfos.UPDATE_INFO.getChatId(), InputGroup.UBI_ACCOUNT_ENTRY_LINK);

        verify(botInnerService).sendText(same(MockUpdateInfos.UPDATE_INFO), anyString());
    }

}