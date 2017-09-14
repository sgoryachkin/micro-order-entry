package org.sego.moe.service;

import org.sego.moe.commons.model.CardEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(CardSource.class)
public class CardOutputEventSource {

    @Autowired
    private CardSource customSource;

    public void sendMessage(CardEvent message) {
        customSource.output().send(MessageBuilder.withPayload(message).build());
    }
}