package github.ricemonger.utilslibrarykafka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaUtilsConfiguration {

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory) {
        return new KafkaTemplate<String, Object>(producerFactory);
    }

    @Bean
    public NotificationKafkaProducer notificationKafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        return new NotificationKafkaProducer(kafkaTemplate);
    }
}
