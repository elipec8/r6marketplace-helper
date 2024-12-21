package github.ricemonger.marketplace.services.factories;

import github.ricemonger.marketplace.services.PotentialTradeStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class PotentialTradeFactoryTest {
    @Autowired
    private PotentialTradeFactory potentialTradeFactory;
    @MockBean
    private PotentialTradeStatsService potentialTradeStatsService;

}