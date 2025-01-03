package github.ricemonger.notifications_service.postgres.repositories;

import github.ricemonger.utilspostgresschema.full_entities.user.TelegramUserEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserPostgresRepositoryTest {
    @Autowired
    private UserPostgresRepository userPostgresRepository;
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Test
    void findTelegramUserChatIdById_should_return_expected_user() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            String chatId = "chatId";
            UserEntity user = new UserEntity();
            entityManager.persist(user);
            TelegramUserEntity telegramUserEntity = new TelegramUserEntity(chatId);
            telegramUserEntity.setUser(user);
            entityManager.persist(telegramUserEntity);
            user.setTelegramUser(telegramUserEntity);
            entityManager.merge(user);
            entityManager.getTransaction().commit();

            assertEquals(chatId, userPostgresRepository.findTelegramUserChatIdById(user.getId()).orElse(null));
        } finally {
            entityManager.close();
        }
    }

}