package github.ricemonger.ubi_users_stats_fetcher.postgres.services.entity_mappers.user;

import github.ricemonger.ubi_users_stats_fetcher.postgres.entities.ubi_account_stats.ItemIdEntity;
import github.ricemonger.ubi_users_stats_fetcher.postgres.entities.ubi_account_stats.UbiTradeEntity;
import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.DTOs.personal.UbiTrade;
import github.ricemonger.utils.enums.TradeCategory;
import github.ricemonger.utils.enums.TradeState;
import github.ricemonger.utils.exceptions.client.ItemDoesntExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UbiTradeEntityMapperTest {
    @Autowired
    private UbiTradeEntityMapper ubiTradeEntityMapper;

    @Test
    public void createEntity_should_return_expected_result_for_existing_item() {
        UbiTrade ubiTrade = new UbiTrade();
        ubiTrade.setTradeId("tradeId");
        ubiTrade.setState(TradeState.Created);
        ubiTrade.setCategory(TradeCategory.Sell);
        ubiTrade.setExpiresAt(LocalDateTime.of(2021, 1, 1, 0, 0));
        ubiTrade.setLastModifiedAt(LocalDateTime.of(2022, 1, 1, 0, 0));
        ubiTrade.setItem(new Item("itemId1"));
        ubiTrade.setSuccessPaymentPrice(1);
        ubiTrade.setSuccessPaymentFee(2);
        ubiTrade.setProposedPaymentPrice(3);
        ubiTrade.setProposedPaymentFee(4);

        ItemIdEntity itemIdEntity1 = new ItemIdEntity("itemId1");
        ItemIdEntity itemIdEntity2 = new ItemIdEntity("itemId2");

        List<ItemIdEntity> existingItems = List.of(itemIdEntity1, itemIdEntity2);

        UbiTradeEntity entity = new UbiTradeEntity();
        entity.setTradeId("tradeId");
        entity.setState(TradeState.Created);
        entity.setCategory(TradeCategory.Sell);
        entity.setExpiresAt(LocalDateTime.of(2021, 1, 1, 0, 0));
        entity.setLastModifiedAt(LocalDateTime.of(2022, 1, 1, 0, 0));
        entity.setItem(new ItemIdEntity("itemId1"));
        entity.setSuccessPaymentPrice(1);
        entity.setSuccessPaymentFee(2);
        entity.setProposedPaymentPrice(3);
        entity.setProposedPaymentFee(4);

        UbiTradeEntity result = ubiTradeEntityMapper.createEntity(ubiTrade, existingItems);

        assertEquals(entity.getTradeId(), result.getTradeId());
        assertEquals(entity.getState(), result.getState());
        assertEquals(entity.getCategory(), result.getCategory());
        assertEquals(entity.getExpiresAt(), result.getExpiresAt());
        assertEquals(entity.getLastModifiedAt(), result.getLastModifiedAt());
        assertTrue(entity.getItem().isEqual(result.getItem()));
        assertEquals(entity.getSuccessPaymentPrice(), result.getSuccessPaymentPrice());
        assertEquals(entity.getSuccessPaymentFee(), result.getSuccessPaymentFee());
        assertEquals(entity.getProposedPaymentPrice(), result.getProposedPaymentPrice());
        assertEquals(entity.getProposedPaymentFee(), result.getProposedPaymentFee());
    }

    @Test
    public void createEntity_should_throw_for_non_existing_item() {
        UbiTrade ubiTrade = new UbiTrade();
        ubiTrade.setTradeId("tradeId");
        ubiTrade.setState(TradeState.Created);
        ubiTrade.setCategory(TradeCategory.Sell);
        ubiTrade.setExpiresAt(LocalDateTime.of(2021, 1, 1, 0, 0));
        ubiTrade.setLastModifiedAt(LocalDateTime.of(2022, 1, 1, 0, 0));
        ubiTrade.setItem(new Item("itemId3"));
        ubiTrade.setSuccessPaymentPrice(1);
        ubiTrade.setSuccessPaymentFee(2);
        ubiTrade.setProposedPaymentPrice(3);
        ubiTrade.setProposedPaymentFee(4);

        ItemIdEntity itemIdEntity1 = new ItemIdEntity("itemId1");
        ItemIdEntity itemIdEntity2 = new ItemIdEntity("itemId2");

        List<ItemIdEntity> existingItems = List.of(itemIdEntity1, itemIdEntity2);

        assertThrows(ItemDoesntExistException.class, () -> ubiTradeEntityMapper.createEntity(ubiTrade, existingItems));
    }
}