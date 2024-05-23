package github.ricemonger.marketplace.databases.neo4j.repositories;

import github.ricemonger.marketplace.databases.neo4j.entities.ItemNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ItemNodeRepository extends Neo4jRepository<ItemNode, String> {
}
