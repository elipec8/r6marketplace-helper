package github.ricemonger.ubi_users_stats_fetcher;

import github.ricemonger.marketplace.databases.redis.services.RedisUtilsConfiguration;
import github.ricemonger.marketplace.graphQl.personal_query_user_stats.PersonalQueryUserStatsGraphQlConfiguration;
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
        PersonalQueryUserStatsGraphQlConfiguration.class,
        TradeStatsCalculatorUtilsConfiguration.class
})
public class UbiUsersStatsFetcherApplication {

    public static void main(String[] args) {
        SpringApplication.run(UbiUsersStatsFetcherApplication.class, args);
    }

}
