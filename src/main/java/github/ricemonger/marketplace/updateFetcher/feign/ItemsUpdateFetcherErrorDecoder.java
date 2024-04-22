package github.ricemonger.marketplace.updateFetcher.feign;

import feign.Response;
import feign.codec.ErrorDecoder;

public class ItemsUpdateFetcherErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        return null;
    }
}
