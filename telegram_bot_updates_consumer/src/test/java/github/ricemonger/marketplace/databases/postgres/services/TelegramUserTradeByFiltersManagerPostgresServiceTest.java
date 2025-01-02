package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.repositories.TradeByFiltersManagerPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user.TradeByFiltersManagerEntityMapper;
import github.ricemonger.marketplace.services.DTOs.TradeByFiltersManager;
import github.ricemonger.utils.exceptions.client.TradeByFiltersManagerDoesntExistException;
import github.ricemonger.utilspostgresschema.full_entities.user.TradeByFiltersManagerEntity;
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
class TelegramUserTradeByFiltersManagerPostgresServiceTest {
    @Autowired
    private TelegramUserTradeByFiltersManagerPostgresService telegramUserTradeByFiltersManagerService;
    @MockBean
    private TradeByFiltersManagerPostgresRepository tradeByFiltersManagerRepository;
    @MockBean
    private TradeByFiltersManagerEntityMapper tradeByFiltersManagerEntityMapper;

    @Test
    public void save_should_handle_to_repository_mapped_entity() {
        TradeByFiltersManager tradeManager = Mockito.mock(TradeByFiltersManager.class);

        TradeByFiltersManagerEntity entity = Mockito.mock(TradeByFiltersManagerEntity.class);

        when(tradeByFiltersManagerEntityMapper.createEntity(eq("chatId"), same(tradeManager))).thenReturn(entity);

        telegramUserTradeByFiltersManagerService.save("chatId", tradeManager);

        verify(tradeByFiltersManagerRepository).save(same(entity));
    }

    @Test
    public void invertEnabledFlagById_should_handle_to_repository() {
        telegramUserTradeByFiltersManagerService.invertEnabledFlagById("chatId", "name");

        verify(tradeByFiltersManagerRepository).invertEnabledFlagByUserTelegramUserChatIdAndName("chatId", "name");
    }

    @Test
    public void deleteById_should_handle_to_repository() {
        telegramUserTradeByFiltersManagerService.deleteById("chatId", "name");

        verify(tradeByFiltersManagerRepository).deleteByUserTelegramUserChatIdAndName("chatId", "name");
    }

    @Test
    public void findById_should_handle_to_repository() {
        TradeByFiltersManagerEntity entity = Mockito.mock(TradeByFiltersManagerEntity.class);
        TradeByFiltersManager tradeManager = Mockito.mock(TradeByFiltersManager.class);

        when(tradeByFiltersManagerRepository.findByUserTelegramUserChatIdAndName("chatId", "name")).thenReturn(java.util.Optional.of(entity));
        when(tradeByFiltersManagerEntityMapper.createDTO(entity)).thenReturn(tradeManager);

        assertSame(tradeManager, telegramUserTradeByFiltersManagerService.findById("chatId", "name"));
    }

    @Test
    public void findById_should_throw_exception_when_entity_not_found() {
        when(tradeByFiltersManagerRepository.findByUserTelegramUserChatIdAndName("chatId", "name")).thenReturn(java.util.Optional.empty());

        assertThrows(TradeByFiltersManagerDoesntExistException.class, () -> telegramUserTradeByFiltersManagerService.findById("chatId", "name"));
    }

    @Test
    public void findAllByChatId_should_return_mapped_dtos() {
        TradeByFiltersManagerEntity entity1 = Mockito.mock(TradeByFiltersManagerEntity.class);
        TradeByFiltersManagerEntity entity2 = Mockito.mock(TradeByFiltersManagerEntity.class);
        TradeByFiltersManager tradeManager1 = Mockito.mock(TradeByFiltersManager.class);
        TradeByFiltersManager tradeManager2 = Mockito.mock(TradeByFiltersManager.class);

        when(tradeByFiltersManagerRepository.findAllByUserTelegramUserChatId("chatId")).thenReturn(List.of(entity1, entity2));
        when(tradeByFiltersManagerEntityMapper.createDTO(entity1)).thenReturn(tradeManager1);
        when(tradeByFiltersManagerEntityMapper.createDTO(entity2)).thenReturn(tradeManager2);

        List<TradeByFiltersManager> result = telegramUserTradeByFiltersManagerService.findAllByChatId("chatId");

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(res -> res == tradeManager1));
        assertTrue(result.stream().anyMatch(res -> res == tradeManager2));
    }
}