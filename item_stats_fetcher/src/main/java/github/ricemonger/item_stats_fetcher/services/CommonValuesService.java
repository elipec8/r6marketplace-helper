package github.ricemonger.item_stats_fetcher.services;

import github.ricemonger.utils.abstract_services.CommonValuesDatabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommonValuesService {

    private final CommonValuesDatabaseService commonValuesDatabaseService;

    public int getExpectedItemCountOrZero() {
        try {
            return commonValuesDatabaseService.getExpectedItemCount();
        } catch (Exception e) {
            return 0;
        }
    }

    public void setExpectedItemCount(int fetchedItemsCount) {
        commonValuesDatabaseService.setExpectedItemCount(fetchedItemsCount);
    }
}
