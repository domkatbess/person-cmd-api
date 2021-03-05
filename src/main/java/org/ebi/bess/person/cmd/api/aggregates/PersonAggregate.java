package org.ebi.bess.person.cmd.api.aggregates;

import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.ebi.bess.person.cmd.api.commands.CreatePersonCommand;
import org.ebi.bess.person.cmd.api.commands.RemovePersonCommand;
import org.ebi.bess.person.cmd.api.commands.UpdatePersonCommand;
import org.ebi.bess.person.core.events.PersonCreatedEvent;
import org.ebi.bess.person.core.events.PersonRemovedEvent;
import org.ebi.bess.person.core.events.PersonUpdatedEvent;
import org.ebi.bess.person.core.models.Person;

@Aggregate
public class PersonAggregate {
    @AggregateIdentifier
    private String id;
    private Person user;

    

    public PersonAggregate() {
        
    }

    @CommandHandler
    public PersonAggregate(CreatePersonCommand command) {
        var newUser = command.getUser();
        newUser.setId(command.getId());
  

        var event = PersonCreatedEvent.builder()
                .id(command.getId())
                .user(newUser)
                .build();

        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(UpdatePersonCommand command) {
        var updatedUser = command.getUser();
        updatedUser.setId(command.getId());


        var event = PersonUpdatedEvent.builder()
                .id(UUID.randomUUID().toString())
                .user(updatedUser)
                .build();

        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(RemovePersonCommand command) {
        var event = new PersonRemovedEvent();
        event.setId(command.getId());

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(PersonCreatedEvent event) {
        this.id = event.getId();
        this.user = event.getUser();
    }

    @EventSourcingHandler
    public void on(PersonUpdatedEvent event) {
        this.user = event.getUser();
    }

    @EventSourcingHandler
    public void on(PersonRemovedEvent event) {
        AggregateLifecycle.markDeleted();
    }
}
