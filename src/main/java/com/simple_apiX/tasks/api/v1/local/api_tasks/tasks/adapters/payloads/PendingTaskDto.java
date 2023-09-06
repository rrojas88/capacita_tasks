package com.simple_apiX.tasks.api.v1.local.api_tasks.tasks.adapters.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class PendingTaskDto {
	
	@NotEmpty(message = "El identificador del usuario es obligatorio")
    @Pattern(regexp = "^[0-9]+$", message = "El identificador del usuario debe ser numérico " )
    private String user_id;

	@NotBlank(message = "La fecha de inicio es requerida")
    @Pattern(regexp = "^$|^([0-9]{4}-[0-9]{2}-[0-9]{2})$", message = "El formato de la fecha de inicio debe ser: AAAA/mm/dd")
    private String date_init;

	@NotBlank(message = "La fecha fin es requerida")
    @Pattern(regexp = "^$|^([0-9]{4}-[0-9]{2}-[0-9]{2})$", message = "El formato de la fecha fin debe ser: AAAA/mm/dd")
    private String date_end;

	public PendingTaskDto() {
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getDate_init() {
		return date_init;
	}

	public void setDate_init(String date_init) {
		this.date_init = date_init;
	}

	public String getDate_end() {
		return date_end;
	}

	public void setDate_end(String date_end) {
		this.date_end = date_end;
	}

	@Override
	public String toString() {
		return "PendingTaskDto [user_id=" + user_id + ", date_init=" + date_init + ", date_end=" + date_end + "]";
	}
}
