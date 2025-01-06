package github.ricemonger.item_trade_stats_calculator.postgres.services;

import github.ricemonger.item_trade_stats_calculator.postgres.entities.ItemSaleEntity;
import github.ricemonger.item_trade_stats_calculator.postgres.repositories.ItemSalePostgresRepository;
import github.ricemonger.item_trade_stats_calculator.postgres.services.entity_mappers.item.ItemSaleEntityMapper;
import github.ricemonger.utils.DTOs.common.ItemSale;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ItemSalePostgresServiceTest {
    @Autowired
    private ItemSalePostgresService itemSalePostgresService;
    @MockBean
    private ItemSalePostgresRepository itemSaleRepository;
    @MockBean
    private ItemSaleEntityMapper itemSaleEntityMapper;

    @Test
    public void findAllForLastMonth_should_return_mapped_dtos() {
        ItemSaleEntity entity1 = Mockito.mock(ItemSaleEntity.class);
        ItemSaleEntity entity2 = Mockito.mock(ItemSaleEntity.class);

        ItemSale dto1 = Mockito.mock(ItemSale.class);
        ItemSale dto2 = Mockito.mock(ItemSale.class);

        Mockito.when(itemSaleRepository.findAllForLastMonth()).thenReturn(List.of(entity1, entity2));

        Mockito.when(itemSaleEntityMapper.createDTO(entity1)).thenReturn(dto1);
        Mockito.when(itemSaleEntityMapper.createDTO(entity2)).thenReturn(dto2);

        List<ItemSale> result = itemSalePostgresService.findAllForLastMonth();

        assertEquals(2, result.size());
        assertTrue(result.contains(dto1));
        assertTrue(result.contains(dto2));
    }
}