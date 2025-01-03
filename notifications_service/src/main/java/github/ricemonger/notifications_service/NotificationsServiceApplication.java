package github.ricemonger.notifications_service;

import github.ricemonger.marketplace.databases.redis.services.RedisUtilsConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({RedisUtilsConfiguration.class})
@EntityScan(basePackages = "github.ricemonger.utilspostgresschema")
public class NotificationsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationsServiceApplication.class, args);
    }

}
