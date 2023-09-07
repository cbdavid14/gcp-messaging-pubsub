package com.cbdavid.pubsub.outbound;

import com.cbdavid.pubsub.configuration.PubSubConfiguration;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;

@Configuration
@RequiredArgsConstructor
public class OutboundConfiguration {

    private final PubSubConfiguration pubSubConfiguration;

    @Bean
    @ServiceActivator(inputChannel = "outputChannel")
    public MessageHandler messageSender(PubSubTemplate pubsubTemplate) {
        return new PubSubMessageHandler(pubsubTemplate, pubSubConfiguration.getTopic());
    }

    @MessagingGateway(defaultRequestChannel = "outputChannel")
    public interface PubsubOutboundGateway {
        void sendToPubsub(String text);
    }
}
