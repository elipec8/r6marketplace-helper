package github.ricemonger.ubi_users_stats_fetcher.services.configurations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class UbiServiceConfiguration {
    @Value("${ubi.session.dateFormat}")
    private String dateFormat;
}
