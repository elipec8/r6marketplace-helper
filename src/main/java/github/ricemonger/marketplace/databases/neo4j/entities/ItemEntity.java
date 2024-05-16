package github.ricemonger.marketplace.databases.neo4j.entities;

import github.ricemonger.marketplace.databases.neo4j.enums.ItemType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Node("Item")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemEntity {

    @Id
    private String itemFullId;

    private String assetUrl;

    private String name;

    private List<String> tags;

    private ItemType type;

    private int maxBuyPrice;

    private int buyOrders;

    private int minSellPrice;

    private int sellOrders;

    private int expectedProfit;

    private int expectedProfitPercentage;

    private Date lastSoldAt;

    private int lastSoldPrice;
}
