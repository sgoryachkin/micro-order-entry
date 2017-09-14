package org.sego.moe.model.card;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Card {
	
	private Set<Long> offerIds = Collections.newSetFromMap(new ConcurrentHashMap<Long, Boolean>());

	public Set<Long> getOfferIds() {
		return offerIds;
	}

	@Override
	public String toString() {
		return "Card [offerIds=" + offerIds + "]";
	}
	
	

}
