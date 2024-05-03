package github.ricemonger.marketplace.databases.redis.services;

import github.ricemonger.marketplace.authorization.AuthorizationDTO;
import github.ricemonger.marketplace.authorization.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    private final AuthorizationService authorizationService;

    private final MainUserConfiguration mainUserConfiguration;

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
        AuthorizationDTO dto = authorizationService.getUserAuthorizationDTO(mainUserConfiguration.getEmail(), mainUserConfiguration.getPassword());

        setFieldAndExpire("mainUserAuthorizationToken", dto.getTicket());

        setFieldAndExpire("mainUserProfileId", dto.getProfileId());

        setFieldAndExpire("mainUserSessionId", dto.getSessionId());

        setFieldAndExpire("mainUserSpaceId", dto.getSpaceId());

        if (dto.getRememberMeTicket() != null) {
            setFieldAndExpire("mainUserRememberMeTicket", dto.getRememberMeTicket());
        }
    }

    private void setFieldAndExpire(String field, String value) {
        redisTemplate.opsForValue().set(field, value);
        redisTemplate.expire(field, mainUserConfiguration.getExpireTimeout(), TimeUnit.SECONDS);
    }

    public int getExpectedItemCount() {
        String value = redisTemplate.opsForValue().get("expectedItemCount");

        if (value == null) {
            return 0;
        }

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
}
