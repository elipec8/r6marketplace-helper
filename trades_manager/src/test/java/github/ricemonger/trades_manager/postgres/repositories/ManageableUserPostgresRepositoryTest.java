package github.ricemonger.trades_manager.postgres.repositories;

import github.ricemonger.trades_manager.postgres.entities.items.ItemIdEntity;
import github.ricemonger.trades_manager.postgres.entities.manageable_users.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class ManageableUserPostgresRepositoryTest {
    @Autowired
    private ManageableUserPostgresRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void findAllUsersWithTradeManagers_should_return_only_manageable_users_has_entry() {

        //Have nothing

        userRepository.save(new ManageableUserEntity());
        userRepository.save(new ManageableUserEntity());
        userRepository.save(new ManageableUserEntity());

        // Have managers, true flag and ubi account entry - must be returned

        ManageableUserEntity userWithTradeByItemIdManagerTrue = new ManageableUserEntity();
        UbiAccountStatsEntity ubiAccountStatsEntity1 = new UbiAccountStatsEntity();
        ubiAccountStatsEntity1.setUbiProfileId("ubiProfileId1");
        ManageableUserUbiAccountEntryEntity ubiAccountEntryEntity1 = new ManageableUserUbiAccountEntryEntity();
        ubiAccountEntryEntity1.setUser(userWithTradeByItemIdManagerTrue);
        ubiAccountEntryEntity1.setEmail("email");
        ubiAccountEntryEntity1.setUbiAccountStats(ubiAccountStatsEntity1);
        userWithTradeByItemIdManagerTrue.setUbiAccountEntry(ubiAccountEntryEntity1);
        userWithTradeByItemIdManagerTrue.setManagingEnabledFlag(true);
        TradeByItemIdManagerEntity tradeByItemIdManagerEntityTrue = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntityTrue.setItem(new ItemIdEntity("1"));
        tradeByItemIdManagerEntityTrue.setUser(userWithTradeByItemIdManagerTrue);
        userWithTradeByItemIdManagerTrue.getTradeByItemIdManagers().add(tradeByItemIdManagerEntityTrue);

        ManageableUserEntity userWithTradeByFiltersManagerTrue = new ManageableUserEntity();
        UbiAccountStatsEntity ubiAccountStatsEntity2 = new UbiAccountStatsEntity();
        ubiAccountStatsEntity2.setUbiProfileId("ubiProfileId2");
        ManageableUserUbiAccountEntryEntity ubiAccountEntryEntity2 = new ManageableUserUbiAccountEntryEntity();
        ubiAccountEntryEntity2.setUser(userWithTradeByFiltersManagerTrue);
        ubiAccountEntryEntity2.setEmail("email");
        ubiAccountEntryEntity2.setUbiAccountStats(ubiAccountStatsEntity2);
        userWithTradeByFiltersManagerTrue.setUbiAccountEntry(ubiAccountEntryEntity2);
        userWithTradeByFiltersManagerTrue.setManagingEnabledFlag(true);
        TradeByFiltersManagerEntity tradeByFiltersManagerEntityTrue = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntityTrue.setName("name");
        tradeByFiltersManagerEntityTrue.setUser(userWithTradeByFiltersManagerTrue);
        userWithTradeByFiltersManagerTrue.getTradeByFiltersManagers().add(tradeByFiltersManagerEntityTrue);

        ManageableUserEntity userWithBothManagersTrue = new ManageableUserEntity();
        UbiAccountStatsEntity ubiAccountStatsEntity3 = new UbiAccountStatsEntity();
        ubiAccountStatsEntity3.setUbiProfileId("ubiProfileId3");
        ManageableUserUbiAccountEntryEntity ubiAccountEntryEntity3 = new ManageableUserUbiAccountEntryEntity();
        ubiAccountEntryEntity3.setUser(userWithBothManagersTrue);
        ubiAccountEntryEntity3.setEmail("email");
        ubiAccountEntryEntity3.setUbiAccountStats(ubiAccountStatsEntity3);
        userWithBothManagersTrue.setUbiAccountEntry(ubiAccountEntryEntity3);
        userWithBothManagersTrue.setManagingEnabledFlag(true);
        TradeByItemIdManagerEntity tradeByItemIdManagerEntityForBothTrue = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntityForBothTrue.setItem(new ItemIdEntity("2"));
        tradeByItemIdManagerEntityForBothTrue.setUser(userWithBothManagersTrue);
        TradeByFiltersManagerEntity tradeByFiltersManagerEntityForBothTrue = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntityForBothTrue.setName("name");
        tradeByFiltersManagerEntityForBothTrue.setUser(userWithBothManagersTrue);
        userWithBothManagersTrue.getTradeByItemIdManagers().add(tradeByItemIdManagerEntityForBothTrue);
        userWithBothManagersTrue.getTradeByFiltersManagers().add(tradeByFiltersManagerEntityForBothTrue);

        userRepository.save(userWithTradeByItemIdManagerTrue);
        userRepository.save(userWithTradeByFiltersManagerTrue);
        userRepository.save(userWithBothManagersTrue);

        // Have managers and ubi account entry, but false flag

        ManageableUserEntity userWithTradeByItemIdManagerFalse = new ManageableUserEntity();
        UbiAccountStatsEntity ubiAccountStatsEntity4 = new UbiAccountStatsEntity();
        ubiAccountStatsEntity4.setUbiProfileId("ubiProfileId4");
        ManageableUserUbiAccountEntryEntity ubiAccountEntryEntity4 = new ManageableUserUbiAccountEntryEntity();
        ubiAccountEntryEntity4.setUser(userWithTradeByItemIdManagerFalse);
        ubiAccountEntryEntity4.setEmail("email");
        ubiAccountEntryEntity4.setUbiAccountStats(ubiAccountStatsEntity4);
        userWithTradeByItemIdManagerFalse.setUbiAccountEntry(ubiAccountEntryEntity4);
        userWithTradeByItemIdManagerFalse.setManagingEnabledFlag(false);
        TradeByItemIdManagerEntity tradeByItemIdManagerEntityFalse = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntityFalse.setItem(new ItemIdEntity("3"));
        tradeByItemIdManagerEntityFalse.setUser(userWithTradeByItemIdManagerFalse);
        userWithTradeByItemIdManagerFalse.getTradeByItemIdManagers().add(tradeByItemIdManagerEntityFalse);

        ManageableUserEntity userWithTradeByFiltersManagerFalse = new ManageableUserEntity();
        UbiAccountStatsEntity ubiAccountStatsEntity5 = new UbiAccountStatsEntity();
        ubiAccountStatsEntity5.setUbiProfileId("ubiProfileId5");
        ManageableUserUbiAccountEntryEntity ubiAccountEntryEntity5 = new ManageableUserUbiAccountEntryEntity();
        ubiAccountEntryEntity5.setUser(userWithTradeByFiltersManagerFalse);
        ubiAccountEntryEntity5.setEmail("email");
        ubiAccountEntryEntity5.setUbiAccountStats(ubiAccountStatsEntity5);
        userWithTradeByFiltersManagerFalse.setUbiAccountEntry(ubiAccountEntryEntity5);
        userWithTradeByFiltersManagerFalse.setManagingEnabledFlag(false);
        TradeByFiltersManagerEntity tradeByFiltersManagerEntityFalse = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntityFalse.setName("name");
        tradeByFiltersManagerEntityFalse.setUser(userWithTradeByFiltersManagerFalse);
        userWithTradeByFiltersManagerFalse.getTradeByFiltersManagers().add(tradeByFiltersManagerEntityFalse);

        ManageableUserEntity userWithBothManagersFalse = new ManageableUserEntity();
        UbiAccountStatsEntity ubiAccountStatsEntity6 = new UbiAccountStatsEntity();
        ubiAccountStatsEntity6.setUbiProfileId("ubiProfileId6");
        ManageableUserUbiAccountEntryEntity ubiAccountEntryEntity6 = new ManageableUserUbiAccountEntryEntity();
        ubiAccountEntryEntity6.setUser(userWithBothManagersFalse);
        ubiAccountEntryEntity6.setEmail("email");
        ubiAccountEntryEntity6.setUbiAccountStats(ubiAccountStatsEntity6);
        userWithBothManagersFalse.setUbiAccountEntry(ubiAccountEntryEntity6);
        userWithBothManagersFalse.setManagingEnabledFlag(false);
        TradeByItemIdManagerEntity tradeByItemIdManagerEntityForBothFalse = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntityForBothFalse.setItem(new ItemIdEntity("4"));
        tradeByItemIdManagerEntityForBothFalse.setUser(userWithBothManagersFalse);
        TradeByFiltersManagerEntity tradeByFiltersManagerEntityForBothFalse = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntityForBothFalse.setName("name");
        tradeByFiltersManagerEntityForBothFalse.setUser(userWithBothManagersFalse);
        userWithBothManagersFalse.getTradeByItemIdManagers().add(tradeByItemIdManagerEntityForBothFalse);
        userWithBothManagersFalse.getTradeByFiltersManagers().add(tradeByFiltersManagerEntityForBothFalse);

        userRepository.save(userWithTradeByItemIdManagerFalse);
        userRepository.save(userWithTradeByFiltersManagerFalse);
        userRepository.save(userWithBothManagersFalse);

        assertEquals(9, userRepository.count());
        assertEquals(3, userRepository.findAllManageableUsers().size());
    }

    @Test
    public void findAllUsersWithTradeManagers_should_return_only_manageable_users_has_no_entry() {

        //Have nothing

        userRepository.save(new ManageableUserEntity());
        userRepository.save(new ManageableUserEntity());
        userRepository.save(new ManageableUserEntity());

        // Have managers and true flag but no ubi account entry

        ManageableUserEntity userWithTradeByItemIdManagerTrue = new ManageableUserEntity();
        userWithTradeByItemIdManagerTrue.setManagingEnabledFlag(true);
        TradeByItemIdManagerEntity tradeByItemIdManagerEntityTrue = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntityTrue.setItem(new ItemIdEntity("1"));
        tradeByItemIdManagerEntityTrue.setUser(userWithTradeByItemIdManagerTrue);
        userWithTradeByItemIdManagerTrue.getTradeByItemIdManagers().add(tradeByItemIdManagerEntityTrue);

        ManageableUserEntity userWithTradeByFiltersManagerTrue = new ManageableUserEntity();
        userWithTradeByFiltersManagerTrue.setManagingEnabledFlag(true);
        TradeByFiltersManagerEntity tradeByFiltersManagerEntityTrue = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntityTrue.setName("name");
        tradeByFiltersManagerEntityTrue.setUser(userWithTradeByFiltersManagerTrue);
        userWithTradeByFiltersManagerTrue.getTradeByFiltersManagers().add(tradeByFiltersManagerEntityTrue);

        ManageableUserEntity userWithBothManagersTrue = new ManageableUserEntity();
        userWithBothManagersTrue.setManagingEnabledFlag(true);
        TradeByItemIdManagerEntity tradeByItemIdManagerEntityForBothTrue = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntityForBothTrue.setItem(new ItemIdEntity("2"));
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

        ManageableUserEntity userWithTradeByItemIdManagerFalse = new ManageableUserEntity();
        userWithTradeByItemIdManagerFalse.setManagingEnabledFlag(false);
        TradeByItemIdManagerEntity tradeByItemIdManagerEntityFalse = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntityFalse.setItem(new ItemIdEntity("3"));
        tradeByItemIdManagerEntityFalse.setUser(userWithTradeByItemIdManagerFalse);
        userWithTradeByItemIdManagerFalse.getTradeByItemIdManagers().add(tradeByItemIdManagerEntityFalse);

        ManageableUserEntity userWithTradeByFiltersManagerFalse = new ManageableUserEntity();
        userWithTradeByFiltersManagerFalse.setManagingEnabledFlag(false);
        TradeByFiltersManagerEntity tradeByFiltersManagerEntityFalse = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntityFalse.setName("name");
        tradeByFiltersManagerEntityFalse.setUser(userWithTradeByFiltersManagerFalse);
        userWithTradeByFiltersManagerFalse.getTradeByFiltersManagers().add(tradeByFiltersManagerEntityFalse);

        ManageableUserEntity userWithBothManagersFalse = new ManageableUserEntity();
        userWithBothManagersFalse.setManagingEnabledFlag(false);
        TradeByItemIdManagerEntity tradeByItemIdManagerEntityForBothFalse = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntityForBothFalse.setItem(new ItemIdEntity("4"));
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

        ManageableUserEntity userWithTradeByItemIdManagerTrueNoStats = new ManageableUserEntity();
        ManageableUserUbiAccountEntryEntity ubiAccountEntryEntity1 = new ManageableUserUbiAccountEntryEntity();
        ubiAccountEntryEntity1.setUser(userWithTradeByItemIdManagerTrueNoStats);
        ubiAccountEntryEntity1.setEmail("email");
        userWithTradeByItemIdManagerTrueNoStats.setUbiAccountEntry(ubiAccountEntryEntity1);
        userWithTradeByItemIdManagerTrueNoStats.setManagingEnabledFlag(true);
        TradeByItemIdManagerEntity tradeByItemIdManagerEntityTrueNoStats = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntityTrueNoStats.setItem(new ItemIdEntity("5"));
        tradeByItemIdManagerEntityTrueNoStats.setUser(userWithTradeByItemIdManagerTrueNoStats);
        userWithTradeByItemIdManagerTrueNoStats.getTradeByItemIdManagers().add(tradeByItemIdManagerEntityTrueNoStats);

        ManageableUserEntity userWithTradeByFiltersManagerTrueNoStats = new ManageableUserEntity();
        ManageableUserUbiAccountEntryEntity ubiAccountEntryEntity2 = new ManageableUserUbiAccountEntryEntity();
        ubiAccountEntryEntity2.setUser(userWithTradeByFiltersManagerTrueNoStats);
        ubiAccountEntryEntity2.setEmail("email");
        userWithTradeByFiltersManagerTrueNoStats.setUbiAccountEntry(ubiAccountEntryEntity2);
        userWithTradeByFiltersManagerTrueNoStats.setManagingEnabledFlag(true);
        TradeByFiltersManagerEntity tradeByFiltersManagerEntityTrueNoStats = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntityTrueNoStats.setName("name");
        tradeByFiltersManagerEntityTrueNoStats.setUser(userWithTradeByFiltersManagerTrueNoStats);
        userWithTradeByFiltersManagerTrueNoStats.getTradeByFiltersManagers().add(tradeByFiltersManagerEntityTrueNoStats);

        ManageableUserEntity userWithBothManagersTrueNoStats = new ManageableUserEntity();
        ManageableUserUbiAccountEntryEntity ubiAccountEntryEntity3 = new ManageableUserUbiAccountEntryEntity();
        ubiAccountEntryEntity3.setUser(userWithBothManagersTrueNoStats);
        ubiAccountEntryEntity3.setEmail("email");
        userWithBothManagersTrueNoStats.setUbiAccountEntry(ubiAccountEntryEntity3);
        userWithBothManagersTrueNoStats.setManagingEnabledFlag(true);
        TradeByItemIdManagerEntity tradeByItemIdManagerEntityForBothTrueNoStats = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntityForBothTrueNoStats.setItem(new ItemIdEntity("6"));
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