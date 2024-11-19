package github.ricemonger.utils.DTOs;

import github.ricemonger.utils.DTOs.items.ItemResaleLock;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class UbiAccountTradingEntry {
    private UbiAccountStatsDTO ubiAccountStatsDTO;

    private UbiAccountAuthorizationDTO ubiAccountAuthorizationDTO;

    public String getUbiProfileId() {
        return ubiAccountStatsDTO.getUbiProfileId();
    }

    public void setUbiProfileId(String ubiProfileId) {
        ubiAccountStatsDTO.setUbiProfileId(ubiProfileId);
    }

    public Integer getSoldIn24h() {
        return ubiAccountStatsDTO.getSoldIn24h();
    }

    public void setSoldIn24h(Integer soldIn24h) {
        ubiAccountStatsDTO.setSoldIn24h(soldIn24h);
    }

    public Integer getBoughtIn24h() {
        return ubiAccountStatsDTO.getBoughtIn24h();
    }

    public void setBoughtIn24h(Integer boughtIn24h) {
        ubiAccountStatsDTO.setBoughtIn24h(boughtIn24h);
    }

    public List<String> getOwnedItemsIds() {
        return ubiAccountStatsDTO.getOwnedItemsIds();
    }

    public void setOwnedItemsIds(List<String> ownedItemsIds) {
        ubiAccountStatsDTO.setOwnedItemsIds(ownedItemsIds);
    }

    public List<ItemResaleLock> getResaleLocks() {
        return ubiAccountStatsDTO.getResaleLocks();
    }

    public void setResaleLocks(List<ItemResaleLock> resaleLocks) {
        ubiAccountStatsDTO.setResaleLocks(resaleLocks);
    }

    public List<UbiTrade> getCurrentBuyTrades() {
        return ubiAccountStatsDTO.getCurrentBuyTrades();
    }

    public void setCurrentBuyTrades(List<UbiTrade> currentBuyTrades) {
        ubiAccountStatsDTO.setCurrentBuyTrades(currentBuyTrades);
    }

    public List<UbiTrade> getCurrentSellTrades() {
        return ubiAccountStatsDTO.getCurrentSellTrades();
    }

    public void setCurrentSellTrades(List<UbiTrade> currentSellTrades) {
        ubiAccountStatsDTO.setCurrentSellTrades(currentSellTrades);
    }

    public List<UbiTrade> getFinishedTrades() {
        return ubiAccountStatsDTO.getFinishedTrades();
    }

    public void setFinishedTrades(List<UbiTrade> finishedTrades) {
        ubiAccountStatsDTO.setFinishedTrades(finishedTrades);
    }

    public String getEmail() {
        return ubiAccountAuthorizationDTO.getEmail();
    }

    public void setEmail(String email) {
        ubiAccountAuthorizationDTO.setEmail(email);
    }

    public String getEncodedPassword() {
        return ubiAccountAuthorizationDTO.getEncodedPassword();
    }

    public void setEncodedPassword(String encodedPassword) {
        ubiAccountAuthorizationDTO.setEncodedPassword(encodedPassword);
    }

    public String getUbiSessionId() {
        return ubiAccountAuthorizationDTO.getUbiSessionId();
    }

    public void setUbiSessionId(String ubiSessionId) {
        ubiAccountAuthorizationDTO.setUbiSessionId(ubiSessionId);
    }

    public String getUbiSpaceId() {
        return ubiAccountAuthorizationDTO.getUbiSpaceId();
    }

    public void setUbiSpaceId(String ubiSpaceId) {
        ubiAccountAuthorizationDTO.setUbiSpaceId(ubiSpaceId);
    }

    public String getUbiAuthTicket() {
        return ubiAccountAuthorizationDTO.getUbiAuthTicket();
    }

    public void setUbiAuthTicket(String ubiAuthTicket) {
        ubiAccountAuthorizationDTO.setUbiAuthTicket(ubiAuthTicket);
    }

    public String getUbiTwoFactorAuthTicket() {
        return ubiAccountAuthorizationDTO.getUbiTwoFactorAuthTicket();
    }

    public void setUbiTwoFactorAuthTicket(String ubiTwoFactorAuthTicket) {
        ubiAccountAuthorizationDTO.setUbiTwoFactorAuthTicket(ubiTwoFactorAuthTicket);
    }

    public String getUbiRememberDeviceTicket() {
        return ubiAccountAuthorizationDTO.getUbiRememberDeviceTicket();
    }

    public void setUbiRememberDeviceTicket(String ubiRememberDeviceTicket) {
        ubiAccountAuthorizationDTO.setUbiRememberDeviceTicket(ubiRememberDeviceTicket);
    }

    public String getUbiRememberMeTicket() {
        return ubiAccountAuthorizationDTO.getUbiRememberMeTicket();
    }

    public void setUbiRememberMeTicket(String ubiRememberMeTicket) {
        ubiAccountAuthorizationDTO.setUbiRememberMeTicket(ubiRememberMeTicket);
    }

    public int hashCode() {
        return Objects.hash(ubiAccountStatsDTO, ubiAccountAuthorizationDTO);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof UbiAccountTradingEntry ubiAccountTradingEntry)) {
            return false;
        }
        return Objects.equals(ubiAccountStatsDTO, ubiAccountTradingEntry.ubiAccountStatsDTO) && Objects.equals(ubiAccountAuthorizationDTO, ubiAccountTradingEntry.ubiAccountAuthorizationDTO);
    }
}
