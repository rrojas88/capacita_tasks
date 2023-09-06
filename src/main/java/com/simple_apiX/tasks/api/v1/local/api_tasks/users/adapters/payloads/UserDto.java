package com.simple_apiX.tasks.api.v1.local.api_tasks.users.adapters.payloads;


import javax.validation.constraints.NotBlank;


public class UserDto {
    
    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;
    @NotBlank(message = "El correo es requerido")
    private String email;
    @NotBlank(message = "La contraseña es requerida")
    private String password;
    
    public UserDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDto [name=" + name + ", email=" + email + ", password=" + password + "]";
    }
}
