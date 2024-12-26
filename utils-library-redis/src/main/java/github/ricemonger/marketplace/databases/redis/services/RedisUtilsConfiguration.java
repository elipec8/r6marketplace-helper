package github.ricemonger.marketplace.databases.redis.services;


import github.ricemonger.utils.abstractions.CommonValuesDatabaseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

@Configuration
public class RedisUtilsConfiguration {

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        final RedisTemplate<String, String> template = new RedisTemplate<String, String>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setValueSerializer(new GenericToStringSerializer<String>(String.class));
        return template;
    }

    @Bean
    public CommonValuesDatabaseService commonValuesDatabaseService(RedisTemplate<String, String> redisTemplate) {
        return new RedisService(redisTemplate);
    }
}
