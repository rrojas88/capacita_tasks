package com.simple_apiX.tasks.api.v1.local.api_tasks.tasks;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simple_apiX.tasks.api.v1.local.Utils.ResponseLocal;
import com.simple_apiX.tasks.api.v1.local.Utils.UtilsLocal;
import com.simple_apiX.tasks.api.v1.local.api_tasks.tasks.adapters.payloads.PendingTaskDto;
import com.simple_apiX.tasks.api.v1.local.api_tasks.tasks.adapters.payloads.TaskDto;



@RestController
@RequestMapping("/tasks")
@CrossOrigin
public class TaskController {
    
    private String myClassName = TaskController.class.getName();

    @Autowired
    TaskService taskService;


	@GetMapping("")
	public ResponseEntity<?> getAll( HttpServletRequest req )
	{
		ResponseLocal response = new ResponseLocal();

		try {
			Object resp = taskService.getAll();

			HttpStatus httpStatus = response.validateService( resp, 
                "Consulta ok",
                this.myClassName, 
                "", 
                req
            );
            return new ResponseEntity( response, httpStatus );
		}
		catch ( Exception e ){
			response.setError( HttpStatus.INTERNAL_SERVER_ERROR.value(), 
                "No se pudo obtener la lista de tareas", 
                e.getMessage(),
                UtilsLocal.emptyErrorList(),
                this.myClassName, 
                "", 
                req
            );
            return new ResponseEntity<>( response, HttpStatus.INTERNAL_SERVER_ERROR );
		}
	}


	@GetMapping("/by-user/{user_id}")
	public ResponseEntity<?> getByUser(
		@PathVariable Integer user_id,
		HttpServletRequest req
	)
	{
		ResponseLocal response = new ResponseLocal();

		try {
			Object resp = taskService.getByUser( user_id );

			HttpStatus httpStatus = response.validateService( resp, 
                "Consulta ok",
                this.myClassName, 
                "", 
                req
            );
            return new ResponseEntity( response, httpStatus );
		}
		catch ( Exception e ){
			response.setError( HttpStatus.INTERNAL_SERVER_ERROR.value(), 
                "No se pudo obtener la lista de tareas", 
                e.getMessage(),
                UtilsLocal.emptyErrorList(),
                this.myClassName, 
                "", 
                req
            );
            return new ResponseEntity<>( response, HttpStatus.INTERNAL_SERVER_ERROR );
		}
	}


	@PostMapping("")
	public ResponseEntity<?> create(
		@Valid @RequestBody TaskDto payload,
		BindingResult bindingResult,
        HttpServletRequest req
	)
	{
		ResponseLocal response = new ResponseLocal();

		// Validación de parámetros (Payload) ------------------------
        if( bindingResult.hasErrors() ){
            response.setError( HttpStatus.INTERNAL_SERVER_ERROR.value(), "", "", 
                bindingResult.getAllErrors(),
                this.myClassName, 
                payload.toString(), 
                req
            );
            return new ResponseEntity<>( response, HttpStatus.INTERNAL_SERVER_ERROR );
        }
		
		try {
			Object resp = taskService.create( payload );

			HttpStatus httpStatus = response.validateService( resp, 
                "Registro ok",
                this.myClassName, 
                payload.toString(), 
                req
            );
            return new ResponseEntity( response, httpStatus );
		}
		catch ( Exception e ){
			response.setError( HttpStatus.INTERNAL_SERVER_ERROR.value(), 
                "No se pudo registrar la tarea", 
                e.getMessage(),
                UtilsLocal.emptyErrorList(),
                this.myClassName, 
                "", 
                req
            );
            return new ResponseEntity<>( response, HttpStatus.INTERNAL_SERVER_ERROR );
		}
	}


	@PutMapping("/{id}")
	public ResponseEntity<?> edit(
		@PathVariable Integer id,
		@Valid @RequestBody TaskDto payload,
		BindingResult bindingResult,
        HttpServletRequest req
	)
	{
		ResponseLocal response = new ResponseLocal();

		// Validación de parámetros (Payload) ------------------------
        if( bindingResult.hasErrors() ){
            response.setError( HttpStatus.INTERNAL_SERVER_ERROR.value(), "", "", 
                bindingResult.getAllErrors(),
                this.myClassName, 
                payload.toString(), 
                req
            );
            return new ResponseEntity<>( response, HttpStatus.INTERNAL_SERVER_ERROR );
        }
		
		try {
			Object resp = taskService.edit( payload, id );

			HttpStatus httpStatus = response.validateService( resp, 
                "Registro ok",
                this.myClassName, 
                payload.toString(), 
                req
            );
            return new ResponseEntity( response, httpStatus );
		}
		catch ( Exception e ){
			response.setError( HttpStatus.INTERNAL_SERVER_ERROR.value(), 
                "No se pudo registrar la tarea", 
                e.getMessage(),
                UtilsLocal.emptyErrorList(),
                this.myClassName, 
                "", 
                req
            );
            return new ResponseEntity<>( response, HttpStatus.INTERNAL_SERVER_ERROR );
		}
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(
		@PathVariable Integer id,
		HttpServletRequest req
	)
	{
		ResponseLocal response = new ResponseLocal();

		try {
			Object resp = taskService.delete( id );

			HttpStatus httpStatus = response.validateService( resp, 
                "Registro eliminado",
                this.myClassName, 
                "", 
                req
            );
            return new ResponseEntity( response, httpStatus );
		}
		catch ( Exception e ){
			response.setError( HttpStatus.INTERNAL_SERVER_ERROR.value(), 
                "No se pudo eliminar el registro", 
                e.getMessage(),
                UtilsLocal.emptyErrorList(),
                this.myClassName, 
                "", 
                req
            );
            return new ResponseEntity<>( response, HttpStatus.INTERNAL_SERVER_ERROR );
		}
	}


	@PostMapping("/pending")
	public ResponseEntity<?> getPendingTask(
		@Valid @RequestBody PendingTaskDto payload,
		BindingResult bindingResult,
        HttpServletRequest req
	)
	{
		ResponseLocal response = new ResponseLocal();

		// Validación de parámetros (Payload) ------------------------
        if( bindingResult.hasErrors() ){
            response.setError( HttpStatus.INTERNAL_SERVER_ERROR.value(), "", "", 
                bindingResult.getAllErrors(),
                this.myClassName, 
                payload.toString(), 
                req
            );
            return new ResponseEntity<>( response, HttpStatus.INTERNAL_SERVER_ERROR );
        }
		
		try {
			Object resp = taskService.getPendingTask( payload );

			HttpStatus httpStatus = response.validateService( resp, 
                "Tareas pendientes",
                this.myClassName, 
                payload.toString(), 
                req
            );
            return new ResponseEntity( response, httpStatus );
		}
		catch ( Exception e ){
			response.setError( HttpStatus.INTERNAL_SERVER_ERROR.value(), 
                "No se pudo consultar las tareas pendientes", 
                e.getMessage(),
                UtilsLocal.emptyErrorList(),
                this.myClassName, 
                "", 
                req
            );
            return new ResponseEntity<>( response, HttpStatus.INTERNAL_SERVER_ERROR );
		}
	}


	@GetMapping("/completed/{id}")
	public ResponseEntity<?> completeTask(
		@PathVariable Integer id,
		HttpServletRequest req
	)
	{
		ResponseLocal response = new ResponseLocal();

		try {
			Object resp = taskService.completeTask( id );
			System.out.println(".... Resp compelte: ");System.out.println( resp );

			HttpStatus httpStatus = response.validateService( resp, 
                "Tarea completada/actualizada",
                this.myClassName, 
                "", 
                req
            );
            return new ResponseEntity( response, httpStatus );
		}
		catch ( Exception e ){
			response.setError( HttpStatus.INTERNAL_SERVER_ERROR.value(), 
                "No se pudo completar la tarea", 
                e.getMessage(),
                UtilsLocal.emptyErrorList(),
                this.myClassName, 
                "", 
                req
            );
            return new ResponseEntity<>( response, HttpStatus.INTERNAL_SERVER_ERROR );
		}
	}


}
