package github.ricemonger.marketplace.databases.neo4j.repositories;

import github.ricemonger.marketplace.databases.neo4j.entities.TelegramInputValuesEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface TelegramInputValuesRepository extends Neo4jRepository<TelegramInputValuesEntity, String>{

    void deleteAllByOwnerChatId(String chatId);

    TelegramInputValuesEntity findByOwnerChatIdAndInputState(String chatId, String inputState);
}
