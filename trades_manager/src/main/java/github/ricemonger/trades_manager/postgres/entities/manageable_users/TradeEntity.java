package github.ricemonger.trades_manager.postgres.entities.manageable_users;

import github.ricemonger.trades_manager.postgres.entities.items.ItemEntity;
import github.ricemonger.utils.enums.TradeCategory;
import github.ricemonger.utils.enums.TradeState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Entity
@Table(name = "ubi_trade")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TradeEntity {
    @Id
    private String tradeId;

    private TradeState state;
    private TradeCategory category;
    private LocalDateTime expiresAt;
    private LocalDateTime lastModifiedAt;

    @ManyToOne
    @JoinColumn(name = "itemId", referencedColumnName = "itemId")
    private ItemEntity item;

    private Integer successPaymentPrice;
    private Integer successPaymentFee;

    private Integer proposedPaymentPrice;
    private Integer proposedPaymentFee;

    private Integer minutesToTrade;
    private Long tradePriority;

    public boolean isEqual(TradeEntity tradeEntity) {
        return Objects.equals(tradeId, tradeEntity.getTradeId());
    }
}
