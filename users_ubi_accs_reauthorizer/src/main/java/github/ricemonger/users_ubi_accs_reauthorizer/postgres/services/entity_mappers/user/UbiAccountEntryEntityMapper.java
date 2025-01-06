package github.ricemonger.users_ubi_accs_reauthorizer.postgres.services.entity_mappers.user;

import github.ricemonger.users_ubi_accs_reauthorizer.postgres.entities.UbiAccountEntryCredentialsEntity;
import github.ricemonger.users_ubi_accs_reauthorizer.services.DTOs.UserUbiCredentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UbiAccountEntryEntityMapper {

    public UserUbiCredentials createUserUbiCredentials(UbiAccountEntryCredentialsEntity ubiAccountEntryCredentialsEntity) {
        return new UserUbiCredentials(ubiAccountEntryCredentialsEntity.getUserId_(),
                ubiAccountEntryCredentialsEntity.getEmail(),
                ubiAccountEntryCredentialsEntity.getEncodedPassword(),
                ubiAccountEntryCredentialsEntity.getUbiRememberMeTicket());
    }
}
