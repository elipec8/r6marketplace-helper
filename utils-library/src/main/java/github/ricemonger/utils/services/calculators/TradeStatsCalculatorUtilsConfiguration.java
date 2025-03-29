package github.ricemonger.utils.services.calculators;

import org.springframework.context.annotation.Bean;

public class TradeStatsCalculatorUtilsConfiguration {

    @Bean
    public ItemFancyPriceCalculator itemFancyPriceCalculator(PricesCommonValuesService pricesCommonValuesService) {
        return new ItemFancyPriceCalculator(pricesCommonValuesService);
    }

    @Bean
    public ItemTradeTimeCalculator itemTradeTimeCalculator(PricesCommonValuesService pricesCommonValuesService, ItemFancyPriceCalculator itemFancyPriceCalculator) {
        return new ItemTradeTimeCalculator(pricesCommonValuesService, itemFancyPriceCalculator);
    }

    @Bean
    public ItemPotentialTradeStatsCalculator itemPotentialTradeStatsCalculator(ItemFancyPriceCalculator itemFancyPriceCalculator, ItemTradeTimeCalculator itemTradeTimeCalculator) {
        return new ItemPotentialTradeStatsCalculator(itemFancyPriceCalculator, itemTradeTimeCalculator);
    }

    @Bean
    public ItemTradePriorityByExpressionCalculator itemTradePriorityByExpressionCalculator() {
        return new ItemTradePriorityByExpressionCalculator();
    }
}
