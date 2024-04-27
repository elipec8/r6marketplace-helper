package github.ricemonger.marketplace.graphs.database.neo4j.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("UbiCredentials")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UbiCredentialsEntity {

    @Id
    private String ubiProfileId;

    private String email;

    private String password;

    private String ubiSessionId;

    private String ubiAuthTicket;

}
