package github.ricemonger.item_trade_stats_calculator.postgres.services.entity_mappers.users;

import github.ricemonger.item_trade_stats_calculator.postgres.entities.PrioritizedTradeEntity;
import github.ricemonger.item_trade_stats_calculator.postgres.entities.UbiTradeEntity;
import github.ricemonger.item_trade_stats_calculator.postgres.services.entity_mappers.item.ItemEntitiesMapper;
import github.ricemonger.item_trade_stats_calculator.services.DTOs.PrioritizedTrade;
import github.ricemonger.utils.DTOs.personal.UbiTrade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TradeEntityMapper {

    private final ItemEntitiesMapper itemEntitiesMapper;

    public UbiTrade createUbiTrade(UbiTradeEntity ubiTradeEntity) {
        return new UbiTrade(
                ubiTradeEntity.getTradeId(),
                ubiTradeEntity.getState(),
                ubiTradeEntity.getCategory(),
                ubiTradeEntity.getExpiresAt(),
                ubiTradeEntity.getLastModifiedAt(),
                itemEntitiesMapper.createItem(ubiTradeEntity.getItem()),
                ubiTradeEntity.getSuccessPaymentPrice(),
                ubiTradeEntity.getSuccessPaymentFee(),
                ubiTradeEntity.getProposedPaymentPrice(),
                ubiTradeEntity.getProposedPaymentFee()
        );
    }

    public PrioritizedTradeEntity createPrioritizedTradeEntity(PrioritizedTrade prioritizedTrade) {
        return new PrioritizedTradeEntity(
                prioritizedTrade.getTradeId(),
                prioritizedTrade.getMinutesToTrade(),
                prioritizedTrade.getTradePriority()
        );
    }
}
