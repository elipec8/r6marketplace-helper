package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.UbiUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.UbiUserEntityId;
import github.ricemonger.marketplace.databases.postgres.mappers.UbiUserPostgresMapper;
import github.ricemonger.marketplace.databases.postgres.repositories.UbiUserPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.UbiUserDatabaseService;
import github.ricemonger.utils.dtos.UbiUser;
import github.ricemonger.utils.exceptions.UbiUserDoesntExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UbiUserPostgresService implements UbiUserDatabaseService {

    private final UbiUserPostgresRepository ubiUserRepository;

    private final UbiUserPostgresMapper mapper;

    @Override
    public void save(UbiUser user) {
        ubiUserRepository.save(mapper.toUbiUserEntity(user));
    }

    @Override
    public void deleteById(String chatId, String email) {
        ubiUserRepository.deleteById(new UbiUserEntityId(chatId, email));
    }

    @Override
    public void deleteAllByChatId(String chatId) {
        ubiUserRepository.deleteAllByChatId(chatId);
    }

    @Override
    public Collection<String> getOwnedItemsIds(String chatId, String email) throws UbiUserDoesntExistException{
        UbiUser user = findById(chatId, email);

        return user.getOwnedItemsIds();
    }

    @Override
    public UbiUser findById(String chatId, String email) throws UbiUserDoesntExistException {
        try {
            UbiUserEntity entity = ubiUserRepository.findById(new UbiUserEntityId(chatId, email)).orElseThrow();
            return mapper.toUbiUser(entity);
        }
        catch (Exception e) {
            throw new UbiUserDoesntExistException("User with chatId " + chatId + " and email " + email + " doesn't exist");
        }
    }

    @Override
    public Collection<UbiUser> findAllByChatId(String chatId) {
        return mapper.toUbiUsers(ubiUserRepository.findAllByChatId(chatId));
    }

    @Override
    public Collection<UbiUser> findAll() {
        return mapper.toUbiUsers(ubiUserRepository.findAll());
    }
}
