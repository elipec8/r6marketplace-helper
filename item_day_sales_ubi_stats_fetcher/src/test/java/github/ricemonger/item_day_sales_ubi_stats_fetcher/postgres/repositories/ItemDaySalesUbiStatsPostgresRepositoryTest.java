package github.ricemonger.item_day_sales_ubi_stats_fetcher.postgres.repositories;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class ItemDaySalesUbiStatsPostgresRepositoryTest {
    @MockBean
    private ItemDaySalesUbiStatsPostgresRepository itemDaySalesUbiStatsPostgresRepository;

    @Disabled // H2 does not support ON CONFLICT DO NOTHING
    @Test
    public void insertAll_should_insert_each_entity() {

    }
}