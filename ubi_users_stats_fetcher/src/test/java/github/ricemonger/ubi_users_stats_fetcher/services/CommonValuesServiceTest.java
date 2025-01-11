package github.ricemonger.ubi_users_stats_fetcher.services;

import github.ricemonger.ubi_users_stats_fetcher.services.configurations.UbiServiceConfiguration;
import github.ricemonger.utils.abstract_services.CommonValuesDatabaseService;
import github.ricemonger.utils.enums.ItemRarity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private UbiServiceConfiguration ubiServiceConfiguration;

    @Test
    public void getLastUbiUsersStatsFetchTime_should_return_last_fetch_time() {
        String lastFetchTime = "2021-08-01T12:00:00";
        when(commonValuesDatabaseService.getLastUbiUsersStatsFetchTime()).thenReturn(lastFetchTime);
        when(ubiServiceConfiguration.getDateFormat()).thenReturn("yyyy-MM-dd'T'HH:mm:ss");

        LocalDateTime result = commonValuesService.getLastUbiUsersStatsFetchTime();

        assertEquals(LocalDateTime.parse(lastFetchTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")), result);
    }

    @Test
    public void getLastUbiUsersStatsFetchTime_should_return_yesterday_time_if_null() {
        when(commonValuesDatabaseService.getLastUbiUsersStatsFetchTime()).thenReturn(null);
        when(ubiServiceConfiguration.getDateFormat()).thenReturn("yyyy-MM-dd'T'HH:mm:ss");

        LocalDateTime result = commonValuesService.getLastUbiUsersStatsFetchTime();

        assertEquals(1, Duration.between(result, LocalDateTime.now()).toDays());
    }

    @Test
    public void getLastUbiUsersStatsFetchTime_should_return_yesterday_time_if_invalid_input() {
        when(commonValuesDatabaseService.getLastUbiUsersStatsFetchTime()).thenReturn("text");
        when(ubiServiceConfiguration.getDateFormat()).thenReturn("yyyy-MM-dd'T'HH:mm:ss");

        LocalDateTime result = commonValuesService.getLastUbiUsersStatsFetchTime();

        assertEquals(1, Duration.between(result, LocalDateTime.now()).toDays());
    }

    @Test
    public void setLastUbiUsersStatsFetchTime_should_set_last_fetch_time() {
        LocalDateTime lastFetchTime = LocalDateTime.of(2021, 8, 1, 12, 0, 0);
        when(ubiServiceConfiguration.getDateFormat()).thenReturn("yyyy-MM-dd'T'HH:mm:ss");

        commonValuesService.setLastUbiUsersStatsFetchTime(lastFetchTime);

        verify(commonValuesDatabaseService).setLastUbiUsersStatsFetchTime("2021-08-01T12:00:00");
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
}