package org.ebi.bess.person.cmd.api.commands;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.ebi.bess.person.core.models.Person;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserCommand {
    @TargetAggregateIdentifier
    private String id;

    private Person user;
}
