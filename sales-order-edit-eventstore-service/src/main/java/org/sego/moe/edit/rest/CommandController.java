package org.sego.moe.edit.rest;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

import java.util.HashMap;
import java.util.Map;

import org.axonframework.commandhandling.CommandBus;
import org.sego.moe.edit.command.CreateSalesOrderCommand;
import org.sego.moe.edit.command.EditSalesOrderCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommandController {
	
	@Autowired
	CommandBus commandBus;
	
	@GetMapping("create/{id}")
	@ResponseBody public String createSalesOrder(@PathVariable String id) {
		CreateSalesOrderCommand command = new CreateSalesOrderCommand();
		command.setId(id);
		commandBus.dispatch(asCommandMessage(command));
		return "ok";
	}
	
	@GetMapping("edit/{id}")
	@ResponseBody public String editSalesOrder(@PathVariable String id) {
		EditSalesOrderCommand command = new EditSalesOrderCommand();
		Map<Long, String> attr = new HashMap<>();
		attr.put(1l, "v1");
		command.setAttributes(attr);
		commandBus.dispatch(asCommandMessage(command));
		return "ok";
	}

}
