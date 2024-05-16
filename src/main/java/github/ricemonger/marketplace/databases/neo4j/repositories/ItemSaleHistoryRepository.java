package github.ricemonger.marketplace.databases.neo4j.repositories;

import github.ricemonger.marketplace.databases.neo4j.entities.ItemSaleHistoryEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ItemSaleHistoryRepository extends Neo4jRepository<ItemSaleHistoryEntity, String> {
}
