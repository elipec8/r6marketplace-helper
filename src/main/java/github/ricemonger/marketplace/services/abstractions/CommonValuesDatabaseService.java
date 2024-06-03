package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.dtos.AuthorizationDTO;
import github.ricemonger.utils.dtos.ConfigResolvedTransactionPeriod;
import github.ricemonger.utils.dtos.ConfigTrades;

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
