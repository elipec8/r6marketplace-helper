package github.ricemonger.item_stats_fetcher.databases.postgres.repositories;

import github.ricemonger.item_stats_fetcher.databases.postgres.entities.ItemMainFieldsEntity;
import github.ricemonger.item_stats_fetcher.databases.postgres.entities.TagEntity;
import github.ricemonger.utils.DTOs.common.ItemMainFieldsI;
import github.ricemonger.utils.DTOs.common.ItemMinSellPrice;
import github.ricemonger.utils.enums.ItemRarity;
import github.ricemonger.utils.enums.ItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ItemMainFieldsPostgresRepositoryTest {

    @SpyBean
    private ItemMainFieldsPostgresRepository itemPostgresRepository;

    @BeforeEach
    void setUp() {
        itemPostgresRepository.deleteAll();
    }

    @Test
    public void updateAllExceptTagsFields_should_update_all_items() {
        ItemMainFieldsEntity existingItem1 = new ItemMainFieldsEntity("itemId1", "assetUrl1", "name1", List.of(new TagEntity("tag1"), new TagEntity("tag2")),
                ItemRarity.RARE, ItemType.WeaponSkin, 101, 102, 103, 104, LocalDateTime.of(2021, 1, 1, 1, 1), 105);

        ItemMainFieldsEntity existingItem2 = new ItemMainFieldsEntity("itemId2", "assetUrl2", "name2", List.of(new TagEntity("tag3"), new TagEntity("tag4")),
                ItemRarity.LEGENDARY, ItemType.CharacterHeadgear, 201, 202, 203, 204, LocalDateTime.of(2022, 1, 1, 1, 1), 205);

        ItemMainFieldsEntity existingItem3 = new ItemMainFieldsEntity("itemId3", "assetUrl3", "name3", List.of(), ItemRarity.EPIC, ItemType.Charm,
                301, 302, 303, 304, LocalDateTime.of(2023, 1, 1, 1, 1), 305);

        itemPostgresRepository.save(existingItem1);
        itemPostgresRepository.save(existingItem2);
        itemPostgresRepository.save(existingItem3);

        ItemMainFieldsEntity savedItem1 = new ItemMainFieldsEntity("itemId1", "newAssetUrl1", "newName1", List.of(),
                ItemRarity.UNCOMMON, ItemType.Charm, 111, 112, 113, 114, LocalDateTime.of(2021, 10, 1, 1, 1), 115);

        ItemMainFieldsEntity savedItem2 = new ItemMainFieldsEntity("itemId2", "newAssetUrl2", "newName2", List.of(new TagEntity("newTag3"),
                new TagEntity("newTag4")),
                ItemRarity.LEGENDARY, ItemType.CharacterHeadgear, 211, 212, 213, 214, LocalDateTime.of(2022, 10, 1, 1, 1), 215);

        ItemMainFieldsEntity savedItem3 = new ItemMainFieldsEntity("itemId3", "newAssetUrl3", "newName3", List.of(new TagEntity("tag5"), new TagEntity("tag6")),
                ItemRarity.RARE, ItemType.WeaponSkin, 311, 312, 313, 314, LocalDateTime.of(2023, 10, 1, 1, 1), 315);

        itemPostgresRepository.updateAllExceptTagsFields(List.of(savedItem1, savedItem2, savedItem3));

        ItemMainFieldsEntity expectedItem1 = new ItemMainFieldsEntity("itemId1", "newAssetUrl1", "newName1", List.of(new TagEntity("tag1"), new TagEntity("tag2")),
                ItemRarity.UNCOMMON, ItemType.Charm, 111, 112, 113, 114, LocalDateTime.of(2021, 10, 1, 1, 1), 115);

        ItemMainFieldsEntity expectedItem2 = new ItemMainFieldsEntity("itemId2", "newAssetUrl2", "newName2", List.of(new TagEntity("tag3"), new TagEntity("tag4")),
                ItemRarity.LEGENDARY, ItemType.CharacterHeadgear, 211, 212, 213, 214, LocalDateTime.of(2022, 10, 1, 1, 1), 215);

        ItemMainFieldsEntity expectedItem3 = new ItemMainFieldsEntity("itemId3", "newAssetUrl3", "newName3", List.of(),
                ItemRarity.RARE, ItemType.WeaponSkin, 311, 312, 313, 314, LocalDateTime.of(2023, 10, 1, 1, 1), 315);

        List<ItemMainFieldsEntity> result = itemPostgresRepository.findAll();

        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(item -> item.isFullyEqual(expectedItem1)));
        assertTrue(result.stream().anyMatch(item -> item.isFullyEqual(expectedItem2)));
        assertTrue(result.stream().anyMatch(item -> item.isFullyEqual(expectedItem3)));
    }


    @Test
    public void updateAllItemsMinSellPrice_should_update_all_items_min_sell_price() {
        ItemMainFieldsEntity item1 = new ItemMainFieldsEntity();
        item1.setItemId("itemId1");
        item1.setMinSellPrice(100);
        ItemMainFieldsEntity item2 = new ItemMainFieldsEntity();
        item2.setItemId("itemId2");
        item2.setMinSellPrice(200);
        ItemMainFieldsEntity item3 = new ItemMainFieldsEntity();
        item3.setItemId("itemId3");
        item3.setMinSellPrice(300);
        itemPostgresRepository.save(item1);
        itemPostgresRepository.save(item2);
        itemPostgresRepository.save(item3);

        List<ItemMinSellPrice> minSellPrices = List.of(
                new ItemMinSellPrice("itemId1", 150),
                new ItemMinSellPrice("itemId2", 250),
                new ItemMinSellPrice("itemId3", 350)
        );

        itemPostgresRepository.updateAllItemsMinSellPrice(minSellPrices);

        assertEquals(150, itemPostgresRepository.findById("itemId1").get().getMinSellPrice());
        assertEquals(250, itemPostgresRepository.findById("itemId2").get().getMinSellPrice());
        assertEquals(350, itemPostgresRepository.findById("itemId3").get().getMinSellPrice());
    }

    @Test
    public void findAllItemIds_should_return_all_item_ids() {
        ItemMainFieldsEntity item1 = new ItemMainFieldsEntity();
        item1.setItemId("itemId1");
        ItemMainFieldsEntity item2 = new ItemMainFieldsEntity();
        item2.setItemId("itemId4");
        ItemMainFieldsEntity item3 = new ItemMainFieldsEntity();
        item3.setItemId("itemId7");
        itemPostgresRepository.save(item1);
        itemPostgresRepository.save(item2);
        itemPostgresRepository.save(item3);

        Set<String> itemIds = itemPostgresRepository.findAllItemIds();

        assertEquals(3, itemIds.size());
        assertTrue(itemIds.contains("itemId1"));
        assertTrue(itemIds.contains("itemId4"));
        assertTrue(itemIds.contains("itemId7"));
    }
}