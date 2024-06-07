package github.ricemonger.marketplace.databases.postgres.mappers;

import github.ricemonger.marketplace.databases.postgres.entities.ItemSaleEntity;
import github.ricemonger.utils.dtos.ItemSale;
import github.ricemonger.utils.dtos.SoldItemDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class ItemSalePostgresMapper {

    public Collection<ItemSaleEntity> mapItemSaleEntities(Collection<? extends SoldItemDetails> items) {
        if (items == null || items.isEmpty()) {
            return List.of();
        } else {
            return items.stream().map(this::mapItemSaleEntity).toList();
        }
    }

    public ItemSaleEntity mapItemSaleEntity(SoldItemDetails item) {
        ItemSaleEntity entity = new ItemSaleEntity();
        entity.setItemId(item.getItemId());
        entity.setSoldAt(item.getLastSoldAt());
        entity.setPrice(item.getLastSoldPrice());
        return entity;
    }

    public Collection<ItemSale> mapItemSales(Collection<ItemSaleEntity> entities) {
        if (entities == null || entities.isEmpty()) {
            return List.of();
        } else {
            return entities.stream().map(this::mapItemSale).toList();
        }
    }

    public ItemSale mapItemSale(ItemSaleEntity entity) {
        ItemSale item = new ItemSale();
        item.setItemId(entity.getItemId());
        item.setSoldAt(entity.getSoldAt());
        item.setPrice(entity.getPrice());
        return item;
    }
}
