package github.ricemonger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class R6smarketplaceHelperApplication {

    public static void main(String[] args) {
        SpringApplication.run(R6smarketplaceHelperApplication.class, args);
    }

}
