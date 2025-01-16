package github.ricemonger.configs_fetcher;

import github.ricemonger.marketplace.databases.redis.services.RedisUtilsConfiguration;
import github.ricemonger.marketplace.graphQl.config_query_marketplace.ConfigQueryMarketplaceGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.config_query_resolved_transactions_period.ConfigQueryResolvedTransactionPeriodGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.config_query_trade.ConfigQueryTradeGraphQlConfiguration;
import github.ricemonger.utils.SchedulingUtilsConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan(basePackages = {"github.ricemonger.configs_fetcher", "github.ricemonger.utilspostgresschema"},
        basePackageClasses = github.ricemonger.utils.PublicMethodLogger.class)
@EntityScan(basePackages = "github.ricemonger.utilspostgresschema")
@Import({SchedulingUtilsConfiguration.class,
        RedisUtilsConfiguration.class,
        ConfigQueryMarketplaceGraphQlConfiguration.class,
        ConfigQueryResolvedTransactionPeriodGraphQlConfiguration.class,
        ConfigQueryTradeGraphQlConfiguration.class})
public class ConfigsFetcherApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigsFetcherApplication.class, args);
    }

}
