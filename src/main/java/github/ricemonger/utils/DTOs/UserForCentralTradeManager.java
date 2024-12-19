package github.ricemonger.utils.DTOs;

import github.ricemonger.utils.DTOs.items.ItemResaleLockWithUbiAccount;
import github.ricemonger.utils.DTOs.items.UbiTrade;
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

    public List<UbiTrade> getCurrentSellTrades() {
        return ubiAccountStats.getCurrentSellTrades();
    }

    public List<UbiTrade> getCurrentBuyTrades() {
        return ubiAccountStats.getCurrentBuyTrades();
    }
}
