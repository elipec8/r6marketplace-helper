package github.ricemonger.trades_manager.postgres.services.entity_mappers.user;

import github.ricemonger.trades_manager.postgres.services.entity_mappers.item.ItemEntityMapper;
import github.ricemonger.utils.DTOs.personal.UbiTrade;
import github.ricemonger.utilspostgresschema.full_entities.user.TradeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TradeEntityMapper {

    private final ItemEntityMapper itemEntityMapper;

    public UbiTrade createDTO(TradeEntity entity) {
        return new UbiTrade(
                entity.getTradeId(),
                entity.getState(),
                entity.getCategory(),
                entity.getExpiresAt(),
                entity.getLastModifiedAt(),
                itemEntityMapper.createDTO(entity.getItem()),
                entity.getSuccessPaymentPrice(),
                entity.getSuccessPaymentFee(),
                entity.getProposedPaymentPrice(),
                entity.getProposedPaymentFee()
        );
    }
}
