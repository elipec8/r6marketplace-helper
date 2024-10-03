package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.ItemDatabaseService;
import github.ricemonger.marketplace.services.abstractions.ItemSaleDatabaseService;
import github.ricemonger.marketplace.services.abstractions.ItemSaleHistoryDatabaseService;
import github.ricemonger.marketplace.services.abstractions.ItemSaleUbiStatsService;
import github.ricemonger.utils.dtos.*;
import github.ricemonger.utils.enums.FilterType;
import github.ricemonger.utils.enums.ItemRarity;
import github.ricemonger.utils.enums.TagGroup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;

@SpringBootTest
class ItemServiceTest {
    @Autowired
    private ItemService itemService;
    @MockBean
    private ItemDatabaseService itemDatabaseService;
    @MockBean
    private ItemSaleDatabaseService saleService;
    @MockBean
    private ItemSaleHistoryDatabaseService historyService;
    @MockBean
    private TagService tagService;
    @MockBean
    private CommonValuesService commonValuesService;
    @MockBean
    private ItemSaleUbiStatsService itemSaleUbiStatsService;

    @Test
    public void saveAllItemsAndSales_should_set_unknown_rarity_for_unspecified_rarity_tag_and_handle_to_services() {
        Item item = new Item();
        item.setItemId("1");
        item.setTags(new ArrayList<>());

        Collection<Item> items = new ArrayList<>();
        items.add(item);

        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("UNCOMMON", "UNCOMMON", TagGroup.Rarity));
        tags.add(new Tag("RARE", "RARE", TagGroup.Rarity));
        tags.add(new Tag("EPIC", "EPIC", TagGroup.Rarity));
        tags.add(new Tag("LEGENDARY", "LEGENDARY", TagGroup.Rarity));
        when(tagService.getTagsByNames(new ArrayList<>(List.of("UNCOMMON", "RARE", "EPIC", "LEGENDARY")))).thenReturn(tags);

        when(commonValuesService.getMinimumMarketplacePrice()).thenReturn(100);
        when(commonValuesService.getMaximumMarketplacePrice()).thenReturn(200);

        itemService.saveAllItemsAndSales(items);

        assertEquals(ItemRarity.UNKNOWN, item.getRarity());

        verify(itemDatabaseService).saveAll(same(items));
        verify(saleService).saveAll(same(items));
    }

    @Test
    public void saveAllItemsUbiStats_should_handle_to_service(){
        List<ItemSaleUbiStats> stats = new ArrayList<>();
        itemService.saveAllItemsUbiStats(stats);
        verify(itemSaleUbiStatsService).saveAll(same(stats));
    }

    @Test
    public void saveAllItemsAndSales_should_set_rarity_for_uncommon() {
        Item item = new Item();
        item.setItemId("1");
        item.setTags(List.of("UNCOMMON"));

        Collection<Item> items = new ArrayList<>();
        items.add(item);

        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("UNCOMMON", "UNCOMMON", TagGroup.Rarity));
        tags.add(new Tag("RARE", "RARE", TagGroup.Rarity));
        tags.add(new Tag("EPIC", "EPIC", TagGroup.Rarity));
        tags.add(new Tag("LEGENDARY", "LEGENDARY", TagGroup.Rarity));
        when(tagService.getTagsByNames(new ArrayList<>(List.of("UNCOMMON", "RARE", "EPIC", "LEGENDARY")))).thenReturn(tags);

        when(commonValuesService.getMinimumUncommonPrice()).thenReturn(300);
        when(commonValuesService.getMaximumUncommonPrice()).thenReturn(400);

        itemService.saveAllItemsAndSales(items);

        assertEquals(ItemRarity.UNCOMMON, item.getRarity());
    }

    @Test
    public void saveAllItemsAndSales_should_set_rarity_for_rare() {
        Item item = new Item();
        item.setItemId("1");
        item.setTags(List.of("RARE"));

        Collection<Item> items = new ArrayList<>();
        items.add(item);

        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("UNCOMMON", "UNCOMMON", TagGroup.Rarity));
        tags.add(new Tag("RARE", "RARE", TagGroup.Rarity));
        tags.add(new Tag("EPIC", "EPIC", TagGroup.Rarity));
        tags.add(new Tag("LEGENDARY", "LEGENDARY", TagGroup.Rarity));
        when(tagService.getTagsByNames(new ArrayList<>(List.of("UNCOMMON", "RARE", "EPIC", "LEGENDARY")))).thenReturn(tags);

        when(commonValuesService.getMinimumRarePrice()).thenReturn(500);
        when(commonValuesService.getMaximumRarePrice()).thenReturn(600);

        itemService.saveAllItemsAndSales(items);

        assertEquals(ItemRarity.RARE, item.getRarity());
    }

    @Test
    public void saveAllItemsAndSales_should_set_rarity_for_epic() {
        Item item = new Item();
        item.setItemId("1");
        item.setTags(List.of("EPIC"));

        Collection<Item> items = new ArrayList<>();
        items.add(item);

        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("UNCOMMON", "UNCOMMON", TagGroup.Rarity));
        tags.add(new Tag("RARE", "RARE", TagGroup.Rarity));
        tags.add(new Tag("EPIC", "EPIC", TagGroup.Rarity));
        tags.add(new Tag("LEGENDARY", "LEGENDARY", TagGroup.Rarity));
        when(tagService.getTagsByNames(new ArrayList<>(List.of("UNCOMMON", "RARE", "EPIC", "LEGENDARY")))).thenReturn(tags);

        when(commonValuesService.getMinimumEpicPrice()).thenReturn(700);
        when(commonValuesService.getMaximumEpicPrice()).thenReturn(800);

        itemService.saveAllItemsAndSales(items);

        assertEquals(ItemRarity.EPIC, item.getRarity());
    }

    @Test
    public void saveAllItemsAndSales_should_set_rarity_for_legendary() {
        Item item = new Item();
        item.setItemId("1");
        item.setTags(List.of("LEGENDARY"));

        Collection<Item> items = new ArrayList<>();
        items.add(item);

        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("UNCOMMON", "UNCOMMON", TagGroup.Rarity));
        tags.add(new Tag("RARE", "RARE", TagGroup.Rarity));
        tags.add(new Tag("EPIC", "EPIC", TagGroup.Rarity));
        tags.add(new Tag("LEGENDARY", "LEGENDARY", TagGroup.Rarity));
        when(tagService.getTagsByNames(new ArrayList<>(List.of("UNCOMMON", "RARE", "EPIC", "LEGENDARY")))).thenReturn(tags);

        when(commonValuesService.getMinimumLegendaryPrice()).thenReturn(900);
        when(commonValuesService.getMaximumLegendaryPrice()).thenReturn(1000);

        itemService.saveAllItemsAndSales(items);

        assertEquals(ItemRarity.LEGENDARY, item.getRarity());
    }

    @Test
    public void calculateAndSaveItemsSaleHistoryStats_should_handle_to_services() {
        Item item1 = new Item();
        item1.setItemId("1");

        Item item2 = new Item();
        item2.setItemId("2");

        List<Item> items = new ArrayList<>();
        when(itemDatabaseService.findAll()).thenReturn(items);

        ItemSale sale1 = new ItemSale();
        sale1.setItemId("1");
        sale1.setLastSoldAt(new Date());

        ItemSale sale2 = new ItemSale();
        sale2.setItemId("1");
        sale2.setLastSoldAt(new Date(new Date().getTime() + 1000));

        ItemSale sale3 = new ItemSale();
        sale2.setItemId("3");
        sale2.setLastSoldAt(new Date());

        ItemSale sale4 = new ItemSale();
        sale2.setItemId("4");
        sale2.setLastSoldAt(new Date());

        List<ItemSale> sales = List.of(sale1, sale2, sale3, sale4);
        when(saleService.findAll()).thenReturn(sales);

        itemService.calculateAndSaveItemsSaleHistoryStats();
        verify(itemDatabaseService).findAll();
        verify(saleService).findAll();

        verify(historyService).saveAll(any());
    }

    @Test
    public void getItemById_should_handle_to_service() {
        Item item = new Item();
        item.setItemId("1");
        when(itemDatabaseService.findById("1")).thenReturn(item);

        Item result = itemService.getItemById("1");

        assertEquals(item, result);
    }

    @Test
    public void getAllItemsByFilter_should_get_all_items_and_call_filter_method_on_them() {
        List<Item> items = new ArrayList<>();
        when(itemDatabaseService.findAll()).thenReturn(items);

        ItemFilter filter = spy(ItemFilter.class);
        filter.setFilterType(FilterType.ALLOW);

        itemService.getAllItemsByFilters(List.of(filter));

        verify(itemDatabaseService).findAll();

        verify(filter).filterItems(items);
    }

    @Test
    public void getAllItems_should_return_service_result() {
        List<Item> items = new ArrayList<>();
        when(itemDatabaseService.findAll()).thenReturn(items);

        List<Item> result = itemService.getAllItems();

        assertSame(items, result);
    }
}