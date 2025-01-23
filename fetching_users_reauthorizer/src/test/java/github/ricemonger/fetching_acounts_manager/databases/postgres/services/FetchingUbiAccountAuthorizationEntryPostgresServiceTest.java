package github.ricemonger.fetching_acounts_manager.databases.postgres.services;

import github.ricemonger.fetching_acounts_manager.databases.postgres.repositories.FetchingUbiAccountAuthorizationEntryPostgresRepository;
import github.ricemonger.fetching_acounts_manager.databases.postgres.services.entity_mappers.FetchingUbiAccountAuthorizationEntryEntityMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
class FetchingUbiAccountAuthorizationEntryPostgresServiceTest {
    @Autowired
    private FetchingUbiAccountAuthorizationEntryPostgresService fetchingUbiAccountAuthorizationEntryPostgresService;
    @MockBean
    private FetchingUbiAccountAuthorizationEntryPostgresRepository fetchingUbiAccountAuthorizationEntryPostgresRepository;
    @MockBean
    private FetchingUbiAccountAuthorizationEntryEntityMapper fetchingUbiAccountAuthorizationEntryEntityMapper;

    @Test
    public void saveAuthorizedUbiUsersAndRemoveOthers_should_clear_all_entries_and_save_mapped_ones() {
        List dtos = mock(List.class);

        List entities = mock(List.class);

        when(fetchingUbiAccountAuthorizationEntryEntityMapper.mapToEntities(dtos)).thenReturn(entities);

        fetchingUbiAccountAuthorizationEntryPostgresService.saveAuthorizedUbiUsersAndRemoveOthers(dtos);

        verify(fetchingUbiAccountAuthorizationEntryPostgresRepository).deleteAll();

        verify(fetchingUbiAccountAuthorizationEntryPostgresRepository).saveAll(entities);
    }
}