package com.simple_apiX.tasks.api.v1.local.api_tasks.tasks.adapters.bd1;

import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {
    
    @Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
    private Integer id;

    private Integer user_id;
    private String task;
    private boolean completed;
    private Timestamp limit_date;
    private Timestamp created;

    public Task() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Timestamp getLimit_date() {
        return limit_date;
    }

    public void setLimit_date(Timestamp limit_date) {
        this.limit_date = limit_date;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Task [id=" + id + ", user_id=" + user_id + ", task=" + task + ", completed=" + completed
                + ", limit_date=" + limit_date + ", created=" + created + "]";
    }
}
