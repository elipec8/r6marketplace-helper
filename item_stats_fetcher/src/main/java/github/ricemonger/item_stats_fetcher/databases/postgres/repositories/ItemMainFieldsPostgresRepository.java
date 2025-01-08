package github.ricemonger.item_stats_fetcher.databases.postgres.repositories;

import github.ricemonger.item_stats_fetcher.databases.postgres.entities.ItemMainFieldsEntity;
import github.ricemonger.utils.DTOs.common.ItemMinSellPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;

public interface ItemMainFieldsPostgresRepository extends JpaRepository<ItemMainFieldsEntity, String> {
    @Transactional(readOnly = true)
    @Query("SELECT i.itemId FROM item_main_fields i")
    HashSet<String> findAllItemIds();

    @Transactional
    default void updateAllItemsMinSellPrice(Collection<? extends ItemMinSellPrice> itemMinSellPrices) {
        for (ItemMinSellPrice itemMinSellPrice : itemMinSellPrices) {
            updateItemMinSellPrice(itemMinSellPrice);
        }
    }

    @Modifying
    @Query("UPDATE item_main_fields i SET i.minSellPrice = :#{#itemMinSellPrice.minSellPrice} WHERE i.itemId = :#{#itemMinSellPrice.itemId}")
    void updateItemMinSellPrice(ItemMinSellPrice itemMinSellPrice);
}
