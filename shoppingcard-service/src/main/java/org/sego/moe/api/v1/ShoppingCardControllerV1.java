package org.sego.moe.api.v1;

import org.sego.moe.commons.model.CardEvent;
import org.sego.moe.service.ShoppingCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1")
public class ShoppingCardControllerV1 {

	@Autowired
    private ShoppingCardService shoppingCardService;

    @RequestMapping(path = "/events", method = RequestMethod.POST)
    public void addCartEvent(@RequestBody CardEvent cartEvent) {
        shoppingCardService.addCartEvent(cartEvent);
    }

}