package github.ricemonger.trades_manager.postgres.services.entity_mappers.user;


import github.ricemonger.trades_manager.services.DTOs.UbiAccountStats;
import github.ricemonger.utilspostgresschema.full_entities.item.ItemEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.UbiAccountStatsEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UbiAccountStatsEntityMapper {

    private final ItemResaleLockEntityMapper itemResaleLockEntityMapper;

    private final TradeEntityMapper tradeEntityMapper;

    public UbiAccountStats createDTO(UbiAccountStatsEntity entity) {
        return new UbiAccountStats(
                entity.getUbiProfileId(),
                entity.getCreditAmount(),
                entity.getSoldIn24h(),
                entity.getBoughtIn24h(),
                entity.getOwnedItems().stream().map(ItemEntity::getItemId).toList(),
                entity.getResaleLocks().stream().map(itemResaleLockEntityMapper::createDTO).toList(),
                entity.getCurrentSellTrades().stream().map(tradeEntityMapper::createDTO).toList(),
                entity.getCurrentBuyTrades().stream().map(tradeEntityMapper::createDTO).toList()
        );
    }
}
