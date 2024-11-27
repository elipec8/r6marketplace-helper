package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.UserEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemFilterPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.UserPostgresRepository;
import github.ricemonger.utils.DTOs.items.ItemFilter;
import github.ricemonger.utils.enums.FilterType;
import github.ricemonger.utils.exceptions.client.ItemFilterDoesntExistException;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class TelegramUserItemFilterPostgresServiceTest {
    private final static String CHAT_ID = "1";
    private final static String ANOTHER_CHAT_ID = "2";

    @Autowired
    private TelegramUserItemFilterPostgresService itemFilterService;
    @Autowired
    private ItemFilterPostgresRepository itemFilterRepository;
    @Autowired
    private TelegramUserPostgresRepository telegramUserRepository;
    @Autowired
    private UserPostgresRepository userRepository;

    @BeforeEach
    public void setUp() {
        itemFilterRepository.deleteAll();
        telegramUserRepository.deleteAll();
        userRepository.deleteAll();

        createTelegramUser(CHAT_ID);
    }

    private TelegramUserEntity createTelegramUser(String chatId) {
        UserEntity user = userRepository.save(new UserEntity());
        return telegramUserRepository.save(new TelegramUserEntity(chatId, user));
    }

    @Test
    public void save_should_create_new_filter() {
        ItemFilter itemFilter1 = new ItemFilter();
        itemFilter1.setName("filter1");
        itemFilterService.save(CHAT_ID, itemFilter1);

        ItemFilter itemFilter2 = new ItemFilter();
        itemFilter2.setName("filter2");
        itemFilterService.save(CHAT_ID, itemFilter2);

        assertEquals(2, itemFilterRepository.findAll().size());
        assertEquals(2, telegramUserRepository.findAll().get(0).getUser().getItemFilters().size());
    }

    @Test
    public void save_should_update_existing_filter() {
        ItemFilter itemFilter = new ItemFilter();
        itemFilter.setName("filter1");
        itemFilter.setFilterType(FilterType.ALLOW);
        itemFilterService.save(CHAT_ID, itemFilter);

        itemFilter.setFilterType(FilterType.DENY);
        itemFilterService.save(CHAT_ID, itemFilter);

        assertEquals(1, itemFilterRepository.findAll().size());
        assertEquals(1, telegramUserRepository.findById(CHAT_ID).get().getUser().getItemFilters().size());
        assertEquals(FilterType.DENY, itemFilterRepository.findAll().get(0).getFilterType());
    }

    @Test
    public void save_should_throw_if_telegram_user_does_not_exist() {
        ItemFilter itemFilter = new ItemFilter();
        itemFilter.setName("filter1");

        assertThrows(TelegramUserDoesntExistException.class, () -> itemFilterService.save(ANOTHER_CHAT_ID, itemFilter));
    }

    @Test
    public void deleteById_should_remove_proper_filter() {
        Long mainUserId = userRepository.findAll().get(0).getId();

        createTelegramUser(ANOTHER_CHAT_ID);

        ItemFilter itemFilter1 = new ItemFilter();
        itemFilter1.setName("filter1");
        itemFilterService.save(CHAT_ID, itemFilter1);
        itemFilterService.save(ANOTHER_CHAT_ID, itemFilter1);

        ItemFilter itemFilter2 = new ItemFilter();
        itemFilter2.setName("filter2");
        itemFilterService.save(CHAT_ID, itemFilter2);
        itemFilterService.save(ANOTHER_CHAT_ID, itemFilter2);

        itemFilterService.deleteById(CHAT_ID, "filter1");

        assertEquals(telegramUserRepository.count(), 2);
        assertEquals(3, itemFilterRepository.count());
        assertEquals(1, telegramUserRepository.findById(CHAT_ID).get().getUser().getItemFilters().size());
        assertEquals("filter2", itemFilterRepository.findAllByUserId(mainUserId).get(0).getName());
    }

    @Test
    public void deleteById_should_throw_if_telegram_user_does_not_exist() {
        assertThrows(TelegramUserDoesntExistException.class, () -> itemFilterService.deleteById(ANOTHER_CHAT_ID, "filter1"));
    }

    @Test
    public void findById_should_return_proper_filter() {
        createTelegramUser(ANOTHER_CHAT_ID);

        ItemFilter itemFilter1 = new ItemFilter();
        itemFilter1.setName("filter1");
        itemFilter1.setFilterType(FilterType.ALLOW);
        itemFilterService.save(CHAT_ID, itemFilter1);
        itemFilter1.setFilterType(FilterType.DENY);
        itemFilterService.save(ANOTHER_CHAT_ID, itemFilter1);

        ItemFilter itemFilter2 = new ItemFilter();
        itemFilter2.setName("filter2");
        itemFilter2.setFilterType(FilterType.DENY);
        itemFilterService.save(CHAT_ID, itemFilter2);
        itemFilter2.setFilterType(FilterType.ALLOW);
        itemFilterService.save(ANOTHER_CHAT_ID, itemFilter2);

        assertEquals(FilterType.ALLOW, itemFilterService.findById(CHAT_ID, "filter1").getFilterType());
        assertEquals(FilterType.DENY, itemFilterService.findById(ANOTHER_CHAT_ID, "filter1").getFilterType());

        assertEquals(FilterType.DENY, itemFilterService.findById(CHAT_ID, "filter2").getFilterType());
        assertEquals(FilterType.ALLOW, itemFilterService.findById(ANOTHER_CHAT_ID, "filter2").getFilterType());
    }

    @Test
    public void findById_should_throw_if_telegram_user_does_not_exist() {
        assertThrows(TelegramUserDoesntExistException.class, () -> itemFilterService.findById(ANOTHER_CHAT_ID, "filter1"));
    }

    @Test
    public void findById_should_throw_if_item_filter_does_not_exist() {
        assertThrows(ItemFilterDoesntExistException.class, () -> itemFilterService.findById(CHAT_ID, "filter1"));
    }

    @Test
    public void findAllByChatId_should_return_all_filters_for_user() {
        createTelegramUser(ANOTHER_CHAT_ID);

        ItemFilter itemFilter1 = new ItemFilter();
        itemFilter1.setName("filter1");
        itemFilter1.setFilterType(FilterType.ALLOW);
        itemFilterService.save(CHAT_ID, itemFilter1);
        itemFilter1.setFilterType(FilterType.DENY);
        itemFilterService.save(ANOTHER_CHAT_ID, itemFilter1);

        ItemFilter itemFilter2 = new ItemFilter();
        itemFilter2.setName("filter2");
        itemFilter2.setFilterType(FilterType.DENY);
        itemFilterService.save(CHAT_ID, itemFilter2);
        itemFilter2.setFilterType(FilterType.ALLOW);
        itemFilterService.save(ANOTHER_CHAT_ID, itemFilter2);

        assertEquals(2, itemFilterService.findAllByChatId(CHAT_ID).size());

        assertEquals(2, itemFilterService.findAllByChatId(ANOTHER_CHAT_ID).size());
    }

    @Test
    public void findAllByChatId_should_return_empty_list_if_no_filters() {
        assertEquals(0, itemFilterService.findAllByChatId(CHAT_ID).size());
    }

    @Test
    public void findAllByChatId_should_throw_if_telegram_user_does_not_exist() {
        assertThrows(TelegramUserDoesntExistException.class, () -> itemFilterService.findAllByChatId(ANOTHER_CHAT_ID));
    }
}