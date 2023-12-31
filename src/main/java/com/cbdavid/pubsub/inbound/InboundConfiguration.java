package com.cbdavid.pubsub.inbound;

import com.cbdavid.pubsub.configuration.PubSubConfiguration;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.AckMode;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage;
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class InboundConfiguration {

    private final PubSubConfiguration pubSubConfiguration;

    @Bean
    public MessageChannel inputChannel() {
        return new DirectChannel();
    }

    @Bean
    public PubSubInboundChannelAdapter messageChannelAdapter(
            @Qualifier("inputChannel") MessageChannel inputChannel,
            PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter =
                new PubSubInboundChannelAdapter(pubSubTemplate, pubSubConfiguration.getSubscription());
        adapter.setOutputChannel(inputChannel);
        //adapter.setAckMode(AckMode.MANUAL);
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "inputChannel")
    public MessageHandler messageReceiver() {
        return message -> {
            log.info("Nuevo mensaje recibido, con el Payload: " + new String((byte[]) message.getPayload()));
            BasicAcknowledgeablePubsubMessage originalMessage =
                    message.getHeaders().get(GcpPubSubHeaders.ORIGINAL_MESSAGE,
                            BasicAcknowledgeablePubsubMessage.class);
            //originalMessage.ack();
        };
    }

}
