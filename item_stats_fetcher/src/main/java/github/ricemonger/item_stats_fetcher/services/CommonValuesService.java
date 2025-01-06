package github.ricemonger.item_stats_fetcher.services;

import github.ricemonger.utils.abstract_services.CommonValuesDatabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommonValuesService {

    private final CommonValuesDatabaseService commonValuesDatabaseService;

    public int getExpectedItemCount() {
        return commonValuesDatabaseService.getExpectedItemCount();
    }

    public void setExpectedItemCount(int fetchedItemsCount) {
        commonValuesDatabaseService.setExpectedItemCount(fetchedItemsCount);
    }
}
