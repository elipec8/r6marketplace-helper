package github.ricemonger.fast_sell_trade_manager.services.configurations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class UbiServiceConfiguration {
    @Value("${ubi.session.minUncommonPrice}")
    private Integer minUncommonPrice;
    @Value("${ubi.session.maxUncommonPrice}")
    private Integer maxUncommonPrice;
    @Value("${ubi.session.minRarePrice}")
    private Integer minRarePrice;
    @Value("${ubi.session.maxRarePrice}")
    private Integer maxRarePrice;
    @Value("${ubi.session.minEpicPrice}")
    private Integer minEpicPrice;
    @Value("${ubi.session.maxEpicPrice}")
    private Integer maxEpicPrice;
    @Value("${ubi.session.minLegendaryPrice}")
    private Integer minLegendaryPrice;
    @Value("${ubi.session.maxLegendaryPrice}")
    private Integer maxLegendaryPrice;
}
