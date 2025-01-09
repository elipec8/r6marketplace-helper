package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.dto_projections.UbiAccountAuthorizationEntryProjection;
import github.ricemonger.utilspostgresschema.full_entities.user.TelegramUserEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.UbiAccountEntryEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.UbiAccountStatsEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class UbiAccountEntryPostgresRepositoryTest {
    @Autowired
    private UbiAccountEntryPostgresRepository ubiAccountEntryPostgresRepository;
    @Autowired
    private TelegramUserPostgresRepository telegramUserPostgresRepository;
    @Autowired
    private UserPostgresRepository userPostgresRepository;
    @Autowired
    private UbiAccountStatsPostgresRepository ubiAccountStatsPostgresRepository;

    @BeforeEach
    void setUp() {
        telegramUserPostgresRepository.deleteAll();
        userPostgresRepository.deleteAll();
        ubiAccountEntryPostgresRepository.deleteAll();
        ubiAccountStatsPostgresRepository.deleteAll();
    }

    @Test
    public void deleteByUserTelegramUserChatId_should_delete_entry() {
        UserEntity userEntity1 = userPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1 = telegramUserPostgresRepository.save(telegramUserEntity1);

        UbiAccountEntryEntity ubiAccountEntryEntity1 = new UbiAccountEntryEntity();
        ubiAccountEntryEntity1.setUser(userEntity1);
        ubiAccountEntryEntity1.setEmail("email1");
        ubiAccountEntryEntity1 = ubiAccountEntryPostgresRepository.save(ubiAccountEntryEntity1);

        UserEntity userEntity2 = userPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity2 = new TelegramUserEntity();
        telegramUserEntity2.setChatId("chatId2");
        telegramUserEntity2.setUser(userEntity2);
        telegramUserEntity2 = telegramUserPostgresRepository.save(telegramUserEntity2);

        UbiAccountEntryEntity ubiAccountEntryEntity2 = new UbiAccountEntryEntity();
        ubiAccountEntryEntity2.setUser(userEntity2);
        ubiAccountEntryEntity2.setEmail("email1");
        ubiAccountEntryEntity2 = ubiAccountEntryPostgresRepository.save(ubiAccountEntryEntity2);

        assertEquals(2, ubiAccountEntryPostgresRepository.count());

        ubiAccountEntryPostgresRepository.deleteByUserTelegramUserChatId("chatId1");

        assertEquals(1, ubiAccountEntryPostgresRepository.count());
        assertEquals(ubiAccountEntryEntity2, ubiAccountEntryPostgresRepository.findAll().get(0));
    }

    @Test
    public void findByUserTelegramUserChatId_should_return_entry() {
        UserEntity userEntity1 = userPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1 = telegramUserPostgresRepository.save(telegramUserEntity1);

        UbiAccountEntryEntity ubiAccountEntryEntity1 = new UbiAccountEntryEntity();
        ubiAccountEntryEntity1.setUser(userEntity1);
        ubiAccountEntryEntity1.setEmail("email1");
        ubiAccountEntryEntity1 = ubiAccountEntryPostgresRepository.save(ubiAccountEntryEntity1);

        UserEntity userEntity2 = userPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity2 = new TelegramUserEntity();
        telegramUserEntity2.setChatId("chatId2");
        telegramUserEntity2.setUser(userEntity2);
        telegramUserEntity2 = telegramUserPostgresRepository.save(telegramUserEntity2);

        UbiAccountEntryEntity ubiAccountEntryEntity2 = new UbiAccountEntryEntity();
        ubiAccountEntryEntity2.setUser(userEntity2);
        ubiAccountEntryEntity2.setEmail("email2");
        ubiAccountEntryEntity2 = ubiAccountEntryPostgresRepository.save(ubiAccountEntryEntity2);

        assertEquals(2, ubiAccountEntryPostgresRepository.count());

        assertEquals(ubiAccountEntryEntity1, ubiAccountEntryPostgresRepository.findByUserTelegramUserChatId("chatId1").get());
    }

    @Test
    public void findUbiAccountAuthorizationEntryByUserTelegramUserChatId_should_return_entry() {
        UserEntity userEntity1 = userPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1 = telegramUserPostgresRepository.save(telegramUserEntity1);

        UbiAccountStatsEntity ubiAccountStatsEntity1 = new UbiAccountStatsEntity();
        ubiAccountStatsEntity1.setUbiProfileId("ubiProfileId1");
        ubiAccountStatsEntity1 = ubiAccountStatsPostgresRepository.save(ubiAccountStatsEntity1);

        UbiAccountEntryEntity ubiAccountEntryEntity1 = new UbiAccountEntryEntity();
        ubiAccountEntryEntity1.setUser(userEntity1);
        ubiAccountEntryEntity1.setEmail("email1");
        ubiAccountEntryEntity1.setEncodedPassword("encodedPassword1");
        ubiAccountEntryEntity1.setUbiSessionId("ubiSessionId1");
        ubiAccountEntryEntity1.setUbiSpaceId("ubiSpaceId1");
        ubiAccountEntryEntity1.setUbiAuthTicket("ubiAuthTicket1");
        ubiAccountEntryEntity1.setUbiRememberDeviceTicket("ubiRememberDeviceTicket1");
        ubiAccountEntryEntity1.setUbiRememberMeTicket("ubiRememberMeTicket1");
        ubiAccountEntryEntity1.setUbiAccountStats(ubiAccountStatsEntity1);
        ubiAccountEntryEntity1 = ubiAccountEntryPostgresRepository.save(ubiAccountEntryEntity1);

        UserEntity userEntity2 = userPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity2 = new TelegramUserEntity();
        telegramUserEntity2.setChatId("chatId2");
        telegramUserEntity2.setUser(userEntity2);
        telegramUserEntity2 = telegramUserPostgresRepository.save(telegramUserEntity2);

        UbiAccountStatsEntity ubiAccountStatsEntity2 = new UbiAccountStatsEntity();
        ubiAccountStatsEntity2.setUbiProfileId("ubiProfileId2");
        ubiAccountStatsEntity2 = ubiAccountStatsPostgresRepository.save(ubiAccountStatsEntity2);

        UbiAccountEntryEntity ubiAccountEntryEntity2 = new UbiAccountEntryEntity();
        ubiAccountEntryEntity2.setUser(userEntity2);
        ubiAccountEntryEntity2.setEmail("email2");
        ubiAccountEntryEntity2.setEncodedPassword("encodedPassword2");
        ubiAccountEntryEntity2.setUbiSessionId("ubiSessionId2");
        ubiAccountEntryEntity2.setUbiSpaceId("ubiSpaceId2");
        ubiAccountEntryEntity2.setUbiAuthTicket("ubiAuthTicket2");
        ubiAccountEntryEntity2.setUbiRememberDeviceTicket("ubiRememberDeviceTicket2");
        ubiAccountEntryEntity2.setUbiRememberMeTicket("ubiRememberMeTicket2");
        ubiAccountEntryEntity2.setUbiAccountStats(ubiAccountStatsEntity2);
        ubiAccountEntryEntity2 = ubiAccountEntryPostgresRepository.save(ubiAccountEntryEntity2);

        assertEquals(2, ubiAccountEntryPostgresRepository.count());

        assertEquals(new UbiAccountAuthorizationEntryProjection(
                        ubiAccountEntryEntity1.getUbiAccountStats().getUbiProfileId(),
                        ubiAccountEntryEntity1.getEmail(),
                        ubiAccountEntryEntity1.getEncodedPassword(),
                        ubiAccountEntryEntity1.getUbiSessionId(),
                        ubiAccountEntryEntity1.getUbiSpaceId(),
                        ubiAccountEntryEntity1.getUbiAuthTicket(),
                        ubiAccountEntryEntity1.getUbiRememberDeviceTicket(),
                        ubiAccountEntryEntity1.getUbiRememberMeTicket()),
                ubiAccountEntryPostgresRepository.findUbiAccountAuthorizationEntryByUserTelegramUserChatId("chatId1").get());
    }
}