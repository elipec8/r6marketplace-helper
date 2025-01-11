package github.ricemonger.ubi_users_stats_fetcher.postgres.services.entity_mappers.user;


import github.ricemonger.ubi_users_stats_fetcher.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.ubi_users_stats_fetcher.postgres.repositories.UbiAccountStatsPostgresRepository;
import github.ricemonger.ubi_users_stats_fetcher.services.DTOs.UbiAccountStats;
import github.ricemonger.utils.DTOs.personal.ItemResaleLock;
import github.ricemonger.utilspostgresschema.full_entities.item.ItemEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.ItemResaleLockEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.TradeEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.UbiAccountStatsEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UbiAccountStatsEntityMapper {

    private final ItemPostgresRepository itemPostgresRepository;

    private final TradeEntityMapper tradeEntityMapper;

    private final UbiAccountStatsPostgresRepository ubiAccountStatsPostgresRepository;

    public List<UbiAccountStatsEntity> createEntities(List<UbiAccountStats> ubiAccounts) {
        List<String> existingItems = itemPostgresRepository.findAllItemIds();

        List<UbiAccountStatsEntity> entities = new LinkedList<>();

        for (UbiAccountStats ubiAccount : ubiAccounts) {

            UbiAccountStatsEntity entity;

            if (ubiAccountStatsPostgresRepository.existsById(ubiAccount.getUbiProfileId())) {
                entity = ubiAccountStatsPostgresRepository.findById(ubiAccount.getUbiProfileId()).get();
                entity.getCurrentBuyTrades().clear();
                entity.getCurrentSellTrades().clear();
                entity.getOwnedItems().clear();
                entity.getResaleLocks().clear();
            } else {
                entity = new UbiAccountStatsEntity(ubiAccount.getUbiProfileId());
            }

            entity.setCreditAmount(ubiAccount.getCreditAmount());
            entity.setSoldIn24h(ubiAccount.getSoldIn24h());
            entity.setBoughtIn24h(ubiAccount.getBoughtIn24h());

            List<ItemEntity> ownedItemsIds = existingItems.stream().filter(ex -> ubiAccount.getOwnedItemsIds().contains(ex)).map(itemPostgresRepository::getReferenceById).toList();
            List<TradeEntity> currentSellTrades = ubiAccount.getCurrentSellTrades().stream().map(ubiTrade -> tradeEntityMapper.createEntity(ubiTrade, existingItems)).toList();
            List<TradeEntity> currentBuyTrades = ubiAccount.getCurrentBuyTrades().stream().map(ubiTrade -> tradeEntityMapper.createEntity(ubiTrade, existingItems)).toList();
            List<ItemResaleLockEntity> resaleLocksEntities = new LinkedList<>();

            for (ItemResaleLock resaleLock : ubiAccount.getResaleLocks()) {
                ItemEntity item = existingItems.stream().filter(ex -> ex.equals(resaleLock.getItemId())).map(itemPostgresRepository::getReferenceById).findFirst().orElse(null);
                if (item != null) {
                    resaleLocksEntities.add(new ItemResaleLockEntity(entity, item, resaleLock.getExpiresAt()));
                } else {
                    log.error("Item with id {} not found, resale lock parsing for this item skipped", resaleLock.getItemId());
                    continue;
                }
            }

            entity.getOwnedItems().addAll(ownedItemsIds);
            entity.getCurrentSellTrades().addAll(currentSellTrades);
            entity.getCurrentBuyTrades().addAll(currentBuyTrades);

            entity.getResaleLocks().addAll(resaleLocksEntities);

            entities.add(entity);
        }
        return entities;
    }
}
