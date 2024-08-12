package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.TelegramUserTradeByFiltersManagerDatabaseService;
import github.ricemonger.marketplace.services.abstractions.TelegramUserTradeByItemIdManagerDatabaseService;
import github.ricemonger.utils.dtos.TradeByFiltersManager;
import github.ricemonger.utils.dtos.TradeByItemIdManager;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.TradeManagerByItemIdDoesntExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;

@SpringBootTest
class TelegramUserTradeManagerServiceTest {
    @Autowired
    private TelegramUserTradeManagerService telegramUserTradeManagerService;
    @MockBean
    private TelegramUserTradeByItemIdManagerDatabaseService telegramUserTradeByItemIdManagerDatabaseService;
    @MockBean
    private TelegramUserTradeByFiltersManagerDatabaseService telegramUserTradeManagerByItemFiltersDatabaseService;

    @Test
    public void saveUserTradeByItemId_Manager_should_handle_to_service() {
        TradeByItemIdManager tradeByItemIdManager = new TradeByItemIdManager();

        telegramUserTradeManagerService.saveUserTradeByItemIdManager("1", tradeByItemIdManager);

        verify(telegramUserTradeByItemIdManagerDatabaseService).save(eq("1"), same(tradeByItemIdManager));
    }

    @Test
    public void saveUserTradeManagerByItemId_should_throw_if_user_doesnt_existManager() {
        doThrow(TelegramUserDoesntExistException.class).when(telegramUserTradeByItemIdManagerDatabaseService).save(any(), any());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserTradeManagerService.saveUserTradeByItemIdManager("123",
                new TradeByItemIdManager()));
    }

    @Test
    public void saveUserTradeByFilters_Manager_should_handle_to_service() {
        TradeByFiltersManager tradeByFiltersManager = new TradeByFiltersManager();

        telegramUserTradeManagerService.saveUserTradeByFiltersManager("1", tradeByFiltersManager);

        verify(telegramUserTradeManagerByItemFiltersDatabaseService).save(eq("1"), same(tradeByFiltersManager));
    }

    @Test
    public void saveUserTradeManagerByItemFilters_should_throw_if_user_doesnt_existManager() {
        doThrow(TelegramUserDoesntExistException.class).when(telegramUserTradeManagerByItemFiltersDatabaseService).save(any(), any());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserTradeManagerService.saveUserTradeByFiltersManager("123",
                new TradeByFiltersManager()));
    }

    @Test
    public void deleteUserTradeByItemIdManagerById_should_handle_to_service() {
        telegramUserTradeManagerService.deleteUserTradeByItemIdManagerById("1", "1");

        verify(telegramUserTradeByItemIdManagerDatabaseService).deleteById(eq("1"), eq("1"));
    }

    @Test
    public void deleteUserTradeManagerByItemIdById_should_throw_if_user_doesnt_existManager() {
        doThrow(TelegramUserDoesntExistException.class).when(telegramUserTradeByItemIdManagerDatabaseService).deleteById(any(), any());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserTradeManagerService.deleteUserTradeByItemIdManagerById("123",
                "itemId"));
    }

    @Test
    public void getUserTradeManagerByItemIdById_should_return_trade__Manager_by_id() {
        String chatId = "chatId";
        String itemId = "itemId";
        TradeByItemIdManager tradeByItemIdManager = new TradeByItemIdManager();
        tradeByItemIdManager.setItemId("itemId");
        when(telegramUserTradeByItemIdManagerDatabaseService.findById(chatId, itemId)).thenReturn(tradeByItemIdManager);

        assertEquals(tradeByItemIdManager, telegramUserTradeManagerService.getUserTradeByItemIdManagerById(chatId, itemId));

        verify(telegramUserTradeByItemIdManagerDatabaseService).findById(chatId, itemId);
    }

    @Test
    public void getUserTradeManagerByItemIdById_should_throw_if_user_doesnt_existManager() {
        when(telegramUserTradeByItemIdManagerDatabaseService.findById(any(), any())).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserTradeManagerService.getUserTradeByItemIdManagerById("chatId", "itemId"));
    }

    @Test
    public void getUserTradeManagerByItemIdById_should_throw_if_trade__doesnt_existManager() {
        when(telegramUserTradeByItemIdManagerDatabaseService.findById(any(), any())).thenThrow(TradeManagerByItemIdDoesntExistException.class);

        assertThrows(TradeManagerByItemIdDoesntExistException.class, () -> telegramUserTradeManagerService.getUserTradeByItemIdManagerById("chatId", "itemId"));
    }

    @Test
    public void getAllUserTradeManagersByItemId_should_return_all_user_trade__item_idManagers() {
        List<TradeByItemIdManager> tradeManagers = new ArrayList<>();

        when(telegramUserTradeByItemIdManagerDatabaseService.findAllByChatId(any())).thenReturn(tradeManagers);

        assertEquals(tradeManagers, telegramUserTradeManagerService.getAllUserTradeByItemIdManagers("chatId"));

        verify(telegramUserTradeByItemIdManagerDatabaseService).findAllByChatId("chatId");
    }

    @Test
    public void getAllUserTradeManagersByItemId_should_throw_if_user_doesnt_existManagers() {
        when(telegramUserTradeByItemIdManagerDatabaseService.findAllByChatId(any())).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserTradeManagerService.getAllUserTradeByItemIdManagers("chatId"));
    }

    @Test
    public void getAllUserTradeManagersByItemFilters_should_return_all_user_trade___filtersManagers() {
        List<TradeByFiltersManager> tradeManagers = new ArrayList<>();

        when(telegramUserTradeManagerByItemFiltersDatabaseService.findAllByChatId(any())).thenReturn(tradeManagers);

        assertEquals(tradeManagers, telegramUserTradeManagerService.getAllUserTradeByFiltersManagers("chatId"));

        verify(telegramUserTradeManagerByItemFiltersDatabaseService).findAllByChatId("chatId");
    }

    @Test
    public void getAllUserTradeManagersByItemFilters_should_throw_if_user_doesnt_existManagers() {
        when(telegramUserTradeManagerByItemFiltersDatabaseService.findAllByChatId(any())).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserTradeManagerService.getAllUserTradeByFiltersManagers("chatId"));
    }
}