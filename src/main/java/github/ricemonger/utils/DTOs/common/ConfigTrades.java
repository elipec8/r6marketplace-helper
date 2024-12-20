package github.ricemonger.utils.DTOs.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigTrades {
    private Integer saleExpiresAfterMinutes;
    private Integer buySlots;
    private Integer sellSlots;
    private Integer buyLimit;
    private Integer sellLimit;
    private Integer resaleLockDurationInMinutes;
    private String paymentItemId;
    private Integer feePercentage;
    private Boolean twoFactorAuthenticationRule;
    private Boolean gameOwnershipRule;
}
