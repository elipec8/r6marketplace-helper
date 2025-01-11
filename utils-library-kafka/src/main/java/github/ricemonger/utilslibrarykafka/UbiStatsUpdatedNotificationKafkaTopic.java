package github.ricemonger.utilslibrarykafka;

import org.apache.kafka.clients.admin.NewTopic;

public class UbiStatsUpdatedNotificationKafkaTopic extends NewTopic {
    public UbiStatsUpdatedNotificationKafkaTopic(String name, int numPartitions, short replicationFactor) {
        super(name, numPartitions, replicationFactor);
    }
}
