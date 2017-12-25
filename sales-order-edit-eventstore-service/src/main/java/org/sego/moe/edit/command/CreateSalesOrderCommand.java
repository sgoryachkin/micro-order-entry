package org.sego.moe.edit.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class CreateSalesOrderCommand {
	
    @TargetAggregateIdentifier
    private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
    
}
