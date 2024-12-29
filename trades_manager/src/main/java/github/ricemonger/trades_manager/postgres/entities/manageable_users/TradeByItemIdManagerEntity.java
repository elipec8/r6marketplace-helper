package github.ricemonger.trades_manager.postgres.entities.manageable_users;

import github.ricemonger.trades_manager.postgres.entities.items.ItemIdEntity;
import github.ricemonger.utils.enums.TradeOperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Table(name = "trade_manager_by_item_id")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(TradeByItemIdManagerEntityId.class)
public class TradeByItemIdManagerEntity {
    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private ManageableUserEntity user;

    @Id
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id", referencedColumnName = "item_id")
    private ItemIdEntity item;

    @Column(name = "enabled")
    private Boolean enabled;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "trade_operation_type")
    private TradeOperationType tradeOperationType;

    @Column(name = "sell_boundary_price")
    private Integer sellBoundaryPrice;
    @Column(name = "buy_boundary_price")
    private Integer buyBoundaryPrice;

    @Column(name = "priority_multiplier")
    private Integer priorityMultiplier;

    public Long getUserId_() {
        return user.getId();
    }

    public String getItemId_() {
        return item.getItemId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TradeByItemIdManagerEntity tradeByItemIdManagerEntity)) {
            return false;
        }
        return Objects.equals(user, tradeByItemIdManagerEntity.user) &&
               Objects.equals(item, tradeByItemIdManagerEntity.item);
    }

    public boolean isFullyEqual(Object o) {
        if (this == o) return true;
        if (o instanceof TradeByItemIdManagerEntity entity) {
            return equals(entity) &&
                   enabled == entity.enabled &&
                   tradeOperationType == entity.tradeOperationType &&
                   Objects.equals(sellBoundaryPrice, entity.sellBoundaryPrice) &&
                   Objects.equals(buyBoundaryPrice, entity.buyBoundaryPrice) &&
                   Objects.equals(priorityMultiplier, entity.priorityMultiplier);
        }
        return false;
    }

    @Override
    public String toString() {
        return "TradeByItemIdManagerEntity(userId=" + getUserId_() + ", itemId=" + getItemId_() + ", enabled=" + enabled + ", tradeOperationType=" + tradeOperationType +
               ", sellBoundaryPrice=" + sellBoundaryPrice + ", buyBoundaryPrice=" + buyBoundaryPrice + ", priorityMultiplier=" + priorityMultiplier + ")";
    }
}
