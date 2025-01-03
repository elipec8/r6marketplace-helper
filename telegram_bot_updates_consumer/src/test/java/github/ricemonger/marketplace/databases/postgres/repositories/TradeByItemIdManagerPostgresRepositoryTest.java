package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.utilspostgresschema.full_entities.item.ItemEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.TelegramUserEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.TradeByItemIdManagerEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.UserEntity;
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
@Disabled
class TradeByItemIdManagerPostgresRepositoryTest {
    @Autowired
    private TradeByItemIdManagerPostgresRepository tradeByItemIdManagerPostgresRepository;
    @Autowired
    private TelegramUserPostgresRepository telegramUserPostgresRepository;
    @Autowired
    private UserPostgresRepository userPostgresRepository;
    @Autowired
    private ItemPostgresRepository itemPostgresRepository;

    @BeforeEach
    void setUp() {
        tradeByItemIdManagerPostgresRepository.deleteAll();
        telegramUserPostgresRepository.deleteAll();
        userPostgresRepository.deleteAll();
        itemPostgresRepository.deleteAll();
    }

    @Test
    public void invertEnabledFlagByUserTelegramUserChatIdAndItemItemId_should_invert_enabled_flag_for_expected_filter() {
        ItemEntity itemEntity1 = new ItemEntity();
        itemEntity1.setItemId("itemId1");
        itemEntity1 = itemPostgresRepository.save(itemEntity1);
        
        ItemEntity itemEntity2 = new ItemEntity();
        itemEntity2.setItemId("itemId2");
        itemEntity2 = itemPostgresRepository.save(itemEntity2);
        
        UserEntity userEntity1 = userPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1 = telegramUserPostgresRepository.save(telegramUserEntity1);

        TradeByItemIdManagerEntity tradeByItemIdManagerEntity11 = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntity11.setItem(itemEntity1);
        telegramUserEntity1.setUser(userEntity1);
        tradeByItemIdManagerEntity11.setEnabled(true);
        tradeByItemIdManagerPostgresRepository.save(tradeByItemIdManagerEntity11);

        TradeByItemIdManagerEntity tradeByItemIdManagerEntity12 = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntity12.setItem(itemEntity2);
        telegramUserEntity1.setUser(userEntity1);
        tradeByItemIdManagerEntity12.setEnabled(false);
        tradeByItemIdManagerPostgresRepository.save(tradeByItemIdManagerEntity12);

        UserEntity userEntity2 = userPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity2 = new TelegramUserEntity();
        telegramUserEntity2.setChatId("chatId2");
        telegramUserEntity2.setUser(userEntity2);
        telegramUserEntity2 = telegramUserPostgresRepository.save(telegramUserEntity2);

        TradeByItemIdManagerEntity tradeByItemIdManagerEntity21 = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntity21.setItem(itemEntity1);
        telegramUserEntity2.setUser(userEntity2);
        tradeByItemIdManagerEntity21.setEnabled(true);
        tradeByItemIdManagerPostgresRepository.save(tradeByItemIdManagerEntity21);

        tradeByItemIdManagerPostgresRepository.invertEnabledFlagByUserTelegramUserChatIdAndItemItemId("chatId1", "itemId1");
        tradeByItemIdManagerPostgresRepository.invertEnabledFlagByUserTelegramUserChatIdAndItemItemId("chatId1", "itemId2");

        List<TradeByItemIdManagerEntity> entities = tradeByItemIdManagerPostgresRepository.findAll();

        assertEquals(3, entities.size());
        assertTrue(entities.stream().anyMatch(entity -> entity.getItemId_().equals("itemId1") && entity.getUser().equals(userEntity1) && !entity.getEnabled()));
        assertTrue(entities.stream().anyMatch(entity -> entity.getItemId_().equals("itemId2") && entity.getUser().equals(userEntity1) && entity.getEnabled()));
        assertTrue(entities.stream().anyMatch(entity -> entity.getItemId_().equals("itemId1") && entity.getUser().equals(userEntity2) && entity.getEnabled()));
    }

    @Test
    public void deleteByUserTelegramUserChatIdAndItemItemId_should_remove_expected_filter() {
        ItemEntity itemEntity1 = new ItemEntity();
        itemEntity1.setItemId("itemId1");
        itemEntity1 = itemPostgresRepository.save(itemEntity1);

        ItemEntity itemEntity2 = new ItemEntity();
        itemEntity2.setItemId("itemId2");
        itemEntity2 = itemPostgresRepository.save(itemEntity2);

        UserEntity userEntity1 = userPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1 = telegramUserPostgresRepository.save(telegramUserEntity1);

        TradeByItemIdManagerEntity tradeByItemIdManagerEntity11 = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntity11.setItem(itemEntity1);
        telegramUserEntity1.setUser(userEntity1);
        tradeByItemIdManagerEntity11.setEnabled(true);
        tradeByItemIdManagerPostgresRepository.save(tradeByItemIdManagerEntity11);

        TradeByItemIdManagerEntity tradeByItemIdManagerEntity12 = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntity12.setItem(itemEntity2);
        telegramUserEntity1.setUser(userEntity1);
        tradeByItemIdManagerEntity12.setEnabled(false);
        tradeByItemIdManagerPostgresRepository.save(tradeByItemIdManagerEntity12);

        UserEntity userEntity2 = userPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity2 = new TelegramUserEntity();
        telegramUserEntity2.setChatId("chatId2");
        telegramUserEntity2.setUser(userEntity2);
        telegramUserEntity2 = telegramUserPostgresRepository.save(telegramUserEntity2);

        TradeByItemIdManagerEntity tradeByItemIdManagerEntity21 = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntity21.setItem(itemEntity1);
        telegramUserEntity2.setUser(userEntity2);
        tradeByItemIdManagerEntity21.setEnabled(true);
        tradeByItemIdManagerPostgresRepository.save(tradeByItemIdManagerEntity21);

        tradeByItemIdManagerPostgresRepository.deleteByUserTelegramUserChatIdAndItemItemId("chatId1", "itemId1");

        List<TradeByItemIdManagerEntity> entities = tradeByItemIdManagerPostgresRepository.findAll();

        assertEquals(2, entities.size());
        assertTrue(entities.stream().anyMatch(entity -> entity.getItemId_().equals("itemId2") && entity.getUser().equals(userEntity1)));
        assertTrue(entities.stream().anyMatch(entity -> entity.getItemId_().equals("itemId1") && entity.getUser().equals(userEntity2)));
    }

    @Test
    public void findByUserTelegramUserChatIdAndItemItemId_should_return_expected_filter() {
        ItemEntity itemEntity1 = new ItemEntity();
        itemEntity1.setItemId("itemId1");
        itemEntity1 = itemPostgresRepository.save(itemEntity1);

        ItemEntity itemEntity2 = new ItemEntity();
        itemEntity2.setItemId("itemId2");
        itemEntity2 = itemPostgresRepository.save(itemEntity2);

        UserEntity userEntity1 = userPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1 = telegramUserPostgresRepository.save(telegramUserEntity1);

        TradeByItemIdManagerEntity tradeByItemIdManagerEntity11 = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntity11.setItem(itemEntity1);
        telegramUserEntity1.setUser(userEntity1);
        tradeByItemIdManagerEntity11.setEnabled(true);
        tradeByItemIdManagerPostgresRepository.save(tradeByItemIdManagerEntity11);

        TradeByItemIdManagerEntity tradeByItemIdManagerEntity12 = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntity12.setItem(itemEntity2);
        telegramUserEntity1.setUser(userEntity1);
        tradeByItemIdManagerEntity12.setEnabled(false);
        tradeByItemIdManagerPostgresRepository.save(tradeByItemIdManagerEntity12);

        UserEntity userEntity2 = userPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity2 = new TelegramUserEntity();
        telegramUserEntity2.setChatId("chatId2");
        telegramUserEntity2.setUser(userEntity2);
        telegramUserEntity2 = telegramUserPostgresRepository.save(telegramUserEntity2);

        TradeByItemIdManagerEntity tradeByItemIdManagerEntity21 = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntity21.setItem(itemEntity1);
        telegramUserEntity2.setUser(userEntity2);
        tradeByItemIdManagerEntity21.setEnabled(true);
        tradeByItemIdManagerPostgresRepository.save(tradeByItemIdManagerEntity21);

        List<TradeByItemIdManagerEntity> entities = tradeByItemIdManagerPostgresRepository.findAll();
        assertEquals(3, entities.size());

        TradeByItemIdManagerEntity entity = tradeByItemIdManagerPostgresRepository.findByUserTelegramUserChatIdAndItemItemId("chatId1", "itemId1").get();

        assertEquals("itemId1", entity.getItemId_());
        assertEquals(userEntity1, entity.getUser());
        assertTrue(entity.getEnabled());
    }

    @Test
    public void findAllByUserTelegramUserChatId_should_return_expected_filters() {
        ItemEntity itemEntity1 = new ItemEntity();
        itemEntity1.setItemId("itemId1");
        itemEntity1 = itemPostgresRepository.save(itemEntity1);

        ItemEntity itemEntity2 = new ItemEntity();
        itemEntity2.setItemId("itemId2");
        itemEntity2 = itemPostgresRepository.save(itemEntity2);

        UserEntity userEntity1 = userPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1 = telegramUserPostgresRepository.save(telegramUserEntity1);

        TradeByItemIdManagerEntity tradeByItemIdManagerEntity11 = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntity11.setItem(itemEntity1);
        telegramUserEntity1.setUser(userEntity1);
        tradeByItemIdManagerEntity11.setEnabled(true);
        tradeByItemIdManagerPostgresRepository.save(tradeByItemIdManagerEntity11);

        TradeByItemIdManagerEntity tradeByItemIdManagerEntity12 = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntity12.setItem(itemEntity2);
        telegramUserEntity1.setUser(userEntity1);
        tradeByItemIdManagerEntity12.setEnabled(false);
        tradeByItemIdManagerPostgresRepository.save(tradeByItemIdManagerEntity12);

        UserEntity userEntity2 = userPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity2 = new TelegramUserEntity();
        telegramUserEntity2.setChatId("chatId2");
        telegramUserEntity2.setUser(userEntity2);
        telegramUserEntity2 = telegramUserPostgresRepository.save(telegramUserEntity2);

        TradeByItemIdManagerEntity tradeByItemIdManagerEntity21 = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntity21.setItem(itemEntity1);
        telegramUserEntity2.setUser(userEntity2);
        tradeByItemIdManagerEntity21.setEnabled(true);
        tradeByItemIdManagerPostgresRepository.save(tradeByItemIdManagerEntity21);

        List<TradeByItemIdManagerEntity> allEntities = tradeByItemIdManagerPostgresRepository.findAll();
        assertEquals(3, allEntities.size());

        List<TradeByItemIdManagerEntity> entities = tradeByItemIdManagerPostgresRepository.findAllByUserTelegramUserChatId("chatId1");

        assertEquals(2, entities.size());
        assertTrue(entities.stream().anyMatch(entity -> entity.getItemId_().equals("itemId1") && entity.getUser().equals(userEntity1) && entity.getEnabled()));
        assertTrue(entities.stream().anyMatch(entity -> entity.getItemId_().equals("itemId2") && entity.getUser().equals(userEntity1) && !entity.getEnabled()));
    }
}