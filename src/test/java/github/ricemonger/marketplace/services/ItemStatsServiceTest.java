package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.ItemDatabaseService;
import github.ricemonger.marketplace.services.abstractions.ItemSaleDatabaseService;
import github.ricemonger.marketplace.services.abstractions.ItemSaleHistoryDatabaseService;
import github.ricemonger.utils.dtos.Item;
import github.ricemonger.utils.dtos.ItemFilter;
import github.ricemonger.utils.enums.FilterType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ItemStatsServiceTest {

    @MockBean
    private ItemDatabaseService itemService;

    @MockBean
    private ItemSaleDatabaseService saleService;

    @MockBean
    private ItemSaleHistoryDatabaseService historyService;

    @MockBean
    private ProfitAndPriorityCalculator profitAndPriorityCalculator;

    @Autowired
    private ItemStatsService itemStatsService;

    @Test
    public void saveAllItemsAndSales_should_handle_to_services() {
        Collection<Item> items = new ArrayList<>();
        itemStatsService.saveAllItemsAndSales(items);
        verify(itemService).saveAll(same(items));
        verify(saleService).saveAll(same(items));
    }

    @Test
    public void calculateAndSaveItemsSaleHistoryStats_should_handle_to_services() {
        itemStatsService.calculateAndSaveItemsSaleHistoryStats();
        verify(itemService).findAll();
        verify(saleService).findAll();
        verify(historyService).saveAll(same(new ArrayList<>()));
    }
}