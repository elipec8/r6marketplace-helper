package github.ricemonger.marketplace.updateFetcher.feign;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ItemsUpdateFetcherFeignClientConfiguration {
    @Bean
    public ErrorDecoder itemsUpdateFetcherErrorDecoder() {
        return new ItemsUpdateFetcherErrorDecoder();
    }
}
