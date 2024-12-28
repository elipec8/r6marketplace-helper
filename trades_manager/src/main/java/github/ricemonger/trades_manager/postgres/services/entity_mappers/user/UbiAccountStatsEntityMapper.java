package github.ricemonger.trades_manager.postgres.services.entity_mappers.user;


import github.ricemonger.trades_manager.postgres.entities.manageable_users.ItemIdEntity;
import github.ricemonger.trades_manager.postgres.entities.ubi_account_stats.UbiAccountStatsEntity;
import github.ricemonger.trades_manager.postgres.repositories.ItemIdPostgresRepository;
import github.ricemonger.utils.DTOs.personal.UbiAccountStatsEntityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UbiAccountStatsEntityMapper {

    private final ItemIdPostgresRepository itemIdPostgresRepository;

    public List<UbiAccountStatsEntity> createEntities(List<UbiAccountStatsEntityDTO> ubiAccounts) {
        List<ItemIdEntity> existingItems = itemIdPostgresRepository.findAll();

        return ubiAccounts.stream().map(ubiAccount -> new UbiAccountStatsEntity(
                ubiAccount.getUbiProfileId(),
                ubiAccount.getCreditAmount(),
                existingItems.stream().filter(item -> ubiAccount.getOwnedItemsIds().contains(item.getItemId())).toList())).toList();
    }
}
