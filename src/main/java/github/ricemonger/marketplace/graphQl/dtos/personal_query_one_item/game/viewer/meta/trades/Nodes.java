package github.ricemonger.marketplace.graphQl.dtos.personal_query_one_item.game.viewer.meta.trades;

import github.ricemonger.marketplace.graphQl.dtos.personal_query_one_item.game.viewer.meta.trades.nodes.Payment;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_one_item.game.viewer.meta.trades.nodes.PaymentOptions;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_one_item.game.viewer.meta.trades.nodes.PaymentProposal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Nodes {
    private String tradeId;
    private String state;
    private String category;
    private String expiresAt;
    private String lastModifiedAt;
    private Payment payment;
    private PaymentOptions[] paymentOptions;
    private PaymentProposal paymentProposal;
}
