package github.ricemonger.ubi_users_stats_fetcher.postgres.entities.ubi_account_stats;

import github.ricemonger.utils.enums.TradeCategory;
import github.ricemonger.utils.enums.TradeState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Table(name = "trade")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UbiTradeEntity {
    @Id
    @Column(name = "trade_id")
    private String tradeId;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "state")
    private TradeState state;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "category")
    private TradeCategory category;
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
    @Column(name = "last_modified_at")
    private LocalDateTime lastModifiedAt;

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "item_id")
    private ItemIdEntity item;

    @Column(name = "success_payment_price")
    private Integer successPaymentPrice;
    @Column(name = "success_payment_fee")
    private Integer successPaymentFee;

    @Column(name = "proposed_payment_price")
    private Integer proposedPaymentPrice;
    @Column(name = "proposed_payment_fee")
    private Integer proposedPaymentFee;

    @Override
    public int hashCode() {
        return Objects.hash(tradeId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof UbiTradeEntity tradeEntity) {
            return Objects.equals(tradeId, tradeEntity.tradeId);
        }
        return false;
    }

    @Override
    public String toString() {
        return "UbiTradeEntity{" +
               "tradeId='" + tradeId + '\'' +
               ", state=" + state +
               ", category=" + category +
               ", expiresAt=" + expiresAt +
               ", lastModifiedAt=" + lastModifiedAt +
               ", item=" + item +
               ", successPaymentPrice=" + successPaymentPrice +
               ", successPaymentFee=" + successPaymentFee +
               ", proposedPaymentPrice=" + proposedPaymentPrice +
               ", proposedPaymentFee=" + proposedPaymentFee +
               '}';
    }
}
