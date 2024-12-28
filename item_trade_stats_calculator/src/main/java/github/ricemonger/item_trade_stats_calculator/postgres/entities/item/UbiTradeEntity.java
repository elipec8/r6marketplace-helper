package github.ricemonger.item_trade_stats_calculator.postgres.entities.item;

import github.ricemonger.utils.enums.TradeCategory;
import github.ricemonger.utils.enums.TradeState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Entity
@Table(name = "ubi_trade")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UbiTradeEntity {
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
}
