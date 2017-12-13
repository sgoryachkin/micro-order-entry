package org.sego.moe.frontend.api.v1;

import java.util.UUID;

import org.sego.moe.frontend.model.OrderItem;
import org.sego.moe.frontend.model.SalesOrder;
import org.sego.moe.frontend.service.CatalogService;
import org.sego.moe.frontend.service.QuotationService;
import org.sego.moe.sales.order.edit.commons.model.SalesOrderEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1")
public class QuotationControllerV1 {
	
	@Autowired
	QuotationService quotationService;
	
	@Autowired
	CatalogService catalogService;

    @GetMapping(path = "/quote/{quoteId}")
    public SalesOrder getQuote(@PathVariable UUID quoteId) {
    	return quotationService.getSalesOrder(quoteId);
    }
    
    @GetMapping(path = "/quote/{quoteId}/addtest")
    public String addTest(@PathVariable UUID quoteId) {
    	OrderItem oi = new OrderItem();
    	oi.setOfferId(UUID.fromString("5f280bf1-23e2-4728-9987-68f91e67e129"));
    	oi.setQuantity(5);
    	quotationService.addOrderItem(quoteId, null, oi);
    	return "success";
    }
    
    @PostMapping(path = "/quote/{quoteId}/orderItem")
    public void addOrderItem(@PathVariable UUID quoteId, @RequestBody OrderItem orderItem) {
    	quotationService.addOrderItem(quoteId, null, orderItem);
    }
    
    @PostMapping(path = "/quote/{quoteId}/{parentOrderItem}/orderItem")
    public void addOrderItem(@PathVariable UUID quoteId, @PathVariable String parentOrderItem, @RequestBody OrderItem orderItem) {
    	quotationService.addOrderItem(quoteId, parentOrderItem, orderItem);
    }
    
    @PostMapping(path = "/events")
    public void addCartEvent(@RequestBody SalesOrderEditEvent cartEvent) {
    	System.out.println("PUB: " + cartEvent);
    	quotationService.receiveMessage(cartEvent);
    }

}