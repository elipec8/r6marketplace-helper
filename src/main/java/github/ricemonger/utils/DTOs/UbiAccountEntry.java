package github.ricemonger.utils.DTOs;

import github.ricemonger.utils.DTOs.items.ItemResaleLockWithUbiAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UbiAccountEntry {
    private UbiAccountAuthorizationEntry ubiAccountAuthorizationEntry;
    private UbiAccountStats ubiAccountStats;

    public String getEmail() {
        return ubiAccountAuthorizationEntry.getEmail();
    }

    public String getEncodedPassword() {
        return ubiAccountAuthorizationEntry.getEncodedPassword();
    }

    public String getAuthorizationEntryProfileId() {
        return ubiAccountAuthorizationEntry.getUbiProfileId();
    }

    public String getUbiSessionId() {
        return ubiAccountAuthorizationEntry.getUbiSessionId();
    }

    public String getUbiSpaceId() {
        return ubiAccountAuthorizationEntry.getUbiSpaceId();
    }

    public String getUbiAuthTicket() {
        return ubiAccountAuthorizationEntry.getUbiAuthTicket();
    }

    public String getUbiTwoFactorAuthTicket() {
        return ubiAccountAuthorizationEntry.getUbiTwoFactorAuthTicket();
    }

    public String getUbiRememberDeviceTicket() {
        return ubiAccountAuthorizationEntry.getUbiRememberDeviceTicket();
    }

    public String getUbiRememberMeTicket() {
        return ubiAccountAuthorizationEntry.getUbiRememberMeTicket();
    }

    public String getUbiProfileId() {
        return ubiAccountStats.getUbiProfileId();
    }

    public Integer getSoldIn24h() {
        return ubiAccountStats.getSoldIn24h();
    }

    public Integer getBoughtIn24h() {
        return ubiAccountStats.getBoughtIn24h();
    }

    public Integer getCreditAmount() {
        return ubiAccountStats.getCreditAmount();
    }

    public List<String> getOwnedItemsIds() {
        return ubiAccountStats.getOwnedItemsIds();
    }

    public List<ItemResaleLockWithUbiAccount> getResaleLocks() {
        return ubiAccountStats.getResaleLocks();
    }

    public List<UbiTrade> getCurrentBuyTrades() {
        return ubiAccountStats.getCurrentBuyTrades();
    }

    public List<UbiTrade> getCurrentSellTrades() {
        return ubiAccountStats.getCurrentSellTrades();
    }
}
