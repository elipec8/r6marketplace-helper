package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.UserEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.UbiAccountEntryPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.UserPostgresRepository;
import github.ricemonger.utils.dtos.UbiAccountEntry;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryDoesntExistException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryAlreadyExistsException;
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
    private UbiAccountEntryPostgresRepository ubiAccountEntryRepository;
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
        UbiAccountEntry account = new UbiAccountEntry();
        account.setUbiProfileId("1");

        telegramUserUbiAccountEntryService.save(CHAT_ID, account);

        assertEquals("1", userRepository.findAll().get(0).getUbiAccountEntry().getUbiProfileId());
        assertEquals(1, telegramUserUbiAccountEntryService.findAll().size());

        createTelegramUser(ANOTHER_CHAT_ID);
        telegramUserUbiAccountEntryService.save(ANOTHER_CHAT_ID, account);

        assertEquals(2, telegramUserUbiAccountEntryService.findAll().size());
    }

    @Test
    public void save_should_update_ubi_account_entry_if_already_exists() {
        UbiAccountEntry account = new UbiAccountEntry();
        account.setUbiProfileId("1");

        telegramUserUbiAccountEntryService.save(CHAT_ID, account);

        assertEquals("1", userRepository.findAll().get(0).getUbiAccountEntry().getUbiProfileId());
        assertEquals(1, telegramUserUbiAccountEntryService.findAll().size());

        account.setUbiSpaceId("spaceID");
        telegramUserUbiAccountEntryService.save(CHAT_ID, account);

        assertEquals("spaceID", userRepository.findAll().get(0).getUbiAccountEntry().getUbiSpaceId());
        assertEquals(1, telegramUserUbiAccountEntryService.findAll().size());
    }

    @Test
    public void save_should_throw_exception_if_user_already_has_another_ubi_account() {
        UbiAccountEntry account = new UbiAccountEntry();
        account.setUbiProfileId("1");

        telegramUserUbiAccountEntryService.save(CHAT_ID, account);

        assertEquals("1", userRepository.findAll().get(0).getUbiAccountEntry().getUbiProfileId());
        assertEquals(1, telegramUserUbiAccountEntryService.findAll().size());

        account.setUbiProfileId("2");

        assertThrows(UbiAccountEntryAlreadyExistsException.class, () -> telegramUserUbiAccountEntryService.save(CHAT_ID, account));
    }

    @Test
    public void save_should_throw_exception_if_telegram_user_doesnt_exist() {
        UbiAccountEntry account = new UbiAccountEntry();
        account.setUbiProfileId("1");

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserUbiAccountEntryService.save(ANOTHER_CHAT_ID, account));
    }

    @Test
    public void deleteByChatId_should_delete_proper_ubi_account_entry_and_cascade() {
        UbiAccountEntry account = new UbiAccountEntry();
        account.setUbiProfileId("1");

        telegramUserUbiAccountEntryService.save(CHAT_ID, account);

        assertEquals("1", userRepository.findAll().get(0).getUbiAccountEntry().getUbiProfileId());
        assertEquals(1, ubiAccountEntryRepository.findAll().size());

        createTelegramUser(ANOTHER_CHAT_ID);
        telegramUserUbiAccountEntryService.save(ANOTHER_CHAT_ID, account);

        assertEquals(2, ubiAccountEntryRepository.findAll().size());

        telegramUserUbiAccountEntryService.deleteByChatId(CHAT_ID);

        assertNull(userRepository.findAll().get(0).getUbiAccountEntry());
        assertEquals(1, ubiAccountEntryRepository.findAll().size());
    }

    @Test
    public void deleteByChatId_should_throw_exception_if_telegram_user_doesnt_exist() {
        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserUbiAccountEntryService.deleteByChatId(ANOTHER_CHAT_ID));
    }

    @Test
    public void findByChatId_should_return_ubi_account() {
        UbiAccountEntry account = new UbiAccountEntry();
        account.setUbiProfileId("1");
        account.setUbiSpaceId("spaceID");

        telegramUserUbiAccountEntryService.save(CHAT_ID, account);

        createTelegramUser(ANOTHER_CHAT_ID);
        account.setUbiSpaceId("spaceID2");
        telegramUserUbiAccountEntryService.save(ANOTHER_CHAT_ID, account);

        assertEquals("spaceID", telegramUserUbiAccountEntryService.findByChatId(CHAT_ID).getUbiSpaceId());
    }

    @Test
    public void findByChatId_should_throw_exception_if_telegram_user_doesnt_exist() {
        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserUbiAccountEntryService.findByChatId(ANOTHER_CHAT_ID));
    }

    @Test
    public void findByChatId_should_throw_exception_if_ubi_account_doesnt_exist() {
        assertThrows(UbiAccountEntryDoesntExistException.class, () -> telegramUserUbiAccountEntryService.findByChatId(CHAT_ID));
    }

    @Test
    public void findAll_should_return_all_ubi_accounts() {
        UbiAccountEntry account = new UbiAccountEntry();
        account.setUbiProfileId("1");
        account.setUbiSpaceId("spaceID");

        telegramUserUbiAccountEntryService.save(CHAT_ID, account);

        createTelegramUser(ANOTHER_CHAT_ID);
        account.setUbiProfileId("2");
        account.setUbiSpaceId("spaceID2");
        telegramUserUbiAccountEntryService.save(ANOTHER_CHAT_ID, account);

        assertEquals(2, telegramUserUbiAccountEntryService.findAll().size());
    }

    @Test
    public void findAll_should_return_empty_list_if_no_ubi_accounts() {
        assertEquals(0, telegramUserUbiAccountEntryService.findAll().size());
    }
}