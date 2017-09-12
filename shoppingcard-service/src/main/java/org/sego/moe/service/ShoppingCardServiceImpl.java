package org.sego.moe.service;

import org.sego.moe.commons.model.CardEvent;
import org.sego.moe.dao.SalesOrderMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCardServiceImpl implements ShoppingCardService {
	
	@Autowired
	CardOutputEventSource cardOutputEventSource;
	
	@Autowired
	SalesOrderMessageRepository messageRepository;
	
    public void addCartEvent(CardEvent cartEvent) {
    	messageRepository.save(cartEvent);
    	cardOutputEventSource.sendMessage(cartEvent);
    }

}
