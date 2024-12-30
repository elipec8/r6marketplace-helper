package github.ricemonger.marketplace.databases.postgres.custom_repositories.tg_user_item_filter_service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemFilterUserIdEntityId {
    private UserIdEntity user;
    private String name;
}
