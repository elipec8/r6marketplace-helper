package github.ricemonger.marketplace.services.configurations;

import github.ricemonger.utils.services.calculators.ItemTradePriorityByExpressionCalculator;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ItemTradePriorityByExpressionCalculatorConfiguration {

    @Bean
    public ItemTradePriorityByExpressionCalculator itemTradePriorityByExpressionCalculator() {
        return new ItemTradePriorityByExpressionCalculator();
    }
}
