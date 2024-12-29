package github.ricemonger.configs_fetcher.services;

import github.ricemonger.utils.DTOs.common.ConfigResolvedTransactionPeriod;
import github.ricemonger.utils.DTOs.common.ConfigTrades;
import github.ricemonger.utils.abstract_services.CommonValuesDatabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonValuesService {

    private final CommonValuesDatabaseService commonValuesDatabaseService;

    public void setConfigResolvedTransactionPeriod(ConfigResolvedTransactionPeriod configResolvedTransactionPeriod) {
        commonValuesDatabaseService.setConfigResolvedTransactionPeriod(configResolvedTransactionPeriod);
    }

    public void setConfigTrades(ConfigTrades configTrades) {
        commonValuesDatabaseService.setConfigTrades(configTrades);
    }
}
