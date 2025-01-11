package github.ricemonger.notifications_service.postgres.repositories;

import github.ricemonger.notifications_service.postgres.dto_projections.ToBeNotifiedUserProjection;
import github.ricemonger.utilspostgresschema.full_entities.user.TelegramUserEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserPostgresRepositoryTest {
    @Autowired
    private UserPostgresRepository userPostgresRepository;
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Test
    void findToBeNotifiedUserChatIdById_should_return_expected_user() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            String chatId = "chatId";
            UserEntity user = new UserEntity();
            user.setPrivateNotificationsEnabledFlag(false);
            user.setPublicNotificationsEnabledFlag(true);
            user.setUbiStatsUpdatedNotificationsEnabledFlag(false);
            user.setTradeManagerNotificationsEnabledFlag(true);
            user.setAuthorizationNotificationsEnabledFlag(false);
            entityManager.persist(user);
            TelegramUserEntity telegramUserEntity = new TelegramUserEntity(chatId);
            telegramUserEntity.setUser(user);
            entityManager.persist(telegramUserEntity);
            user.setTelegramUser(telegramUserEntity);
            entityManager.merge(user);
            entityManager.getTransaction().commit();

            assertEquals(new ToBeNotifiedUserProjection("chatId", false, true, false, true, false),
                    userPostgresRepository.findToBeNotifiedUserIdById(user.getId()).orElse(null));
        } finally {
            entityManager.close();
        }
    }

    @Test
    public void findAllToBeNotifiedUsers_should_return_expected_users() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            String chatId1 = "chatId1";
            UserEntity user1 = new UserEntity();
            user1.setPrivateNotificationsEnabledFlag(true);
            user1.setPublicNotificationsEnabledFlag(false);
            user1.setUbiStatsUpdatedNotificationsEnabledFlag(true);
            user1.setTradeManagerNotificationsEnabledFlag(false);
            user1.setAuthorizationNotificationsEnabledFlag(true);
            entityManager.persist(user1);
            TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity(chatId1);
            telegramUserEntity1.setUser(user1);
            entityManager.persist(telegramUserEntity1);
            user1.setTelegramUser(telegramUserEntity1);
            entityManager.merge(user1);

            String chatId2 = "chatId2";
            UserEntity user2 = new UserEntity();
            user2.setPrivateNotificationsEnabledFlag(false);
            user2.setPublicNotificationsEnabledFlag(true);
            user2.setUbiStatsUpdatedNotificationsEnabledFlag(false);
            user2.setTradeManagerNotificationsEnabledFlag(true);
            user2.setAuthorizationNotificationsEnabledFlag(false);
            entityManager.persist(user2);
            TelegramUserEntity telegramUserEntity2 = new TelegramUserEntity(chatId2);
            telegramUserEntity2.setUser(user2);
            entityManager.persist(telegramUserEntity2);
            user2.setTelegramUser(telegramUserEntity2);
            entityManager.merge(user2);
            entityManager.getTransaction().commit();

            List<ToBeNotifiedUserProjection> expected = List.of(new ToBeNotifiedUserProjection("chatId1", true, false, true, false, true),
                    new ToBeNotifiedUserProjection("chatId2", false, true, false, true, false));

            List<ToBeNotifiedUserProjection> result = userPostgresRepository.findAllToBeNotifiedUsers();

            assertEquals(result.size(), expected.size());
            assertTrue(result.containsAll(expected));
        } finally {
            entityManager.close();
        }
    }
}