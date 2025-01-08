package github.ricemonger.item_stats_fetcher.scheduled_tasks;

import github.ricemonger.item_stats_fetcher.services.CommonValuesService;
import github.ricemonger.item_stats_fetcher.services.ItemService;
import github.ricemonger.item_stats_fetcher.services.NotificationService;
import github.ricemonger.marketplace.graphQl.common_query_items.CommonQueryItemsGraphQlClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ScheduledAllItemsStatsFetcherTest {

    @MockBean
    private CommonQueryItemsGraphQlClientService graphQlClientService;

    @MockBean
    private NotificationService telegramBotService;

    @MockBean
    private ItemService itemService;

    @MockBean
    private CommonValuesService commonValuesService;

    @Autowired
    private ScheduledAllItemsStatsFetcher scheduledAllItemsStatsFetcher;

    @Test
    public void fetchAllItemStats_should_save_items_and_insert_sales_if_totat_amount_is_smaller_then_count() {
        List itemMainFields = mock(List.class);
        when(itemMainFields.size()).thenReturn(1);
        when(commonValuesService.getExpectedItemCountOrZero()).thenReturn(2);

        when(graphQlClientService.fetchAllItemStats()).thenReturn(itemMainFields);

        scheduledAllItemsStatsFetcher.fetchAllItemStats();

        verify(itemService,never()).updateAllItemsMainFieldsExceptTags(any());
        verify(itemService).saveAllItemsMainFields(same(itemMainFields));
        verify(itemService).insertAllItemsLastSales(same(itemMainFields));
    }

    @Test
    public void fetchAllItemStats_should_save_items_and_insert_sales_if_totat_amount_is_bigger_then_count_and_call_on_item_amount_increase() {
        List itemMainFields = mock(List.class);
        when(itemMainFields.size()).thenReturn(2);
        when(commonValuesService.getExpectedItemCountOrZero()).thenReturn(1);

        when(graphQlClientService.fetchAllItemStats()).thenReturn(itemMainFields);

        scheduledAllItemsStatsFetcher.fetchAllItemStats();

        verify(itemService,never()).updateAllItemsMainFieldsExceptTags(any());
        verify(itemService).saveAllItemsMainFields(same(itemMainFields));
        verify(itemService).insertAllItemsLastSales(same(itemMainFields));
        verify(commonValuesService).setExpectedItemCount(2);
        verify(telegramBotService).notifyAllUsersAboutItemAmountIncrease(1, 2);
    }

    @Test
    public void fetchAllItemStats_should_update_items_and_insert_sales_if_totat_amount_is_bigger_then_count() {
        List itemMainFields = mock(List.class);
        when(itemMainFields.size()).thenReturn(2);
        when(commonValuesService.getExpectedItemCountOrZero()).thenReturn(2);

        when(graphQlClientService.fetchAllItemStats()).thenReturn(itemMainFields);

        scheduledAllItemsStatsFetcher.fetchAllItemStats();

        verify(itemService,never()).saveAllItemsMainFields(any());
        verify(itemService).updateAllItemsMainFieldsExceptTags(same(itemMainFields));
        verify(itemService).insertAllItemsLastSales(same(itemMainFields));
    }
}