package github.ricemonger.item_stats_fetcher.databases.postgres.repositories;


import github.ricemonger.item_stats_fetcher.databases.postgres.entities.ItemSaleEntity;
import github.ricemonger.item_stats_fetcher.databases.postgres.entities.ItemSaleEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemSalePostgresRepository extends JpaRepository<ItemSaleEntity, ItemSaleEntityId> {

}
