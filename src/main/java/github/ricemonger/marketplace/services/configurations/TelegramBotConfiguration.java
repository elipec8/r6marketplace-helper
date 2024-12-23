package github.ricemonger.marketplace.services.configurations;

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

    @Value("${telegram.bot.message.height}")
    private Integer messageHeight;

    @Value("${telegram.bot.message.limit}")
    private Integer messageLimit;
}
