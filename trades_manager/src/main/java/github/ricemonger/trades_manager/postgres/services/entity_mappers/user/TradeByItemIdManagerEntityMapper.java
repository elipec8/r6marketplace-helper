package github.ricemonger.trades_manager.postgres.services.entity_mappers.user;

import github.ricemonger.trades_manager.postgres.entities.manageable_users.TradeByItemIdManagerEntity;
import github.ricemonger.utils.DTOs.personal.TradeByItemIdManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TradeByItemIdManagerEntityMapper {

    public TradeByItemIdManager createDTO(TradeByItemIdManagerEntity entity) {
        return new TradeByItemIdManager(
                entity.getItemId_(),
                entity.getEnabled(),
                entity.getTradeOperationType(),
                entity.getSellBoundaryPrice(),
                entity.getBuyBoundaryPrice(),
                entity.getPriorityMultiplier()
        );
    }
}
