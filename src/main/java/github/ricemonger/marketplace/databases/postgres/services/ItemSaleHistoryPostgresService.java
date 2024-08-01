package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemSaleHistoryEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemSaleHistoryPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.ItemSaleHistoryDatabaseService;
import github.ricemonger.utils.dtos.ItemSaleHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ItemSaleHistoryPostgresService implements ItemSaleHistoryDatabaseService {

    private final ItemSaleHistoryPostgresRepository itemSaleHistoryRepository;

    public void saveAll(Collection<ItemSaleHistory> histories) {
        if (histories != null && !histories.isEmpty()) {
            itemSaleHistoryRepository.saveAll(histories.stream().map(ItemSaleHistoryEntity::new).toList());
        }
    }
}
