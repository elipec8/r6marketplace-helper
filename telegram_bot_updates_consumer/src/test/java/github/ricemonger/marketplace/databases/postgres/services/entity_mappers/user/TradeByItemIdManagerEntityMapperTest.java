package github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user;

import github.ricemonger.marketplace.databases.postgres.repositories.CustomUserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.utils.DTOs.personal.TradeByItemIdManager;
import github.ricemonger.utils.enums.TradeOperationType;
import github.ricemonger.utils.exceptions.client.ItemDoesntExistException;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utilspostgresschema.full_entities.item.ItemEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.TradeByItemIdManagerEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class TradeByItemIdManagerEntityMapperTest {
    @Autowired
    private TradeByItemIdManagerEntityMapper tradeByItemIdManagerEntityMapper;
    @MockBean
    private CustomUserPostgresRepository userRepository;
    @MockBean
    private ItemPostgresRepository itemRepository;

    @Test
    public void createEntity_should_properly_map_entity() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        when(userRepository.existsByTelegramUserChatId("chatId")).thenReturn(true);
        when(userRepository.getReferenceByTelegramUserChatId("chatId")).thenReturn(user);

        ItemEntity item = new ItemEntity();
        item.setItemId("itemId");

        when(itemRepository.existsById("itemId")).thenReturn(true);
        when(itemRepository.getReferenceById("itemId")).thenReturn(item);

        TradeByItemIdManager dto = new TradeByItemIdManager();
        dto.setItemId("itemId");
        dto.setEnabled(true);
        dto.setTradeOperationType(TradeOperationType.BUY);
        dto.setSellBoundaryPrice(5);
        dto.setBuyBoundaryPrice(10);
        dto.setPriorityMultiplier(2);

        TradeByItemIdManagerEntity expected = new TradeByItemIdManagerEntity();
        expected.setUser(user);
        expected.setItem(item);
        expected.setEnabled(true);
        expected.setTradeOperationType(TradeOperationType.BUY);
        expected.setSellBoundaryPrice(5);
        expected.setBuyBoundaryPrice(10);
        expected.setPriorityMultiplier(2);

        assertTrue(expected.isFullyEqual(tradeByItemIdManagerEntityMapper.createEntity("chatId", dto)));
    }

    @Test
    public void createEntity_should_throw_if_user_doesnt_exist() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        when(userRepository.existsByTelegramUserChatId("chatId")).thenReturn(false);
        when(userRepository.getReferenceByTelegramUserChatId("chatId")).thenReturn(user);
        ItemEntity item = new ItemEntity();
        item.setItemId("itemId");

        when(itemRepository.existsById("itemId")).thenReturn(true);
        when(itemRepository.getReferenceById("itemId")).thenReturn(item);

        TradeByItemIdManager dto = new TradeByItemIdManager();
        dto.setItemId("itemId");
        dto.setEnabled(true);
        dto.setTradeOperationType(TradeOperationType.BUY);
        dto.setSellBoundaryPrice(5);
        dto.setBuyBoundaryPrice(10);
        dto.setPriorityMultiplier(2);

        assertThrows(TelegramUserDoesntExistException.class, () -> tradeByItemIdManagerEntityMapper.createEntity("chatId", dto));
    }

    @Test
    public void createEntity_should_throw_if_item_doesnt_exist() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        when(userRepository.existsByTelegramUserChatId("chatId")).thenReturn(true);
        when(userRepository.getReferenceByTelegramUserChatId("chatId")).thenReturn(user);
        ItemEntity item = new ItemEntity();
        item.setItemId("itemId");

        when(itemRepository.existsById("itemId")).thenReturn(false);
        when(itemRepository.getReferenceById("itemId")).thenReturn(item);

        TradeByItemIdManager dto = new TradeByItemIdManager();
        dto.setItemId("itemId");
        dto.setEnabled(true);
        dto.setTradeOperationType(TradeOperationType.BUY);
        dto.setSellBoundaryPrice(5);
        dto.setBuyBoundaryPrice(10);
        dto.setPriorityMultiplier(2);

        assertThrows(ItemDoesntExistException.class, () -> tradeByItemIdManagerEntityMapper.createEntity("chatId", dto));
    }

    @Test
    public void createDTO_should_properly_map_dto() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        ItemEntity item = new ItemEntity();
        item.setItemId("itemId");
        TradeByItemIdManagerEntity entity = new TradeByItemIdManagerEntity();
        entity.setUser(user);
        entity.setItem(item);
        entity.setEnabled(true);
        entity.setTradeOperationType(TradeOperationType.BUY);
        entity.setSellBoundaryPrice(5);
        entity.setBuyBoundaryPrice(10);
        entity.setPriorityMultiplier(2);

        TradeByItemIdManager expected = new TradeByItemIdManager();
        expected.setItemId("itemId");
        expected.setEnabled(true);
        expected.setTradeOperationType(TradeOperationType.BUY);
        expected.setSellBoundaryPrice(5);
        expected.setBuyBoundaryPrice(10);
        expected.setPriorityMultiplier(2);

        assertEquals(expected, tradeByItemIdManagerEntityMapper.createDTO(entity));
    }
}