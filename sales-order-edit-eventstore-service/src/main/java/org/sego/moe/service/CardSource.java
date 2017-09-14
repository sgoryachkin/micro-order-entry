package org.sego.moe.service;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface CardSource {
	
    String OUTPUT = "cardsource";

    @Output(CardSource.OUTPUT)
    MessageChannel output();

}
