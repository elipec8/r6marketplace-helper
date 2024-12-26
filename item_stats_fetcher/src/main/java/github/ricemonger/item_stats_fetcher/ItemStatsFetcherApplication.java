package github.ricemonger.item_stats_fetcher;

import github.ricemonger.utils.SchedulingConfiguration;
import github.ricemonger.marketplace.databases.redis.services.RedisUtilsConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({SchedulingConfiguration.class, RedisUtilsConfiguration.class})
public class ItemStatsFetcherApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemStatsFetcherApplication.class, args);
    }
}
