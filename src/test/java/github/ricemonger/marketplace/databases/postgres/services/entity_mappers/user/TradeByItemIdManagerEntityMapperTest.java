package github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TradeByItemIdManagerEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.UserEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.UserPostgresRepository;
import github.ricemonger.utils.DTOs.personal.TradeByItemIdManager;
import github.ricemonger.utils.enums.TradeOperationType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class TradeByItemIdManagerEntityMapperTest {
    @Autowired
    private TradeByItemIdManagerEntityMapper tradeByItemIdManagerEntityMapper;
    @MockBean
    private UserPostgresRepository userRepository;
    @MockBean
    private ItemPostgresRepository itemRepository;

    @Test
    public void createEntityForTelegramUser_should_properly_map_entity() {
        when(userRepository.findByTelegramUserChatId("chatId")).thenReturn(new UserEntity(1L));
        when(itemRepository.findById("itemId")).thenReturn(Optional.of(new ItemEntity("itemId")));

        TradeByItemIdManager dto = new TradeByItemIdManager();
        dto.setItemId("itemId");
        dto.setEnabled(true);
        dto.setTradeOperationType(TradeOperationType.BUY);
        dto.setSellBoundaryPrice(5);
        dto.setBuyBoundaryPrice(10);
        dto.setPriorityMultiplier(2);

        TradeByItemIdManagerEntity expected = new TradeByItemIdManagerEntity();
        expected.setUser(new UserEntity(1L));
        expected.setItem(new ItemEntity("itemId"));
        expected.setEnabled(true);
        expected.setTradeOperationType(TradeOperationType.BUY);
        expected.setSellBoundaryPrice(5);
        expected.setBuyBoundaryPrice(10);
        expected.setPriorityMultiplier(2);

        assertTrue(expected.isFullyEqualExceptUser(tradeByItemIdManagerEntityMapper.createEntityForTelegramUser("chatId", dto)));
    }

    @Test
    public void createDTO_should_properly_map_dto() {
        TradeByItemIdManagerEntity entity = new TradeByItemIdManagerEntity();
        entity.setUser(new UserEntity(1L));
        entity.setItem(new ItemEntity("itemId"));
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