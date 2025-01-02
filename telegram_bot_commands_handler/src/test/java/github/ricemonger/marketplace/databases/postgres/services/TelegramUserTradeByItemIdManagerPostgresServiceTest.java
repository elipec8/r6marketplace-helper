package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.repositories.TradeByItemIdManagerPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user.TradeByItemIdManagerEntityMapper;
import github.ricemonger.utils.DTOs.personal.TradeByItemIdManager;
import github.ricemonger.utils.exceptions.client.TradeByItemIdManagerDoesntExistException;
import github.ricemonger.utilspostgresschema.full_entities.user.TradeByItemIdManagerEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TelegramUserTradeByItemIdManagerPostgresServiceTest {
    @Autowired
    private TelegramUserTradeByItemIdManagerPostgresService telegramUserTradeByItemIdManagerService;
    @MockBean
    private TradeByItemIdManagerPostgresRepository TradeByItemIdManagerRepository;
    @MockBean
    private TradeByItemIdManagerEntityMapper TradeByItemIdManagerEntityMapper;

    @Test
    public void save_should_handle_to_repository_mapped_entity() {
        TradeByItemIdManager tradeManager = Mockito.mock(TradeByItemIdManager.class);

        TradeByItemIdManagerEntity entity = Mockito.mock(TradeByItemIdManagerEntity.class);

        when(TradeByItemIdManagerEntityMapper.createEntity(eq("chatId"), same(tradeManager))).thenReturn(entity);

        telegramUserTradeByItemIdManagerService.save("chatId", tradeManager);

        verify(TradeByItemIdManagerRepository).save(same(entity));
    }

    @Test
    public void invertEnabledFlagById_should_handle_to_repository() {
        telegramUserTradeByItemIdManagerService.invertEnabledFlagById("chatId", "itemId");

        verify(TradeByItemIdManagerRepository).invertEnabledFlagByUserTelegramUserChatIdAndItemItemId("chatId", "itemId");
    }

    @Test
    public void deleteById_should_handle_to_repository() {
        telegramUserTradeByItemIdManagerService.deleteById("chatId", "itemId");

        verify(TradeByItemIdManagerRepository).deleteByUserTelegramUserChatIdAndItemItemId("chatId", "itemId");
    }

    @Test
    public void findById_should_handle_to_repository() {
        TradeByItemIdManagerEntity entity = Mockito.mock(TradeByItemIdManagerEntity.class);
        TradeByItemIdManager tradeManager = Mockito.mock(TradeByItemIdManager.class);

        when(TradeByItemIdManagerRepository.findByUserTelegramUserChatIdAndItemItemId("chatId", "itemId")).thenReturn(java.util.Optional.of(entity));
        when(TradeByItemIdManagerEntityMapper.createDTO(entity)).thenReturn(tradeManager);

        assertSame(tradeManager, telegramUserTradeByItemIdManagerService.findById("chatId", "itemId"));
    }

    @Test
    public void findById_should_throw_exception_when_entity_not_found() {
        when(TradeByItemIdManagerRepository.findByUserTelegramUserChatIdAndItemItemId("chatId", "itemId")).thenReturn(java.util.Optional.empty());

        assertThrows(TradeByItemIdManagerDoesntExistException.class, () -> telegramUserTradeByItemIdManagerService.findById("chatId", "itemId"));
    }

    @Test
    public void findAllByChatId_should_return_mapped_dtos() {
        TradeByItemIdManagerEntity entity1 = Mockito.mock(TradeByItemIdManagerEntity.class);
        TradeByItemIdManagerEntity entity2 = Mockito.mock(TradeByItemIdManagerEntity.class);
        TradeByItemIdManager tradeManager1 = Mockito.mock(TradeByItemIdManager.class);
        TradeByItemIdManager tradeManager2 = Mockito.mock(TradeByItemIdManager.class);

        when(TradeByItemIdManagerRepository.findAllByUserTelegramUserChatId("chatId")).thenReturn(List.of(entity1, entity2));
        when(TradeByItemIdManagerEntityMapper.createDTO(entity1)).thenReturn(tradeManager1);
        when(TradeByItemIdManagerEntityMapper.createDTO(entity2)).thenReturn(tradeManager2);

        List<TradeByItemIdManager> result = telegramUserTradeByItemIdManagerService.findAllByChatId("chatId");

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(res -> res == tradeManager1));
        assertTrue(result.stream().anyMatch(res -> res == tradeManager2));
    }
}