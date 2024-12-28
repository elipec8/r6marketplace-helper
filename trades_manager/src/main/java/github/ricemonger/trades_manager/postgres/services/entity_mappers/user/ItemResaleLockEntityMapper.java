package github.ricemonger.trades_manager.postgres.services.entity_mappers.user;

import github.ricemonger.trades_manager.postgres.entities.manageable_users.ItemResaleLockEntity;
import github.ricemonger.utils.DTOs.personal.ItemResaleLock;
import org.springframework.stereotype.Service;

@Service
public class ItemResaleLockEntityMapper {

    public ItemResaleLock createDTO(ItemResaleLockEntity entity) {
        return new ItemResaleLock(entity.getItemId_(),entity.getExpiresAt());
    }
}
