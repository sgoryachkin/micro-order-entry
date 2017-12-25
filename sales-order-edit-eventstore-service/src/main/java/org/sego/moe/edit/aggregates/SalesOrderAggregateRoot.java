package org.sego.moe.edit.aggregates;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.sego.moe.edit.command.CreateSalesOrderCommand;
import org.sego.moe.edit.command.EditSalesOrderCommand;
import org.sego.moe.sales.order.edit.commons.event.SalesOrderCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aggregate
public class SalesOrderAggregateRoot {
	
	private static final Logger LOG = LoggerFactory.getLogger(SalesOrderAggregateRoot.class);
	
    @AggregateIdentifier
    private String id;
    
    public SalesOrderAggregateRoot() {
     
    }
    
    @CommandHandler
    public SalesOrderAggregateRoot(CreateSalesOrderCommand command) {
        LOG.debug("CreateSalesOrderCommand '{}'", command.getId());
        apply(new SalesOrderCreateEvent(command.getId()));
    }
    
    @CommandHandler
    public void handle(EditSalesOrderCommand command) {
    	LOG.debug("EditSalesOrderCommand '{}'", command.getId());
        apply(new EditSalesOrderCommand());
    }
    
    @EventSourcingHandler
    public void on(SalesOrderCreateEvent event) {
        this.id = event.getId();
        LOG.debug("Applied: 'SalesOrderCreateEvent' [{}]", event.getId());
    }

    @EventSourcingHandler
    public void on(EditSalesOrderCommand event) {
        LOG.debug("Applied: 'EditSalesOrderCommand' [{}]", event.getId());
    }

}
