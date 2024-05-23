package github.ricemonger.marketplace.databases.neo4j.repositories;

import github.ricemonger.marketplace.databases.neo4j.entities.ItemSaleHistoryNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ItemSaleHistoryNodeRepository extends Neo4jRepository<ItemSaleHistoryNode, String> {
}
