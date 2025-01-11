package github.ricemonger.item_trade_stats_calculator;

import github.ricemonger.utils.SchedulingUtilsConfiguration;
import github.ricemonger.utils.services.calculators.TradeStatsCalculatorUtilsConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan(basePackages = {"github.ricemonger.item_trade_stats_calculator"},
        basePackageClasses = github.ricemonger.utils.PublicMethodLogger.class)
@EntityScan(basePackages = "github.ricemonger.utilspostgresschema")
@Import({SchedulingUtilsConfiguration.class,
        TradeStatsCalculatorUtilsConfiguration.class})
public class ItemTradeStatsCalculatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemTradeStatsCalculatorApplication.class, args);
    }

}
