package github.ricemonger.marketplace.graphs.database.redis.services;

import github.ricemonger.marketplace.authorization.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    private final AuthorizationService authorizationService;

    public String getMainUserAuthorizationTokenOrAuthorizeAndGet(){
        String token = redisTemplate.opsForValue().get("mainUserAuthorizationToken");

        if(token == null){
            token = authorizationService.createAndGetMainUserAuthorizationToken();
            redisTemplate.opsForValue().set("mainUserAuthorizationToken", token);
            if(token == null){
                throw new InvalidUbiCredentialsException("Failed to get or create main user authorization token");
            }
        }

        return token;
    }

}
