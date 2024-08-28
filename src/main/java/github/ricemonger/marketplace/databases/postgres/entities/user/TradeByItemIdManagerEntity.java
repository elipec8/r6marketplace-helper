package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.utils.dtos.TradeByItemIdManager;
import github.ricemonger.utils.enums.TradeManagerTradeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity(name = "trade_manager_by_item_id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(TradeByItemIdManagerEntityId.class)
public class TradeByItemIdManagerEntity {
    @MapsId
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private UserEntity user;

    @Id
    private String itemId;

    private boolean enabled;

    private TradeManagerTradeType tradeType;

    private Integer sellBoundaryPrice;
    private Integer sellStartingPrice;

    private Integer buyBoundaryPrice;
    private Integer buyStartingPrice;

    private Integer priority;

    public TradeByItemIdManagerEntity(UserEntity user, TradeByItemIdManager tradeManager) {
        this.user = user;
        this.itemId = tradeManager.getItemId();
        this.enabled = tradeManager.isEnabled();
        this.tradeType = tradeManager.getTradeType();
        this.sellStartingPrice = tradeManager.getSellStartingPrice();
        this.sellBoundaryPrice = tradeManager.getSellBoundaryPrice();
        this.buyStartingPrice = tradeManager.getBuyStartingPrice();
        this.buyBoundaryPrice = tradeManager.getBuyBoundaryPrice();
        this.priority = tradeManager.getPriority();
    }

    public TradeByItemIdManager toTradeByItemIdManager() {
        TradeByItemIdManager tradeManager = new TradeByItemIdManager();
        tradeManager.setItemId(itemId);
        tradeManager.setEnabled(enabled);
        tradeManager.setTradeType(tradeType);
        tradeManager.setSellStartingPrice(sellStartingPrice);
        tradeManager.setSellBoundaryPrice(sellBoundaryPrice);
        tradeManager.setBuyStartingPrice(buyStartingPrice);
        tradeManager.setBuyBoundaryPrice(buyBoundaryPrice);
        tradeManager.setPriority(priority);
        return tradeManager;
    }
}
