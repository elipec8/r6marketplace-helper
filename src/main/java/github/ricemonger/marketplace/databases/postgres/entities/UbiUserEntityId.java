package github.ricemonger.marketplace.databases.postgres.entities;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UbiUserEntityId {
    private String chatId;

    private String email;
}
