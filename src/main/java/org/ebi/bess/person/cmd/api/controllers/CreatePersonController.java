package org.ebi.bess.person.cmd.api.controllers;

import java.util.UUID;

import javax.validation.Valid;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.ebi.bess.person.cmd.api.commands.CreatePersonCommand;
import org.ebi.bess.person.cmd.api.dto.CreatePersonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/createPerson")
public class CreatePersonController {
    private final CommandGateway commandGateway;

    @Autowired
    public CreatePersonController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public ResponseEntity<CreatePersonResponse> createPerson(@Valid @RequestBody CreatePersonCommand command) {
        var id = UUID.randomUUID().toString();
        System.out.println(command.getUser().getFirstName());
        command.setId(id);

        try {
            commandGateway.send(command);

            return new ResponseEntity<>(new CreatePersonResponse(id, "User successfully registered!"), HttpStatus.CREATED);
        } catch (Exception e) {
            var safeErrorMessage = "Error while processing register user request for id - " + id;
            System.out.println(e.toString());

            return new ResponseEntity<>(new CreatePersonResponse(id, safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
