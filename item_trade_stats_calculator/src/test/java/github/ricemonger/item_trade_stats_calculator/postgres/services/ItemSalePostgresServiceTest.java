package github.ricemonger.item_trade_stats_calculator.postgres.services;

import github.ricemonger.item_trade_stats_calculator.postgres.dto_projections.ItemSaleProjection;
import github.ricemonger.item_trade_stats_calculator.postgres.repositories.CustomItemSalePostgresRepository;
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
    private CustomItemSalePostgresRepository itemSaleRepository;
    @MockBean
    private ItemSaleEntityMapper itemSaleEntityMapper;

    @Test
    public void findAllForLastMonth_should_return_mapped_dtos() {
        ItemSaleProjection projection1 = Mockito.mock(ItemSaleProjection.class);
        ItemSaleProjection projection2 = Mockito.mock(ItemSaleProjection.class);

        ItemSale dto1 = Mockito.mock(ItemSale.class);
        ItemSale dto2 = Mockito.mock(ItemSale.class);

        Mockito.when(itemSaleRepository.findAllForLastMonth()).thenReturn(List.of(projection1, projection2));

        Mockito.when(itemSaleEntityMapper.createDTO(projection1)).thenReturn(dto1);
        Mockito.when(itemSaleEntityMapper.createDTO(projection2)).thenReturn(dto2);

        List<ItemSale> result = itemSalePostgresService.findAllForLastMonth();

        assertEquals(2, result.size());
        assertTrue(result.contains(dto1));
        assertTrue(result.contains(dto2));
    }
}