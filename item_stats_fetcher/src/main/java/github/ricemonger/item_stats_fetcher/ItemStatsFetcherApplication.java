package github.ricemonger.item_stats_fetcher;

import github.ricemonger.marketplace.graphQl.client_services.fetchAllItemsStats.FetchAllItemsStatsGraphQlClientService;
import github.ricemonger.utilslibraryredis.services.RedisUtilsConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({RedisUtilsConfiguration.class})
public class ItemStatsFetcherApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemStatsFetcherApplication.class, args);
    }
}
