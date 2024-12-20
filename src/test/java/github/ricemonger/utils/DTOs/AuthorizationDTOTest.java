package github.ricemonger.utils.DTOs;

import github.ricemonger.utils.DTOs.auth.AuthorizationDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthorizationDTOTest {

    @Test
    public void userForCentralTradeManager_constructor_sets_fields_correctly() {
        UserForCentralTradeManager userForCentralTradeManager = new UserForCentralTradeManager();

        UbiAccountStatsEntityDTO ubiAccountStatsEntityDTO = new UbiAccountStatsEntityDTO();
        ubiAccountStatsEntityDTO.setUbiProfileId("ubiProfileId");
        userForCentralTradeManager.setUbiAccountStats(ubiAccountStatsEntityDTO);

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
        UbiAccountStatsEntityDTO ubiAccountStatsEntityDTO = new UbiAccountStatsEntityDTO();
        ubiAccountStatsEntityDTO.setUbiProfileId("ubiProfileIdAccountStats");
        UbiAccountEntryWithTelegram ubiAccountWithTelegram = new UbiAccountEntryWithTelegram();
        UbiAccountAuthorizationEntry ubiAccountAuthorizationEntryEntityDTO = new UbiAccountAuthorizationEntry();
        ubiAccountAuthorizationEntryEntityDTO.setUbiProfileId("ubiProfileId");
        ubiAccountAuthorizationEntryEntityDTO.setUbiSessionId("ubiSessionId");
        ubiAccountAuthorizationEntryEntityDTO.setUbiSpaceId("ubiSpaceId");
        ubiAccountAuthorizationEntryEntityDTO.setUbiAuthTicket("ubiAuthTicket");
        ubiAccountAuthorizationEntryEntityDTO.setUbiTwoFactorAuthTicket("ubiTwoFactorAuthTicket");
        ubiAccountAuthorizationEntryEntityDTO.setUbiRememberDeviceTicket("ubiRememberDeviceTicket");
        ubiAccountAuthorizationEntryEntityDTO.setUbiRememberMeTicket("ubiRememberMeTicket");
        UbiAccountEntry ubiAccountEntryEntityDTO = new UbiAccountEntry();
        ubiAccountEntryEntityDTO.setUbiAccountAuthorizationEntry(ubiAccountAuthorizationEntryEntityDTO);
        ubiAccountEntryEntityDTO.setUbiAccountStatsEntityDTO(ubiAccountStatsEntityDTO);
        ubiAccountWithTelegram.setUbiAccountEntry(ubiAccountEntryEntityDTO);

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