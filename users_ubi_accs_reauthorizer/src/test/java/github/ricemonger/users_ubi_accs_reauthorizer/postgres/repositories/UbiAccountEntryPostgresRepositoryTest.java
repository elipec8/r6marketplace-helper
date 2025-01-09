package github.ricemonger.users_ubi_accs_reauthorizer.postgres.repositories;

import github.ricemonger.users_ubi_accs_reauthorizer.postgres.dto_projections.UnauthorizedAccountProjection;
import github.ricemonger.users_ubi_accs_reauthorizer.postgres.entities.UbiAccountEntryEntity;
import github.ricemonger.users_ubi_accs_reauthorizer.postgres.entities.UbiAccountStatsIdEntity;
import github.ricemonger.users_ubi_accs_reauthorizer.postgres.entities.UserIdEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Disabled // EntityExistsException: detached entity passed to persist UserIdEntity, UbiAccountEntryEntity
class UbiAccountEntryPostgresRepositoryTest {
    @Autowired
    private UbiAccountEntryPostgresRepository ubiAccountEntryPostgresRepository;
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    @Transactional
    void setUp() {
        ubiAccountEntryPostgresRepository.deleteAll();
    }

    @Test
    public void deleteAllUbiAccountStatsForUnauthorizedUsers_should_remove_ubi_account_stats_only_for_unauthorized_users() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            UserIdEntity user1 = new UserIdEntity(1L);
            entityManager.persist(user1);

            UbiAccountEntryEntity entry1 = new UbiAccountEntryEntity();
            entry1.setUser(user1);
            entry1.setEmail("email1");
            UbiAccountStatsIdEntity stats1 = new UbiAccountStatsIdEntity("ubiProfileId1");
            entityManager.persist(stats1);
            entry1.setUbiAccountStats(stats1);
            entityManager.persist(entry1);

            UserIdEntity user2 = new UserIdEntity(2L);
            entityManager.persist(user2);

            UbiAccountEntryEntity entry2 = new UbiAccountEntryEntity();
            entry2.setUser(user2);
            entry2.setEmail("email2");
            UbiAccountStatsIdEntity stats2 = new UbiAccountStatsIdEntity("ubiProfileId2");
            entityManager.persist(stats2);
            entry2.setUbiAccountStats(stats2);
            entityManager.persist(entry2);

            UserIdEntity user3 = new UserIdEntity(3L);
            entityManager.persist(user3);

            UbiAccountEntryEntity entry3 = new UbiAccountEntryEntity();
            entry3.setUser(user3);
            entry3.setEmail("email3");
            UbiAccountStatsIdEntity stats3 = new UbiAccountStatsIdEntity("ubiProfileId3");
            entityManager.persist(stats3);
            entry3.setUbiAccountStats(stats3);
            entityManager.persist(entry3);

            entityManager.getTransaction().commit();

            entityManager.clear();

            List<UnauthorizedAccountProjection> projectionList = List.of(new UnauthorizedAccountProjection(1L, "email1"), new UnauthorizedAccountProjection(2L, "email2"));

            ubiAccountEntryPostgresRepository.deleteAllUbiAccountStatsForUnauthorizedUsers(projectionList);

            List<UbiAccountEntryEntity> result = ubiAccountEntryPostgresRepository.findAll();

            assertEquals(3, result.size());
            assertTrue(result.stream().anyMatch(res -> res.getUser().equals(user1) && res.getEmail().equals("email1") && res.getUbiAccountStats() == null));
            assertTrue(result.stream().anyMatch(res -> res.getUser().equals(user2) && res.getEmail().equals("email2") && res.getUbiAccountStats() == null));
            assertTrue(result.stream().anyMatch(res -> res.getUser().equals(user3) && res.getEmail().equals("email1") && res.getUbiAccountStats() != null));
        } finally {
            entityManager.close();
        }
    }

    @Test

    public void deleteUbiAccountStatsForUnauthorizedUser_should_remove_ubi_account_stats_only_for_unauthorized_users() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.clear();
            entityManager.getTransaction().begin();

            UserIdEntity user1 = new UserIdEntity(1L);
            entityManager.persist(user1);

            UbiAccountEntryEntity entry1 = new UbiAccountEntryEntity();
            entry1.setUser(user1);
            entry1.setEmail("email1");
            UbiAccountStatsIdEntity stats1 = new UbiAccountStatsIdEntity("ubiProfileId1");
            entityManager.persist(stats1);
            entry1.setUbiAccountStats(stats1);
            entityManager.persist(entry1);

            UserIdEntity user2 = new UserIdEntity(2L);
            entityManager.persist(user2);

            UbiAccountEntryEntity entry2 = new UbiAccountEntryEntity();
            entry2.setUser(user2);
            entry2.setEmail("email2");
            UbiAccountStatsIdEntity stats2 = new UbiAccountStatsIdEntity("ubiProfileId2");
            entityManager.persist(stats2);
            entry2.setUbiAccountStats(stats2);
            entityManager.persist(entry2);

            UserIdEntity user3 = new UserIdEntity(3L);
            entityManager.persist(user3);

            UbiAccountEntryEntity entry3 = new UbiAccountEntryEntity();
            entry3.setUser(user3);
            entry3.setEmail("email3");
            UbiAccountStatsIdEntity stats3 = new UbiAccountStatsIdEntity("ubiProfileId3");
            entityManager.persist(stats3);
            entry3.setUbiAccountStats(stats3);
            entityManager.persist(entry3);

            entityManager.getTransaction().commit();

            entityManager.clear();

            List<UnauthorizedAccountProjection> projectionList = List.of(new UnauthorizedAccountProjection(1L, "email1"), new UnauthorizedAccountProjection(2L, "email2"));

            ubiAccountEntryPostgresRepository.deleteUbiAccountStatsForUnauthorizedUser(projectionList.get(0));
            ubiAccountEntryPostgresRepository.deleteUbiAccountStatsForUnauthorizedUser(projectionList.get(1));

            List<UbiAccountEntryEntity> result = ubiAccountEntryPostgresRepository.findAll();

            assertEquals(3, result.size());
            assertTrue(result.stream().anyMatch(res -> res.getUser().equals(user1) && res.getEmail().equals("email1") && res.getUbiAccountStats() == null));
            assertTrue(result.stream().anyMatch(res -> res.getUser().equals(user2) && res.getEmail().equals("email2") && res.getUbiAccountStats() == null));
            assertTrue(result.stream().anyMatch(res -> res.getUser().equals(user3) && res.getEmail().equals("email1") && res.getUbiAccountStats() != null));
        } finally {
            entityManager.close();
        }
    }
}