package github.ricemonger.utilslibrarykafka;

import org.apache.kafka.clients.admin.NewTopic;

public class PublicAllUsersNotificationKafkaTopic extends NewTopic {
    public PublicAllUsersNotificationKafkaTopic(String name, int numPartitions, short replicationFactor) {
        super(name, numPartitions, replicationFactor);
    }
}
