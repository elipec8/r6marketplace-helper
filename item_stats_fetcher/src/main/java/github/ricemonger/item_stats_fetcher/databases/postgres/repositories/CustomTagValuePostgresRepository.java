package github.ricemonger.item_stats_fetcher.databases.postgres.repositories;


import github.ricemonger.item_stats_fetcher.databases.postgres.custom_entities.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomTagValuePostgresRepository extends JpaRepository<TagEntity, String> {
}
