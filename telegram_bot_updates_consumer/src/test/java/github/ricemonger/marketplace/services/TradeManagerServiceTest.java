package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.DTOs.TradeByFiltersManager;
import github.ricemonger.marketplace.services.abstractions.TelegramUserTradeByFiltersManagerDatabaseService;
import github.ricemonger.marketplace.services.abstractions.TelegramUserTradeByItemIdManagerDatabaseService;
import github.ricemonger.utils.DTOs.personal.TradeByItemIdManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;

@SpringBootTest
class TradeManagerServiceTest {
    @Autowired
    private TradeManagerService tradeManagerService;
    @MockBean
    private TelegramUserTradeByItemIdManagerDatabaseService telegramUserTradeByItemIdManagerDatabaseService;
    @MockBean
    private TelegramUserTradeByFiltersManagerDatabaseService telegramUserTradeByFiltersManagerDatabaseService;

    @Test
    public void saveUserTradeByItemId_Manager_should_handle_to_service() {
        TradeByItemIdManager tradeByItemIdManager = new TradeByItemIdManager();

        tradeManagerService.saveTradeByItemIdManager("1", tradeByItemIdManager);

        verify(telegramUserTradeByItemIdManagerDatabaseService).save(eq("1"), same(tradeByItemIdManager));
    }

    @Test
    public void saveUserTradeByFilters_Manager_should_handle_to_service() {
        TradeByFiltersManager tradeByFiltersManager = new TradeByFiltersManager();

        tradeManagerService.saveTradeByFiltersManager("1", tradeByFiltersManager);

        verify(telegramUserTradeByFiltersManagerDatabaseService).save(eq("1"), same(tradeByFiltersManager));
    }

    @Test
    public void invertTradeByFiltersManagerEnabledFlagById_should_handle_to_service() {
        tradeManagerService.invertTradeByFiltersManagerEnabledFlagById("1", "1");

        verify(telegramUserTradeByFiltersManagerDatabaseService).invertEnabledFlagById(eq("1"), eq("1"));
    }

    @Test
    public void invertTradeByItemIdManagerEnabledFlagById_should_handle_to_service() {
        tradeManagerService.invertTradeByItemIdManagerEnabledFlagById("1", "1");

        verify(telegramUserTradeByItemIdManagerDatabaseService).invertEnabledFlagById(eq("1"), eq("1"));
    }

    @Test
    public void deleteTradeByItemIdManagerById_should_handle_to_service() {
        tradeManagerService.deleteTradeByItemIdManagerById("1", "1");

        verify(telegramUserTradeByItemIdManagerDatabaseService).deleteById(eq("1"), eq("1"));
    }

    @Test
    public void deleteTradeByFiltersManagerById_should_handle_to_service() {
        tradeManagerService.deleteTradeByFiltersManagerById("1", "1");

        verify(telegramUserTradeByFiltersManagerDatabaseService).deleteById(eq("1"), eq("1"));
    }

    @Test
    public void getTradeByItemIdManagerById_should_return_trade_manager_by_id() {
        String chatId = "chatId";
        String itemId = "itemId";
        TradeByItemIdManager tradeByItemIdManager = new TradeByItemIdManager();
        tradeByItemIdManager.setItemId("itemId");
        when(telegramUserTradeByItemIdManagerDatabaseService.findById(chatId, itemId)).thenReturn(tradeByItemIdManager);

        assertSame(tradeByItemIdManager, tradeManagerService.getTradeByItemIdManagerById(chatId, itemId));
    }

    @Test
    public void getTradeByFiltersManagerById_should_return_trade_manager_by_id() {
        String chatId = "chatId";
        String name = "name";
        TradeByFiltersManager tradeByFiltersManager = new TradeByFiltersManager();
        tradeByFiltersManager.setName("name");
        when(telegramUserTradeByFiltersManagerDatabaseService.findById(chatId, name)).thenReturn(tradeByFiltersManager);

        assertSame(tradeByFiltersManager, tradeManagerService.getTradeByFiltersManagerById(chatId, name));
    }

    @Test
    public void getAllUserTradeManagersByItemId_should_return_all_user_trade__item_id() {
        List<TradeByItemIdManager> tradeManagers = new ArrayList<>();

        when(telegramUserTradeByItemIdManagerDatabaseService.findAllByChatId(any())).thenReturn(tradeManagers);

        assertSame(tradeManagers, tradeManagerService.getAllTradeByItemIdManagersByChatId("chatId"));
    }

    @Test
    public void getAllUserTradeManagersByItemFilters_should_return_all_user_trade___filters() {
        List<TradeByFiltersManager> tradeManagers = new ArrayList<>();

        when(telegramUserTradeByFiltersManagerDatabaseService.findAllByChatId(any())).thenReturn(tradeManagers);

        assertSame(tradeManagers, tradeManagerService.getAllTradeByFiltersManagersByChatId("chatId"));
    }
}