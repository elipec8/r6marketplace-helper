package github.ricemonger.marketplace.updateFetcher.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "items-update-fetcher", url = "${ubi.urls.updateItems}")
public interface ItemsUpdateFetcherFeignClient {

    @PostMapping
    public String fetchUpdate(
            @RequestHeader("Authorization") String authorization,
            @RequestHeader("Ubi-Regionid") String regionId,
            @RequestHeader("Ubi-localecode") String localeCode,
            @RequestHeader("Ubi-Appid") String ubiAppId,
            @RequestHeader("Ubi-Sessionid") String sessionId,
            @RequestHeader("Ubi-Profileid") String profileId,
            @RequestBody String payload);
}
