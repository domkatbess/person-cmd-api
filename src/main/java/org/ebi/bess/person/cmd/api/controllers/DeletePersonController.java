package org.ebi.bess.person.cmd.api.controllers;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.ebi.bess.person.cmd.api.commands.RemovePersonCommand;
import org.ebi.bess.person.core.dto.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/deletePerson")
public class DeletePersonController {
    private final CommandGateway commandGateway;

    @Autowired
    public DeletePersonController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> removeUser(@PathVariable(value = "id") String id) {
        try {
            commandGateway.send(new RemovePersonCommand(id));

            return new ResponseEntity<>(new BaseResponse("User successfully removed!"), HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Error while processing remove user request for id - " + id;
            System.out.println(e.toString());

            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
