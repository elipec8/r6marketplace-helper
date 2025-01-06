package github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user;

import github.ricemonger.marketplace.databases.postgres.repositories.UbiAccountStatsPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.UserPostgresRepository;
import github.ricemonger.marketplace.services.DTOs.UbiAccountAuthorizationEntry;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utilspostgresschema.full_entities.user.UbiAccountEntryEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.UbiAccountStatsEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class UbiAccountEntryEntityMapperTest {
    @Autowired
    private UbiAccountEntryEntityMapper ubiAccountEntryEntityMapper;
    @MockBean
    private UserPostgresRepository userPostgresRepository;
    @MockBean
    private UbiAccountStatsPostgresRepository ubiAccountStatsPostgresRepository;

    @Test
    public void createEntity_should_properly_map_entity_if_ubi_stats_exists() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        when(userPostgresRepository.existsByTelegramUserChatId("chatId")).thenReturn(true);
        when(userPostgresRepository.getReferenceByTelegramUserChatId("chatId")).thenReturn(userEntity);

        UbiAccountStatsEntity ubiAccountStatsEntity = new UbiAccountStatsEntity();
        ubiAccountStatsEntity.setUbiProfileId("ubiProfileId");
        when(ubiAccountStatsPostgresRepository.findById("ubiProfileId")).thenReturn(Optional.of(ubiAccountStatsEntity));

        UbiAccountAuthorizationEntry account = new UbiAccountAuthorizationEntry();
        account.setUbiProfileId("ubiProfileId");
        account.setEmail("email");
        account.setEncodedPassword("encodedPassword");
        account.setUbiSessionId("ubiSessionId");
        account.setUbiSpaceId("ubiSpaceId");
        account.setUbiAuthTicket("ubiAuthTicket");
        account.setUbiRememberDeviceTicket("ubiRememberDeviceTicket");
        account.setUbiRememberMeTicket("ubiRememberMeTicket");

        UbiAccountEntryEntity entity = ubiAccountEntryEntityMapper.createEntity("chatId", account);

        assertEquals(userEntity, entity.getUser());
        assertEquals("email", entity.getEmail());
        assertEquals("encodedPassword", entity.getEncodedPassword());
        assertEquals("ubiSessionId", entity.getUbiSessionId());
        assertEquals("ubiSpaceId", entity.getUbiSpaceId());
        assertEquals("ubiAuthTicket", entity.getUbiAuthTicket());
        assertEquals("ubiRememberDeviceTicket", entity.getUbiRememberDeviceTicket());
        assertEquals("ubiRememberMeTicket", entity.getUbiRememberMeTicket());
        assertEquals(ubiAccountStatsEntity, entity.getUbiAccountStats());
    }

    @Test
    public void createEntity_should_properly_map_entity_if_ubi_stats_doesnt_exist() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        when(userPostgresRepository.existsByTelegramUserChatId("chatId")).thenReturn(true);
        when(userPostgresRepository.getReferenceByTelegramUserChatId("chatId")).thenReturn(userEntity);

        UbiAccountStatsEntity ubiAccountStatsEntity = new UbiAccountStatsEntity();
        ubiAccountStatsEntity.setUbiProfileId("ubiProfileId");
        when(ubiAccountStatsPostgresRepository.findById("ubiProfileId")).thenReturn(Optional.empty());

        UbiAccountAuthorizationEntry account = new UbiAccountAuthorizationEntry();
        account.setUbiProfileId("ubiProfileId");
        account.setEmail("email");
        account.setEncodedPassword("encodedPassword");
        account.setUbiSessionId("ubiSessionId");
        account.setUbiSpaceId("ubiSpaceId");
        account.setUbiAuthTicket("ubiAuthTicket");
        account.setUbiRememberDeviceTicket("ubiRememberDeviceTicket");
        account.setUbiRememberMeTicket("ubiRememberMeTicket");

        UbiAccountEntryEntity entity = ubiAccountEntryEntityMapper.createEntity("chatId", account);

        assertEquals(userEntity, entity.getUser());
        assertEquals("email", entity.getEmail());
        assertEquals("encodedPassword", entity.getEncodedPassword());
        assertEquals("ubiSessionId", entity.getUbiSessionId());
        assertEquals("ubiSpaceId", entity.getUbiSpaceId());
        assertEquals("ubiAuthTicket", entity.getUbiAuthTicket());
        assertEquals("ubiRememberDeviceTicket", entity.getUbiRememberDeviceTicket());
        assertEquals("ubiRememberMeTicket", entity.getUbiRememberMeTicket());
        assertEquals(ubiAccountStatsEntity, entity.getUbiAccountStats());
    }

    @Test
    public void createEntity_should_throw_if_user_doesnt_exist() {
        when(userPostgresRepository.existsByTelegramUserChatId("chatId")).thenReturn(false);
        UbiAccountAuthorizationEntry account = new UbiAccountAuthorizationEntry();
        account.setUbiProfileId("ubiProfileId1");
        account.setEmail("email");
        account.setEncodedPassword("encodedPassword");
        account.setUbiSessionId("ubiSessionId");
        account.setUbiSpaceId("ubiSpaceId");
        account.setUbiAuthTicket("ubiAuthTicket");
        account.setUbiRememberDeviceTicket("ubiRememberDeviceTicket");
        account.setUbiRememberMeTicket("ubiRememberMeTicket");

        assertThrows(TelegramUserDoesntExistException.class, () -> ubiAccountEntryEntityMapper.createEntity("chatId", account));
    }
}