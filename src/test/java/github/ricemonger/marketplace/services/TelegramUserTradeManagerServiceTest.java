package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.TelegramUserTradeByFiltersManagerDatabaseService;
import github.ricemonger.marketplace.services.abstractions.TelegramUserTradeByItemIdManagerDatabaseService;
import github.ricemonger.utils.dtos.TradeByFiltersManager;
import github.ricemonger.utils.dtos.TradeByItemIdManager;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
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
    private TelegramUserTradeByFiltersManagerDatabaseService telegramUserTradeByFiltersManagerDatabaseService;

    @Test
    public void saveUserTradeByItemId_Manager_should_handle_to_service() {
        TradeByItemIdManager tradeByItemIdManager = new TradeByItemIdManager();

        telegramUserTradeManagerService.saveUserTradeByItemIdManager("1", tradeByItemIdManager);

        verify(telegramUserTradeByItemIdManagerDatabaseService).save(eq("1"), same(tradeByItemIdManager));
    }

    @Test
    public void saveUserTradeManagerByItemId_should_throw_if_user_doesnt_exist() {
        doThrow(TelegramUserDoesntExistException.class).when(telegramUserTradeByItemIdManagerDatabaseService).save(any(), any());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserTradeManagerService.saveUserTradeByItemIdManager("123",
                new TradeByItemIdManager()));
    }

    @Test
    public void saveUserTradeByFilters_Manager_should_handle_to_service() {
        TradeByFiltersManager tradeByFiltersManager = new TradeByFiltersManager();

        telegramUserTradeManagerService.saveUserTradeByFiltersManager("1", tradeByFiltersManager);

        verify(telegramUserTradeByFiltersManagerDatabaseService).save(eq("1"), same(tradeByFiltersManager));
    }

    @Test
    public void saveUserTradeManagerByItemFilters_should_throw_if_user_doesnt_exist() {
        doThrow(TelegramUserDoesntExistException.class).when(telegramUserTradeByFiltersManagerDatabaseService).save(any(), any());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserTradeManagerService.saveUserTradeByFiltersManager("123",
                new TradeByFiltersManager()));
    }

    @Test
    public void invertUserTradeByFiltersManagerEnabledFlagById_should_handle_to_service() {
        telegramUserTradeManagerService.invertUserTradeByFiltersManagerEnabledFlagById("1", "1");

        verify(telegramUserTradeByFiltersManagerDatabaseService).invertEnabledFlagById(eq("1"), eq("1"));
    }

    @Test
    public void invertUserTradeByFiltersManagerEnabledFlagById_should_throw_if_user_doesnt_exist() {
        doThrow(RuntimeException.class).when(telegramUserTradeByFiltersManagerDatabaseService).invertEnabledFlagById(any(), any());

        assertThrows(RuntimeException.class, () -> telegramUserTradeManagerService.invertUserTradeByFiltersManagerEnabledFlagById("123", "name"));
    }

    @Test
    public void invertUserTradeByItemIdManagerEnabledFlagById_should_handle_to_service() {
        telegramUserTradeManagerService.invertUserTradeByItemIdManagerEnabledFlagById("1", "1");

        verify(telegramUserTradeByItemIdManagerDatabaseService).invertEnabledFlagById(eq("1"), eq("1"));
    }

    @Test
    public void invertUserTradeByItemIdManagerEnabledFlagById_should_throw_if_user_doesnt_exist() {
        doThrow(RuntimeException.class).when(telegramUserTradeByItemIdManagerDatabaseService).invertEnabledFlagById(any(), any());

        assertThrows(RuntimeException.class, () -> telegramUserTradeManagerService.invertUserTradeByItemIdManagerEnabledFlagById("123", "itemId"));
    }

    @Test
    public void deleteUserTradeByItemIdManagerById_should_handle_to_service() {
        telegramUserTradeManagerService.deleteUserTradeByItemIdManagerById("1", "1");

        verify(telegramUserTradeByItemIdManagerDatabaseService).deleteById(eq("1"), eq("1"));
    }

    @Test
    public void deleteUserTradeManagerByItemIdById_should_throw_if_user_doesnt_exist() {
        doThrow(TelegramUserDoesntExistException.class).when(telegramUserTradeByItemIdManagerDatabaseService).deleteById(any(), any());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserTradeManagerService.deleteUserTradeByItemIdManagerById("123",
                "itemId"));
    }

    @Test
    public void deleteUserTradeByFiltersManagerById_should_handle_to_service() {
        telegramUserTradeManagerService.deleteUserTradeByFiltersManagerById("1", "1");

        verify(telegramUserTradeByFiltersManagerDatabaseService).deleteById(eq("1"), eq("1"));
    }

    @Test
    public void deleteUserTradeManagerByFiltersById_should_throw_if_user_doesnt_exist() {
        doThrow(TelegramUserDoesntExistException.class).when(telegramUserTradeByFiltersManagerDatabaseService).deleteById(any(), any());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserTradeManagerService.deleteUserTradeByFiltersManagerById("123",
                "name"));
    }

    @Test
    public void getUserTradeByItemIdManagerById_should_return_trade_manager_by_id() {
        String chatId = "chatId";
        String itemId = "itemId";
        TradeByItemIdManager tradeByItemIdManager = new TradeByItemIdManager();
        tradeByItemIdManager.setItemId("itemId");
        when(telegramUserTradeByItemIdManagerDatabaseService.findById(chatId, itemId)).thenReturn(tradeByItemIdManager);

        assertEquals(tradeByItemIdManager, telegramUserTradeManagerService.getUserTradeByItemIdManagerById(chatId, itemId));

        verify(telegramUserTradeByItemIdManagerDatabaseService).findById(chatId, itemId);
    }

    @Test
    public void getUserTradeByItemIdManagerId_should_throw_if_service_throws() {
        when(telegramUserTradeByItemIdManagerDatabaseService.findById(any(), any())).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> telegramUserTradeManagerService.getUserTradeByItemIdManagerById("chatId", "itemId"));
    }

    @Test
    public void getUserTradeByFiltersManagerById_should_return_trade_manager_by_id() {
        String chatId = "chatId";
        String name = "name";
        TradeByFiltersManager tradeByFiltersManager = new TradeByFiltersManager();
        tradeByFiltersManager.setName("name");
        when(telegramUserTradeByFiltersManagerDatabaseService.findById(chatId, name)).thenReturn(tradeByFiltersManager);

        assertEquals(tradeByFiltersManager, telegramUserTradeManagerService.getUserTradeByFiltersManagerById(chatId, name));

        verify(telegramUserTradeByFiltersManagerDatabaseService).findById(chatId, name);
    }

    @Test
    public void getUserTradeByFiltersManagerById_should_throw_if_service_throws() {
        when(telegramUserTradeByFiltersManagerDatabaseService.findById(any(), any())).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> telegramUserTradeManagerService.getUserTradeByFiltersManagerById("chatId", "name"));
    }

    @Test
    public void getAllUserTradeManagersByItemId_should_return_all_user_trade__item_id() {
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
    public void getAllUserTradeManagersByItemFilters_should_return_all_user_trade___filters() {
        List<TradeByFiltersManager> tradeManagers = new ArrayList<>();

        when(telegramUserTradeByFiltersManagerDatabaseService.findAllByChatId(any())).thenReturn(tradeManagers);

        assertEquals(tradeManagers, telegramUserTradeManagerService.getAllUserTradeByFiltersManagers("chatId"));

        verify(telegramUserTradeByFiltersManagerDatabaseService).findAllByChatId("chatId");
    }

    @Test
    public void getAllUserTradeManagersByItemFilters_should_throw_if_user_doesnt_exist() {
        when(telegramUserTradeByFiltersManagerDatabaseService.findAllByChatId(any())).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserTradeManagerService.getAllUserTradeByFiltersManagers("chatId"));
    }
}