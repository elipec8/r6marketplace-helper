package github.ricemonger.item_trade_stats_calculator.postgres.entities.item;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Table(name = "ubi_trade")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrioritizedTradeEntity {
    @Id
    private String tradeId;

    private Integer minutesToTrade;

    private Long tradePriority;
}
