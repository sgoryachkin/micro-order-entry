package org.sego.moe.service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.sego.moe.model.CardEvent;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCardServiceImpl implements ShoppingCardService {
	
	List<CardEvent> eventStore = new CopyOnWriteArrayList<>();
	
    public void addCartEvent(CardEvent cartEvent) {
    	eventStore.add(cartEvent);
    	System.out.println(cartEvent + "storeSize: " + eventStore.size());
    	// TODO Fire event
    }

}
