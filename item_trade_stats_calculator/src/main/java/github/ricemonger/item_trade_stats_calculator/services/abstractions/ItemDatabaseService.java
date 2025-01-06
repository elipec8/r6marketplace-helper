package github.ricemonger.item_trade_stats_calculator.services.abstractions;

import github.ricemonger.item_trade_stats_calculator.services.DTOs.ItemRecalculationRequiredFields;
import github.ricemonger.utils.DTOs.common.ItemHistoryFieldsI;

import java.util.Collection;
import java.util.List;

public interface ItemDatabaseService {
    void saveAllHistoryFields(Collection<? extends ItemHistoryFieldsI> items);

    List<ItemRecalculationRequiredFields> findAllRecalculationRequiredFields();
}
