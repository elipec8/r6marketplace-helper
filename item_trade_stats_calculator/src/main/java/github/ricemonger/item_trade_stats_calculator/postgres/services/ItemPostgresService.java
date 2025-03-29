package github.ricemonger.item_trade_stats_calculator.postgres.services;

import github.ricemonger.item_trade_stats_calculator.postgres.repositories.CustomItemPostgresRepository;
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

    private final CustomItemPostgresRepository itemRepository;

    private final ItemEntitiesMapper itemEntitiesMapper;

    @Override
    @Transactional
    public void updateAllItemsHistoryFields(Collection<? extends ItemHistoryFieldsI> items) {
        itemRepository.updateAllItemsHistoryFields(items.stream().map(itemEntitiesMapper::createHistoryFieldsDtoProjection).toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemRecalculationRequiredFields> findAllItemsRecalculationRequiredFields() {
        return itemRepository.findAllItemsRecalculationRequiredFields().stream().map(itemEntitiesMapper::createRecalculationRequiredFieldsDTO).toList();
    }
}
