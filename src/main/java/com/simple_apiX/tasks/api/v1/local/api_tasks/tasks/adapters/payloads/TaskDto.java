package com.simple_apiX.tasks.api.v1.local.api_tasks.tasks.adapters.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.NotEmpty;


public class TaskDto {
    
    @NotEmpty(message = "El identificador del usuario de la tarea es obligatorio")
    @Pattern(regexp = "^[0-9]+$", message = "El identificador del usuario debe ser numérico " )
    private String user_id;

    @NotBlank(message = "La tarea es obligatoria")
    private String task;

    @NotBlank(message = "La fecha limite de la tarea es requerida")
    @Pattern(regexp = "^$|^([0-9]{4}-[0-9]{2}-[0-9]{2})$", message = "El formato de la fecha limite debe ser: AAAA/mm/dd")
    private String limit_date;

    public TaskDto() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getLimit_date() {
        return limit_date;
    }

    public void setLimit_date(String limit_date) {
        this.limit_date = limit_date;
    }

    @Override
    public String toString() {
        return "TaskDto [user_id=" + user_id + ", task=" + task + ", limit_date=" + limit_date + "]";
    }
}
