package github.ricemonger.utilspostgresschema.repositories.user;

import github.ricemonger.utilspostgresschema.full_entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPostgresRepository extends JpaRepository<UserEntity, Long> {
}
