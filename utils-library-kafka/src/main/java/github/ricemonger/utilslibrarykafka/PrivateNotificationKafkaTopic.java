package github.ricemonger.utilslibrarykafka;

import org.apache.kafka.clients.admin.NewTopic;

public class PrivateNotificationKafkaTopic extends NewTopic {
    public PrivateNotificationKafkaTopic(String name, int numPartitions, short replicationFactor) {
        super(name, numPartitions, replicationFactor);
    }
}
