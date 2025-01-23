package github.ricemonger.fast_sell_trade_manager.postgres.services.entity_mappers.fetch;

import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import github.ricemonger.utilspostgresschema.full_entities.fetch.FetchingUbiAccountAuthorizationEntryEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class FetchingUbiAccountAuthorizationEntryEntityMapperTest {
    @Autowired
    private FetchingUbiAccountAuthorizationEntryEntityMapper fetchingUbiAccountAuthorizationEntryEntityMapper;

    @Test
    public void createAuthorizationDTO_should_return_expected_result() {
        FetchingUbiAccountAuthorizationEntryEntity entity = new FetchingUbiAccountAuthorizationEntryEntity();
        entity.setUbiAuthTicket("ubiAuthTicket");
        entity.setUbiProfileId("ubiProfileId");
        entity.setUbiSpaceId("ubiSpaceId");
        entity.setUbiSessionId("ubiSessionId");
        entity.setUbiRememberDeviceTicket("ubiRememberDeviceTicket");
        entity.setUbiRememberMeTicket("ubiRememberMeTicket");

        AuthorizationDTO dto = fetchingUbiAccountAuthorizationEntryEntityMapper.createAuthorizationDTO(entity);

        assertEquals(entity.getUbiAuthTicket(), dto.getTicket());
        assertEquals(entity.getUbiProfileId(), dto.getProfileId());
        assertEquals(entity.getUbiSpaceId(), dto.getSpaceId());
        assertEquals(entity.getUbiSessionId(), dto.getSessionId());
        assertEquals(entity.getUbiRememberDeviceTicket(), dto.getRememberDeviceTicket());
        assertEquals(entity.getUbiRememberMeTicket(), dto.getRememberMeTicket());
    }
}