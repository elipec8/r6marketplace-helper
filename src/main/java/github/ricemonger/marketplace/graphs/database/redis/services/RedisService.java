package github.ricemonger.marketplace.graphs.database.redis.services;

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

    public String getMainUserAuthorizationToken(){
        return getOrCreateMainUserField("mainUserAuthorizationToken");
    }

    public String getMainUserProfileId(){
        return getOrCreateMainUserField("mainUserProfileId");
    }

    public String getMainUserSessionId(){
        return getOrCreateMainUserField("mainUserSessionId");
    }

    public String getMainUserRememberMeTicket(){
        return getOrCreateMainUserField("mainUserRememberMeTicket");
    }

    public String getMainUserSpaceId(){
        return getOrCreateMainUserField("mainUserSpaceId");
    }

    private String getOrCreateMainUserField(String field){
        String value = redisTemplate.opsForValue().get(field);

        if(value == null){
            createMainUserAuthHeaders();

            value = redisTemplate.opsForValue().get(field);
        }

        return value;
    }

    private void createMainUserAuthHeaders(){
                AuthorizationDTO dto = authorizationService.getUserAuthorizationDTO(mainUserConfiguration.getEmail(), mainUserConfiguration.getPassword());
                redisTemplate.opsForValue().set("mainUserAuthorizationToken", dto.getTicket());
                redisTemplate.expire("mainUserAuthorizationToken", 9000, TimeUnit.SECONDS);
                redisTemplate.opsForValue().set("mainUserProfileId", dto.getProfileId());
                redisTemplate.opsForValue().set("mainUserSessionId", dto.getSessionId());
                redisTemplate.opsForValue().set("mainUserSpaceId", dto.getSpaceId());
                if(dto.getRememberMeTicket() != null) {
                    redisTemplate.opsForValue().set("mainUserRememberMeTicket", dto.getRememberMeTicket());
                }
    }
}
