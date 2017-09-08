package org.sego.moe.model;

public class CardEvent {
	
	private Long cardId;
	private Long offerId;
	
	public Long getCardId() {
		return cardId;
	}
	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}
	public Long getOfferId() {
		return offerId;
	}
	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}
	
	@Override
	public String toString() {
		return "CardEvent [cardId=" + cardId + ", offerId=" + offerId + "]";
	}
	

}
