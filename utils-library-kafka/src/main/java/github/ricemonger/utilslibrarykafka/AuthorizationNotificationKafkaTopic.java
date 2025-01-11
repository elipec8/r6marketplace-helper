package github.ricemonger.utilslibrarykafka;

import org.apache.kafka.clients.admin.NewTopic;

public class AuthorizationNotificationKafkaTopic extends NewTopic {
    public AuthorizationNotificationKafkaTopic(String name, int numPartitions, short replicationFactor) {
        super(name, numPartitions, replicationFactor);
    }
}
