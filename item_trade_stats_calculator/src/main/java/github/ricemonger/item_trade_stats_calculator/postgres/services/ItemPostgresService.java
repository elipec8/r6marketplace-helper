package github.ricemonger.item_trade_stats_calculator.postgres.services;

import github.ricemonger.item_trade_stats_calculator.postgres.repositories.ItemHistoryFieldsPostgresRepository;
import github.ricemonger.item_trade_stats_calculator.postgres.repositories.ItemRecalculationRequiredFieldsPostgresRepository;
import github.ricemonger.item_trade_stats_calculator.postgres.services.entity_mappers.item.ItemEntitiesMapper;
import github.ricemonger.item_trade_stats_calculator.services.DTOs.ItemRecalculationRequiredFields;
import github.ricemonger.item_trade_stats_calculator.services.abstractions.ItemDatabaseService;
import github.ricemonger.utils.DTOs.common.ItemHistoryFieldsI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemPostgresService implements ItemDatabaseService {

    private final ItemHistoryFieldsPostgresRepository itemHistoryFieldsRepository;

    private final ItemRecalculationRequiredFieldsPostgresRepository itemMainFieldsRepository;

    private final ItemEntitiesMapper itemEntitiesMapper;

    @Transactional
    public void saveAllHistoryFields(Collection<? extends ItemHistoryFieldsI> items) {
        itemHistoryFieldsRepository.saveAll(items.stream().map(itemEntitiesMapper::createHistoryFieldsEntity).toList());
    }

    @Transactional(readOnly = true)
    public List<ItemRecalculationRequiredFields> findAllRecalculationRequiredFields() {
        return itemMainFieldsRepository.findAll().stream().map(itemEntitiesMapper::createRecalculationRequiredFieldsDTO).toList();
    }
}
