package github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user;


import github.ricemonger.marketplace.databases.postgres.repositories.UserPostgresRepository;
import github.ricemonger.utilspostgresschema.full_entities.user.TelegramUserEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramUserEntityMapper {

    private final UserPostgresRepository userRepository;

    public TelegramUserEntity createNewEntityForNewUser(String chatId) {
        UserEntity user = userRepository.save(new UserEntity());
        TelegramUserEntity entity = new TelegramUserEntity();
        entity.setChatId(chatId);
        entity.setUser(user);
        return entity;
    }
}
