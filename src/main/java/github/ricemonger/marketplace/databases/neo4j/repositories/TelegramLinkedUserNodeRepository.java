package github.ricemonger.marketplace.databases.neo4j.repositories;

import github.ricemonger.marketplace.databases.neo4j.entities.TelegramLinkedUserNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface TelegramLinkedUserNodeRepository extends Neo4jRepository<TelegramLinkedUserNode, String> {
}
