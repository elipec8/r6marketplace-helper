package github.ricemonger.item_trade_stats_calculator.postgres.repositories;


import github.ricemonger.item_trade_stats_calculator.postgres.dto_projections.ItemHistoryFieldsDtoProjection;
import github.ricemonger.item_trade_stats_calculator.postgres.dto_projections.ItemRecalculationRequiredFieldsDtoProjection;
import github.ricemonger.utilspostgresschema.full_entities.item.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ItemPostgresRepository extends JpaRepository<ItemEntity, String> {
    @Transactional
    default void updateAllItemsHistoryFields(List<ItemHistoryFieldsDtoProjection> projections) {
        for (ItemHistoryFieldsDtoProjection projection : projections) {
            updateHistoryFields(projection);
        }
    }

    @Transactional
    @Modifying
    @Query(value = "UPDATE ItemEntity i SET " +
                   "i.monthAveragePrice = :#{#projection.monthAveragePrice}, " +
                   "i.monthMedianPrice = :#{#projection.monthMedianPrice}, " +
                   "i.monthMaxPrice = :#{#projection.monthMaxPrice}, " +
                   "i.monthMinPrice = :#{#projection.monthMinPrice}, " +
                   "i.monthSalesPerDay = :#{#projection.monthSalesPerDay}, " +
                   "i.monthSales = :#{#projection.monthSales}, " +
                   "i.dayAveragePrice = :#{#projection.dayAveragePrice}, " +
                   "i.dayMedianPrice = :#{#projection.dayMedianPrice}, " +
                   "i.dayMaxPrice = :#{#projection.dayMaxPrice}, " +
                   "i.dayMinPrice = :#{#projection.dayMinPrice}, " +
                   "i.daySales = :#{#projection.daySales}, " +
                   "i.priorityToSellByMaxBuyPrice = :#{#projection.priorityToSellByMaxBuyPrice}, " +
                   "i.priorityToSellByNextFancySellPrice = :#{#projection.priorityToSellByNextFancySellPrice}, " +
                   "i.priorityToBuyByMinSellPrice = :#{#projection.priorityToBuyByMinSellPrice}, " +
                   "i.priorityToBuyIn1Hour = :#{#projection.priorityToBuyIn1Hour}, " +
                   "i.priorityToBuyIn6Hours = :#{#projection.priorityToBuyIn6Hours}, " +
                   "i.priorityToBuyIn24Hours = :#{#projection.priorityToBuyIn24Hours}, " +
                   "i.priorityToBuyIn168Hours = :#{#projection.priorityToBuyIn168Hours}, " +
                   "i.priorityToBuyIn720Hours = :#{#projection.priorityToBuyIn720Hours}, " +
                   "i.priceToBuyIn1Hour = :#{#projection.priceToBuyIn1Hour}, " +
                   "i.priceToBuyIn6Hours = :#{#projection.priceToBuyIn6Hours}, " +
                   "i.priceToBuyIn24Hours = :#{#projection.priceToBuyIn24Hours}, " +
                   "i.priceToBuyIn168Hours = :#{#projection.priceToBuyIn168Hours}, " +
                   "i.priceToBuyIn720Hours = :#{#projection.priceToBuyIn720Hours} " +
                   "WHERE i.itemId = :#{#projection.itemId}")
    void updateHistoryFields(ItemHistoryFieldsDtoProjection projection);

    @Transactional(readOnly = true)
    @Query("SELECT new github.ricemonger.item_trade_stats_calculator.postgres.dto_projections.ItemRecalculationRequiredFieldsDtoProjection(" +
           "i.itemId, i.rarity, i.maxBuyPrice, i.buyOrdersCount, i.minSellPrice, i.sellOrdersCount) " +
           "FROM ItemEntity i")
    List<ItemRecalculationRequiredFieldsDtoProjection> findAllItemsRecalculationRequiredFields();
}
