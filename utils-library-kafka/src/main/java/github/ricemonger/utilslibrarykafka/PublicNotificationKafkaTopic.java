package github.ricemonger.utilslibrarykafka;

import org.apache.kafka.clients.admin.NewTopic;

public class PublicNotificationKafkaTopic extends NewTopic {
    public PublicNotificationKafkaTopic(String name, int numPartitions, short replicationFactor) {
        super(name, numPartitions, replicationFactor);
    }
}
