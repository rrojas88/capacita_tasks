package com.simple_apiX.tasks.api.v1.local.api_tasks.tasks.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.simple_apiX.tasks.api.v1.local.Utils.ErrorService;
import com.simple_apiX.tasks.api.v1.local.api_tasks.tasks.adapters.bd1.Task;
import com.simple_apiX.tasks.api.v1.local.api_tasks.tasks.adapters.bd1.TaskRepository;


@Service
public class TaskAdapter {
    
    private String myClassName = TaskAdapter.class.getName();

    @Autowired
    TaskRepository taskRepository;


    public Object findAll()
    {
        try {
            Object resp = taskRepository.findAll();
            return resp;
        }
        catch ( Exception e ){
            return new ErrorService( 
                "Ha ocurrido un error obteniendo las tareas", 
                e.getMessage(), 
                myClassName
            );
        }
    }


	public Object getByUser( Integer user_id )
	{
		try {
			Object resp = taskRepository.findByUserId( user_id );
			return resp;
		}
		catch ( Exception e ){
			return new ErrorService( 
				"Ha ocurrido un error consultando tareas por usuario", 
				e.getMessage(), 
				myClassName
			);
	    }
	}


	public Object getById( Integer id )
	{
		try {
			Optional resp = taskRepository.findById( id );
			if( ! resp.isPresent() ) return null;
			return resp.get();
		}
		catch ( Exception e ){
			return new ErrorService( 
				"Ha ocurrido un error consultando la tarea", 
				e.getMessage(), 
				myClassName
			);
	    }
	}


    public Object createOrEdit( Task task )
    {
        try {
            Object resp = taskRepository.save( task );
            return resp;
        }
        catch ( Exception e ){
            return new ErrorService( 
                "Ha ocurrido un error guardando la tarea", 
                e.getMessage(), 
                myClassName
            );
        }
    }

    /*
    public Object edit( Task task )
    {
        try {
            Object resp = taskRepository.save( task );
            return resp;
        }
        catch ( Exception e ){
            return new ErrorService( 
                "Ha ocurrido un error guardando tarea", 
                e.getMessage(), 
                myClassName
            );
        }
    } */

    public Object deleteById( Integer id )
    {
        try {
            Optional<Task> resp = taskRepository.findById( id );
            if( resp.isPresent() ){
                taskRepository.deleteById( id );
                return true;
            }
            return false;
        }
        catch ( Exception e ){
            return new ErrorService( 
                "Ha ocurrido un error borrando la tarea", 
                e.getMessage(), 
                myClassName
            );
        }
    }


	public Object getPendingTask( Integer user_id, String date_init, String date_end )
	{
		try {
			String  sql = "SELECT id, task, limit_date, created FROM tasks \n" + 
			"WHERE user_id = "+user_id+" AND completed = false AND \n" + 
			"limit_date BETWEEN '"+date_init+"' AND '"+date_end+"' \n" + 
			"ORDER BY limit_date ASC; ";
			Object resp = taskRepository.getPendingTask( sql );
			return resp;
		}
		catch ( Exception e ){
			return new ErrorService( 
				"Ha ocurrido un error consultando las tareas pendientes", 
				e.getMessage(), 
				myClassName
			);
	    }
	}


}
