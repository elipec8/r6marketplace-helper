package github.ricemonger.item_stats_fetcher;

import github.ricemonger.marketplace.databases.redis.services.RedisUtilsConfiguration;
import github.ricemonger.marketplace.graphQl.common_query_items.CommonQueryItemsGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.common_query_items_min_sell_prices.CommonQueryItemMinSellPricesGraphQlConfiguration;
import github.ricemonger.utils.SchedulingUtilsConfiguration;
import github.ricemonger.utilslibrarykafka.KafkaUtilsConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan(basePackages = {"github.ricemonger.item_stats_fetcher", "github.ricemonger.utilspostgresschema"},
        basePackageClasses = github.ricemonger.utils.PublicMethodLogger.class)
@Import({SchedulingUtilsConfiguration.class,
        RedisUtilsConfiguration.class,
        CommonQueryItemsGraphQlConfiguration.class,
        CommonQueryItemMinSellPricesGraphQlConfiguration.class,
        KafkaUtilsConfiguration.class})
public class ItemStatsFetcherApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemStatsFetcherApplication.class, args);
    }
}
