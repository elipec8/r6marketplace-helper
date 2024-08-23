package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.UserEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.TradeByFiltersManagerPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.UserPostgresRepository;
import github.ricemonger.utils.dtos.TradeByFiltersManager;
import github.ricemonger.utils.enums.TradeManagerTradeType;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class TelegramUserTradeByFiltersManagerPostgresServiceTest {
    private final static String CHAT_ID = "1";
    private final static String ANOTHER_CHAT_ID = "2";

    @Autowired
    private TelegramUserTradeByFiltersManagerPostgresService telegramUserTradeByFiltersManagerService;
    @Autowired
    private TradeByFiltersManagerPostgresRepository tradeByFiltersManagerRepository;
    @Autowired
    private TelegramUserPostgresRepository telegramUserRepository;
    @Autowired
    private UserPostgresRepository userRepository;

    @BeforeEach
    public void setUp() {
        tradeByFiltersManagerRepository.deleteAll();
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
        TradeByFiltersManager tradeManager = new TradeByFiltersManager();
        tradeManager.setName("name1");
        telegramUserTradeByFiltersManagerService.save(CHAT_ID, tradeManager);
        tradeManager.setName("name2");
        telegramUserTradeByFiltersManagerService.save(CHAT_ID, tradeManager);

        createTelegramUser(ANOTHER_CHAT_ID);
        telegramUserTradeByFiltersManagerService.save(ANOTHER_CHAT_ID, tradeManager);

        assertEquals(2, telegramUserRepository.findById(CHAT_ID).get().getUser().getTradeByFiltersManagers().size());
        assertEquals(3, tradeByFiltersManagerRepository.findAll().size());
    }

    @Test
    public void save_should_update_trade_manager_if_exists() {
        TradeByFiltersManager tradeManager = new TradeByFiltersManager();
        tradeManager.setName("name1");
        tradeManager.setPriority(10);
        tradeManager.setMinBuySellProfit(100);
        tradeManager.setMinProfitPercent(100);
        tradeManager.setTradeType(TradeManagerTradeType.BUY);
        telegramUserTradeByFiltersManagerService.save(CHAT_ID, tradeManager);
        tradeManager.setName("name1");
        tradeManager.setPriority(20);
        tradeManager.setMinBuySellProfit(200);
        tradeManager.setMinProfitPercent(200);
        tradeManager.setTradeType(TradeManagerTradeType.SELL);
        tradeManager.setAppliedFilters(List.of());
        telegramUserTradeByFiltersManagerService.save(CHAT_ID, tradeManager);

        createTelegramUser(ANOTHER_CHAT_ID);
        telegramUserTradeByFiltersManagerService.save(ANOTHER_CHAT_ID, tradeManager);

        assertEquals(1, telegramUserRepository.findById(CHAT_ID).get().getUser().getTradeByFiltersManagers().size());
        assertEquals(2,tradeByFiltersManagerRepository.findAll().size());

        assertEquals(tradeManager, telegramUserRepository.findById(CHAT_ID).get().getUser().getTradeByFiltersManagers().get(0).toTradeByFiltersManager());
    }

    @Test
    public void save_should_throw_if_telegram_user_doesnt_exist() {
        TradeByFiltersManager tradeManager = new TradeByFiltersManager();

        assertThrows(TelegramUserDoesntExistException.class, () ->{telegramUserTradeByFiltersManagerService.save(ANOTHER_CHAT_ID, tradeManager);});
    }
}