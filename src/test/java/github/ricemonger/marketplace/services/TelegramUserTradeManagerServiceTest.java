package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.TelegramUserTradeManagerByItemFilterDatabaseService;
import github.ricemonger.marketplace.services.abstractions.TelegramUserTradeManagerByItemIdDatabaseService;
import github.ricemonger.utils.dtos.TradeManagerByItemFilters;
import github.ricemonger.utils.dtos.TradeManagerByItemId;
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
    private TelegramUserTradeManagerByItemIdDatabaseService telegramUserTradeManagerByItemIdDatabaseService;
    @MockBean
    private TelegramUserTradeManagerByItemFilterDatabaseService telegramUserTradeManagerByItemFiltersDatabaseService;

    @Test
    public void saveUserTradeManagerByItemId_should_handle_to_service() {
        TradeManagerByItemId tradeManagerByItemId = new TradeManagerByItemId();

        telegramUserTradeManagerService.saveUserTradeManagerByItemId("1", tradeManagerByItemId);

        verify(telegramUserTradeManagerByItemIdDatabaseService).save(eq("1"), same(tradeManagerByItemId));
    }

    @Test
    public void saveUserTradeManagerByItemId_should_throw_if_user_doesnt_exist() {
        doThrow(TelegramUserDoesntExistException.class).when(telegramUserTradeManagerByItemIdDatabaseService).save(any(), any());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserTradeManagerService.saveUserTradeManagerByItemId("123",
                new TradeManagerByItemId()));
    }

    @Test
    public void saveUserTradeManagerByItemFilters_should_handle_to_service() {
        TradeManagerByItemFilters tradeManagerByItemFilters = new TradeManagerByItemFilters();

        telegramUserTradeManagerService.saveUserTradeManagerByItemFilters("1", tradeManagerByItemFilters);

        verify(telegramUserTradeManagerByItemFiltersDatabaseService).save(eq("1"), same(tradeManagerByItemFilters));
    }

    @Test
    public void saveUserTradeManagerByItemFilters_should_throw_if_user_doesnt_exist() {
        doThrow(TelegramUserDoesntExistException.class).when(telegramUserTradeManagerByItemFiltersDatabaseService).save(any(), any());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserTradeManagerService.saveUserTradeManagerByItemFilters("123",
                new TradeManagerByItemFilters()));
    }

    @Test
    public void deleteUserTradeManagerByItemIdById_should_handle_to_service() {
        telegramUserTradeManagerService.deleteUserTradeManagerByItemIdById("1", "1");

        verify(telegramUserTradeManagerByItemIdDatabaseService).deleteById(eq("1"), eq("1"));
    }

    @Test
    public void deleteUserTradeManagerByItemIdById_should_throw_if_user_doesnt_exist() {
        doThrow(TelegramUserDoesntExistException.class).when(telegramUserTradeManagerByItemIdDatabaseService).deleteById(any(), any());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserTradeManagerService.deleteUserTradeManagerByItemIdById("123",
                "itemId"));
    }

    @Test
    public void getUserTradeManagerByItemIdById_should_return_trade_manager_by_id() {
        String chatId = "chatId";
        String itemId = "itemId";
        TradeManagerByItemId tradeManagerByItemId = new TradeManagerByItemId();
        tradeManagerByItemId.setItemId("itemId");
        when(telegramUserTradeManagerByItemIdDatabaseService.findById(chatId, itemId)).thenReturn(tradeManagerByItemId);

        assertEquals(tradeManagerByItemId, telegramUserTradeManagerService.getUserTradeManagerByItemIdById(chatId, itemId));

        verify(telegramUserTradeManagerByItemIdDatabaseService).findById(chatId, itemId);
    }

    @Test
    public void getUserTradeManagerByItemIdById_should_throw_if_user_doesnt_exist() {
        when(telegramUserTradeManagerByItemIdDatabaseService.findById(any(), any())).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserTradeManagerService.getUserTradeManagerByItemIdById("chatId", "itemId"));
    }

    @Test
    public void getUserTradeManagerByItemIdById_should_throw_if_trade_manager_doesnt_exist() {
        when(telegramUserTradeManagerByItemIdDatabaseService.findById(any(), any())).thenThrow(TradeManagerByItemIdDoesntExistException.class);

        assertThrows(TradeManagerByItemIdDoesntExistException.class, () -> telegramUserTradeManagerService.getUserTradeManagerByItemIdById("chatId", "itemId"));
    }

    @Test
    public void getAllUserTradeManagersByItemId_should_return_all_user_trade_managers_item_id() {
        List<TradeManagerByItemId> tradeManagers = new ArrayList<>();

        when(telegramUserTradeManagerByItemIdDatabaseService.findAllByChatId(any())).thenReturn(tradeManagers);

        assertEquals(tradeManagers, telegramUserTradeManagerService.getAllUserTradeManagersByItemId("chatId"));

        verify(telegramUserTradeManagerByItemIdDatabaseService).findAllByChatId("chatId");
    }

    @Test
    public void getAllUserTradeManagersByItemId_should_throw_if_user_doesnt_exist() {
        when(telegramUserTradeManagerByItemIdDatabaseService.findAllByChatId(any())).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserTradeManagerService.getAllUserTradeManagersByItemId("chatId"));
    }

    @Test
    public void getAllUserTradeManagersByItemFilters_should_return_all_user_trade_managers_item_filters() {
        List<TradeManagerByItemFilters> tradeManagers = new ArrayList<>();

        when(telegramUserTradeManagerByItemFiltersDatabaseService.findAllByChatId(any())).thenReturn(tradeManagers);

        assertEquals(tradeManagers, telegramUserTradeManagerService.getAllUserTradeManagersByItemFilters("chatId"));

        verify(telegramUserTradeManagerByItemFiltersDatabaseService).findAllByChatId("chatId");
    }

    @Test
    public void getAllUserTradeManagersByItemFilters_should_throw_if_user_doesnt_exist() {
        when(telegramUserTradeManagerByItemFiltersDatabaseService.findAllByChatId(any())).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserTradeManagerService.getAllUserTradeManagersByItemFilters("chatId"));
    }
}