package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.utils.DTOs.UbiAccountAuthorizationEntry;
import github.ricemonger.utils.DTOs.UbiAccountAuthorizationEntryWithTelegram;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UbiAccountEntryEntityDTOEntityTest {

    @Test
    public void constructor_should_set_fields_correctly() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        UbiAccountStatsEntity ubiAccount = new UbiAccountStatsEntity();
        ubiAccount.setUbiProfileId("profile1");
        UbiAccountAuthorizationEntry account = new UbiAccountAuthorizationEntry();
        account.setEmail("email@example.com");
        account.setEncodedPassword("encodedPassword");
        account.setUbiSessionId("sessionId");
        account.setUbiSpaceId("spaceId");
        account.setUbiAuthTicket("authTicket");
        account.setUbiTwoFactorAuthTicket("twoFactorAuthTicket");
        account.setUbiRememberDeviceTicket("rememberDeviceTicket");
        account.setUbiRememberMeTicket("rememberMeTicket");

        UbiAccountEntryEntity result = new UbiAccountEntryEntity(user, ubiAccount, account);

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
        user.setPrivateNotificationsEnabledFlag(true);
        user.setTelegramUser(telegramUserEntity);
        UbiAccountStatsEntity ubiAccount = new UbiAccountStatsEntity();
        ubiAccount.setUbiProfileId("profile1");
        UbiAccountEntryEntity entity = new UbiAccountEntryEntity();
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
        assertTrue(result.getPrivateNotificationsEnabledFlag());
        assertEquals("email@example.com", result.getUbiAccountAuthorizationEntry().getEmail());
        assertEquals("encodedPassword", result.getUbiAccountAuthorizationEntry().getEncodedPassword());
        assertEquals("profile1", result.getUbiAccountAuthorizationEntry().getUbiProfileId());
        assertEquals("sessionId", result.getUbiAccountAuthorizationEntry().getUbiSessionId());
        assertEquals("spaceId", result.getUbiAccountAuthorizationEntry().getUbiSpaceId());
        assertEquals("authTicket", result.getUbiAccountAuthorizationEntry().getUbiAuthTicket());
        assertEquals("twoFactorAuthTicket", result.getUbiAccountAuthorizationEntry().getUbiTwoFactorAuthTicket());
        assertEquals("rememberDeviceTicket", result.getUbiAccountAuthorizationEntry().getUbiRememberDeviceTicket());
        assertEquals("rememberMeTicket", result.getUbiAccountAuthorizationEntry().getUbiRememberMeTicket());
    }

    @Test
    public void toUbiAccountAuthorizationEntry_should_return_correct_dto() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        UbiAccountStatsEntity ubiAccount = new UbiAccountStatsEntity();
        ubiAccount.setUbiProfileId("profile1");
        UbiAccountEntryEntity entity = new UbiAccountEntryEntity();
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

        UbiAccountAuthorizationEntry result = entity.toUbiAccountAuthorizationEntry();

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