package github.ricemonger.marketplace.updateFetcher;

public class MarketplacePayloadMapper {

    public static FetchUpdateRequestVariables toPayload(String payload) {
        return new FetchUpdateRequestVariables();
    }

    public static String fromPayload(FetchUpdateRequestVariables payload) {
        return "";
    }
}
