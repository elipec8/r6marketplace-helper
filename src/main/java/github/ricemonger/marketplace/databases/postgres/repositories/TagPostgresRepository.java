package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagPostgresRepository extends JpaRepository<TagEntity, String> {
    Optional<TagEntity> findByName(String name);
}
