package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.utilspostgresschema.full_entities.user.ItemFilterEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.TelegramUserEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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

        ItemFilterEntity itemFilterEntity12 = new ItemFilterEntity();
        itemFilterEntity12.setName("name2");
        itemFilterEntity12.setUser(userEntity);

        UserEntity userEntity2 = userRepository.save(new UserEntity());

        ItemFilterEntity itemFilterEntity21 = new ItemFilterEntity();
        itemFilterEntity21.setName("name1");
        itemFilterEntity21.setUser(userEntity2);

        itemFilterPostgresRepository.deleteByUserTelegramUserChatIdAndName("chatId", "name1");
        assertEquals(2, itemFilterPostgresRepository.findAll().size());
        assertTrue(itemFilterPostgresRepository.findAll().stream().anyMatch(itemFilterEntity -> itemFilterEntity.getName().equals("name2") && itemFilterEntity.getUser().equals(userEntity)));
        assertTrue(itemFilterPostgresRepository.findAll().stream().anyMatch(itemFilterEntity -> itemFilterEntity.getName().equals("name1") && itemFilterEntity.getUser().equals(userEntity2)));
    }
}