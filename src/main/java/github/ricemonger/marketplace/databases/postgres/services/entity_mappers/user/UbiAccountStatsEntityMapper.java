package github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.UbiAccountStatsEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.utils.DTOs.UbiAccountStatsEntityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UbiAccountStatsEntityMapper {

    private final ItemPostgresRepository itemPostgresRepository;

    public List<UbiAccountStatsEntity> createEntities(List<UbiAccountStatsEntityDTO> ubiAccounts) {
        List<ItemEntity> existingItems = itemPostgresRepository.findAll();

        return ubiAccounts.stream().map(ubiAccount -> new UbiAccountStatsEntity(
                ubiAccount.getUbiProfileId(),
                ubiAccount.getCreditAmount(),
                existingItems.stream().filter(item -> ubiAccount.getOwnedItemsIds().contains(item.getItemId())).toList())).toList();
    }

    public UbiAccountStatsEntityDTO createDTO(UbiAccountStatsEntity entity) {
        return new UbiAccountStatsEntityDTO(
                entity.getUbiProfileId(),
                entity.getCreditAmount(),
                entity.getOwnedItems().stream().map(ItemEntity::getItemId).toList());
    }
}
