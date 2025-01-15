package github.ricemonger.utilspostgresschema.repositories.item;

import github.ricemonger.utilspostgresschema.full_entities.item.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagPostgresRepository extends JpaRepository<TagEntity, String> {
}
