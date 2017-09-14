package org.sego.moe.frontend.api.v1;

import org.sego.moe.frontend.model.OrderItem;
import org.sego.moe.frontend.model.SalesOrder;
import org.sego.moe.frontend.service.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1")
public class QuotationControllerV1 {
	
	@Autowired
	QuotationService quotationService;

    @RequestMapping(path = "/quote/{quoteId}", method = RequestMethod.GET)
    public SalesOrder getQuote(@PathVariable String quoteId) {
    	return quotationService.getSalesOrder(Long.valueOf(quoteId));
    }
    
    @RequestMapping(path = "/quote/{quoteId}/orderItem", method = RequestMethod.POST)
    public void addOrderItem(@PathVariable String quoteId, @RequestBody OrderItem orderItem) {
    	quotationService.addOrderItem(Long.valueOf(quoteId), null, orderItem);
    }
    
    @RequestMapping(path = "/quote/{quoteId}/{parentOrderItem}/orderItem", method = RequestMethod.POST)
    public void addOrderItem(@PathVariable String quoteId, @PathVariable String parentOrderItem, @RequestBody OrderItem orderItem) {
    	quotationService.addOrderItem(Long.valueOf(quoteId), parentOrderItem, orderItem);
    }

}