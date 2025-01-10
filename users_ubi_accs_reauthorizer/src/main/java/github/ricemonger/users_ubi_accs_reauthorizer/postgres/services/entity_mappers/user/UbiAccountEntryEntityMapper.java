package github.ricemonger.users_ubi_accs_reauthorizer.postgres.services.entity_mappers.user;

import github.ricemonger.users_ubi_accs_reauthorizer.postgres.dto_projections.UserUbiAccountAuthorizedProjection;
import github.ricemonger.users_ubi_accs_reauthorizer.postgres.dto_projections.UserUbiAccountCredentialsProjection;
import github.ricemonger.users_ubi_accs_reauthorizer.postgres.dto_projections.UserUnauthorizedUbiAccountProjection;
import github.ricemonger.users_ubi_accs_reauthorizer.services.DTOs.UserUbiAccountCredentials;
import github.ricemonger.users_ubi_accs_reauthorizer.services.DTOs.UserUnauthorizedUbiAccount;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UbiAccountEntryEntityMapper {

    public UserUbiAccountCredentials createUserUbiAccountCredentials(UserUbiAccountCredentialsProjection projection) {
        return new UserUbiAccountCredentials(
                projection.getUserId(),
                projection.getEmail(),
                projection.getEncodedPassword(),
                projection.getUbiAuthTicket(),
                projection.getUbiRememberDeviceTicket());
    }

    public UserUbiAccountAuthorizedProjection createUserUbiAccountAuthorizedProjection(Long userId, String email, AuthorizationDTO authDTO) {
        return new UserUbiAccountAuthorizedProjection(
                userId,
                email,
                authDTO.getSessionId(),
                authDTO.getSpaceId(),
                authDTO.getTicket(),
                authDTO.getRememberDeviceTicket(),
                authDTO.getRememberMeTicket());
    }

    public UserUnauthorizedUbiAccountProjection createUnauthorizedAccountProjection(UserUnauthorizedUbiAccount userUnauthorizedUbiAccount) {
        return new UserUnauthorizedUbiAccountProjection(userUnauthorizedUbiAccount.getId(), userUnauthorizedUbiAccount.getEmail());
    }
}
