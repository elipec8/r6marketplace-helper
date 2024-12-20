package github.ricemonger.utils.DTOs;

import github.ricemonger.utils.DTOs.personal.UbiAccountAuthorizationEntry;
import github.ricemonger.utils.DTOs.personal.UbiAccountEntry;
import github.ricemonger.utils.DTOs.personal.UbiAccountEntryWithTelegram;
import github.ricemonger.utils.DTOs.personal.UbiAccountStatsEntityDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UbiAccountEntryWithTelegramTest {

    @Test
    public void getEmail_should_return_email() {
        UbiAccountEntryWithTelegram ubiAccountEntryWithTelegram = new UbiAccountEntryWithTelegram();
        UbiAccountAuthorizationEntry ubiAccountAuthorizationEntry = new UbiAccountAuthorizationEntry();
        UbiAccountStatsEntityDTO ubiAccountStatsEntityDTO = new UbiAccountStatsEntityDTO();
        ubiAccountAuthorizationEntry.setEmail("testEmail");
        UbiAccountEntry ubiAccountEntry = new UbiAccountEntry(ubiAccountAuthorizationEntry, ubiAccountStatsEntityDTO);
        ubiAccountEntryWithTelegram.setUbiAccountEntry(ubiAccountEntry);
        assertEquals("testEmail", ubiAccountEntryWithTelegram.getEmail());
    }

    @Test
    public void getEmail_should_return_null_if_ubiAccountEntry_is_null() {
        UbiAccountEntryWithTelegram ubiAccountEntryWithTelegram = new UbiAccountEntryWithTelegram();
        ubiAccountEntryWithTelegram.setUbiAccountEntry(null);
        assertNull(ubiAccountEntryWithTelegram.getEmail());
    }

    @Test
    public void getEncodedPassword_should_return_encoded_password() {
        UbiAccountEntryWithTelegram ubiAccountEntryWithTelegram = new UbiAccountEntryWithTelegram();
        UbiAccountAuthorizationEntry ubiAccountAuthorizationEntry = new UbiAccountAuthorizationEntry();
        UbiAccountStatsEntityDTO ubiAccountStatsEntityDTO = new UbiAccountStatsEntityDTO();
        ubiAccountAuthorizationEntry.setEncodedPassword("testEncodedPassword");
        UbiAccountEntry ubiAccountEntry = new UbiAccountEntry(ubiAccountAuthorizationEntry, ubiAccountStatsEntityDTO);
        ubiAccountEntryWithTelegram.setUbiAccountEntry(ubiAccountEntry);
        assertEquals("testEncodedPassword", ubiAccountEntryWithTelegram.getEncodedPassword());
    }

    @Test
    public void getEncodedPassword_should_return_null_if_ubiAccountEntry_is_null() {
        UbiAccountEntryWithTelegram ubiAccountEntryWithTelegram = new UbiAccountEntryWithTelegram();
        ubiAccountEntryWithTelegram.setUbiAccountEntry(null);
        assertNull(ubiAccountEntryWithTelegram.getEncodedPassword());
    }

    @Test
    public void getAuthorizationEntryProfileId_should_return_authorization_entry_profile_id() {
        UbiAccountEntryWithTelegram ubiAccountEntryWithTelegram = new UbiAccountEntryWithTelegram();
        UbiAccountAuthorizationEntry ubiAccountAuthorizationEntry = new UbiAccountAuthorizationEntry();
        UbiAccountStatsEntityDTO ubiAccountStatsEntityDTO = new UbiAccountStatsEntityDTO();
        ubiAccountAuthorizationEntry.setUbiProfileId("testUbiProfileId");
        UbiAccountEntry ubiAccountEntry = new UbiAccountEntry(ubiAccountAuthorizationEntry, ubiAccountStatsEntityDTO);
        ubiAccountEntryWithTelegram.setUbiAccountEntry(ubiAccountEntry);
        assertEquals("testUbiProfileId", ubiAccountEntryWithTelegram.getAuthorizationEntryProfileId());
    }

    @Test
    public void getAuthorizationEntryProfileId_should_return_null_if_ubiAccountEntry_is_null() {
        UbiAccountEntryWithTelegram ubiAccountEntryWithTelegram = new UbiAccountEntryWithTelegram();
        ubiAccountEntryWithTelegram.setUbiAccountEntry(null);
        assertNull(ubiAccountEntryWithTelegram.getAuthorizationEntryProfileId());
    }

    @Test
    public void getUbiSessionId_should_return_ubi_session_id() {
        UbiAccountEntryWithTelegram ubiAccountEntryWithTelegram = new UbiAccountEntryWithTelegram();
        UbiAccountAuthorizationEntry ubiAccountAuthorizationEntry = new UbiAccountAuthorizationEntry();
        UbiAccountStatsEntityDTO ubiAccountStatsEntityDTO = new UbiAccountStatsEntityDTO();
        ubiAccountAuthorizationEntry.setUbiSessionId("testUbiSessionId");
        UbiAccountEntry ubiAccountEntry = new UbiAccountEntry(ubiAccountAuthorizationEntry, ubiAccountStatsEntityDTO);
        ubiAccountEntryWithTelegram.setUbiAccountEntry(ubiAccountEntry);
        assertEquals("testUbiSessionId", ubiAccountEntryWithTelegram.getUbiSessionId());
    }

    @Test
    public void getUbiSessionId_should_return_null_if_ubiAccountEntry_is_null() {
        UbiAccountEntryWithTelegram ubiAccountEntryWithTelegram = new UbiAccountEntryWithTelegram();
        ubiAccountEntryWithTelegram.setUbiAccountEntry(null);
        assertNull(ubiAccountEntryWithTelegram.getUbiSessionId());
    }

    @Test
    public void getUbiSpaceId_should_return_ubi_space_id() {
        UbiAccountEntryWithTelegram ubiAccountEntryWithTelegram = new UbiAccountEntryWithTelegram();
        UbiAccountAuthorizationEntry ubiAccountAuthorizationEntry = new UbiAccountAuthorizationEntry();
        UbiAccountStatsEntityDTO ubiAccountStatsEntityDTO = new UbiAccountStatsEntityDTO();
        ubiAccountAuthorizationEntry.setUbiSpaceId("testUbiSpaceId");
        UbiAccountEntry ubiAccountEntry = new UbiAccountEntry(ubiAccountAuthorizationEntry, ubiAccountStatsEntityDTO);
        ubiAccountEntryWithTelegram.setUbiAccountEntry(ubiAccountEntry);
        assertEquals("testUbiSpaceId", ubiAccountEntryWithTelegram.getUbiSpaceId());
    }

    @Test
    public void getUbiSpaceId_should_return_null_if_ubiAccountEntry_is_null() {
        UbiAccountEntryWithTelegram ubiAccountEntryWithTelegram = new UbiAccountEntryWithTelegram();
        ubiAccountEntryWithTelegram.setUbiAccountEntry(null);
        assertNull(ubiAccountEntryWithTelegram.getUbiSpaceId());
    }

    @Test
    public void getUbiAuthTicket_should_return_ubi_auth_ticket() {
        UbiAccountEntryWithTelegram ubiAccountEntryWithTelegram = new UbiAccountEntryWithTelegram();
        UbiAccountAuthorizationEntry ubiAccountAuthorizationEntry = new UbiAccountAuthorizationEntry();
        UbiAccountStatsEntityDTO ubiAccountStatsEntityDTO = new UbiAccountStatsEntityDTO();
        ubiAccountAuthorizationEntry.setUbiAuthTicket("testUbiAuthTicket");
        UbiAccountEntry ubiAccountEntry = new UbiAccountEntry(ubiAccountAuthorizationEntry, ubiAccountStatsEntityDTO);
        ubiAccountEntryWithTelegram.setUbiAccountEntry(ubiAccountEntry);
        assertEquals("testUbiAuthTicket", ubiAccountEntryWithTelegram.getUbiAuthTicket());
    }

    @Test
    public void getUbiAuthTicket_should_return_null_if_ubiAccountEntry_is_null() {
        UbiAccountEntryWithTelegram ubiAccountEntryWithTelegram = new UbiAccountEntryWithTelegram();
        ubiAccountEntryWithTelegram.setUbiAccountEntry(null);
        assertNull(ubiAccountEntryWithTelegram.getUbiAuthTicket());
    }

    @Test
    public void getUbiRememberDeviceTicket_should_return_ubi_remember_device_ticket() {
        UbiAccountEntryWithTelegram ubiAccountEntryWithTelegram = new UbiAccountEntryWithTelegram();
        UbiAccountAuthorizationEntry ubiAccountAuthorizationEntry = new UbiAccountAuthorizationEntry();
        UbiAccountStatsEntityDTO ubiAccountStatsEntityDTO = new UbiAccountStatsEntityDTO();
        ubiAccountAuthorizationEntry.setUbiRememberDeviceTicket("testUbiRememberDeviceTicket");
        UbiAccountEntry ubiAccountEntry = new UbiAccountEntry(ubiAccountAuthorizationEntry, ubiAccountStatsEntityDTO);
        ubiAccountEntryWithTelegram.setUbiAccountEntry(ubiAccountEntry);
        assertEquals("testUbiRememberDeviceTicket", ubiAccountEntryWithTelegram.getUbiRememberDeviceTicket());
    }

    @Test
    public void getUbiRememberDeviceTicket_should_return_null_if_ubiAccountEntry_is_null() {
        UbiAccountEntryWithTelegram ubiAccountEntryWithTelegram = new UbiAccountEntryWithTelegram();
        ubiAccountEntryWithTelegram.setUbiAccountEntry(null);
        assertNull(ubiAccountEntryWithTelegram.getUbiRememberDeviceTicket());
    }

    @Test
    public void getUbiRememberMeTicket_should_return_ubi_remember_me_ticket() {
        UbiAccountEntryWithTelegram ubiAccountEntryWithTelegram = new UbiAccountEntryWithTelegram();
        UbiAccountAuthorizationEntry ubiAccountAuthorizationEntry = new UbiAccountAuthorizationEntry();
        UbiAccountStatsEntityDTO ubiAccountStatsEntityDTO = new UbiAccountStatsEntityDTO();
        ubiAccountAuthorizationEntry.setUbiRememberMeTicket("testUbiRememberMeTicket");
        UbiAccountEntry ubiAccountEntry = new UbiAccountEntry(ubiAccountAuthorizationEntry, ubiAccountStatsEntityDTO);
        ubiAccountEntryWithTelegram.setUbiAccountEntry(ubiAccountEntry);
        assertEquals("testUbiRememberMeTicket", ubiAccountEntryWithTelegram.getUbiRememberMeTicket());
    }

    @Test
    public void getUbiRememberMeTicket_should_return_null_if_ubiAccountEntry_is_null() {
        UbiAccountEntryWithTelegram ubiAccountEntryWithTelegram = new UbiAccountEntryWithTelegram();
        ubiAccountEntryWithTelegram.setUbiAccountEntry(null);
        assertNull(ubiAccountEntryWithTelegram.getUbiRememberMeTicket());
    }

    @Test
    public void getUbiProfileId_should_return_ubi_profile_id() {
        UbiAccountEntryWithTelegram ubiAccountEntryWithTelegram = new UbiAccountEntryWithTelegram();
        UbiAccountAuthorizationEntry ubiAccountAuthorizationEntry = new UbiAccountAuthorizationEntry();
        UbiAccountStatsEntityDTO ubiAccountStatsEntityDTO = new UbiAccountStatsEntityDTO();
        ubiAccountStatsEntityDTO.setUbiProfileId("testUbiProfileId");
        UbiAccountEntry ubiAccountEntry = new UbiAccountEntry(ubiAccountAuthorizationEntry, ubiAccountStatsEntityDTO);
        ubiAccountEntryWithTelegram.setUbiAccountEntry(ubiAccountEntry);
        assertEquals("testUbiProfileId", ubiAccountEntryWithTelegram.getUbiProfileId());
    }

    @Test
    public void getUbiProfileId_should_return_null_if_ubiAccountEntry_is_null() {
        UbiAccountEntryWithTelegram ubiAccountEntryWithTelegram = new UbiAccountEntryWithTelegram();
        ubiAccountEntryWithTelegram.setUbiAccountEntry(null);
        assertNull(ubiAccountEntryWithTelegram.getUbiProfileId());
    }

    @Test
    public void getCreditAmount_should_return_credit_amount() {
        UbiAccountEntryWithTelegram ubiAccountEntryWithTelegram = new UbiAccountEntryWithTelegram();
        UbiAccountAuthorizationEntry ubiAccountAuthorizationEntry = new UbiAccountAuthorizationEntry();
        UbiAccountStatsEntityDTO ubiAccountStatsEntityDTO = new UbiAccountStatsEntityDTO();
        ubiAccountStatsEntityDTO.setCreditAmount(100);
        UbiAccountEntry ubiAccountEntry = new UbiAccountEntry(ubiAccountAuthorizationEntry, ubiAccountStatsEntityDTO);
        ubiAccountEntryWithTelegram.setUbiAccountEntry(ubiAccountEntry);
        assertEquals(100, ubiAccountEntryWithTelegram.getCreditAmount());
    }

    @Test
    public void getCreditAmount_should_return_null_if_ubiAccountEntry_is_null() {
        UbiAccountEntryWithTelegram ubiAccountEntryWithTelegram = new UbiAccountEntryWithTelegram();
        ubiAccountEntryWithTelegram.setUbiAccountEntry(null);
        assertNull(ubiAccountEntryWithTelegram.getCreditAmount());
    }

    @Test
    public void getOwnedItemsIds_should_return_owned_items_ids() {
        UbiAccountEntryWithTelegram ubiAccountEntryWithTelegram = new UbiAccountEntryWithTelegram();
        UbiAccountAuthorizationEntry ubiAccountAuthorizationEntry = new UbiAccountAuthorizationEntry();
        UbiAccountStatsEntityDTO ubiAccountStatsEntityDTO = new UbiAccountStatsEntityDTO();
        ubiAccountStatsEntityDTO.setOwnedItemsIds(List.of("testOwnedItemsId1", "testOwnedItemsId2"));
        UbiAccountEntry ubiAccountEntry = new UbiAccountEntry(ubiAccountAuthorizationEntry, ubiAccountStatsEntityDTO);
        ubiAccountEntryWithTelegram.setUbiAccountEntry(ubiAccountEntry);
        assertEquals(List.of("testOwnedItemsId1", "testOwnedItemsId2"), ubiAccountEntryWithTelegram.getOwnedItemsIds());
    }

    @Test
    public void getOwnedItemsIds_should_return_null_if_ubiAccountEntry_is_null() {
        UbiAccountEntryWithTelegram ubiAccountEntryWithTelegram = new UbiAccountEntryWithTelegram();
        ubiAccountEntryWithTelegram.setUbiAccountEntry(null);
        assertNull(ubiAccountEntryWithTelegram.getOwnedItemsIds());
    }
}