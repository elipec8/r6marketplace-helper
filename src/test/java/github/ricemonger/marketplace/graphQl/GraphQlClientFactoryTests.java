package github.ricemonger.marketplace.graphQl;

import github.ricemonger.marketplace.UbiServiceConfiguration;
import github.ricemonger.marketplace.databases.redis.services.RedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

@SpringBootTest
public class GraphQlClientFactoryTests {

    @MockBean
    private RedisService redisService;

    @Autowired
    private UbiServiceConfiguration ubiServiceConfiguration;

    private GraphQlClientFactory graphQlClientFactory;

    @BeforeEach
    public void setUp() {
        graphQlClientFactory = new GraphQlClientFactory(ubiServiceConfiguration, redisService);
    }

    @Test
    public void getOrCreateAllItemsStatsFetcherClientShouldGetFieldsFromRedisMainUser() {
        graphQlClientFactory.createMainUserClient();

        verify(redisService).getMainUserAuthorizationToken();
        verify(redisService).getMainUserSessionId();
        verify(redisService).getMainUserProfileId();
        verify(redisService).getMainUserSpaceId();
    }

    @Test
    public void createMainUserClientShouldNotBuildFromRedisIfClientAlreadyExists() {
        graphQlClientFactory.createMainUserClient();

        reset(redisService);

        graphQlClientFactory.createMainUserClient();

        verify(redisService, never()).getMainUserAuthorizationToken();
        verify(redisService, never()).getMainUserSessionId();
        verify(redisService, never()).getMainUserProfileId();
        verify(redisService, never()).getMainUserSpaceId();
    }

    @Test
    public void createMainUserClientShouldBuildFromRedisIfClientExpired() throws InterruptedException {
        graphQlClientFactory.createMainUserClient();

        reset(redisService);

        Thread.sleep(ubiServiceConfiguration.getExpireTimeout() * 1000L + 1);

        graphQlClientFactory.createMainUserClient();

        verify(redisService).getMainUserAuthorizationToken();
        verify(redisService).getMainUserSessionId();
        verify(redisService).getMainUserProfileId();
        verify(redisService).getMainUserSpaceId();
    }


}
