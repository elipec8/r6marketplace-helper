package github.ricemonger.ubi_users_stats_fetcher;

import github.ricemonger.marketplace.databases.redis.services.RedisUtilsConfiguration;
import github.ricemonger.marketplace.graphQl.personal_query_credits_amount.PersonalQueryCreditAmountGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.personal_query_current_orders.PersonalQueryCurrentOrdersGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.personal_query_finished_orders.PersonalQueryFinishedOrdersGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.personal_query_locked_items.PersonalQueryLockedItemsGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.personal_query_owned_items.PersonalQueryOwnedItemsGraphQlConfiguration;
import github.ricemonger.utils.SchedulingUtilsConfiguration;
import github.ricemonger.utils.services.calculators.TradeStatsCalculatorUtilsConfiguration;
import github.ricemonger.utilslibrarykafka.KafkaUtilsConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan(basePackages = {"github.ricemonger.ubi_users_stats_fetcher", "github.ricemonger.utilspostgresschema"},
        basePackageClasses = github.ricemonger.utils.PublicMethodLogger.class)
@EntityScan(basePackages = "github.ricemonger.utilspostgresschema")
@Import({
        KafkaUtilsConfiguration.class,
        RedisUtilsConfiguration.class,
        SchedulingUtilsConfiguration.class,
        PersonalQueryLockedItemsGraphQlConfiguration.class,
        PersonalQueryOwnedItemsGraphQlConfiguration.class,
        PersonalQueryCurrentOrdersGraphQlConfiguration.class,
        PersonalQueryFinishedOrdersGraphQlConfiguration.class,
        PersonalQueryCreditAmountGraphQlConfiguration.class,
        TradeStatsCalculatorUtilsConfiguration.class
})
public class UbiUsersStatsFetcherApplication {

    public static void main(String[] args) {
        SpringApplication.run(UbiUsersStatsFetcherApplication.class, args);
    }

}
