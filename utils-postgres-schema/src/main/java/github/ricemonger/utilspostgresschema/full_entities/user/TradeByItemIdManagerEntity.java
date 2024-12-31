package github.ricemonger.utilspostgresschema.full_entities.user;

import github.ricemonger.utils.enums.TradeOperationType;
import github.ricemonger.utilspostgresschema.id_entities.user.IdTradeByItemIdManagerEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TradeByItemIdManagerEntity extends IdTradeByItemIdManagerEntity {
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
}
