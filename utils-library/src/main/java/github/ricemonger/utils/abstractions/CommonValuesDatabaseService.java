package github.ricemonger.utils.abstractions;

import github.ricemonger.utils.DTOs.common.ConfigResolvedTransactionPeriod;
import github.ricemonger.utils.DTOs.common.ConfigTrades;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;

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

    void setLastUbiUsersStatsFetchTime(String dateTime);

    String getLastUbiUsersStatsFetchTime();
}
