package github.ricemonger.marketplace.databases.postgres.entities.user;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ItemFilterEntityTest {

    @Test


    private boolean entitiesAreEqual(ItemFilterEntity entity1, ItemFilterEntity entity2) {
        return entity1 == entity2 || (entity1 != null && entity2 != null &&
                                      Objects.equals(entity1.getUser(), entity2.getUser()) &&
                                      Objects.equals(entity1.getName(), entity2.getName()) &&
                                      entity1.getFilterType() == entity2.getFilterType() &&
                                      entity1.getIsOwned() == entity2.getIsOwned() &&
                                      Objects.equals(entity1.getItemNamePatterns(), entity2.getItemNamePatterns()) &&
                                      Objects.equals(entity1.getItemTypes(), entity2.getItemTypes()) &&
                                      Objects.equals(entity1.getMinPrice(), entity2.getMinPrice()) &&
                                      Objects.equals(entity1.getMaxPrice(), entity2.getMaxPrice()) &&
                                      Objects.equals(entity1.getMinLastSoldPrice(), entity2.getMinLastSoldPrice()) &&
                                      Objects.equals(entity1.getMaxLastSoldPrice(), entity2.getMaxLastSoldPrice()));
    }
}