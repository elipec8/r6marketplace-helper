package github.ricemonger.item_trade_stats_calculator.postgres.services.entity_mappers.item;

import github.ricemonger.item_trade_stats_calculator.postgres.entities.item.ItemHistoryFieldsEntity;
import github.ricemonger.item_trade_stats_calculator.postgres.entities.item.ItemRecalculationRequiredFieldsEntity;
import github.ricemonger.item_trade_stats_calculator.services.DTOs.ItemHistoryFields;
import github.ricemonger.item_trade_stats_calculator.services.DTOs.ItemRecalculationRequiredFields;
import github.ricemonger.utils.DTOs.common.ItemHistoryFieldsI;
import github.ricemonger.utils.enums.ItemRarity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ItemEntitiesMapperTest {

    @Autowired
    private ItemEntitiesMapper itemEntitiesMapper;

    @Test
    public void createRecalculationRequiredFieldsDTO_should_return_expected_result() {
        ItemRecalculationRequiredFieldsEntity entity = new ItemRecalculationRequiredFieldsEntity();
        entity.setItemId("itemId");
        entity.setRarity(ItemRarity.RARE);
        entity.setMaxBuyPrice(1);
        entity.setBuyOrdersCount(2);
        entity.setMinSellPrice(3);
        entity.setSellOrdersCount(4);

        ItemRecalculationRequiredFields result = itemEntitiesMapper.createRecalculationRequiredFieldsDTO(entity);

        assertEquals(entity.getItemId(), result.getItemId());
        assertEquals(entity.getRarity(), result.getRarity());
        assertEquals(entity.getMaxBuyPrice(), result.getMaxBuyPrice());
        assertEquals(entity.getBuyOrdersCount(), result.getBuyOrdersCount());
        assertEquals(entity.getMinSellPrice(), result.getMinSellPrice());
        assertEquals(entity.getSellOrdersCount(), result.getSellOrdersCount());
    }

    @Test
    public void createHistoryFieldsEntity_should_return_expected_result() {
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
        dto.setPriorityToSellByMaxBuyPrice(12L);
        dto.setPriorityToSellByNextFancySellPrice(13L);
        dto.setPriorityToBuyByMinSellPrice(14L);
        dto.setPriorityToBuyIn1Hour(15L);
        dto.setPriorityToBuyIn6Hours(16L);
        dto.setPriorityToBuyIn24Hours(17L);
        dto.setPriorityToBuyIn168Hours(18L);
        dto.setPriorityToBuyIn720Hours(19L);
        dto.setPriceToBuyIn1Hour(20);
        dto.setPriceToBuyIn6Hours(21);
        dto.setPriceToBuyIn24Hours(22);
        dto.setPriceToBuyIn168Hours(23);
        dto.setPriceToBuyIn720Hours(24);

        ItemHistoryFieldsEntity result = itemEntitiesMapper.createHistoryFieldsEntity(dto);

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
        assertEquals(dto.getPriorityToSellByMaxBuyPrice(), result.getPriorityToSellByMaxBuyPrice());
        assertEquals(dto.getPriorityToSellByNextFancySellPrice(), result.getPriorityToSellByNextFancySellPrice());
        assertEquals(dto.getPriorityToBuyByMinSellPrice(), result.getPriorityToBuyByMinSellPrice());
        assertEquals(dto.getPriorityToBuyIn1Hour(), result.getPriorityToBuyIn1Hour());
        assertEquals(dto.getPriorityToBuyIn6Hours(), result.getPriorityToBuyIn6Hours());
        assertEquals(dto.getPriorityToBuyIn24Hours(), result.getPriorityToBuyIn24Hours());
        assertEquals(dto.getPriorityToBuyIn168Hours(), result.getPriorityToBuyIn168Hours());
        assertEquals(dto.getPriorityToBuyIn720Hours(), result.getPriorityToBuyIn720Hours());
        assertEquals(dto.getPriceToBuyIn1Hour(), result.getPriceToBuyIn1Hour());
        assertEquals(dto.getPriceToBuyIn6Hours(), result.getPriceToBuyIn6Hours());
        assertEquals(dto.getPriceToBuyIn24Hours(), result.getPriceToBuyIn24Hours());
        assertEquals(dto.getPriceToBuyIn168Hours(), result.getPriceToBuyIn168Hours());
        assertEquals(dto.getPriceToBuyIn720Hours(), result.getPriceToBuyIn720Hours());
    }

}