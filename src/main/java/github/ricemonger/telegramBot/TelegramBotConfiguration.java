package github.ricemonger.telegramBot;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Setter
@Getter
public class TelegramBotConfiguration {

    @Value("${TELEGRAM_BOT_TOKEN}")
    private String TELEGRAM_BOT_TOKEN;

    @Value("${TELEGRAM_BOT_USERNAME}")
    private String TELEGRAM_BOT_USERNAME;
}
