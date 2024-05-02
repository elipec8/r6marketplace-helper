package github.ricemonger.marketplace.graphs.database.redis.services;

import github.ricemonger.marketplace.authorization.AuthorizationDTO;
import github.ricemonger.marketplace.authorization.AuthorizationService;
import github.ricemonger.marketplace.graphs.GraphQlClientService;
import github.ricemonger.marketplace.updateFetcher.ScheduledAllItemsStatsFetcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RedisServiceTests {

    @MockBean
    private GraphQlClientService graphQlClientService; // Beans init exception without it

    @Autowired
    private MainUserConfiguration mainUserConfiguration;

    @MockBean
    private AuthorizationService authorizationService;

    @Autowired
    private RedisService redisService;

    //RedisTemplate interactions didn't tested cause of RedisConfiguration's MockBeans initialization problems in test environment

    @BeforeEach
    public void setUp() {
        when(authorizationService.getUserAuthorizationDTO(mainUserConfiguration.getEmail(), mainUserConfiguration.getPassword())).thenReturn(
                new AuthorizationDTO("ticket",
                        "profileId",
                        "spaceId",
                        "sessionId",
                        "twoFactorAuthTicket",
                        "rememberDeviceTicket",
                        "rememberMeTicket"
                ));
    }


    @Test
    public void getMainUserAuthorizationTokenShouldReturnValueFromAuthorizationService() {
        String ticket = redisService.getMainUserAuthorizationToken();

        assertEquals("ticket", ticket);
    }

    @Test
    public void getMainUserProfileIdShouldReturnValueFromAuthorizationService() {
        String profileId = redisService.getMainUserProfileId();

        assertEquals("profileId", profileId);
    }


    @Test
    public void getMainUserSessionIdShouldReturnValueFromAuthorizationService() {
        String sessionId = redisService.getMainUserSessionId();

        assertEquals("sessionId", sessionId);
    }

    @Test
    public void getMainUserRememberMeTicketShouldReturnValueFromAuthorizationService() {
        String rememberMeTicket = redisService.getMainUserRememberMeTicket();

        assertEquals("rememberMeTicket", rememberMeTicket);
    }

    @Test
    public void getMainUserSpaceIdShouldReturnValueFromAuthorizationService() {
        String spaceId = redisService.getMainUserSpaceId();

        assertEquals("spaceId", spaceId);
    }
}
