package github.ricemonger.marketplace.services;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest
class PotentialTradeStatsServiceTest {
    @SpyBean
    private PotentialTradeStatsService potentialTradeStatsService;
    @MockBean
    private CommonValuesService commonValuesService;
}