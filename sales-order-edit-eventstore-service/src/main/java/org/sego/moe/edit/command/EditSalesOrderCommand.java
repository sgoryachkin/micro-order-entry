package org.sego.moe.edit.command;

import java.util.Map;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class EditSalesOrderCommand {
	
    @TargetAggregateIdentifier
    private String id;
    
    private Map<Long, String> attributes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<Long, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<Long, String> attributes) {
		this.attributes = attributes;
	}
	
}
