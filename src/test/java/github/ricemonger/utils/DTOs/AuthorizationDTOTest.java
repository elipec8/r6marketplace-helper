package github.ricemonger.utils.DTOs;

import github.ricemonger.utils.UbiAccountEntry;
import github.ricemonger.utils.UbiAccountEntryWithTelegram;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthorizationDTOTest {

    @Test
    public void userForCentralTradeManager_constructor_sets_fields_correctly() {
        UserForCentralTradeManager userForCentralTradeManager = new UserForCentralTradeManager();

        UbiAccountStats ubiAccountStats = new UbiAccountStats();
        ubiAccountStats.setUbiProfileId("ubiProfileId");
        userForCentralTradeManager.setUbiAccountStats(ubiAccountStats);

        userForCentralTradeManager.setUbiSessionId("ubiSessionId");
        userForCentralTradeManager.setUbiSpaceId("ubiSpaceId");
        userForCentralTradeManager.setUbiAuthTicket("ubiAuthTicket");
        userForCentralTradeManager.setUbiTwoFactorAuthTicket("ubiTwoFactorAuthTicket");
        userForCentralTradeManager.setUbiRememberDeviceTicket("ubiRememberDeviceTicket");
        userForCentralTradeManager.setUbiRememberMeTicket("ubiRememberMeTicket");

        AuthorizationDTO authorizationDTO = new AuthorizationDTO(userForCentralTradeManager);

        assertEquals("ubiAuthTicket", authorizationDTO.getTicket());
        assertEquals("ubiProfileId", authorizationDTO.getProfileId());
        assertEquals("ubiSpaceId", authorizationDTO.getSpaceId());
        assertEquals("ubiSessionId", authorizationDTO.getSessionId());
        assertEquals("ubiTwoFactorAuthTicket", authorizationDTO.getTwoFactorAuthenticationTicket());
        assertEquals("ubiRememberDeviceTicket", authorizationDTO.getRememberDeviceTicket());
        assertEquals("ubiRememberMeTicket", authorizationDTO.getRememberMeTicket());
    }

    @Test
    public void ubiAccountEntryWithTelegram_constructor_sets_fields_correctly() {
        UbiAccountStats ubiAccountStats = new UbiAccountStats();
        ubiAccountStats.setUbiProfileId("ubiProfileIdAccountStats");
        UbiAccountEntryWithTelegram ubiAccountWithTelegram = new UbiAccountEntryWithTelegram();
        UbiAccountAuthorizationEntry ubiAccountAuthorizationEntry = new UbiAccountAuthorizationEntry();
        ubiAccountAuthorizationEntry.setUbiProfileId("ubiProfileId");
        ubiAccountAuthorizationEntry.setUbiSessionId("ubiSessionId");
        ubiAccountAuthorizationEntry.setUbiSpaceId("ubiSpaceId");
        ubiAccountAuthorizationEntry.setUbiAuthTicket("ubiAuthTicket");
        ubiAccountAuthorizationEntry.setUbiTwoFactorAuthTicket("ubiTwoFactorAuthTicket");
        ubiAccountAuthorizationEntry.setUbiRememberDeviceTicket("ubiRememberDeviceTicket");
        ubiAccountAuthorizationEntry.setUbiRememberMeTicket("ubiRememberMeTicket");
        UbiAccountEntry ubiAccountEntry = new UbiAccountEntry();
        ubiAccountEntry.setUbiAccountAuthorizationEntry(ubiAccountAuthorizationEntry);
        ubiAccountEntry.setUbiAccountStats(ubiAccountStats);
        ubiAccountWithTelegram.setUbiAccountEntry(ubiAccountEntry);

        AuthorizationDTO authorizationDTO = new AuthorizationDTO(ubiAccountWithTelegram);

        assertEquals("ubiAuthTicket", authorizationDTO.getTicket());
        assertEquals("ubiProfileId", authorizationDTO.getProfileId());
        assertEquals("ubiSpaceId", authorizationDTO.getSpaceId());
        assertEquals("ubiSessionId", authorizationDTO.getSessionId());
        assertEquals("ubiTwoFactorAuthTicket", authorizationDTO.getTwoFactorAuthenticationTicket());
        assertEquals("ubiRememberDeviceTicket", authorizationDTO.getRememberDeviceTicket());
        assertEquals("ubiRememberMeTicket", authorizationDTO.getRememberMeTicket());
    }
}