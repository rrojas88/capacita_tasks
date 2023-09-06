package com.simple_apiX.tasks.api.v1.local.api_tasks.users.adapters.bd1;

import javax.persistence.*;


@Entity
@Table(name = "users")
public class User {
    
    @Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
    private Integer id;

    private String name;
    private String email;
    private String password;
    
    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + "]";
    }
}
