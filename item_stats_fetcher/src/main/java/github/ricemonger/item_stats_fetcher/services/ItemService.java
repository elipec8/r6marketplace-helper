package github.ricemonger.item_stats_fetcher.services;

import github.ricemonger.item_stats_fetcher.services.abstractions.ItemDatabaseService;
import github.ricemonger.utils.DTOs.common.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class ItemService {

    private final ItemDatabaseService itemDatabaseService;

    public void saveAllItemsMainFields(Collection<Item> items) {
        System.out.println("Saving all items main fields");
    }

    public void saveAllItemLastSales(Collection<Item> items) {
        System.out.println("Saving all item last sales");
    }
}
