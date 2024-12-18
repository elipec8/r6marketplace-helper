package github.ricemonger.utils.DTOs;

import github.ricemonger.utils.DTOs.items.ItemEntityDTO;
import github.ricemonger.utils.DTOs.items.ItemHistoryFieldsI;
import github.ricemonger.utils.DTOs.items.ItemMainFieldsI;
import github.ricemonger.utils.enums.ItemRarity;
import github.ricemonger.utils.enums.ItemType;
import github.ricemonger.utils.enums.TradeCategory;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    public void getPrognosedTradeSuccessMinutes_should_return_0_when_trade_category_is_null_or_Unknown() {
        ItemEntityDTO item = new ItemEntityDTO();
        item.setMinSellPrice(10);
        item.setPriceToBuyIn1Hour(20);
        item.setPriceToBuyIn6Hours(30);
        item.setPriceToBuyIn24Hours(40);
        item.setPriceToBuyIn168Hours(50);
        item.setMaxBuyPrice(60);
        item.setPriceToSellIn1Hour(70);
        item.setPriceToSellIn6Hours(80);
        item.setPriceToSellIn24Hours(90);
        item.setPriceToSellIn168Hours(100);

        assertEquals(0, item.getPrognosedTradeSuccessMinutes(15, null));
        assertEquals(0, item.getPrognosedTradeSuccessMinutes(15, TradeCategory.Unknown));
    }

    @Test
    public void getPrognosedTradeSuccessMinutes_should_return_value_by_prices_and_tradeCategory() {
        ItemEntityDTO item = new ItemEntityDTO();
        item.setMinSellPrice(120);
        item.setPriceToBuyIn1Hour(110);
        item.setPriceToBuyIn6Hours(100);
        item.setPriceToBuyIn24Hours(90);
        item.setPriceToBuyIn168Hours(80);
        item.setMonthMinPrice(70);

        item.setMaxBuyPrice(10);
        item.setPriceToSellIn1Hour(20);
        item.setPriceToSellIn6Hours(30);
        item.setPriceToSellIn24Hours(40);
        item.setPriceToSellIn168Hours(50);
        item.setMonthMaxPrice(60);

        assertEquals(TRADE_MANAGER_FIXED_RATE_MINUTES, item.getPrognosedTradeSuccessMinutes(125, TradeCategory.Buy));
        assertEquals(60, item.getPrognosedTradeSuccessMinutes(115, TradeCategory.Buy));
        assertEquals(360, item.getPrognosedTradeSuccessMinutes(105, TradeCategory.Buy));
        assertEquals(1440, item.getPrognosedTradeSuccessMinutes(95, TradeCategory.Buy));
        assertEquals(10080, item.getPrognosedTradeSuccessMinutes(85, TradeCategory.Buy));
        assertEquals(43200, item.getPrognosedTradeSuccessMinutes(75, TradeCategory.Buy));
        assertEquals(0, item.getPrognosedTradeSuccessMinutes(65, TradeCategory.Buy));

        assertEquals(TRADE_MANAGER_FIXED_RATE_MINUTES, item.getPrognosedTradeSuccessMinutes(5, TradeCategory.Sell));
        assertEquals(60, item.getPrognosedTradeSuccessMinutes(15, TradeCategory.Sell));
        assertEquals(360, item.getPrognosedTradeSuccessMinutes(25, TradeCategory.Sell));
        assertEquals(1440, item.getPrognosedTradeSuccessMinutes(35, TradeCategory.Sell));
        assertEquals(10080, item.getPrognosedTradeSuccessMinutes(45, TradeCategory.Sell));
        assertEquals(43200, item.getPrognosedTradeSuccessMinutes(55, TradeCategory.Sell));
        assertEquals(0, item.getPrognosedTradeSuccessMinutes(65, TradeCategory.Sell));
    }

    @Test
    public void setRarityByTags_should_set_rarity_to_unknown_when_tags_are_null() {
        ItemEntityDTO item = new ItemEntityDTO();
        item.setTags(null);
        item.setRarityByTags("uncommon", "rare", "epic", "legendary");
        assertEquals(ItemRarity.UNKNOWN, item.getRarity());
    }

    @Test
    public void setRarityByTags_should_set_rarity_to_unknown_when_tags_contains_no_rarity() {
        ItemEntityDTO item = new ItemEntityDTO();
        item.setTags(List.of("unknown"));
        item.setRarityByTags("uncommon", "rare", "epic", "legendary");
        assertEquals(ItemRarity.UNKNOWN, item.getRarity());
    }

    @Test
    public void setRarityByTags_should_set_rarity_to_uncommon_when_tags_contains_uncommon() {
        ItemEntityDTO item = new ItemEntityDTO();
        item.setTags(List.of("uncommon"));
        item.setRarityByTags("uncommon", "rare", "epic", "legendary");
        assertEquals(ItemRarity.UNCOMMON, item.getRarity());
    }

    @Test
    public void setRarityByTags_should_set_rarity_to_rare_when_tags_contains_rare() {
        ItemEntityDTO item = new ItemEntityDTO();
        item.setTags(List.of("rare"));
        item.setRarityByTags("uncommon", "rare", "epic", "legendary");
        assertEquals(ItemRarity.RARE, item.getRarity());
    }

    @Test
    public void setRarityByTags_should_set_rarity_to_epic_when_tags_contains_epic() {
        ItemEntityDTO item = new ItemEntityDTO();
        item.setTags(List.of("epic"));
        item.setRarityByTags("uncommon", "rare", "epic", "legendary");
        assertEquals(ItemRarity.EPIC, item.getRarity());
    }

    @Test
    public void setRarityByTags_should_set_rarity_to_legendary_when_tags_contains_legendary() {
        ItemEntityDTO item = new ItemEntityDTO();
        item.setTags(List.of("legendary"));
        item.setRarityByTags("uncommon", "rare", "epic", "legendary");
        assertEquals(ItemRarity.LEGENDARY, item.getRarity());
    }

    @Test
    public void setRarityByTags_should_set_highest_rarity_if_contains_few_rare() {
        ItemEntityDTO item = new ItemEntityDTO();
        item.setTags(List.of("uncommon", "rare"));
        item.setRarityByTags("uncommon", "rare", "epic", "legendary");
        assertEquals(ItemRarity.RARE, item.getRarity());
    }

    @Test
    public void setRarityByTags_should_set_highest_rarity_if_contains_few_epic() {
        ItemEntityDTO item = new ItemEntityDTO();
        item.setTags(List.of("uncommon", "rare", "epic"));
        item.setRarityByTags("uncommon", "rare", "epic", "legendary");
        assertEquals(ItemRarity.EPIC, item.getRarity());
    }

    @Test
    public void setRarityByTags_should_set_highest_rarity_if_contains_few_legendary() {
        ItemEntityDTO item = new ItemEntityDTO();
        item.setTags(List.of("uncommon", "rare", "epic", "legendary"));
        item.setRarityByTags("uncommon", "rare", "epic", "legendary");
        assertEquals(ItemRarity.LEGENDARY, item.getRarity());
    }

    @Test
    public void setMainFields_should_alter_main_fields() {
        ItemEntityDTO item = new ItemEntityDTO();

        ItemMainFieldsI mainFields = new ItemEntityDTO();
        mainFields.setItemId("1");
        mainFields.setAssetUrl("url");
        mainFields.setName("name");
        mainFields.setTags(List.of("tag"));
        mainFields.setRarity(ItemRarity.UNCOMMON);
        mainFields.setType(ItemType.WeaponSkin);
        mainFields.setMaxBuyPrice(1);
        mainFields.setBuyOrdersCount(2);
        mainFields.setMinSellPrice(3);
        mainFields.setSellOrdersCount(4);
        mainFields.setLastSoldAt(LocalDateTime.of(2021, 1, 1, 1, 1));
        mainFields.setLastSoldPrice(5);
        item.setMainFields(mainFields);

        assertEquals("1", item.getItemId());
        assertEquals("url", item.getAssetUrl());
        assertEquals("name", item.getName());
        assertEquals(List.of("tag"), item.getTags());
        assertEquals(ItemRarity.UNCOMMON, item.getRarity());
        assertEquals(ItemType.WeaponSkin, item.getType());
        assertEquals(1, item.getMaxBuyPrice());
        assertEquals(2, item.getBuyOrdersCount());
        assertEquals(3, item.getMinSellPrice());
        assertEquals(4, item.getSellOrdersCount());
        assertEquals(LocalDateTime.of(2021, 1, 1, 1, 1), item.getLastSoldAt());
        assertEquals(5, item.getLastSoldPrice());
    }

    @Test
    public void setItemHistoryFields_should_alter_history_fields() {
        ItemEntityDTO item = new ItemEntityDTO();

        ItemHistoryFieldsI historyFields = new ItemEntityDTO();
        historyFields.setMonthAveragePrice(60);
        historyFields.setMonthMedianPrice(70);
        historyFields.setMonthMaxPrice(80);
        historyFields.setMonthMinPrice(90);
        historyFields.setMonthSalesPerDay(100);
        historyFields.setDayAveragePrice(110);
        historyFields.setDayMedianPrice(120);
        historyFields.setDayMaxPrice(130);
        historyFields.setDayMinPrice(140);
        historyFields.setDaySales(150);
        historyFields.setPriceToSellIn1Hour(160);
        historyFields.setPriceToSellIn6Hours(170);
        historyFields.setPriceToSellIn24Hours(180);
        historyFields.setPriceToSellIn168Hours(190);
        historyFields.setPriceToBuyIn1Hour(200);
        historyFields.setPriceToBuyIn6Hours(210);
        historyFields.setPriceToBuyIn24Hours(220);
        historyFields.setPriceToBuyIn168Hours(230);
        item.setHistoryFields(historyFields);

        assertEquals(60, item.getMonthAveragePrice());
        assertEquals(70, item.getMonthMedianPrice());
        assertEquals(80, item.getMonthMaxPrice());
        assertEquals(90, item.getMonthMinPrice());
        assertEquals(100, item.getMonthSalesPerDay());
        assertEquals(110, item.getDayAveragePrice());
        assertEquals(120, item.getDayMedianPrice());
        assertEquals(130, item.getDayMaxPrice());
        assertEquals(140, item.getDayMinPrice());
        assertEquals(150, item.getDaySales());
        assertEquals(160, item.getPriceToSellIn1Hour());
        assertEquals(170, item.getPriceToSellIn6Hours());
        assertEquals(180, item.getPriceToSellIn24Hours());
        assertEquals(190, item.getPriceToSellIn168Hours());
        assertEquals(200, item.getPriceToBuyIn1Hour());
        assertEquals(210, item.getPriceToBuyIn6Hours());
        assertEquals(220, item.getPriceToBuyIn24Hours());
        assertEquals(230, item.getPriceToBuyIn168Hours());
    }

    @Test
    public void itemMainFieldsAreEqual_should_return_true_when_all_main_fields_are_equal_ignoring_history_fields() {
        ItemEntityDTO item1 = new ItemEntityDTO();
        item1.setItemId("1");
        item1.setAssetUrl("url");
        item1.setName("name");
        item1.setTags(List.of("tag"));
        item1.setRarity(ItemRarity.UNCOMMON);
        item1.setType(ItemType.WeaponSkin);
        item1.setMaxBuyPrice(1);
        item1.setBuyOrdersCount(2);
        item1.setMinSellPrice(3);
        item1.setSellOrdersCount(4);
        item1.setLastSoldAt(LocalDateTime.of(2021, 1, 1, 1, 1));
        item1.setLastSoldPrice(5);
        item1.setMonthAveragePrice(6);
        item1.setMonthMedianPrice(7);
        item1.setMonthMaxPrice(8);
        item1.setMonthMinPrice(9);
        item1.setMonthSalesPerDay(10);
        item1.setDayAveragePrice(11);
        item1.setDayMedianPrice(12);
        item1.setDayMaxPrice(13);
        item1.setDayMinPrice(14);
        item1.setDaySales(15);
        item1.setPriceToSellIn1Hour(16);
        item1.setPriceToSellIn6Hours(17);
        item1.setPriceToSellIn24Hours(18);
        item1.setPriceToSellIn168Hours(19);
        item1.setPriceToBuyIn1Hour(20);
        item1.setPriceToBuyIn6Hours(21);
        item1.setPriceToBuyIn24Hours(22);
        item1.setPriceToBuyIn168Hours(23);

        ItemEntityDTO item2 = new ItemEntityDTO();
        item2.setItemId("1");
        item2.setAssetUrl("url");
        item2.setName("name");
        item2.setTags(List.of("tag"));
        item2.setRarity(ItemRarity.UNCOMMON);
        item2.setType(ItemType.WeaponSkin);
        item2.setMaxBuyPrice(1);
        item2.setBuyOrdersCount(2);
        item2.setMinSellPrice(3);
        item2.setSellOrdersCount(4);
        item2.setLastSoldAt(LocalDateTime.of(2021, 1, 1, 1, 1));
        item2.setLastSoldPrice(5);
        item2.setMonthAveragePrice(60);
        item2.setMonthMedianPrice(70);
        item2.setMonthMaxPrice(80);
        item2.setMonthMinPrice(90);
        item2.setMonthSalesPerDay(100);
        item2.setDayAveragePrice(110);
        item2.setDayMedianPrice(120);
        item2.setDayMaxPrice(130);
        item2.setDayMinPrice(140);
        item2.setDaySales(150);
        item2.setPriceToSellIn1Hour(160);
        item2.setPriceToSellIn6Hours(170);
        item2.setPriceToSellIn24Hours(180);
        item2.setPriceToSellIn168Hours(190);
        item2.setPriceToBuyIn1Hour(200);
        item2.setPriceToBuyIn6Hours(210);
        item2.setPriceToBuyIn24Hours(220);
        item2.setPriceToBuyIn168Hours(230);

        assertTrue(item1.itemMainFieldsAreEqual(item2));
    }

    @Test
    public void itemMainFieldsAreEqual_should_return_false_if_any_main_field_is_different() {
        ItemEntityDTO item1 = new ItemEntityDTO();
        item1.setItemId("1");
        item1.setAssetUrl("url");
        item1.setName("name");
        item1.setTags(List.of("tag"));
        item1.setRarity(ItemRarity.UNCOMMON);
        item1.setType(ItemType.WeaponSkin);
        item1.setMaxBuyPrice(1);
        item1.setBuyOrdersCount(2);
        item1.setMinSellPrice(3);
        item1.setSellOrdersCount(4);
        item1.setLastSoldAt(LocalDateTime.of(2021, 1, 1, 1, 1));
        item1.setLastSoldPrice(5);

        ItemEntityDTO itemId = new ItemEntityDTO(item1);
        itemId.setItemId("2");

        ItemEntityDTO assetUrl = new ItemEntityDTO(item1);
        assetUrl.setAssetUrl("url2");

        ItemEntityDTO name = new ItemEntityDTO(item1);
        name.setName("name2");

        ItemEntityDTO tags = new ItemEntityDTO(item1);
        tags.setTags(List.of("tag2"));

        ItemEntityDTO rarity = new ItemEntityDTO(item1);
        rarity.setRarity(ItemRarity.RARE);

        ItemEntityDTO type = new ItemEntityDTO(item1);
        type.setType(ItemType.CharacterHeadgear);

        ItemEntityDTO maxBuyPrice = new ItemEntityDTO(item1);
        maxBuyPrice.setMaxBuyPrice(10);

        ItemEntityDTO buyOrdersCount = new ItemEntityDTO(item1);
        buyOrdersCount.setBuyOrdersCount(20);

        ItemEntityDTO minSellPrice = new ItemEntityDTO(item1);
        minSellPrice.setMinSellPrice(30);

        ItemEntityDTO sellOrdersCount = new ItemEntityDTO(item1);
        sellOrdersCount.setSellOrdersCount(40);

        ItemEntityDTO lastSoldAt = new ItemEntityDTO(item1);
        lastSoldAt.setLastSoldAt(LocalDateTime.of(2024, 1, 1, 1, 1));

        ItemEntityDTO lastSoldPrice = new ItemEntityDTO(item1);
        lastSoldPrice.setLastSoldPrice(50);

        assertFalse(item1.itemMainFieldsAreEqual(itemId));
        assertFalse(item1.itemMainFieldsAreEqual(assetUrl));
        assertFalse(item1.itemMainFieldsAreEqual(name));
        assertFalse(item1.itemMainFieldsAreEqual(tags));
        assertFalse(item1.itemMainFieldsAreEqual(rarity));
        assertFalse(item1.itemMainFieldsAreEqual(type));
        assertFalse(item1.itemMainFieldsAreEqual(maxBuyPrice));
        assertFalse(item1.itemMainFieldsAreEqual(buyOrdersCount));
        assertFalse(item1.itemMainFieldsAreEqual(minSellPrice));
        assertFalse(item1.itemMainFieldsAreEqual(sellOrdersCount));
        assertFalse(item1.itemMainFieldsAreEqual(lastSoldAt));
        assertFalse(item1.itemMainFieldsAreEqual(lastSoldPrice));
    }


    @Test
    public void itemMainFieldsAreEqual_should_return_false_when_item_is_null() {
        ItemEntityDTO item1 = new ItemEntityDTO();
        item1.setItemId("1");
        item1.setAssetUrl("url");
        item1.setName("name");
        item1.setTags(List.of("tag"));
        item1.setRarity(ItemRarity.UNCOMMON);
        item1.setType(ItemType.WeaponSkin);
        item1.setMaxBuyPrice(1);
        item1.setBuyOrdersCount(2);
        item1.setMinSellPrice(3);
        item1.setSellOrdersCount(4);
        item1.setLastSoldAt(LocalDateTime.of(2021, 1, 1, 1, 1));
        item1.setLastSoldPrice(5);
        item1.setMonthAveragePrice(6);
        item1.setMonthMedianPrice(7);
        item1.setMonthMaxPrice(8);
        item1.setMonthMinPrice(9);
        item1.setMonthSalesPerDay(10);
        item1.setDayAveragePrice(11);
        item1.setDayMedianPrice(12);
        item1.setDayMaxPrice(13);
        item1.setDayMinPrice(14);
        item1.setDaySales(15);
        item1.setPriceToSellIn1Hour(16);
        item1.setPriceToSellIn6Hours(17);
        item1.setPriceToSellIn24Hours(18);
        item1.setPriceToSellIn168Hours(19);
        item1.setPriceToBuyIn1Hour(20);
        item1.setPriceToBuyIn6Hours(21);
        item1.setPriceToBuyIn24Hours(22);
        item1.setPriceToBuyIn168Hours(23);

        ItemEntityDTO item2 = null;

        assertFalse(item1.itemMainFieldsAreEqual(item2));
    }

    @Test
    public void itemMainFieldsAreEqual_should_return_false_when_object_of_another_class() {
        ItemEntityDTO item1 = new ItemEntityDTO();
        item1.setItemId("1");
        item1.setAssetUrl("url");
        item1.setName("name");
        item1.setTags(List.of("tag"));
        item1.setRarity(ItemRarity.UNCOMMON);
        item1.setType(ItemType.WeaponSkin);
        item1.setMaxBuyPrice(1);
        item1.setBuyOrdersCount(2);
        item1.setMinSellPrice(3);
        item1.setSellOrdersCount(4);
        item1.setLastSoldAt(LocalDateTime.of(2021, 1, 1, 1, 1));
        item1.setLastSoldPrice(5);
        item1.setMonthAveragePrice(6);
        item1.setMonthMedianPrice(7);
        item1.setMonthMaxPrice(8);
        item1.setMonthMinPrice(9);
        item1.setMonthSalesPerDay(10);
        item1.setDayAveragePrice(11);
        item1.setDayMedianPrice(12);
        item1.setDayMaxPrice(13);
        item1.setDayMinPrice(14);
        item1.setDaySales(15);
        item1.setPriceToSellIn1Hour(16);
        item1.setPriceToSellIn6Hours(17);
        item1.setPriceToSellIn24Hours(18);
        item1.setPriceToSellIn168Hours(19);
        item1.setPriceToBuyIn1Hour(20);
        item1.setPriceToBuyIn6Hours(21);
        item1.setPriceToBuyIn24Hours(22);
        item1.setPriceToBuyIn168Hours(23);

        ItemFilter item2 = new ItemFilter();

        assertFalse(item1.itemMainFieldsAreEqual(item2));
    }

    @Test
    public void itemHistoryFieldsAreEqual_should_return_true_if_history_fields_are_equal_ignoring_main_fields() {
        ItemEntityDTO item1 = new ItemEntityDTO();
        item1.setItemId("1");
        item1.setAssetUrl("url");
        item1.setName("name");
        item1.setTags(List.of("tag"));
        item1.setRarity(ItemRarity.UNCOMMON);
        item1.setType(ItemType.WeaponSkin);
        item1.setMaxBuyPrice(1);
        item1.setBuyOrdersCount(2);
        item1.setMinSellPrice(3);
        item1.setSellOrdersCount(4);
        item1.setLastSoldAt(LocalDateTime.of(2021, 1, 1, 1, 1));
        item1.setLastSoldPrice(5);
        item1.setMonthAveragePrice(6);
        item1.setMonthMedianPrice(7);
        item1.setMonthMaxPrice(8);
        item1.setMonthMinPrice(9);
        item1.setMonthSalesPerDay(10);
        item1.setDayAveragePrice(11);
        item1.setDayMedianPrice(12);
        item1.setDayMaxPrice(13);
        item1.setDayMinPrice(14);
        item1.setDaySales(15);
        item1.setPriceToSellIn1Hour(16);
        item1.setPriceToSellIn6Hours(17);
        item1.setPriceToSellIn24Hours(18);
        item1.setPriceToSellIn168Hours(19);
        item1.setPriceToBuyIn1Hour(20);
        item1.setPriceToBuyIn6Hours(21);
        item1.setPriceToBuyIn24Hours(22);
        item1.setPriceToBuyIn168Hours(23);

        ItemEntityDTO item2 = new ItemEntityDTO();
        item2.setItemId("1");
        item2.setAssetUrl("url2");
        item2.setName("name2");
        item2.setTags(List.of("tag2"));
        item2.setRarity(ItemRarity.RARE);
        item2.setType(ItemType.CharacterHeadgear);
        item2.setMaxBuyPrice(10);
        item2.setBuyOrdersCount(20);
        item2.setMinSellPrice(30);
        item2.setSellOrdersCount(40);
        item2.setLastSoldAt(LocalDateTime.of(2024, 1, 1, 1, 1));
        item2.setLastSoldPrice(50);
        item2.setMonthAveragePrice(6);
        item2.setMonthMedianPrice(7);
        item2.setMonthMaxPrice(8);
        item2.setMonthMinPrice(9);
        item2.setMonthSalesPerDay(10);
        item2.setDayAveragePrice(11);
        item2.setDayMedianPrice(12);
        item2.setDayMaxPrice(13);
        item2.setDayMinPrice(14);
        item2.setDaySales(15);
        item2.setPriceToSellIn1Hour(16);
        item2.setPriceToSellIn6Hours(17);
        item2.setPriceToSellIn24Hours(18);
        item2.setPriceToSellIn168Hours(19);
        item2.setPriceToBuyIn1Hour(20);
        item2.setPriceToBuyIn6Hours(21);
        item2.setPriceToBuyIn24Hours(22);
        item2.setPriceToBuyIn168Hours(23);

        assertTrue(item1.itemHistoryFieldsAreEqual(item2));
    }

    @Test
    public void itemHistoryFieldsAreEqual_should_return_false_if_any_history_field_is_different() {
        ItemEntityDTO item1 = new ItemEntityDTO();
        item1.setItemId("1");
        item1.setAssetUrl("url");
        item1.setName("name");
        item1.setTags(List.of("tag"));
        item1.setRarity(ItemRarity.UNCOMMON);
        item1.setType(ItemType.WeaponSkin);
        item1.setMaxBuyPrice(1);
        item1.setBuyOrdersCount(2);
        item1.setMinSellPrice(3);
        item1.setSellOrdersCount(4);
        item1.setLastSoldAt(LocalDateTime.of(2021, 1, 1, 1, 1));
        item1.setLastSoldPrice(5);
        item1.setMonthAveragePrice(6);
        item1.setMonthMedianPrice(7);
        item1.setMonthMaxPrice(8);
        item1.setMonthMinPrice(9);
        item1.setMonthSalesPerDay(10);
        item1.setDayAveragePrice(11);
        item1.setDayMedianPrice(12);
        item1.setDayMaxPrice(13);
        item1.setDayMinPrice(14);
        item1.setDaySales(15);
        item1.setPriceToSellIn1Hour(16);
        item1.setPriceToSellIn6Hours(17);
        item1.setPriceToSellIn24Hours(18);
        item1.setPriceToSellIn168Hours(19);
        item1.setPriceToBuyIn1Hour(20);
        item1.setPriceToBuyIn6Hours(21);
        item1.setPriceToBuyIn24Hours(22);
        item1.setPriceToBuyIn168Hours(23);

        ItemEntityDTO itemId = new ItemEntityDTO(item1);
        itemId.setItemId("2");

        ItemEntityDTO monthAveragePrice = new ItemEntityDTO(item1);
        monthAveragePrice.setMonthAveragePrice(60);

        ItemEntityDTO monthMedianPrice = new ItemEntityDTO(item1);
        monthMedianPrice.setMonthMedianPrice(70);

        ItemEntityDTO monthMaxPrice = new ItemEntityDTO(item1);
        monthMaxPrice.setMonthMaxPrice(80);

        ItemEntityDTO monthMinPrice = new ItemEntityDTO(item1);
        monthMinPrice.setMonthMinPrice(90);

        ItemEntityDTO monthSalesPerDay = new ItemEntityDTO(item1);
        monthSalesPerDay.setMonthSalesPerDay(100);

        ItemEntityDTO dayAveragePrice = new ItemEntityDTO(item1);
        dayAveragePrice.setDayAveragePrice(110);

        ItemEntityDTO dayMedianPrice = new ItemEntityDTO(item1);
        dayMedianPrice.setDayMedianPrice(120);

        ItemEntityDTO dayMaxPrice = new ItemEntityDTO(item1);
        dayMaxPrice.setDayMaxPrice(130);

        ItemEntityDTO dayMinPrice = new ItemEntityDTO(item1);
        dayMinPrice.setDayMinPrice(140);

        ItemEntityDTO daySales = new ItemEntityDTO(item1);
        daySales.setDaySales(150);

        ItemEntityDTO priceToSellIn1Hour = new ItemEntityDTO(item1);
        priceToSellIn1Hour.setPriceToSellIn1Hour(160);

        ItemEntityDTO priceToSellIn6Hours = new ItemEntityDTO(item1);
        priceToSellIn6Hours.setPriceToSellIn6Hours(170);

        ItemEntityDTO priceToSellIn24Hours = new ItemEntityDTO(item1);
        priceToSellIn24Hours.setPriceToSellIn24Hours(180);

        ItemEntityDTO priceToSellIn168Hours = new ItemEntityDTO(item1);
        priceToSellIn168Hours.setPriceToSellIn168Hours(190);

        ItemEntityDTO priceToBuyIn1Hour = new ItemEntityDTO(item1);
        priceToBuyIn1Hour.setPriceToBuyIn1Hour(200);

        ItemEntityDTO priceToBuyIn6Hours = new ItemEntityDTO(item1);
        priceToBuyIn6Hours.setPriceToBuyIn6Hours(210);

        ItemEntityDTO priceToBuyIn24Hours = new ItemEntityDTO(item1);
        priceToBuyIn24Hours.setPriceToBuyIn24Hours(220);

        ItemEntityDTO priceToBuyIn168Hours = new ItemEntityDTO(item1);
        priceToBuyIn168Hours.setPriceToBuyIn168Hours(230);

        assertFalse(item1.itemHistoryFieldsAreEqual(itemId));
        assertFalse(item1.itemHistoryFieldsAreEqual(monthAveragePrice));
        assertFalse(item1.itemHistoryFieldsAreEqual(monthMedianPrice));
        assertFalse(item1.itemHistoryFieldsAreEqual(monthMaxPrice));
        assertFalse(item1.itemHistoryFieldsAreEqual(monthMinPrice));
        assertFalse(item1.itemHistoryFieldsAreEqual(monthSalesPerDay));
        assertFalse(item1.itemHistoryFieldsAreEqual(dayAveragePrice));
        assertFalse(item1.itemHistoryFieldsAreEqual(dayMedianPrice));
        assertFalse(item1.itemHistoryFieldsAreEqual(dayMaxPrice));
        assertFalse(item1.itemHistoryFieldsAreEqual(dayMinPrice));
        assertFalse(item1.itemHistoryFieldsAreEqual(daySales));
        assertFalse(item1.itemHistoryFieldsAreEqual(priceToSellIn1Hour));
        assertFalse(item1.itemHistoryFieldsAreEqual(priceToSellIn6Hours));
        assertFalse(item1.itemHistoryFieldsAreEqual(priceToSellIn24Hours));
        assertFalse(item1.itemHistoryFieldsAreEqual(priceToSellIn168Hours));
        assertFalse(item1.itemHistoryFieldsAreEqual(priceToBuyIn1Hour));
        assertFalse(item1.itemHistoryFieldsAreEqual(priceToBuyIn6Hours));
        assertFalse(item1.itemHistoryFieldsAreEqual(priceToBuyIn24Hours));
        assertFalse(item1.itemHistoryFieldsAreEqual(priceToBuyIn168Hours));
    }

    @Test
    public void itemHistoryFieldsAreEqual_should_return_false_when_item_is_null() {
        ItemEntityDTO item1 = new ItemEntityDTO();
        item1.setItemId("1");
        item1.setAssetUrl("url");
        item1.setName("name");
        item1.setTags(List.of("tag"));
        item1.setRarity(ItemRarity.UNCOMMON);
        item1.setType(ItemType.WeaponSkin);
        item1.setMaxBuyPrice(1);
        item1.setBuyOrdersCount(2);
        item1.setMinSellPrice(3);
        item1.setSellOrdersCount(4);
        item1.setLastSoldAt(LocalDateTime.of(2021, 1, 1, 1, 1));
        item1.setLastSoldPrice(5);
        item1.setMonthAveragePrice(6);
        item1.setMonthMedianPrice(7);
        item1.setMonthMaxPrice(8);
        item1.setMonthMinPrice(9);
        item1.setMonthSalesPerDay(10);
        item1.setDayAveragePrice(11);
        item1.setDayMedianPrice(12);
        item1.setDayMaxPrice(13);
        item1.setDayMinPrice(14);
        item1.setDaySales(15);
        item1.setPriceToSellIn1Hour(16);
        item1.setPriceToSellIn6Hours(17);
        item1.setPriceToSellIn24Hours(18);
        item1.setPriceToSellIn168Hours(19);
        item1.setPriceToBuyIn1Hour(20);
        item1.setPriceToBuyIn6Hours(21);
        item1.setPriceToBuyIn24Hours(22);
        item1.setPriceToBuyIn168Hours(23);

        ItemEntityDTO item2 = null;

        assertFalse(item1.itemHistoryFieldsAreEqual(item2));
    }

    @Test
    public void itemHistoryFieldsAreEqual_should_return_false_when_object_of_another_class() {
        ItemEntityDTO item1 = new ItemEntityDTO();
        item1.setItemId("1");
        item1.setAssetUrl("url");
        item1.setName("name");
        item1.setTags(List.of("tag"));
        item1.setRarity(ItemRarity.UNCOMMON);
        item1.setType(ItemType.WeaponSkin);
        item1.setMaxBuyPrice(1);
        item1.setBuyOrdersCount(2);
        item1.setMinSellPrice(3);
        item1.setSellOrdersCount(4);
        item1.setLastSoldAt(LocalDateTime.of(2021, 1, 1, 1, 1));
        item1.setLastSoldPrice(5);
        item1.setMonthAveragePrice(6);
        item1.setMonthMedianPrice(7);
        item1.setMonthMaxPrice(8);
        item1.setMonthMinPrice(9);
        item1.setMonthSalesPerDay(10);
        item1.setDayAveragePrice(11);
        item1.setDayMedianPrice(12);
        item1.setDayMaxPrice(13);
        item1.setDayMinPrice(14);
        item1.setDaySales(15);
        item1.setPriceToSellIn1Hour(16);
        item1.setPriceToSellIn6Hours(17);
        item1.setPriceToSellIn24Hours(18);
        item1.setPriceToSellIn168Hours(19);
        item1.setPriceToBuyIn1Hour(20);
        item1.setPriceToBuyIn6Hours(21);
        item1.setPriceToBuyIn24Hours(22);
        item1.setPriceToBuyIn168Hours(23);

        ItemFilter item2 = new ItemFilter();

        assertFalse(item1.itemHistoryFieldsAreEqual(item2));
    }

    @Test
    public void isFullyEqualTo_should_return_true_when_all_fields_are_equal() {
        ItemEntityDTO item1 = new ItemEntityDTO();
        item1.setItemId("1");
        item1.setAssetUrl("url");
        item1.setName("name");
        item1.setTags(List.of("tag"));
        item1.setRarity(ItemRarity.UNCOMMON);
        item1.setType(ItemType.WeaponSkin);
        item1.setMaxBuyPrice(1);
        item1.setBuyOrdersCount(2);
        item1.setMinSellPrice(3);
        item1.setSellOrdersCount(4);
        item1.setLastSoldAt(LocalDateTime.of(2021, 1, 1, 1, 1));
        item1.setLastSoldPrice(5);
        item1.setMonthAveragePrice(6);
        item1.setMonthMedianPrice(7);
        item1.setMonthMaxPrice(8);
        item1.setMonthMinPrice(9);
        item1.setMonthSalesPerDay(10);
        item1.setDayAveragePrice(11);
        item1.setDayMedianPrice(12);
        item1.setDayMaxPrice(13);
        item1.setDayMinPrice(14);
        item1.setDaySales(15);
        item1.setPriceToSellIn1Hour(16);
        item1.setPriceToSellIn6Hours(17);
        item1.setPriceToSellIn24Hours(18);
        item1.setPriceToSellIn168Hours(19);
        item1.setPriceToBuyIn1Hour(20);
        item1.setPriceToBuyIn6Hours(21);
        item1.setPriceToBuyIn24Hours(22);
        item1.setPriceToBuyIn168Hours(23);

        ItemEntityDTO item2 = new ItemEntityDTO();
        item2.setItemId("1");
        item2.setAssetUrl("url");
        item2.setName("name");
        item2.setTags(List.of("tag"));
        item2.setRarity(ItemRarity.UNCOMMON);
        item2.setType(ItemType.WeaponSkin);
        item2.setMaxBuyPrice(1);
        item2.setBuyOrdersCount(2);
        item2.setMinSellPrice(3);
        item2.setSellOrdersCount(4);
        item2.setLastSoldAt(LocalDateTime.of(2021, 1, 1, 1, 1));
        item2.setLastSoldPrice(5);
        item2.setMonthAveragePrice(6);
        item2.setMonthMedianPrice(7);
        item2.setMonthMaxPrice(8);
        item2.setMonthMinPrice(9);
        item2.setMonthSalesPerDay(10);
        item2.setDayAveragePrice(11);
        item2.setDayMedianPrice(12);
        item2.setDayMaxPrice(13);
        item2.setDayMinPrice(14);
        item2.setDaySales(15);
        item2.setPriceToSellIn1Hour(16);
        item2.setPriceToSellIn6Hours(17);
        item2.setPriceToSellIn24Hours(18);
        item2.setPriceToSellIn168Hours(19);
        item2.setPriceToBuyIn1Hour(20);
        item2.setPriceToBuyIn6Hours(21);
        item2.setPriceToBuyIn24Hours(22);
        item2.setPriceToBuyIn168Hours(23);

        assertTrue(item1.itemMainFieldsAreEqual(item2));
    }

    @Test
    public void isFullyEqualTo_should_return_false_when_any_field_is_different() {
        ItemEntityDTO item1 = new ItemEntityDTO();
        item1.setItemId("1");
        item1.setAssetUrl("url");
        item1.setName("name");
        item1.setTags(List.of("tag"));
        item1.setRarity(ItemRarity.UNCOMMON);
        item1.setType(ItemType.WeaponSkin);
        item1.setMaxBuyPrice(1);
        item1.setBuyOrdersCount(2);
        item1.setMinSellPrice(3);
        item1.setSellOrdersCount(4);
        item1.setLastSoldAt(LocalDateTime.of(2021, 1, 1, 1, 1));
        item1.setLastSoldPrice(5);
        item1.setMonthAveragePrice(6);
        item1.setMonthMedianPrice(7);
        item1.setMonthMaxPrice(8);
        item1.setMonthMinPrice(9);
        item1.setMonthSalesPerDay(10);
        item1.setDayAveragePrice(11);
        item1.setDayMedianPrice(12);
        item1.setDayMaxPrice(13);
        item1.setDayMinPrice(14);
        item1.setDaySales(15);
        item1.setPriceToSellIn1Hour(16);
        item1.setPriceToSellIn6Hours(17);
        item1.setPriceToSellIn24Hours(18);
        item1.setPriceToSellIn168Hours(19);
        item1.setPriceToBuyIn1Hour(20);
        item1.setPriceToBuyIn6Hours(21);
        item1.setPriceToBuyIn24Hours(22);
        item1.setPriceToBuyIn168Hours(23);

        ItemEntityDTO itemId = new ItemEntityDTO(item1);
        itemId.setItemId("2");

        ItemEntityDTO assetUrl = new ItemEntityDTO(item1);
        assetUrl.setAssetUrl("url2");

        ItemEntityDTO name = new ItemEntityDTO(item1);
        name.setName("name2");

        ItemEntityDTO tags = new ItemEntityDTO(item1);
        tags.setTags(List.of("tag2"));

        ItemEntityDTO rarity = new ItemEntityDTO(item1);
        rarity.setRarity(ItemRarity.RARE);

        ItemEntityDTO type = new ItemEntityDTO(item1);
        type.setType(ItemType.CharacterHeadgear);

        ItemEntityDTO maxBuyPrice = new ItemEntityDTO(item1);
        maxBuyPrice.setMaxBuyPrice(10);

        ItemEntityDTO buyOrdersCount = new ItemEntityDTO(item1);
        buyOrdersCount.setBuyOrdersCount(20);

        ItemEntityDTO minSellPrice = new ItemEntityDTO(item1);
        minSellPrice.setMinSellPrice(30);

        ItemEntityDTO sellOrdersCount = new ItemEntityDTO(item1);
        sellOrdersCount.setSellOrdersCount(40);

        ItemEntityDTO lastSoldAt = new ItemEntityDTO(item1);
        lastSoldAt.setLastSoldAt(LocalDateTime.of(2024, 1, 1, 1, 1));

        ItemEntityDTO lastSoldPrice = new ItemEntityDTO(item1);
        lastSoldPrice.setLastSoldPrice(50);

        ItemEntityDTO monthAveragePrice = new ItemEntityDTO(item1);
        monthAveragePrice.setMonthAveragePrice(60);

        ItemEntityDTO monthMedianPrice = new ItemEntityDTO(item1);
        monthMedianPrice.setMonthMedianPrice(70);

        ItemEntityDTO monthMaxPrice = new ItemEntityDTO(item1);
        monthMaxPrice.setMonthMaxPrice(80);

        ItemEntityDTO monthMinPrice = new ItemEntityDTO(item1);
        monthMinPrice.setMonthMinPrice(90);

        ItemEntityDTO monthSalesPerDay = new ItemEntityDTO(item1);
        monthSalesPerDay.setMonthSalesPerDay(100);

        ItemEntityDTO dayAveragePrice = new ItemEntityDTO(item1);
        dayAveragePrice.setDayAveragePrice(110);

        ItemEntityDTO dayMedianPrice = new ItemEntityDTO(item1);
        dayMedianPrice.setDayMedianPrice(120);

        ItemEntityDTO dayMaxPrice = new ItemEntityDTO(item1);
        dayMaxPrice.setDayMaxPrice(130);

        ItemEntityDTO dayMinPrice = new ItemEntityDTO(item1);
        dayMinPrice.setDayMinPrice(140);

        ItemEntityDTO daySales = new ItemEntityDTO(item1);
        daySales.setDaySales(150);

        ItemEntityDTO priceToSellIn1Hour = new ItemEntityDTO(item1);
        priceToSellIn1Hour.setPriceToSellIn1Hour(160);

        ItemEntityDTO priceToSellIn6Hours = new ItemEntityDTO(item1);
        priceToSellIn6Hours.setPriceToSellIn6Hours(170);

        ItemEntityDTO priceToSellIn24Hours = new ItemEntityDTO(item1);
        priceToSellIn24Hours.setPriceToSellIn24Hours(180);

        ItemEntityDTO priceToSellIn168Hours = new ItemEntityDTO(item1);
        priceToSellIn168Hours.setPriceToSellIn168Hours(190);

        ItemEntityDTO priceToBuyIn1Hour = new ItemEntityDTO(item1);
        priceToBuyIn1Hour.setPriceToBuyIn1Hour(200);

        ItemEntityDTO priceToBuyIn6Hours = new ItemEntityDTO(item1);
        priceToBuyIn6Hours.setPriceToBuyIn6Hours(210);

        ItemEntityDTO priceToBuyIn24Hours = new ItemEntityDTO(item1);
        priceToBuyIn24Hours.setPriceToBuyIn24Hours(220);

        ItemEntityDTO priceToBuyIn168Hours = new ItemEntityDTO(item1);
        priceToBuyIn168Hours.setPriceToBuyIn168Hours(230);

        assertFalse(item1.isFullyEqualTo(itemId));
        assertFalse(item1.isFullyEqualTo(assetUrl));
        assertFalse(item1.isFullyEqualTo(name));
        assertFalse(item1.isFullyEqualTo(tags));
        assertFalse(item1.isFullyEqualTo(rarity));
        assertFalse(item1.isFullyEqualTo(type));
        assertFalse(item1.isFullyEqualTo(maxBuyPrice));
        assertFalse(item1.isFullyEqualTo(buyOrdersCount));
        assertFalse(item1.isFullyEqualTo(minSellPrice));
        assertFalse(item1.isFullyEqualTo(sellOrdersCount));
        assertFalse(item1.isFullyEqualTo(lastSoldAt));
        assertFalse(item1.isFullyEqualTo(lastSoldPrice));
        assertFalse(item1.isFullyEqualTo(monthAveragePrice));
        assertFalse(item1.isFullyEqualTo(monthMedianPrice));
        assertFalse(item1.isFullyEqualTo(monthMaxPrice));
        assertFalse(item1.isFullyEqualTo(monthMinPrice));
        assertFalse(item1.isFullyEqualTo(monthSalesPerDay));
        assertFalse(item1.isFullyEqualTo(dayAveragePrice));
        assertFalse(item1.isFullyEqualTo(dayMedianPrice));
        assertFalse(item1.isFullyEqualTo(dayMaxPrice));
        assertFalse(item1.isFullyEqualTo(dayMinPrice));
        assertFalse(item1.isFullyEqualTo(daySales));
        assertFalse(item1.isFullyEqualTo(priceToSellIn1Hour));
        assertFalse(item1.isFullyEqualTo(priceToSellIn6Hours));
        assertFalse(item1.isFullyEqualTo(priceToSellIn24Hours));
        assertFalse(item1.isFullyEqualTo(priceToSellIn168Hours));
        assertFalse(item1.isFullyEqualTo(priceToBuyIn1Hour));
        assertFalse(item1.isFullyEqualTo(priceToBuyIn6Hours));
        assertFalse(item1.isFullyEqualTo(priceToBuyIn24Hours));
        assertFalse(item1.isFullyEqualTo(priceToBuyIn168Hours));
    }

    @Test
    public void isFullyEqualTo_should_return_false_when_item_is_null() {
        ItemEntityDTO item1 = new ItemEntityDTO();
        item1.setItemId("1");
        item1.setAssetUrl("url");
        item1.setName("name");
        item1.setTags(List.of("tag"));
        item1.setRarity(ItemRarity.UNCOMMON);
        item1.setType(ItemType.WeaponSkin);
        item1.setMaxBuyPrice(1);
        item1.setBuyOrdersCount(2);
        item1.setMinSellPrice(3);
        item1.setSellOrdersCount(4);
        item1.setLastSoldAt(LocalDateTime.of(2021, 1, 1, 1, 1));
        item1.setLastSoldPrice(5);
        item1.setMonthAveragePrice(6);
        item1.setMonthMedianPrice(7);
        item1.setMonthMaxPrice(8);
        item1.setMonthMinPrice(9);
        item1.setMonthSalesPerDay(10);
        item1.setDayAveragePrice(11);
        item1.setDayMedianPrice(12);
        item1.setDayMaxPrice(13);
        item1.setDayMinPrice(14);
        item1.setDaySales(15);
        item1.setPriceToSellIn1Hour(16);
        item1.setPriceToSellIn6Hours(17);
        item1.setPriceToSellIn24Hours(18);
        item1.setPriceToSellIn168Hours(19);
        item1.setPriceToBuyIn1Hour(20);
        item1.setPriceToBuyIn6Hours(21);
        item1.setPriceToBuyIn24Hours(22);
        item1.setPriceToBuyIn168Hours(23);

        ItemEntityDTO item2 = null;

        assertFalse(item1.isFullyEqualTo(item2));
    }

    @Test
    public void isFullyEqualTo_should_return_false_when_object_of_another_class() {
        ItemEntityDTO item1 = new ItemEntityDTO();
        item1.setItemId("1");
        item1.setAssetUrl("url");
        item1.setName("name");
        item1.setTags(List.of("tag"));
        item1.setRarity(ItemRarity.UNCOMMON);
        item1.setType(ItemType.WeaponSkin);
        item1.setMaxBuyPrice(1);
        item1.setBuyOrdersCount(2);
        item1.setMinSellPrice(3);
        item1.setSellOrdersCount(4);
        item1.setLastSoldAt(LocalDateTime.of(2021, 1, 1, 1, 1));
        item1.setLastSoldPrice(5);
        item1.setMonthAveragePrice(6);
        item1.setMonthMedianPrice(7);
        item1.setMonthMaxPrice(8);
        item1.setMonthMinPrice(9);
        item1.setMonthSalesPerDay(10);
        item1.setDayAveragePrice(11);
        item1.setDayMedianPrice(12);
        item1.setDayMaxPrice(13);
        item1.setDayMinPrice(14);
        item1.setDaySales(15);
        item1.setPriceToSellIn1Hour(16);
        item1.setPriceToSellIn6Hours(17);
        item1.setPriceToSellIn24Hours(18);
        item1.setPriceToSellIn168Hours(19);
        item1.setPriceToBuyIn1Hour(20);
        item1.setPriceToBuyIn6Hours(21);
        item1.setPriceToBuyIn24Hours(22);
        item1.setPriceToBuyIn168Hours(23);

        ItemFilter item2 = new ItemFilter();

        assertFalse(item1.isFullyEqualTo(item2));
    }

    @Test
    public void toStringBySettings_should_print_proper_amount_of_fields() {
        ItemShownFieldsSettings settings = new ItemShownFieldsSettings();
        settings.setItemShowNameFlag(false);
        settings.setItemShowItemTypeFlag(false);
        settings.setItemShowMaxBuyPrice(false);
        settings.setItemShowBuyOrdersCountFlag(false);
        settings.setItemShowMinSellPriceFlag(false);
        settings.setItemsShowSellOrdersCountFlag(false);
        settings.setItemShowPictureFlag(false);

        ItemEntityDTO item = new ItemEntityDTO();
        assertEquals(1, item.toStringBySettings(settings).split("\n").length);

        settings.setItemShowNameFlag(true);
        assertEquals(2, item.toStringBySettings(settings).split("\n").length);

        settings.setItemShowItemTypeFlag(true);
        assertEquals(3, item.toStringBySettings(settings).split("\n").length);

        settings.setItemShowMaxBuyPrice(true);
        assertEquals(4, item.toStringBySettings(settings).split("\n").length);

        settings.setItemShowBuyOrdersCountFlag(true);
        assertEquals(5, item.toStringBySettings(settings).split("\n").length);

        settings.setItemShowMinSellPriceFlag(true);
        assertEquals(6, item.toStringBySettings(settings).split("\n").length);

        settings.setItemsShowSellOrdersCountFlag(true);
        assertEquals(7, item.toStringBySettings(settings).split("\n").length);

        settings.setItemShowPictureFlag(true);
        assertEquals(8, item.toStringBySettings(settings).split("\n").length);
    }

    @Test
    public void hashCode_should_be_equal_for_equal_itemId_ignoring_other_fields() {
        ItemEntityDTO item1 = new ItemEntityDTO();
        item1.setItemId("1");
        item1.setAssetUrl("url");
        item1.setName("name");
        item1.setTags(List.of("tag"));
        item1.setRarity(ItemRarity.UNCOMMON);
        item1.setType(ItemType.WeaponSkin);
        item1.setMaxBuyPrice(1);
        item1.setBuyOrdersCount(2);
        item1.setMinSellPrice(3);
        item1.setSellOrdersCount(4);
        item1.setLastSoldAt(LocalDateTime.of(2021, 1, 1, 1, 1));
        item1.setLastSoldPrice(5);
        item1.setMonthAveragePrice(6);
        item1.setMonthMedianPrice(7);
        item1.setMonthMaxPrice(8);
        item1.setMonthMinPrice(9);
        item1.setMonthSalesPerDay(10);
        item1.setDayAveragePrice(11);
        item1.setDayMedianPrice(12);
        item1.setDayMaxPrice(13);
        item1.setDayMinPrice(14);
        item1.setDaySales(15);
        item1.setPriceToSellIn1Hour(16);
        item1.setPriceToSellIn6Hours(17);
        item1.setPriceToSellIn24Hours(18);
        item1.setPriceToSellIn168Hours(19);
        item1.setPriceToBuyIn1Hour(20);
        item1.setPriceToBuyIn6Hours(21);
        item1.setPriceToBuyIn24Hours(22);
        item1.setPriceToBuyIn168Hours(23);

        ItemEntityDTO item2 = new ItemEntityDTO();
        item2.setItemId("1");
        item2.setAssetUrl("url2");
        item2.setName("name2");
        item2.setTags(List.of("tag2"));
        item2.setRarity(ItemRarity.RARE);
        item2.setType(ItemType.CharacterHeadgear);
        item2.setMaxBuyPrice(10);
        item2.setBuyOrdersCount(20);
        item2.setMinSellPrice(30);
        item2.setSellOrdersCount(40);
        item2.setLastSoldAt(LocalDateTime.of(2024, 1, 1, 1, 1));
        item2.setLastSoldPrice(50);
        item2.setMonthAveragePrice(60);
        item2.setMonthMedianPrice(70);
        item2.setMonthMaxPrice(80);
        item2.setMonthMinPrice(90);
        item2.setMonthSalesPerDay(100);
        item2.setDayAveragePrice(110);
        item2.setDayMedianPrice(120);
        item2.setDayMaxPrice(130);
        item2.setDayMinPrice(140);
        item2.setDaySales(150);
        item2.setPriceToSellIn1Hour(160);
        item2.setPriceToSellIn6Hours(170);
        item2.setPriceToSellIn24Hours(180);
        item2.setPriceToSellIn168Hours(190);
        item2.setPriceToBuyIn1Hour(200);
        item2.setPriceToBuyIn6Hours(210);
        item2.setPriceToBuyIn24Hours(220);
        item2.setPriceToBuyIn168Hours(230);

        assertEquals(item1.hashCode(), item2.hashCode());
    }

    @Test
    public void hashCode_should_be_different_for_different_itemId() {
        ItemEntityDTO item1 = new ItemEntityDTO();
        item1.setItemId("1");

        ItemEntityDTO item2 = new ItemEntityDTO();
        item2.setItemId("2");

        assertNotEquals(item1.hashCode(), item2.hashCode());
    }

    @Test
    public void equals_should_be_true_for_equal_itemId_and_ignore_other_fields() {
        ItemEntityDTO item1 = new ItemEntityDTO();
        item1.setItemId("1");
        item1.setAssetUrl("url");
        item1.setName("name");
        item1.setTags(List.of("tag"));
        item1.setRarity(ItemRarity.UNCOMMON);
        item1.setType(ItemType.WeaponSkin);
        item1.setMaxBuyPrice(1);
        item1.setBuyOrdersCount(2);
        item1.setMinSellPrice(3);
        item1.setSellOrdersCount(4);
        item1.setLastSoldAt(LocalDateTime.of(2021, 1, 1, 1, 1));
        item1.setLastSoldPrice(5);
        item1.setMonthAveragePrice(6);
        item1.setMonthMedianPrice(7);
        item1.setMonthMaxPrice(8);
        item1.setMonthMinPrice(9);
        item1.setMonthSalesPerDay(10);
        item1.setDayAveragePrice(11);
        item1.setDayMedianPrice(12);
        item1.setDayMaxPrice(13);
        item1.setDayMinPrice(14);
        item1.setDaySales(15);
        item1.setPriceToSellIn1Hour(16);
        item1.setPriceToSellIn6Hours(17);
        item1.setPriceToSellIn24Hours(18);
        item1.setPriceToSellIn168Hours(19);
        item1.setPriceToBuyIn1Hour(20);
        item1.setPriceToBuyIn6Hours(21);
        item1.setPriceToBuyIn24Hours(22);
        item1.setPriceToBuyIn168Hours(23);

        ItemEntityDTO item2 = new ItemEntityDTO();
        item2.setItemId("1");
        item2.setAssetUrl("url2");
        item2.setName("name2");
        item2.setTags(List.of("tag2"));
        item2.setRarity(ItemRarity.RARE);
        item2.setType(ItemType.CharacterHeadgear);
        item2.setMaxBuyPrice(10);
        item2.setBuyOrdersCount(20);
        item2.setMinSellPrice(30);
        item2.setSellOrdersCount(40);
        item2.setLastSoldAt(LocalDateTime.of(2024, 1, 1, 1, 1));
        item2.setLastSoldPrice(50);
        item2.setMonthAveragePrice(60);
        item2.setMonthMedianPrice(70);
        item2.setMonthMaxPrice(80);
        item2.setMonthMinPrice(90);
        item2.setMonthSalesPerDay(100);
        item2.setDayAveragePrice(110);
        item2.setDayMedianPrice(120);
        item2.setDayMaxPrice(130);
        item2.setDayMinPrice(140);
        item2.setDaySales(150);
        item2.setPriceToSellIn1Hour(160);
        item2.setPriceToSellIn6Hours(170);
        item2.setPriceToSellIn24Hours(180);
        item2.setPriceToSellIn168Hours(190);
        item2.setPriceToBuyIn1Hour(200);
        item2.setPriceToBuyIn6Hours(210);
        item2.setPriceToBuyIn24Hours(220);
        item2.setPriceToBuyIn168Hours(230);

        assertEquals(item1, item2);
    }

    @Test
    public void equals_should_be_false_for_different_itemId() {
        ItemEntityDTO item1 = new ItemEntityDTO();
        item1.setItemId("1");

        ItemEntityDTO item2 = new ItemEntityDTO();
        item2.setItemId("2");

        assertNotEquals(item1, item2);
    }

    @Test
    public void equals_should_be_false_for_null() {
        ItemEntityDTO item1 = new ItemEntityDTO();
        item1.setItemId("1");

        ItemEntityDTO item2 = null;

        assertNotEquals(item2, item1);
    }

    @Test
    public void equals_should_be_false_for_object_of_another_class() {
        ItemEntityDTO item1 = new ItemEntityDTO();
        item1.setItemId("1");

        ItemFilter item2 = new ItemFilter();

        assertNotEquals(item2, item1);
    }
}