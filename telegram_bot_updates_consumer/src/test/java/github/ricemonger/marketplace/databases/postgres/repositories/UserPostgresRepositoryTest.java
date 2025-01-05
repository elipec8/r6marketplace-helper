package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.dto_projections.ItemShowSettingsProjection;
import github.ricemonger.marketplace.services.DTOs.ItemShownFieldsSettings;
import github.ricemonger.marketplace.services.DTOs.TradeManagersSettings;
import github.ricemonger.utilspostgresschema.full_entities.user.ItemFilterEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.TelegramUserEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserPostgresRepositoryTest {
    @Autowired
    private UserPostgresRepository userPostgresRepository;
    @Autowired
    private TelegramUserPostgresRepository telegramUserPostgresRepository;
    @Autowired
    private ItemFilterPostgresRepository itemFilterPostgresRepository;
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    public void setUp() {
        userPostgresRepository.deleteAll();
        telegramUserPostgresRepository.deleteAll();
        itemFilterPostgresRepository.deleteAll();
    }

    @Test
    public void existsByTelegramUserChatId_should_return_expected_value() {
        UserEntity userEntity1 = userPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1 = telegramUserPostgresRepository.save(telegramUserEntity1);

        assertTrue(userPostgresRepository.existsByTelegramUserChatId(telegramUserEntity1.getChatId()));
        assertFalse(userPostgresRepository.existsByTelegramUserChatId("chatId2"));
    }

    @Test
    public void getReferenceByTelegramUserChatId_should_return_expected_value() {
        UserEntity userEntity1 = userPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1 = telegramUserPostgresRepository.save(telegramUserEntity1);

        assertEquals(userPostgresRepository.getReferenceByTelegramUserChatId(telegramUserEntity1.getChatId()), userEntity1);
    }

    @Test
    public void updateItemShowFieldsSettingsByTelegramUserChatId_should_update_fields() {
        UserEntity userEntity1 = userPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1 = telegramUserPostgresRepository.save(telegramUserEntity1);

        ItemShownFieldsSettings settings = new ItemShownFieldsSettings();
        settings.setItemShowNameFlag(false);
        settings.setItemShowItemTypeFlag(false);
        settings.setItemShowMaxBuyPrice(false);
        settings.setItemShowBuyOrdersCountFlag(false);
        settings.setItemShowMinSellPriceFlag(false);
        settings.setItemsShowSellOrdersCountFlag(false);
        settings.setItemShowPictureFlag(false);

        userPostgresRepository.updateItemShowFieldsSettingsByTelegramUserChatId(telegramUserEntity1.getChatId(), settings);

        UserEntity userEntity2 = userPostgresRepository.findById(userEntity1.getId()).get();
        assertFalse(userEntity2.getItemShowNameFlag());
        assertFalse(userEntity2.getItemShowItemTypeFlag());
        assertFalse(userEntity2.getItemShowMaxBuyPrice());
        assertFalse(userEntity2.getItemShowBuyOrdersCountFlag());
        assertFalse(userEntity2.getItemShowMinSellPriceFlag());
        assertFalse(userEntity2.getItemsShowSellOrdersCountFlag());
        assertFalse(userEntity2.getItemShowPictureFlag());
    }

    @Test
    public void updateTradeManagersSettingsNewManagersAreActiveFlagByTelegramUserChatId_should_update_field() {
        UserEntity userEntity1 = userPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1 = telegramUserPostgresRepository.save(telegramUserEntity1);

        userPostgresRepository.updateTradeManagersSettingsNewManagersAreActiveFlagByTelegramUserChatId(telegramUserEntity1.getChatId(), true);

        UserEntity userEntity2 = userPostgresRepository.findById(userEntity1.getId()).get();
        assertTrue(userEntity2.getNewManagersAreActiveFlag());
    }

    @Test
    public void updateTradeManagersSettingsManagingEnabledFlagByTelegramUserChatId_should_update_field() {
        UserEntity userEntity1 = userPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1 = telegramUserPostgresRepository.save(telegramUserEntity1);

        userPostgresRepository.updateTradeManagersSettingsManagingEnabledFlagByTelegramUserChatId(telegramUserEntity1.getChatId(), true);

        UserEntity userEntity2 = userPostgresRepository.findById(userEntity1.getId()).get();
        assertTrue(userEntity2.getManagingEnabledFlag());
    }

    @Test
    public void findTradeManagersSettingsByTelegramUserChatId_should_return_expected_value() {
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setNewManagersAreActiveFlag(true);
        userEntity1.setManagingEnabledFlag(false);
        userEntity1 = userPostgresRepository.save(userEntity1);
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1 = telegramUserPostgresRepository.save(telegramUserEntity1);

        assertEquals(userPostgresRepository.findTradeManagersSettingsByTelegramUserChatId(telegramUserEntity1.getChatId()).get(), new TradeManagersSettings(true, false));
    }

    @Test
    public void addItemShowAppliedFilter_should_add_filter() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            UserEntity userEntity1 = new UserEntity();
            entityManager.persist(userEntity1);

            TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
            telegramUserEntity1.setChatId("chatId1");
            telegramUserEntity1.setUser(userEntity1);
            entityManager.persist(telegramUserEntity1);

            userEntity1.setTelegramUser(telegramUserEntity1);
            entityManager.merge(userEntity1);

            ItemFilterEntity itemFilterEntity11 = new ItemFilterEntity();
            itemFilterEntity11.setName("filter11");
            itemFilterEntity11.setUser(userEntity1);
            entityManager.persist(itemFilterEntity11);

            ItemFilterEntity itemFilterEntity12 = new ItemFilterEntity();
            itemFilterEntity12.setName("filter12");
            itemFilterEntity12.setUser(userEntity1);
            entityManager.persist(itemFilterEntity12);

            transaction.commit();

            entityManager.clear();

            transaction.begin();
            userPostgresRepository.addItemShowAppliedFilter(userEntity1.getId(), "filter11");
            entityManager.flush();
            transaction.commit();

            UserEntity userEntity = entityManager.find(UserEntity.class, userEntity1.getId());
            assertEquals(1, userEntity.getItemShowAppliedFilters().size());
            assertEquals(itemFilterEntity11, userEntity.getItemShowAppliedFilters().get(0));
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Test
    @Disabled
    public void deleteItemShowAppliedFilter_should_delete_filter() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            UserEntity userEntity1 = new UserEntity();
            entityManager.persist(userEntity1);

            TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
            telegramUserEntity1.setChatId("chatId1");
            telegramUserEntity1.setUser(userEntity1);
            entityManager.persist(telegramUserEntity1);

            userEntity1.setTelegramUser(telegramUserEntity1);
            entityManager.merge(userEntity1);

            ItemFilterEntity itemFilterEntity11 = new ItemFilterEntity();
            itemFilterEntity11.setName("filter11");
            itemFilterEntity11.setUser(userEntity1);
            entityManager.persist(itemFilterEntity11);

            ItemFilterEntity itemFilterEntity12 = new ItemFilterEntity();
            itemFilterEntity12.setName("filter12");
            itemFilterEntity12.setUser(userEntity1);
            entityManager.persist(itemFilterEntity12);

            userEntity1.getItemShowAppliedFilters().add(itemFilterEntity11);
            userEntity1.getItemShowAppliedFilters().add(itemFilterEntity12);
            entityManager.merge(userEntity1);

            transaction.commit();

            transaction.begin();
            userPostgresRepository.deleteItemShowAppliedFilter(userEntity1.getId(), "filter11");
            entityManager.flush();
            transaction.commit();

            //entityManager.clear();

            UserEntity userEntity = entityManager.find(UserEntity.class, userEntity1.getId());
            assertEquals(1, userEntity.getItemShowAppliedFilters().size());
            assertEquals(itemFilterEntity12, userEntity.getItemShowAppliedFilters().get(0));
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Test
    public void findUserIdByTelegramUserChatId_should_return_expected_value() {
        UserEntity userEntity1 = userPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1 = telegramUserPostgresRepository.save(telegramUserEntity1);

        UserEntity userEntity2 = userPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity2 = new TelegramUserEntity();
        telegramUserEntity2.setChatId("chatId2");
        telegramUserEntity2.setUser(userEntity2);
        telegramUserEntity2 = telegramUserPostgresRepository.save(telegramUserEntity2);

        assertEquals(userEntity1.getId(), userPostgresRepository.findUserIdByTelegramUserChatId(telegramUserEntity1.getChatId()).get());
    }

    @Test
    @Transactional
    public void findAllUserItemShowAppliedFiltersNamesByTelegramUserChatId_should_return_expected_value() {
        UserEntity userEntity1 = userPostgresRepository.saveAndFlush(new UserEntity());
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserPostgresRepository.saveAndFlush(telegramUserEntity1);
        userEntity1.setTelegramUser(telegramUserEntity1);
        userEntity1 = userPostgresRepository.saveAndFlush(userEntity1);

        ItemFilterEntity itemFilterEntity11 = new ItemFilterEntity();
        itemFilterEntity11.setName("filter11");
        itemFilterEntity11.setUser(userEntity1);
        itemFilterEntity11 = itemFilterPostgresRepository.saveAndFlush(itemFilterEntity11);
        ItemFilterEntity itemFilterEntity12 = new ItemFilterEntity();
        itemFilterEntity12.setName("filter12");
        itemFilterEntity12.setUser(userEntity1);
        itemFilterEntity12 = itemFilterPostgresRepository.saveAndFlush(itemFilterEntity12);
        userEntity1.getItemShowAppliedFilters().add(itemFilterEntity11);
        userEntity1.getItemShowAppliedFilters().add(itemFilterEntity12);
        userEntity1 = userPostgresRepository.saveAndFlush(userEntity1);

        UserEntity userEntity2 = userPostgresRepository.saveAndFlush(new UserEntity());
        TelegramUserEntity telegramUserEntity2 = new TelegramUserEntity();
        telegramUserEntity2.setChatId("chatId2");
        telegramUserEntity2.setUser(userEntity2);
        telegramUserPostgresRepository.saveAndFlush(telegramUserEntity2);
        userEntity2.setTelegramUser(telegramUserEntity2);
        userEntity2 = userPostgresRepository.saveAndFlush(userEntity2);

        ItemFilterEntity itemFilterEntity21 = new ItemFilterEntity();
        itemFilterEntity21.setName("filter21");
        itemFilterEntity21.setUser(userEntity2);
        itemFilterEntity21 = itemFilterPostgresRepository.saveAndFlush(itemFilterEntity21);
        userEntity2.getItemShowAppliedFilters().add(itemFilterEntity21);
        userEntity2 = userPostgresRepository.saveAndFlush(userEntity2);

        List<String> names1 = userPostgresRepository.findAllUserItemShowAppliedFiltersNamesByTelegramUserChatId(userEntity1.getTelegramUser().getChatId());
        assertEquals(2, names1.size());
        assertTrue(names1.contains("filter11"));
        assertTrue(names1.contains("filter12"));

        List<String> names2 = userPostgresRepository.findAllUserItemShowAppliedFiltersNamesByTelegramUserChatId(userEntity2.getTelegramUser().getChatId());
        assertEquals(1, names2.size());
        assertTrue(names2.contains("filter21"));
    }

    @Test
    @Transactional
    public void findAllUserItemShowAppliedFiltersByTelegramUserChatId_should_return_expected_value() {
        UserEntity userEntity1 = userPostgresRepository.saveAndFlush(new UserEntity());
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserPostgresRepository.saveAndFlush(telegramUserEntity1);
        userEntity1.setTelegramUser(telegramUserEntity1);
        userEntity1 = userPostgresRepository.saveAndFlush(userEntity1);

        ItemFilterEntity itemFilterEntity11 = new ItemFilterEntity();
        itemFilterEntity11.setName("filter11");
        itemFilterEntity11.setUser(userEntity1);
        itemFilterEntity11 = itemFilterPostgresRepository.saveAndFlush(itemFilterEntity11);
        ItemFilterEntity itemFilterEntity12 = new ItemFilterEntity();
        itemFilterEntity12.setName("filter12");
        itemFilterEntity12.setUser(userEntity1);
        itemFilterEntity12 = itemFilterPostgresRepository.saveAndFlush(itemFilterEntity12);
        userEntity1.getItemShowAppliedFilters().add(itemFilterEntity11);
        userEntity1.getItemShowAppliedFilters().add(itemFilterEntity12);
        userEntity1 = userPostgresRepository.saveAndFlush(userEntity1);

        UserEntity userEntity2 = userPostgresRepository.saveAndFlush(new UserEntity());
        TelegramUserEntity telegramUserEntity2 = new TelegramUserEntity();
        telegramUserEntity2.setChatId("chatId2");
        telegramUserEntity2.setUser(userEntity2);
        telegramUserPostgresRepository.saveAndFlush(telegramUserEntity2);
        userEntity2.setTelegramUser(telegramUserEntity2);
        userEntity2 = userPostgresRepository.saveAndFlush(userEntity2);

        ItemFilterEntity itemFilterEntity21 = new ItemFilterEntity();
        itemFilterEntity21.setName("filter21");
        itemFilterEntity21.setUser(userEntity2);
        itemFilterEntity21 = itemFilterPostgresRepository.saveAndFlush(itemFilterEntity21);
        userEntity2.getItemShowAppliedFilters().add(itemFilterEntity21);
        userEntity2 = userPostgresRepository.saveAndFlush(userEntity2);

        List<ItemFilterEntity> filters1 = userPostgresRepository.findAllUserItemShowAppliedFiltersByTelegramUserChatId(userEntity1.getTelegramUser().getChatId());
        assertEquals(2, filters1.size());
        assertTrue(filters1.contains(itemFilterEntity11));
        assertTrue(filters1.contains(itemFilterEntity12));

        List<ItemFilterEntity> filters2 = userPostgresRepository.findAllUserItemShowAppliedFiltersByTelegramUserChatId(userEntity2.getTelegramUser().getChatId());
        assertEquals(1, filters2.size());
        assertTrue(filters2.contains(itemFilterEntity21));
    }

    @Test
    public void findItemShowSettingsByTelegramUserChatId_should_return_expected_value() {
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setItemShowNameFlag(false);
        userEntity1.setItemShowItemTypeFlag(false);
        userEntity1.setItemShowMaxBuyPrice(false);
        userEntity1.setItemShowBuyOrdersCountFlag(false);
        userEntity1.setItemShowMinSellPriceFlag(false);
        userEntity1.setItemsShowSellOrdersCountFlag(false);
        userEntity1.setItemShowPictureFlag(false);
        userEntity1 = userPostgresRepository.save(userEntity1);
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1.setItemShowFewInMessageFlag(true);
        telegramUserEntity1.setItemShowMessagesLimit(25);
        telegramUserEntity1 = telegramUserPostgresRepository.save(telegramUserEntity1);

        ItemShowSettingsProjection settings = userPostgresRepository.findItemShowSettingsByTelegramUserChatId(telegramUserEntity1.getChatId()).get();
        assertEquals(new ItemShowSettingsProjection(25, true, false, false, false, false, false, false, false), settings);
    }
}