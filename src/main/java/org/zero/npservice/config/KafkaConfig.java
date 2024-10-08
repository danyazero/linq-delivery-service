package org.zero.npservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.zero.npservice.model.delivery.novaPost.NPDeliveryStatus;
import org.zero.npservice.model.kafka.DeliveryEvent;
import org.zero.npservice.model.kafka.Event;
import org.zero.npservice.model.kafka.OrderEvent;
import org.zero.npservice.model.kafka.PaymentEvent;
import org.zero.npservice.model.kafka.data.Delivery;
import org.zero.npservice.model.kafka.data.Order;
import org.zero.npservice.model.kafka.data.Payment;
import org.zero.npservice.utils.KafkaPropsProvider;

@Configuration
public class KafkaConfig {

  public ConsumerFactory<String, OrderEvent> orderConsumerFactory() {
    return KafkaPropsProvider.consumer(OrderEvent.class, Order.class, Event.class);
  }

  @Bean("orderListenerContainerFactory")
  public KafkaListenerContainerFactory<?> orderListenerContainerFactory() {
    var factory = new ConcurrentKafkaListenerContainerFactory<String, OrderEvent>();
    factory.setConsumerFactory(orderConsumerFactory());

    return factory;
  }

  public ConsumerFactory<String, PaymentEvent> paymentConsumerFactory() {
    return KafkaPropsProvider.consumer(PaymentEvent.class, Payment.class, Event.class);
  }

  @Bean("paymentListenerContainerFactory")
  public KafkaListenerContainerFactory<?> paymentListenerContainerFactory() {
    var factory = new ConcurrentKafkaListenerContainerFactory<String, PaymentEvent>();
    factory.setConsumerFactory(paymentConsumerFactory());

    return factory;
  }

  @Bean
  public ProducerFactory<String, DeliveryEvent> deliveryProducerFactory(
      KafkaProperties kafkaProperties) {
    return KafkaPropsProvider.producer(DeliveryEvent.class, Delivery.class, NPDeliveryStatus.class);
  }

  @Bean
  public KafkaTemplate<String, DeliveryEvent> deliveryKafkaTemplate(
      ProducerFactory<String, DeliveryEvent> producerFactory) {
    return new KafkaTemplate<>(producerFactory);
  }

  @Bean
  public NewTopic deliveryTopic() {
    return new NewTopic("delivery", 1, (short) 1);
  }
}
