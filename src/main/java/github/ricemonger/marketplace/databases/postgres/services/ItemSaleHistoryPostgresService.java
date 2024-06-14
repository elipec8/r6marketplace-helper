package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.mappers.ItemSaleHistoryPostgresMapper;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemSaleHistoryPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.ItemSaleHistoryDatabaseService;
import github.ricemonger.utils.dtos.ItemSaleHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ItemSaleHistoryPostgresService implements ItemSaleHistoryDatabaseService {

    private final ItemSaleHistoryPostgresRepository repository;

    private final ItemSaleHistoryPostgresMapper mapper;

    public void saveAllItemSaleHistoryStats(Collection<ItemSaleHistory> histories){
        if(histories.isEmpty())
            return;
        else {
            repository.saveAll(mapper.mapItemSaleHistoryEntities(histories));
        }
    }
}
