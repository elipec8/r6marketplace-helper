package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.user.ItemFilterEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.ItemFilterEntityId;
import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.UserEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemFilterPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user.ItemFilterEntityMapper;
import github.ricemonger.utils.DTOs.personal.ItemFilter;
import github.ricemonger.utils.exceptions.client.ItemFilterDoesntExistException;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TelegramUserItemFilterPostgresServiceTest {
    @Autowired
    private TelegramUserItemFilterPostgresService telegramUserItemFilterService;
    @MockBean
    private ItemFilterPostgresRepository itemFilterRepository;
    @MockBean
    private TelegramUserPostgresRepository telegramUserRepository;
    @MockBean
    private ItemFilterEntityMapper itemFilterEntityMapper;

    @Test
    public void save_should_map_and_save_dto() throws TelegramUserDoesntExistException {
        ItemFilter filter = new ItemFilter();
        ItemFilterEntity entity = new ItemFilterEntity();
        String chatId = "chatId";
        when(itemFilterEntityMapper.createEntityForTelegramUserChatId(same(chatId), same(filter))).thenReturn(entity);

        itemFilterRepository.save(entity);

        verify(itemFilterRepository).save(same(entity));
    }

    @Test
    public void deleteAllByChatId_should_clear_and_save_user() throws TelegramUserDoesntExistException {
        TelegramUserEntity telegramUser = new TelegramUserEntity();
        telegramUser.setUser(new UserEntity(1L));
        ItemFilterEntity filterEntity1 = new ItemFilterEntity();
        filterEntity1.setName("name");
        ItemFilterEntity filterEntity2 = new ItemFilterEntity();
        filterEntity2.setName("name2");
        List<ItemFilterEntity> filtersEntities = List.of(filterEntity1, filterEntity2);
        telegramUser.getUser().setItemFilters(filtersEntities);

        when(telegramUserRepository.findById("chatId")).thenReturn(Optional.of(telegramUser));

        telegramUserItemFilterService.deleteById("chatId", "name");

        assertEquals(1, telegramUser.getUser().getItemFilters().size());
        assertTrue(telegramUser.getUser().getItemFilters().stream().allMatch(filter -> filter.getName().equals("name2")));
        verify(telegramUserRepository).save(same(telegramUser));
    }

    @Test
    public void findById_should_return_mapped_dto() throws TelegramUserDoesntExistException, ItemFilterDoesntExistException {
        TelegramUserEntity telegramUser = new TelegramUserEntity();
        UserEntity userEntity = new UserEntity(1L);
        telegramUser.setUser(userEntity);
        ItemFilterEntity entity = new ItemFilterEntity();
        ItemFilter filter = new ItemFilter();
        filter.setName("name");

        when(telegramUserRepository.findById("chatId")).thenReturn(Optional.of(telegramUser));
        when(itemFilterRepository.findById(new ItemFilterEntityId(userEntity, "name"))).thenReturn(Optional.of(entity));
        when(itemFilterEntityMapper.createDTO(same(entity))).thenReturn(filter);

        ItemFilter result = telegramUserItemFilterService.findById("chatId", "name");

        assertEquals(filter, result);
    }

    @Test
    public void findAllByChatId_should_return_mapped_dtos() throws TelegramUserDoesntExistException {
        List<ItemFilterEntity> entities = new ArrayList<>();
        ItemFilterEntity entity1 = new ItemFilterEntity();
        ItemFilterEntity entity2 = new ItemFilterEntity();
        entities.add(entity1);
        entities.add(entity2);
        ItemFilter filter1 = new ItemFilter();
        filter1.setName("name1");
        ItemFilter filter2 = new ItemFilter();
        filter2.setName("name2");
        when(itemFilterRepository.findAllByUserTelegramUserChatId("chatId")).thenReturn(entities);
        when(itemFilterEntityMapper.createDTO(same(entity1))).thenReturn(filter1);
        when(itemFilterEntityMapper.createDTO(same(entity2))).thenReturn(filter2);

        List<ItemFilter> expected = List.of(filter1, filter2);

        List<ItemFilter> result = telegramUserItemFilterService.findAllByChatId("chatId");

        assertTrue(result.size() == 2 && result.stream().allMatch(res -> expected.stream().anyMatch(ex -> ex == res)));
    }
}