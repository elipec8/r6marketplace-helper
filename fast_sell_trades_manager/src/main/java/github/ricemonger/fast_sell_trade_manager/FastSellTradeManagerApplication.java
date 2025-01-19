package github.ricemonger.fast_sell_trade_manager;

import github.ricemonger.marketplace.databases.redis.services.RedisUtilsConfiguration;
import github.ricemonger.marketplace.graphQl.personal_mutation_cancel.PersonalMutationCancelGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.personal_mutation_sell_create.PersonalMutationSellCreateGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.personal_mutation_sell_update.PersonalMutationSellUpdateGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.personal_query_owned_items_prices_and_current_sell_orders.PersonalQueryOwnedItemsPricesAndCurrentSellOrdersGraphQlConfiguration;
import github.ricemonger.utils.SchedulingUtilsConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan(basePackages = {"github.ricemonger.fast_sell_trade_manager", "github.ricemonger.utilspostgresschema"},
        basePackageClasses = github.ricemonger.utils.PublicMethodLogger.class)
@EntityScan(basePackages = "github.ricemonger.utilspostgresschema")
@Import({
        RedisUtilsConfiguration.class,
        SchedulingUtilsConfiguration.class,
        PersonalMutationSellCreateGraphQlConfiguration.class,
        PersonalMutationSellUpdateGraphQlConfiguration.class,
        PersonalMutationCancelGraphQlConfiguration.class,
        PersonalQueryOwnedItemsPricesAndCurrentSellOrdersGraphQlConfiguration.class
})
public class FastSellTradeManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastSellTradeManagerApplication.class, args);
    }
}
