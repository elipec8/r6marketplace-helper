package github.ricemonger.item_trade_stats_calculator;

import github.ricemonger.utils.SchedulingUtilsConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan(basePackages = {"github.ricemonger.item_trade_stats_calculator"},
        basePackageClasses = github.ricemonger.utils.PublicMethodLogger.class)
@Import({SchedulingUtilsConfiguration.class})
public class ItemTradeStatsCalculatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemTradeStatsCalculatorApplication.class, args);
    }

}
