package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.utils.enums.TradeOperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@Entity(name = "trade_manager_by_item_id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(TradeByItemIdManagerEntityId.class)
public class TradeByItemIdManagerEntity {
    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private UserEntity user;

    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId", referencedColumnName = "itemId")
    private ItemEntity item;

    private Boolean enabled;

    @Enumerated(EnumType.ORDINAL)
    private TradeOperationType tradeOperationType;

    private Integer sellBoundaryPrice;
    private Integer buyBoundaryPrice;

    private Integer priorityMultiplier;

    public Long getUserId() {
        return user.getId();
    }

    public String getItemId() {
        return item.getItemId();
    }

    public boolean isFullyEqualExceptUser(Object o) {
        if (this == o) return true;
        if (o instanceof TradeByItemIdManagerEntity entity) {
            return Objects.equals(getUserId(), entity.getUserId()) &&
                   Objects.equals(item.getItemId(), entity.item.getItemId()) &&
                   enabled == entity.enabled &&
                   tradeOperationType == entity.tradeOperationType &&
                   Objects.equals(sellBoundaryPrice, entity.sellBoundaryPrice) &&
                   Objects.equals(buyBoundaryPrice, entity.buyBoundaryPrice) &&
                   Objects.equals(priorityMultiplier, entity.priorityMultiplier);
        }
        return false;
    }
}
