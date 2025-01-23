package github.ricemonger.fetching_acounts_manager.services.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UbiUserCredentials {
    private String email;
    private String password;
}
