package github.ricemonger.marketplace.databases.redis.services;

import github.ricemonger.marketplace.authorization.AuthorizationDTO;
import github.ricemonger.marketplace.authorization.AuthorizationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RedisServiceTests {

    @SpyBean
    @Qualifier("redisTemplate")
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private MainUserConfiguration mainUserConfiguration;

    @MockBean
    private AuthorizationService authorizationService;

    @Autowired
    private RedisService redisService;

    @BeforeEach
    public void setUp() {
        redisTemplate.delete("mainUserAuthorizationToken");
        redisTemplate.delete("mainUserProfileId");
        redisTemplate.delete("mainUserSessionId");
        redisTemplate.delete("mainUserSpaceId");
        redisTemplate.delete("mainUserRememberMeTicket");
        redisTemplate.delete("expectedItemCount");

        when(authorizationService.getUserAuthorizationDTO(mainUserConfiguration.getEmail(), mainUserConfiguration.getPassword())).thenReturn(new AuthorizationDTO("ticket", "profileId", "spaceId", "sessionId", "twoFactorAuthTicket", "rememberDeviceTicket", "rememberMeTicket"));
    }

    @AfterEach
    public void cleanUp() {
        redisTemplate.delete("mainUserAuthorizationToken");
        redisTemplate.delete("mainUserProfileId");
        redisTemplate.delete("mainUserSessionId");
        redisTemplate.delete("mainUserSpaceId");
        redisTemplate.delete("mainUserRememberMeTicket");
        redisTemplate.delete("expectedItemCount");
    }


    @Test
    public void getMainUserAuthorizationTokenShouldReturnValueFromAuthorizationServiceIfEmpty() {
        String ticket = redisService.getMainUserAuthorizationToken();

        assertEquals("ticket", ticket);
    }

    @Test
    public void getMainUserAuthorizationTokenShouldCreateAllAuthorizationFieldsIfEmpty() {
        ValueOperations mock = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(mock);

        redisService.getMainUserAuthorizationToken();

        shouldCreateMainUserAuthHeaders(mock);
    }

    @Test
    public void getMainUserProfileIdShouldReturnValueFromAuthorizationServiceIfEmpty() {
        String profileId = redisService.getMainUserProfileId();

        assertEquals("profileId", profileId);
    }

    @Test
    public void getMainUserProfileIdShouldCreateAllAuthorizationFieldsIfEmpty() {
        ValueOperations mock = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(mock);

        redisService.getMainUserProfileId();

        shouldCreateMainUserAuthHeaders(mock);
    }


    @Test
    public void getMainUserSessionIdShouldReturnValueFromAuthorizationServiceIfEmpty() {
        String sessionId = redisService.getMainUserSessionId();

        assertEquals("sessionId", sessionId);
    }

    @Test
    public void getMainUserSessionIdShouldCreateAllAuthorizationFieldsIfEmpty() {
        ValueOperations mock = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(mock);

        redisService.getMainUserSessionId();

        shouldCreateMainUserAuthHeaders(mock);
    }

    @Test
    public void getMainUserRememberMeTicketShouldReturnValueFromAuthorizationServiceIfEmpty() {
        String rememberMeTicket = redisService.getMainUserRememberMeTicket();

        assertEquals("rememberMeTicket", rememberMeTicket);
    }

    @Test
    public void getMainUserRememberMeTicketShouldCreateAllAuthorizationFieldsIfEmpty() {
        ValueOperations mock = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(mock);

        redisService.getMainUserRememberMeTicket();

        shouldCreateMainUserAuthHeaders(mock);
    }

    @Test
    public void rememberMeTicketShouldNotBeCreatedIfNullInDto() {
        when(authorizationService.getUserAuthorizationDTO(mainUserConfiguration.getEmail(), mainUserConfiguration.getPassword())).thenReturn(new AuthorizationDTO("ticket", "profileId", "spaceId", "sessionId", "twoFactorAuthTicket", "rememberDeviceTicket", null));

        ValueOperations mock = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(mock);

        redisService.getMainUserRememberMeTicket();

        AuthorizationDTO dto = authorizationService.getUserAuthorizationDTO(mainUserConfiguration.getEmail(), mainUserConfiguration.getPassword());

        verify(mock).set("mainUserAuthorizationToken", dto.getTicket());
        verify(redisTemplate).expire("mainUserAuthorizationToken", mainUserConfiguration.getExpireTimeout(), TimeUnit.SECONDS);

        verify(mock).set("mainUserProfileId", dto.getProfileId());
        verify(redisTemplate).expire("mainUserProfileId", mainUserConfiguration.getExpireTimeout(), TimeUnit.SECONDS);

        verify(mock).set("mainUserSessionId", dto.getSessionId());
        verify(redisTemplate).expire("mainUserSessionId", mainUserConfiguration.getExpireTimeout(), TimeUnit.SECONDS);

        verify(mock).set("mainUserSpaceId", dto.getSpaceId());
        verify(redisTemplate).expire("mainUserSpaceId", mainUserConfiguration.getExpireTimeout(), TimeUnit.SECONDS);

        verify(mock, never()).set("mainUserRememberMeTicket", dto.getRememberMeTicket());
        verify(redisTemplate, never()).expire("mainUserRememberMeTicket", mainUserConfiguration.getExpireTimeout(), TimeUnit.SECONDS);
    }

    @Test
    public void getMainUserSpaceIdShouldReturnValueFromAuthorizationServiceIfEmpty() {
        String spaceId = redisService.getMainUserSpaceId();

        assertEquals("spaceId", spaceId);
    }

    @Test
    public void getMainUserSpaceIdShouldCreateAllAuthorizationFieldsIfEmpty() {
        ValueOperations mock = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(mock);

        redisService.getMainUserSpaceId();

        shouldCreateMainUserAuthHeaders(mock);
    }

    @Test
    public void getExpectedItemCountShouldReturnZeroIfEmpty() {
        assertEquals(0, redisService.getExpectedItemCount());
    }

    @Test
    public void getExpectedItemCountShouldReturnZeroIfInvalid() {
        redisTemplate.opsForValue().set("expectedItemCount", "");

        assertEquals(0, redisService.getExpectedItemCount());
    }

    @Test
    public void getExpectedItemCountShouldReturnFromRedis() {
        redisTemplate.opsForValue().set("expectedItemCount", String.valueOf(10));

        assertEquals(10, redisService.getExpectedItemCount());
    }

    @Test
    public void setExpectedItemCountShouldSaveInRedis() {
        ValueOperations valueOperations = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        redisService.setExpectedItemCount(99);

        verify(valueOperations).set("expectedItemCount", String.valueOf(99));
    }

    private void shouldCreateMainUserAuthHeaders(ValueOperations valueOperations) {

        AuthorizationDTO dto = authorizationService.getUserAuthorizationDTO(mainUserConfiguration.getEmail(), mainUserConfiguration.getPassword());

        verify(valueOperations).set("mainUserAuthorizationToken", dto.getTicket());
        verify(redisTemplate).expire("mainUserAuthorizationToken", mainUserConfiguration.getExpireTimeout(), TimeUnit.SECONDS);

        verify(valueOperations).set("mainUserProfileId", dto.getProfileId());
        verify(redisTemplate).expire("mainUserProfileId", mainUserConfiguration.getExpireTimeout(), TimeUnit.SECONDS);

        verify(valueOperations).set("mainUserSessionId", dto.getSessionId());
        verify(redisTemplate).expire("mainUserSessionId", mainUserConfiguration.getExpireTimeout(), TimeUnit.SECONDS);

        verify(valueOperations).set("mainUserSpaceId", dto.getSpaceId());
        verify(redisTemplate).expire("mainUserSpaceId", mainUserConfiguration.getExpireTimeout(), TimeUnit.SECONDS);

        verify(valueOperations).set("mainUserRememberMeTicket", dto.getRememberMeTicket());
        verify(redisTemplate).expire("mainUserRememberMeTicket", mainUserConfiguration.getExpireTimeout(), TimeUnit.SECONDS);
    }
}
