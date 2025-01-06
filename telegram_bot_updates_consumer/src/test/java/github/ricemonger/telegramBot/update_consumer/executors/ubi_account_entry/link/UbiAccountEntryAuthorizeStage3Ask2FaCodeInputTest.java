package github.ricemonger.telegramBot.update_consumer.executors.ubi_account_entry.link;

import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.telegramBot.update_consumer.executors.MockUpdateInfos;
import github.ricemonger.utils.enums.InputState;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@SpringBootTest
class UbiAccountEntryAuthorizeStage3Ask2FaCodeInputTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_process_middle_input() {
        UbiAccountEntryAuthorizeStage3Ask2FaCodeInput ubiAccountEntryAuthorizeStage3Ask2FaCodeInput = new UbiAccountEntryAuthorizeStage3Ask2FaCodeInput();
        ubiAccountEntryAuthorizeStage3Ask2FaCodeInput.initAndExecute(MockUpdateInfos.UPDATE_INFO_PASSWORD_INPUT, botInnerService);

        verify(botInnerService).sendText(eq(MockUpdateInfos.UPDATE_INFO_PASSWORD_INPUT), anyString());
        verify(botInnerService).saveUserInput(MockUpdateInfos.UPDATE_INFO_PASSWORD_INPUT);
        verify(botInnerService).setUserInputState(MockUpdateInfos.UPDATE_INFO_PASSWORD_INPUT.getChatId(), InputState.UBI_ACCOUNT_ENTRY_2FA_CODE);
    }
}