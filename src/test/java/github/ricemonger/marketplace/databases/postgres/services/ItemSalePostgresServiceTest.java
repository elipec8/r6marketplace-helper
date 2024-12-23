package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemSaleEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemSalePostgresRepository;
import github.ricemonger.marketplace.databases.postgres.services.entity_mappers.item.ItemSaleEntityMapper;
import github.ricemonger.utils.DTOs.common.ItemSale;
import github.ricemonger.utils.DTOs.common.SoldItemDetails;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class ItemSalePostgresServiceTest {
    @SpyBean
    private ItemSalePostgresService itemSaleService;
    @MockBean
    private ItemSalePostgresRepository itemSaleRepository;
    @MockBean
    private ItemSaleEntityMapper itemSaleEntityMapper;

    @Test
    public void saveAll_should_map_and_save_all_dtos() {
        List<SoldItemDetails> soldItems = List.of();
        List<ItemSaleEntity> entities = List.of();

        when(itemSaleEntityMapper.createEntities(same(soldItems))).thenReturn(entities);

        itemSaleService.saveAll(soldItems);

        verify(itemSaleRepository).saveAll(same(entities));
    }

    @Test
    public void findAllForLastMonth_should_map_and_return_filtered_entities() {
        ItemSaleEntity entity1 = new ItemSaleEntity();
        ItemSaleEntity entity2 = new ItemSaleEntity();
        List<ItemSaleEntity> entities = List.of(entity1, entity2);
        when(itemSaleRepository.findAllForLastMonth()).thenReturn(entities);

        ItemSale sale1 = new ItemSale();
        ItemSale sale2 = new ItemSale();
        List<ItemSale> sales = List.of(sale1, sale2);
        when(itemSaleEntityMapper.createDTO(same(entity1))).thenReturn(sale1);
        when(itemSaleEntityMapper.createDTO(same(entity2))).thenReturn(sale2);

        List<ItemSale> result = itemSaleService.findAllForLastMonth();

        assertTrue(result.size() == sales.size() && result.stream().allMatch(res -> sales.stream().anyMatch(sale -> sale == res)));
    }

    @Test
    public void findAll_should_map_and_return_all_entities() {
        ItemSaleEntity entity1 = new ItemSaleEntity();
        ItemSaleEntity entity2 = new ItemSaleEntity();
        List<ItemSaleEntity> entities = List.of(entity1, entity2);
        when(itemSaleRepository.findAllForLastMonth()).thenReturn(entities);

        ItemSale sale1 = new ItemSale();
        ItemSale sale2 = new ItemSale();
        List<ItemSale> sales = List.of(sale1, sale2);
        when(itemSaleEntityMapper.createDTO(same(entity1))).thenReturn(sale1);
        when(itemSaleEntityMapper.createDTO(same(entity2))).thenReturn(sale2);

        List<ItemSale> result = itemSaleService.findAllForLastMonth();

        System.out.println("sales:");
        for (ItemSale sale : sales) {
            System.out.println(sale);
        }

        System.out.println("result:");
        for (ItemSale itemSale : result) {
            System.out.println(itemSale);
        }

        assertTrue(result.size() == sales.size() && result.stream().allMatch(res -> sales.stream().anyMatch(sale -> sale == res)));
    }
}