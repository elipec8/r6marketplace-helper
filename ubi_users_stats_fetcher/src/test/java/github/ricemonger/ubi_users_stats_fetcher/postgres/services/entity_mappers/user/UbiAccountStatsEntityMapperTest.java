package github.ricemonger.ubi_users_stats_fetcher.postgres.services.entity_mappers.user;

import github.ricemonger.ubi_users_stats_fetcher.postgres.entities.ubi_account_stats.ItemIdEntity;
import github.ricemonger.ubi_users_stats_fetcher.postgres.entities.ubi_account_stats.ItemResaleLockEntity;
import github.ricemonger.ubi_users_stats_fetcher.postgres.entities.ubi_account_stats.UbiAccountStatsEntity;
import github.ricemonger.ubi_users_stats_fetcher.postgres.entities.ubi_account_stats.UbiTradeEntity;
import github.ricemonger.ubi_users_stats_fetcher.postgres.repositories.ItemIdPostgresRepository;
import github.ricemonger.ubi_users_stats_fetcher.services.DTOs.UbiAccountStats;
import github.ricemonger.utils.DTOs.personal.ItemResaleLock;
import github.ricemonger.utils.DTOs.personal.UbiTrade;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class UbiAccountStatsEntityMapperTest {
    @Autowired
    private UbiAccountStatsEntityMapper ubiAccountStatsEntityMapper;
    @MockBean
    private ItemIdPostgresRepository itemIdPostgresRepository;
    @MockBean
    private UbiTradeEntityMapper ubiTradeEntityMapper;

    @Test
    public void createEntities_should_return_expected_result() {
        List<ItemIdEntity> existingItems = List.of(new ItemIdEntity("itemId1"), new ItemIdEntity("itemId2"));
        when(itemIdPostgresRepository.findAll()).thenReturn(existingItems);

        UbiTrade currentSellTrade11 = Mockito.mock(UbiTrade.class);
        UbiTrade currentSellTrade12 = Mockito.mock(UbiTrade.class);

        UbiTrade currentBuyTrade11 = Mockito.mock(UbiTrade.class);

        UbiTradeEntity currentSellTradeEntity11 = new UbiTradeEntity();
        currentSellTradeEntity11.setTradeId("currentSellTradeEntity11");

        UbiTradeEntity currentSellTradeEntity12 = new UbiTradeEntity();
        currentSellTradeEntity12.setTradeId("currentSellTradeEntity12");

        UbiTradeEntity currentBuyTradeEntity11 = new UbiTradeEntity();
        currentBuyTradeEntity11.setTradeId("currentBuyTradeEntity11");

        when(ubiTradeEntityMapper.createEntity(currentSellTrade11, existingItems)).thenReturn(currentSellTradeEntity11);
        when(ubiTradeEntityMapper.createEntity(currentSellTrade12, existingItems)).thenReturn(currentSellTradeEntity12);
        when(ubiTradeEntityMapper.createEntity(currentBuyTrade11, existingItems)).thenReturn(currentBuyTradeEntity11);

        UbiAccountStats dto1 = new UbiAccountStats();
        dto1.setUbiProfileId("ubiProfileId1");
        dto1.setCreditAmount(100);
        dto1.setSoldIn24h(11);
        dto1.setBoughtIn24h(12);
        dto1.setOwnedItemsIds(List.of("itemId1", "itemId2", "itemId3"));
        ItemResaleLock resaleLock11 = new ItemResaleLock("itemId3", LocalDateTime.of(2021, 1, 1, 0, 0));
        ItemResaleLock resaleLock12 = new ItemResaleLock("itemId4", LocalDateTime.of(2021, 2, 1, 0, 0));
        dto1.setResaleLocks(List.of(resaleLock11, resaleLock12));
        dto1.setCurrentSellTrades(List.of(currentSellTrade11, currentSellTrade12));
        dto1.setCurrentBuyTrades(List.of(currentBuyTrade11));

        UbiAccountStatsEntity entity1 = new UbiAccountStatsEntity();
        entity1.setUbiProfileId("ubiProfileId1");
        entity1.setCreditAmount(100);
        entity1.setSoldIn24h(11);
        entity1.setBoughtIn24h(12);
        entity1.setOwnedItems(List.of(existingItems.get(0), existingItems.get(1)));
        entity1.setResaleLocks(List.of());
        entity1.setCurrentSellTrades(List.of(currentSellTradeEntity11, currentSellTradeEntity12));
        entity1.setCurrentBuyTrades(List.of(currentBuyTradeEntity11));

        UbiTrade currentSellTrade21 = Mockito.mock(UbiTrade.class);

        UbiTrade currentBuyTrade21 = Mockito.mock(UbiTrade.class);
        UbiTrade currentBuyTrade22 = Mockito.mock(UbiTrade.class);

        UbiTradeEntity currentSellTradeEntity21 = new UbiTradeEntity();
        currentSellTradeEntity21.setTradeId("currentSellTradeEntity21");

        UbiTradeEntity currentBuyTradeEntity21 = new UbiTradeEntity();
        currentBuyTradeEntity21.setTradeId("currentBuyTradeEntity21");

        UbiTradeEntity currentBuyTradeEntity22 = new UbiTradeEntity();
        currentBuyTradeEntity22.setTradeId("currentBuyTradeEntity22");

        when(ubiTradeEntityMapper.createEntity(currentSellTrade21, existingItems)).thenReturn(currentSellTradeEntity21);
        when(ubiTradeEntityMapper.createEntity(currentBuyTrade21, existingItems)).thenReturn(currentBuyTradeEntity21);
        when(ubiTradeEntityMapper.createEntity(currentBuyTrade22, existingItems)).thenReturn(currentBuyTradeEntity22);

        UbiAccountStats dto2 = new UbiAccountStats();
        dto2.setUbiProfileId("ubiProfileId2");
        dto2.setCreditAmount(200);
        dto2.setSoldIn24h(21);
        dto2.setBoughtIn24h(22);
        dto2.setOwnedItemsIds(List.of("itemId3", "itemId4"));
        ItemResaleLock resaleLock21 = new ItemResaleLock("itemId1", LocalDateTime.of(2022, 1, 1, 0, 0));
        ItemResaleLock resaleLock22 = new ItemResaleLock("itemId2", LocalDateTime.of(2022, 2, 1, 0, 0));
        dto2.setResaleLocks(List.of(resaleLock21, resaleLock22));
        dto2.setCurrentSellTrades(List.of(currentSellTrade21));
        dto2.setCurrentBuyTrades(List.of(currentBuyTrade21, currentBuyTrade22));

        UbiAccountStatsEntity entity2 = new UbiAccountStatsEntity();
        entity2.setUbiProfileId("ubiProfileId2");
        entity2.setCreditAmount(200);
        entity2.setSoldIn24h(21);
        entity2.setBoughtIn24h(22);
        ItemResaleLockEntity resaleLockEntity21 = new ItemResaleLockEntity(entity2, existingItems.get(0), LocalDateTime.of(2022, 1, 1, 0, 0));
        ItemResaleLockEntity resaleLockEntity22 = new ItemResaleLockEntity(entity2, existingItems.get(1), LocalDateTime.of(2022, 2, 1, 0, 0));
        entity2.setOwnedItems(List.of());
        entity2.setResaleLocks(List.of(resaleLockEntity21, resaleLockEntity22));
        entity2.setCurrentSellTrades(List.of(currentSellTradeEntity21));
        entity2.setCurrentBuyTrades(List.of(currentBuyTradeEntity21, currentBuyTradeEntity22));

        List<UbiAccountStats> ubiAccounts = List.of(dto1, dto2);

        List<UbiAccountStatsEntity> result = ubiAccountStatsEntityMapper.createEntities(ubiAccounts);

        System.out.println("Expected: ");
        System.out.println(entity1);
        System.out.println(entity2);

        System.out.println("Result: ");
        result.forEach(System.out::println);

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(entity -> entity.isFullyEqual(entity1)));
        assertTrue(result.stream().anyMatch(entity -> entity.isFullyEqual(entity2)));
    }
}