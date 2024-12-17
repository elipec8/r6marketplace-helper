package github.ricemonger.marketplace.services.central_trade_manager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PotentialTradeStats {
    private Integer price;
    private Integer prognosedTradeSuccessMinutes;
    private Long tradePriority;
}
