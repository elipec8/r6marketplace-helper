package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.entities.ItemSaleEntity;
import github.ricemonger.marketplace.databases.postgres.entities.ItemSaleHistoryEntity;
import github.ricemonger.marketplace.databases.postgres.mappers.ItemPostgresMapper;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemSaleHistoryPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemSalePostgresRepository;
import github.ricemonger.utils.dtos.Item;
import github.ricemonger.utils.dtos.ItemSale;
import github.ricemonger.utils.dtos.ItemSaleHistory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ItemPostgresServiceTest {

    @MockBean
    private ItemPostgresRepository itemPostgresRepository;

    @MockBean
    private ItemSalePostgresRepository itemSalePostgresRepository;

    @MockBean
    private ItemSaleHistoryPostgresRepository itemSaleHistoryPostgresRepository;

    @MockBean
    private ItemPostgresMapper mapper;

    @Autowired
    private ItemPostgresService itemPostgresService;

    @Test
    public void saveAllItemsAndItemSales_should_map_and_call_repositories() {
        List<ItemEntity> itemEntities = new ArrayList<>();
        when(mapper.mapItemEntities(anyCollection())).thenReturn(itemEntities);
        List<ItemSaleEntity> itemSaleEntities = new ArrayList<>();
        when(mapper.mapItemSaleEntities(anyCollection())).thenReturn(itemSaleEntities);

        itemPostgresService.saveAllItems(List.of(new Item()));

        verify(itemPostgresRepository).saveAll(new HashSet<>(itemEntities));
        verify(itemSalePostgresRepository).saveAll(new HashSet<>(itemSaleEntities));
    }

    @Test
    public void saveAllItemsAndItemSales_should_not_throw_if_empty() {
        Executable executable = () -> itemPostgresService.saveAllItems(new HashSet<>());

        assertDoesNotThrow(executable);
    }

    @Test
    public void findAllItems_should_map_and_return() {
        List<ItemEntity> itemEntities = new ArrayList<>();
        when(itemPostgresRepository.findAll()).thenReturn(itemEntities);

        List<Item> items = new ArrayList<>();
        when(mapper.mapItems(itemEntities)).thenReturn(items);

        assertEquals(items, itemPostgresService.findAllItems());
    }

    @Test
    public void findAllItems_should_return_empty_list_if_empty() {
        List<ItemEntity> itemEntities = new ArrayList<>();
        when(itemPostgresRepository.findAll()).thenReturn(itemEntities);

        List<Item> items = new ArrayList<>();
        when(mapper.mapItems(itemEntities)).thenReturn(items);

        assertEquals(items, itemPostgresService.findAllItems());
    }

    @Test
    public void findAllItemSales_should_map_and_return() {
        List<ItemSaleEntity> itemSaleEntities = new ArrayList<>();
        when(itemSalePostgresRepository.findAll()).thenReturn(itemSaleEntities);

        List<ItemSale> itemSales = new ArrayList<>();
        when(mapper.mapItemSales(itemSaleEntities)).thenReturn(itemSales);

        assertEquals(itemSales, itemPostgresService.findAllItemSales());
    }

    @Test
    public void findAllItemSales_should_return_empty_list_if_empty() {
        List<ItemSaleEntity> itemSaleEntities = new ArrayList<>();
        when(itemSalePostgresRepository.findAll()).thenReturn(itemSaleEntities);

        List<ItemSale> itemSales = new ArrayList<>();
        when(mapper.mapItemSales(itemSaleEntities)).thenReturn(itemSales);

        assertEquals(itemSales, itemPostgresService.findAllItemSales());
    }

    @Test
    public void saveAllItemSaleHistoryStats_should_map_and_call_repository() {
        List<ItemSaleHistoryEntity> itemSaleHistoryEntities = List.of(new ItemSaleHistoryEntity());
        when(mapper.mapItemSaleHistoryEntities(anyCollection())).thenReturn(itemSaleHistoryEntities);

        itemPostgresService.saveAllItemSaleHistoryStats(List.of(new ItemSaleHistory()));

        verify(itemSaleHistoryPostgresRepository).saveAll(itemSaleHistoryEntities);
    }

    @Test
    public void saveAllItemSaleHistoryStats_should_not_throw_if_empty() {
        Executable executable = () -> itemPostgresService.saveAllItemSaleHistoryStats(new ArrayList<>());

        assertDoesNotThrow(executable);
    }

    @Test
    public void findAllItemsByIds_should_map_and_return() {
        List<ItemEntity> itemEntities = new ArrayList<>();
        when(itemPostgresRepository.findAllById(anyCollection())).thenReturn(itemEntities);

        List<Item> items = new ArrayList<>();
        when(mapper.mapItems(itemEntities)).thenReturn(items);

        assertEquals(items, itemPostgresService.findAllItemsByIds(new ArrayList<>()));
    }

    @Test
    public void findAllItemsByIds_should_return_empty_list_if_empty_ids() {
        List<ItemEntity> itemEntities = new ArrayList<>();
        when(itemPostgresRepository.findAllById(anyCollection())).thenReturn(itemEntities);

        List<Item> items = new ArrayList<>();
        when(mapper.mapItems(itemEntities)).thenReturn(items);

        assertEquals(items, itemPostgresService.findAllItemsByIds(new ArrayList<>()));
    }
}