package github.ricemonger.telegramBot.executors.tradeManagers.createUpdate.oneItem.buyAndSell;

import github.ricemonger.telegramBot.executors.MockUpdateInfos;
import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.edit.oneItem.buyAndSell.TradeByItemIdManagerBuyAndSellEditStage3AskBoundaryBuyPriceInput;
import github.ricemonger.utils.enums.InputState;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@SpringBootTest
class TradeByItemIdManagerBuyAndSellEditStage3AskBoundaryBuyPriceInputTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_process_middle_input_with_text() {
        TradeByItemIdManagerBuyAndSellEditStage3AskBoundaryBuyPriceInput commandExecutor = new TradeByItemIdManagerBuyAndSellEditStage3AskBoundaryBuyPriceInput();
        commandExecutor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).saveUserInput(MockUpdateInfos.UPDATE_INFO);
        verify(botInnerService).setUserInputState(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_BUY_PRICE);

        verify(botInnerService).sendText(eq(MockUpdateInfos.UPDATE_INFO), anyString());
    }
}