package github.ricemonger.trades_manager.postgres.repositories;

import github.ricemonger.trades_manager.postgres.dto_projections.ManageableUserProjection;
import github.ricemonger.utilspostgresschema.full_entities.item.ItemEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class CustomUserPostgresRepositoryTest {
    @Autowired
    private CustomUserPostgresRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void findAllUsersWithTradeManagers_should_return_only_manageable_users_has_entry() {

        //Have nothing

        userRepository.save(new UserEntity());
        userRepository.save(new UserEntity());
        userRepository.save(new UserEntity());

        // Have managers, true flag and ubi account entry - must be returned

        UserEntity userWithTradeByItemIdManagerTrue = new UserEntity();
        UbiAccountStatsEntity ubiAccountStatsEntity1 = new UbiAccountStatsEntity();
        ubiAccountStatsEntity1.setUbiProfileId("ubiProfileId1");
        UbiAccountEntryEntity ubiAccountEntryEntity1 = new UbiAccountEntryEntity();
        ubiAccountEntryEntity1.setUser(userWithTradeByItemIdManagerTrue);
        ubiAccountEntryEntity1.setEmail("email");
        ubiAccountEntryEntity1.setUbiAccountStats(ubiAccountStatsEntity1);
        userWithTradeByItemIdManagerTrue.setUbiAccountEntry(ubiAccountEntryEntity1);
        userWithTradeByItemIdManagerTrue.setManagingEnabledFlag(true);
        TradeByItemIdManagerEntity tradeByItemIdManagerEntityTrue = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntityTrue.setItem(new ItemEntity("1"));
        tradeByItemIdManagerEntityTrue.setUser(userWithTradeByItemIdManagerTrue);
        userWithTradeByItemIdManagerTrue.getTradeByItemIdManagers().add(tradeByItemIdManagerEntityTrue);

        UserEntity userWithTradeByFiltersManagerTrue = new UserEntity();
        UbiAccountStatsEntity ubiAccountStatsEntity2 = new UbiAccountStatsEntity();
        ubiAccountStatsEntity2.setUbiProfileId("ubiProfileId2");
        UbiAccountEntryEntity ubiAccountEntryEntity2 = new UbiAccountEntryEntity();
        ubiAccountEntryEntity2.setUser(userWithTradeByFiltersManagerTrue);
        ubiAccountEntryEntity2.setEmail("email");
        ubiAccountEntryEntity2.setUbiAccountStats(ubiAccountStatsEntity2);
        userWithTradeByFiltersManagerTrue.setUbiAccountEntry(ubiAccountEntryEntity2);
        userWithTradeByFiltersManagerTrue.setManagingEnabledFlag(true);
        TradeByFiltersManagerEntity tradeByFiltersManagerEntityTrue = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntityTrue.setName("name");
        tradeByFiltersManagerEntityTrue.setUser(userWithTradeByFiltersManagerTrue);
        userWithTradeByFiltersManagerTrue.getTradeByFiltersManagers().add(tradeByFiltersManagerEntityTrue);

        UserEntity userWithBothManagersTrue = new UserEntity();
        UbiAccountStatsEntity ubiAccountStatsEntity3 = new UbiAccountStatsEntity();
        ubiAccountStatsEntity3.setUbiProfileId("ubiProfileId3");
        UbiAccountEntryEntity ubiAccountEntryEntity3 = new UbiAccountEntryEntity();
        ubiAccountEntryEntity3.setUser(userWithBothManagersTrue);
        ubiAccountEntryEntity3.setEmail("email");
        ubiAccountEntryEntity3.setUbiAccountStats(ubiAccountStatsEntity3);
        userWithBothManagersTrue.setUbiAccountEntry(ubiAccountEntryEntity3);
        userWithBothManagersTrue.setManagingEnabledFlag(true);
        TradeByItemIdManagerEntity tradeByItemIdManagerEntityForBothTrue = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntityForBothTrue.setItem(new ItemEntity("2"));
        tradeByItemIdManagerEntityForBothTrue.setUser(userWithBothManagersTrue);
        TradeByFiltersManagerEntity tradeByFiltersManagerEntityForBothTrue = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntityForBothTrue.setName("name");
        tradeByFiltersManagerEntityForBothTrue.setUser(userWithBothManagersTrue);
        userWithBothManagersTrue.getTradeByItemIdManagers().add(tradeByItemIdManagerEntityForBothTrue);
        userWithBothManagersTrue.getTradeByFiltersManagers().add(tradeByFiltersManagerEntityForBothTrue);

        userWithTradeByItemIdManagerTrue = userRepository.save(userWithTradeByItemIdManagerTrue);
        userWithTradeByFiltersManagerTrue = userRepository.save(userWithTradeByFiltersManagerTrue);
        userWithBothManagersTrue = userRepository.save(userWithBothManagersTrue);

        // Have managers and ubi account entry, but false flag

        UserEntity userWithTradeByItemIdManagerFalse = new UserEntity();
        UbiAccountStatsEntity ubiAccountStatsEntity4 = new UbiAccountStatsEntity();
        ubiAccountStatsEntity4.setUbiProfileId("ubiProfileId4");
        UbiAccountEntryEntity ubiAccountEntryEntity4 = new UbiAccountEntryEntity();
        ubiAccountEntryEntity4.setUser(userWithTradeByItemIdManagerFalse);
        ubiAccountEntryEntity4.setEmail("email");
        ubiAccountEntryEntity4.setUbiAccountStats(ubiAccountStatsEntity4);
        userWithTradeByItemIdManagerFalse.setUbiAccountEntry(ubiAccountEntryEntity4);
        userWithTradeByItemIdManagerFalse.setManagingEnabledFlag(false);
        TradeByItemIdManagerEntity tradeByItemIdManagerEntityFalse = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntityFalse.setItem(new ItemEntity("3"));
        tradeByItemIdManagerEntityFalse.setUser(userWithTradeByItemIdManagerFalse);
        userWithTradeByItemIdManagerFalse.getTradeByItemIdManagers().add(tradeByItemIdManagerEntityFalse);

        UserEntity userWithTradeByFiltersManagerFalse = new UserEntity();
        UbiAccountStatsEntity ubiAccountStatsEntity5 = new UbiAccountStatsEntity();
        ubiAccountStatsEntity5.setUbiProfileId("ubiProfileId5");
        UbiAccountEntryEntity ubiAccountEntryEntity5 = new UbiAccountEntryEntity();
        ubiAccountEntryEntity5.setUser(userWithTradeByFiltersManagerFalse);
        ubiAccountEntryEntity5.setEmail("email");
        ubiAccountEntryEntity5.setUbiAccountStats(ubiAccountStatsEntity5);
        userWithTradeByFiltersManagerFalse.setUbiAccountEntry(ubiAccountEntryEntity5);
        userWithTradeByFiltersManagerFalse.setManagingEnabledFlag(false);
        TradeByFiltersManagerEntity tradeByFiltersManagerEntityFalse = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntityFalse.setName("name");
        tradeByFiltersManagerEntityFalse.setUser(userWithTradeByFiltersManagerFalse);
        userWithTradeByFiltersManagerFalse.getTradeByFiltersManagers().add(tradeByFiltersManagerEntityFalse);

        UserEntity userWithBothManagersFalse = new UserEntity();
        UbiAccountStatsEntity ubiAccountStatsEntity6 = new UbiAccountStatsEntity();
        ubiAccountStatsEntity6.setUbiProfileId("ubiProfileId6");
        UbiAccountEntryEntity ubiAccountEntryEntity6 = new UbiAccountEntryEntity();
        ubiAccountEntryEntity6.setUser(userWithBothManagersFalse);
        ubiAccountEntryEntity6.setEmail("email");
        ubiAccountEntryEntity6.setUbiAccountStats(ubiAccountStatsEntity6);
        userWithBothManagersFalse.setUbiAccountEntry(ubiAccountEntryEntity6);
        userWithBothManagersFalse.setManagingEnabledFlag(false);
        TradeByItemIdManagerEntity tradeByItemIdManagerEntityForBothFalse = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntityForBothFalse.setItem(new ItemEntity("4"));
        tradeByItemIdManagerEntityForBothFalse.setUser(userWithBothManagersFalse);
        TradeByFiltersManagerEntity tradeByFiltersManagerEntityForBothFalse = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntityForBothFalse.setName("name");
        tradeByFiltersManagerEntityForBothFalse.setUser(userWithBothManagersFalse);
        userWithBothManagersFalse.getTradeByItemIdManagers().add(tradeByItemIdManagerEntityForBothFalse);
        userWithBothManagersFalse.getTradeByFiltersManagers().add(tradeByFiltersManagerEntityForBothFalse);

        userRepository.save(userWithTradeByItemIdManagerFalse);
        userRepository.save(userWithTradeByFiltersManagerFalse);
        userRepository.save(userWithBothManagersFalse);

        List<ManageableUserProjection> projections = userRepository.findAllManageableUsers();

        assertEquals(9, userRepository.count());
        assertEquals(3, projections.size());
    }

    @Test
    public void findAllUsersWithTradeManagers_should_return_only_manageable_users_has_no_entry() {

        //Have nothing

        userRepository.save(new UserEntity());
        userRepository.save(new UserEntity());
        userRepository.save(new UserEntity());

        // Have managers and true flag but no ubi account entry

        UserEntity userWithTradeByItemIdManagerTrue = new UserEntity();
        userWithTradeByItemIdManagerTrue.setManagingEnabledFlag(true);
        TradeByItemIdManagerEntity tradeByItemIdManagerEntityTrue = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntityTrue.setItem(new ItemEntity("1"));
        tradeByItemIdManagerEntityTrue.setUser(userWithTradeByItemIdManagerTrue);
        userWithTradeByItemIdManagerTrue.getTradeByItemIdManagers().add(tradeByItemIdManagerEntityTrue);

        UserEntity userWithTradeByFiltersManagerTrue = new UserEntity();
        userWithTradeByFiltersManagerTrue.setManagingEnabledFlag(true);
        TradeByFiltersManagerEntity tradeByFiltersManagerEntityTrue = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntityTrue.setName("name");
        tradeByFiltersManagerEntityTrue.setUser(userWithTradeByFiltersManagerTrue);
        userWithTradeByFiltersManagerTrue.getTradeByFiltersManagers().add(tradeByFiltersManagerEntityTrue);

        UserEntity userWithBothManagersTrue = new UserEntity();
        userWithBothManagersTrue.setManagingEnabledFlag(true);
        TradeByItemIdManagerEntity tradeByItemIdManagerEntityForBothTrue = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntityForBothTrue.setItem(new ItemEntity("2"));
        tradeByItemIdManagerEntityForBothTrue.setUser(userWithBothManagersTrue);
        TradeByFiltersManagerEntity tradeByFiltersManagerEntityForBothTrue = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntityForBothTrue.setName("name");
        tradeByFiltersManagerEntityForBothTrue.setUser(userWithBothManagersTrue);
        userWithBothManagersTrue.getTradeByItemIdManagers().add(tradeByItemIdManagerEntityForBothTrue);
        userWithBothManagersTrue.getTradeByFiltersManagers().add(tradeByFiltersManagerEntityForBothTrue);

        userRepository.save(userWithTradeByItemIdManagerTrue);
        userRepository.save(userWithTradeByFiltersManagerTrue);
        userRepository.save(userWithBothManagersTrue);

        // Have managers, but false flag and no entry

        UserEntity userWithTradeByItemIdManagerFalse = new UserEntity();
        userWithTradeByItemIdManagerFalse.setManagingEnabledFlag(false);
        TradeByItemIdManagerEntity tradeByItemIdManagerEntityFalse = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntityFalse.setItem(new ItemEntity("3"));
        tradeByItemIdManagerEntityFalse.setUser(userWithTradeByItemIdManagerFalse);
        userWithTradeByItemIdManagerFalse.getTradeByItemIdManagers().add(tradeByItemIdManagerEntityFalse);

        UserEntity userWithTradeByFiltersManagerFalse = new UserEntity();
        userWithTradeByFiltersManagerFalse.setManagingEnabledFlag(false);
        TradeByFiltersManagerEntity tradeByFiltersManagerEntityFalse = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntityFalse.setName("name");
        tradeByFiltersManagerEntityFalse.setUser(userWithTradeByFiltersManagerFalse);
        userWithTradeByFiltersManagerFalse.getTradeByFiltersManagers().add(tradeByFiltersManagerEntityFalse);

        UserEntity userWithBothManagersFalse = new UserEntity();
        userWithBothManagersFalse.setManagingEnabledFlag(false);
        TradeByItemIdManagerEntity tradeByItemIdManagerEntityForBothFalse = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntityForBothFalse.setItem(new ItemEntity("4"));
        tradeByItemIdManagerEntityForBothFalse.setUser(userWithBothManagersFalse);
        TradeByFiltersManagerEntity tradeByFiltersManagerEntityForBothFalse = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntityForBothFalse.setName("name");
        tradeByFiltersManagerEntityForBothFalse.setUser(userWithBothManagersFalse);
        userWithBothManagersFalse.getTradeByItemIdManagers().add(tradeByItemIdManagerEntityForBothFalse);
        userWithBothManagersFalse.getTradeByFiltersManagers().add(tradeByFiltersManagerEntityForBothFalse);

        userRepository.save(userWithTradeByItemIdManagerFalse);
        userRepository.save(userWithTradeByFiltersManagerFalse);
        userRepository.save(userWithBothManagersFalse);

        // Have managers and true flag and ubi account entry but no ubi account stats

        UserEntity userWithTradeByItemIdManagerTrueNoStats = new UserEntity();
        UbiAccountEntryEntity ubiAccountEntryEntity1 = new UbiAccountEntryEntity();
        ubiAccountEntryEntity1.setUser(userWithTradeByItemIdManagerTrueNoStats);
        ubiAccountEntryEntity1.setEmail("email");
        userWithTradeByItemIdManagerTrueNoStats.setUbiAccountEntry(ubiAccountEntryEntity1);
        userWithTradeByItemIdManagerTrueNoStats.setManagingEnabledFlag(true);
        TradeByItemIdManagerEntity tradeByItemIdManagerEntityTrueNoStats = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntityTrueNoStats.setItem(new ItemEntity("5"));
        tradeByItemIdManagerEntityTrueNoStats.setUser(userWithTradeByItemIdManagerTrueNoStats);
        userWithTradeByItemIdManagerTrueNoStats.getTradeByItemIdManagers().add(tradeByItemIdManagerEntityTrueNoStats);

        UserEntity userWithTradeByFiltersManagerTrueNoStats = new UserEntity();
        UbiAccountEntryEntity ubiAccountEntryEntity2 = new UbiAccountEntryEntity();
        ubiAccountEntryEntity2.setUser(userWithTradeByFiltersManagerTrueNoStats);
        ubiAccountEntryEntity2.setEmail("email");
        userWithTradeByFiltersManagerTrueNoStats.setUbiAccountEntry(ubiAccountEntryEntity2);
        userWithTradeByFiltersManagerTrueNoStats.setManagingEnabledFlag(true);
        TradeByFiltersManagerEntity tradeByFiltersManagerEntityTrueNoStats = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntityTrueNoStats.setName("name");
        tradeByFiltersManagerEntityTrueNoStats.setUser(userWithTradeByFiltersManagerTrueNoStats);
        userWithTradeByFiltersManagerTrueNoStats.getTradeByFiltersManagers().add(tradeByFiltersManagerEntityTrueNoStats);

        UserEntity userWithBothManagersTrueNoStats = new UserEntity();
        UbiAccountEntryEntity ubiAccountEntryEntity3 = new UbiAccountEntryEntity();
        ubiAccountEntryEntity3.setUser(userWithBothManagersTrueNoStats);
        ubiAccountEntryEntity3.setEmail("email");
        userWithBothManagersTrueNoStats.setUbiAccountEntry(ubiAccountEntryEntity3);
        userWithBothManagersTrueNoStats.setManagingEnabledFlag(true);
        TradeByItemIdManagerEntity tradeByItemIdManagerEntityForBothTrueNoStats = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntityForBothTrueNoStats.setItem(new ItemEntity("6"));
        tradeByItemIdManagerEntityForBothTrueNoStats.setUser(userWithBothManagersTrueNoStats);
        TradeByFiltersManagerEntity tradeByFiltersManagerEntityForBothTrueNoStats = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntityForBothTrueNoStats.setName("name");
        tradeByFiltersManagerEntityForBothTrueNoStats.setUser(userWithBothManagersTrueNoStats);
        userWithBothManagersTrueNoStats.getTradeByItemIdManagers().add(tradeByItemIdManagerEntityForBothTrueNoStats);
        userWithBothManagersTrueNoStats.getTradeByFiltersManagers().add(tradeByFiltersManagerEntityForBothTrueNoStats);

        userRepository.save(userWithTradeByItemIdManagerTrueNoStats);
        userRepository.save(userWithTradeByFiltersManagerTrueNoStats);
        userRepository.save(userWithBothManagersTrueNoStats);

        assertEquals(12, userRepository.count());
        assertEquals(0, userRepository.findAllManageableUsers().size());
    }
}