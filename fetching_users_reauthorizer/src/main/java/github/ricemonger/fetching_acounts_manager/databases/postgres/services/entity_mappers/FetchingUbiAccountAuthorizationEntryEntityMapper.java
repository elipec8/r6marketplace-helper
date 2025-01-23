package github.ricemonger.fetching_acounts_manager.databases.postgres.services.entity_mappers;

import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import github.ricemonger.utilspostgresschema.full_entities.fetch.FetchingUbiAccountAuthorizationEntryEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class FetchingUbiAccountAuthorizationEntryEntityMapper {

    public List<FetchingUbiAccountAuthorizationEntryEntity> mapToEntities(Collection<AuthorizationDTO> authorizedUbiUserList) {
        return authorizedUbiUserList.stream().map(
                dto -> new FetchingUbiAccountAuthorizationEntryEntity(
                        dto.getProfileId(),
                        dto.getSessionId(),
                        dto.getSpaceId(),
                        dto.getTicket(),
                        dto.getRememberDeviceTicket(),
                        dto.getRememberMeTicket()
                )
        ).toList();
    }
}
