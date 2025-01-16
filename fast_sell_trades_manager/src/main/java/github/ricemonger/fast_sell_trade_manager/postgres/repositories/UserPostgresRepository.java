package github.ricemonger.fast_sell_trade_manager.postgres.repositories;

import github.ricemonger.utilspostgresschema.full_entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPostgresRepository extends JpaRepository<UserEntity, String> {
}
