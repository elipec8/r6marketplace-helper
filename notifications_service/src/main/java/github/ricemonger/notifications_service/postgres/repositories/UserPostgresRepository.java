package github.ricemonger.notifications_service.postgres.repositories;

import github.ricemonger.notifications_service.services.DTOs.ToBeNotifiedUser;
import github.ricemonger.utilspostgresschema.full_entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserPostgresRepository extends JpaRepository<UserEntity, Long> {
    @Transactional(readOnly = true)
    @Query("SELECT new github.ricemonger.notifications_service.services.DTOs.ToBeNotifiedUser(" +
           "u.telegramUser.chatId," +
           "u.privateNotificationsEnabledFlag," +
           "u.publicNotificationsEnabledFlag)" +
           "FROM UserEntity u WHERE u.id = :id")
    Optional<ToBeNotifiedUser> findToBeNotifiedUserIdById(Long id);

    @Transactional(readOnly = true)
    @Query("SELECT new github.ricemonger.notifications_service.services.DTOs.ToBeNotifiedUser(" +
           "u.telegramUser.chatId," +
           "u.privateNotificationsEnabledFlag," +
           "u.publicNotificationsEnabledFlag)" +
           "FROM UserEntity u")
    List<ToBeNotifiedUser> findAllToBeNotifiedUsers();
}
