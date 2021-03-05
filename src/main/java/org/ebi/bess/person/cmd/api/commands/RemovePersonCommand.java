package org.ebi.bess.person.cmd.api.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class RemovePersonCommand {
    @TargetAggregateIdentifier
    private String id;
}
