package github.ricemonger.notifications_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"github.ricemonger.notifications_service"},
        basePackageClasses = github.ricemonger.utils.PublicMethodLogger.class)
@EntityScan(basePackages = "github.ricemonger.utilspostgresschema")
public class NotificationsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationsServiceApplication.class, args);
    }

}
