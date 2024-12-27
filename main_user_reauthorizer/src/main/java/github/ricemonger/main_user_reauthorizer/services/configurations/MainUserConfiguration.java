package github.ricemonger.main_user_reauthorizer.services.configurations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Setter
@Getter
public class MainUserConfiguration {
    @Value("${auth.ubi_credentials.email}")
    private String email;
    @Value("${auth.ubi_credentials.password}")
    private String password;
    @Value("${auth.ubi_credentials.platform}")
    private String platform;
}
