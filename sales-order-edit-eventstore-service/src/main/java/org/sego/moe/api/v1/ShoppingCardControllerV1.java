package org.sego.moe.api.v1;

import org.sego.moe.sales.order.edit.commons.model.SalesOrderEditEvent;
import org.sego.moe.service.ShoppingCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

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
    
    @GetMapping(path = "/events/{salesOrderId}")
    public Flux<SalesOrderEditEvent> getSalesOrderEvents(@PathVariable Long salesOrderId) {
    	System.out.println("getSalesOrderEvents " + salesOrderId);
    	return shoppingCardService.getSalesOrderEvents(salesOrderId);
    }

}