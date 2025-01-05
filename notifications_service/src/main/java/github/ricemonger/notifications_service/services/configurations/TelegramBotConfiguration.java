package github.ricemonger.notifications_service.services.configurations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Setter
@Getter
public class TelegramBotConfiguration {

    @Value("${auth.telegram.bot.token}")
    private String TELEGRAM_BOT_TOKEN;

    @Value("${auth.telegram.bot.username}")
    private String TELEGRAM_BOT_USERNAME;

    @Value("${telegram.bot.message.height}")
    private Integer messageHeight;

    @Value("${telegram.bot.message.limit}")
    private Integer messageLimit;
}
