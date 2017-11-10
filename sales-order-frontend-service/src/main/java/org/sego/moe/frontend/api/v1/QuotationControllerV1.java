package org.sego.moe.frontend.api.v1;

import java.util.Map;

import org.sego.moe.frontend.model.Offer;
import org.sego.moe.frontend.model.OrderItem;
import org.sego.moe.frontend.model.SalesOrder;
import org.sego.moe.frontend.service.CatalogService;
import org.sego.moe.frontend.service.QuotationService;
import org.sego.moe.sales.order.edit.commons.model.SalesOrderEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/v1")
public class QuotationControllerV1 {
	
	@Autowired
	QuotationService quotationService;
	
	@Autowired
	CatalogService catalogService;

    @GetMapping(path = "/quote/{quoteId}")
    public SalesOrder getQuote(@PathVariable String quoteId) {
    	return quotationService.getSalesOrder(Long.valueOf(quoteId));
    }
    
    @GetMapping(path = "/quote/{quoteId}/addtest")
    public String addTest(@PathVariable String quoteId) {
    	OrderItem oi = new OrderItem();
    	oi.setOfferId("1");
    	oi.setQuantity(5);
    	quotationService.addOrderItem(Long.valueOf(quoteId), null, oi);
    	return "success";
    }
    
    @PostMapping(path = "/quote/{quoteId}/orderItem")
    public void addOrderItem(@PathVariable String quoteId, @RequestBody OrderItem orderItem) {
    	quotationService.addOrderItem(Long.valueOf(quoteId), null, orderItem);
    }
    
    @PostMapping(path = "/quote/{quoteId}/{parentOrderItem}/orderItem")
    public void addOrderItem(@PathVariable String quoteId, @PathVariable String parentOrderItem, @RequestBody OrderItem orderItem) {
    	quotationService.addOrderItem(Long.valueOf(quoteId), parentOrderItem, orderItem);
    }
    
    @PostMapping(path = "/events")
    public void addCartEvent(@RequestBody SalesOrderEditEvent cartEvent) {
    	System.out.println("PUB: " + cartEvent);
    	quotationService.receiveMessage(cartEvent);
    }

}