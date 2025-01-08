package github.ricemonger.item_trade_stats_calculator.postgres.repositories;

import github.ricemonger.item_trade_stats_calculator.postgres.dto_projections.PrioritizedTradeDtoProjection;
import github.ricemonger.item_trade_stats_calculator.postgres.dto_projections.UbiTradeDtoProjection;
import github.ricemonger.utilspostgresschema.full_entities.user.TradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TradePostgresRepository extends JpaRepository<TradeEntity, String> {
    @Transactional
    default void prioritizeAllTrades(List<PrioritizedTradeDtoProjection> projections) {
        for (PrioritizedTradeDtoProjection projection : projections) {
            prioritizeTrade(projection);
        }
    }

    @Transactional
    @Modifying
    @Query(value = "UPDATE TradeEntity t SET " +
                   "t.minutesToTrade= :#{#projection.minutesToTrade}, " +
                   "t.tradePriority = :#{#projection.tradePriority} " +
                   "WHERE t.tradeId = :#{#projection.tradeId}")
    void prioritizeTrade(PrioritizedTradeDtoProjection projection);

    @Transactional(readOnly = true)
    @Query("SELECT new github.ricemonger.item_trade_stats_calculator.postgres.dto_projections.UbiTradeDtoProjection(" +
           "t.tradeId," +
           " t.state," +
           "t.category," +
           "t.expiresAt," +
           "t.lastModifiedAt," +
           "t.item," +
           "t.successPaymentPrice," +
           "t.successPaymentFee," +
           "t.proposedPaymentPrice," +
           "t.proposedPaymentFee) " +
           "FROM TradeEntity t")
    List<UbiTradeDtoProjection> findAllUbiTrades();
}
