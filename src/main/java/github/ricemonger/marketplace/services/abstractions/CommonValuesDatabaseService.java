package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.DTOs.AuthorizationDTO;
import github.ricemonger.utils.DTOs.ConfigResolvedTransactionPeriod;
import github.ricemonger.utils.DTOs.ConfigTrades;

public interface CommonValuesDatabaseService {
    int getExpectedItemCount();

    void setExpectedItemCount(int newItemsAmount);

    ConfigResolvedTransactionPeriod getConfigResolvedTransactionPeriod();

    void setConfigResolvedTransactionPeriod(ConfigResolvedTransactionPeriod configResolvedTransactionPeriod);

    ConfigTrades getConfigTrades();

    void setConfigTrades(ConfigTrades configTrades);

    String getPaymentItemId();

    String getMainUserAuthorizationToken();

    String getMainUserProfileId();

    String getMainUserSessionId();

    String getMainUserRememberMeTicket();

    void setMainUserAuthorization(AuthorizationDTO dto, int expireTimeout);
}
