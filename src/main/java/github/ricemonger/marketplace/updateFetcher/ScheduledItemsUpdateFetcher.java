package github.ricemonger.marketplace.updateFetcher;

import github.ricemonger.marketplace.updateFetcher.feign.ItemsUpdateFetcherFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduledItemsUpdateFetcher {

    private final ItemsUpdateFetcherFeignClient itemsUpdateFetcherFeignClient;

    @Setter
    @Value("${ubi.urls.updateItem}")
    private String url;

    @Scheduled(fixedRate = 5 * 60 * 1000) // 5min
    public void fetchUpdates(){
        int offset = 0;

    }
}
