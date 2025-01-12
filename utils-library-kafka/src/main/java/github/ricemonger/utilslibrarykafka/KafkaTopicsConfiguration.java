package github.ricemonger.utilslibrarykafka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicsConfiguration {

    @Bean
    public PrivateNotificationKafkaTopic privateNotificationKafkaTopic() {
        return new PrivateNotificationKafkaTopic("private.notification", 2, (short) 1);
    }

    @Bean
    public PublicNotificationKafkaTopic publicNotificationKafkaTopic() {
        return new PublicNotificationKafkaTopic("public.notification", 2, (short) 1);
    }

    @Bean
    public PublicAllUsersNotificationKafkaTopic publicAllUsersNotificationKafkaTopic() {
        return new PublicAllUsersNotificationKafkaTopic("public.all.users.notification", 2, (short) 1);
    }

    @Bean
    public UbiStatsUpdatedNotificationKafkaTopic ubiStatsUpdatedNotificationKafkaTopic() {
        return new UbiStatsUpdatedNotificationKafkaTopic("private.ubi_stats_updated.notification", 2, (short) 1);
    }

    @Bean
    public TradeManagerNotificationKafkaTopic tradeManagerNotificationKafkaTopic() {
        return new TradeManagerNotificationKafkaTopic("private.trade_manager.notification", 2, (short) 1);
    }

    @Bean
    public AuthorizationNotificationKafkaTopic authorizationNotificationKafkaTopic() {
        return new AuthorizationNotificationKafkaTopic("private.authorization.notification", 2, (short) 1);
    }
}
