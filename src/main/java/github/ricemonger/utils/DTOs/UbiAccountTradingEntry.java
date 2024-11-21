package github.ricemonger.utils.DTOs;

import github.ricemonger.utils.DTOs.items.ItemResaleLockWithUbiAccount;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class UbiAccountTradingEntry {
    private UbiAccountStats ubiAccountStats;

    private UbiAccountAuthorizationEntry ubiAccountAuthorizationEntry;

    public String getUbiProfileId() {
        return ubiAccountStats.getUbiProfileId();
    }

    public void setUbiProfileId(String ubiProfileId) {
        ubiAccountStats.setUbiProfileId(ubiProfileId);
    }

    public Integer getSoldIn24h() {
        return ubiAccountStats.getSoldIn24h();
    }

    public void setSoldIn24h(Integer soldIn24h) {
        ubiAccountStats.setSoldIn24h(soldIn24h);
    }

    public Integer getBoughtIn24h() {
        return ubiAccountStats.getBoughtIn24h();
    }

    public void setBoughtIn24h(Integer boughtIn24h) {
        ubiAccountStats.setBoughtIn24h(boughtIn24h);
    }

    public List<String> getOwnedItemsIds() {
        return ubiAccountStats.getOwnedItemsIds();
    }

    public void setOwnedItemsIds(List<String> ownedItemsIds) {
        ubiAccountStats.setOwnedItemsIds(ownedItemsIds);
    }

    public List<ItemResaleLockWithUbiAccount> getResaleLocks() {
        return ubiAccountStats.getResaleLocks();
    }

    public void setResaleLocks(List<ItemResaleLockWithUbiAccount> resaleLocks) {
        ubiAccountStats.setResaleLocks(resaleLocks);
    }

    public List<UbiTrade> getCurrentBuyTrades() {
        return ubiAccountStats.getCurrentBuyTrades();
    }

    public void setCurrentBuyTrades(List<UbiTrade> currentBuyTrades) {
        ubiAccountStats.setCurrentBuyTrades(currentBuyTrades);
    }

    public List<UbiTrade> getCurrentSellTrades() {
        return ubiAccountStats.getCurrentSellTrades();
    }

    public void setCurrentSellTrades(List<UbiTrade> currentSellTrades) {
        ubiAccountStats.setCurrentSellTrades(currentSellTrades);
    }

    public List<UbiTrade> getFinishedTrades() {
        return ubiAccountStats.getFinishedTrades();
    }

    public void setFinishedTrades(List<UbiTrade> finishedTrades) {
        ubiAccountStats.setFinishedTrades(finishedTrades);
    }

    public String getEmail() {
        return ubiAccountAuthorizationEntry.getEmail();
    }

    public void setEmail(String email) {
        ubiAccountAuthorizationEntry.setEmail(email);
    }

    public String getEncodedPassword() {
        return ubiAccountAuthorizationEntry.getEncodedPassword();
    }

    public void setEncodedPassword(String encodedPassword) {
        ubiAccountAuthorizationEntry.setEncodedPassword(encodedPassword);
    }

    public String getUbiSessionId() {
        return ubiAccountAuthorizationEntry.getUbiSessionId();
    }

    public void setUbiSessionId(String ubiSessionId) {
        ubiAccountAuthorizationEntry.setUbiSessionId(ubiSessionId);
    }

    public String getUbiSpaceId() {
        return ubiAccountAuthorizationEntry.getUbiSpaceId();
    }

    public void setUbiSpaceId(String ubiSpaceId) {
        ubiAccountAuthorizationEntry.setUbiSpaceId(ubiSpaceId);
    }

    public String getUbiAuthTicket() {
        return ubiAccountAuthorizationEntry.getUbiAuthTicket();
    }

    public void setUbiAuthTicket(String ubiAuthTicket) {
        ubiAccountAuthorizationEntry.setUbiAuthTicket(ubiAuthTicket);
    }

    public String getUbiTwoFactorAuthTicket() {
        return ubiAccountAuthorizationEntry.getUbiTwoFactorAuthTicket();
    }

    public void setUbiTwoFactorAuthTicket(String ubiTwoFactorAuthTicket) {
        ubiAccountAuthorizationEntry.setUbiTwoFactorAuthTicket(ubiTwoFactorAuthTicket);
    }

    public String getUbiRememberDeviceTicket() {
        return ubiAccountAuthorizationEntry.getUbiRememberDeviceTicket();
    }

    public void setUbiRememberDeviceTicket(String ubiRememberDeviceTicket) {
        ubiAccountAuthorizationEntry.setUbiRememberDeviceTicket(ubiRememberDeviceTicket);
    }

    public String getUbiRememberMeTicket() {
        return ubiAccountAuthorizationEntry.getUbiRememberMeTicket();
    }

    public void setUbiRememberMeTicket(String ubiRememberMeTicket) {
        ubiAccountAuthorizationEntry.setUbiRememberMeTicket(ubiRememberMeTicket);
    }

    public int hashCode() {
        return Objects.hash(ubiAccountStats, ubiAccountAuthorizationEntry);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof UbiAccountTradingEntry ubiAccountTradingEntry)) {
            return false;
        }
        return Objects.equals(ubiAccountStats, ubiAccountTradingEntry.ubiAccountStats) && Objects.equals(ubiAccountAuthorizationEntry, ubiAccountTradingEntry.ubiAccountAuthorizationEntry);
    }
}
