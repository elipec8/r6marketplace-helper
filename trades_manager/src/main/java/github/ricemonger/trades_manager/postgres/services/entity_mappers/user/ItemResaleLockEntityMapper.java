package github.ricemonger.trades_manager.postgres.services.entity_mappers.user;

import github.ricemonger.utils.DTOs.personal.ItemResaleLock;
import github.ricemonger.utilspostgresschema.full_entities.user.ItemResaleLockEntity;
import org.springframework.stereotype.Service;

@Service
public class ItemResaleLockEntityMapper {

    public ItemResaleLock createDTO(ItemResaleLockEntity entity) {
        return new ItemResaleLock(entity.getItemId_(), entity.getExpiresAt());
    }
}
