package github.ricemonger.marketplace.databases.redis.services;

import github.ricemonger.marketplace.authorization.AuthorizationService;
import github.ricemonger.utils.DTOs.auth.AuthorizationDTO;
import github.ricemonger.utils.DTOs.ConfigResolvedTransactionPeriod;
import github.ricemonger.utils.DTOs.ConfigTrades;
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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RedisServiceTest {

    private static final int EXPIRE_TIMEOUT = 3;
    @SpyBean
    @Qualifier("redisTemplate")
    private RedisTemplate<String, String> redisTemplate;
    @MockBean
    private AuthorizationService authorizationService;
    @Autowired
    private RedisService redisService;

    @BeforeEach
    public void setUp() {
        cleanUp();

        when(authorizationService.authorizeAndGetBaseAuthorizedDTO(any(), any())).thenReturn(new AuthorizationDTO("ticket", "profileId"
                , "spaceId", "sessionId", "twoFactorAuthTicket", "rememberDeviceTicket", "rememberMeTicket"));
    }

    @AfterEach
    public void cleanUp() {
        redisTemplate.delete("mainUserAuthorizationToken");
        redisTemplate.delete("mainUserProfileId");
        redisTemplate.delete("mainUserSessionId");
        redisTemplate.delete("mainUserSpaceId");
        redisTemplate.delete("mainUserRememberMeTicket");
        redisTemplate.delete("expectedItemCount");
        redisTemplate.delete("buyResolvedTransactionPeriod");
        redisTemplate.delete("sellResolvedTransactionPeriod");
        redisTemplate.delete("saleExpiresAfterMinutes");
        redisTemplate.delete("buySlots");
        redisTemplate.delete("sellSlots");
        redisTemplate.delete("buyLimit");
        redisTemplate.delete("sellLimit");
        redisTemplate.delete("resaleLockDurationInMinutes");
        redisTemplate.delete("paymentItemId");
        redisTemplate.delete("feePercentage");
        redisTemplate.delete("twoFactorAuthenticationRule");
        redisTemplate.delete("mainUserAuthorizationToken");
        redisTemplate.delete("mainUserSessionId");
        redisTemplate.delete("mainUserProfileId");
        redisTemplate.delete("mainUserRememberMeTicket");
    }

    @Test
    public void getExpectedItemCount_should_return_zero_if_empty() {
        assertEquals(0, redisService.getExpectedItemCount());
    }

    @Test
    public void getExpectedItemCount_should_return_zero_if_invalid() {
        redisTemplate.opsForValue().set("expectedItemCount", "");

        assertEquals(0, redisService.getExpectedItemCount());
    }

    @Test
    public void getExpectedItemCount_should_return_from_redis() {
        redisTemplate.opsForValue().set("expectedItemCount", String.valueOf(10));

        assertEquals(10, redisService.getExpectedItemCount());
    }

    @Test
    public void setExpectedItemCount_should_save_in_redis() {
        ValueOperations valueOperations = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        redisService.setExpectedItemCount(99);

        verify(valueOperations).set("expectedItemCount", String.valueOf(99));
    }

    @Test
    public void getConfigResolvedTransactionPeriod_should_return_zero_if_empty() {
        assertEquals(new ConfigResolvedTransactionPeriod(0, 0), redisService.getConfigResolvedTransactionPeriod());
    }

    @Test
    public void getConfigResolvedTransactionPeriod_should_return_zero_if_invalid() {
        redisTemplate.opsForValue().set("buyResolvedTransactionPeriod", "");
        redisTemplate.opsForValue().set("sellResolvedTransactionPeriod", "");

        assertEquals(new ConfigResolvedTransactionPeriod(0, 0), redisService.getConfigResolvedTransactionPeriod());
    }

    @Test
    public void getConfigResolvedTransactionPeriod_should_return_from_redis() {
        redisTemplate.opsForValue().set("buyResolvedTransactionPeriod", "10");
        redisTemplate.opsForValue().set("sellResolvedTransactionPeriod", "20");

        assertEquals(new ConfigResolvedTransactionPeriod(10, 20), redisService.getConfigResolvedTransactionPeriod());
    }

    @Test
    public void setConfigResolvedTransactionPeriod_should_save_in_redis() {
        ValueOperations valueOperations = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        redisService.setConfigResolvedTransactionPeriod(new ConfigResolvedTransactionPeriod(10, 20));

        verify(valueOperations).set("buyResolvedTransactionPeriod", "10");
        verify(valueOperations).set("sellResolvedTransactionPeriod", "20");
    }

    @Test
    public void getConfigTrades_should_return_zero_if_empty() {
        assertEquals(new ConfigTrades(0, 0, 0, 0, 0, 0, null, 0, false, false), redisService.getConfigTrades());
    }

    @Test
    public void getConfigTrades_should_return_zero_if_invalid() {
        redisTemplate.opsForValue().set("saleExpiresAfterMinutes", "");
        redisTemplate.opsForValue().set("buySlots", "");
        redisTemplate.opsForValue().set("sellSlots", "");
        redisTemplate.opsForValue().set("buyLimit", "");
        redisTemplate.opsForValue().set("sellLimit", "");
        redisTemplate.opsForValue().set("resaleLockDurationInMinutes", "");
        redisTemplate.opsForValue().set("paymentItemId", "");
        redisTemplate.opsForValue().set("feePercentage", "");
        redisTemplate.opsForValue().set("twoFactorAuthenticationRule", "");
        redisTemplate.opsForValue().set("gameOwnershipRule", "");

        assertEquals(new ConfigTrades(0, 0, 0, 0, 0, 0, "", 0, false, false), redisService.getConfigTrades());
    }

    @Test
    public void getConfigTrades_should_return_from_redis() {
        redisTemplate.opsForValue().set("saleExpiresAfterMinutes", "10");
        redisTemplate.opsForValue().set("buySlots", "20");
        redisTemplate.opsForValue().set("sellSlots", "30");
        redisTemplate.opsForValue().set("buyLimit", "40");
        redisTemplate.opsForValue().set("sellLimit", "50");
        redisTemplate.opsForValue().set("resaleLockDurationInMinutes", "60");
        redisTemplate.opsForValue().set("paymentItemId", "paymentItemId");
        redisTemplate.opsForValue().set("feePercentage", "70");
        redisTemplate.opsForValue().set("twoFactorAuthenticationRule", "true");
        redisTemplate.opsForValue().set("gameOwnershipRule", "true");

        assertEquals(new ConfigTrades(10, 20, 30, 40, 50, 60, "paymentItemId", 70, true, true), redisService.getConfigTrades());
    }

    @Test
    public void setConfigTrades_should_save_in_redis() {
        ValueOperations valueOperations = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        redisService.setConfigTrades(new ConfigTrades(10, 20, 30, 40, 50, 60, "paymentItemId", 70, true, true));

        verify(valueOperations).set("saleExpiresAfterMinutes", "10");
        verify(valueOperations).set("buySlots", "20");
        verify(valueOperations).set("sellSlots", "30");
        verify(valueOperations).set("buyLimit", "40");
        verify(valueOperations).set("sellLimit", "50");
        verify(valueOperations).set("resaleLockDurationInMinutes", "60");
        verify(valueOperations).set("paymentItemId", "paymentItemId");
        verify(valueOperations).set("feePercentage", "70");
        verify(valueOperations).set("twoFactorAuthenticationRule", "true");
        verify(valueOperations).set("gameOwnershipRule", "true");
    }

    @Test
    public void getPaymentItemId_should_return_value_from_redis() {
        redisTemplate.opsForValue().set("paymentItemId", "paymentItemId");

        assertEquals("paymentItemId", redisService.getPaymentItemId());
    }

    @Test
    public void getPaymentItemId_should_return_null_if_empty() {
        assertNull(redisService.getPaymentItemId());
    }

    @Test
    public void getMainUserAuthorizationToken_should_return_value_from_redis() {
        redisTemplate.opsForValue().set("mainUserAuthorizationToken", "mainUserAuthorizationToken");

        assertEquals("mainUserAuthorizationToken", redisService.getMainUserAuthorizationToken());
    }

    @Test
    public void getMainUserAuthorizationToken_should_return_null_if_empty() {
        assertNull(redisService.getMainUserAuthorizationToken());
    }

    @Test
    public void getMainUserProfileId_should_return_value_from_redis() {
        redisTemplate.opsForValue().set("mainUserProfileId", "mainUserProfileId");

        assertEquals("mainUserProfileId", redisService.getMainUserProfileId());
    }

    @Test
    public void getMainUserProfileId_should_return_null_if_empty() {
        assertNull(redisService.getMainUserProfileId());
    }


    @Test
    public void getMainUserSessionId_should_return_value_from_redis() {
        redisTemplate.opsForValue().set("mainUserSessionId", "mainUserSessionId");

        assertEquals("mainUserSessionId", redisService.getMainUserSessionId());
    }

    @Test
    public void getMainUserSessionId_should_return_null_if_empty() {
        assertNull(redisService.getMainUserSessionId());
    }

    @Test
    public void getMainUserRememberMeTicket_should_return_value_from_redis() {
        redisTemplate.opsForValue().set("mainUserRememberMeTicket", "mainUserRememberMeTicket");

        assertEquals("mainUserRememberMeTicket", redisService.getMainUserRememberMeTicket());
    }

    @Test
    public void getMainUserRememberMeTicket_should_return_null_if_empty() {
        assertNull(redisService.getMainUserRememberMeTicket());
    }

    @Test
    public void setMainUserAuthorization_should_save_values_from_dto() {
        ValueOperations mock = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(mock);

        AuthorizationDTO dto = new AuthorizationDTO("ticket", "profileId", "spaceId", "sessionId", "twoFactorAuthTicket", "rememberDeviceTicket",
                "rememberMeTicket");
        when(authorizationService.authorizeAndGetBaseAuthorizedDTO(any(), any())).thenReturn(dto);

        redisService.setMainUserAuthorization(dto, EXPIRE_TIMEOUT);

        verify(mock).set("mainUserAuthorizationToken", dto.getTicket());
        verify(redisTemplate).expire("mainUserAuthorizationToken", EXPIRE_TIMEOUT, TimeUnit.SECONDS);

        verify(mock).set("mainUserProfileId", dto.getProfileId());
        verify(redisTemplate).expire("mainUserProfileId", EXPIRE_TIMEOUT, TimeUnit.SECONDS);

        verify(mock).set("mainUserSessionId", dto.getSessionId());
        verify(redisTemplate).expire("mainUserSessionId", EXPIRE_TIMEOUT, TimeUnit.SECONDS);

        verify(mock).set("mainUserRememberMeTicket", dto.getRememberMeTicket());
        verify(redisTemplate).expire("mainUserRememberMeTicket", EXPIRE_TIMEOUT, TimeUnit.SECONDS);
    }

    @Test
    public void setMainUserAuthorization_should_save_values_from_dto_without_rememberMeTicket() {
        ValueOperations mock = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(mock);

        AuthorizationDTO dto = new AuthorizationDTO("ticket", "profileId", "spaceId", "sessionId", "twoFactorAuthTicket", "rememberDeviceTicket",
                null);
        when(authorizationService.authorizeAndGetBaseAuthorizedDTO(any(), any())).thenReturn(dto);

        redisService.setMainUserAuthorization(dto, EXPIRE_TIMEOUT);

        verify(mock).set("mainUserAuthorizationToken", dto.getTicket());
        verify(redisTemplate).expire("mainUserAuthorizationToken", EXPIRE_TIMEOUT, TimeUnit.SECONDS);

        verify(mock).set("mainUserProfileId", dto.getProfileId());
        verify(redisTemplate).expire("mainUserProfileId", EXPIRE_TIMEOUT, TimeUnit.SECONDS);

        verify(mock).set("mainUserSessionId", dto.getSessionId());
        verify(redisTemplate).expire("mainUserSessionId", EXPIRE_TIMEOUT, TimeUnit.SECONDS);

        verify(mock, never()).set("mainUserRememberMeTicket", dto.getRememberMeTicket());
        verify(redisTemplate, never()).expire("mainUserRememberMeTicket", EXPIRE_TIMEOUT, TimeUnit.SECONDS);
    }

    @Test
    public void setLastUbiUsersStatsFetchTime_should_save_in_redis() {
        ValueOperations valueOperations = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        redisService.setLastUbiUsersStatsFetchTime("2021-10-10T10:10:10");

        verify(valueOperations).set("lastUbiUsersStatsFetchTime", "2021-10-10T10:10:10");
    }

    @Test
    public void getLastUbiUsersStatsFetchTime_should_return_value_from_redis() {
        redisTemplate.opsForValue().set("lastUbiUsersStatsFetchTime", "2021-10-10T10:10:10");

        assertEquals("2021-10-10T10:10:10", redisService.getLastUbiUsersStatsFetchTime());
    }
}
