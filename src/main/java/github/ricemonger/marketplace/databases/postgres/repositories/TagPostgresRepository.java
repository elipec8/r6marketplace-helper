package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagPostgresRepository extends JpaRepository<TagEntity, String> {
}
