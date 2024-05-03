package github.ricemonger.marketplace.databases.neo4j.repositories;

import github.ricemonger.marketplace.databases.neo4j.entities.ItemEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ItemRepository extends Neo4jRepository<ItemEntity, String> {
}
