package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TradeManagerByItemIdEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.UserEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.TradeManagerByItemIdPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.UserPostgresRepository;
import github.ricemonger.utils.dtos.TradeManagerByItemId;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.TradeManagerByItemIdDoesntExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class TelegramUserTradeManagerByItemIdPostgresServiceTest {
    private final static String CHAT_ID = "1";
    private final static String ANOTHER_CHAT_ID = "2";

    @Autowired
    private TelegramUserTradeManagerByItemIdPostgresService telegramUserTradeManagerByItemIdService;
    @Autowired
    private TradeManagerByItemIdPostgresRepository tradeManagerByItemIdRepository;
    @Autowired
    private TelegramUserPostgresRepository telegramUserRepository;
    @Autowired
    private UserPostgresRepository userRepository;

    @BeforeEach
    public void setUp() {
        tradeManagerByItemIdRepository.deleteAll();
        telegramUserRepository.deleteAll();
        userRepository.deleteAll();
        createTelegramUser(CHAT_ID);
    }

    private TelegramUserEntity createTelegramUser(String chatId) {
        UserEntity user = userRepository.save(new UserEntity());
        return telegramUserRepository.save(new TelegramUserEntity(chatId, user));
    }

    @Test
    public void save_should_create_new_trade_manager_if_doesnt_exist() {
        TradeManagerByItemId tradeManager = new TradeManagerByItemId();
        tradeManager.setItemId("1");
        telegramUserTradeManagerByItemIdService.save(CHAT_ID, tradeManager);
        tradeManager.setItemId("2");
        telegramUserTradeManagerByItemIdService.save(CHAT_ID, tradeManager);

        createTelegramUser(ANOTHER_CHAT_ID);
        telegramUserTradeManagerByItemIdService.save(ANOTHER_CHAT_ID, tradeManager);

        assertEquals(2, telegramUserRepository.findById(CHAT_ID).get().getUser().getTradeManagersByItemId().size());
        assertEquals(1, telegramUserRepository.findById(ANOTHER_CHAT_ID).get().getUser().getTradeManagersByItemId().size());
        assertEquals(3, tradeManagerByItemIdRepository.findAll().size());
    }

    @Test
    public void save_should_update_existing_trade_manager() {
        TradeManagerByItemId tradeManager = new TradeManagerByItemId();
        tradeManager.setItemId("1");
        tradeManager.setBuyStartingPrice(1);
        tradeManager.setBuyBoundaryPrice(2);
        tradeManager.setSellStartingPrice(3);
        tradeManager.setSellBoundaryPrice(4);
        tradeManager.setPriority(5);
        telegramUserTradeManagerByItemIdService.save(CHAT_ID, tradeManager);

        tradeManager.setBuyStartingPrice(6);
        tradeManager.setBuyBoundaryPrice(7);
        tradeManager.setSellStartingPrice(8);
        tradeManager.setSellBoundaryPrice(9);
        tradeManager.setPriority(10);
        telegramUserTradeManagerByItemIdService.save(CHAT_ID, tradeManager);

        TradeManagerByItemIdEntity tradeManagerEntity = tradeManagerByItemIdRepository.findAll().get(0);
        assertEquals(6, tradeManagerEntity.getBuyStartingPrice());
        assertEquals(7, tradeManagerEntity.getBuyBoundaryPrice());
        assertEquals(8, tradeManagerEntity.getSellStartingPrice());
        assertEquals(9, tradeManagerEntity.getSellBoundaryPrice());
        assertEquals(10, tradeManagerEntity.getPriority());
    }

    @Test
    public void save_should_throw_exception_if_telegram_user_doesnt_exist() {
        TradeManagerByItemId tradeManager = new TradeManagerByItemId();
        tradeManager.setItemId("1");

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserTradeManagerByItemIdService.save(ANOTHER_CHAT_ID, tradeManager));
    }

    @Test
    public void deleteById_should_remove_trade_manager() {
        TradeManagerByItemId tradeManager = new TradeManagerByItemId();
        tradeManager.setItemId("1");
        telegramUserTradeManagerByItemIdService.save(CHAT_ID, tradeManager);
        tradeManager.setItemId("2");
        telegramUserTradeManagerByItemIdService.save(CHAT_ID, tradeManager);

        createTelegramUser(ANOTHER_CHAT_ID);
        telegramUserTradeManagerByItemIdService.save(ANOTHER_CHAT_ID, tradeManager);

        telegramUserTradeManagerByItemIdService.deleteById(CHAT_ID, "2");

        assertEquals(1, telegramUserRepository.findById(CHAT_ID).get().getUser().getTradeManagersByItemId().size());
        assertEquals(1, telegramUserRepository.findById(ANOTHER_CHAT_ID).get().getUser().getTradeManagersByItemId().size());
        assertEquals(2, tradeManagerByItemIdRepository.findAll().size());
    }

    @Test
    public void deleteById_should_throw_exception_if_telegram_user_doesnt_exist() {
        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserTradeManagerByItemIdService.deleteById(ANOTHER_CHAT_ID, "1"));
    }

    @Test
    public void findById_should_return_trade_manager() {
        TradeManagerByItemId tradeManager = new TradeManagerByItemId();
        tradeManager.setItemId("1");
        tradeManager.setBuyStartingPrice(1);
        telegramUserTradeManagerByItemIdService.save(CHAT_ID, tradeManager);

        tradeManager.setItemId("2");
        tradeManager.setBuyStartingPrice(1);
        telegramUserTradeManagerByItemIdService.save(CHAT_ID, tradeManager);

        createTelegramUser(ANOTHER_CHAT_ID);
        tradeManager.setItemId("1");
        tradeManager.setBuyStartingPrice(1);
        telegramUserTradeManagerByItemIdService.save(ANOTHER_CHAT_ID, tradeManager);

        TradeManagerByItemId foundTradeManager = telegramUserTradeManagerByItemIdService.findById(CHAT_ID, "1");

        assertEquals(1, foundTradeManager.getBuyStartingPrice());
    }

    @Test
    public void findById_should_throw_exception_if_telegram_user_doesnt_exist() {
        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserTradeManagerByItemIdService.findById(ANOTHER_CHAT_ID, "1"));
    }

    @Test
    public void findById_should_throw_exception_if_trade_manager_doesnt_exist() {
        assertThrows(TradeManagerByItemIdDoesntExistException.class, () -> telegramUserTradeManagerByItemIdService.findById(CHAT_ID, "1"));
    }

    @Test
    public void findAllByChatId_should_return_all_trade_managers_for_user() {
        TradeManagerByItemId tradeManager = new TradeManagerByItemId();
        tradeManager.setItemId("1");
        tradeManager.setBuyStartingPrice(1);
        telegramUserTradeManagerByItemIdService.save(CHAT_ID, tradeManager);

        tradeManager.setItemId("2");
        tradeManager.setBuyStartingPrice(1);
        telegramUserTradeManagerByItemIdService.save(CHAT_ID, tradeManager);

        createTelegramUser(ANOTHER_CHAT_ID);
        tradeManager.setItemId("1");
        tradeManager.setBuyStartingPrice(1);
        telegramUserTradeManagerByItemIdService.save(ANOTHER_CHAT_ID, tradeManager);

        Collection<TradeManagerByItemId> tradeManagers = telegramUserTradeManagerByItemIdService.findAllByChatId(CHAT_ID);

        assertEquals(2, tradeManagers.size());
    }

    @Test
    public void findAllByChatId_should_throw_exception_if_telegram_user_doesnt_exist() {
        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserTradeManagerByItemIdService.findAllByChatId(ANOTHER_CHAT_ID));
    }
}