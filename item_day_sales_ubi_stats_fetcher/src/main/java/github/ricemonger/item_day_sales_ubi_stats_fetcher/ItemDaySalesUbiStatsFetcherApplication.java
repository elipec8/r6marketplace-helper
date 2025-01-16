package github.ricemonger.item_day_sales_ubi_stats_fetcher;

import github.ricemonger.marketplace.databases.redis.services.RedisUtilsConfiguration;
import github.ricemonger.marketplace.graphQl.common_query_items_sale_stats.CommonQueryItemsSaleStatsGraphQlConfiguration;
import github.ricemonger.utils.SchedulingUtilsConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan(basePackages = {"github.ricemonger.item_day_sales_ubi_stats_fetcher", "github.ricemonger.utilspostgresschema"},
        basePackageClasses = github.ricemonger.utils.PublicMethodLogger.class)
@Import({
        SchedulingUtilsConfiguration.class,
        RedisUtilsConfiguration.class,
        CommonQueryItemsSaleStatsGraphQlConfiguration.class
})
public class ItemDaySalesUbiStatsFetcherApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemDaySalesUbiStatsFetcherApplication.class, args);
    }

}
