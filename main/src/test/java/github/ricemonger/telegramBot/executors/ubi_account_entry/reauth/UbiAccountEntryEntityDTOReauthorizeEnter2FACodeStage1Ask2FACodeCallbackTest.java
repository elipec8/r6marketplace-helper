package github.ricemonger.telegramBot.executors.ubi_account_entry.reauth;

import github.ricemonger.telegramBot.executors.MockUpdateInfos;
import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.telegramBot.update_consumer.executors.ubi_account_entry.reauth_two_fa_code.UbiAccountEntryReauthorizeEnter2FACodeStage1Ask2FACodeCallback;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.verify;

@SpringBootTest
class UbiAccountEntryEntityDTOReauthorizeEnter2FACodeStage1Ask2FACodeCallbackTest {

    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_ask_for_2fa_code() {
        UbiAccountEntryReauthorizeEnter2FACodeStage1Ask2FACodeCallback ubiAccountEntryReauthorizeEnter2FACodeStage1Ask2FACodeCallback =
                new UbiAccountEntryReauthorizeEnter2FACodeStage1Ask2FACodeCallback();

        ubiAccountEntryReauthorizeEnter2FACodeStage1Ask2FACodeCallback.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).clearUserInputsAndSetInputStateAndGroup(MockUpdateInfos.UPDATE_INFO.getChatId());
        verify(botInnerService).setUserInputGroup(MockUpdateInfos.UPDATE_INFO.getChatId(), InputGroup.UBI_ACCOUNT_ENTRY_REAUTHORIZE_2FA_CODE);
        verify(botInnerService).setUserInputState(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.UBI_ACCOUNT_ENTRY_2FA_CODE);
    }
}