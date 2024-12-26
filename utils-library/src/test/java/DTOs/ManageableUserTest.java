package DTOs;

import github.ricemonger.utils.DTOs.personal.ManageableUser;
import github.ricemonger.utils.DTOs.personal.UbiAccountStatsEntityDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ManageableUserTest {

    @Test
    public void getUbiProfileId_should_return_ubiProfileId_from_ubiAccountStatsEntityDTO() {
        ManageableUser manageableUser = new ManageableUser();
        UbiAccountStatsEntityDTO ubiAccountStatsEntityDTO = new UbiAccountStatsEntityDTO();
        ubiAccountStatsEntityDTO.setUbiProfileId("test");
        manageableUser.setUbiAccountStatsEntityDTO(ubiAccountStatsEntityDTO);

        assertEquals("test", manageableUser.getUbiProfileId());
    }

    @Test
    public void getUbiProfileId_should_return_null_when_ubiAccountStatsEntityDTO_is_null() {
        ManageableUser manageableUser = new ManageableUser();
        manageableUser.setUbiAccountStatsEntityDTO(null);

        assertNull(manageableUser.getUbiProfileId());
    }

}