package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.utils.enums.InputState;
import github.ricemonger.utilspostgresschema.full_entities.user.TelegramUserEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.TelegramUserInputEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.UserEntity;
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
class TelegramUserInputPostgresRepositoryTest {
    @Autowired
    private TelegramUserInputPostgresRepository telegramUserInputPostgresRepository;
    @Autowired
    private TelegramUserPostgresRepository telegramUserPostgresRepository;
    @Autowired
    private UserPostgresRepository userPostgresRepository;

    @BeforeEach
    void setUp() {
        telegramUserInputPostgresRepository.deleteAll();
        telegramUserPostgresRepository.deleteAll();
        userPostgresRepository.deleteAll();
    }

    @Test
    public void deleteAllByTelegramUserChatId_should_delete_all_entities_with_given_chat_id() {
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

        TelegramUserInputEntity telegramUserInputEntity11 = new TelegramUserInputEntity();
        telegramUserInputEntity11.setTelegramUser(telegramUserEntity1);
        telegramUserInputEntity11.setInputState(InputState.TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_SELL_PRICE);
        telegramUserInputEntity11.setValue("value11");
        telegramUserInputPostgresRepository.save(telegramUserInputEntity11);

        TelegramUserInputEntity telegramUserInputEntity12 = new TelegramUserInputEntity();
        telegramUserInputEntity12.setTelegramUser(telegramUserEntity1);
        telegramUserInputEntity12.setInputState(InputState.TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_BUY_PRICE);
        telegramUserInputEntity12.setValue("value12");
        telegramUserInputPostgresRepository.save(telegramUserInputEntity12);

        TelegramUserInputEntity telegramUserInputEntity21 = new TelegramUserInputEntity();
        telegramUserInputEntity21.setTelegramUser(telegramUserEntity2);
        telegramUserInputEntity21.setInputState(InputState.TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_SELL_PRICE);
        telegramUserInputEntity21.setValue("value21");
        telegramUserInputPostgresRepository.save(telegramUserInputEntity21);

        assertEquals(3, telegramUserInputPostgresRepository.count());

        telegramUserInputPostgresRepository.deleteAllByChatId("chatId1");

        assertEquals(1, telegramUserInputPostgresRepository.count());
        assertEquals(telegramUserInputEntity21, telegramUserInputPostgresRepository.findAll().get(0));
    }

    @Test
    public void findAllByTelegramUserChatId_should_return_all_inputs_for_tg_user() {
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

        TelegramUserInputEntity telegramUserInputEntity11 = new TelegramUserInputEntity();
        telegramUserInputEntity11.setTelegramUser(telegramUserEntity1);
        telegramUserInputEntity11.setInputState(InputState.TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_SELL_PRICE);
        telegramUserInputEntity11.setValue("value11");
        telegramUserInputPostgresRepository.save(telegramUserInputEntity11);

        TelegramUserInputEntity telegramUserInputEntity12 = new TelegramUserInputEntity();
        telegramUserInputEntity12.setTelegramUser(telegramUserEntity1);
        telegramUserInputEntity12.setInputState(InputState.TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_BUY_PRICE);
        telegramUserInputEntity12.setValue("value12");
        telegramUserInputPostgresRepository.save(telegramUserInputEntity12);

        TelegramUserInputEntity telegramUserInputEntity21 = new TelegramUserInputEntity();
        telegramUserInputEntity21.setTelegramUser(telegramUserEntity2);
        telegramUserInputEntity21.setInputState(InputState.TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_SELL_PRICE);
        telegramUserInputEntity21.setValue("value21");
        telegramUserInputPostgresRepository.save(telegramUserInputEntity21);

        assertEquals(3, telegramUserInputPostgresRepository.count());

        List<TelegramUserInputEntity> result = telegramUserInputPostgresRepository.findAllByTelegramUserChatId("chatId1");

        assertEquals(2, result.size());
        assertTrue(result.contains(telegramUserInputEntity11));
        assertTrue(result.contains(telegramUserInputEntity12));
    }
}