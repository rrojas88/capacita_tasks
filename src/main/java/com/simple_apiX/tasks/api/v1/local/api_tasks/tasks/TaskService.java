package com.simple_apiX.tasks.api.v1.local.api_tasks.tasks;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simple_apiX.tasks.api.v1.local.Utils.ErrorService;
import com.simple_apiX.tasks.api.v1.local.Utils.UtilsLocal;
import com.simple_apiX.tasks.api.v1.local.Utils.UtilsService;
import com.simple_apiX.tasks.api.v1.local.api_tasks.tasks.adapters.TaskAdapter;
import com.simple_apiX.tasks.api.v1.local.api_tasks.tasks.adapters.bd1.Task;
import com.simple_apiX.tasks.api.v1.local.api_tasks.tasks.adapters.payloads.PendingTaskDto;
import com.simple_apiX.tasks.api.v1.local.api_tasks.tasks.adapters.payloads.TaskDto;


@Service
public class TaskService {
    
    private String myClassName = TaskService.class.getName();

    @Autowired
    TaskAdapter taskAdapter;

    
	public Object getAll(  )
	{
		try {
			Object resp = taskAdapter.findAll();
			return resp;
		}
		catch ( Exception e ) {
			return new ErrorService( 
				"Ha ocurrido un error obteniendo la lista de usuarios", 
				e.getMessage(), 
				myClassName
			);
		}
	}


	public Object getByUser( Integer user_id )
	{
		try {
			Object resp = taskAdapter.getByUser( user_id );
			return resp;
		}
		catch ( Exception e ) {
			return new ErrorService(
				"Ha ocurrido un error obteniendo las tareas del usuario", 
				e.getMessage(), 
				myClassName
			);
		}
	}


	public Object create( TaskDto taskDto )
	{
		try {
			Task task = new Task();
			task.setUser_id( Integer.parseInt( taskDto.getUser_id() ) );
			task.setTask( taskDto.getTask() );
			task.setLimit_date( UtilsLocal.strDateToTimestamp( taskDto.getLimit_date(), "yyyy-MM-dd") );
			String strCreated = UtilsLocal.getDateTimeNow();
			task.setCreated( UtilsLocal.strDateToTimestamp( strCreated, "yyyy-MM-dd hh:mm:ss") );

			Object resp = taskAdapter.createOrEdit( task );
			return resp;
		}
		catch (Exception e) {
			return new ErrorService( 
			    "Ha ocurrido un error registrando la tarea", 
			    e.getMessage(), 
			    myClassName
			);
		}
	}


	public Object edit( TaskDto taskDto, Integer id )
	{
		try {
			Object respBd = taskAdapter.getById( id );
			if( respBd == null || UtilsService.isErrorService( respBd ) ) return respBd;

			Task task = ( Task ) respBd;
			task.setUser_id( Integer.parseInt( taskDto.getUser_id() ) );
			task.setTask( taskDto.getTask() );
			task.setLimit_date( UtilsLocal.strDateToTimestamp( taskDto.getLimit_date(), "yyyy-MM-dd") );

			Object resp = taskAdapter.createOrEdit( task );
			return resp;
		}
		catch (Exception e) {
			return new ErrorService( 
			    "Ha ocurrido un error modificando la tarea", 
			    e.getMessage(), 
			    myClassName
			);
		}
	}


	public Object delete( Integer id )
	{
		try {
			Object resp = taskAdapter.deleteById( id );
			return resp;
		}
		catch ( Exception e ) {
			return new ErrorService(
				"Ha ocurrido un error obteniendo las tareas del usuario", 
				e.getMessage(), 
				myClassName
			);
		}
	}


	public Object getPendingTask( PendingTaskDto payload )
	{
		try {
			Object resp = taskAdapter.getPendingTask(
				Integer.parseInt( payload.getUser_id() ),
				payload.getDate_init(),
				payload.getDate_end()
			);
			return resp;
		}
		catch ( Exception e ) {
			return new ErrorService(
				"Ha ocurrido un error obteniendo las tareas pendientes", 
				e.getMessage(), 
				myClassName
			);
		}
	}


	public Object completeTask( Integer id )
	{
		try {
			Object respBd = taskAdapter.getById( id );
			if( respBd == null || UtilsService.isErrorService( respBd ) ) return respBd;

			Task task = (Task) respBd;
			task.setCompleted( true );
			Object resp = taskAdapter.createOrEdit( task );
			if( UtilsService.isErrorService( resp ) ) return resp;
			
			return true;
		}
		catch (Exception e) {
			return new ErrorService( 
			    "Ha ocurrido un error completando la tarea", 
			    e.getMessage(), 
			    myClassName
			);
		}
	}


}
