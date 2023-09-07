package com.cbdavid.pubsub.controllers;

import com.cbdavid.pubsub.outbound.OutboundConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/send")
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    @Autowired
    private OutboundConfiguration.PubsubOutboundGateway messagingGateway;

    @PostMapping("/")
    public void sendMessage(@RequestBody String message){
        log.info("Send this message to outbound channgel {}", message);
        messagingGateway.sendToPubsub(message);
    }

}
