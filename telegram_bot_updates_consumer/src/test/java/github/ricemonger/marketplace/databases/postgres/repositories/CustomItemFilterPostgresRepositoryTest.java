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
class CustomItemFilterPostgresRepositoryTest {
    @Autowired
    private CustomItemFilterPostgresRepository customItemFilterPostgresRepository;
    @Autowired
    private CustomTelegramUserPostgresRepository telegramUserRepository;
    @Autowired
    private CustomUserPostgresRepository userRepository;

    @BeforeEach
    public void setUp() {
        customItemFilterPostgresRepository.deleteAll();
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
        customItemFilterPostgresRepository.save(itemFilterEntity11);

        ItemFilterEntity itemFilterEntity12 = new ItemFilterEntity();
        itemFilterEntity12.setName("name2");
        itemFilterEntity12.setUser(userEntity);
        customItemFilterPostgresRepository.save(itemFilterEntity12);

        UserEntity userEntity2 = userRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity2 = new TelegramUserEntity();
        telegramUserEntity2.setChatId("chatId2");
        telegramUserEntity2.setUser(userEntity2);
        telegramUserEntity2 = telegramUserRepository.save(telegramUserEntity2);

        ItemFilterEntity itemFilterEntity21 = new ItemFilterEntity();
        itemFilterEntity21.setName("name1");
        itemFilterEntity21.setUser(userEntity2);
        customItemFilterPostgresRepository.save(itemFilterEntity21);

        customItemFilterPostgresRepository.deleteByUserTelegramUserChatIdAndName("chatId", "name1");
        assertEquals(2, customItemFilterPostgresRepository.findAll().size());
        assertTrue(customItemFilterPostgresRepository.findAll().stream().anyMatch(itemFilterEntity -> itemFilterEntity.getName().equals("name2") && itemFilterEntity.getUser().equals(userEntity)));
        assertTrue(customItemFilterPostgresRepository.findAll().stream().anyMatch(itemFilterEntity -> itemFilterEntity.getName().equals("name1") && itemFilterEntity.getUser().equals(userEntity2)));
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
        customItemFilterPostgresRepository.save(itemFilterEntity11);

        ItemFilterEntity itemFilterEntity12 = new ItemFilterEntity();
        itemFilterEntity12.setName("name2");
        itemFilterEntity12.setUser(userEntity);
        customItemFilterPostgresRepository.save(itemFilterEntity12);

        UserEntity userEntity2 = userRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity2 = new TelegramUserEntity();
        telegramUserEntity2.setChatId("chatId2");
        telegramUserEntity2.setUser(userEntity2);
        telegramUserEntity2 = telegramUserRepository.save(telegramUserEntity2);

        ItemFilterEntity itemFilterEntity21 = new ItemFilterEntity();
        itemFilterEntity21.setName("name1");
        itemFilterEntity21.setUser(userEntity2);
        customItemFilterPostgresRepository.save(itemFilterEntity21);

        assertEquals(itemFilterEntity11, customItemFilterPostgresRepository.findByUserTelegramUserChatIdAndName("chatId", "name1").get());
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
        customItemFilterPostgresRepository.save(itemFilterEntity11);

        ItemFilterEntity itemFilterEntity12 = new ItemFilterEntity();
        itemFilterEntity12.setName("name2");
        itemFilterEntity12.setUser(userEntity);
        customItemFilterPostgresRepository.save(itemFilterEntity12);

        UserEntity userEntity2 = userRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity2 = new TelegramUserEntity();
        telegramUserEntity2.setChatId("chatId2");
        telegramUserEntity2.setUser(userEntity2);
        telegramUserEntity2 = telegramUserRepository.save(telegramUserEntity2);

        ItemFilterEntity itemFilterEntity21 = new ItemFilterEntity();
        itemFilterEntity21.setName("name3");
        itemFilterEntity21.setUser(userEntity2);
        customItemFilterPostgresRepository.save(itemFilterEntity21);

        List<String> names = customItemFilterPostgresRepository.findAllNamesByUserTelegramUserChatId("chatId");
        assertEquals(3, customItemFilterPostgresRepository.findAll().size());
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
        customItemFilterPostgresRepository.save(itemFilterEntity11);

        ItemFilterEntity itemFilterEntity12 = new ItemFilterEntity();
        itemFilterEntity12.setName("name2");
        itemFilterEntity12.setUser(userEntity);
        customItemFilterPostgresRepository.save(itemFilterEntity12);

        UserEntity userEntity2 = userRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity2 = new TelegramUserEntity();
        telegramUserEntity2.setChatId("chatId2");
        telegramUserEntity2.setUser(userEntity2);
        telegramUserEntity2 = telegramUserRepository.save(telegramUserEntity2);

        ItemFilterEntity itemFilterEntity21 = new ItemFilterEntity();
        itemFilterEntity21.setName("name3");
        itemFilterEntity21.setUser(userEntity2);
        customItemFilterPostgresRepository.save(itemFilterEntity21);

        List<ItemFilterEntity> result = customItemFilterPostgresRepository.findAllByUserTelegramUserChatId("chatId");
        assertEquals(3, customItemFilterPostgresRepository.findAll().size());
        assertEquals(2, result.size());
        assertTrue(result.contains(itemFilterEntity11));
        assertTrue(result.contains(itemFilterEntity12));
    }
}