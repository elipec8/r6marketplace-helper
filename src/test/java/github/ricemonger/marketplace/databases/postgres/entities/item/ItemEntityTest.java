package github.ricemonger.marketplace.databases.postgres.entities.item;

import github.ricemonger.utils.DTOs.items.Item;
import github.ricemonger.utils.enums.ItemRarity;
import github.ricemonger.utils.enums.ItemType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;

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
        item.setLastSoldAt(LocalDateTime.MIN);
        item.setLastSoldPrice(9);
        item.setRarity(ItemRarity.EPIC);
        item.setMonthAveragePrice(10);
        item.setMonthMedianPrice(11);
        item.setMonthMaxPrice(12);
        item.setMonthMinPrice(13);
        item.setMonthSalesPerDay(14);
        item.setDayAveragePrice(15);
        item.setDayMedianPrice(16);
        item.setDayMaxPrice(17);
        item.setDayMinPrice(18);
        item.setDaySales(19);
        item.setPriceToSellIn1Hour(20);
        item.setPriceToSellIn6Hours(21);
        item.setPriceToSellIn24Hours(22);
        item.setPriceToSellIn168Hours(23);
        item.setPriceToBuyIn1Hour(24);
        item.setPriceToBuyIn6Hours(25);
        item.setPriceToBuyIn24Hours(26);
        item.setPriceToBuyIn168Hours(27);

        Set<TagEntity> tagsEntities = new HashSet<>();
        TagEntity tagEntity1 = new TagEntity();
        tagEntity1.setValue("tag1");
        tagsEntities.add(tagEntity1);
        TagEntity tagEntity2 = new TagEntity();
        tagEntity2.setValue("tag2");
        tagsEntities.add(tagEntity2);

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
        expected.setLastSoldAt(LocalDateTime.MIN);
        expected.setLastSoldPrice(9);
        expected.setRarity(ItemRarity.EPIC);
        expected.setMonthAveragePrice(10);
        expected.setMonthMedianPrice(11);
        expected.setMonthMaxPrice(12);
        expected.setMonthMinPrice(13);
        expected.setMonthSalesPerDay(14);
        expected.setDayAveragePrice(15);
        expected.setDayMedianPrice(16);
        expected.setDayMaxPrice(17);
        expected.setDayMinPrice(18);
        expected.setDaySales(19);
        expected.setPriceToSellIn1Hour(20);
        expected.setPriceToSellIn6Hours(21);
        expected.setPriceToSellIn24Hours(22);
        expected.setPriceToSellIn168Hours(23);
        expected.setPriceToBuyIn1Hour(24);
        expected.setPriceToBuyIn6Hours(25);
        expected.setPriceToBuyIn24Hours(26);
        expected.setPriceToBuyIn168Hours(27);

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
        item.setType(ItemType.CharacterHeadgear);
        item.setMaxBuyPrice(5);
        item.setBuyOrdersCount(6);
        item.setMinSellPrice(7);
        item.setSellOrdersCount(8);
        item.setLastSoldAt(LocalDateTime.MAX);
        item.setLastSoldPrice(9);
        item.setRarity(ItemRarity.UNCOMMON);
        item.setMonthAveragePrice(10);
        item.setMonthMedianPrice(11);
        item.setMonthMaxPrice(12);
        item.setMonthMinPrice(13);
        item.setMonthSalesPerDay(14);
        item.setDayAveragePrice(15);
        item.setDayMedianPrice(16);
        item.setDayMaxPrice(17);
        item.setDayMinPrice(18);
        item.setDaySales(19);
        item.setPriceToSellIn1Hour(20);
        item.setPriceToSellIn6Hours(21);
        item.setPriceToSellIn24Hours(22);
        item.setPriceToSellIn168Hours(23);
        item.setPriceToBuyIn1Hour(24);
        item.setPriceToBuyIn6Hours(25);
        item.setPriceToBuyIn24Hours(26);
        item.setPriceToBuyIn168Hours(27);

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
        expected.setType(ItemType.CharacterHeadgear);
        expected.setMaxBuyPrice(5);
        expected.setBuyOrdersCount(6);
        expected.setMinSellPrice(7);
        expected.setSellOrdersCount(8);
        expected.setLastSoldAt(LocalDateTime.MAX);
        expected.setLastSoldPrice(9);
        expected.setRarity(ItemRarity.UNCOMMON);
        expected.setMonthAveragePrice(10);
        expected.setMonthMedianPrice(11);
        expected.setMonthMaxPrice(12);
        expected.setMonthMinPrice(13);
        expected.setMonthSalesPerDay(14);
        expected.setDayAveragePrice(15);
        expected.setDayMedianPrice(16);
        expected.setDayMaxPrice(17);
        expected.setDayMinPrice(18);
        expected.setDaySales(19);
        expected.setPriceToSellIn1Hour(20);
        expected.setPriceToSellIn6Hours(21);
        expected.setPriceToSellIn24Hours(22);
        expected.setPriceToSellIn168Hours(23);
        expected.setPriceToBuyIn1Hour(24);
        expected.setPriceToBuyIn6Hours(25);
        expected.setPriceToBuyIn24Hours(26);
        expected.setPriceToBuyIn168Hours(27);

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
        item.setType(ItemType.Unknown);
        item.setMaxBuyPrice(5);
        item.setBuyOrdersCount(6);
        item.setMinSellPrice(7);
        item.setSellOrdersCount(8);
        item.setLastSoldAt(LocalDateTime.MAX);
        item.setLastSoldPrice(9);
        item.setRarity(ItemRarity.UNKNOWN);
        item.setMonthAveragePrice(10);
        item.setMonthMedianPrice(11);
        item.setMonthMaxPrice(12);
        item.setMonthMinPrice(13);
        item.setMonthSalesPerDay(14);
        item.setDayAveragePrice(15);
        item.setDayMedianPrice(16);
        item.setDayMaxPrice(17);
        item.setDayMinPrice(18);
        item.setDaySales(19);
        item.setPriceToSellIn1Hour(20);
        item.setPriceToSellIn6Hours(21);
        item.setPriceToSellIn24Hours(22);
        item.setPriceToSellIn168Hours(23);
        item.setPriceToBuyIn1Hour(24);
        item.setPriceToBuyIn6Hours(25);
        item.setPriceToBuyIn24Hours(26);
        item.setPriceToBuyIn168Hours(27);

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
        expected.setTags(List.of());
        expected.setType(ItemType.Unknown);
        expected.setMaxBuyPrice(5);
        expected.setBuyOrdersCount(6);
        expected.setMinSellPrice(7);
        expected.setSellOrdersCount(8);
        expected.setLastSoldAt(LocalDateTime.MAX);
        expected.setLastSoldPrice(9);
        expected.setRarity(ItemRarity.UNKNOWN);
        expected.setMonthAveragePrice(10);
        expected.setMonthMedianPrice(11);
        expected.setMonthMaxPrice(12);
        expected.setMonthMinPrice(13);
        expected.setMonthSalesPerDay(14);
        expected.setDayAveragePrice(15);
        expected.setDayMedianPrice(16);
        expected.setDayMaxPrice(17);
        expected.setDayMinPrice(18);
        expected.setDaySales(19);
        expected.setPriceToSellIn1Hour(20);
        expected.setPriceToSellIn6Hours(21);
        expected.setPriceToSellIn24Hours(22);
        expected.setPriceToSellIn168Hours(23);
        expected.setPriceToBuyIn1Hour(24);
        expected.setPriceToBuyIn6Hours(25);
        expected.setPriceToBuyIn24Hours(26);
        expected.setPriceToBuyIn168Hours(27);

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
        item.setMaxBuyPrice(null);
        item.setBuyOrdersCount(null);
        item.setMinSellPrice(null);
        item.setSellOrdersCount(null);
        item.setLastSoldAt(null);
        item.setLastSoldPrice(null);
        item.setRarity(null);
        item.setMonthAveragePrice(null);
        item.setMonthMedianPrice(null);
        item.setMonthMaxPrice(null);
        item.setMonthMinPrice(null);
        item.setMonthSalesPerDay(null);
        item.setDayAveragePrice(null);
        item.setDayMedianPrice(null);
        item.setDayMaxPrice(null);
        item.setDayMinPrice(null);
        item.setDaySales(null);
        item.setPriceToSellIn1Hour(null);
        item.setPriceToSellIn6Hours(null);
        item.setPriceToSellIn24Hours(null);
        item.setPriceToSellIn168Hours(null);
        item.setPriceToBuyIn1Hour(null);
        item.setPriceToBuyIn6Hours(null);
        item.setPriceToBuyIn24Hours(null);
        item.setPriceToBuyIn168Hours(null);

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
        expected.setTags(null);
        expected.setType(null);
        expected.setMaxBuyPrice(null);
        expected.setBuyOrdersCount(null);
        expected.setMinSellPrice(null);
        expected.setSellOrdersCount(null);
        expected.setLastSoldAt(null);
        expected.setLastSoldPrice(null);
        expected.setRarity(null);
        expected.setMonthAveragePrice(null);
        expected.setMonthMedianPrice(null);
        expected.setMonthMaxPrice(null);
        expected.setMonthMinPrice(null);
        expected.setMonthSalesPerDay(null);
        expected.setDayAveragePrice(null);
        expected.setDayMedianPrice(null);
        expected.setDayMaxPrice(null);
        expected.setDayMinPrice(null);
        expected.setDaySales(null);
        expected.setPriceToSellIn1Hour(null);
        expected.setPriceToSellIn6Hours(null);
        expected.setPriceToSellIn24Hours(null);
        expected.setPriceToSellIn168Hours(null);
        expected.setPriceToBuyIn1Hour(null);
        expected.setPriceToBuyIn6Hours(null);
        expected.setPriceToBuyIn24Hours(null);
        expected.setPriceToBuyIn168Hours(null);

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
        entity.setLastSoldAt(LocalDateTime.MIN);
        entity.setLastSoldPrice(9);
        entity.setRarity(ItemRarity.LEGENDARY);
        entity.setMonthAveragePrice(10);
        entity.setMonthMedianPrice(11);
        entity.setMonthMaxPrice(12);
        entity.setMonthMinPrice(13);
        entity.setMonthSalesPerDay(14);
        entity.setDayAveragePrice(15);
        entity.setDayMedianPrice(16);
        entity.setDayMaxPrice(17);
        entity.setDayMinPrice(18);
        entity.setDaySales(19);
        entity.setPriceToSellIn1Hour(20);
        entity.setPriceToSellIn6Hours(21);
        entity.setPriceToSellIn24Hours(22);
        entity.setPriceToSellIn168Hours(23);
        entity.setPriceToBuyIn1Hour(24);
        entity.setPriceToBuyIn6Hours(25);
        entity.setPriceToBuyIn24Hours(26);
        entity.setPriceToBuyIn168Hours(27);

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
        expected.setLastSoldAt(LocalDateTime.MIN);
        expected.setLastSoldPrice(9);
        expected.setRarity(ItemRarity.LEGENDARY);
        expected.setMonthAveragePrice(10);
        expected.setMonthMedianPrice(11);
        expected.setMonthMaxPrice(12);
        expected.setMonthMinPrice(13);
        expected.setMonthSalesPerDay(14);
        expected.setDayAveragePrice(15);
        expected.setDayMedianPrice(16);
        expected.setDayMaxPrice(17);
        expected.setDayMinPrice(18);
        expected.setDaySales(19);
        expected.setPriceToSellIn1Hour(20);
        expected.setPriceToSellIn6Hours(21);
        expected.setPriceToSellIn24Hours(22);
        expected.setPriceToSellIn168Hours(23);
        expected.setPriceToBuyIn1Hour(24);
        expected.setPriceToBuyIn6Hours(25);
        expected.setPriceToBuyIn24Hours(26);
        expected.setPriceToBuyIn168Hours(27);

        Item actual = entity.toItem();

        List<String> expectedTags = expected.getTags();
        List<String> actualTags = actual.getTags();

        expected.setTags(null);
        actual.setTags(null);

        assertTrue(expectedTags.containsAll(actualTags) && actualTags.containsAll(expectedTags));
        assertTrue(expected.isFullyEqualTo(actual));
    }

    @Test
    public void toItem_should_properly_map_with_valid_fields_and_empty_tags() {
        ItemEntity entity = new ItemEntity();
        entity.setItemId("1");
        entity.setAssetUrl("2");
        entity.setName("3");
        entity.setTags(new ArrayList<>());
        entity.setType(ItemType.CharacterUniform);
        entity.setMaxBuyPrice(5);
        entity.setBuyOrdersCount(6);
        entity.setMinSellPrice(7);
        entity.setSellOrdersCount(8);
        entity.setLastSoldAt(LocalDateTime.MAX);
        entity.setLastSoldPrice(9);
        entity.setRarity(ItemRarity.EPIC);
        entity.setMonthAveragePrice(10);
        entity.setMonthMedianPrice(11);
        entity.setMonthMaxPrice(12);
        entity.setMonthMinPrice(13);
        entity.setMonthSalesPerDay(14);
        entity.setDayAveragePrice(15);
        entity.setDayMedianPrice(16);
        entity.setDayMaxPrice(17);
        entity.setDayMinPrice(18);
        entity.setDaySales(19);
        entity.setPriceToSellIn1Hour(20);
        entity.setPriceToSellIn6Hours(21);
        entity.setPriceToSellIn24Hours(22);
        entity.setPriceToSellIn168Hours(23);
        entity.setPriceToBuyIn1Hour(24);
        entity.setPriceToBuyIn6Hours(25);
        entity.setPriceToBuyIn24Hours(26);
        entity.setPriceToBuyIn168Hours(27);

        Item expected = new Item();
        expected.setItemId("1");
        expected.setAssetUrl("2");
        expected.setName("3");
        expected.setTags(List.of());
        expected.setType(ItemType.CharacterUniform);
        expected.setMaxBuyPrice(5);
        expected.setBuyOrdersCount(6);
        expected.setMinSellPrice(7);
        expected.setSellOrdersCount(8);
        expected.setLastSoldAt(LocalDateTime.MAX);
        expected.setLastSoldPrice(9);
        expected.setRarity(ItemRarity.EPIC);
        expected.setMonthAveragePrice(10);
        expected.setMonthMedianPrice(11);
        expected.setMonthMaxPrice(12);
        expected.setMonthMinPrice(13);
        expected.setMonthSalesPerDay(14);
        expected.setDayAveragePrice(15);
        expected.setDayMedianPrice(16);
        expected.setDayMaxPrice(17);
        expected.setDayMinPrice(18);
        expected.setDaySales(19);
        expected.setPriceToSellIn1Hour(20);
        expected.setPriceToSellIn6Hours(21);
        expected.setPriceToSellIn24Hours(22);
        expected.setPriceToSellIn168Hours(23);
        expected.setPriceToBuyIn1Hour(24);
        expected.setPriceToBuyIn6Hours(25);
        expected.setPriceToBuyIn24Hours(26);
        expected.setPriceToBuyIn168Hours(27);

        Item actual = entity.toItem();

        assertTrue(expected.isFullyEqualTo(actual));
    }

    @Test
    public void toItem_should_properly_map_with_null_fields() {
        ItemEntity entity = new ItemEntity();
        entity.setItemId(null);
        entity.setAssetUrl(null);
        entity.setName(null);
        entity.setTags(null);
        entity.setType(null);
        entity.setMaxBuyPrice(null);
        entity.setBuyOrdersCount(null);
        entity.setMinSellPrice(null);
        entity.setSellOrdersCount(null);
        entity.setLastSoldAt(null);
        entity.setLastSoldPrice(null);
        entity.setRarity(null);
        entity.setMonthAveragePrice(null);
        entity.setMonthMedianPrice(null);
        entity.setMonthMaxPrice(null);
        entity.setMonthMinPrice(null);
        entity.setMonthSalesPerDay(null);
        entity.setDayAveragePrice(null);
        entity.setDayMedianPrice(null);
        entity.setDayMaxPrice(null);
        entity.setDayMinPrice(null);
        entity.setDaySales(null);
        entity.setPriceToSellIn1Hour(null);
        entity.setPriceToSellIn6Hours(null);
        entity.setPriceToSellIn24Hours(null);
        entity.setPriceToSellIn168Hours(null);
        entity.setPriceToBuyIn1Hour(null);
        entity.setPriceToBuyIn6Hours(null);
        entity.setPriceToBuyIn24Hours(null);
        entity.setPriceToBuyIn168Hours(null);

        Item expected = new Item();
        expected.setItemId(null);
        expected.setAssetUrl(null);
        expected.setName(null);
        expected.setTags(List.of());
        expected.setType(null);
        expected.setMaxBuyPrice(null);
        expected.setBuyOrdersCount(null);
        expected.setMinSellPrice(null);
        expected.setSellOrdersCount(null);
        expected.setLastSoldAt(null);
        expected.setLastSoldPrice(null);
        expected.setRarity(null);
        expected.setMonthAveragePrice(null);
        expected.setMonthMedianPrice(null);
        expected.setMonthMaxPrice(null);
        expected.setMonthMinPrice(null);
        expected.setMonthSalesPerDay(null);
        expected.setDayAveragePrice(null);
        expected.setDayMedianPrice(null);
        expected.setDayMaxPrice(null);
        expected.setDayMinPrice(null);
        expected.setDaySales(null);
        expected.setPriceToSellIn1Hour(null);
        expected.setPriceToSellIn6Hours(null);
        expected.setPriceToSellIn24Hours(null);
        expected.setPriceToSellIn168Hours(null);
        expected.setPriceToBuyIn1Hour(null);
        expected.setPriceToBuyIn6Hours(null);
        expected.setPriceToBuyIn24Hours(null);
        expected.setPriceToBuyIn168Hours(null);

        Item actual = entity.toItem();

        assertTrue(expected.isFullyEqualTo(actual));
    }

    private boolean entitiesAreEqual(ItemEntity entity1, ItemEntity entity2) {

        List<String> entity1TagsValues = new ArrayList<>();
        if (entity1.getTags() != null) {
            entity1TagsValues = entity1.getTags().stream().map(TagEntity::getValue).toList();
        }
        List<String> entity2TagsValues = new ArrayList<>();
        if (entity2.getTags() != null) {
            entity2TagsValues = entity2.getTags().stream().map(TagEntity::getValue).toList();
        }

        boolean tagsEqual = entity1TagsValues.containsAll(entity2TagsValues) && entity2TagsValues.containsAll(entity1TagsValues);


        return Objects.equals(entity1.getItemId(), entity2.getItemId()) &&
               Objects.equals(entity1.getAssetUrl(), entity2.getAssetUrl()) &&
               Objects.equals(entity1.getName(), entity2.getName()) &&
               tagsEqual &&
               Objects.equals(entity1.getRarity(), entity2.getRarity()) &&
               Objects.equals(entity1.getType(), entity2.getType()) &&
               entity1.getMaxBuyPrice() == entity2.getMaxBuyPrice() &&
               entity1.getBuyOrdersCount() == entity2.getBuyOrdersCount() &&
               entity1.getMinSellPrice() == entity2.getMinSellPrice() &&
               entity1.getSellOrdersCount() == entity2.getSellOrdersCount() &&
               Objects.equals(entity1.getLastSoldAt(), entity2.getLastSoldAt()) &&
               entity1.getLastSoldPrice() == entity2.getLastSoldPrice() &&
               entity1.getMonthAveragePrice() == entity2.getMonthAveragePrice() &&
               entity1.getMonthMedianPrice() == entity2.getMonthMedianPrice() &&
               entity1.getMonthMaxPrice() == entity2.getMonthMaxPrice() &&
               entity1.getMonthMinPrice() == entity2.getMonthMinPrice() &&
               entity1.getMonthSalesPerDay() == entity2.getMonthSalesPerDay() &&
               entity1.getDayAveragePrice() == entity2.getDayAveragePrice() &&
               entity1.getDayMedianPrice() == entity2.getDayMedianPrice() &&
               entity1.getDayMaxPrice() == entity2.getDayMaxPrice() &&
               entity1.getDayMinPrice() == entity2.getDayMinPrice() &&
               entity1.getDaySales() == entity2.getDaySales() &&
               entity1.getPriceToSellIn1Hour() == entity2.getPriceToSellIn1Hour() &&
               entity1.getPriceToSellIn6Hours() == entity2.getPriceToSellIn6Hours() &&
               entity1.getPriceToSellIn24Hours() == entity2.getPriceToSellIn24Hours() &&
               entity1.getPriceToSellIn168Hours() == entity2.getPriceToSellIn168Hours() &&
               entity1.getPriceToBuyIn1Hour() == entity2.getPriceToBuyIn1Hour() &&
               entity1.getPriceToBuyIn6Hours() == entity2.getPriceToBuyIn6Hours() &&
               entity1.getPriceToBuyIn24Hours() == entity2.getPriceToBuyIn24Hours() &&
               entity1.getPriceToBuyIn168Hours() == entity2.getPriceToBuyIn168Hours();
    }
}