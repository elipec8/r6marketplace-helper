package github.ricemonger.main_user_reauthorizer;

import github.ricemonger.marketplace.databases.redis.services.RedisUtilsConfiguration;
import github.ricemonger.utils.SchedulingUtilsConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
        SchedulingUtilsConfiguration.class,
        RedisUtilsConfiguration.class
})
public class MainUserReauthorizerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainUserReauthorizerApplication.class, args);
    }

}
