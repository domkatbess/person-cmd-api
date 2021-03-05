package org.ebi.bess.person.cmd.api.dto;

import org.ebi.bess.person.core.dto.BaseResponse;

public class RegisterUserResponse extends BaseResponse {
    private String id;

    public RegisterUserResponse(String id, String message) {
        super(message);
        this.id = id;
    }
}
