package github.ricemonger.item_stats_fetcher.databases.postgres.repositories;


import github.ricemonger.item_stats_fetcher.databases.postgres.entities.TagValueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagValuePostgresRepository extends JpaRepository<TagValueEntity, String> {
}
