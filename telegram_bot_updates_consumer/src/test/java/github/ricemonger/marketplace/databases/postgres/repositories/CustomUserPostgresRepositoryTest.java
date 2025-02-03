package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.dto_projections.ItemShowSettingsProjection;
import github.ricemonger.marketplace.databases.postgres.dto_projections.ItemShownFieldsSettingsProjection;
import github.ricemonger.marketplace.databases.postgres.dto_projections.NotificationsSettingsProjection;
import github.ricemonger.marketplace.databases.postgres.dto_projections.TradeManagersSettingsProjection;
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
class CustomUserPostgresRepositoryTest {
    @Autowired
    private CustomUserPostgresRepository customUserPostgresRepository;
    @Autowired
    private CustomTelegramUserPostgresRepository customTelegramUserPostgresRepository;
    @Autowired
    private CustomItemFilterPostgresRepository customItemFilterPostgresRepository;
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    public void setUp() {
        customUserPostgresRepository.deleteAll();
        customTelegramUserPostgresRepository.deleteAll();
        customItemFilterPostgresRepository.deleteAll();
    }

    @Test
    public void existsByTelegramUserChatId_should_return_expected_value() {
        UserEntity userEntity1 = customUserPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1 = customTelegramUserPostgresRepository.save(telegramUserEntity1);

        assertTrue(customUserPostgresRepository.existsByTelegramUserChatId(telegramUserEntity1.getChatId()));
        assertFalse(customUserPostgresRepository.existsByTelegramUserChatId("chatId2"));
    }

    @Test
    public void getReferenceByTelegramUserChatId_should_return_expected_value() {
        UserEntity userEntity1 = customUserPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1 = customTelegramUserPostgresRepository.save(telegramUserEntity1);

        assertEquals(customUserPostgresRepository.getReferenceByTelegramUserChatId(telegramUserEntity1.getChatId()), userEntity1);
    }

    @Test
    public void updateItemShowFieldsSettingsByTelegramUserChatId_should_update_fields() {
        UserEntity userEntity1 = customUserPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1 = customTelegramUserPostgresRepository.save(telegramUserEntity1);

        ItemShownFieldsSettingsProjection settings = new ItemShownFieldsSettingsProjection();
        settings.setItemShowNameFlag(false);
        settings.setItemShowItemTypeFlag(false);
        settings.setItemShowMaxBuyPrice(false);
        settings.setItemShowBuyOrdersCountFlag(false);
        settings.setItemShowMinSellPriceFlag(false);
        settings.setItemsShowSellOrdersCountFlag(false);
        settings.setItemShowPictureFlag(false);

        customUserPostgresRepository.updateItemShowFieldsSettingsByTelegramUserChatId(telegramUserEntity1.getChatId(), settings);

        UserEntity userEntity2 = customUserPostgresRepository.findById(userEntity1.getId()).get();
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
        UserEntity userEntity1 = customUserPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1 = customTelegramUserPostgresRepository.save(telegramUserEntity1);

        customUserPostgresRepository.updateTradeManagersSettingsNewManagersAreActiveFlagByTelegramUserChatId(telegramUserEntity1.getChatId(), true);

        UserEntity userEntity2 = customUserPostgresRepository.findById(userEntity1.getId()).get();
        assertTrue(userEntity2.getNewManagersAreActiveFlag());
    }

    @Test
    public void updateTradeManagersSettingsManagingEnabledFlagByTelegramUserChatId_should_update_field() {
        UserEntity userEntity1 = customUserPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1 = customTelegramUserPostgresRepository.save(telegramUserEntity1);

        customUserPostgresRepository.updateTradeManagersSettingsManagingEnabledFlagByTelegramUserChatId(telegramUserEntity1.getChatId(), true);

        UserEntity userEntity2 = customUserPostgresRepository.findById(userEntity1.getId()).get();
        assertTrue(userEntity2.getManagingEnabledFlag());
    }

    @Test
    public void updateTradeManagersSellSettingsManagingEnabledFlagByTelegramUserChatId_should_update_field() {
        UserEntity userEntity1 = customUserPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1 = customTelegramUserPostgresRepository.save(telegramUserEntity1);

        customUserPostgresRepository.updateTradeManagersSellSettingsManagingEnabledFlagByTelegramUserChatId(telegramUserEntity1.getChatId(), true);

        UserEntity userEntity2 = customUserPostgresRepository.findById(userEntity1.getId()).get();
        assertTrue(userEntity2.getSellTradesManagingEnabledFlag());
    }

    @Test
    public void updateTradeManagersBuySettingsManagingEnabledFlagByTelegramUserChatId_should_update_field() {
        UserEntity userEntity1 = customUserPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1 = customTelegramUserPostgresRepository.save(telegramUserEntity1);

        customUserPostgresRepository.updateTradeManagersBuySettingsManagingEnabledFlagByTelegramUserChatId(telegramUserEntity1.getChatId(), false);

        UserEntity userEntity2 = customUserPostgresRepository.findById(userEntity1.getId()).get();
        assertFalse(userEntity2.getBuyTradesManagingEnabledFlag());
    }

    @Test
    public void updateTradeManagersSellSettingsTradePriorityExpressionByTelegramUserChatId_should_update_field() {
        UserEntity userEntity1 = customUserPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1 = customTelegramUserPostgresRepository.save(telegramUserEntity1);

        customUserPostgresRepository.updateTradeManagersSellSettingsTradePriorityExpressionByTelegramUserChatId(telegramUserEntity1.getChatId(), "tradePriorityExpression");

        UserEntity userEntity2 = customUserPostgresRepository.findById(userEntity1.getId()).get();
        assertEquals("tradePriorityExpression", userEntity2.getSellTradePriorityExpression());
    }

    @Test
    public void updateTradeManagersBuySettingsTradePriorityExpressionByTelegramUserChatId_should_update_field() {
        UserEntity userEntity1 = customUserPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1 = customTelegramUserPostgresRepository.save(telegramUserEntity1);

        customUserPostgresRepository.updateTradeManagersBuySettingsTradePriorityExpressionByTelegramUserChatId(telegramUserEntity1.getChatId(), "tradePriorityExpression");

        UserEntity userEntity2 = customUserPostgresRepository.findById(userEntity1.getId()).get();
        assertEquals("tradePriorityExpression", userEntity2.getBuyTradePriorityExpression());
    }

    @Test
    public void findTradeManagersSettingsByTelegramUserChatId_should_return_expected_value() {
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setNewManagersAreActiveFlag(true);
        userEntity1.setManagingEnabledFlag(false);
        userEntity1.setSellTradesManagingEnabledFlag(false);
        userEntity1.setSellTradePriorityExpression("sellTradePriorityExpression");
        userEntity1.setBuyTradesManagingEnabledFlag(true);
        userEntity1.setBuyTradePriorityExpression("buyTradePriorityExpression");
        userEntity1 = customUserPostgresRepository.save(userEntity1);
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1 = customTelegramUserPostgresRepository.save(telegramUserEntity1);

        assertEquals(customUserPostgresRepository.findTradeManagersSettingsByTelegramUserChatId(telegramUserEntity1.getChatId()).get(),
                new TradeManagersSettingsProjection(
                        true,
                        false,
                        false,
                        "sellTradePriorityExpression",
                        true,
                        "buyTradePriorityExpression"));
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
            customUserPostgresRepository.addItemShowAppliedFilter(userEntity1.getId(), "filter11");
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
            customUserPostgresRepository.deleteItemShowAppliedFilter(userEntity1.getId(), "filter11");
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
        UserEntity userEntity1 = customUserPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1 = customTelegramUserPostgresRepository.save(telegramUserEntity1);

        UserEntity userEntity2 = customUserPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity2 = new TelegramUserEntity();
        telegramUserEntity2.setChatId("chatId2");
        telegramUserEntity2.setUser(userEntity2);
        telegramUserEntity2 = customTelegramUserPostgresRepository.save(telegramUserEntity2);

        assertEquals(userEntity1.getId(), customUserPostgresRepository.findUserIdByTelegramUserChatId(telegramUserEntity1.getChatId()).get());
    }

    @Test
    @Transactional
    public void findAllUserItemShowAppliedFiltersNamesByTelegramUserChatId_should_return_expected_value() {
        UserEntity userEntity1 = customUserPostgresRepository.saveAndFlush(new UserEntity());
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        customTelegramUserPostgresRepository.saveAndFlush(telegramUserEntity1);
        userEntity1.setTelegramUser(telegramUserEntity1);
        userEntity1 = customUserPostgresRepository.saveAndFlush(userEntity1);

        ItemFilterEntity itemFilterEntity11 = new ItemFilterEntity();
        itemFilterEntity11.setName("filter11");
        itemFilterEntity11.setUser(userEntity1);
        itemFilterEntity11 = customItemFilterPostgresRepository.saveAndFlush(itemFilterEntity11);
        ItemFilterEntity itemFilterEntity12 = new ItemFilterEntity();
        itemFilterEntity12.setName("filter12");
        itemFilterEntity12.setUser(userEntity1);
        itemFilterEntity12 = customItemFilterPostgresRepository.saveAndFlush(itemFilterEntity12);
        userEntity1.getItemShowAppliedFilters().add(itemFilterEntity11);
        userEntity1.getItemShowAppliedFilters().add(itemFilterEntity12);
        userEntity1 = customUserPostgresRepository.saveAndFlush(userEntity1);

        UserEntity userEntity2 = customUserPostgresRepository.saveAndFlush(new UserEntity());
        TelegramUserEntity telegramUserEntity2 = new TelegramUserEntity();
        telegramUserEntity2.setChatId("chatId2");
        telegramUserEntity2.setUser(userEntity2);
        customTelegramUserPostgresRepository.saveAndFlush(telegramUserEntity2);
        userEntity2.setTelegramUser(telegramUserEntity2);
        userEntity2 = customUserPostgresRepository.saveAndFlush(userEntity2);

        ItemFilterEntity itemFilterEntity21 = new ItemFilterEntity();
        itemFilterEntity21.setName("filter21");
        itemFilterEntity21.setUser(userEntity2);
        itemFilterEntity21 = customItemFilterPostgresRepository.saveAndFlush(itemFilterEntity21);
        userEntity2.getItemShowAppliedFilters().add(itemFilterEntity21);
        userEntity2 = customUserPostgresRepository.saveAndFlush(userEntity2);

        List<String> names1 = customUserPostgresRepository.findAllUserItemShowAppliedFiltersNamesByTelegramUserChatId(userEntity1.getTelegramUser().getChatId());
        assertEquals(2, names1.size());
        assertTrue(names1.contains("filter11"));
        assertTrue(names1.contains("filter12"));

        List<String> names2 = customUserPostgresRepository.findAllUserItemShowAppliedFiltersNamesByTelegramUserChatId(userEntity2.getTelegramUser().getChatId());
        assertEquals(1, names2.size());
        assertTrue(names2.contains("filter21"));
    }

    @Test
    @Transactional
    public void findAllUserItemShowAppliedFiltersByTelegramUserChatId_should_return_expected_value() {
        UserEntity userEntity1 = customUserPostgresRepository.saveAndFlush(new UserEntity());
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        customTelegramUserPostgresRepository.saveAndFlush(telegramUserEntity1);
        userEntity1.setTelegramUser(telegramUserEntity1);
        userEntity1 = customUserPostgresRepository.saveAndFlush(userEntity1);

        ItemFilterEntity itemFilterEntity11 = new ItemFilterEntity();
        itemFilterEntity11.setName("filter11");
        itemFilterEntity11.setUser(userEntity1);
        itemFilterEntity11 = customItemFilterPostgresRepository.saveAndFlush(itemFilterEntity11);
        ItemFilterEntity itemFilterEntity12 = new ItemFilterEntity();
        itemFilterEntity12.setName("filter12");
        itemFilterEntity12.setUser(userEntity1);
        itemFilterEntity12 = customItemFilterPostgresRepository.saveAndFlush(itemFilterEntity12);
        userEntity1.getItemShowAppliedFilters().add(itemFilterEntity11);
        userEntity1.getItemShowAppliedFilters().add(itemFilterEntity12);
        userEntity1 = customUserPostgresRepository.saveAndFlush(userEntity1);

        UserEntity userEntity2 = customUserPostgresRepository.saveAndFlush(new UserEntity());
        TelegramUserEntity telegramUserEntity2 = new TelegramUserEntity();
        telegramUserEntity2.setChatId("chatId2");
        telegramUserEntity2.setUser(userEntity2);
        customTelegramUserPostgresRepository.saveAndFlush(telegramUserEntity2);
        userEntity2.setTelegramUser(telegramUserEntity2);
        userEntity2 = customUserPostgresRepository.saveAndFlush(userEntity2);

        ItemFilterEntity itemFilterEntity21 = new ItemFilterEntity();
        itemFilterEntity21.setName("filter21");
        itemFilterEntity21.setUser(userEntity2);
        itemFilterEntity21 = customItemFilterPostgresRepository.saveAndFlush(itemFilterEntity21);
        userEntity2.getItemShowAppliedFilters().add(itemFilterEntity21);
        userEntity2 = customUserPostgresRepository.saveAndFlush(userEntity2);

        List<ItemFilterEntity> filters1 = customUserPostgresRepository.findAllUserItemShowAppliedFiltersByTelegramUserChatId(userEntity1.getTelegramUser().getChatId());
        assertEquals(2, filters1.size());
        assertTrue(filters1.contains(itemFilterEntity11));
        assertTrue(filters1.contains(itemFilterEntity12));

        List<ItemFilterEntity> filters2 = customUserPostgresRepository.findAllUserItemShowAppliedFiltersByTelegramUserChatId(userEntity2.getTelegramUser().getChatId());
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
        userEntity1 = customUserPostgresRepository.save(userEntity1);
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1.setItemShowFewInMessageFlag(true);
        telegramUserEntity1.setItemShowMessagesLimit(25);
        telegramUserEntity1 = customTelegramUserPostgresRepository.save(telegramUserEntity1);

        ItemShowSettingsProjection settings = customUserPostgresRepository.findItemShowSettingsByTelegramUserChatId(telegramUserEntity1.getChatId()).get();
        assertEquals(new ItemShowSettingsProjection(25, true, false, false, false, false, false, false, false), settings);
    }

    @Test
    @Disabled // H2 UNSUPPORTED
    public void invertPrivateNotificationsFlagByTelegramUserChatId_should_invert_flag() {
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setPrivateNotificationsEnabledFlag(false);
        userEntity1 = customUserPostgresRepository.save(userEntity1);
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1 = customTelegramUserPostgresRepository.save(telegramUserEntity1);

        customUserPostgresRepository.invertPrivateNotificationsFlagByTelegramUserChatId(telegramUserEntity1.getChatId());

        UserEntity userEntity2 = customUserPostgresRepository.findById(userEntity1.getId()).get();
        assertTrue(userEntity2.getPrivateNotificationsEnabledFlag());
    }

    @Test
    @Disabled // H2 UNSUPPORTED
    public void invertPublicNotificationsFlagByTelegramUserChatId_should_invert_flag() {
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setPublicNotificationsEnabledFlag(false);
        userEntity1 = customUserPostgresRepository.save(userEntity1);
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1 = customTelegramUserPostgresRepository.save(telegramUserEntity1);

        customUserPostgresRepository.invertPublicNotificationsFlagByTelegramUserChatId(telegramUserEntity1.getChatId());

        UserEntity userEntity2 = customUserPostgresRepository.findById(userEntity1.getId()).get();
        assertTrue(userEntity2.getPublicNotificationsEnabledFlag());
    }

    @Test
    @Disabled // H2 UNSUPPORTED
    public void invertUbiStatsUpdatedNotificationsFlagByTelegramUserChatId_should_invert_flag() {
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setUbiStatsUpdatedNotificationsEnabledFlag(false);
        userEntity1 = customUserPostgresRepository.save(userEntity1);
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1 = customTelegramUserPostgresRepository.save(telegramUserEntity1);

        customUserPostgresRepository.invertUbiStatsUpdatedNotificationsFlagByTelegramUserChatId(telegramUserEntity1.getChatId());

        UserEntity userEntity2 = customUserPostgresRepository.findById(userEntity1.getId()).get();
        assertTrue(userEntity2.getUbiStatsUpdatedNotificationsEnabledFlag());
    }

    @Test
    @Disabled // H2 UNSUPPORTED
    public void invertTradeManagerNotificationsFlagByTelegramUserChatId_should_invert_flag() {
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setTradeManagerNotificationsEnabledFlag(false);
        userEntity1 = customUserPostgresRepository.save(userEntity1);
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1 = customTelegramUserPostgresRepository.save(telegramUserEntity1);

        customUserPostgresRepository.invertTradeManagerNotificationsFlagByTelegramUserChatId(telegramUserEntity1.getChatId());

        UserEntity userEntity2 = customUserPostgresRepository.findById(userEntity1.getId()).get();
        assertTrue(userEntity2.getTradeManagerNotificationsEnabledFlag());
    }

    @Test
    @Disabled // H2 UNSUPPORTED
    public void invertAuthorizationNotificationsFlagByTelegramUserChatId_should_invert_flag() {
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setAuthorizationNotificationsEnabledFlag(false);
        userEntity1 = customUserPostgresRepository.save(userEntity1);
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1 = customTelegramUserPostgresRepository.save(telegramUserEntity1);

        customUserPostgresRepository.invertAuthorizationNotificationsFlagByTelegramUserChatId(telegramUserEntity1.getChatId());

        UserEntity userEntity2 = customUserPostgresRepository.findById(userEntity1.getId()).get();
        assertTrue(userEntity2.getAuthorizationNotificationsEnabledFlag());
    }

    @Test
    public void findNotificationsSettingsByTelegramUserChatId_should_return_expected_value() {
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setPublicNotificationsEnabledFlag(false);
        userEntity1.setPrivateNotificationsEnabledFlag(true);
        userEntity1.setUbiStatsUpdatedNotificationsEnabledFlag(false);
        userEntity1.setTradeManagerNotificationsEnabledFlag(false);
        userEntity1.setAuthorizationNotificationsEnabledFlag(true);
        userEntity1 = customUserPostgresRepository.save(userEntity1);
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1 = customTelegramUserPostgresRepository.save(telegramUserEntity1);

        NotificationsSettingsProjection settings = customUserPostgresRepository.findNotificationsSettingsByTelegramUserChatId(telegramUserEntity1.getChatId()).get();
        assertEquals(new NotificationsSettingsProjection(false, true, false, false, true), settings);
    }
}