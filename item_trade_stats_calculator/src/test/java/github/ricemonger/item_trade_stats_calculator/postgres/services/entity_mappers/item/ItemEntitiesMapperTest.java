package github.ricemonger.item_trade_stats_calculator.postgres.services.entity_mappers.item;

import github.ricemonger.item_trade_stats_calculator.postgres.dto_projections.ItemHistoryFieldsProjection;
import github.ricemonger.item_trade_stats_calculator.postgres.dto_projections.ItemRecalculationRequiredFieldsProjection;
import github.ricemonger.item_trade_stats_calculator.services.DTOs.ItemHistoryFields;
import github.ricemonger.item_trade_stats_calculator.services.DTOs.ItemRecalculationRequiredFields;
import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.DTOs.common.ItemHistoryFieldsI;
import github.ricemonger.utils.enums.ItemRarity;
import github.ricemonger.utils.enums.ItemType;
import github.ricemonger.utilspostgresschema.full_entities.item.ItemEntity;
import github.ricemonger.utilspostgresschema.full_entities.item.TagEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ItemEntitiesMapperTest {

    @Autowired
    private ItemEntitiesMapper itemEntitiesMapper;

    @Test
    public void createRecalculationRequiredFieldsDTO_should_return_expected_result() {
        ItemRecalculationRequiredFieldsProjection projection = new ItemRecalculationRequiredFieldsProjection();
        projection.setItemId("itemId");
        projection.setRarity(ItemRarity.RARE);
        projection.setMaxBuyPrice(1);
        projection.setBuyOrdersCount(2);
        projection.setMinSellPrice(3);
        projection.setSellOrdersCount(4);

        ItemRecalculationRequiredFields result = itemEntitiesMapper.createRecalculationRequiredFieldsDTO(projection);

        assertEquals(projection.getItemId(), result.getItemId());
        assertEquals(projection.getRarity(), result.getRarity());
        assertEquals(projection.getMaxBuyPrice(), result.getMaxBuyPrice());
        assertEquals(projection.getBuyOrdersCount(), result.getBuyOrdersCount());
        assertEquals(projection.getMinSellPrice(), result.getMinSellPrice());
        assertEquals(projection.getSellOrdersCount(), result.getSellOrdersCount());
    }

    @Test
    public void createHistoryFieldsDtoProjection_should_return_expected_result() {
        ItemHistoryFieldsI dto = new ItemHistoryFields();

        dto.setItemId("itemId");
        dto.setMonthAveragePrice(1);
        dto.setMonthMedianPrice(2);
        dto.setMonthMaxPrice(3);
        dto.setMonthMinPrice(4);
        dto.setMonthSalesPerDay(5);
        dto.setMonthSales(6);
        dto.setDayAveragePrice(7);
        dto.setDayMedianPrice(8);
        dto.setDayMaxPrice(9);
        dto.setDayMinPrice(10);
        dto.setDaySales(11);
        dto.setPriceToBuyIn1Hour(20);
        dto.setPriceToBuyIn6Hours(21);
        dto.setPriceToBuyIn24Hours(22);
        dto.setPriceToBuyIn168Hours(23);
        dto.setPriceToBuyIn720Hours(24);

        ItemHistoryFieldsProjection result = itemEntitiesMapper.createHistoryFieldsDtoProjection(dto);

        assertEquals(dto.getItemId(), result.getItemId());
        assertEquals(dto.getMonthAveragePrice(), result.getMonthAveragePrice());
        assertEquals(dto.getMonthMedianPrice(), result.getMonthMedianPrice());
        assertEquals(dto.getMonthMaxPrice(), result.getMonthMaxPrice());
        assertEquals(dto.getMonthMinPrice(), result.getMonthMinPrice());
        assertEquals(dto.getMonthSalesPerDay(), result.getMonthSalesPerDay());
        assertEquals(dto.getMonthSales(), result.getMonthSales());
        assertEquals(dto.getDayAveragePrice(), result.getDayAveragePrice());
        assertEquals(dto.getDayMedianPrice(), result.getDayMedianPrice());
        assertEquals(dto.getDayMaxPrice(), result.getDayMaxPrice());
        assertEquals(dto.getDayMinPrice(), result.getDayMinPrice());
        assertEquals(dto.getDaySales(), result.getDaySales());
        assertEquals(dto.getPriceToBuyIn1Hour(), result.getPriceToBuyIn1Hour());
        assertEquals(dto.getPriceToBuyIn6Hours(), result.getPriceToBuyIn6Hours());
        assertEquals(dto.getPriceToBuyIn24Hours(), result.getPriceToBuyIn24Hours());
        assertEquals(dto.getPriceToBuyIn168Hours(), result.getPriceToBuyIn168Hours());
        assertEquals(dto.getPriceToBuyIn720Hours(), result.getPriceToBuyIn720Hours());
    }

    @Test
    public void createItem_should_properly_map_entity_to_dto() {
        ItemEntity entity = new ItemEntity();
        entity.setItemId("itemId");
        entity.setAssetUrl("assetUrl");
        entity.setName("name");
        TagEntity tagEntity1 = new TagEntity();
        tagEntity1.setValue("tag1");
        TagEntity tagEntity2 = new TagEntity();
        tagEntity2.setValue("tag2");
        entity.setTags(List.of(tagEntity1, tagEntity2));
        entity.setRarity(ItemRarity.RARE);
        entity.setType(ItemType.WeaponSkin);
        entity.setMaxBuyPrice(1);
        entity.setBuyOrdersCount(2);
        entity.setMinSellPrice(3);
        entity.setSellOrdersCount(4);
        entity.setLastSoldAt(LocalDateTime.of(2021, 1, 1, 1, 1));
        entity.setLastSoldPrice(5);
        entity.setMonthAveragePrice(6);
        entity.setMonthMedianPrice(7);
        entity.setMonthMaxPrice(8);
        entity.setMonthMinPrice(9);
        entity.setMonthSalesPerDay(10);
        entity.setMonthSales(11);
        entity.setDayAveragePrice(12);
        entity.setDayMedianPrice(13);
        entity.setDayMaxPrice(14);
        entity.setDayMinPrice(15);
        entity.setDaySales(16);
        entity.setPriceToBuyIn1Hour(25);
        entity.setPriceToBuyIn6Hours(26);
        entity.setPriceToBuyIn24Hours(27);
        entity.setPriceToBuyIn168Hours(28);
        entity.setPriceToBuyIn720Hours(29);

        Item item = itemEntitiesMapper.createItem(entity);

        assertEquals(entity.getItemId(), item.getItemId());
        assertEquals(entity.getAssetUrl(), item.getAssetUrl());
        assertEquals(entity.getName(), item.getName());
        assertTrue(item.getTags().contains("tag1") && item.getTags().contains("tag2"));
        assertEquals(entity.getRarity(), item.getRarity());
        assertEquals(entity.getType(), item.getType());
        assertEquals(entity.getMaxBuyPrice(), item.getMaxBuyPrice());
        assertEquals(entity.getBuyOrdersCount(), item.getBuyOrdersCount());
        assertEquals(entity.getMinSellPrice(), item.getMinSellPrice());
        assertEquals(entity.getSellOrdersCount(), item.getSellOrdersCount());
        assertEquals(entity.getLastSoldAt(), item.getLastSoldAt());
        assertEquals(entity.getLastSoldPrice(), item.getLastSoldPrice());
        assertEquals(entity.getMonthAveragePrice(), item.getMonthAveragePrice());
        assertEquals(entity.getMonthMedianPrice(), item.getMonthMedianPrice());
        assertEquals(entity.getMonthMaxPrice(), item.getMonthMaxPrice());
        assertEquals(entity.getMonthMinPrice(), item.getMonthMinPrice());
        assertEquals(entity.getMonthSalesPerDay(), item.getMonthSalesPerDay());
        assertEquals(entity.getMonthSales(), item.getMonthSales());
        assertEquals(entity.getDayAveragePrice(), item.getDayAveragePrice());
        assertEquals(entity.getDayMedianPrice(), item.getDayMedianPrice());
        assertEquals(entity.getDayMaxPrice(), item.getDayMaxPrice());
        assertEquals(entity.getDayMinPrice(), item.getDayMinPrice());
        assertEquals(entity.getDaySales(), item.getDaySales());
        assertEquals(entity.getPriceToBuyIn1Hour(), item.getPriceToBuyIn1Hour());
        assertEquals(entity.getPriceToBuyIn6Hours(), item.getPriceToBuyIn6Hours());
        assertEquals(entity.getPriceToBuyIn24Hours(), item.getPriceToBuyIn24Hours());
        assertEquals(entity.getPriceToBuyIn168Hours(), item.getPriceToBuyIn168Hours());
        assertEquals(entity.getPriceToBuyIn720Hours(), item.getPriceToBuyIn720Hours());
    }
}