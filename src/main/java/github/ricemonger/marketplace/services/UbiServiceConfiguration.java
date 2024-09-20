package github.ricemonger.marketplace.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class UbiServiceConfiguration {
    @Value("${ubi.urls.graphql}")
    private String graphqlUrl;
    @Value("${ubi.urls.authorization}")
    private String authorizationUrl;
    @Value("${ubi.session.contentType}")
    private String contentType;
    @Value("${ubi.session.userAgent}")
    private String userAgent;
    @Value("${ubi.session.appId}")
    private String ubiAppId;
    @Value("${ubi.session.spaceId}")
    private String ubiSpaceId;
    @Value("${ubi.session.regionId}")
    private String ubiRegionId;
    @Value("${ubi.session.localeCode}")
    private String UbiLocaleCode;
    @Value("${ubi.session.expireTimeout}")
    private Integer expireTimeout;
    @Value("${ubi.session.dateFormat}")
    private String dateFormat;
    @Value("${ubi.session.itemSaleStatsDateFormat}")
    private String itemSaleStatsDateFormat;
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
