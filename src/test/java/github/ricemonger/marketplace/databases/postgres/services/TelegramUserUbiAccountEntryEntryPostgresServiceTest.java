package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.UserEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.UbiAccountAuthorizationEntryPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.UserPostgresRepository;
import github.ricemonger.utils.DTOs.UbiAccountAuthorizationEntry;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryAlreadyExistsException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryDoesntExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TelegramUserUbiAccountEntryEntryPostgresServiceTest {
    private final static String CHAT_ID = "1";
    private final static String ANOTHER_CHAT_ID = "2";

    @Autowired
    private TelegramUserUbiAccountEntryPostgresService telegramUserUbiAccountEntryService;
    @Autowired
    private UbiAccountAuthorizationEntryPostgresRepository ubiAccountEntryRepository;
    @Autowired
    private TelegramUserPostgresRepository telegramUserRepository;
    @Autowired
    private UserPostgresRepository userRepository;

    @BeforeEach
    public void setUp() {
        ubiAccountEntryRepository.deleteAll();
        telegramUserRepository.deleteAll();
        userRepository.deleteAll();
        createTelegramUser(CHAT_ID);
    }

    private TelegramUserEntity createTelegramUser(String chatId) {
        UserEntity user = userRepository.save(new UserEntity());
        return telegramUserRepository.save(new TelegramUserEntity(chatId, user));
    }

    @Test
    public void save_should_create_new_ubi_account_entry_if_doesnt_exist() {
        UbiAccountAuthorizationEntry account = new UbiAccountAuthorizationEntry();
        account.setUbiProfileId("1");

        telegramUserUbiAccountEntryService.saveAuthorizationInfo(CHAT_ID, account);

        assertEquals("1", userRepository.findAll().get(0).getUbiAccountAuthorizationEntry().getUbiProfileId());
        assertEquals(1, telegramUserUbiAccountEntryService.findAllAuthorizationInfoForTelegram().size());

        createTelegramUser(ANOTHER_CHAT_ID);
        telegramUserUbiAccountEntryService.saveAuthorizationInfo(ANOTHER_CHAT_ID, account);

        assertEquals(2, telegramUserUbiAccountEntryService.findAllAuthorizationInfoForTelegram().size());
    }

    @Test
    public void save_should_update_ubi_account_entry_if_already_exists() {
        UbiAccountAuthorizationEntry account = new UbiAccountAuthorizationEntry();
        account.setUbiProfileId("1");

        telegramUserUbiAccountEntryService.saveAuthorizationInfo(CHAT_ID, account);

        assertEquals("1", userRepository.findAll().get(0).getUbiAccountAuthorizationEntry().getUbiProfileId());
        assertEquals(1, telegramUserUbiAccountEntryService.findAllAuthorizationInfoForTelegram().size());

        account.setUbiSpaceId("spaceID");
        telegramUserUbiAccountEntryService.saveAuthorizationInfo(CHAT_ID, account);

        assertEquals("spaceID", userRepository.findAll().get(0).getUbiAccountAuthorizationEntry().getUbiSpaceId());
        assertEquals(1, telegramUserUbiAccountEntryService.findAllAuthorizationInfoForTelegram().size());
    }

    @Test
    public void save_should_throw_exception_if_user_already_has_another_ubi_account() {
        UbiAccountAuthorizationEntry account = new UbiAccountAuthorizationEntry();
        account.setUbiProfileId("1");

        telegramUserUbiAccountEntryService.saveAuthorizationInfo(CHAT_ID, account);

        assertEquals("1", userRepository.findAll().get(0).getUbiAccountAuthorizationEntry().getUbiProfileId());
        assertEquals(1, telegramUserUbiAccountEntryService.findAllAuthorizationInfoForTelegram().size());

        account.setUbiProfileId("2");

        assertThrows(UbiAccountEntryAlreadyExistsException.class, () -> telegramUserUbiAccountEntryService.saveAuthorizationInfo(CHAT_ID, account));
    }

    @Test
    public void save_should_throw_exception_if_telegram_user_doesnt_exist() {
        UbiAccountAuthorizationEntry account = new UbiAccountAuthorizationEntry();
        account.setUbiProfileId("1");

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserUbiAccountEntryService.saveAuthorizationInfo(ANOTHER_CHAT_ID, account));
    }

    @Test
    public void deleteByChatId_should_delete_proper_ubi_account_entry_and_cascade() {
        UbiAccountAuthorizationEntry account = new UbiAccountAuthorizationEntry();
        account.setUbiProfileId("1");

        telegramUserUbiAccountEntryService.saveAuthorizationInfo(CHAT_ID, account);

        assertEquals("1", userRepository.findAll().get(0).getUbiAccountAuthorizationEntry().getUbiProfileId());
        assertEquals(1, ubiAccountEntryRepository.findAll().size());

        createTelegramUser(ANOTHER_CHAT_ID);
        telegramUserUbiAccountEntryService.saveAuthorizationInfo(ANOTHER_CHAT_ID, account);

        assertEquals(2, ubiAccountEntryRepository.findAll().size());

        telegramUserUbiAccountEntryService.deleteAuthorizationInfoByChatId(CHAT_ID);

        assertNull(userRepository.findAll().get(0).getUbiAccountAuthorizationEntry());
        assertEquals(1, ubiAccountEntryRepository.findAll().size());
    }

    @Test
    public void deleteByChatId_should_throw_exception_if_telegram_user_doesnt_exist() {
        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserUbiAccountEntryService.deleteAuthorizationInfoByChatId(ANOTHER_CHAT_ID));
    }

    @Test
    public void findByChatId_should_return_ubi_account() {
        UbiAccountAuthorizationEntry account = new UbiAccountAuthorizationEntry();
        account.setUbiProfileId("1");
        account.setUbiSpaceId("spaceID");

        telegramUserUbiAccountEntryService.saveAuthorizationInfo(CHAT_ID, account);

        createTelegramUser(ANOTHER_CHAT_ID);
        account.setUbiSpaceId("spaceID2");
        telegramUserUbiAccountEntryService.saveAuthorizationInfo(ANOTHER_CHAT_ID, account);

        assertEquals("spaceID", telegramUserUbiAccountEntryService.findAuthorizationInfoByChatId(CHAT_ID).getUbiSpaceId());
    }

    @Test
    public void findByChatId_should_throw_exception_if_telegram_user_doesnt_exist() {
        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserUbiAccountEntryService.findAuthorizationInfoByChatId(ANOTHER_CHAT_ID));
    }

    @Test
    public void findByChatId_should_throw_exception_if_ubi_account_doesnt_exist() {
        assertThrows(UbiAccountEntryDoesntExistException.class, () -> telegramUserUbiAccountEntryService.findAuthorizationInfoByChatId(CHAT_ID));
    }

    @Test
    public void findAll_should_return_all_ubi_accounts() {
        UbiAccountAuthorizationEntry account = new UbiAccountAuthorizationEntry();
        account.setUbiProfileId("1");
        account.setUbiSpaceId("spaceID");

        telegramUserUbiAccountEntryService.saveAuthorizationInfo(CHAT_ID, account);

        createTelegramUser(ANOTHER_CHAT_ID);
        account.setUbiProfileId("2");
        account.setUbiSpaceId("spaceID2");
        telegramUserUbiAccountEntryService.saveAuthorizationInfo(ANOTHER_CHAT_ID, account);

        assertEquals(2, telegramUserUbiAccountEntryService.findAllAuthorizationInfoForTelegram().size());
    }

    @Test
    public void findAll_should_return_empty_list_if_no_ubi_accounts() {
        assertEquals(0, telegramUserUbiAccountEntryService.findAllAuthorizationInfoForTelegram().size());
    }
}