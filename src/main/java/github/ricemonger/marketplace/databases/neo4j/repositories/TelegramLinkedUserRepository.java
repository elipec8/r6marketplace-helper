package github.ricemonger.marketplace.databases.neo4j.repositories;

import github.ricemonger.marketplace.databases.neo4j.entities.TelegramLinkedUserEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface TelegramLinkedUserRepository extends Neo4jRepository<TelegramLinkedUserEntity, String> {
}
