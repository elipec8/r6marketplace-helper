package github.ricemonger.marketplace.updateFetcher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraphQlRequestVariables {

    public static final int MAX_LIMIT = 500;

    //R6S credits id
    public static final String PAYMENT_ITEM_ID = "9ef71262-515b-46e8-b9a8-b6b6ad456c67";

    public static final String SORT_BY_DIRECTION = "DESC";

    public final static String DEFAULT_SPACE_ID = "0d2ae42d-4c27-4cb7-af6c-2099062302bb";

    private MarketplaceOperationName marketplaceOperationName;

    private boolean withOwnership;

    private String spaceId;

    private int limit;

    private int offset;

    private List<String> filterByTypes;

    private List<String> filterByTags;

    private SortingField sortByField;

    private OrderType sortByOrderType;



}
