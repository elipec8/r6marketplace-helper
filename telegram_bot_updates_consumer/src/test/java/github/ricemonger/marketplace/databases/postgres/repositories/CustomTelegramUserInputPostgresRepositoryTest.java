package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.dto_projections.TelegramUserInputProjection;
import github.ricemonger.utils.enums.InputState;
import github.ricemonger.utilspostgresschema.full_entities.user.TelegramUserEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.TelegramUserInputEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.UserEntity;
import github.ricemonger.utilspostgresschema.ids.user.TelegramUserInputEntityId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class CustomTelegramUserInputPostgresRepositoryTest {
    @Autowired
    private CustomTelegramUserInputPostgresRepository customTelegramUserInputPostgresRepository;
    @Autowired
    private CustomTelegramUserPostgresRepository customTelegramUserPostgresRepository;
    @Autowired
    private CustomUserPostgresRepository customUserPostgresRepository;

    @BeforeEach
    void setUp() {
        customTelegramUserInputPostgresRepository.deleteAll();
        customTelegramUserPostgresRepository.deleteAll();
        customUserPostgresRepository.deleteAll();
    }

    @Test
    public void deleteAllByTelegramUserChatId_should_delete_all_entities_with_given_chat_id() {
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

        TelegramUserInputEntity telegramUserInputEntity11 = new TelegramUserInputEntity();
        telegramUserInputEntity11.setTelegramUser(telegramUserEntity1);
        telegramUserInputEntity11.setInputState(InputState.TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_SELL_PRICE);
        telegramUserInputEntity11.setValue("value11");
        customTelegramUserInputPostgresRepository.save(telegramUserInputEntity11);

        TelegramUserInputEntity telegramUserInputEntity12 = new TelegramUserInputEntity();
        telegramUserInputEntity12.setTelegramUser(telegramUserEntity1);
        telegramUserInputEntity12.setInputState(InputState.TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_BUY_PRICE);
        telegramUserInputEntity12.setValue("value12");
        customTelegramUserInputPostgresRepository.save(telegramUserInputEntity12);

        TelegramUserInputEntity telegramUserInputEntity21 = new TelegramUserInputEntity();
        telegramUserInputEntity21.setTelegramUser(telegramUserEntity2);
        telegramUserInputEntity21.setInputState(InputState.TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_SELL_PRICE);
        telegramUserInputEntity21.setValue("value21");
        customTelegramUserInputPostgresRepository.save(telegramUserInputEntity21);

        assertEquals(3, customTelegramUserInputPostgresRepository.count());

        customTelegramUserInputPostgresRepository.deleteAllByChatId("chatId1");

        assertEquals(1, customTelegramUserInputPostgresRepository.count());
        assertEquals(telegramUserInputEntity21, customTelegramUserInputPostgresRepository.findAll().get(0));
    }

    @Test
    public void findInputById_should_return_all_input_by_id() {
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

        TelegramUserInputEntity telegramUserInputEntity11 = new TelegramUserInputEntity();
        telegramUserInputEntity11.setTelegramUser(telegramUserEntity1);
        telegramUserInputEntity11.setInputState(InputState.TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_SELL_PRICE);
        telegramUserInputEntity11.setValue("value11");
        customTelegramUserInputPostgresRepository.save(telegramUserInputEntity11);

        TelegramUserInputEntity telegramUserInputEntity12 = new TelegramUserInputEntity();
        telegramUserInputEntity12.setTelegramUser(telegramUserEntity1);
        telegramUserInputEntity12.setInputState(InputState.TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_BUY_PRICE);
        telegramUserInputEntity12.setValue("value12");
        customTelegramUserInputPostgresRepository.save(telegramUserInputEntity12);

        TelegramUserInputEntity telegramUserInputEntity21 = new TelegramUserInputEntity();
        telegramUserInputEntity21.setTelegramUser(telegramUserEntity2);
        telegramUserInputEntity21.setInputState(InputState.TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_SELL_PRICE);
        telegramUserInputEntity21.setValue("value21");
        customTelegramUserInputPostgresRepository.save(telegramUserInputEntity21);

        TelegramUserInputProjection expectedProjection1 = new TelegramUserInputProjection("chatId1", InputState.TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_SELL_PRICE, "value11");

        assertEquals(3, customTelegramUserInputPostgresRepository.count());

        TelegramUserInputProjection result = customTelegramUserInputPostgresRepository.findInputById("chatId1", InputState.TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_SELL_PRICE).get();

        assertEquals(expectedProjection1, result);
    }

    @Test
    public void findAllByTelegramUserChatId_should_return_all_inputs_for_tg_user() {
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

        TelegramUserInputEntity telegramUserInputEntity11 = new TelegramUserInputEntity();
        telegramUserInputEntity11.setTelegramUser(telegramUserEntity1);
        telegramUserInputEntity11.setInputState(InputState.TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_SELL_PRICE);
        telegramUserInputEntity11.setValue("value11");
        customTelegramUserInputPostgresRepository.save(telegramUserInputEntity11);

        TelegramUserInputEntity telegramUserInputEntity12 = new TelegramUserInputEntity();
        telegramUserInputEntity12.setTelegramUser(telegramUserEntity1);
        telegramUserInputEntity12.setInputState(InputState.TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_BUY_PRICE);
        telegramUserInputEntity12.setValue("value12");
        customTelegramUserInputPostgresRepository.save(telegramUserInputEntity12);

        TelegramUserInputEntity telegramUserInputEntity21 = new TelegramUserInputEntity();
        telegramUserInputEntity21.setTelegramUser(telegramUserEntity2);
        telegramUserInputEntity21.setInputState(InputState.TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_SELL_PRICE);
        telegramUserInputEntity21.setValue("value21");
        customTelegramUserInputPostgresRepository.save(telegramUserInputEntity21);

        TelegramUserInputProjection expectedProjection1 = new TelegramUserInputProjection("chatId1", InputState.TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_SELL_PRICE, "value11");
        TelegramUserInputProjection expectedProjection2 = new TelegramUserInputProjection("chatId1", InputState.TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_BUY_PRICE, "value12");

        assertEquals(3, customTelegramUserInputPostgresRepository.count());

        List<TelegramUserInputProjection> result = customTelegramUserInputPostgresRepository.findAllByChatId("chatId1");

        assertEquals(2, result.size());
        assertTrue(result.contains(expectedProjection1));
        assertTrue(result.contains(expectedProjection2));
    }
}