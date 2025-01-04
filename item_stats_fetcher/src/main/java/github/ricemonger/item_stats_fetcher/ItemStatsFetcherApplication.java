package github.ricemonger.item_stats_fetcher;

import github.ricemonger.marketplace.databases.redis.services.RedisUtilsConfiguration;
import github.ricemonger.marketplace.graphQl.common_query_items.CommonQueryItemsGraphQlConfiguration;
import github.ricemonger.utils.SchedulingUtilsConfiguration;
import github.ricemonger.utilslibrarykafka.KafkaUtilsConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({SchedulingUtilsConfiguration.class,
        RedisUtilsConfiguration.class,
        CommonQueryItemsGraphQlConfiguration.class,
        KafkaUtilsConfiguration.class})
public class ItemStatsFetcherApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemStatsFetcherApplication.class, args);
    }
}
