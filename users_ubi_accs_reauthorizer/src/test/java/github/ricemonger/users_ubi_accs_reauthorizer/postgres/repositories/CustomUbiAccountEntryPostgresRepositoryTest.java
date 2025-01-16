package github.ricemonger.users_ubi_accs_reauthorizer.postgres.repositories;

import github.ricemonger.users_ubi_accs_reauthorizer.postgres.dto_projections.UserUnauthorizedUbiAccountProjection;
import github.ricemonger.utilspostgresschema.full_entities.user.UbiAccountEntryEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.UbiAccountStatsEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.UserEntity;
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
class CustomUbiAccountEntryPostgresRepositoryTest {
    @Autowired
    private CustomUbiAccountEntryPostgresRepository customUbiAccountEntryPostgresRepository;
    @Autowired
    private CustomUbiAccountEntryPostgresRepository ubiAccountStatsPostgresRepository;
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    @Transactional
    void setUp() {
        ubiAccountStatsPostgresRepository.deleteAll();
        customUbiAccountEntryPostgresRepository.deleteAll();
    }

    @Test
    @Transactional
    @Disabled // EntityExistsException: detached entity passed to persist UserEntity, UbiAccountEntryEntity
    public void unlinkAllUbiAccountStatsForUnauthorizedUsers_should_remove_ubi_account_stats_only_for_unauthorized_users() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            UserEntity user1 = new UserEntity(1L);
            entityManager.persist(user1);

            UbiAccountEntryEntity entry1 = new UbiAccountEntryEntity();
            entry1.setUser(user1);
            entry1.setEmail("email1");
            UbiAccountStatsEntity stats1 = new UbiAccountStatsEntity("ubiProfileId1");
            entityManager.persist(stats1);
            entry1.setUbiAccountStats(stats1);
            entityManager.persist(entry1);

            UserEntity user2 = new UserEntity(2L);
            entityManager.persist(user2);

            UbiAccountEntryEntity entry2 = new UbiAccountEntryEntity();
            entry2.setUser(user2);
            entry2.setEmail("email2");
            UbiAccountStatsEntity stats2 = new UbiAccountStatsEntity("ubiProfileId2");
            entityManager.persist(stats2);
            entry2.setUbiAccountStats(stats2);
            entityManager.persist(entry2);

            UserEntity user3 = new UserEntity(3L);
            entityManager.persist(user3);

            UbiAccountEntryEntity entry3 = new UbiAccountEntryEntity();
            entry3.setUser(user3);
            entry3.setEmail("email3");
            UbiAccountStatsEntity stats3 = new UbiAccountStatsEntity("ubiProfileId3");
            entityManager.persist(stats3);
            entry3.setUbiAccountStats(stats3);
            entityManager.persist(entry3);

            entityManager.getTransaction().commit();

            entityManager.clear();

            List<UserUnauthorizedUbiAccountProjection> projectionList = List.of(new UserUnauthorizedUbiAccountProjection(1L, "email1"), new UserUnauthorizedUbiAccountProjection(2L, "email2"));

            customUbiAccountEntryPostgresRepository.unlinkAllUbiAccountStatsForUnauthorizedUsers(projectionList);

            List<UbiAccountEntryEntity> result = customUbiAccountEntryPostgresRepository.findAll();

            assertEquals(3, result.size());
            assertTrue(result.stream().anyMatch(res -> res.getUser().equals(user1) && res.getEmail().equals("email1") && res.getUbiAccountStats() == null));
            assertTrue(result.stream().anyMatch(res -> res.getUser().equals(user2) && res.getEmail().equals("email2") && res.getUbiAccountStats() == null));
            assertTrue(result.stream().anyMatch(res -> res.getUser().equals(user3) && res.getEmail().equals("email1") && res.getUbiAccountStats() != null));
        } finally {
            entityManager.close();
        }
    }

    @Test
    @Disabled // EntityExistsException: detached entity passed to persist UserEntity, UbiAccountEntryEntity
    public void unlinkUbiAccountStatsForUnauthorizedUser_should_remove_ubi_account_stats_only_for_unauthorized_users() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.clear();
            entityManager.getTransaction().begin();

            UserEntity user1 = new UserEntity(1L);
            entityManager.persist(user1);

            UbiAccountEntryEntity entry1 = new UbiAccountEntryEntity();
            entry1.setUser(user1);
            entry1.setEmail("email1");
            UbiAccountStatsEntity stats1 = new UbiAccountStatsEntity("ubiProfileId1");
            entityManager.persist(stats1);
            entry1.setUbiAccountStats(stats1);
            entityManager.persist(entry1);

            UserEntity user2 = new UserEntity(2L);
            entityManager.persist(user2);

            UbiAccountEntryEntity entry2 = new UbiAccountEntryEntity();
            entry2.setUser(user2);
            entry2.setEmail("email2");
            UbiAccountStatsEntity stats2 = new UbiAccountStatsEntity("ubiProfileId2");
            entityManager.persist(stats2);
            entry2.setUbiAccountStats(stats2);
            entityManager.persist(entry2);

            UserEntity user3 = new UserEntity(3L);
            entityManager.persist(user3);

            UbiAccountEntryEntity entry3 = new UbiAccountEntryEntity();
            entry3.setUser(user3);
            entry3.setEmail("email3");
            UbiAccountStatsEntity stats3 = new UbiAccountStatsEntity("ubiProfileId3");
            entityManager.persist(stats3);
            entry3.setUbiAccountStats(stats3);
            entityManager.persist(entry3);

            entityManager.getTransaction().commit();

            entityManager.clear();

            List<UserUnauthorizedUbiAccountProjection> projectionList = List.of(new UserUnauthorizedUbiAccountProjection(1L, "email1"), new UserUnauthorizedUbiAccountProjection(2L, "email2"));

            customUbiAccountEntryPostgresRepository.unlinkUbiAccountStatsForUnauthorizedUser(projectionList.get(0));
            customUbiAccountEntryPostgresRepository.unlinkUbiAccountStatsForUnauthorizedUser(projectionList.get(1));

            List<UbiAccountEntryEntity> result = customUbiAccountEntryPostgresRepository.findAll();

            assertEquals(3, result.size());
            assertTrue(result.stream().anyMatch(res -> res.getUser().equals(user1) && res.getEmail().equals("email1") && res.getUbiAccountStats() == null));
            assertTrue(result.stream().anyMatch(res -> res.getUser().equals(user2) && res.getEmail().equals("email2") && res.getUbiAccountStats() == null));
            assertTrue(result.stream().anyMatch(res -> res.getUser().equals(user3) && res.getEmail().equals("email1") && res.getUbiAccountStats() != null));
        } finally {
            entityManager.close();
        }
    }
}