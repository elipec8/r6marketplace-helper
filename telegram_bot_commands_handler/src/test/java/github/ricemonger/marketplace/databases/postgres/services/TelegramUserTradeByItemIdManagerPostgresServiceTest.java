package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TradeByItemIdManagerEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TradeByItemIdManagerEntityId;
import github.ricemonger.marketplace.databases.postgres.entities.user.UserEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.TradeByItemIdManagerPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user.TradeByItemIdManagerEntityMapper;
import github.ricemonger.utils.DTOs.personal.TradeByItemIdManager;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.client.TradeByItemIdManagerDoesntExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class TelegramUserTradeByItemIdManagerPostgresServiceTest {
    @Autowired
    private TelegramUserTradeByItemIdManagerPostgresService telegramUserTradeManagerByItemIdService;
    @MockBean
    private TradeByItemIdManagerPostgresRepository tradeByItemIdManagerRepository;
    @MockBean
    private TelegramUserPostgresRepository telegramUserRepository;
    @MockBean
    private ItemPostgresPostgresRepository itemRepository;
    @MockBean
    private TradeByItemIdManagerEntityMapper tradeByItemIdManagerEntityMapper;

    @Test
    public void save_should_map_and_save_dto() throws TelegramUserDoesntExistException {
        TradeByItemIdManager manager = new TradeByItemIdManager();
        TradeByItemIdManagerEntity entity = new TradeByItemIdManagerEntity();


        when(tradeByItemIdManagerEntityMapper.createEntity(eq("chatId"), same(manager))).thenReturn(entity);

        telegramUserTradeManagerByItemIdService.save("chatId", manager);

        verify(tradeByItemIdManagerRepository).save(same(entity));
    }

    @Test
    public void invertEnabledFlagById_should_invert_flag_and_save_manager() throws TelegramUserDoesntExistException, TradeByItemIdManagerDoesntExistException {
        TelegramUserEntity user = new TelegramUserEntity();
        user.setUser(new UserEntity(1L));
        ItemEntity item = new ItemEntity();
        TradeByItemIdManagerEntity manager = new TradeByItemIdManagerEntity();
        manager.setEnabled(true);
        when(telegramUserRepository.findById("chatId")).thenReturn(Optional.of(user));
        when(itemRepository.findById("itemId")).thenReturn(Optional.of(item));
        when(tradeByItemIdManagerRepository.findById(new TradeByItemIdManagerEntityId(new UserEntity(1L), item))).thenReturn(Optional.of(manager));

        telegramUserTradeManagerByItemIdService.invertEnabledFlagById("chatId", "itemId");

        assertFalse(manager.getEnabled());
        verify(tradeByItemIdManagerRepository).save(same(manager));
    }

    @Test
    public void invertEnabledFlagById_should_throw_exception_when_manager_doesnt_exist() {
        when(telegramUserRepository.findById("chatId")).thenReturn(Optional.empty());
        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserTradeManagerByItemIdService.invertEnabledFlagById("chatId", "itemId"));
    }

    @Test
    public void deleteById_should_remove_manager_and_save_user() throws TelegramUserDoesntExistException {
        TelegramUserEntity user = new TelegramUserEntity();
        user.setUser(new UserEntity(1L));
        TradeByItemIdManagerEntity managerEntity1 = new TradeByItemIdManagerEntity();
        managerEntity1.setItem(new ItemEntity("itemId"));
        TradeByItemIdManagerEntity managerEntity2 = new TradeByItemIdManagerEntity();
        managerEntity2.setItem(new ItemEntity("itemId2"));
        user.getUser().getTradeByItemIdManagers().add(managerEntity1);
        user.getUser().getTradeByItemIdManagers().add(managerEntity2);
        when(telegramUserRepository.findById("chatId")).thenReturn(Optional.of(user));

        telegramUserTradeManagerByItemIdService.deleteById("chatId", "itemId");

        assertTrue(user.getUser().getTradeByItemIdManagers().size() == 1 && user.getUser().getTradeByItemIdManagers().stream().noneMatch(manager -> manager.getItem().getItemId().equals("itemId")));
        verify(telegramUserRepository).save(same(user));
    }

    @Test
    public void deleteById_should_throw_exception_when_user_doesnt_exist() {
        when(telegramUserRepository.findById("chatId")).thenReturn(Optional.empty());
        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserTradeManagerByItemIdService.deleteById("chatId", "itemId"));
    }

    @Test
    public void findById_should_return_mapped_dto() throws TelegramUserDoesntExistException, TradeByItemIdManagerDoesntExistException {
        TelegramUserEntity user = new TelegramUserEntity();
        ItemEntity item = new ItemEntity();
        TradeByItemIdManagerEntity manager = new TradeByItemIdManagerEntity();
        TradeByItemIdManager dto = new TradeByItemIdManager();
        when(telegramUserRepository.findById("chatId")).thenReturn(Optional.of(user));
        when(itemRepository.findById("itemId")).thenReturn(Optional.of(item));
        when(tradeByItemIdManagerRepository.findById(any(TradeByItemIdManagerEntityId.class))).thenReturn(Optional.of(manager));
        when(tradeByItemIdManagerEntityMapper.createDTO(same(manager))).thenReturn(dto);

        TradeByItemIdManager result = telegramUserTradeManagerByItemIdService.findById("chatId", "itemId");

        assertSame(dto, result);
    }

    @Test
    public void findById_should_throw_exception_when_user_doesnt_exist() {
        when(telegramUserRepository.findById("chatId")).thenReturn(Optional.empty());
        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserTradeManagerByItemIdService.findById("chatId", "itemId"));
    }

    @Test
    public void findAllByChatId_should_return_all_mapped_dtos() throws TelegramUserDoesntExistException {
        TelegramUserEntity user = new TelegramUserEntity();
        user.setUser(new UserEntity(1L));
        TradeByItemIdManagerEntity entity1 = new TradeByItemIdManagerEntity();
        TradeByItemIdManagerEntity entity2 = new TradeByItemIdManagerEntity();
        user.getUser().getTradeByItemIdManagers().add(entity1);
        user.getUser().getTradeByItemIdManagers().add(entity2);
        TradeByItemIdManager dto1 = new TradeByItemIdManager();
        TradeByItemIdManager dto2 = new TradeByItemIdManager();
        when(telegramUserRepository.findById("chatId")).thenReturn(Optional.of(user));
        when(tradeByItemIdManagerRepository.findAllByUserId(1L)).thenReturn(List.of(entity1, entity2));
        when(tradeByItemIdManagerEntityMapper.createDTO(same(entity1))).thenReturn(dto1);
        when(tradeByItemIdManagerEntityMapper.createDTO(same(entity2))).thenReturn(dto2);

        List<TradeByItemIdManager> result = telegramUserTradeManagerByItemIdService.findAllByChatId("chatId");

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(dto -> dto == dto1));
        assertTrue(result.stream().anyMatch(dto -> dto == dto2));
    }

    @Test
    public void findAllByChatId_should_throw_exception_when_user_doesnt_exist() {
        when(telegramUserRepository.findById("chatId")).thenReturn(Optional.empty());
        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserTradeManagerByItemIdService.findAllByChatId("chatId"));
    }
}