package github.ricemonger.item_trade_stats_calculator.postgres.repositories;

import github.ricemonger.item_trade_stats_calculator.postgres.dto_projections.ItemHistoryFieldsProjection;
import github.ricemonger.item_trade_stats_calculator.postgres.dto_projections.ItemRecalculationRequiredFieldsProjection;
import github.ricemonger.utilspostgresschema.full_entities.item.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CustomItemPostgresRepository extends JpaRepository<ItemEntity, String> {
    @Transactional
    default void updateAllItemsHistoryFields(List<ItemHistoryFieldsProjection> projections) {
        for (ItemHistoryFieldsProjection projection : projections) {
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
                   "i.priceToBuyIn1Hour = :#{#projection.priceToBuyIn1Hour}, " +
                   "i.priceToBuyIn6Hours = :#{#projection.priceToBuyIn6Hours}, " +
                   "i.priceToBuyIn24Hours = :#{#projection.priceToBuyIn24Hours}, " +
                   "i.priceToBuyIn168Hours = :#{#projection.priceToBuyIn168Hours}, " +
                   "i.priceToBuyIn720Hours = :#{#projection.priceToBuyIn720Hours} " +
                   "WHERE i.itemId = :#{#projection.itemId}")
    void updateHistoryFields(ItemHistoryFieldsProjection projection);

    @Transactional(readOnly = true)
    @Query("SELECT new github.ricemonger.item_trade_stats_calculator.postgres.dto_projections.ItemRecalculationRequiredFieldsProjection(" +
           "i.itemId, i.rarity, i.maxBuyPrice, i.buyOrdersCount, i.minSellPrice, i.sellOrdersCount) " +
           "FROM ItemEntity i")
    List<ItemRecalculationRequiredFieldsProjection> findAllItemsRecalculationRequiredFields();
}
