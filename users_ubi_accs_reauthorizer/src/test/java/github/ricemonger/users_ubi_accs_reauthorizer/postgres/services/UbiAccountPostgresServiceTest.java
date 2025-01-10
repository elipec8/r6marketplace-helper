package github.ricemonger.users_ubi_accs_reauthorizer.postgres.services;

import github.ricemonger.users_ubi_accs_reauthorizer.postgres.dto_projections.UserUbiAccountAuthorizedProjection;
import github.ricemonger.users_ubi_accs_reauthorizer.postgres.dto_projections.UserUbiAccountCredentialsProjection;
import github.ricemonger.users_ubi_accs_reauthorizer.postgres.dto_projections.UserUnauthorizedUbiAccountProjection;
import github.ricemonger.users_ubi_accs_reauthorizer.postgres.repositories.UbiAccountEntryPostgresRepository;
import github.ricemonger.users_ubi_accs_reauthorizer.postgres.repositories.UbiAccountStatsPostgresRepository;
import github.ricemonger.users_ubi_accs_reauthorizer.postgres.services.entity_mappers.user.UbiAccountEntryEntityMapper;
import github.ricemonger.users_ubi_accs_reauthorizer.services.DTOs.UserUnauthorizedUbiAccount;
import github.ricemonger.users_ubi_accs_reauthorizer.services.DTOs.UserUbiAccountCredentials;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import github.ricemonger.utilspostgresschema.full_entities.user.UbiAccountStatsEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@SpringBootTest
class UbiAccountPostgresServiceTest {
    @Autowired
    private UbiAccountPostgresService ubiAccountPostgresService;
    @MockBean
    private UbiAccountEntryPostgresRepository ubiAccountEntryPostgresRepository;
    @MockBean
    private UbiAccountStatsPostgresRepository ubiAccountStatsPostgresRepository;
    @MockBean
    private UbiAccountEntryEntityMapper ubiAccountEntryEntityMapper;

    @Test
    public void updateAuthorizedUserCredntialsAndLinkUbiAccountStats_should_only_update_ubi_credentials_if_same_ubi_account_stats_linked(){
        Long userId=  1L;
        String email = "email";
        AuthorizationDTO dto = new AuthorizationDTO();
        dto.setProfileId("profileId");
        when(ubiAccountEntryPostgresRepository.findUbiAccountStatsProfileIdByUserIdAndEmail(userId, email)).thenReturn(Optional.of("profileId"));

        UserUbiAccountAuthorizedProjection proj = mock(UserUbiAccountAuthorizedProjection.class);
        when(ubiAccountEntryEntityMapper.createUserUbiAccountAuthorizedProjection(userId, email, dto)).thenReturn(proj);

        ubiAccountPostgresService.updateCredentialsAndLinkUbiAccountStatsForAuthorizedUser(1L, "email", dto);

        verify(ubiAccountEntryPostgresRepository).updateUserUbiCredentials(proj);

        verify(ubiAccountStatsPostgresRepository, never()).save(any(UbiAccountStatsEntity.class));
        verify(ubiAccountEntryPostgresRepository, never()).linkUbiAccountStatsByUserIdAndEmail(any(Long.class), any(String.class), any(String.class));
    }

    @Test
    public void saveAuthorizationInfo_should_save_new_ubi_account_stats_and_link_it_if_different_ubi_account_stats_linked_and_authorized_doesnt_exist_another_linked(){
        Long userId=  1L;
        String email = "email";
        AuthorizationDTO dto = new AuthorizationDTO();
        dto.setProfileId("profileId");
        when(ubiAccountEntryPostgresRepository.findUbiAccountStatsProfileIdByUserIdAndEmail(userId, email)).thenReturn(Optional.of("differentProfileId"));

        UserUbiAccountAuthorizedProjection proj = mock(UserUbiAccountAuthorizedProjection.class);
        when(ubiAccountEntryEntityMapper.createUserUbiAccountAuthorizedProjection(userId, email, dto)).thenReturn(proj);

        when(ubiAccountStatsPostgresRepository.existsById("profileId")).thenReturn(false);

        ubiAccountPostgresService.updateCredentialsAndLinkUbiAccountStatsForAuthorizedUser(1L, "email", dto);

        verify(ubiAccountEntryPostgresRepository).updateUserUbiCredentials(proj);

        verify(ubiAccountStatsPostgresRepository).save(argThat((UbiAccountStatsEntity e) -> e.getUbiProfileId().equals("profileId")));
        verify(ubiAccountEntryPostgresRepository).linkUbiAccountStatsByUserIdAndEmail(userId, email, "profileId");
    }

    @Test
    public void updateCredentialsAndLinkUbiAccountStats_should_link_to_different_ubi_account_stats_ForAuthorizedUser_if_another_existing_profileId_another_linked(){
        Long userId=  1L;
        String email = "email";
        AuthorizationDTO dto = new AuthorizationDTO();
        dto.setProfileId("profileId");
        when(ubiAccountEntryPostgresRepository.findUbiAccountStatsProfileIdByUserIdAndEmail(userId, email)).thenReturn(Optional.of("differentProfileId"));

        UserUbiAccountAuthorizedProjection proj = mock(UserUbiAccountAuthorizedProjection.class);
        when(ubiAccountEntryEntityMapper.createUserUbiAccountAuthorizedProjection(userId, email, dto)).thenReturn(proj);

        when(ubiAccountStatsPostgresRepository.existsById("profileId")).thenReturn(true);

        ubiAccountPostgresService.updateCredentialsAndLinkUbiAccountStatsForAuthorizedUser(1L, "email", dto);

        verify(ubiAccountEntryPostgresRepository).updateUserUbiCredentials(proj);

        verify(ubiAccountStatsPostgresRepository, never()).save(any(UbiAccountStatsEntity.class));
        verify(ubiAccountEntryPostgresRepository).linkUbiAccountStatsByUserIdAndEmail(userId, email, "profileId");
    }

    @Test
    public void saveAuthorizationInfo_should_save_new_ubi_account_stats_and_link_it_if_different_ubi_account_stats_linked_and_authorized_doesnt_exist_null_exists(){
        Long userId=  1L;
        String email = "email";
        AuthorizationDTO dto = new AuthorizationDTO();
        dto.setProfileId("profileId");
        when(ubiAccountEntryPostgresRepository.findUbiAccountStatsProfileIdByUserIdAndEmail(userId, email)).thenReturn(Optional.empty());

        UserUbiAccountAuthorizedProjection proj = mock(UserUbiAccountAuthorizedProjection.class);
        when(ubiAccountEntryEntityMapper.createUserUbiAccountAuthorizedProjection(userId, email, dto)).thenReturn(proj);

        when(ubiAccountStatsPostgresRepository.existsById("profileId")).thenReturn(false);

        ubiAccountPostgresService.updateCredentialsAndLinkUbiAccountStatsForAuthorizedUser(1L, "email", dto);

        verify(ubiAccountEntryPostgresRepository).updateUserUbiCredentials(proj);

        verify(ubiAccountStatsPostgresRepository).save(argThat((UbiAccountStatsEntity e) -> e.getUbiProfileId().equals("profileId")));
        verify(ubiAccountEntryPostgresRepository).linkUbiAccountStatsByUserIdAndEmail(userId, email, "profileId");
    }

    @Test
    public void updateCredentialsAndLinkUbiAccountStats_should_link_to_different_ubi_account_stats_ForAuthorizedUser_if_another_existing_profileId_null_exists(){
        Long userId=  1L;
        String email = "email";
        AuthorizationDTO dto = new AuthorizationDTO();
        dto.setProfileId("profileId");
        when(ubiAccountEntryPostgresRepository.findUbiAccountStatsProfileIdByUserIdAndEmail(userId, email)).thenReturn(Optional.empty());

        UserUbiAccountAuthorizedProjection proj = mock(UserUbiAccountAuthorizedProjection.class);
        when(ubiAccountEntryEntityMapper.createUserUbiAccountAuthorizedProjection(userId, email, dto)).thenReturn(proj);

        when(ubiAccountStatsPostgresRepository.existsById("profileId")).thenReturn(true);

        ubiAccountPostgresService.updateCredentialsAndLinkUbiAccountStatsForAuthorizedUser(1L, "email", dto);

        verify(ubiAccountEntryPostgresRepository).updateUserUbiCredentials(proj);

        verify(ubiAccountStatsPostgresRepository, never()).save(any(UbiAccountStatsEntity.class));
        verify(ubiAccountEntryPostgresRepository).linkUbiAccountStatsByUserIdAndEmail(userId, email, "profileId");
    }

    @Test
    public void unlinkUbiAccountStatsForUnauthorizedUsers_should_unlink_all_ubi_account_stats_for_unauthorized_users(){
        UserUnauthorizedUbiAccount user1 = mock(UserUnauthorizedUbiAccount.class);
        UserUnauthorizedUbiAccount user2 = mock(UserUnauthorizedUbiAccount.class);

        UserUnauthorizedUbiAccountProjection proj1 = mock(UserUnauthorizedUbiAccountProjection.class);
        UserUnauthorizedUbiAccountProjection proj2 = mock(UserUnauthorizedUbiAccountProjection.class);

        when(ubiAccountEntryEntityMapper.createUnauthorizedAccountProjection(user1)).thenReturn(proj1);
        when(ubiAccountEntryEntityMapper.createUnauthorizedAccountProjection(user2)).thenReturn(proj2);

        ubiAccountPostgresService.unlinkUbiAccountStatsForUnauthorizedUsers(List.of(user1, user2));

        verify(ubiAccountEntryPostgresRepository).unlinkAllUbiAccountStatsForUnauthorizedUsers(argThat((List<UserUnauthorizedUbiAccountProjection> projections) -> projections.contains(proj1) && projections.contains(proj2) && projections.size() == 2));
    }

    @Test
    public void findAllUsersUbiCredentials_should_return_all_users_ubi_Account_credentials(){
        UserUbiAccountCredentials cred1 = mock(UserUbiAccountCredentials.class);
        UserUbiAccountCredentials cred2 = mock(UserUbiAccountCredentials.class);

        UserUbiAccountCredentialsProjection proj1 = mock(UserUbiAccountCredentialsProjection.class);
        UserUbiAccountCredentialsProjection proj2 = mock(UserUbiAccountCredentialsProjection.class);

        when(ubiAccountEntryEntityMapper.createUserUbiAccountCredentials(proj1)).thenReturn(cred1);
        when(ubiAccountEntryEntityMapper.createUserUbiAccountCredentials(proj2)).thenReturn(cred2);

        when(ubiAccountEntryPostgresRepository.findAllUsersUbiCredentials()).thenReturn(List.of(proj1, proj2));

        List<UserUbiAccountCredentials> result = ubiAccountPostgresService.findAllUsersUbiAccountCredentials();

        assertTrue(result.contains(cred1));
        assertTrue(result.contains(cred2));
        assertEquals(2, result.size());
    }
}