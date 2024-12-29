package github.ricemonger.ubi_users_stats_fetcher.postgres.entities.ubi_account_stats;

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
public class UbiTradeEntity {
    @Id
    private String tradeId;

    private TradeState state;
    private TradeCategory category;
    private LocalDateTime expiresAt;
    private LocalDateTime lastModifiedAt;

    @ManyToOne
    @JoinColumn(name = "itemId", referencedColumnName = "itemId")
    private ItemIdEntity item;

    private Integer successPaymentPrice;
    private Integer successPaymentFee;

    private Integer proposedPaymentPrice;
    private Integer proposedPaymentFee;

    public boolean isEqual(UbiTradeEntity ubiTradeEntity) {
        if (this == ubiTradeEntity) return true;
        if (ubiTradeEntity == null) return false;
        return Objects.equals(tradeId, ubiTradeEntity.getTradeId());
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
