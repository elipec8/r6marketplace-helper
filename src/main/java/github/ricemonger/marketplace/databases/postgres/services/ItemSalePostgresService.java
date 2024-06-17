package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.ItemSaleEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemSalePostgresRepository;
import github.ricemonger.marketplace.services.abstractions.ItemSaleDatabaseService;
import github.ricemonger.utils.dtos.Item;
import github.ricemonger.utils.dtos.ItemSale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ItemSalePostgresService implements ItemSaleDatabaseService {

    private final ItemSalePostgresRepository repository;

    public void saveAll(Collection<Item> items) {
        if (items != null) {
            repository.saveAll(items.stream().map(ItemSaleEntity::new).toList());
        }
    }

    public Collection<ItemSale> findAll() {
        return repository.findAll().stream().map(ItemSaleEntity::toItemSale).toList();
    }
}
