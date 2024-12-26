package github.ricemonger;

import github.ricemonger.marketplace.databases.redis.services.RedisUtilsConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({RedisUtilsConfiguration.class})
public class R6sMarketplaceHelperApplication {

    public static void main(String[] args) {
        SpringApplication.run(R6sMarketplaceHelperApplication.class, args);
    }

}
