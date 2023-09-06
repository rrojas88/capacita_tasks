package com.simple_apiX.tasks.api.v1.local.api_tasks.users.adapters.payloads;

import javax.validation.constraints.NotBlank;

public class HeadersDto {
    
    @NotBlank(message = "El token temporal es requerido")
    private String token_tmp;

    public HeadersDto( String token ) {
        this.token_tmp = token;
    }

    public String getToken_tmp() {
        return token_tmp;
    }

    public void setToken_tmp(String token_tmp) {
        this.token_tmp = token_tmp;
    }

    @Override
    public String toString() {
        return "HeadersDto [token_tmp=" + token_tmp + "]";
    }
}
