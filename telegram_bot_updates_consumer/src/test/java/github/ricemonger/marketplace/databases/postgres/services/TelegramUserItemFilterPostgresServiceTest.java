package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.repositories.CustomItemFilterPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user.ItemFilterEntityMapper;
import github.ricemonger.utils.DTOs.personal.ItemFilter;
import github.ricemonger.utils.exceptions.client.ItemFilterDoesntExistException;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utilspostgresschema.full_entities.user.ItemFilterEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.TelegramUserEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.UserEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TelegramUserItemFilterPostgresServiceTest {
    @Autowired
    private TelegramUserItemFilterPostgresService telegramUserItemFilterService;
    @MockBean
    private CustomItemFilterPostgresRepository itemFilterRepository;
    @MockBean
    private ItemFilterEntityMapper itemFilterEntityMapper;

    @Test
    public void save_should_map_and_save_dto() throws TelegramUserDoesntExistException {
        ItemFilter filter = new ItemFilter();
        ItemFilterEntity entity = new ItemFilterEntity();
        String chatId = "chatId";
        when(itemFilterEntityMapper.createEntity(same(chatId), same(filter))).thenReturn(entity);

        itemFilterRepository.save(entity);

        verify(itemFilterRepository).save(same(entity));
    }

    @Test
    public void deleteById_should_handle_to_repository() throws TelegramUserDoesntExistException {
        telegramUserItemFilterService.deleteById("chatId", "name");
        verify(itemFilterRepository).deleteByUserTelegramUserChatIdAndName("chatId", "name");
    }

    @Test
    public void findById_should_return_mapped_dto() throws TelegramUserDoesntExistException, ItemFilterDoesntExistException {
        TelegramUserEntity telegramUser = new TelegramUserEntity();
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        telegramUser.setUser(userEntity);
        ItemFilterEntity entity = new ItemFilterEntity();
        ItemFilter filter = new ItemFilter();
        filter.setName("name");

        when(itemFilterRepository.findByUserTelegramUserChatIdAndName(eq("chatId"), eq("name"))).thenReturn(Optional.of(entity));
        when(itemFilterEntityMapper.createDTO(same(entity))).thenReturn(filter);

        assertSame(filter, telegramUserItemFilterService.findById("chatId", "name"));
    }

    @Test
    public void findAllNamesByChatId_should_return_repository_result() throws TelegramUserDoesntExistException {
        List names = Mockito.mock(List.class);
        when(itemFilterRepository.findAllNamesByUserTelegramUserChatId("chatId")).thenReturn(names);

        assertSame(names, telegramUserItemFilterService.findAllNamesByChatId("chatId"));
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