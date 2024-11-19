package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemSaleEntity;
import github.ricemonger.marketplace.databases.postgres.entities.item.ItemSaleEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemSalePostgresRepository extends JpaRepository<ItemSaleEntity, ItemSaleEntityId> {

    @Query(value = "SELECT * FROM item_sale s WHERE s.sold_at >= CURRENT_DATE-30", nativeQuery = true)
    List<ItemSaleEntity> findAllLastMonthSales();
}
