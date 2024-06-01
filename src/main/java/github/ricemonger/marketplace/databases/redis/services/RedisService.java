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

    private final AuthorizationService authorizationService;

    private final MainUserConfiguration mainUserConfiguration;

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

    public String getMainUserAuthorizationToken() {
        return getOrCreateMainUserField("mainUserAuthorizationToken");
    }

    public String getMainUserProfileId() {
        return getOrCreateMainUserField("mainUserProfileId");
    }

    public String getMainUserSessionId() {
        return getOrCreateMainUserField("mainUserSessionId");
    }

    public String getMainUserRememberMeTicket() {
        return getOrCreateMainUserField("mainUserRememberMeTicket");
    }

    public String getMainUserSpaceId() {
        return getOrCreateMainUserField("mainUserSpaceId");
    }

    private String getOrCreateMainUserField(String field) {
        String value = redisTemplate.opsForValue().get(field);

        if (value == null) {
            createMainUserAuthHeaders();

            value = redisTemplate.opsForValue().get(field);
        }

        return value;
    }

    private void createMainUserAuthHeaders() {
        AuthorizationDTO dto = authorizationService.authorizeAndGetDTO(mainUserConfiguration.getEmail(), mainUserConfiguration.getPassword());

        setFieldAndExpire("mainUserAuthorizationToken", dto.getTicket());

        setFieldAndExpire("mainUserProfileId", dto.getProfileId());

        setFieldAndExpire("mainUserSessionId", dto.getSessionId());

        setFieldAndExpire("mainUserSpaceId", dto.getSpaceId());

        setFieldAndExpire("authorizationUpdatedDate", new Date().toString());

        if (dto.getRememberMeTicket() != null) {
            setFieldAndExpire("mainUserRememberMeTicket", dto.getRememberMeTicket());
        }
    }

    private void setFieldAndExpire(String field, String value) {
        redisTemplate.opsForValue().set(field, value);
        redisTemplate.expire(field, mainUserConfiguration.getExpireTimeout(), TimeUnit.SECONDS);
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
