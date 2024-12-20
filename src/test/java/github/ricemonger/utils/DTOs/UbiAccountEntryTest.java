package github.ricemonger.utils.DTOs;

import github.ricemonger.utils.DTOs.personal.UbiAccountAuthorizationEntry;
import github.ricemonger.utils.DTOs.personal.UbiAccountEntry;
import github.ricemonger.utils.DTOs.personal.UbiAccountStatsEntityDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UbiAccountEntryTest {

    @Test
    public void getEmail_should_return_email() {
        UbiAccountEntry ubiAccountEntry = new UbiAccountEntry();
        UbiAccountAuthorizationEntry ubiAccountAuthorizationEntry = new UbiAccountAuthorizationEntry();
        ubiAccountAuthorizationEntry.setEmail("testEmail");
        ubiAccountEntry.setUbiAccountAuthorizationEntry(ubiAccountAuthorizationEntry);
        assertEquals("testEmail", ubiAccountEntry.getEmail());
    }

    @Test
    public void getEmail_should_return_null_if_ubiAccountAuthorizationEntry_is_null() {
        UbiAccountEntry ubiAccountEntry = new UbiAccountEntry();
        ubiAccountEntry.setUbiAccountAuthorizationEntry(null);
        assertNull(ubiAccountEntry.getEmail());
    }

    @Test
    public void getEncodedPassword_should_return_encoded_password() {
        UbiAccountEntry ubiAccountEntry = new UbiAccountEntry();
        UbiAccountAuthorizationEntry ubiAccountAuthorizationEntry = new UbiAccountAuthorizationEntry();
        ubiAccountAuthorizationEntry.setEncodedPassword("testEncodedPassword");
        ubiAccountEntry.setUbiAccountAuthorizationEntry(ubiAccountAuthorizationEntry);
        assertEquals("testEncodedPassword", ubiAccountEntry.getEncodedPassword());
    }

    @Test
    public void getEncodedPassword_should_return_null_if_ubiAccountAuthorizationEntry_is_null() {
        UbiAccountEntry ubiAccountEntry = new UbiAccountEntry();
        ubiAccountEntry.setUbiAccountAuthorizationEntry(null);
        assertNull(ubiAccountEntry.getEncodedPassword());
    }

    @Test
    public void getAuthorizationEntryProfileId_should_return_ubiProfileId() {
        UbiAccountEntry ubiAccountEntry = new UbiAccountEntry();
        UbiAccountAuthorizationEntry ubiAccountAuthorizationEntry = new UbiAccountAuthorizationEntry();
        ubiAccountAuthorizationEntry.setUbiProfileId("testUbiProfileId");
        ubiAccountEntry.setUbiAccountAuthorizationEntry(ubiAccountAuthorizationEntry);
        assertEquals("testUbiProfileId", ubiAccountEntry.getAuthorizationEntryProfileId());
    }

    @Test
    public void getAuthorizationEntryProfileId_should_return_null_if_ubiAccountAuthorizationEntry_is_null() {
        UbiAccountEntry ubiAccountEntry = new UbiAccountEntry();
        ubiAccountEntry.setUbiAccountAuthorizationEntry(null);
        assertNull(ubiAccountEntry.getAuthorizationEntryProfileId());
    }

    @Test
    public void getUbiSessionId_should_return_ubiSessionId() {
        UbiAccountEntry ubiAccountEntry = new UbiAccountEntry();
        UbiAccountAuthorizationEntry ubiAccountAuthorizationEntry = new UbiAccountAuthorizationEntry();
        ubiAccountAuthorizationEntry.setUbiSessionId("testUbiSessionId");
        ubiAccountEntry.setUbiAccountAuthorizationEntry(ubiAccountAuthorizationEntry);
        assertEquals("testUbiSessionId", ubiAccountEntry.getUbiSessionId());
    }

    @Test
    public void getUbiSessionId_should_return_null_if_ubiAccountAuthorizationEntry_is_null() {
        UbiAccountEntry ubiAccountEntry = new UbiAccountEntry();
        ubiAccountEntry.setUbiAccountAuthorizationEntry(null);
        assertNull(ubiAccountEntry.getUbiSessionId());
    }

    @Test
    public void getUbiSpaceId_should_return_ubiSpaceId() {
        UbiAccountEntry ubiAccountEntry = new UbiAccountEntry();
        UbiAccountAuthorizationEntry ubiAccountAuthorizationEntry = new UbiAccountAuthorizationEntry();
        ubiAccountAuthorizationEntry.setUbiSpaceId("testUbiSpaceId");
        ubiAccountEntry.setUbiAccountAuthorizationEntry(ubiAccountAuthorizationEntry);
        assertEquals("testUbiSpaceId", ubiAccountEntry.getUbiSpaceId());
    }

    @Test
    public void getUbiSpaceId_should_return_null_if_ubiAccountAuthorizationEntry_is_null() {
        UbiAccountEntry ubiAccountEntry = new UbiAccountEntry();
        ubiAccountEntry.setUbiAccountAuthorizationEntry(null);
        assertNull(ubiAccountEntry.getUbiSpaceId());
    }

    @Test
    public void getUbiAuthTicket_should_return_ubiAuthTicket() {
        UbiAccountEntry ubiAccountEntry = new UbiAccountEntry();
        UbiAccountAuthorizationEntry ubiAccountAuthorizationEntry = new UbiAccountAuthorizationEntry();
        ubiAccountAuthorizationEntry.setUbiAuthTicket("testUbiAuthTicket");
        ubiAccountEntry.setUbiAccountAuthorizationEntry(ubiAccountAuthorizationEntry);
        assertEquals("testUbiAuthTicket", ubiAccountEntry.getUbiAuthTicket());
    }

    @Test
    public void getUbiAuthTicket_should_return_null_if_ubiAccountAuthorizationEntry_is_null() {
        UbiAccountEntry ubiAccountEntry = new UbiAccountEntry();
        ubiAccountEntry.setUbiAccountAuthorizationEntry(null);
        assertNull(ubiAccountEntry.getUbiAuthTicket());
    }

    @Test
    public void getUbiRememberDeviceTicket_should_return_ubiRememberDeviceTicket() {
        UbiAccountEntry ubiAccountEntry = new UbiAccountEntry();
        UbiAccountAuthorizationEntry ubiAccountAuthorizationEntry = new UbiAccountAuthorizationEntry();
        ubiAccountAuthorizationEntry.setUbiRememberDeviceTicket("testUbiRememberDeviceTicket");
        ubiAccountEntry.setUbiAccountAuthorizationEntry(ubiAccountAuthorizationEntry);
        assertEquals("testUbiRememberDeviceTicket", ubiAccountEntry.getUbiRememberDeviceTicket());
    }

    @Test
    public void getUbiRememberDeviceTicket_should_return_null_if_ubiAccountAuthorizationEntry_is_null() {
        UbiAccountEntry ubiAccountEntry = new UbiAccountEntry();
        ubiAccountEntry.setUbiAccountAuthorizationEntry(null);
        assertNull(ubiAccountEntry.getUbiRememberDeviceTicket());
    }

    @Test
    public void getUbiRememberMeTicket_should_return_ubiRememberMeTicket() {
        UbiAccountEntry ubiAccountEntry = new UbiAccountEntry();
        UbiAccountAuthorizationEntry ubiAccountAuthorizationEntry = new UbiAccountAuthorizationEntry();
        ubiAccountAuthorizationEntry.setUbiRememberMeTicket("testUbiRememberMeTicket");
        ubiAccountEntry.setUbiAccountAuthorizationEntry(ubiAccountAuthorizationEntry);
        assertEquals("testUbiRememberMeTicket", ubiAccountEntry.getUbiRememberMeTicket());
    }

    @Test
    public void getUbiRememberMeTicket_should_return_null_if_ubiAccountAuthorizationEntry_is_null() {
        UbiAccountEntry ubiAccountEntry = new UbiAccountEntry();
        ubiAccountEntry.setUbiAccountAuthorizationEntry(null);
        assertNull(ubiAccountEntry.getUbiRememberMeTicket());
    }

    @Test
    public void getUbiProfileId_should_return_ubiProfileId() {
        UbiAccountEntry ubiAccountEntry = new UbiAccountEntry();
        UbiAccountStatsEntityDTO ubiAccountStatsEntityDTO = new UbiAccountStatsEntityDTO();
        ubiAccountStatsEntityDTO.setUbiProfileId("testUbiProfileId");
        ubiAccountEntry.setUbiAccountStatsEntityDTO(ubiAccountStatsEntityDTO);
        assertEquals("testUbiProfileId", ubiAccountEntry.getUbiProfileId());
    }

    @Test
    public void getUbiProfileId_should_return_null_if_ubiAccountStatsEntityDTO_is_null() {
        UbiAccountEntry ubiAccountEntry = new UbiAccountEntry();
        ubiAccountEntry.setUbiAccountStatsEntityDTO(null);
        assertNull(ubiAccountEntry.getUbiProfileId());
    }

    @Test
    public void getCreditAmount_should_return_creditAmount() {
        UbiAccountEntry ubiAccountEntry = new UbiAccountEntry();
        UbiAccountStatsEntityDTO ubiAccountStatsEntityDTO = new UbiAccountStatsEntityDTO();
        ubiAccountStatsEntityDTO.setCreditAmount(100);
        ubiAccountEntry.setUbiAccountStatsEntityDTO(ubiAccountStatsEntityDTO);
        assertEquals(100, ubiAccountEntry.getCreditAmount());
    }

    @Test
    public void getCreditAmount_should_return_null_if_ubiAccountStatsEntityDTO_is_null() {
        UbiAccountEntry ubiAccountEntry = new UbiAccountEntry();
        ubiAccountEntry.setUbiAccountStatsEntityDTO(null);
        assertNull(ubiAccountEntry.getCreditAmount());
    }

    @Test
    public void getOwnedItemsIds_should_return_ownedItemsIds() {
        UbiAccountEntry ubiAccountEntry = new UbiAccountEntry();
        UbiAccountStatsEntityDTO ubiAccountStatsEntityDTO = new UbiAccountStatsEntityDTO();
        List<String> ownedItemsIds = List.of("testOwnedItemsId1", "testOwnedItemsId2");
        ubiAccountStatsEntityDTO.setOwnedItemsIds(ownedItemsIds);
        ubiAccountEntry.setUbiAccountStatsEntityDTO(ubiAccountStatsEntityDTO);
        assertEquals(ownedItemsIds, ubiAccountEntry.getOwnedItemsIds());
    }

    @Test
    public void getOwnedItemsIds_should_return_null_if_ubiAccountStatsEntityDTO_is_null() {
        UbiAccountEntry ubiAccountEntry = new UbiAccountEntry();
        ubiAccountEntry.setUbiAccountStatsEntityDTO(null);
        assertNull(ubiAccountEntry.getOwnedItemsIds());
    }
}