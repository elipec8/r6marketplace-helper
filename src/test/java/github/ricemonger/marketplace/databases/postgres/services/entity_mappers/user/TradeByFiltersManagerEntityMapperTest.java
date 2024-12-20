package github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user;

import github.ricemonger.marketplace.databases.postgres.entities.user.ItemFilterEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TradeByFiltersManagerEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.UserEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.UserPostgresRepository;
import github.ricemonger.utils.DTOs.personal.ItemFilter;
import github.ricemonger.utils.DTOs.personal.TradeByFiltersManager;
import github.ricemonger.utils.enums.TradeOperationType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class TradeByFiltersManagerEntityMapperTest {
    @Autowired
    private TradeByFiltersManagerEntityMapper tradeByFiltersManagerEntityMapper;
    @MockBean
    private UserPostgresRepository userPostgresRepository;
    @MockBean
    private ItemFilterEntityMapper itemFilterEntityMapper;

    @Test
    public void createEntityForTelegramUser_should_properly_map_entity() {
        when(userPostgresRepository.findByTelegramUserChatId("chatId")).thenReturn(new UserEntity(1L));
        when(itemFilterEntityMapper.createEntityForUser(any(), any())).thenReturn(new ItemFilterEntity());

        TradeByFiltersManager dto = new TradeByFiltersManager();
        dto.setName("name");
        dto.setEnabled(true);
        dto.setTradeOperationType(TradeOperationType.BUY);
        dto.setAppliedFilters(List.of(new ItemFilter()));
        dto.setMinDifferenceFromMedianPrice(10);
        dto.setMinDifferenceFromMedianPricePercent(11);
        dto.setPriorityMultiplier(12);

        TradeByFiltersManagerEntity expected = new TradeByFiltersManagerEntity();
        expected.setUser(new UserEntity(1L));
        expected.setName("name");
        expected.setEnabled(true);
        expected.setTradeOperationType(TradeOperationType.BUY);
        expected.setAppliedFilters(List.of(new ItemFilterEntity()));
        expected.setMinDifferenceFromMedianPrice(10);
        expected.setMinDifferenceFromMedianPricePercent(11);
        expected.setPriorityMultiplier(12);

        assertTrue(expected.isFullyEqualExceptUser(tradeByFiltersManagerEntityMapper.createEntityForTelegramUser("chatId", dto)));
    }

    @Test
    public void createDTO_should_properly_map_dto() {
        when(itemFilterEntityMapper.createDTO(any())).thenReturn(new ItemFilter());

        TradeByFiltersManagerEntity entity = new TradeByFiltersManagerEntity();
        entity.setUser(new UserEntity(1L));
        entity.setName("name");
        entity.setEnabled(true);
        entity.setTradeOperationType(TradeOperationType.BUY);
        entity.setAppliedFilters(List.of(new ItemFilterEntity()));
        entity.setMinDifferenceFromMedianPrice(10);
        entity.setMinDifferenceFromMedianPricePercent(11);
        entity.setPriorityMultiplier(12);

        TradeByFiltersManager expected = new TradeByFiltersManager();
        expected.setName("name");
        expected.setEnabled(true);
        expected.setTradeOperationType(TradeOperationType.BUY);
        expected.setAppliedFilters(List.of(new ItemFilter()));
        expected.setMinDifferenceFromMedianPrice(10);
        expected.setMinDifferenceFromMedianPricePercent(11);
        expected.setPriorityMultiplier(12);

        assertEquals(expected, tradeByFiltersManagerEntityMapper.createDTO(entity));
    }
}