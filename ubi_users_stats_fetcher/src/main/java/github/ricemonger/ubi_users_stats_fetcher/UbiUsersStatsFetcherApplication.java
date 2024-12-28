package github.ricemonger.ubi_users_stats_fetcher;

import github.ricemonger.marketplace.databases.redis.services.RedisUtilsConfiguration;
import github.ricemonger.marketplace.graphQl.personal_mutation_buy_create.PersonalMutationBuyCreateGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.personal_mutation_buy_update.PersonalMutationBuyUpdateGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.personal_mutation_cancel.PersonalMutationCancelGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.personal_mutation_sell_create.PersonalMutationSellCreateGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.personal_mutation_sell_update.PersonalMutationSellUpdateGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.personal_query_credits_amount.PersonalQueryCreditAmountGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.personal_query_current_orders.PersonalQueryCurrentOrdersGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.personal_query_finished_orders.PersonalQueryFinishedOrdersGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.personal_query_locked_items.PersonalQueryLockedItemsGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.personal_query_owned_items.PersonalQueryOwnedItemsGraphQlConfiguration;
import github.ricemonger.utils.SchedulingUtilsConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
        RedisUtilsConfiguration.class,
        SchedulingUtilsConfiguration.class,
        PersonalQueryLockedItemsGraphQlConfiguration.class,
        PersonalQueryOwnedItemsGraphQlConfiguration.class,
        PersonalQueryCurrentOrdersGraphQlConfiguration.class,
        PersonalQueryFinishedOrdersGraphQlConfiguration.class,
        PersonalQueryCreditAmountGraphQlConfiguration.class
})
public class UbiUsersStatsFetcherApplication {

    public static void main(String[] args) {
        SpringApplication.run(UbiUsersStatsFetcherApplication.class, args);
    }

}
