package org.sego.moe.service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
	
	List<CardEvent> eventStore = new CopyOnWriteArrayList<>();
	
    public void addCartEvent(CardEvent cartEvent) {
    	messageRepository.save(cartEvent);
    	//eventStore.add(cartEvent);
    	System.out.println(cartEvent + "storeSize: " + eventStore.size());
    	cardOutputEventSource.sendMessage(cartEvent);
    }

}
