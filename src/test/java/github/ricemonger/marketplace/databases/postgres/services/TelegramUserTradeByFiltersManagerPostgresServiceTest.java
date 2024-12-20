package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TradeByFiltersManagerEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TradeByFiltersManagerEntityId;
import github.ricemonger.marketplace.databases.postgres.entities.user.UserEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.TradeByFiltersManagerPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user.TradeByFiltersManagerEntityMapper;
import github.ricemonger.utils.DTOs.TradeByFiltersManager;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.client.TradeByFiltersManagerDoesntExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class TelegramUserTradeByFiltersManagerPostgresServiceTest {
    @Autowired
    private TelegramUserTradeByFiltersManagerPostgresService telegramUserTradeByFiltersManagerService;
    @MockBean
    private TradeByFiltersManagerPostgresRepository tradeByFiltersManagerRepository;
    @MockBean
    private TelegramUserPostgresRepository telegramUserRepository;
    @MockBean
    private TradeByFiltersManagerEntityMapper tradeByFiltersManagerEntityMapper;

    @Test
    public void save_should_map_and_save_dto() throws TelegramUserDoesntExistException {
        TradeByFiltersManager manager = new TradeByFiltersManager();
        TradeByFiltersManagerEntity entity = new TradeByFiltersManagerEntity();
        String chatId = "chatId";
        when(tradeByFiltersManagerEntityMapper.createEntityForTelegramUser(same(chatId), same(manager))).thenReturn(entity);

        telegramUserTradeByFiltersManagerService.save("chatId", manager);

        verify(tradeByFiltersManagerRepository).save(same(entity));
    }

    @Test
    public void invertEnabledFlagById_should_invert_flag_and_save_manager() throws TelegramUserDoesntExistException, TradeByFiltersManagerDoesntExistException {
        TelegramUserEntity user = new TelegramUserEntity();
        TradeByFiltersManagerEntity manager = new TradeByFiltersManagerEntity();
        manager.setEnabled(true);
        when(telegramUserRepository.findById("chatId")).thenReturn(Optional.of(user));
        when(tradeByFiltersManagerRepository.findById(any(TradeByFiltersManagerEntityId.class))).thenReturn(Optional.of(manager));

        telegramUserTradeByFiltersManagerService.invertEnabledFlagById("chatId", "name");

        assertFalse(manager.isEnabled());
        verify(tradeByFiltersManagerRepository).save(same(manager));
    }

    @Test
    public void deleteById_should_remove_manager_and_save_user() throws TelegramUserDoesntExistException {
        TelegramUserEntity user = new TelegramUserEntity();
        TradeByFiltersManagerEntity manager1 = new TradeByFiltersManagerEntity();
        manager1.setName("name");
        TradeByFiltersManagerEntity manager2 = new TradeByFiltersManagerEntity();
        manager2.setName("name1");
        user.getUser().getTradeByFiltersManagers().add(manager1);
        user.getUser().getTradeByFiltersManagers().add(manager2);
        when(telegramUserRepository.findById("chatId")).thenReturn(Optional.of(user));

        telegramUserTradeByFiltersManagerService.deleteById("chatId", "name");

        assertEquals(1, user.getUser().getTradeByFiltersManagers().size());
        assertFalse(user.getUser().getTradeByFiltersManagers().contains(manager1));
        verify(telegramUserRepository).save(same(user));
    }

    @Test
    public void findById_should_return_mapped_dto() throws TelegramUserDoesntExistException, TradeByFiltersManagerDoesntExistException {
        TelegramUserEntity user = new TelegramUserEntity();
        user.setUser(new UserEntity(1L));
        TradeByFiltersManagerEntity managerEntity = new TradeByFiltersManagerEntity();
        TradeByFiltersManager managerDTO = new TradeByFiltersManager();
        when(telegramUserRepository.findById("chatId")).thenReturn(Optional.of(user));
        when(tradeByFiltersManagerRepository.findById(new TradeByFiltersManagerEntityId(new UserEntity(1L), "name"))).thenReturn(Optional.of(managerEntity));
        when(tradeByFiltersManagerEntityMapper.createDTO(same(managerEntity))).thenReturn(managerDTO);

        TradeByFiltersManager result = telegramUserTradeByFiltersManagerService.findById("chatId", "name");

        assertSame(managerDTO, result);
    }

    @Test
    public void findAllByChatId_should_return_all_mapped_dtos() throws TelegramUserDoesntExistException {
        TelegramUserEntity user = new TelegramUserEntity();
        TradeByFiltersManagerEntity manager1 = new TradeByFiltersManagerEntity();
        TradeByFiltersManagerEntity manager2 = new TradeByFiltersManagerEntity();
        user.getUser().getTradeByFiltersManagers().add(manager1);
        user.getUser().getTradeByFiltersManagers().add(manager2);
        TradeByFiltersManager dto1 = new TradeByFiltersManager();
        TradeByFiltersManager dto2 = new TradeByFiltersManager();
        when(telegramUserRepository.findById("chatId")).thenReturn(Optional.of(user));
        when(tradeByFiltersManagerEntityMapper.createDTO(same(manager1))).thenReturn(dto1);
        when(tradeByFiltersManagerEntityMapper.createDTO(same(manager2))).thenReturn(dto2);

        List<TradeByFiltersManager> result = telegramUserTradeByFiltersManagerService.findAllByChatId("chatId");

        assertTrue(result.size() == 2 && result.stream().allMatch(res -> List.of(dto1, dto2).stream().anyMatch(ex -> ex == res)));
    }
}