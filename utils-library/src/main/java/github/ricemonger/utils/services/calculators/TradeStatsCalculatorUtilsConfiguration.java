package github.ricemonger.utils.services.calculators;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TradeStatsCalculatorUtilsConfiguration {

    @Bean
    public ItemFancyPriceCalculator itemFancyPriceCalculator(CalculatorsCommonValuesService calculatorsCommonValuesService) {
        return new ItemFancyPriceCalculator(calculatorsCommonValuesService);
    }

    @Bean
    public ItemTradeTimeCalculator itemTradeTimeCalculator(CalculatorsCommonValuesService calculatorsCommonValuesService, ItemFancyPriceCalculator itemFancyPriceCalculator) {
        return new ItemTradeTimeCalculator(calculatorsCommonValuesService, itemFancyPriceCalculator);
    }

    @Bean
    ItemTradePriorityCalculator itemTradePriorityCalculator(CalculatorsCommonValuesService calculatorsCommonValuesService) {
        return new ItemTradePriorityCalculator(calculatorsCommonValuesService);
    }

    @Bean
    public ItemTradeStatsCalculator itemTradeStatsCalculator(ItemFancyPriceCalculator itemFancyPriceCalculator, ItemTradeTimeCalculator itemTradeTimeCalculator, ItemTradePriorityCalculator itemTradePriorityCalculator) {
        return new ItemTradeStatsCalculator(itemFancyPriceCalculator, itemTradeTimeCalculator, itemTradePriorityCalculator);
    }
}
