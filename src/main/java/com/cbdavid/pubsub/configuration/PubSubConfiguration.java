package com.cbdavid.pubsub.configuration;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "messaging.pubsub")
public class PubSubConfiguration {
    private String Topic;
    private String subscription;
}
