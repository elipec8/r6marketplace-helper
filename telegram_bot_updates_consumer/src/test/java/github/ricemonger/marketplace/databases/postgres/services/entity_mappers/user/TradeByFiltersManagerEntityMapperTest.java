package github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user;


import github.ricemonger.marketplace.databases.postgres.repositories.CustomUserPostgresRepository;
import github.ricemonger.marketplace.services.DTOs.TradeByFiltersManager;
import github.ricemonger.utils.enums.TradeOperationType;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utilspostgresschema.full_entities.user.ItemFilterEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.TradeByFiltersManagerEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class TradeByFiltersManagerEntityMapperTest {
    @Autowired
    private TradeByFiltersManagerEntityMapper tradeByFiltersManagerEntityMapper;
    @MockBean
    private CustomUserPostgresRepository customUserPostgresRepository;

    @Test
    public void createEntity_should_properly_map_entity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        ItemFilterEntity itemFilterEntity1 = new ItemFilterEntity();
        itemFilterEntity1.setName("filter1");
        ItemFilterEntity itemFilterEntity2 = new ItemFilterEntity();
        itemFilterEntity2.setName("filter2");
        ItemFilterEntity itemFilterEntity3 = new ItemFilterEntity();
        itemFilterEntity3.setName("filter3");
        userEntity.setItemFilters(List.of(itemFilterEntity1, itemFilterEntity2, itemFilterEntity3));
        when(customUserPostgresRepository.existsByTelegramUserChatId("chatId")).thenReturn(true);
        when(customUserPostgresRepository.getReferenceByTelegramUserChatId("chatId")).thenReturn(userEntity);

        TradeByFiltersManager dto = new TradeByFiltersManager();
        dto.setName("name");
        dto.setEnabled(true);
        dto.setTradeOperationType(TradeOperationType.BUY);
        dto.setAppliedFilters(List.of("filter1", "filter2", "filter4"));
        dto.setMinDifferenceFromMedianPrice(10);
        dto.setMinDifferenceFromMedianPricePercent(11);
        dto.setPriorityMultiplier(12);

        TradeByFiltersManagerEntity expected = new TradeByFiltersManagerEntity();
        expected.setUser(userEntity);
        expected.setName("name");
        expected.setEnabled(true);
        expected.setTradeOperationType(TradeOperationType.BUY);
        expected.setAppliedFilters(List.of(itemFilterEntity1, itemFilterEntity2));
        expected.setMinDifferenceFromMedianPrice(10);
        expected.setMinDifferenceFromMedianPricePercent(11);
        expected.setPriorityMultiplier(12);

        assertTrue(expected.isFullyEqual(tradeByFiltersManagerEntityMapper.createEntity("chatId", dto)));
    }

    @Test
    public void createEntity_should_throw_if_user_doesnt_exist() {
        when(customUserPostgresRepository.existsByTelegramUserChatId("chatId")).thenReturn(false);
        TradeByFiltersManager dto = new TradeByFiltersManager();
        dto.setName("name");
        dto.setEnabled(true);
        dto.setTradeOperationType(TradeOperationType.BUY);
        dto.setAppliedFilters(List.of("filter1", "filter2", "filter4"));
        dto.setMinDifferenceFromMedianPrice(10);
        dto.setMinDifferenceFromMedianPricePercent(11);
        dto.setPriorityMultiplier(12);

        assertThrows(TelegramUserDoesntExistException.class, () -> tradeByFiltersManagerEntityMapper.createEntity("chatId", dto));
    }

    @Test
    public void createDTO_should_properly_map_dto() {
        ItemFilterEntity itemFilterEntity1 = new ItemFilterEntity();
        itemFilterEntity1.setName("filter1");
        ItemFilterEntity itemFilterEntity2 = new ItemFilterEntity();
        itemFilterEntity2.setName("filter2");
        ItemFilterEntity itemFilterEntity3 = new ItemFilterEntity();
        itemFilterEntity3.setName("filter3");
        TradeByFiltersManagerEntity entity = new TradeByFiltersManagerEntity();
        entity.setUser(new UserEntity());
        entity.setName("name");
        entity.setEnabled(true);
        entity.setTradeOperationType(TradeOperationType.BUY);
        entity.setAppliedFilters(List.of(itemFilterEntity1, itemFilterEntity2, itemFilterEntity3));
        entity.setMinDifferenceFromMedianPrice(10);
        entity.setMinDifferenceFromMedianPricePercent(11);
        entity.setPriorityMultiplier(12);

        TradeByFiltersManager expected = new TradeByFiltersManager();
        expected.setName("name");
        expected.setEnabled(true);
        expected.setTradeOperationType(TradeOperationType.BUY);
        expected.setAppliedFilters(List.of("filter1", "filter2", "filter3"));
        expected.setMinDifferenceFromMedianPrice(10);
        expected.setMinDifferenceFromMedianPricePercent(11);
        expected.setPriorityMultiplier(12);

        assertEquals(expected, tradeByFiltersManagerEntityMapper.createDTO(entity));
    }
}