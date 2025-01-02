package github.ricemonger.telegramBot.update_consumer.executors.items.show;

import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.telegramBot.update_consumer.executors.MockUpdateInfos;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ItemsShowStage1AskOffsetCallbackTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_process_first_input_and_send_text() {
        ItemsShowStage1AskOffsetCallback commandExecutor = new ItemsShowStage1AskOffsetCallback();
        commandExecutor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).sendText(eq(MockUpdateInfos.UPDATE_INFO), anyString());

        verify(botInnerService).clearUserInputsAndSetInputStateAndGroup(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.ITEMS_SHOW_OFFSET, InputGroup.ITEMS_SHOW);
    }
}