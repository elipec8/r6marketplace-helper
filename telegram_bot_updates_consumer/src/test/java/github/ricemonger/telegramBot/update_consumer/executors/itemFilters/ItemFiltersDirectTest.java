package github.ricemonger.telegramBot.update_consumer.executors.itemFilters;

import github.ricemonger.telegramBot.update_consumer.executors.MockUpdateInfos;
import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ItemFiltersDirectTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_ask_from_inline_keyboard_if_registered() {
        ItemFiltersDirect itemFiltersDirect = new ItemFiltersDirect();
        when(botInnerService.isUserRegistered(MockUpdateInfos.UPDATE_INFO.getChatId())).thenReturn(true);

        itemFiltersDirect.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).askFromInlineKeyboard(same(MockUpdateInfos.UPDATE_INFO), anyString(), anyInt(), any());
        verify(botInnerService, never()).sendText(same(MockUpdateInfos.UPDATE_INFO), anyString());
    }

    @Test
    public void initAndExecute_should_ask_to_register_if_not_registered() {
        ItemFiltersDirect itemFiltersDirect = new ItemFiltersDirect();
        when(botInnerService.isUserRegistered(MockUpdateInfos.UPDATE_INFO.getChatId())).thenReturn(false);

        itemFiltersDirect.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService, never()).askFromInlineKeyboard(same(MockUpdateInfos.UPDATE_INFO), anyString(), anyInt(), any());
        verify(botInnerService).sendText(same(MockUpdateInfos.UPDATE_INFO), anyString());
    }
}