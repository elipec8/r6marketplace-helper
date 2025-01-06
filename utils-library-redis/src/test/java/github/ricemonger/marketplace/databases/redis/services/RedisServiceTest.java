package github.ricemonger.marketplace.databases.redis.services;

import github.ricemonger.utils.DTOs.common.ConfigResolvedTransactionPeriod;
import github.ricemonger.utils.DTOs.common.ConfigTrades;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RedisServiceTest {
    private static final int EXPIRE_TIMEOUT = 3;

    private RedisTemplate<String, String> redisTemplate = Mockito.mock(RedisTemplate.class);

    private RedisService redisService = new RedisService(redisTemplate);

    @Test
    public void getExpectedItemCount_should_return_from_redis() {
        ValueOperations valueOperations = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("expectedItemCount")).thenReturn("10");

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
    public void getConfigResolvedTransactionPeriod_should_return_from_redis() {
        ValueOperations valueOperations = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("buyResolvedTransactionPeriod")).thenReturn("10");
        when(valueOperations.get("sellResolvedTransactionPeriod")).thenReturn("20");

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
    public void getConfigTrades_should_return_from_redis() {
        ValueOperations valueOperations = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("saleExpiresAfterMinutes")).thenReturn("10");
        when(valueOperations.get("buySlots")).thenReturn("20");
        when(valueOperations.get("sellSlots")).thenReturn("30");
        when(valueOperations.get("buyLimit")).thenReturn("40");
        when(valueOperations.get("sellLimit")).thenReturn("50");
        when(valueOperations.get("resaleLockDurationInMinutes")).thenReturn("60");
        when(valueOperations.get("paymentItemId")).thenReturn("paymentItemId");
        when(valueOperations.get("feePercentage")).thenReturn("70");
        when(valueOperations.get("twoFactorAuthenticationRule")).thenReturn("true");
        when(valueOperations.get("gameOwnershipRule")).thenReturn("true");

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
        ValueOperations valueOperations = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("paymentItemId")).thenReturn("paymentItemId");

        assertEquals("paymentItemId", redisService.getPaymentItemId());
    }

    @Test
    public void getMainUserAuthorizationToken_should_return_value_from_redis() {
        ValueOperations valueOperations = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("mainUserAuthorizationToken")).thenReturn("mainUserAuthorizationToken");

        assertEquals("mainUserAuthorizationToken", redisService.getMainUserAuthorizationToken());
    }

    @Test
    public void getMainUserProfileId_should_return_value_from_redis() {
        ValueOperations valueOperations = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("mainUserProfileId")).thenReturn("mainUserProfileId");

        assertEquals("mainUserProfileId", redisService.getMainUserProfileId());
    }

    @Test
    public void getMainUserSessionId_should_return_value_from_redis() {
        ValueOperations valueOperations = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("mainUserSessionId")).thenReturn("mainUserSessionId");

        assertEquals("mainUserSessionId", redisService.getMainUserSessionId());
    }

    @Test
    public void getMainUserRememberMeTicket_should_return_value_from_redis() {
        ValueOperations valueOperations = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("mainUserRememberMeTicket")).thenReturn("mainUserRememberMeTicket");

        assertEquals("mainUserRememberMeTicket", redisService.getMainUserRememberMeTicket());
    }

    @Test
    public void setMainUserAuthorization_should_save_values_from_dto() {
        ValueOperations mock = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(mock);

        AuthorizationDTO dto = new AuthorizationDTO("ticket", "profileId", "spaceId", "sessionId", "rememberDeviceTicket", "rememberMeTicket");

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

        AuthorizationDTO dto = new AuthorizationDTO("ticket", "profileId", "spaceId", "sessionId", "rememberDeviceTicket", null);

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
        ValueOperations valueOperations = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("lastUbiUsersStatsFetchTime")).thenReturn("2021-10-10T10:10:10");

        assertEquals("2021-10-10T10:10:10", redisService.getLastUbiUsersStatsFetchTime());
    }
}
