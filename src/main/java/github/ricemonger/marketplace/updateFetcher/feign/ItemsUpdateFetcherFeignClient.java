package github.ricemonger.marketplace.updateFetcher.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "itemsUpdateFetcher"
        , url = "${ubi.urls.updateItems}"
        , configuration = ItemsUpdateFetcherFeignClientConfiguration.class)
public interface ItemsUpdateFetcherFeignClient{

    @PostMapping
    String fetchItems(
            @RequestHeader("Authorization") String ubiToken,
            @RequestHeader("Ubi-Localecode") String ubiLocaleCode,
            @RequestHeader("Ubi-Regionid") String ubiRegionId,
            @RequestHeader("Ubi-Appid") String ubiAppId,
            @RequestHeader("Ubi-Profileid") String ubiProfileId,
            @RequestHeader("Ubi-Sessionid") String ubiSessionId,
            @RequestBody String payload);
}
