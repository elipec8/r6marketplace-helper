package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.utils.DTOs.TradeByItemIdManager;
import github.ricemonger.utils.enums.TradeOperationType;
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
    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private UserEntity user;

    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId", referencedColumnName = "itemId")
    private ItemEntity item;

    private boolean enabled;

    @Enumerated(EnumType.ORDINAL)
    private TradeOperationType tradeOperationType;

    private Integer sellBoundaryPrice;
    private Integer buyBoundaryPrice;

    private Integer priorityMultiplier;

    public String getItemId() {
        return item.getItemId();
    }
}
