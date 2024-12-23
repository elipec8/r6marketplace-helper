package github.ricemonger.utils.DTOs;

import github.ricemonger.utils.DTOs.personal.UbiAccountAuthorizationEntry;
import github.ricemonger.utils.DTOs.personal.UbiAccountAuthorizationEntryWithTelegram;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UbiAccountAuthorizationEntryWithTelegramTest {

    @Test
    public void getEmail_should_return_email_from_ubiAccountAuthorizationEntry() {
        UbiAccountAuthorizationEntryWithTelegram ubiAccountAuthorizationEntryWithTelegram = new UbiAccountAuthorizationEntryWithTelegram();
        UbiAccountAuthorizationEntry ubiAccountAuthorizationEntry = new UbiAccountAuthorizationEntry();
        ubiAccountAuthorizationEntry.setEmail("testEmail");
        ubiAccountAuthorizationEntryWithTelegram.setUbiAccountAuthorizationEntry(ubiAccountAuthorizationEntry);

        assertEquals("testEmail", ubiAccountAuthorizationEntryWithTelegram.getEmail());
    }

    @Test
    public void getEmail_should_return_null_if_ubiAccountAuthorizationEntry_is_null() {
        UbiAccountAuthorizationEntryWithTelegram ubiAccountAuthorizationEntryWithTelegram = new UbiAccountAuthorizationEntryWithTelegram();
        ubiAccountAuthorizationEntryWithTelegram.setUbiAccountAuthorizationEntry(null);

        assertNull(ubiAccountAuthorizationEntryWithTelegram.getEmail());
    }

    @Test
    public void getEncodedPassword_should_return_encodedPassword_from_ubiAccountAuthorizationEntry() {
        UbiAccountAuthorizationEntryWithTelegram ubiAccountAuthorizationEntryWithTelegram = new UbiAccountAuthorizationEntryWithTelegram();
        UbiAccountAuthorizationEntry ubiAccountAuthorizationEntry = new UbiAccountAuthorizationEntry();
        ubiAccountAuthorizationEntry.setEncodedPassword("testEncodedPassword");
        ubiAccountAuthorizationEntryWithTelegram.setUbiAccountAuthorizationEntry(ubiAccountAuthorizationEntry);

        assertEquals("testEncodedPassword", ubiAccountAuthorizationEntryWithTelegram.getEncodedPassword());
    }

    @Test
    public void getEncodedPassword_should_return_null_if_ubiAccountAuthorizationEntry_is_null() {
        UbiAccountAuthorizationEntryWithTelegram ubiAccountAuthorizationEntryWithTelegram = new UbiAccountAuthorizationEntryWithTelegram();
        ubiAccountAuthorizationEntryWithTelegram.setUbiAccountAuthorizationEntry(null);

        assertNull(ubiAccountAuthorizationEntryWithTelegram.getEncodedPassword());
    }

    @Test
    public void getUbiProfileId_should_return_ubiProfileId_from_ubiAccountAuthorizationEntry() {
        UbiAccountAuthorizationEntryWithTelegram ubiAccountAuthorizationEntryWithTelegram = new UbiAccountAuthorizationEntryWithTelegram();
        UbiAccountAuthorizationEntry ubiAccountAuthorizationEntry = new UbiAccountAuthorizationEntry();
        ubiAccountAuthorizationEntry.setUbiProfileId("testUbiProfileId");
        ubiAccountAuthorizationEntryWithTelegram.setUbiAccountAuthorizationEntry(ubiAccountAuthorizationEntry);

        assertEquals("testUbiProfileId", ubiAccountAuthorizationEntryWithTelegram.getUbiProfileId());
    }

    @Test
    public void getUbiProfileId_should_return_null_if_ubiAccountAuthorizationEntry_is_null() {
        UbiAccountAuthorizationEntryWithTelegram ubiAccountAuthorizationEntryWithTelegram = new UbiAccountAuthorizationEntryWithTelegram();
        ubiAccountAuthorizationEntryWithTelegram.setUbiAccountAuthorizationEntry(null);

        assertNull(ubiAccountAuthorizationEntryWithTelegram.getUbiProfileId());
    }

    @Test
    public void getUbiSessionId_should_return_ubiSessionId_from_ubiAccountAuthorizationEntry() {
        UbiAccountAuthorizationEntryWithTelegram ubiAccountAuthorizationEntryWithTelegram = new UbiAccountAuthorizationEntryWithTelegram();
        UbiAccountAuthorizationEntry ubiAccountAuthorizationEntry = new UbiAccountAuthorizationEntry();
        ubiAccountAuthorizationEntry.setUbiSessionId("testUbiSessionId");
        ubiAccountAuthorizationEntryWithTelegram.setUbiAccountAuthorizationEntry(ubiAccountAuthorizationEntry);

        assertEquals("testUbiSessionId", ubiAccountAuthorizationEntryWithTelegram.getUbiSessionId());
    }

    @Test
    public void getUbiSessionId_should_return_null_if_ubiAccountAuthorizationEntry_is_null() {
        UbiAccountAuthorizationEntryWithTelegram ubiAccountAuthorizationEntryWithTelegram = new UbiAccountAuthorizationEntryWithTelegram();
        ubiAccountAuthorizationEntryWithTelegram.setUbiAccountAuthorizationEntry(null);

        assertNull(ubiAccountAuthorizationEntryWithTelegram.getUbiSessionId());
    }

    @Test
    public void getUbiSpaceId_should_return_ubiSpaceId_from_ubiAccountAuthorizationEntry() {
        UbiAccountAuthorizationEntryWithTelegram ubiAccountAuthorizationEntryWithTelegram = new UbiAccountAuthorizationEntryWithTelegram();
        UbiAccountAuthorizationEntry ubiAccountAuthorizationEntry = new UbiAccountAuthorizationEntry();
        ubiAccountAuthorizationEntry.setUbiSpaceId("testUbiSpaceId");
        ubiAccountAuthorizationEntryWithTelegram.setUbiAccountAuthorizationEntry(ubiAccountAuthorizationEntry);

        assertEquals("testUbiSpaceId", ubiAccountAuthorizationEntryWithTelegram.getUbiSpaceId());
    }

    @Test
    public void getUbiSpaceId_should_return_null_if_ubiAccountAuthorizationEntry_is_null() {
        UbiAccountAuthorizationEntryWithTelegram ubiAccountAuthorizationEntryWithTelegram = new UbiAccountAuthorizationEntryWithTelegram();
        ubiAccountAuthorizationEntryWithTelegram.setUbiAccountAuthorizationEntry(null);

        assertNull(ubiAccountAuthorizationEntryWithTelegram.getUbiSpaceId());
    }

    @Test
    public void getUbiAuthTicket_should_return_ubiAuthTicket_from_ubiAccountAuthorizationEntry() {
        UbiAccountAuthorizationEntryWithTelegram ubiAccountAuthorizationEntryWithTelegram = new UbiAccountAuthorizationEntryWithTelegram();
        UbiAccountAuthorizationEntry ubiAccountAuthorizationEntry = new UbiAccountAuthorizationEntry();
        ubiAccountAuthorizationEntry.setUbiAuthTicket("testUbiAuthTicket");
        ubiAccountAuthorizationEntryWithTelegram.setUbiAccountAuthorizationEntry(ubiAccountAuthorizationEntry);

        assertEquals("testUbiAuthTicket", ubiAccountAuthorizationEntryWithTelegram.getUbiAuthTicket());
    }

    @Test
    public void getUbiAuthTicket_should_return_null_if_ubiAccountAuthorizationEntry_is_null() {
        UbiAccountAuthorizationEntryWithTelegram ubiAccountAuthorizationEntryWithTelegram = new UbiAccountAuthorizationEntryWithTelegram();
        ubiAccountAuthorizationEntryWithTelegram.setUbiAccountAuthorizationEntry(null);

        assertNull(ubiAccountAuthorizationEntryWithTelegram.getUbiAuthTicket());
    }

    @Test
    public void getUbiRememberDeviceTicket_should_return_ubiRememberDeviceTicket_from_ubiAccountAuthorizationEntry() {
        UbiAccountAuthorizationEntryWithTelegram ubiAccountAuthorizationEntryWithTelegram = new UbiAccountAuthorizationEntryWithTelegram();
        UbiAccountAuthorizationEntry ubiAccountAuthorizationEntry = new UbiAccountAuthorizationEntry();
        ubiAccountAuthorizationEntry.setUbiRememberDeviceTicket("testUbiRememberDeviceTicket");
        ubiAccountAuthorizationEntryWithTelegram.setUbiAccountAuthorizationEntry(ubiAccountAuthorizationEntry);

        assertEquals("testUbiRememberDeviceTicket", ubiAccountAuthorizationEntryWithTelegram.getUbiRememberDeviceTicket());
    }

    @Test
    public void getUbiRememberDeviceTicket_should_return_null_if_ubiAccountAuthorizationEntry_is_null() {
        UbiAccountAuthorizationEntryWithTelegram ubiAccountAuthorizationEntryWithTelegram = new UbiAccountAuthorizationEntryWithTelegram();
        ubiAccountAuthorizationEntryWithTelegram.setUbiAccountAuthorizationEntry(null);

        assertNull(ubiAccountAuthorizationEntryWithTelegram.getUbiRememberDeviceTicket());
    }

    @Test
    public void getUbiRememberMeTicket_should_return_ubiRememberMeTicket_from_ubiAccountAuthorizationEntry() {
        UbiAccountAuthorizationEntryWithTelegram ubiAccountAuthorizationEntryWithTelegram = new UbiAccountAuthorizationEntryWithTelegram();
        UbiAccountAuthorizationEntry ubiAccountAuthorizationEntry = new UbiAccountAuthorizationEntry();
        ubiAccountAuthorizationEntry.setUbiRememberMeTicket("testUbiRememberMeTicket");
        ubiAccountAuthorizationEntryWithTelegram.setUbiAccountAuthorizationEntry(ubiAccountAuthorizationEntry);

        assertEquals("testUbiRememberMeTicket", ubiAccountAuthorizationEntryWithTelegram.getUbiRememberMeTicket());
    }

    @Test
    public void getUbiRememberMeTicket_should_return_null_if_ubiAccountAuthorizationEntry_is_null() {
        UbiAccountAuthorizationEntryWithTelegram ubiAccountAuthorizationEntryWithTelegram = new UbiAccountAuthorizationEntryWithTelegram();
        ubiAccountAuthorizationEntryWithTelegram.setUbiAccountAuthorizationEntry(null);

        assertNull(ubiAccountAuthorizationEntryWithTelegram.getUbiRememberMeTicket());
    }
}