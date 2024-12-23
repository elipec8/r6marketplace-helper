package github.ricemonger.utils.DTOs.personal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserForCentralTradeManager {
    private Long id;

    private UbiAccountStats ubiAccountStats;

    private String ubiSessionId;
    private String ubiSpaceId;
    private String ubiAuthTicket;
    private String ubiRememberDeviceTicket;
    private String ubiRememberMeTicket;

    private String chatId;
    private Boolean privateNotificationsEnabledFlag;

    private List<TradeByFiltersManager> tradeByFiltersManagers = new ArrayList<>();

    private List<TradeByItemIdManager> tradeByItemIdManagers = new ArrayList<>();

    public UserForCentralTradeManager(ManageableUser manageableUser, UbiAccountStats linkedUbiAccount) {
        this.id = manageableUser.getId();
        this.ubiAccountStats = linkedUbiAccount;
        this.ubiSessionId = manageableUser.getUbiSessionId();
        this.ubiSpaceId = manageableUser.getUbiSpaceId();
        this.ubiAuthTicket = manageableUser.getUbiAuthTicket();
        this.ubiRememberDeviceTicket = manageableUser.getUbiRememberDeviceTicket();
        this.ubiRememberMeTicket = manageableUser.getUbiRememberMeTicket();
        this.chatId = manageableUser.getChatId();
        this.privateNotificationsEnabledFlag = manageableUser.getPrivateNotificationsEnabledFlag();
        this.tradeByFiltersManagers = manageableUser.getTradeByFiltersManagers();
        this.tradeByItemIdManagers = manageableUser.getTradeByItemIdManagers();
    }

    public String getUbiProfileId() {
        return ubiAccountStats == null ? null : ubiAccountStats.getUbiProfileId();
    }

    public Integer getSoldIn24h() {
        return ubiAccountStats == null ? null : ubiAccountStats.getSoldIn24h();
    }

    public Integer getBoughtIn24h() {
        return ubiAccountStats == null ? null : ubiAccountStats.getBoughtIn24h();
    }

    public Integer getCreditAmount() {
        return ubiAccountStats == null ? null : ubiAccountStats.getCreditAmount();
    }

    public List<String> getOwnedItemsIds() {
        return ubiAccountStats == null ? null : ubiAccountStats.getOwnedItemsIds();
    }

    public List<ItemResaleLockWithUbiAccount> getResaleLocks() {
        return ubiAccountStats == null ? null : ubiAccountStats.getResaleLocks();
    }

    public List<UbiTrade> getCurrentSellTrades() {
        return ubiAccountStats == null ? null : ubiAccountStats.getCurrentSellTrades();
    }

    public List<UbiTrade> getCurrentBuyTrades() {
        return ubiAccountStats == null ? null : ubiAccountStats.getCurrentBuyTrades();
    }
}
