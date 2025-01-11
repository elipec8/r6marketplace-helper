package github.ricemonger.ubi_users_stats_fetcher.services;

import github.ricemonger.utils.DTOs.common.Item;

import java.util.List;

public interface ItemDatabaseService {
    List<Item> findItemsByIds(List<String> itemIds);
}
