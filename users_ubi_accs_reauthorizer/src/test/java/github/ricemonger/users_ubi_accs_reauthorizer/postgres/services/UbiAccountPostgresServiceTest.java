package github.ricemonger.users_ubi_accs_reauthorizer.postgres.services;

import github.ricemonger.users_ubi_accs_reauthorizer.postgres.entities.UbiAccountEntryCredentialsEntity;
import github.ricemonger.users_ubi_accs_reauthorizer.postgres.entities.UbiAccountEntryEntity;
import github.ricemonger.users_ubi_accs_reauthorizer.postgres.entities.UbiAccountStatsIdEntity;
import github.ricemonger.users_ubi_accs_reauthorizer.postgres.repositories.UbiAccountEntryCredentialsPostgresRepository;
import github.ricemonger.users_ubi_accs_reauthorizer.postgres.repositories.UbiAccountEntryPostgresRepository;
import github.ricemonger.users_ubi_accs_reauthorizer.postgres.services.entity_mappers.user.UbiAccountEntryEntityMapper;
import github.ricemonger.users_ubi_accs_reauthorizer.services.DTOs.UserUbiCredentials;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UbiAccountPostgresServiceTest {
    @Autowired
    private UbiAccountPostgresService ubiAccountPostgresService;
    @MockBean
    private UbiAccountEntryPostgresRepository ubiAccountEntryPostgresRepository;
    @MockBean
    private UbiAccountEntryCredentialsPostgresRepository ubiAccountEntryCredentialsRepository;
    @MockBean
    private UbiAccountEntryEntityMapper ubiAccountEntryEntityMapper;

    @Test
    public void saveAuthorizationInfo_should_throw_exception_when_user_doesnt_exist() {
        assertThrows(TelegramUserDoesntExistException.class, () -> ubiAccountPostgresService.saveAuthorizationInfo(1L, "email", new AuthorizationDTO()));
    }

    @Test
    public void saveAuthorizationInfo_should_throw_exception_when_user_has_another_ubi_account() {
        UbiAccountEntryEntity entity = new UbiAccountEntryEntity();
        entity.setUbiAccountStats(new UbiAccountStatsIdEntity("profileId1"));

        when(ubiAccountEntryPostgresRepository.findById(any())).thenReturn(Optional.of(entity));

        AuthorizationDTO authDTO = new AuthorizationDTO();
        authDTO.setProfileId("profileId2");

        assertThrows(UbiAccountEntryAlreadyExistsException.class, () -> ubiAccountPostgresService.saveAuthorizationInfo(1L, "email", authDTO));
    }

    @Test
    public void saveAuthorizationInfo_should_save_new_authorization_dto_if_same_ubi_acc() {
        UbiAccountEntryEntity entity = Mockito.mock(UbiAccountEntryEntity.class);
        when(entity.getUbiProfileId_()).thenReturn("profileId1");

        when(ubiAccountEntryPostgresRepository.findById(any())).thenReturn(Optional.of(entity));

        AuthorizationDTO authDTO = new AuthorizationDTO();
        authDTO.setProfileId("profileId1");

        ubiAccountPostgresService.saveAuthorizationInfo(1L, "email", authDTO);

        verify(entity).setAuthorizationDTOFields(authDTO);

        verify(ubiAccountEntryPostgresRepository).save(entity);
    }

    @Test
    public void findAllUsersUbiCredentials_should_return_all_mapped_users_ubi_credentials() {
        UbiAccountEntryCredentialsEntity entity1 = Mockito.mock(UbiAccountEntryCredentialsEntity.class);
        UbiAccountEntryCredentialsEntity entity2 = Mockito.mock(UbiAccountEntryCredentialsEntity.class);

        when(ubiAccountEntryCredentialsRepository.findAll()).thenReturn(java.util.List.of(entity1, entity2));

        UserUbiCredentials userUbiCredentials1 = Mockito.mock(UserUbiCredentials.class);
        UserUbiCredentials userUbiCredentials2 = Mockito.mock(UserUbiCredentials.class);

        when(ubiAccountEntryEntityMapper.createUserUbiCredentials(entity1)).thenReturn(userUbiCredentials1);
        when(ubiAccountEntryEntityMapper.createUserUbiCredentials(entity2)).thenReturn(userUbiCredentials2);

        List<UserUbiCredentials> result = ubiAccountPostgresService.findAllUsersUbiCredentials();

        assertEquals(2, result.size());
        assertTrue(result.contains(userUbiCredentials1));
        assertTrue(result.contains(userUbiCredentials2));
    }
}