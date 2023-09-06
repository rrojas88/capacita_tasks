package com.simple_apiX.tasks.api.v1.local.api_tasks.tasks.adapters.bd1;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name = "tasks")
public class PendingTask {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Integer id;
	
    private String task;
    private String limit_date;
    private String created;

	public PendingTask() {
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	@Override
	public String toString() {
		return "PendingTask [id=" + id + ", task=" + task + ", limit_date=" + limit_date + ", created="
				+ created + "]";
	}

}
