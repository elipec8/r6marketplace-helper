package github.ricemonger.configs_fetcher.database.postgres.repositories;


import github.ricemonger.utilspostgresschema.full_entities.item.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagPostgresRepository extends JpaRepository<TagEntity, String> {
}
