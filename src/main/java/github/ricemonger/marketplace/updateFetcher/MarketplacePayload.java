package github.ricemonger.marketplace.updateFetcher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketplacePayload {

    public static final int MAX_LIMIT = 500;

    private MarketplaceOperationName marketplaceOperationName;

    private List<String> filterByTypes;

    private List<String> filterByTags;

    private int limit;

    private int offset;

    private String sortByField;

    private String sortByOrderType;

    private String spaceId;

    private boolean withOwnership;

}
