package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.utils.DTOs.UbiAccountStatsDTO;
import github.ricemonger.utils.DTOs.UbiTrade;
import github.ricemonger.utils.DTOs.items.ItemResaleLock;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Entity(name = "ubi_account_stats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UbiAccountEntity {
    @Id
    private String ubiProfileId;

    private Integer soldIn24h;

    private Integer boughtIn24h;

    @ManyToMany
    @JoinTable(name = "ubi_account_current_owned_items",
            joinColumns = {@JoinColumn(name = "ubiProfileId", referencedColumnName = "ubiProfileId")},
            inverseJoinColumns = @JoinColumn(name = "itemId", referencedColumnName = "itemid"))
    private List<ItemEntity> ownedItems;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(name = "ubi_account_current_resale_locks",
            joinColumns = {@JoinColumn(name = "ubiProfileId", referencedColumnName = "ubiProfileId")},
            inverseJoinColumns = @JoinColumn(name = "itemId", referencedColumnName = "itemId"))
    private List<ItemResaleLockEntity> resaleLocks = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(name = "ubi_account_current_buy_trades",
            joinColumns = {@JoinColumn(name = "ubiProfileId", referencedColumnName = "ubiProfileId")},
            inverseJoinColumns = @JoinColumn(name = "tradeId", referencedColumnName = "tradeId"))
    private List<UbiTradeEntity> currentBuyTrades = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(name = "ubi_account_current_sell_trades",
            joinColumns = {@JoinColumn(name = "ubiProfileId", referencedColumnName = "ubiProfileId")},
            inverseJoinColumns = @JoinColumn(name = "tradeId", referencedColumnName = "tradeId"))
    private List<UbiTradeEntity> currentSellTrades = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(name = "ubi_account_finished_trades",
            joinColumns = {@JoinColumn(name = "ubiProfileId", referencedColumnName = "ubiProfileId")},
            inverseJoinColumns = @JoinColumn(name = "tradeId", referencedColumnName = "tradeId"))
    private List<UbiTradeEntity> finishedTrades = new ArrayList<>();

    public UbiAccountEntity(String ubiProfileId) {
        this.ubiProfileId = ubiProfileId;
    }

    public UbiAccountStatsDTO toUbiAccountStats() {
        UbiAccountStatsDTO ubiAccountTradingEntry = new UbiAccountStatsDTO();
        ubiAccountTradingEntry.setUbiProfileId(this.getUbiProfileId());

        List<String> ownedItemsIds = new ArrayList<>();
        for (ItemEntity item : this.ownedItems) {
            ownedItemsIds.add(item.getItemId());
        }
        ubiAccountTradingEntry.setOwnedItemsIds(ownedItemsIds);

        ubiAccountTradingEntry.setSoldIn24h(this.boughtIn24h);
        ubiAccountTradingEntry.setBoughtIn24h(this.boughtIn24h);

        List<ItemResaleLock> resaleLocks = new ArrayList<>();
        for (ItemResaleLockEntity lock : this.resaleLocks) {
            resaleLocks.add(lock.toItemResaleLock());
        }
        ubiAccountTradingEntry.setResaleLocks(resaleLocks);

        List<UbiTrade> currentBuyTrades = new ArrayList<>();
        for (UbiTradeEntity trade : this.currentBuyTrades) {
            currentBuyTrades.add(trade.toUbiTrade());
        }
        ubiAccountTradingEntry.setCurrentBuyTrades(currentBuyTrades);

        List<UbiTrade> currentSellTrades = new ArrayList<>();
        for (UbiTradeEntity trade : this.currentSellTrades) {
            currentSellTrades.add(trade.toUbiTrade());
        }
        ubiAccountTradingEntry.setCurrentSellTrades(currentSellTrades);

        List<UbiTrade> finishedTrades = new ArrayList<>();
        for (UbiTradeEntity trade : this.finishedTrades) {
            finishedTrades.add(trade.toUbiTrade());
        }
        ubiAccountTradingEntry.setFinishedTrades(finishedTrades);

        return ubiAccountTradingEntry;
    }
}
