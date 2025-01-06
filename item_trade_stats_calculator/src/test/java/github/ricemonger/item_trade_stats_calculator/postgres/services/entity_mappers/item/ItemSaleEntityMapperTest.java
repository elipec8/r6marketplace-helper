package github.ricemonger.item_trade_stats_calculator.postgres.services.entity_mappers.item;

import github.ricemonger.item_trade_stats_calculator.postgres.entities.ItemIdEntity;
import github.ricemonger.item_trade_stats_calculator.postgres.entities.ItemSaleEntity;
import github.ricemonger.utils.DTOs.common.ItemSale;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ItemSaleEntityMapperTest {
    @Autowired
    private ItemSaleEntityMapper itemSaleEntityMapper;

    @Test
    public void createDTO_should_return_expected_dto() {
        ItemSaleEntity entity = new ItemSaleEntity();
        entity.setItem(new ItemIdEntity("itemId"));
        entity.setSoldAt(LocalDateTime.of(2021, 9, 1, 0, 0));
        entity.setPrice(100);

        ItemSale itemSale = itemSaleEntityMapper.createDTO(entity);

        assertEquals("itemId", itemSale.getItemId());
        assertEquals(LocalDateTime.of(2021, 9, 1, 0, 0), itemSale.getSoldAt());
        assertEquals(100, itemSale.getPrice());
    }
}