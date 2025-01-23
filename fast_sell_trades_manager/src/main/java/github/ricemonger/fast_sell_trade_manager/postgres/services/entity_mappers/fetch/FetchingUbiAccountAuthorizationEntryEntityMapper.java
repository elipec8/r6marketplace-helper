package github.ricemonger.fast_sell_trade_manager.postgres.services.entity_mappers.fetch;

import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import github.ricemonger.utilspostgresschema.full_entities.fetch.FetchingUbiAccountAuthorizationEntryEntity;
import org.springframework.stereotype.Service;

@Service
public class FetchingUbiAccountAuthorizationEntryEntityMapper {

    public AuthorizationDTO createAuthorizationDTO(FetchingUbiAccountAuthorizationEntryEntity entity) {
        AuthorizationDTO dto = new AuthorizationDTO();
        dto.setTicket(entity.getUbiAuthTicket());
        dto.setProfileId(entity.getUbiProfileId());
        dto.setSpaceId(entity.getUbiSpaceId());
        dto.setSessionId(entity.getUbiSessionId());
        dto.setRememberDeviceTicket(entity.getUbiRememberDeviceTicket());
        dto.setRememberMeTicket(entity.getUbiRememberMeTicket());

        return dto;
    }
}
