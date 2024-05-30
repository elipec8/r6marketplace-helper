package github.ricemonger.marketplace.graphQl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.verify;

@SpringBootTest
class GraphQlClientServiceTests {

    @MockBean
    private GraphQlClientFactory graphQlClientFactory;

    @Autowired
    private GraphQlClientService graphQlClientService;

    @Test
    void fetchAllItemStatsGetClientFromFactory() {
        try {
            graphQlClientService.fetchAllItemStats(1);
        }
        catch (Exception e) {

        }

        verify(graphQlClientFactory).createMainUserClient();
    }
}