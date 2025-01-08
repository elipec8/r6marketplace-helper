package github.ricemonger.item_trade_stats_calculator.postgres.dto_projections;

import github.ricemonger.utils.enums.TradeCategory;
import github.ricemonger.utils.enums.TradeState;
import github.ricemonger.utilspostgresschema.full_entities.item.ItemEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UbiTradeDtoProjection {
    private String tradeId;
    private TradeState state;
    private TradeCategory category;
    private LocalDateTime expiresAt;
    private LocalDateTime lastModifiedAt;

    private ItemEntity item;

    private Integer successPaymentPrice;
    private Integer successPaymentFee;

    private Integer proposedPaymentPrice;
    private Integer proposedPaymentFee;
}
