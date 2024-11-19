package github.ricemonger.marketplace.databases.postgres.entities.item;

import github.ricemonger.utils.DTOs.items.Item;
import github.ricemonger.utils.enums.ItemRarity;
import github.ricemonger.utils.enums.ItemType;
import org.junit.jupiter.api.Test;

import java.util.*;

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
        item.setRarity(ItemRarity.EPIC);

        Set<TagEntity> tagsEntities = new HashSet<>();
        TagEntity tagEntity1 = new TagEntity();
        tagEntity1.setValue("tag1");
        tagsEntities.add(tagEntity1);
        TagEntity tagEntity2 = new TagEntity();
        tagEntity2.setValue("tag2");
        tagsEntities.add(tagEntity2);
        TagEntity tagEntity3 = new TagEntity();
        tagEntity3.setValue("tag3");
        tagsEntities.add(tagEntity3);

        ItemEntity expected = new ItemEntity();
        expected.setItemId("1");
        expected.setAssetUrl("2");
        expected.setName("3");
        expected.setTags(List.of(tagEntity1, tagEntity2));
        expected.setType(ItemType.WeaponSkin);
        expected.setMaxBuyPrice(5);
        expected.setBuyOrdersCount(6);
        expected.setMinSellPrice(7);
        expected.setSellOrdersCount(8);
        expected.setLastSoldAt(new Date(0));
        expected.setLastSoldPrice(9);
        expected.setRarity(ItemRarity.EPIC);

        ItemEntity actual = new ItemEntity(item, tagsEntities);

        assertTrue(entitiesAreEqual(expected, actual));
    }

    @Test
    public void toItemEntity_should_properly_map_with_invalid_tag() {
        Item item = new Item();
        item.setItemId("1");
        item.setAssetUrl("2");
        item.setName("3");
        item.setTags(List.of("tag1", "tag2", "tag4"));
        item.setType(ItemType.WeaponSkin);
        item.setMaxBuyPrice(5);
        item.setBuyOrdersCount(6);
        item.setMinSellPrice(7);
        item.setSellOrdersCount(8);
        item.setLastSoldAt(new Date(0));
        item.setLastSoldPrice(9);
        item.setRarity(ItemRarity.UNCOMMON);

        Set<TagEntity> tagsEntities = new HashSet<>();
        TagEntity tagEntity1 = new TagEntity();
        tagEntity1.setValue("tag1");
        tagsEntities.add(tagEntity1);
        TagEntity tagEntity2 = new TagEntity();
        tagEntity2.setValue("tag2");
        tagsEntities.add(tagEntity2);
        TagEntity tagEntity3 = new TagEntity();
        tagEntity3.setValue("tag3");
        tagsEntities.add(tagEntity3);

        ItemEntity expected = new ItemEntity();
        expected.setItemId("1");
        expected.setAssetUrl("2");
        expected.setName("3");
        expected.setTags(List.of(tagEntity1, tagEntity2));
        expected.setType(ItemType.WeaponSkin);
        expected.setMaxBuyPrice(5);
        expected.setBuyOrdersCount(6);
        expected.setMinSellPrice(7);
        expected.setSellOrdersCount(8);
        expected.setLastSoldAt(new Date(0));
        expected.setLastSoldPrice(9);
        expected.setRarity(ItemRarity.UNCOMMON);

        ItemEntity actual = new ItemEntity(item, tagsEntities);

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
        item.setRarity(ItemRarity.UNKNOWN);

        Set<TagEntity> tagsEntities = new HashSet<>();
        TagEntity tagEntity1 = new TagEntity();
        tagEntity1.setValue("tag1");
        tagsEntities.add(tagEntity1);
        TagEntity tagEntity2 = new TagEntity();
        tagEntity2.setValue("tag2");
        tagsEntities.add(tagEntity2);
        TagEntity tagEntity3 = new TagEntity();
        tagEntity3.setValue("tag3");
        tagsEntities.add(tagEntity3);

        ItemEntity expected = new ItemEntity();
        expected.setItemId("1");
        expected.setAssetUrl("2");
        expected.setName("3");
        expected.setTags(new ArrayList<>());
        expected.setType(ItemType.WeaponSkin);
        expected.setMaxBuyPrice(5);
        expected.setBuyOrdersCount(6);
        expected.setMinSellPrice(7);
        expected.setSellOrdersCount(8);
        expected.setLastSoldAt(new Date(0));
        expected.setLastSoldPrice(9);
        expected.setRarity(ItemRarity.UNKNOWN);

        ItemEntity actual = new ItemEntity(item, tagsEntities);

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

        Set<TagEntity> tagsEntities = new HashSet<>();
        TagEntity tagEntity1 = new TagEntity();
        tagEntity1.setValue("tag1");
        tagsEntities.add(tagEntity1);
        TagEntity tagEntity2 = new TagEntity();
        tagEntity2.setValue("tag2");
        tagsEntities.add(tagEntity2);
        TagEntity tagEntity3 = new TagEntity();
        tagEntity3.setValue("tag3");
        tagsEntities.add(tagEntity3);

        ItemEntity expected = new ItemEntity();
        expected.setItemId(null);
        expected.setAssetUrl(null);
        expected.setName(null);
        expected.setTags(new ArrayList<>());
        expected.setType(null);
        expected.setMaxBuyPrice(0);
        expected.setBuyOrdersCount(0);
        expected.setMinSellPrice(0);
        expected.setSellOrdersCount(0);
        expected.setLastSoldAt(null);
        expected.setLastSoldPrice(0);
        expected.setRarity(null);

        ItemEntity actual = new ItemEntity(item, tagsEntities);

        assertTrue(entitiesAreEqual(expected, actual));
    }

    @Test
    public void toItem_should_properly_map_with_valid_fields() {
        Set<TagEntity> tagsEntities = new HashSet<>();
        TagEntity tagEntity1 = new TagEntity();
        tagEntity1.setValue("tag1");
        tagsEntities.add(tagEntity1);
        TagEntity tagEntity2 = new TagEntity();
        tagEntity2.setValue("tag2");
        tagsEntities.add(tagEntity2);

        ItemEntity entity = new ItemEntity();
        entity.setItemId("1");
        entity.setAssetUrl("2");
        entity.setName("3");
        entity.setTags(tagsEntities.stream().toList());
        entity.setType(ItemType.WeaponSkin);
        entity.setMaxBuyPrice(5);
        entity.setBuyOrdersCount(6);
        entity.setMinSellPrice(7);
        entity.setSellOrdersCount(8);
        entity.setLastSoldAt(new Date(0));
        entity.setLastSoldPrice(9);
        entity.setRarity(ItemRarity.LEGENDARY);

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
        expected.setRarity(ItemRarity.LEGENDARY);

        Item actual = entity.toItem();

        assertEquals(expected, actual);
    }

    @Test
    public void toItem_should_properly_map_with_valid_fields_and_empty_tags() {
        ItemEntity entity = new ItemEntity();
        entity.setItemId("1");
        entity.setAssetUrl("2");
        entity.setName("3");
        entity.setTags(new ArrayList<>());
        entity.setType(ItemType.WeaponSkin);
        entity.setMaxBuyPrice(5);
        entity.setBuyOrdersCount(6);
        entity.setMinSellPrice(7);
        entity.setSellOrdersCount(8);
        entity.setLastSoldAt(new Date(0));
        entity.setLastSoldPrice(9);
        entity.setRarity(ItemRarity.RARE);

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
        expected.setRarity(ItemRarity.RARE);

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
        expected.setTags(List.of());
        expected.setType(null);
        expected.setMaxBuyPrice(0);
        expected.setBuyOrdersCount(0);
        expected.setMinSellPrice(0);
        expected.setSellOrdersCount(0);
        expected.setLastSoldAt(null);
        expected.setLastSoldPrice(0);
        expected.setRarity(null);

        Item actual = entity.toItem();

        assertEquals(expected, actual);
    }

    private boolean entitiesAreEqual(ItemEntity entity1, ItemEntity entity2) {

        List<String> entity1TagsValues = entity1.getTags().stream().map(TagEntity::getValue).toList();
        List<String> entity2TagsValues = entity2.getTags().stream().map(TagEntity::getValue).toList();

        boolean tagsEqual = entity1TagsValues.containsAll(entity2TagsValues) && entity2TagsValues.containsAll(entity1TagsValues);


        return Objects.equals(entity1.getItemId(), entity2.getItemId()) &&
               Objects.equals(entity1.getAssetUrl(), entity2.getAssetUrl()) &&
               Objects.equals(entity1.getName(), entity2.getName()) &&
               tagsEqual &&
               Objects.equals(entity1.getType(), entity2.getType()) &&
               entity1.getMaxBuyPrice() == entity2.getMaxBuyPrice() &&
               entity1.getBuyOrdersCount() == entity2.getBuyOrdersCount() &&
               entity1.getMinSellPrice() == entity2.getMinSellPrice() &&
               entity1.getSellOrdersCount() == entity2.getSellOrdersCount() &&
               Objects.equals(entity1.getLastSoldAt(), entity2.getLastSoldAt()) &&
               entity1.getLastSoldPrice() == entity2.getLastSoldPrice() &&
               entity1.getRarity() == entity2.getRarity();
    }
}