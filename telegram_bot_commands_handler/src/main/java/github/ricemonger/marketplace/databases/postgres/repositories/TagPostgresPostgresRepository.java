package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.utils.enums.TagGroup;
import github.ricemonger.utilspostgresschema.full_entities.item.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface TagPostgresPostgresRepository extends JpaRepository<TagEntity, String> {
    @Query("SELECT t FROM full_tag t WHERE t.name IN (:names)")
    List<TagEntity> findAllByNames(@Param("names") Collection<String> names);

    List<TagEntity> findAllByTagGroup(TagGroup tagGroup);
}
