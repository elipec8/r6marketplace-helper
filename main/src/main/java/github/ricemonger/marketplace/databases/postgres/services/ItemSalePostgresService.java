package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.repositories.ItemSalePostgresRepository;
import github.ricemonger.marketplace.databases.postgres.services.entity_mappers.item.ItemSaleEntityMapper;
import github.ricemonger.marketplace.services.abstractions.ItemSaleDatabaseService;
import github.ricemonger.utils.DTOs.common.ItemSale;
import github.ricemonger.utils.DTOs.common.SoldItemDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemSalePostgresService implements ItemSaleDatabaseService {

    private final ItemSalePostgresRepository itemSaleRepository;

    private final ItemSaleEntityMapper itemSaleEntityMapper;

    @Override
    @Transactional
    public void saveAll(Collection<? extends SoldItemDetails> soldItems) {
        itemSaleRepository.saveAll(itemSaleEntityMapper.createEntities(soldItems));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemSale> findAllForLastMonth() {
        return itemSaleRepository.findAllForLastMonth().stream().map(itemSaleEntityMapper::createDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemSale> findAll() {
        return itemSaleRepository.findAll().stream().map(itemSaleEntityMapper::createDTO).toList();
    }
}

