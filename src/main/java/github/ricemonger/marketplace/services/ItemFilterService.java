package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.ItemFilterDatabaseService;
import github.ricemonger.utils.dtos.ItemFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemFilterService {

    private final ItemFilterDatabaseService itemFilterDatabaseService;

    public void saveItemFilter(ItemFilter itemFilter) {
        itemFilterDatabaseService.saveItemFilter(itemFilter);
    }
}
