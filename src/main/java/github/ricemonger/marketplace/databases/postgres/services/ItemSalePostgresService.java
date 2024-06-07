package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.mappers.ItemSalePostgresMapper;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemSalePostgresRepository;
import github.ricemonger.marketplace.services.abstractions.ItemSaleDatabaseService;
import github.ricemonger.utils.dtos.Item;
import github.ricemonger.utils.dtos.ItemSale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class ItemSalePostgresService implements ItemSaleDatabaseService {

    private final ItemSalePostgresRepository repository;

    private final ItemSalePostgresMapper mapper;

    public Collection<ItemSale> findAllItemSales() {
        return mapper.mapItemSales(repository.findAll());
    }

    public void saveAllItemSales(Collection<Item> items) {
        repository.saveAll(new HashSet<>(mapper.mapItemSaleEntities(items)));
    }
}
