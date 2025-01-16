package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.utilspostgresschema.full_entities.user.TelegramUserEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.TradeByFiltersManagerEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Disabled
class CustomTradeByFiltersManagerPostgresRepositoryTest {
    @Autowired
    private CustomTradeByFiltersManagerPostgresRepository customTradeByFiltersManagerPostgresRepository;
    @Autowired
    private CustomTelegramUserPostgresRepository customTelegramUserPostgresRepository;
    @Autowired
    private CustomUserPostgresRepository customUserPostgresRepository;

    @BeforeEach
    void setUp() {
        customTradeByFiltersManagerPostgresRepository.deleteAll();
        customTelegramUserPostgresRepository.deleteAll();
        customUserPostgresRepository.deleteAll();
    }

    @Test
    public void invertEnabledFlagByUserTelegramUserChatIdAndName_should_invert_enabled_flag_for_expected_filter() {
        UserEntity userEntity1 = customUserPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1 = customTelegramUserPostgresRepository.save(telegramUserEntity1);
        userEntity1.setTelegramUser(telegramUserEntity1);

        TradeByFiltersManagerEntity tradeByFiltersManagerEntity11 = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntity11.setName("name1");
        telegramUserEntity1.setUser(userEntity1);
        tradeByFiltersManagerEntity11.setEnabled(true);
        customTradeByFiltersManagerPostgresRepository.save(tradeByFiltersManagerEntity11);

        TradeByFiltersManagerEntity tradeByFiltersManagerEntity12 = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntity12.setName("name2");
        telegramUserEntity1.setUser(userEntity1);
        tradeByFiltersManagerEntity12.setEnabled(false);
        customTradeByFiltersManagerPostgresRepository.save(tradeByFiltersManagerEntity12);

        UserEntity userEntity2 = customUserPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity2 = new TelegramUserEntity();
        telegramUserEntity2.setChatId("chatId2");
        telegramUserEntity2.setUser(userEntity2);
        telegramUserEntity2 = customTelegramUserPostgresRepository.save(telegramUserEntity2);

        TradeByFiltersManagerEntity tradeByFiltersManagerEntity21 = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntity21.setName("name1");
        telegramUserEntity2.setUser(userEntity2);
        tradeByFiltersManagerEntity21.setEnabled(true);
        customTradeByFiltersManagerPostgresRepository.save(tradeByFiltersManagerEntity21);

        customTradeByFiltersManagerPostgresRepository.invertEnabledFlagByUserTelegramUserChatIdAndName("chatId1", "name1");
        customTradeByFiltersManagerPostgresRepository.invertEnabledFlagByUserTelegramUserChatIdAndName("chatId1", "name2");

        List<TradeByFiltersManagerEntity> entities = customTradeByFiltersManagerPostgresRepository.findAll();

        assertEquals(3, entities.size());
        assertTrue(entities.stream().anyMatch(entity -> entity.getName().equals("name1") && entity.getUser().equals(userEntity1) && !entity.getEnabled()));
        assertTrue(entities.stream().anyMatch(entity -> entity.getName().equals("name2") && entity.getUser().equals(userEntity1) && entity.getEnabled()));
        assertTrue(entities.stream().anyMatch(entity -> entity.getName().equals("name1") && entity.getUser().equals(userEntity2) && entity.getEnabled()));
    }

    @Test
    public void deleteByUserTelegramUserChatIdAndName_should_remove_expected_filter() {
        UserEntity userEntity1 = customUserPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1 = customTelegramUserPostgresRepository.save(telegramUserEntity1);

        TradeByFiltersManagerEntity tradeByFiltersManagerEntity11 = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntity11.setName("name1");
        telegramUserEntity1.setUser(userEntity1);
        tradeByFiltersManagerEntity11.setEnabled(true);
        customTradeByFiltersManagerPostgresRepository.save(tradeByFiltersManagerEntity11);

        TradeByFiltersManagerEntity tradeByFiltersManagerEntity12 = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntity12.setName("name2");
        telegramUserEntity1.setUser(userEntity1);
        tradeByFiltersManagerEntity12.setEnabled(false);
        customTradeByFiltersManagerPostgresRepository.save(tradeByFiltersManagerEntity12);

        UserEntity userEntity2 = customUserPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity2 = new TelegramUserEntity();
        telegramUserEntity2.setChatId("chatId2");
        telegramUserEntity2.setUser(userEntity2);
        telegramUserEntity2 = customTelegramUserPostgresRepository.save(telegramUserEntity2);

        TradeByFiltersManagerEntity tradeByFiltersManagerEntity21 = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntity21.setName("name1");
        telegramUserEntity2.setUser(userEntity2);
        tradeByFiltersManagerEntity21.setEnabled(true);
        customTradeByFiltersManagerPostgresRepository.save(tradeByFiltersManagerEntity21);

        customTradeByFiltersManagerPostgresRepository.deleteByUserTelegramUserChatIdAndName("chatId1", "name1");

        List<TradeByFiltersManagerEntity> entities = customTradeByFiltersManagerPostgresRepository.findAll();

        assertEquals(2, entities.size());
        assertTrue(entities.stream().anyMatch(entity -> entity.getName().equals("name2") && entity.getUser().equals(userEntity1)));
        assertTrue(entities.stream().anyMatch(entity -> entity.getName().equals("name1") && entity.getUser().equals(userEntity2)));
    }

    @Test
    public void findByUserTelegramUserChatIdAndName_should_return_expected_filter() {
        UserEntity userEntity1 = customUserPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1 = customTelegramUserPostgresRepository.save(telegramUserEntity1);

        TradeByFiltersManagerEntity tradeByFiltersManagerEntity11 = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntity11.setName("name1");
        telegramUserEntity1.setUser(userEntity1);
        tradeByFiltersManagerEntity11.setEnabled(true);
        customTradeByFiltersManagerPostgresRepository.save(tradeByFiltersManagerEntity11);

        TradeByFiltersManagerEntity tradeByFiltersManagerEntity12 = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntity12.setName("name2");
        telegramUserEntity1.setUser(userEntity1);
        tradeByFiltersManagerEntity12.setEnabled(false);
        customTradeByFiltersManagerPostgresRepository.save(tradeByFiltersManagerEntity12);

        UserEntity userEntity2 = customUserPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity2 = new TelegramUserEntity();
        telegramUserEntity2.setChatId("chatId2");
        telegramUserEntity2.setUser(userEntity2);
        telegramUserEntity2 = customTelegramUserPostgresRepository.save(telegramUserEntity2);

        TradeByFiltersManagerEntity tradeByFiltersManagerEntity21 = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntity21.setName("name1");
        telegramUserEntity2.setUser(userEntity2);
        tradeByFiltersManagerEntity21.setEnabled(true);
        customTradeByFiltersManagerPostgresRepository.save(tradeByFiltersManagerEntity21);

        List<TradeByFiltersManagerEntity> entities = customTradeByFiltersManagerPostgresRepository.findAll();
        assertEquals(3, entities.size());

        TradeByFiltersManagerEntity entity = customTradeByFiltersManagerPostgresRepository.findByUserTelegramUserChatIdAndName("chatId1", "name1").get();

        assertEquals("name1", entity.getName());
        assertEquals(userEntity1, entity.getUser());
        assertTrue(entity.getEnabled());
    }

    @Test
    public void findAllByUserTelegramUserChatId_should_return_expected_filters() {
        UserEntity userEntity1 = customUserPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1 = customTelegramUserPostgresRepository.save(telegramUserEntity1);
        userEntity1.setTelegramUser(telegramUserEntity1);
        userEntity1 = customUserPostgresRepository.save(userEntity1);

        TradeByFiltersManagerEntity tradeByFiltersManagerEntity11 = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntity11.setName("name1");
        telegramUserEntity1.setUser(userEntity1);
        tradeByFiltersManagerEntity11.setEnabled(true);
        customTradeByFiltersManagerPostgresRepository.save(tradeByFiltersManagerEntity11);

        TradeByFiltersManagerEntity tradeByFiltersManagerEntity12 = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntity12.setName("name2");
        telegramUserEntity1.setUser(userEntity1);
        tradeByFiltersManagerEntity12.setEnabled(false);
        customTradeByFiltersManagerPostgresRepository.save(tradeByFiltersManagerEntity12);

        UserEntity userEntity2 = customUserPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity2 = new TelegramUserEntity();
        telegramUserEntity2.setChatId("chatId2");
        telegramUserEntity2.setUser(userEntity2);
        telegramUserEntity2 = customTelegramUserPostgresRepository.save(telegramUserEntity2);

        TradeByFiltersManagerEntity tradeByFiltersManagerEntity21 = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntity21.setName("name1");
        telegramUserEntity2.setUser(userEntity2);
        tradeByFiltersManagerEntity21.setEnabled(true);
        customTradeByFiltersManagerPostgresRepository.save(tradeByFiltersManagerEntity21);

        List<TradeByFiltersManagerEntity> allEntities = customTradeByFiltersManagerPostgresRepository.findAll();
        assertEquals(3, allEntities.size());

        UserEntity userEntity1u = customUserPostgresRepository.findById(userEntity1.getId()).get();
        List<TradeByFiltersManagerEntity> entities = customTradeByFiltersManagerPostgresRepository.findAllByUserTelegramUserChatId("chatId1");

        assertEquals(2, entities.size());
        assertTrue(entities.stream().anyMatch(entity -> entity.getName().equals("name1") && entity.getUser().equals(userEntity1u) && entity.getEnabled()));
        assertTrue(entities.stream().anyMatch(entity -> entity.getName().equals("name2") && entity.getUser().equals(userEntity1u) && !entity.getEnabled()));
    }
}