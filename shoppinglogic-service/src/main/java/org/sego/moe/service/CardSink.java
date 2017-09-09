package org.sego.moe.service;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface CardSink {
	
    String INPUT = "cardsource";

    @Input(INPUT)
	SubscribableChannel input();

}
