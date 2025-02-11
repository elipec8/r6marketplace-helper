package github.ricemonger.utils.DTOs.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class PrioritizedPotentialTradeStats extends PotentialTradeStats {

    private Long tradePriority;

    public PrioritizedPotentialTradeStats(Integer price, Integer time, Long tradePriority) {
        super(price, time);
        this.tradePriority = tradePriority;
    }

    public PrioritizedPotentialTradeStats(PotentialTradeStats potentialTradeStats, Long tradePriority) {
        super(potentialTradeStats.getPrice(), potentialTradeStats.getTime());
        this.tradePriority = tradePriority;
    }

    @Override
    public boolean isValid() {
        return super.isValid() && tradePriority != null;
    }
}
