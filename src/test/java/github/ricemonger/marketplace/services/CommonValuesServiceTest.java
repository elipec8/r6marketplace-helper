package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.CommonValuesDatabaseService;
import github.ricemonger.telegramBot.TelegramBotConfiguration;
import github.ricemonger.utils.dtos.AuthorizationDTO;
import github.ricemonger.utils.dtos.ConfigResolvedTransactionPeriod;
import github.ricemonger.utils.dtos.ConfigTrades;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CommonValuesServiceTest {
    @Autowired
    private CommonValuesService commonValuesService;
    @MockBean
    private CommonValuesDatabaseService commonValuesDatabaseService;
    @MockBean
    private MainUserConfiguration mainUserConfiguration;
    @MockBean
    private UbiServiceConfiguration ubiServiceConfiguration;
    @SpyBean
    private TelegramBotConfiguration telegramBotConfiguration;

    @Test
    void getExpectedItemCount_should_handle_to_service() {
        int expectedItemCount = 10;
        when(commonValuesDatabaseService.getExpectedItemCount()).thenReturn(expectedItemCount);

        assertEquals(expectedItemCount, commonValuesService.getExpectedItemCount());
    }

    @Test
    void setExpectedItemCount_should_handle_to_service() {
        int expectedItemCount = 10;
        commonValuesService.setExpectedItemCount(expectedItemCount);

        verify(commonValuesDatabaseService).setExpectedItemCount(expectedItemCount);
    }

    @Test
    void getConfigResolvedTransactionPeriod_should_handle_to_service() {
        ConfigResolvedTransactionPeriod configResolvedTransactionPeriod = new ConfigResolvedTransactionPeriod(10, 20);
        when(commonValuesDatabaseService.getConfigResolvedTransactionPeriod()).thenReturn(configResolvedTransactionPeriod);

        assertEquals(configResolvedTransactionPeriod, commonValuesService.getConfigResolvedTransactionPeriod());
    }

    @Test
    void setConfigResolvedTransactionPeriod_should_handle_to_service() {
        ConfigResolvedTransactionPeriod configResolvedTransactionPeriod = new ConfigResolvedTransactionPeriod(10, 20);
        commonValuesService.setConfigResolvedTransactionPeriod(configResolvedTransactionPeriod);

        verify(commonValuesDatabaseService).setConfigResolvedTransactionPeriod(configResolvedTransactionPeriod);
    }

    @Test
    void getConfigTrades_should_handle_to_service() {
        ConfigTrades configTrades = new ConfigTrades();
        when(commonValuesDatabaseService.getConfigTrades()).thenReturn(configTrades);

        assertEquals(configTrades, commonValuesService.getConfigTrades());
    }

    @Test
    void setConfigTrades_should_handle_to_service() {
        ConfigTrades configTrades = new ConfigTrades();
        commonValuesService.setConfigTrades(configTrades);

        verify(commonValuesDatabaseService).setConfigTrades(configTrades);
    }

    @Test
    void getPaymentItemId_should_handle_to_service() {
        String paymentItemId = "paymentItemId";
        when(commonValuesDatabaseService.getPaymentItemId()).thenReturn(paymentItemId);

        assertEquals(paymentItemId, commonValuesService.getPaymentItemId());
    }

    @Test
    void getMainUserAuthorizationToken_should_handle_to_service() {
        String mainUserAuthorizationToken
                = "mainUserAuthorizationToken";
        when(commonValuesDatabaseService.getMainUserAuthorizationToken()).thenReturn(mainUserAuthorizationToken);

        assertEquals(mainUserAuthorizationToken, commonValuesService.getMainUserAuthorizationToken());
    }

    @Test
    void getMainUserProfileId_should_handle_to_service() {
        String mainUserProfileId = "mainUserProfileId";
        when(commonValuesDatabaseService.getMainUserProfileId()).thenReturn(mainUserProfileId);

        assertEquals(mainUserProfileId, commonValuesService.getMainUserProfileId());
    }

    @Test
    void getMainUserSessionId_should_handle_to_service() {
        String mainUserSessionId = "mainUserSessionId";
        when(commonValuesDatabaseService.getMainUserSessionId()).thenReturn(mainUserSessionId);

        assertEquals(mainUserSessionId, commonValuesService.getMainUserSessionId());
    }

    @Test
    void getMainUserRememberMeTicket_should_handle_to_service() {
        String mainUserRememberMeTicket = "mainUserRememberMeTicket";
        when(commonValuesDatabaseService.getMainUserRememberMeTicket()).thenReturn(mainUserRememberMeTicket);

        assertEquals(mainUserRememberMeTicket, commonValuesService.getMainUserRememberMeTicket());
    }

    @Test
    void setMainUserAuthorization_should_handle_to_service() {
        commonValuesService.setMainUserAuthorization(new AuthorizationDTO());

        verify(commonValuesDatabaseService).setMainUserAuthorization(new AuthorizationDTO(), 0);
    }

    @Test
    void getMainUserEmail_should_handle_to_service() {
        String email = "email";
        when(mainUserConfiguration.getEmail()).thenReturn(email);

        assertEquals(email, commonValuesService.getMainUserEmail());
    }

    @Test
    void getMainUserPassword_should_handle_to_service() {
        String password = "password";
        when(mainUserConfiguration.getPassword()).thenReturn(password);

        assertEquals(password, commonValuesService.getMainUserPassword());
    }

    @Test
    void getMainUserPlatform_should_handle_to_service() {
        String platform = "platform";
        when(mainUserConfiguration.getPlatform()).thenReturn(platform);

        assertEquals(platform, commonValuesService.getMainUserPlatform());
    }

    @Test
    void getGraphqlUrl_should_handle_to_service() {
        String graphqlUrl = "graphqlUrl";
        when(ubiServiceConfiguration.getGraphqlUrl()).thenReturn(graphqlUrl);

        assertEquals(graphqlUrl, commonValuesService.getGraphqlUrl());
    }

    @Test
    void getAuthorizationUrl_should_handle_to_service() {
        String authorizationUrl = "authorizationUrl";
        when(ubiServiceConfiguration.getAuthorizationUrl()).thenReturn(authorizationUrl);

        assertEquals(authorizationUrl, commonValuesService.getAuthorizationUrl());
    }

    @Test
    void getContentType_should_handle_to_service() {
        String contentType = "contentType";
        when(ubiServiceConfiguration.getContentType()).thenReturn(contentType);

        assertEquals(contentType, commonValuesService.getContentType());
    }

    @Test
    void getUserAgent_should_handle_to_service() {
        String userAgent = "userAgent";
        when(ubiServiceConfiguration.getUserAgent()).thenReturn(userAgent);

        assertEquals(userAgent, commonValuesService.getUserAgent());
    }

    @Test
    void getUbiAppId_should_handle_to_service() {
        String ubiAppId = "ubiAppId";
        when(ubiServiceConfiguration.getUbiAppId()).thenReturn(ubiAppId);

        assertEquals(ubiAppId, commonValuesService.getUbiAppId());
    }

    @Test
    void getUbiGameSpaceId_should_handle_to_service() {
        String ubiGameSpaceId = "ubiGameSpaceId";
        when(ubiServiceConfiguration.getUbiSpaceId()).thenReturn(ubiGameSpaceId);

        assertEquals(ubiGameSpaceId, commonValuesService.getUbiGameSpaceId());
    }

    @Test
    void getRegionId_should_handle_to_service() {
        String regionId = "regionId";
        when(ubiServiceConfiguration.getUbiRegionId()).thenReturn(regionId);

        assertEquals(regionId, commonValuesService.getUbiRegionId());
    }

    @Test
    void getLocaleCode_should_handle_to_service() {
        String localeCode = "localeCode";
        when(ubiServiceConfiguration.getUbiLocaleCode()).thenReturn(localeCode);

        assertEquals(localeCode, commonValuesService.getUbiLocaleCode());
    }

    @Test
    void getExpireTimeout_should_handle_to_service() {
        int expireTimeout = 10;
        when(ubiServiceConfiguration.getExpireTimeout()).thenReturn(expireTimeout);

        assertEquals(expireTimeout, commonValuesService.getExpireTimeout());
    }

    @Test
    void getDateFormat_should_handle_to_service() {
        String dateFormat = "dateFormat";
        when(ubiServiceConfiguration.getDateFormat()).thenReturn(dateFormat);

        assertEquals(dateFormat, commonValuesService.getDateFormat());
    }

    @Test
    public void getMinimumUncommonPrice_should_handle_to_service() {
        Integer minPrice = 10;
        when(ubiServiceConfiguration.getMinUncommonPrice()).thenReturn(minPrice);

        assertEquals(minPrice, commonValuesService.getMinimumUncommonPrice());
    }

    @Test
    public void getMaximumUncommonPrice_should_handle_to_service() {
        Integer maxPrice = 10;
        when(ubiServiceConfiguration.getMaxUncommonPrice()).thenReturn(maxPrice);

        assertEquals(maxPrice, commonValuesService.getMaximumUncommonPrice());
    }

    @Test
    public void getMinimumRarePrice_should_handle_to_service() {
        Integer minPrice = 10;
        when(ubiServiceConfiguration.getMinRarePrice()).thenReturn(minPrice);

        assertEquals(minPrice, commonValuesService.getMinimumRarePrice());
    }

    @Test
    public void getMaximumRarePrice_should_handle_to_service() {
        Integer maxPrice = 10;
        when(ubiServiceConfiguration.getMaxRarePrice()).thenReturn(maxPrice);

        assertEquals(maxPrice, commonValuesService.getMaximumRarePrice());
    }

    @Test
    public void getMinimumEpicPrice_should_handle_to_service() {
        Integer minPrice = 10;
        when(ubiServiceConfiguration.getMinEpicPrice()).thenReturn(minPrice);

        assertEquals(minPrice, commonValuesService.getMinimumEpicPrice());
    }

    @Test
    public void getMaximumEpicPrice_should_handle_to_service() {
        Integer maxPrice = 10;
        when(ubiServiceConfiguration.getMaxEpicPrice()).thenReturn(maxPrice);

        assertEquals(maxPrice, commonValuesService.getMaximumEpicPrice());
    }

    @Test
    public void getMinimumLegendaryPrice_should_handle_to_service() {
        Integer minPrice = 10;
        when(ubiServiceConfiguration.getMinLegendaryPrice()).thenReturn(minPrice);

        assertEquals(minPrice, commonValuesService.getMinimumLegendaryPrice());
    }

    @Test
    public void getMaximumLegendaryPrice_should_handle_to_service() {
        Integer maxPrice = 10;
        when(ubiServiceConfiguration.getMaxLegendaryPrice()).thenReturn(maxPrice);

        assertEquals(maxPrice, commonValuesService.getMaximumLegendaryPrice());
    }

    @Test
    public void getMinimumMarketplacePrice_should_handle_to_service() {
        Integer minPrice = 10;
        when(ubiServiceConfiguration.getMinUncommonPrice()).thenReturn(minPrice + 2);
        when(ubiServiceConfiguration.getMinRarePrice()).thenReturn(minPrice + 1);
        when(ubiServiceConfiguration.getMinEpicPrice()).thenReturn(minPrice);
        when(ubiServiceConfiguration.getMinLegendaryPrice()).thenReturn(minPrice + 3);

        assertEquals(minPrice, commonValuesService.getMinimumMarketplacePrice());
    }

    @Test
    public void getMaximumMarketplacePrice_should_handle_to_service() {
        Integer maxPrice = 10;
        when(ubiServiceConfiguration.getMaxLegendaryPrice()).thenReturn(maxPrice);

        assertEquals(maxPrice, commonValuesService.getMaximumMarketplacePrice());
    }

    @Test
    public void getMaximumTelegramMessageHeight_should_handle_to_service() {
        Integer messageHeight = 10;
        when(telegramBotConfiguration.getMessageHeight()).thenReturn(messageHeight);

        assertEquals(messageHeight, commonValuesService.getMaximumTelegramMessageHeight());
    }

    @Test
    public void getMaximumTelegramMessageLimit_should_handle_to_service() {
        Integer messageLimit = 10;
        when(telegramBotConfiguration.getMessageLimit()).thenReturn(messageLimit);

        assertEquals(messageLimit, commonValuesService.getMaximumTelegramMessageLimit());
    }
}