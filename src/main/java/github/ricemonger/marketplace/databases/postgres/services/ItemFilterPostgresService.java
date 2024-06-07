package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.mappers.ItemFilterPostgresMapper;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemFilterPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.ItemFilterDatabaseService;
import github.ricemonger.utils.dtos.ItemFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ItemFilterPostgresService implements ItemFilterDatabaseService {

    private final ItemFilterPostgresRepository repository;

    private final ItemFilterPostgresMapper mapper;

    public Collection<ItemFilter> findAllItemFiltersByChatId(String chatId) {
        return mapper.mapItemFilters(repository.findAllByChatId(chatId));
    }

    public void saveItemFilter(ItemFilter filter) {
        repository.save(mapper.mapItemFilterEntity(filter));
    }
}
