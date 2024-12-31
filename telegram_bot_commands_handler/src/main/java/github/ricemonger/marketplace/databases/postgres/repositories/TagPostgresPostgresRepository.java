package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.utils.enums.TagGroup;
import github.ricemonger.utilspostgresschema.full_entities.item.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

public interface TagPostgresPostgresRepository extends JpaRepository<TagEntity, String> {
    @Transactional(readOnly = true)
    @Query("SELECT t FROM tag t WHERE t.name IN (:names)")
    List<TagEntity> findAllByNames(@Param("names") Collection<String> names);

    @Transactional(readOnly = true)
    List<TagEntity> findAllByTagGroup(TagGroup tagGroup);
}
