package github.ricemonger.users_ubi_accs_reauthorizer;

import github.ricemonger.marketplace.databases.redis.services.RedisUtilsConfiguration;
import github.ricemonger.utils.SchedulingUtilsConfiguration;
import github.ricemonger.utilslibrarykafka.KafkaUtilsConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
        KafkaUtilsConfiguration.class,
        SchedulingUtilsConfiguration.class,
        RedisUtilsConfiguration.class
})
public class UsersUbiAccsReauthorizerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsersUbiAccsReauthorizerApplication.class, args);
    }

}
