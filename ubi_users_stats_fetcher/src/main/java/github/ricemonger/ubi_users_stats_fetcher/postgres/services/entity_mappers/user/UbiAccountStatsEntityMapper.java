package github.ricemonger.ubi_users_stats_fetcher.postgres.services.entity_mappers.user;


import github.ricemonger.ubi_users_stats_fetcher.postgres.entities.ubi_account_stats.ItemIdEntity;
import github.ricemonger.ubi_users_stats_fetcher.postgres.entities.ubi_account_stats.ItemResaleLockEntity;
import github.ricemonger.ubi_users_stats_fetcher.postgres.entities.ubi_account_stats.UbiAccountStatsEntity;
import github.ricemonger.ubi_users_stats_fetcher.postgres.repositories.ItemIdPostgresRepository;
import github.ricemonger.ubi_users_stats_fetcher.services.DTOs.UbiAccountStats;
import github.ricemonger.utils.DTOs.personal.ItemResaleLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UbiAccountStatsEntityMapper {

    private final ItemIdPostgresRepository itemIdPostgresRepository;

    private final UbiTradeEntityMapper ubiTradeEntityMapper;

    public List<UbiAccountStatsEntity> createEntities(List<UbiAccountStats> ubiAccounts) {
        List<ItemIdEntity> existingItems = itemIdPostgresRepository.findAll();

        List<UbiAccountStatsEntity> entities = new LinkedList<>();

        for (UbiAccountStats ubiAccount : ubiAccounts) {
            UbiAccountStatsEntity entity = new UbiAccountStatsEntity();
            entity.setUbiProfileId(ubiAccount.getUbiProfileId());
            entity.setCreditAmount(ubiAccount.getCreditAmount());
            entity.setSoldIn24h(ubiAccount.getSoldIn24h());
            entity.setBoughtIn24h(ubiAccount.getBoughtIn24h());
            entity.setOwnedItems(existingItems.stream().filter(item -> ubiAccount.getOwnedItemsIds().contains(item.getItemId())).toList());
            entity.setCurrentSellTrades(ubiAccount.getCurrentSellTrades().stream().map(ubiTrade -> ubiTradeEntityMapper.createEntity(ubiTrade, existingItems)).toList());
            entity.setCurrentBuyTrades(ubiAccount.getCurrentBuyTrades().stream().map(ubiTrade -> ubiTradeEntityMapper.createEntity(ubiTrade, existingItems)).toList());

            List<ItemResaleLockEntity> resaleLocksEntities = new LinkedList<>();

            for (ItemResaleLock resaleLock : ubiAccount.getResaleLocks()) {
                ItemIdEntity item = existingItems.stream().filter(itemIdEntity -> itemIdEntity.getItemId().equals(resaleLock.getItemId())).findFirst().orElse(null);
                if (item != null) {
                    resaleLocksEntities.add(new ItemResaleLockEntity(entity, item, resaleLock.getExpiresAt()));
                } else {
                    log.error("Item with id {} not found, resale lock parsing for this item skipped", resaleLock.getItemId());
                    continue;
                }
            }

            entity.setResaleLocks(resaleLocksEntities);

            entities.add(entity);
        }
        return entities;
    }
}
