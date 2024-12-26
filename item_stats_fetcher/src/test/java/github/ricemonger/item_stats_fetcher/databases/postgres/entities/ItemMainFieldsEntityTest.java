package github.ricemonger.item_stats_fetcher.databases.postgres.entities;

import github.ricemonger.utils.enums.ItemRarity;
import github.ricemonger.utils.enums.ItemType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemMainFieldsEntityTest {

    @Test
    public void isEqual_should_return_true_if_same() {
        ItemMainFieldsEntity entity = new ItemMainFieldsEntity();
        assertTrue(entity.isEqual(entity));
    }

    @Test
    public void isEqual_should_return_true_if_equal_id() {
        ItemMainFieldsEntity entity1 = new ItemMainFieldsEntity();
        entity1.setItemId("itemId");
        entity1.setAssetUrl("url");
        entity1.setName("name");
        entity1.setTags(Collections.emptyList());
        entity1.setRarity(ItemRarity.RARE);
        entity1.setType(ItemType.WeaponSkin);
        entity1.setMaxBuyPrice(100);
        entity1.setBuyOrdersCount(10);
        entity1.setMinSellPrice(200);
        entity1.setSellOrdersCount(20);
        entity1.setLastSoldAt(LocalDateTime.of(2021, 1, 1, 0, 0));
        entity1.setLastSoldPrice(150);

        ItemMainFieldsEntity entity2 = new ItemMainFieldsEntity();
        entity2.setItemId("itemId");

        assertTrue(entity1.isEqual(entity2));
    }

    @Test
    public void isEqual_should_return_false_for_null() {
        ItemMainFieldsEntity entity = new ItemMainFieldsEntity();
        assertFalse(entity.isEqual(null));
    }


    @Test
    public void isFullyEqual_should_return_true_if_same() {
        ItemMainFieldsEntity entity = new ItemMainFieldsEntity();
        assertTrue(entity.isFullyEqual(entity));
    }

    @Test
    public void isFullyEqual_should_return_true_if_equal() {
        ItemMainFieldsEntity entity1 = new ItemMainFieldsEntity();
        entity1.setItemId("itemId");
        entity1.setAssetUrl("url");
        entity1.setName("name");
        entity1.setTags(Collections.emptyList());
        entity1.setRarity(ItemRarity.RARE);
        entity1.setType(ItemType.WeaponSkin);
        entity1.setMaxBuyPrice(100);
        entity1.setBuyOrdersCount(10);
        entity1.setMinSellPrice(200);
        entity1.setSellOrdersCount(20);
        entity1.setLastSoldAt(LocalDateTime.of(2021, 1, 1, 0, 0));
        entity1.setLastSoldPrice(150);

        ItemMainFieldsEntity entity2 = new ItemMainFieldsEntity();
        entity2.setItemId("itemId");
        entity2.setAssetUrl("url");
        entity2.setName("name");
        entity2.setTags(Collections.emptyList());
        entity2.setRarity(ItemRarity.RARE);
        entity2.setType(ItemType.WeaponSkin);
        entity2.setMaxBuyPrice(100);
        entity2.setBuyOrdersCount(10);
        entity2.setMinSellPrice(200);
        entity2.setSellOrdersCount(20);
        entity2.setLastSoldAt(LocalDateTime.of(2021, 1, 1, 0, 0));
        entity2.setLastSoldPrice(150);

        assertTrue(entity1.isFullyEqual(entity2));
    }

    @Test
    public void isFullyEqual_should_return_false_if_not_equal() {
        ItemMainFieldsEntity entity1 = new ItemMainFieldsEntity();
        entity1.setItemId("itemId1");
        entity1.setAssetUrl("url1");
        entity1.setName("name1");
        entity1.setTags(Collections.emptyList());
        entity1.setRarity(ItemRarity.RARE);
        entity1.setType(ItemType.WeaponSkin);
        entity1.setMaxBuyPrice(100);
        entity1.setBuyOrdersCount(10);
        entity1.setMinSellPrice(200);
        entity1.setSellOrdersCount(20);
        entity1.setLastSoldAt(LocalDateTime.of(2021, 1, 1, 0, 0));
        entity1.setLastSoldPrice(150);

        ItemMainFieldsEntity entity2 = new ItemMainFieldsEntity();
        entity2.setItemId("itemId");
        entity2.setAssetUrl("url");
        entity2.setName("name");
        entity2.setTags(Collections.emptyList());
        entity2.setRarity(ItemRarity.RARE);
        entity2.setType(ItemType.WeaponSkin);
        entity2.setMaxBuyPrice(100);
        entity2.setBuyOrdersCount(10);
        entity2.setMinSellPrice(200);
        entity2.setSellOrdersCount(20);
        entity2.setLastSoldAt(LocalDateTime.of(2021, 1, 1, 0, 0));
        entity2.setLastSoldPrice(150);

        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setItemId("itemId");
        entity1.setAssetUrl("url1");
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setAssetUrl("url");
        entity1.setName("name1");
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setName("name");
        entity1.setTags(null);
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setTags(List.of(new TagValueEntity()));
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setTags(Collections.emptyList());
        entity1.setRarity(ItemRarity.UNCOMMON);
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setRarity(ItemRarity.RARE);
        entity1.setType(ItemType.WeaponAttachmentSkinSet);
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setType(ItemType.WeaponSkin);
        entity1.setMaxBuyPrice(101);
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setMaxBuyPrice(100);
        entity1.setBuyOrdersCount(11);
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setBuyOrdersCount(10);
        entity1.setMinSellPrice(201);
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setMinSellPrice(200);
        entity1.setSellOrdersCount(21);
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setSellOrdersCount(20);
        entity1.setLastSoldAt(LocalDateTime.of(2021, 1, 2, 0, 0));
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setLastSoldAt(LocalDateTime.of(2021, 1, 1, 0, 0));
        entity1.setLastSoldPrice(151);
        assertFalse(entity1.isFullyEqual(entity2));
    }
}