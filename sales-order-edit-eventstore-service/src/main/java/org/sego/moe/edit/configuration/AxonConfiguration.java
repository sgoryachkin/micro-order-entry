package org.sego.moe.edit.configuration;

import java.util.Arrays;
import java.util.List;

import org.axonframework.commandhandling.AggregateAnnotationCommandHandler;
import org.axonframework.commandhandling.AsynchronousCommandBus;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.EventListener;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventsourcing.DomainEventMessage;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.DomainEventStream;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.TrackingEventStream;
import org.axonframework.eventsourcing.eventstore.TrackingToken;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.axonframework.messaging.MessageHandler;
import org.axonframework.spring.commandhandling.gateway.CommandGatewayFactoryBean;
import org.sego.moe.edit.aggregates.SalesOrderAggregateRoot;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.common.Registration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class AxonConfiguration {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AxonConfiguration.class);

   // @Autowired
   // public void configure(EventHandlingConfiguration configuration) {
   //     configuration.registerTrackingProcessor("TransactionHistory");
   // }
    
    
	@Bean
	public CommandBus commandBus() {
		AsynchronousCommandBus commandBus = new AsynchronousCommandBus() {
			@Override
			public <C, R> void dispatch(CommandMessage<C> command, CommandCallback<? super C, R> callback) {
				LOGGER.info("CommandBus:dispatch({}, {})", command.getPayload(), callback);
				super.dispatch(command, callback);
			}
			@Override
			public Registration subscribe(String commandName, MessageHandler<? super CommandMessage<?>> handler) {
				LOGGER.info("CommandBus:subscribe({}, {})", commandName, handler);
				return super.subscribe(commandName, handler);
			}
		};
//		commandBus.setHandlerInterceptors(Collections.singletonList(new BeanValidationInterceptor()));
//		commandBus.setTransactionManager(new SpringTransactionManager(transactionManager));
		return commandBus;
	}

	@Bean
	public CommandGatewayFactoryBean<CommandGateway> commandGatewayFactoryBean() {
		CommandGatewayFactoryBean<CommandGateway> factory = new CommandGatewayFactoryBean<CommandGateway>();
		factory.setCommandBus(commandBus());
		return factory;
	}

	@Bean
	public EventStore eventStore() {
		LOGGER.info("EVENT STORE");
		return new EmbeddedEventStore(new InMemoryEventStorageEngine()) {
			@Override
			public DomainEventStream readEvents(String aggregateIdentifier) {
				LOGGER.info("EventStore:readEvents({})", aggregateIdentifier);
				return super.readEvents(aggregateIdentifier);
			}
			@Override
			public void storeSnapshot(DomainEventMessage<?> snapshot) {
				LOGGER.info("EventStore:storeSnapshot({})", snapshot);
				super.storeSnapshot(snapshot);
			}
		};
	}

	@Bean
	public Repository<SalesOrderAggregateRoot> eventsRepository() {
		EventSourcingRepository<SalesOrderAggregateRoot> repository = new EventSourcingRepository<SalesOrderAggregateRoot>(SalesOrderAggregateRoot.class, eventStore()) {
			
		};
		//repository.setEventBus(eventBus());
		return repository;
	}

	@Bean
	public AggregateAnnotationCommandHandler<SalesOrderAggregateRoot> taskCommandHandler() {
		AggregateAnnotationCommandHandler<SalesOrderAggregateRoot> handler = new AggregateAnnotationCommandHandler<>(SalesOrderAggregateRoot.class, eventsRepository());
		handler.subscribe(commandBus());
		return handler;
	}

    
}
