package github.ricemonger.ubi_users_stats_fetcher.postgres.repositories;

import github.ricemonger.ubi_users_stats_fetcher.postgres.entities.user_ubi_account_entry.UbiAccountEntryEntity;
import github.ricemonger.ubi_users_stats_fetcher.postgres.entities.user_ubi_account_entry.UbiAccountStatsIdCreditAmountEntity;
import github.ricemonger.ubi_users_stats_fetcher.postgres.entities.user_ubi_account_entry.UserUbiAccountEntryEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserUbiAccountEntryPostgresRepositoryTest {
    @Autowired
    private UserUbiAccountEntryPostgresRepository userUbiAccountEntryPostgresRepository;

    @BeforeEach
    @Transactional
    public void setUp() {
        userUbiAccountEntryPostgresRepository.deleteAll();
    }

    @Test
    @Transactional
    public void findAllUserWithAuthorizedUbiAccounts_should_return_only_users_with_Authorized_ubi_accounts_entries() {
        UserUbiAccountEntryEntity userUbiAccountEntryEntity1 = new UserUbiAccountEntryEntity();

        UbiAccountEntryEntity ubiAccountEntryEntity1 = new UbiAccountEntryEntity();
        ubiAccountEntryEntity1.setEmail("email1");
        ubiAccountEntryEntity1.setUbiAuthTicket("ubiAuthTicket1");
        ubiAccountEntryEntity1.setUbiSpaceId("ubiSpaceId1");
        ubiAccountEntryEntity1.setUbiSessionId("ubiSessionId1");
        ubiAccountEntryEntity1.setUbiRememberMeTicket("ubiRememberMeTicket1");
        ubiAccountEntryEntity1.setUbiRememberDeviceTicket("ubiRememberDeviceTicket1");

        ubiAccountEntryEntity1.setUser(userUbiAccountEntryEntity1);

        userUbiAccountEntryEntity1.setUbiAccountEntry(ubiAccountEntryEntity1);

        UbiAccountStatsIdCreditAmountEntity ubiAccountStatsEntity1 = new UbiAccountStatsIdCreditAmountEntity();
        ubiAccountStatsEntity1.setUbiProfileId("ubiProfileId1");
        ubiAccountStatsEntity1.setCreditAmount(100);

        ubiAccountEntryEntity1.setUbiAccountStats(ubiAccountStatsEntity1);


        UserUbiAccountEntryEntity userUbiAccountEntryEntity2 = new UserUbiAccountEntryEntity();

        UbiAccountEntryEntity ubiAccountEntryEntity2 = new UbiAccountEntryEntity();
        ubiAccountEntryEntity2.setEmail("email2");
        ubiAccountEntryEntity2.setUbiAuthTicket("ubiAuthTicket2");
        ubiAccountEntryEntity2.setUbiSpaceId("ubiSpaceId2");
        ubiAccountEntryEntity2.setUbiSessionId("ubiSessionId2");
        ubiAccountEntryEntity2.setUbiRememberMeTicket("ubiRememberMeTicket2");
        ubiAccountEntryEntity2.setUbiRememberDeviceTicket("ubiRememberDeviceTicket2");

        ubiAccountEntryEntity2.setUser(userUbiAccountEntryEntity2);

        userUbiAccountEntryEntity2.setUbiAccountEntry(ubiAccountEntryEntity2);

        UbiAccountStatsIdCreditAmountEntity ubiAccountStatsEntity2 = new UbiAccountStatsIdCreditAmountEntity();
        ubiAccountStatsEntity2.setUbiProfileId("ubiProfileId2");
        ubiAccountStatsEntity2.setCreditAmount(200);

        ubiAccountEntryEntity2.setUbiAccountStats(ubiAccountStatsEntity2);


        UserUbiAccountEntryEntity userUbiAccountEntryEntity3 = new UserUbiAccountEntryEntity();

        UbiAccountEntryEntity ubiAccountEntryEntity3 = new UbiAccountEntryEntity();
        ubiAccountEntryEntity3.setEmail("email3");
        ubiAccountEntryEntity3.setUbiAuthTicket("ubiAuthTicket3");
        ubiAccountEntryEntity3.setUbiSpaceId("ubiSpaceId3");
        ubiAccountEntryEntity3.setUbiSessionId("ubiSessionId3");
        ubiAccountEntryEntity3.setUbiRememberMeTicket("ubiRememberMeTicket3");
        ubiAccountEntryEntity3.setUbiRememberDeviceTicket("ubiRememberDeviceTicket3");

        ubiAccountEntryEntity3.setUser(userUbiAccountEntryEntity3);

        userUbiAccountEntryEntity3.setUbiAccountEntry(ubiAccountEntryEntity3);


        UserUbiAccountEntryEntity userUbiAccountEntryEntity4 = new UserUbiAccountEntryEntity();


        userUbiAccountEntryPostgresRepository.save(userUbiAccountEntryEntity1);
        userUbiAccountEntryPostgresRepository.save(userUbiAccountEntryEntity2);
        userUbiAccountEntryPostgresRepository.save(userUbiAccountEntryEntity3);
        userUbiAccountEntryPostgresRepository.save(userUbiAccountEntryEntity4);


        List<UserUbiAccountEntryEntity> result = userUbiAccountEntryPostgresRepository.findAllUserWithAuthorizedUbiAccounts();

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(user -> user.getId().equals(userUbiAccountEntryEntity1.getId())));
        assertTrue(result.stream().anyMatch(user -> user.getId().equals(userUbiAccountEntryEntity2.getId())));
    }
}