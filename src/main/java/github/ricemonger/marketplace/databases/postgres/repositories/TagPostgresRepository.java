package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TagPostgresRepository extends JpaRepository<TagEntity, String> {
    Optional<TagEntity> findByName(String name);

    @Query("SELECT t FROM tag t WHERE t.name IN (:names)")
    List<TagEntity> findAllByNames(@Param("names") Collection<String> names);
}
