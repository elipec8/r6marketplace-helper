package github.ricemonger.telegramBot.update_consumer.executors.ubi_account_entry.link;

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
class UbiAccountEntryAuthorizeStage1AskEmailCallbackTest {

    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_process_first_input() {
        UbiAccountEntryAuthorizeStage1AskEmailCallback ubiAccountEntryAuthorizeStage1AskEmailCallback = new UbiAccountEntryAuthorizeStage1AskEmailCallback();

        ubiAccountEntryAuthorizeStage1AskEmailCallback.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).clearUserInputsAndSetInputStateAndGroup(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.UBI_ACCOUNT_ENTRY_EMAIL, InputGroup.UBI_ACCOUNT_ENTRY_LINK);

        verify(botInnerService).sendText(same(MockUpdateInfos.UPDATE_INFO), anyString());
    }

}