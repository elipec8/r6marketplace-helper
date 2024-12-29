package github.ricemonger.ubi_users_stats_fetcher.postgres.services;

import github.ricemonger.ubi_users_stats_fetcher.postgres.repositories.UbiAccountStatsPostgresRepository;
import github.ricemonger.ubi_users_stats_fetcher.postgres.services.entity_mappers.user.UbiAccountStatsEntityMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;

@SpringBootTest
class UbiAccountStatsPostgresServiceTest {
    @Autowired
    private UbiAccountStatsPostgresService ubiAccountStatsPostgresService;
    @MockBean
    private UbiAccountStatsPostgresRepository ubiAccountStatsRepository;
    @MockBean
    private UbiAccountStatsEntityMapper ubiAccountStatsEntityMapper;

    @Test
    public void saveAll_should_map_and_save_all() {
        List dtos = Mockito.mock(List.class);

        List entities = Mockito.mock(List.class);

        Mockito.when(ubiAccountStatsEntityMapper.createEntities(dtos)).thenReturn(entities);

        ubiAccountStatsPostgresService.saveAll(dtos);

        verify(ubiAccountStatsRepository).saveAll(same(entities));
    }
}