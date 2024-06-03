package github.ricemonger.marketplace.graphQl;

import github.ricemonger.marketplace.services.CommonValuesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class GraphQlClientFactoryTest {

    @MockBean
    private CommonValuesService commonValuesService;

    private GraphQlClientFactory graphQlClientFactory;

    @BeforeEach
    public void setUp() {
        graphQlClientFactory = new GraphQlClientFactory(commonValuesService);
    }

    @Test
    public void getOrCreateAllItemsStatsFetcherClientShouldGetFieldsFromRedisMainUser() {
        graphQlClientFactory.createMainUserClient();

        verify(commonValuesService).getMainUserAuthorizationToken();
        verify(commonValuesService).getMainUserSessionId();
        verify(commonValuesService).getMainUserProfileId();
        verify(commonValuesService).getUbiGameSpaceId();
    }

    @Test
    public void createMainUserClientShouldBuildFromRedisIfClientExpired() throws InterruptedException {
        graphQlClientFactory.createMainUserClient();

        reset(commonValuesService);

        Thread.sleep(commonValuesService.getExpireTimeout() * 1000L + 1);

        graphQlClientFactory.createMainUserClient();

        verify(commonValuesService).getMainUserAuthorizationToken();
        verify(commonValuesService).getMainUserSessionId();
        verify(commonValuesService).getMainUserProfileId();
        verify(commonValuesService).getUbiGameSpaceId();
    }
}
