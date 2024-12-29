package github.ricemonger.ubi_users_stats_fetcher.services;

import github.ricemonger.ubi_users_stats_fetcher.services.configurations.UbiServiceConfiguration;
import github.ricemonger.utils.abstract_services.CommonValuesDatabaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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
    public void setLastUbiUsersStatsFetchTime_should_set_last_fetch_time() {
        LocalDateTime lastFetchTime = LocalDateTime.of(2021, 8, 1, 12, 0, 0);
        when(ubiServiceConfiguration.getDateFormat()).thenReturn("yyyy-MM-dd'T'HH:mm:ss");

        commonValuesService.setLastUbiUsersStatsFetchTime(lastFetchTime);

        verify(commonValuesDatabaseService).setLastUbiUsersStatsFetchTime("2021-08-01T12:00:00");
    }
}