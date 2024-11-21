package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.utils.DTOs.UbiAccountAuthorizationDTO;
import github.ricemonger.utils.DTOs.UbiAccountAuthorizationEntryWithTelegram;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UbiAccountAuthorizationEntryEntityTest {

    @Test
    public void constructor_should_set_fields_correctly() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        UbiAccountStatsEntity ubiAccount = new UbiAccountStatsEntity();
        ubiAccount.setUbiProfileId("profile1");
        UbiAccountAuthorizationDTO account = new UbiAccountAuthorizationDTO();
        account.setEmail("email@example.com");
        account.setEncodedPassword("encodedPassword");
        account.setUbiSessionId("sessionId");
        account.setUbiSpaceId("spaceId");
        account.setUbiAuthTicket("authTicket");
        account.setUbiTwoFactorAuthTicket("twoFactorAuthTicket");
        account.setUbiRememberDeviceTicket("rememberDeviceTicket");
        account.setUbiRememberMeTicket("rememberMeTicket");

        UbiAccountAuthorizationEntryEntity result = new UbiAccountAuthorizationEntryEntity(user, ubiAccount, account);

        assertEquals(1L, result.getUser().getId());
        assertEquals("profile1", result.getUbiAccountStats().getUbiProfileId());
        assertEquals("email@example.com", result.getEmail());
        assertEquals("encodedPassword", result.getEncodedPassword());
        assertEquals("sessionId", result.getUbiSessionId());
        assertEquals("spaceId", result.getUbiSpaceId());
        assertEquals("authTicket", result.getUbiAuthTicket());
        assertEquals("twoFactorAuthTicket", result.getUbiTwoFactorAuthTicket());
        assertEquals("rememberDeviceTicket", result.getUbiRememberDeviceTicket());
        assertEquals("rememberMeTicket", result.getUbiRememberMeTicket());
    }

    @Test
    public void toUbiAccountAuthorizationEntryWithTelegram_should_return_correct_dto() {
        TelegramUserEntity telegramUserEntity = new TelegramUserEntity();
        telegramUserEntity.setChatId("chat1");
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setTelegramUser(telegramUserEntity);
        UbiAccountStatsEntity ubiAccount = new UbiAccountStatsEntity();
        ubiAccount.setUbiProfileId("profile1");
        UbiAccountAuthorizationEntryEntity entity = new UbiAccountAuthorizationEntryEntity();
        entity.setUser(user);
        entity.setUbiAccountStats(ubiAccount);
        entity.setEmail("email@example.com");
        entity.setEncodedPassword("encodedPassword");
        entity.setUbiSessionId("sessionId");
        entity.setUbiSpaceId("spaceId");
        entity.setUbiAuthTicket("authTicket");
        entity.setUbiTwoFactorAuthTicket("twoFactorAuthTicket");
        entity.setUbiRememberDeviceTicket("rememberDeviceTicket");
        entity.setUbiRememberMeTicket("rememberMeTicket");

        UbiAccountAuthorizationEntryWithTelegram result = entity.toUbiAccountAuthorizationEntryWithTelegram();

        assertEquals("chat1", result.getChatId());
        assertEquals("email@example.com", result.getUbiAccountEntry().getEmail());
        assertEquals("encodedPassword", result.getUbiAccountEntry().getEncodedPassword());
        assertEquals("profile1", result.getUbiAccountEntry().getUbiProfileId());
        assertEquals("sessionId", result.getUbiAccountEntry().getUbiSessionId());
        assertEquals("spaceId", result.getUbiAccountEntry().getUbiSpaceId());
        assertEquals("authTicket", result.getUbiAccountEntry().getUbiAuthTicket());
        assertEquals("twoFactorAuthTicket", result.getUbiAccountEntry().getUbiTwoFactorAuthTicket());
        assertEquals("rememberDeviceTicket", result.getUbiAccountEntry().getUbiRememberDeviceTicket());
        assertEquals("rememberMeTicket", result.getUbiAccountEntry().getUbiRememberMeTicket());
    }

    @Test
    public void toUbiAccountAuthorizationEntry_should_return_correct_dto() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        UbiAccountStatsEntity ubiAccount = new UbiAccountStatsEntity();
        ubiAccount.setUbiProfileId("profile1");
        UbiAccountAuthorizationEntryEntity entity = new UbiAccountAuthorizationEntryEntity();
        entity.setUser(user);
        entity.setUbiAccountStats(ubiAccount);
        entity.setEmail("email@example.com");
        entity.setEncodedPassword("encodedPassword");
        entity.setUbiSessionId("sessionId");
        entity.setUbiSpaceId("spaceId");
        entity.setUbiAuthTicket("authTicket");
        entity.setUbiTwoFactorAuthTicket("twoFactorAuthTicket");
        entity.setUbiRememberDeviceTicket("rememberDeviceTicket");
        entity.setUbiRememberMeTicket("rememberMeTicket");

        UbiAccountAuthorizationDTO result = entity.toUbiAccountAuthorizationEntry();

        assertEquals("email@example.com", result.getEmail());
        assertEquals("encodedPassword", result.getEncodedPassword());
        assertEquals("profile1", result.getUbiProfileId());
        assertEquals("sessionId", result.getUbiSessionId());
        assertEquals("spaceId", result.getUbiSpaceId());
        assertEquals("authTicket", result.getUbiAuthTicket());
        assertEquals("twoFactorAuthTicket", result.getUbiTwoFactorAuthTicket());
        assertEquals("rememberDeviceTicket", result.getUbiRememberDeviceTicket());
        assertEquals("rememberMeTicket", result.getUbiRememberMeTicket());
    }
}