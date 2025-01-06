package github.ricemonger.notifications_service.postgres.repositories;

import github.ricemonger.notifications_service.services.DTOs.ToBeNotifiedUser;
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
            user.setPrivateNotificationsEnabledFlag(true);
            user.setPublicNotificationsEnabledFlag(false);
            entityManager.persist(user);
            TelegramUserEntity telegramUserEntity = new TelegramUserEntity(chatId);
            telegramUserEntity.setUser(user);
            entityManager.persist(telegramUserEntity);
            user.setTelegramUser(telegramUserEntity);
            entityManager.merge(user);
            entityManager.getTransaction().commit();

            assertEquals(new ToBeNotifiedUser("chatId", true, false), userPostgresRepository.findToBeNotifiedUserIdById(user.getId()).orElse(null));
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
            entityManager.persist(user2);
            TelegramUserEntity telegramUserEntity2 = new TelegramUserEntity(chatId2);
            telegramUserEntity2.setUser(user2);
            entityManager.persist(telegramUserEntity2);
            user2.setTelegramUser(telegramUserEntity2);
            entityManager.merge(user2);
            entityManager.getTransaction().commit();

            List<ToBeNotifiedUser> expected = List.of(new ToBeNotifiedUser("chatId1", true, false), new ToBeNotifiedUser("chatId2", false, true));

            List<ToBeNotifiedUser> result = userPostgresRepository.findAllToBeNotifiedUsers();

            assertEquals(result.size(), expected.size());
            assertTrue(result.containsAll(expected));
        } finally {
            entityManager.close();
        }
    }
}