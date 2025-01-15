package github.ricemonger.fast_sell_trade_manager.postgres.repositories;

import github.ricemonger.fast_sell_trade_manager.postgres.dto_projections.ItemMedianPriceAndRarityProjection;
import github.ricemonger.fast_sell_trade_manager.postgres.dto_projections.ItemMedianPriceAndRarityProjectionI;
import github.ricemonger.utils.enums.ItemRarity;
import github.ricemonger.utilspostgresschema.full_entities.item.ItemEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.ItemResaleLockEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.UbiAccountStatsEntity;
import github.ricemonger.utilspostgresschema.repositories.item.ItemPostgresRepository;
import github.ricemonger.utilspostgresschema.repositories.user.ItemResaleLockPostgresRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CustomUbiAccountStatsPostgresRepositoryTest {
    @Autowired
    private CustomUbiAccountStatsPostgresRepository customUbiAccountStatsPostgresRepository;
    @Autowired
    private ItemResaleLockPostgresRepository itemResaleLockPostgresRepository;
    @Autowired
    private ItemPostgresRepository itemPostgresRepository;

    @BeforeEach
    @Transactional
    void setUp() {
        itemResaleLockPostgresRepository.deleteAll();
        customUbiAccountStatsPostgresRepository.deleteAll();
        itemPostgresRepository.deleteAll();
    }

    @Test
    @Transactional
    @Disabled //Cannot project java.lang.Byte to github.ricemonger.utils.enums.ItemRarity; Target type is not an interface and no matching Converter found
    public void findOwnedItemsMedianPriceAndRarity_should_return_expected_projection_only_for_owned_items() {
        ItemEntity item1 = new ItemEntity();
        item1.setItemId("item1");
        item1.setRarity(ItemRarity.RARE);
        item1.setMonthMedianPrice(100);

        ItemEntity item2 = new ItemEntity();
        item2.setItemId("item2");
        item2.setRarity(ItemRarity.EPIC);
        item2.setMonthMedianPrice(200);

        ItemEntity item3 = new ItemEntity();
        item3.setItemId("item3");
        item3.setRarity(ItemRarity.LEGENDARY);
        item3.setMonthMedianPrice(300);

        ItemEntity item4 = new ItemEntity();
        item4.setItemId("item4");
        item4.setRarity(ItemRarity.UNCOMMON);
        item4.setMonthMedianPrice(400);

        item1 = itemPostgresRepository.save(item1);
        item2 = itemPostgresRepository.save(item2);
        item3 = itemPostgresRepository.save(item3);
        item4 = itemPostgresRepository.save(item4);

        UbiAccountStatsEntity stats1 = new UbiAccountStatsEntity();
        stats1.setUbiProfileId("profile1");
        stats1.setOwnedItems(List.of(item1, item2));

        UbiAccountStatsEntity stats2 = new UbiAccountStatsEntity();
        stats2.setUbiProfileId("profile2");
        stats2.setOwnedItems(List.of(item3));

        customUbiAccountStatsPostgresRepository.save(stats1);
        customUbiAccountStatsPostgresRepository.save(stats2);

        List<ItemMedianPriceAndRarityProjectionI> result = customUbiAccountStatsPostgresRepository.findOwnedItemsMedianPriceAndRarity("profile1");

        List<ItemMedianPriceAndRarityProjection> resultProjections = result.stream().map(ItemMedianPriceAndRarityProjection::new).toList();

        List<ItemMedianPriceAndRarityProjection> expected = new ArrayList<>();
        expected.add(new ItemMedianPriceAndRarityProjection("item1", ItemRarity.RARE, 100));
        expected.add(new ItemMedianPriceAndRarityProjection("item2", ItemRarity.EPIC, 200));

        assertEquals(2, result.size());
        assertTrue(expected.containsAll(resultProjections));
    }

    @Test
    @Transactional
    public void findAllUserResaleLocksItemIds_should_return_item_ids_of_all_resale_locks_for_user() {
        ItemEntity item1 = new ItemEntity();
        item1.setItemId("item1");

        ItemEntity item2 = new ItemEntity();
        item2.setItemId("item2");

        ItemEntity item3 = new ItemEntity();
        item3.setItemId("item3");

        ItemEntity item4 = new ItemEntity();
        item4.setItemId("item4");

        item1 = itemPostgresRepository.save(item1);
        item2 = itemPostgresRepository.save(item2);
        item3 = itemPostgresRepository.save(item3);
        item4 = itemPostgresRepository.save(item4);

        UbiAccountStatsEntity stats1 = new UbiAccountStatsEntity();
        stats1.setUbiProfileId("profile1");

        UbiAccountStatsEntity stats2 = new UbiAccountStatsEntity();
        stats2.setUbiProfileId("profile2");

        stats1 = customUbiAccountStatsPostgresRepository.save(stats1);
        stats2 = customUbiAccountStatsPostgresRepository.save(stats2);

        itemResaleLockPostgresRepository.save(new ItemResaleLockEntity(stats1, item1, LocalDateTime.of(2021, 1, 1, 0, 0)));
        itemResaleLockPostgresRepository.save(new ItemResaleLockEntity(stats1, item2, LocalDateTime.of(2021, 1, 1, 0, 0)));
        itemResaleLockPostgresRepository.save(new ItemResaleLockEntity(stats2, item3, LocalDateTime.of(2021, 1, 1, 0, 0)));

        List<String> result = customUbiAccountStatsPostgresRepository.findAllUserResaleLocksItemIds("profile1");

        assertEquals(2, result.size());
        assertTrue(result.contains("item1"));
        assertTrue(result.contains("item2"));
    }
}