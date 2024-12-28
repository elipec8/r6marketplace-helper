package github.ricemonger.ubi_users_stats_fetcher.services;


import github.ricemonger.ubi_users_stats_fetcher.services.configurations.UbiServiceConfiguration;
import github.ricemonger.utils.abstractions.CommonValuesDatabaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommonValuesService {

    private final CommonValuesDatabaseService commonValuesDatabaseService;

    private final UbiServiceConfiguration ubiServiceConfiguration;

    public LocalDateTime getLastUbiUsersStatsFetchTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ubiServiceConfiguration.getDateFormat());
        try {
            return LocalDateTime.parse(commonValuesDatabaseService.getLastUbiUsersStatsFetchTime(), formatter);
        } catch (DateTimeParseException e) {
            return LocalDateTime.now().minusDays(1).withNano(0);
        }
    }

    public void setLastUbiUsersStatsFetchTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ubiServiceConfiguration.getDateFormat());
        commonValuesDatabaseService.setLastUbiUsersStatsFetchTime(formatter.format(localDateTime));
    }
}
