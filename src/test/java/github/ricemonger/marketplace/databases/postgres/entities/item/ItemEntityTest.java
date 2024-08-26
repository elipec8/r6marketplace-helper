package github.ricemonger.marketplace.databases.postgres.entities.item;

import github.ricemonger.utils.dtos.Item;
import github.ricemonger.utils.enums.ItemType;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemEntityTest {

    @Test
    public void toItemEntity_should_properly_map_with_valid_fields() {
        Item item = new Item();
        item.setItemId("1");
        item.setAssetUrl("2");
        item.setName("3");
        item.setTags(List.of("tag1", "tag2"));
        item.setType(ItemType.WeaponSkin);
        item.setMaxBuyPrice(5);
        item.setBuyOrdersCount(6);
        item.setMinSellPrice(7);
        item.setSellOrdersCount(8);
        item.setLastSoldAt(new Date(0));
        item.setLastSoldPrice(9);
        item.setLimitMinPrice(10);
        item.setLimitMaxPrice(11);

        ItemEntity expected = new ItemEntity();
        expected.setItemId("1");
        expected.setAssetUrl("2");
        expected.setName("3");
        expected.setTags("tag1,tag2");
        expected.setType(ItemType.WeaponSkin);
        expected.setMaxBuyPrice(5);
        expected.setBuyOrdersCount(6);
        expected.setMinSellPrice(7);
        expected.setSellOrdersCount(8);
        expected.setLastSoldAt(new Date(0));
        expected.setLastSoldPrice(9);
        expected.setLimitMinPrice(10);
        expected.setLimitMaxPrice(11);

        ItemEntity actual = new ItemEntity(item);

        assertTrue(entitiesAreEqual(expected, actual));
    }

    @Test
    public void toItemEntity_should_properly_map_with_valid_fields_and_empty_tags() {
        Item item = new Item();
        item.setItemId("1");
        item.setAssetUrl("2");
        item.setName("3");
        item.setTags(List.of());
        item.setType(ItemType.WeaponSkin);
        item.setMaxBuyPrice(5);
        item.setBuyOrdersCount(6);
        item.setMinSellPrice(7);
        item.setSellOrdersCount(8);
        item.setLastSoldAt(new Date(0));
        item.setLastSoldPrice(9);
        item.setLimitMinPrice(10);
        item.setLimitMaxPrice(11);

        ItemEntity expected = new ItemEntity();
        expected.setItemId("1");
        expected.setAssetUrl("2");
        expected.setName("3");
        expected.setTags("");
        expected.setType(ItemType.WeaponSkin);
        expected.setMaxBuyPrice(5);
        expected.setBuyOrdersCount(6);
        expected.setMinSellPrice(7);
        expected.setSellOrdersCount(8);
        expected.setLastSoldAt(new Date(0));
        expected.setLastSoldPrice(9);
        expected.setLimitMinPrice(10);
        expected.setLimitMaxPrice(11);

        ItemEntity actual = new ItemEntity(item);

        assertTrue(entitiesAreEqual(expected, actual));
    }

    @Test
    public void toItemEntity_should_properly_map_with_null_fields() {
        Item item = new Item();
        item.setItemId(null);
        item.setAssetUrl(null);
        item.setName(null);
        item.setTags(null);
        item.setType(null);
        item.setLastSoldAt(null);

        ItemEntity expected = new ItemEntity();
        expected.setItemId(null);
        expected.setAssetUrl(null);
        expected.setName(null);
        expected.setTags(null);
        expected.setType(null);
        expected.setMaxBuyPrice(0);
        expected.setBuyOrdersCount(0);
        expected.setMinSellPrice(0);
        expected.setSellOrdersCount(0);
        expected.setLastSoldAt(null);
        expected.setLastSoldPrice(0);
        expected.setLimitMinPrice(0);
        expected.setLimitMaxPrice(0);

        ItemEntity actual = new ItemEntity(item);

        assertTrue(entitiesAreEqual(expected, actual));
    }

    @Test
    public void toItem_should_properly_map_with_valid_fields() {
        ItemEntity entity = new ItemEntity();
        entity.setItemId("1");
        entity.setAssetUrl("2");
        entity.setName("3");
        entity.setTags("tag1,tag2");
        entity.setType(ItemType.WeaponSkin);
        entity.setMaxBuyPrice(5);
        entity.setBuyOrdersCount(6);
        entity.setMinSellPrice(7);
        entity.setSellOrdersCount(8);
        entity.setLastSoldAt(new Date(0));
        entity.setLastSoldPrice(9);
        entity.setLimitMinPrice(10);
        entity.setLimitMaxPrice(11);

        Item expected = new Item();
        expected.setItemId("1");
        expected.setAssetUrl("2");
        expected.setName("3");
        expected.setTags(List.of("tag1", "tag2"));
        expected.setType(ItemType.WeaponSkin);
        expected.setMaxBuyPrice(5);
        expected.setBuyOrdersCount(6);
        expected.setMinSellPrice(7);
        expected.setSellOrdersCount(8);
        expected.setLastSoldAt(new Date(0));
        expected.setLastSoldPrice(9);
        expected.setLimitMinPrice(10);
        expected.setLimitMaxPrice(11);

        Item actual = entity.toItem();

        assertEquals(expected, actual);
    }

    @Test
    public void toItem_should_properly_map_with_valid_fields_and_empty_tags() {
        ItemEntity entity = new ItemEntity();
        entity.setItemId("1");
        entity.setAssetUrl("2");
        entity.setName("3");
        entity.setTags("");
        entity.setType(ItemType.WeaponSkin);
        entity.setMaxBuyPrice(5);
        entity.setBuyOrdersCount(6);
        entity.setMinSellPrice(7);
        entity.setSellOrdersCount(8);
        entity.setLastSoldAt(new Date(0));
        entity.setLastSoldPrice(9);
        entity.setLimitMinPrice(10);
        entity.setLimitMaxPrice(11);

        Item expected = new Item();
        expected.setItemId("1");
        expected.setAssetUrl("2");
        expected.setName("3");
        expected.setTags(List.of());
        expected.setType(ItemType.WeaponSkin);
        expected.setMaxBuyPrice(5);
        expected.setBuyOrdersCount(6);
        expected.setMinSellPrice(7);
        expected.setSellOrdersCount(8);
        expected.setLastSoldAt(new Date(0));
        expected.setLastSoldPrice(9);
        expected.setLimitMinPrice(10);
        expected.setLimitMaxPrice(11);

        Item actual = entity.toItem();

        assertEquals(expected, actual);
    }

    @Test
    public void toItem_should_properly_map_with_null_fields() {
        ItemEntity entity = new ItemEntity();
        entity.setItemId(null);
        entity.setAssetUrl(null);
        entity.setName(null);
        entity.setTags(null);
        entity.setType(null);
        entity.setLastSoldAt(null);

        Item expected = new Item();
        expected.setItemId(null);
        expected.setAssetUrl(null);
        expected.setName(null);
        expected.setTags(null);
        expected.setType(null);
        expected.setMaxBuyPrice(0);
        expected.setBuyOrdersCount(0);
        expected.setMinSellPrice(0);
        expected.setSellOrdersCount(0);
        expected.setLastSoldAt(null);
        expected.setLastSoldPrice(0);
        expected.setLimitMinPrice(0);
        expected.setLimitMaxPrice(0);

        Item actual = entity.toItem();

        assertEquals(expected, actual);
    }

    private boolean entitiesAreEqual(ItemEntity entity1, ItemEntity entity2) {
        return Objects.equals(entity1.getItemId(), entity2.getItemId()) &&
               Objects.equals(entity1.getAssetUrl(), entity2.getAssetUrl()) &&
               Objects.equals(entity1.getName(), entity2.getName()) &&
               Objects.equals(entity1.getTags(), entity2.getTags()) &&
               Objects.equals(entity1.getType(), entity2.getType()) &&
               entity1.getMaxBuyPrice() == entity2.getMaxBuyPrice() &&
               entity1.getBuyOrdersCount() == entity2.getBuyOrdersCount() &&
               entity1.getMinSellPrice() == entity2.getMinSellPrice() &&
               entity1.getSellOrdersCount() == entity2.getSellOrdersCount() &&
               Objects.equals(entity1.getLastSoldAt(), entity2.getLastSoldAt()) &&
               entity1.getLastSoldPrice() == entity2.getLastSoldPrice() &&
               entity1.getLimitMinPrice() == entity2.getLimitMinPrice() &&
               entity1.getLimitMaxPrice() == entity2.getLimitMaxPrice();
    }
}