package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserPostgresRepository extends JpaRepository<UserEntity, String> {
    @Query("SELECT u FROM helper_user u WHERE (SIZE(u.tradeByItemIdManagers) > 0 OR SIZE(u.tradeByFiltersManagers) > 0) AND u.managingEnabledFlag = true AND u.ubiAccountEntry IS NOT NULL")
    List<UserEntity> findAllManageableUsers();

    UserEntity findByTelegramUserChatId(String chatId);
}
