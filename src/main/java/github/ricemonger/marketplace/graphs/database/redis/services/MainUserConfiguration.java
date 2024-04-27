package github.ricemonger.marketplace.graphs.database.redis.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainUserConfiguration {

    @Value("${auth.ubi_credentials.email}")
    private String email;
    @Value("${auth.ubi_credentials.password}")
    private String password;
    @Value("${auth.ubi_credentials.platform}")
    private String platform;
}
