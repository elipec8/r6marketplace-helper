package github.ricemonger.item_trade_stats_calculator.postgres.services.entity_mappers.users;

import github.ricemonger.item_trade_stats_calculator.postgres.dto_projections.PrioritizedTradeProjection;
import github.ricemonger.item_trade_stats_calculator.postgres.dto_projections.UbiTradeProjection;
import github.ricemonger.item_trade_stats_calculator.postgres.services.entity_mappers.item.ItemEntitiesMapper;
import github.ricemonger.item_trade_stats_calculator.services.DTOs.PrioritizedTrade;
import github.ricemonger.utils.DTOs.personal.UbiTrade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TradeEntityMapper {

    private final ItemEntitiesMapper itemEntitiesMapper;

    public UbiTrade createUbiTrade(UbiTradeProjection projection) {
        return new UbiTrade(
                projection.getTradeId(),
                projection.getState(),
                projection.getCategory(),
                projection.getExpiresAt(),
                projection.getLastModifiedAt(),
                itemEntitiesMapper.createItem(projection.getItem()),
                projection.getSuccessPaymentPrice(),
                projection.getSuccessPaymentFee(),
                projection.getProposedPaymentPrice(),
                projection.getProposedPaymentFee()
        );
    }

    public PrioritizedTradeProjection createPrioritizedTradeDtoProjection(PrioritizedTrade prioritizedTrade) {
        return new PrioritizedTradeProjection(
                prioritizedTrade.getTradeId(),
                prioritizedTrade.getMinutesToTrade(),
                prioritizedTrade.getTradePriority()
        );
    }
}
