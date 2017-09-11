package org.sego.moe.frontend.api.v1;

import org.sego.moe.frontend.model.SalesOrder;
import org.sego.moe.frontend.service.CardOutputEventSink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1")
public class QuotationControllerV1 {
	
	@Autowired
	CardOutputEventSink quotationService;


    @RequestMapping(path = "/quote/{quoteId}", method = RequestMethod.GET)
    public SalesOrder getQuote(String quoteId) {
    	return quotationService.getSalesOrder(Long.valueOf(quoteId));
    }
    
    @RequestMapping(path = "/hello", method = RequestMethod.GET)
    public String hello() throws Exception {
        return "Hello";
    }

}