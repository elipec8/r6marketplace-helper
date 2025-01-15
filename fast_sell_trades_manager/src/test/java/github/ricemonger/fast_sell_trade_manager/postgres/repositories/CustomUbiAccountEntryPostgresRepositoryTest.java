package github.ricemonger.fast_sell_trade_manager.postgres.repositories;

import github.ricemonger.fast_sell_trade_manager.postgres.dto_projections.FastSellManagedUserProjection;
import github.ricemonger.utilspostgresschema.full_entities.user.UbiAccountEntryEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.UbiAccountStatsEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.UserEntity;
import github.ricemonger.utilspostgresschema.repositories.user.UbiAccountEntryPostgresRepository;
import github.ricemonger.utilspostgresschema.repositories.user.UbiAccountStatsPostgresRepository;
import github.ricemonger.utilspostgresschema.repositories.user.UserPostgresRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CustomUbiAccountEntryPostgresRepositoryTest {
    @Autowired
    private CustomUbiAccountEntryPostgresRepository customUbiAccountEntryPostgresRepository;
    @Autowired
    private UbiAccountStatsPostgresRepository ubiAccountStatsPostgresRepository;
    @Autowired
    private UserPostgresRepository userPostgresRepository;

    @BeforeEach
    void setUp() {
        customUbiAccountEntryPostgresRepository.deleteAll();
        ubiAccountStatsPostgresRepository.deleteAll();
        userPostgresRepository.deleteAll();
    }

    @Test
    @Transactional
    public void findFastManagedUserById_should_returnFastSellManagedUserProjection() {
        UserEntity userEntity1 = userPostgresRepository.save(new UserEntity(1L));

        UbiAccountEntryEntity ubiAccountEntryEntity1 = new UbiAccountEntryEntity();
        ubiAccountEntryEntity1.setUser(userEntity1);
        ubiAccountEntryEntity1.setEmail("email1");
        ubiAccountEntryEntity1.setUbiAuthTicket("ubiAuthTicket1");
        ubiAccountEntryEntity1.setUbiSpaceId("ubiSpaceId1");
        ubiAccountEntryEntity1.setUbiSessionId("ubiSessionId1");
        ubiAccountEntryEntity1.setUbiRememberDeviceTicket("ubiRememberDeviceTicket1");
        ubiAccountEntryEntity1.setUbiRememberMeTicket("ubiRememberMeTicket1");

        UbiAccountStatsEntity ubiAccountStatsEntity1 = new UbiAccountStatsEntity();
        ubiAccountStatsEntity1.setUbiProfileId("ubiProfileId1");
        ubiAccountStatsEntity1.setSoldIn24h(1);

        ubiAccountStatsPostgresRepository.save(ubiAccountStatsEntity1);
        ubiAccountEntryEntity1.setUbiAccountStats(ubiAccountStatsEntity1);

        customUbiAccountEntryPostgresRepository.save(ubiAccountEntryEntity1);

        UserEntity userEntity2 = userPostgresRepository.save(new UserEntity(2L));

        UbiAccountEntryEntity ubiAccountEntryEntity2 = new UbiAccountEntryEntity();
        ubiAccountEntryEntity2.setUser(userEntity2);
        ubiAccountEntryEntity2.setEmail("email2");
        ubiAccountEntryEntity2.setUbiAuthTicket("ubiAuthTicket2");
        ubiAccountEntryEntity2.setUbiSpaceId("ubiSpaceId2");
        ubiAccountEntryEntity2.setUbiSessionId("ubiSessionId2");
        ubiAccountEntryEntity2.setUbiRememberDeviceTicket("ubiRememberDeviceTicket2");
        ubiAccountEntryEntity2.setUbiRememberMeTicket("ubiRememberMeTicket2");

        UbiAccountStatsEntity ubiAccountStatsEntity2 = new UbiAccountStatsEntity();
        ubiAccountStatsEntity2.setUbiProfileId("ubiProfileId2");
        ubiAccountStatsEntity2.setSoldIn24h(2);

        ubiAccountStatsPostgresRepository.save(ubiAccountStatsEntity2);
        ubiAccountEntryEntity2.setUbiAccountStats(ubiAccountStatsEntity2);
        customUbiAccountEntryPostgresRepository.save(ubiAccountEntryEntity2);


        FastSellManagedUserProjection fastSellManagedUserProjection1 = customUbiAccountEntryPostgresRepository.findFastSellManagedUserById(1L, "email1").get();
        FastSellManagedUserProjection fastSellManagedUserProjection2 = customUbiAccountEntryPostgresRepository.findFastSellManagedUserById(2L, "email2").get();

        FastSellManagedUserProjection expected1 = new FastSellManagedUserProjection("ubiAuthTicket1", "ubiProfileId1", "ubiSpaceId1", "ubiSessionId1", "ubiRememberDeviceTicket1", "ubiRememberMeTicket1", 1);
        FastSellManagedUserProjection expected2 = new FastSellManagedUserProjection("ubiAuthTicket2", "ubiProfileId2", "ubiSpaceId2", "ubiSessionId2", "ubiRememberDeviceTicket2", "ubiRememberMeTicket2", 2);

        assertEquals(expected1, fastSellManagedUserProjection1);
        assertEquals(expected2, fastSellManagedUserProjection2);
    }
}