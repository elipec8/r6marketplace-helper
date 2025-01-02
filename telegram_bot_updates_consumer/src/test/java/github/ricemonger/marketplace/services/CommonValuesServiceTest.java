package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.configurations.TelegramBotConfiguration;
import github.ricemonger.marketplace.services.configurations.UbiServiceConfiguration;
import github.ricemonger.utils.enums.ItemRarity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class CommonValuesServiceTest {
    @Autowired
    private CommonValuesService commonValuesService;
    @MockBean
    private UbiServiceConfiguration ubiServiceConfiguration;
    @SpyBean
    private TelegramBotConfiguration telegramBotConfiguration;

    @Test
    void getTrustedDevice_Id_should_handle_to_service() {
        String trustedDevice = "trustedDevice";
        when(ubiServiceConfiguration.getTrustedDeviceId()).thenReturn(trustedDevice);

        assertEquals(trustedDevice, commonValuesService.getTrustedDeviceId());
    }

    @Test
    void getTrustedDeviceFriendlyName_should_handle_to_service() {
        String friendlyName = "friendlyName";
        when(ubiServiceConfiguration.getTrustedDeviceFriendlyName()).thenReturn(friendlyName);

        assertEquals(friendlyName, commonValuesService.getTrustedDeviceFriendlyName());
    }

    @Test
    void getAuthorizationUrl_should_handle_to_service() {
        String authorizationUrl = "authorizationUrl";
        when(ubiServiceConfiguration.getAuthorizationUrl()).thenReturn(authorizationUrl);

        assertEquals(authorizationUrl, commonValuesService.getAuthorizationUrl());
    }

    @Test
    public void getGetTwoFaCodeToSmsUrl_should_handle_to_service() {
        String twoFaCodeToSmsUrl = "twoFaCodeToSmsUrl";
        when(ubiServiceConfiguration.getTwoFaCodeToSmsUrl()).thenReturn(twoFaCodeToSmsUrl);

        assertEquals(twoFaCodeToSmsUrl, commonValuesService.getTwoFaCodeToSmsUrl());
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
    public void getUbiTwoFaAppId_should_handle_to_service() {
        String ubiTwoFaAppId = "ubiTwoFaAppId";
        when(ubiServiceConfiguration.getUbiTwoFaAppId()).thenReturn(ubiTwoFaAppId);

        assertEquals(ubiTwoFaAppId, commonValuesService.getUbiTwoFaAppId());
    }

    @Test
    public void getMinimumPriceByRarity_should_return_for_uncommon() {
        Integer minPrice = 10;
        when(ubiServiceConfiguration.getMinUncommonPrice()).thenReturn(minPrice);

        assertEquals(minPrice, commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON));
    }

    @Test
    public void getMinimumPriceByRarity_should_return_for_rare() {
        Integer minPrice = 10;
        when(ubiServiceConfiguration.getMinRarePrice()).thenReturn(minPrice);

        assertEquals(minPrice, commonValuesService.getMinimumPriceByRarity(ItemRarity.RARE));
    }

    @Test
    public void getMinimumPriceByRarity_should_return_for_epic() {
        Integer minPrice = 10;
        when(ubiServiceConfiguration.getMinEpicPrice()).thenReturn(minPrice);

        assertEquals(minPrice, commonValuesService.getMinimumPriceByRarity(ItemRarity.EPIC));
    }

    @Test
    public void getMinimumPriceByRarity_should_return_for_legendary() {
        Integer minPrice = 10;
        when(ubiServiceConfiguration.getMinLegendaryPrice()).thenReturn(minPrice);

        assertEquals(minPrice, commonValuesService.getMinimumPriceByRarity(ItemRarity.LEGENDARY));
    }

    @Test
    public void getMinimumPriceByRarity_should_return_default() {
        Integer minPrice = 10;
        when(ubiServiceConfiguration.getMinLegendaryPrice()).thenReturn(minPrice);

        assertEquals(minPrice, commonValuesService.getMinimumPriceByRarity(ItemRarity.UNKNOWN));
    }

    @Test
    public void getMaximumPriceByRarity_should_for_uncommon() {
        Integer maxPrice = 10;
        when(ubiServiceConfiguration.getMaxUncommonPrice()).thenReturn(maxPrice);

        assertEquals(maxPrice, commonValuesService.getMaximumPriceByRarity(ItemRarity.UNCOMMON));
    }

    @Test
    public void getMaximumPriceByRarity_should_for_rare() {
        Integer maxPrice = 10;
        when(ubiServiceConfiguration.getMaxRarePrice()).thenReturn(maxPrice);

        assertEquals(maxPrice, commonValuesService.getMaximumPriceByRarity(ItemRarity.RARE));
    }

    @Test
    public void getMaximumPriceByRarity_should_for_epic() {
        Integer maxPrice = 10;
        when(ubiServiceConfiguration.getMaxEpicPrice()).thenReturn(maxPrice);

        assertEquals(maxPrice, commonValuesService.getMaximumPriceByRarity(ItemRarity.EPIC));
    }

    @Test
    public void getMaximumPriceByRarity_should_for_legendary() {
        Integer maxPrice = 10;
        when(ubiServiceConfiguration.getMaxLegendaryPrice()).thenReturn(maxPrice);

        assertEquals(maxPrice, commonValuesService.getMaximumPriceByRarity(ItemRarity.LEGENDARY));
    }

    @Test
    public void getMaximumPriceByRarity_should_return_default() {
        Integer maxPrice = 10;
        when(ubiServiceConfiguration.getMaxUncommonPrice()).thenReturn(maxPrice);

        assertEquals(maxPrice, commonValuesService.getMaximumPriceByRarity(ItemRarity.UNCOMMON));
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