package github.ricemonger.item_trade_stats_calculator.postgres.services;


import github.ricemonger.item_trade_stats_calculator.postgres.repositories.ItemSalePostgresRepository;
import github.ricemonger.item_trade_stats_calculator.postgres.services.entity_mappers.item.ItemSaleEntityMapper;
import github.ricemonger.item_trade_stats_calculator.services.abstractions.ItemSaleDatabaseService;
import github.ricemonger.utils.DTOs.common.ItemSale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemSalePostgresService implements ItemSaleDatabaseService {

    private final ItemSalePostgresRepository itemSaleRepository;

    private final ItemSaleEntityMapper itemSaleEntityMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ItemSale> findAllForLastMonth() {
        return itemSaleRepository.findAllForLastMonth().stream().map(itemSaleEntityMapper::createDTO).toList();
    }
}

