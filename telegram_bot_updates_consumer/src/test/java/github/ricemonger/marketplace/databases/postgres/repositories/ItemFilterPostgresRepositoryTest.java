package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.utilspostgresschema.full_entities.user.ItemFilterEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.TelegramUserEntity;
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
class ItemFilterPostgresRepositoryTest {
    @Autowired
    private ItemFilterPostgresRepository itemFilterPostgresRepository;
    @Autowired
    private TelegramUserPostgresRepository telegramUserRepository;
    @Autowired
    private UserPostgresRepository userRepository;

    @BeforeEach
    public void setUp() {
        itemFilterPostgresRepository.deleteAll();
        telegramUserRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void deleteByUserTelegramUserChatIdAndName_should_fully_remove_item_Filter_from_db() {
        UserEntity userEntity = userRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity = new TelegramUserEntity();
        telegramUserEntity.setChatId("chatId");
        telegramUserEntity.setUser(userEntity);
        telegramUserEntity = telegramUserRepository.save(telegramUserEntity);

        ItemFilterEntity itemFilterEntity11 = new ItemFilterEntity();
        itemFilterEntity11.setName("name1");
        itemFilterEntity11.setUser(userEntity);
        itemFilterPostgresRepository.save(itemFilterEntity11);

        ItemFilterEntity itemFilterEntity12 = new ItemFilterEntity();
        itemFilterEntity12.setName("name2");
        itemFilterEntity12.setUser(userEntity);
        itemFilterPostgresRepository.save(itemFilterEntity12);

        UserEntity userEntity2 = userRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity2 = new TelegramUserEntity();
        telegramUserEntity2.setChatId("chatId2");
        telegramUserEntity2.setUser(userEntity2);
        telegramUserEntity2 = telegramUserRepository.save(telegramUserEntity2);

        ItemFilterEntity itemFilterEntity21 = new ItemFilterEntity();
        itemFilterEntity21.setName("name1");
        itemFilterEntity21.setUser(userEntity2);
        itemFilterPostgresRepository.save(itemFilterEntity21);

        itemFilterPostgresRepository.deleteByUserTelegramUserChatIdAndName("chatId", "name1");
        assertEquals(2, itemFilterPostgresRepository.findAll().size());
        assertTrue(itemFilterPostgresRepository.findAll().stream().anyMatch(itemFilterEntity -> itemFilterEntity.getName().equals("name2") && itemFilterEntity.getUser().equals(userEntity)));
        assertTrue(itemFilterPostgresRepository.findAll().stream().anyMatch(itemFilterEntity -> itemFilterEntity.getName().equals("name1") && itemFilterEntity.getUser().equals(userEntity2)));
    }

    @Test
    public void findByUserTelegramUserChatIdAndName_should_return_only_one_filter() {
        UserEntity userEntity = userRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity = new TelegramUserEntity();
        telegramUserEntity.setChatId("chatId");
        telegramUserEntity.setUser(userEntity);
        telegramUserEntity = telegramUserRepository.save(telegramUserEntity);

        ItemFilterEntity itemFilterEntity11 = new ItemFilterEntity();
        itemFilterEntity11.setName("name1");
        itemFilterEntity11.setUser(userEntity);
        itemFilterPostgresRepository.save(itemFilterEntity11);

        ItemFilterEntity itemFilterEntity12 = new ItemFilterEntity();
        itemFilterEntity12.setName("name2");
        itemFilterEntity12.setUser(userEntity);
        itemFilterPostgresRepository.save(itemFilterEntity12);

        UserEntity userEntity2 = userRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity2 = new TelegramUserEntity();
        telegramUserEntity2.setChatId("chatId2");
        telegramUserEntity2.setUser(userEntity2);
        telegramUserEntity2 = telegramUserRepository.save(telegramUserEntity2);

        ItemFilterEntity itemFilterEntity21 = new ItemFilterEntity();
        itemFilterEntity21.setName("name1");
        itemFilterEntity21.setUser(userEntity2);
        itemFilterPostgresRepository.save(itemFilterEntity21);

        assertEquals(itemFilterEntity11, itemFilterPostgresRepository.findByUserTelegramUserChatIdAndName("chatId", "name1").get());
    }

    @Test
    public void findAllNamesByUserTelegramUserChatId_should_return_only_filter_names_of_one_user() {
        UserEntity userEntity = userRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity = new TelegramUserEntity();
        telegramUserEntity.setChatId("chatId");
        telegramUserEntity.setUser(userEntity);
        telegramUserEntity = telegramUserRepository.save(telegramUserEntity);

        ItemFilterEntity itemFilterEntity11 = new ItemFilterEntity();
        itemFilterEntity11.setName("name1");
        itemFilterEntity11.setUser(userEntity);
        itemFilterPostgresRepository.save(itemFilterEntity11);

        ItemFilterEntity itemFilterEntity12 = new ItemFilterEntity();
        itemFilterEntity12.setName("name2");
        itemFilterEntity12.setUser(userEntity);
        itemFilterPostgresRepository.save(itemFilterEntity12);

        UserEntity userEntity2 = userRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity2 = new TelegramUserEntity();
        telegramUserEntity2.setChatId("chatId2");
        telegramUserEntity2.setUser(userEntity2);
        telegramUserEntity2 = telegramUserRepository.save(telegramUserEntity2);

        ItemFilterEntity itemFilterEntity21 = new ItemFilterEntity();
        itemFilterEntity21.setName("name3");
        itemFilterEntity21.setUser(userEntity2);
        itemFilterPostgresRepository.save(itemFilterEntity21);

        List<String> names = itemFilterPostgresRepository.findAllNamesByUserTelegramUserChatId("chatId");
        assertEquals(3, itemFilterPostgresRepository.findAll().size());
        assertEquals(2, names.size());
        assertTrue(names.contains("name1"));
        assertTrue(names.contains("name2"));
    }

    @Test
    public void findAllByUserTelegramUserChatId_should_return_all_filters_for_telegramUser() {
        UserEntity userEntity = userRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity = new TelegramUserEntity();
        telegramUserEntity.setChatId("chatId");
        telegramUserEntity.setUser(userEntity);
        telegramUserEntity = telegramUserRepository.save(telegramUserEntity);

        ItemFilterEntity itemFilterEntity11 = new ItemFilterEntity();
        itemFilterEntity11.setName("name1");
        itemFilterEntity11.setUser(userEntity);
        itemFilterPostgresRepository.save(itemFilterEntity11);

        ItemFilterEntity itemFilterEntity12 = new ItemFilterEntity();
        itemFilterEntity12.setName("name2");
        itemFilterEntity12.setUser(userEntity);
        itemFilterPostgresRepository.save(itemFilterEntity12);

        UserEntity userEntity2 = userRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity2 = new TelegramUserEntity();
        telegramUserEntity2.setChatId("chatId2");
        telegramUserEntity2.setUser(userEntity2);
        telegramUserEntity2 = telegramUserRepository.save(telegramUserEntity2);

        ItemFilterEntity itemFilterEntity21 = new ItemFilterEntity();
        itemFilterEntity21.setName("name3");
        itemFilterEntity21.setUser(userEntity2);
        itemFilterPostgresRepository.save(itemFilterEntity21);

        List<ItemFilterEntity> result = itemFilterPostgresRepository.findAllByUserTelegramUserChatId("chatId");
        assertEquals(3, itemFilterPostgresRepository.findAll().size());
        assertEquals(2, result.size());
        assertTrue(result.contains(itemFilterEntity11));
        assertTrue(result.contains(itemFilterEntity12));
    }
}