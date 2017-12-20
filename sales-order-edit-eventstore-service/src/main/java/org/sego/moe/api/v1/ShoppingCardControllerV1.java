package org.sego.moe.api.v1;

import java.util.UUID;

import org.sego.moe.sales.order.edit.commons.model.SalesOrderEditEvent;
import org.sego.moe.service.ShoppingCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/v1")
public class ShoppingCardControllerV1 {

	@Autowired
    private ShoppingCardService shoppingCardService;

    @PostMapping(path = "/events")
    public void addCartEvent(@RequestBody SalesOrderEditEvent cartEvent) {
    	System.out.println("addCartEvent " + cartEvent.toString());
        shoppingCardService.addEditSalesOrderEvent(cartEvent);
    }
    
    
    @GetMapping(path = "/events/{salesOrderId}/test")
    public void addCartEvent(@PathVariable UUID salesOrderId) {
    	System.out.println("test");
    	SalesOrderEditEvent soEvent = new SalesOrderEditEvent();
    	soEvent.setSalesOrderId(salesOrderId);
    	for (int i = 0; i < 100; i++) {
    		shoppingCardService.addEditSalesOrderEvent(soEvent);
		}
    }
    
    @GetMapping(path = "/events/{salesOrderId}")
    public Flux<SalesOrderEditEvent> getSalesOrderEvents(@PathVariable UUID salesOrderId, @RequestParam(required = false) Long fromTime) {
    	System.out.println("getSalesOrderEvents " + salesOrderId);
    	if (fromTime != null) {
    		return shoppingCardService.getSalesOrderEvents(salesOrderId, fromTime);
    	} else {
    		return shoppingCardService.getSalesOrderEvents(salesOrderId);
    	}
    }
    
    @GetMapping(path = "/events/{salesOrderId}/count")
    public Mono<Long> getCountSalesOrderEvents(@PathVariable UUID salesOrderId, @RequestParam(required = false) Long fromTime) {
    	System.out.println("getCountSalesOrderEvents " + salesOrderId);
    	if (fromTime != null) {
    		return shoppingCardService.getCountSalesOrderEvents(salesOrderId, fromTime);
    	} else {
    		return shoppingCardService.getCountSalesOrderEvents(salesOrderId);
    	}
    }
    

}