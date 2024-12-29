package github.ricemonger.trades_manager.services.abstractions;

import github.ricemonger.utils.DTOs.common.Item;

import java.util.List;

public interface ItemDatabaseService {
    List<Item> findAll();
}
