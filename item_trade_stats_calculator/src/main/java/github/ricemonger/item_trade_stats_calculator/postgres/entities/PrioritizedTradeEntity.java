package github.ricemonger.item_trade_stats_calculator.postgres.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Table(name = "trade")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrioritizedTradeEntity {
    @Id
    @Column(name = "trade_id")
    private String tradeId;

    @Column(name = "minutes_to_trade")
    private Integer minutesToTrade;
    @Column(name = "trade_priority")
    private Long tradePriority;

    @Override
    public int hashCode() {
        return Objects.hash(tradeId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof PrioritizedTradeEntity tradeEntity) {
            return Objects.equals(tradeId, tradeEntity.tradeId);
        }
        return false;
    }
}
