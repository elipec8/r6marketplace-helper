package github.ricemonger.marketplace.databases.redis.services;

import github.ricemonger.marketplace.authorization.AuthorizationService;
import github.ricemonger.utils.dtos.AuthorizationDTO;
import github.ricemonger.utils.dtos.ConfigResolvedTransactionPeriod;
import github.ricemonger.utils.dtos.ConfigTrades;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public int getExpectedItemCount() {
        String value = redisTemplate.opsForValue().get("expectedItemCount");

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void setExpectedItemCount(int newItemsAmount) {
        redisTemplate.opsForValue().set("expectedItemCount", String.valueOf(newItemsAmount));
    }

    public ConfigResolvedTransactionPeriod getConfigResolvedTransactionPeriod() {
        return new ConfigResolvedTransactionPeriod(getIntField("buyResolvedTransactionPeriod"), getIntField("sellResolvedTransactionPeriod"));
    }

    public void setConfigResolvedTransactionPeriod(ConfigResolvedTransactionPeriod configResolvedTransactionPeriod) {
        redisTemplate.opsForValue().set("buyResolvedTransactionPeriod", String.valueOf(configResolvedTransactionPeriod.getBuyResolvedTransactionPeriod()));
        redisTemplate.opsForValue().set("sellResolvedTransactionPeriod", String.valueOf(configResolvedTransactionPeriod.getSellResolvedTransactionPeriod()));
    }

    public ConfigTrades getConfigTrades() {
        ConfigTrades configTrades = new ConfigTrades();
        configTrades.setSaleExpiresAfterMinutes(getIntField("saleExpiresAfterMinutes"));
        configTrades.setBuySlots(getIntField("buySlots"));
        configTrades.setSellSlots(getIntField("sellSlots"));
        configTrades.setBuyLimit(getIntField("buyLimit"));
        configTrades.setSellLimit(getIntField("sellLimit"));
        configTrades.setResaleLockDurationInMinutes(getIntField("resaleLockDurationInMinutes"));
        configTrades.setPaymentItemId(redisTemplate.opsForValue().get("paymentItemId"));
        configTrades.setFeePercentage(getIntField("feePercentage"));
        configTrades.setTwoFactorAuthenticationRule(Boolean.parseBoolean(redisTemplate.opsForValue().get("twoFactorAuthenticationRule")));
        configTrades.setGameOwnershipRule(Boolean.parseBoolean(redisTemplate.opsForValue().get("gameOwnershipRule")));
        return configTrades;
    }

    public void setConfigTrades(ConfigTrades configTrades) {
        redisTemplate.opsForValue().set("saleExpiresAfterMinutes", String.valueOf(configTrades.getSaleExpiresAfterMinutes()));
        redisTemplate.opsForValue().set("buySlots", String.valueOf(configTrades.getBuySlots()));
        redisTemplate.opsForValue().set("sellSlots", String.valueOf(configTrades.getSellSlots()));
        redisTemplate.opsForValue().set("buyLimit", String.valueOf(configTrades.getBuyLimit()));
        redisTemplate.opsForValue().set("sellLimit", String.valueOf(configTrades.getSellLimit()));
        redisTemplate.opsForValue().set("resaleLockDurationInMinutes", String.valueOf(configTrades.getResaleLockDurationInMinutes()));
        redisTemplate.opsForValue().set("paymentItemId", configTrades.getPaymentItemId());
        redisTemplate.opsForValue().set("feePercentage", String.valueOf(configTrades.getFeePercentage()));
        redisTemplate.opsForValue().set("twoFactorAuthenticationRule", String.valueOf(configTrades.isTwoFactorAuthenticationRule()));
        redisTemplate.opsForValue().set("gameOwnershipRule", String.valueOf(configTrades.isGameOwnershipRule()));
    }

    public String getPaymentItemId() {
        return redisTemplate.opsForValue().get("paymentItemId");
    }

    public String getMainUserAuthorizationToken() {
        return redisTemplate.opsForValue().get("mainUserAuthorizationToken");
    }

    public String getMainUserProfileId() {
        return redisTemplate.opsForValue().get("mainUserProfileId");
    }

    public String getMainUserSessionId() {
        return redisTemplate.opsForValue().get("mainUserSessionId");
    }

    public String getMainUserRememberMeTicket() {
        return redisTemplate.opsForValue().get("mainUserRememberMeTicket");
    }

    public String getMainUserSpaceId() {
        return redisTemplate.opsForValue().get("mainUserSpaceId");
    }

    public void setMainUserAuthorization(AuthorizationDTO dto, int expireTimeout) {
        setFieldAndExpire("mainUserAuthorizationToken", dto.getTicket(), expireTimeout);

        setFieldAndExpire("mainUserProfileId", dto.getProfileId(), expireTimeout);

        setFieldAndExpire("mainUserSessionId", dto.getSessionId(), expireTimeout);

        setFieldAndExpire("mainUserSpaceId", dto.getSpaceId(), expireTimeout);

        setFieldAndExpire("authorizationUpdatedDate", new Date().toString(), expireTimeout);

        if (dto.getRememberMeTicket() != null) {
            setFieldAndExpire("mainUserRememberMeTicket", dto.getRememberMeTicket(), expireTimeout);
        }
    }

    private void setFieldAndExpire(String field, String value,int expireTimeout) {
        redisTemplate.opsForValue().set(field, value);
        redisTemplate.expire(field, expireTimeout, TimeUnit.SECONDS);
    }

    private int getIntField(String field) {
        String value = redisTemplate.opsForValue().get(field);

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException | NullPointerException e) {
            log.error("Error parsing " + field + " from redis, value-" + value);
            return 0;
        }
    }
}
