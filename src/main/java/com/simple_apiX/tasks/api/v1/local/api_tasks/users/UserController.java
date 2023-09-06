package com.simple_apiX.tasks.api.v1.local.api_tasks.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simple_apiX.tasks.api.v1.local.Utils.ResponseLocal;
import com.simple_apiX.tasks.api.v1.local.Utils.UtilsLocal;
import com.simple_apiX.tasks.api.v1.local.api_tasks.users.adapters.payloads.HeadersDto;
import com.simple_apiX.tasks.api.v1.local.api_tasks.users.adapters.payloads.UserDto;



@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
    
    private String myClassName = UserController.class.getName();


    @Autowired
    UserService userService;


	@GetMapping("")
	public ResponseEntity<?> getAll( HttpServletRequest req )
	{
		ResponseLocal response = new ResponseLocal();

		try {
			Object resp = userService.getAll();

			HttpStatus httpStatus = response.validateService( resp, 
                "Consulta ok",
                this.myClassName, 
                "", 
                req
            );
            return new ResponseEntity( response, httpStatus );
		}
		catch ( Exception e ){
			response.setError( HttpStatus.BAD_REQUEST.value(), 
                "No se pudo obtener la lista de usuarios", 
                e.getMessage(),
                UtilsLocal.emptyErrorList(),
                this.myClassName, 
                "", 
                req
            );
            return new ResponseEntity<>( response, HttpStatus.BAD_REQUEST );
		}
	}



	@PostMapping("")
	public ResponseEntity<?> register(
		@Valid @RequestBody UserDto payload,
		BindingResult bindingResult,
        HttpServletRequest req
	)
	{
		ResponseLocal response = new ResponseLocal();
		Validator validator = UtilsLocal.getValidatorDto();

		// Validacion de Header u Otros Dtos -------------------------
		String token_tmp = req.getHeader("token_tmp");
		HeadersDto headersDto = new HeadersDto( token_tmp );
		Set<ConstraintViolation<HeadersDto>> errorsResp = validator.validate( headersDto );
        if( ! errorsResp.isEmpty() && errorsResp.size() != 0 ){
             List<String> errorsList = new ArrayList<>();
             errorsResp.forEach( er -> errorsList.add( er.getMessage() )  );
             response.setError( HttpStatus.BAD_REQUEST.value(),
                    UtilsLocal.getErrorMessagesList(errorsList),
                    "",
                    UtilsLocal.emptyErrorList(),
                    this.myClassName, 
                    headersDto.toString(), 
                    req
                );
             return new ResponseEntity<>( response, HttpStatus.BAD_REQUEST );
         }

		// Validación de parámetros (Payload) ------------------------
        if( bindingResult.hasErrors() ){
            response.setError( HttpStatus.BAD_REQUEST.value(), "", "", 
                bindingResult.getAllErrors(),
                this.myClassName, 
                payload.toString(), 
                req
            );
            return new ResponseEntity<>( response, HttpStatus.BAD_REQUEST );
        }
        
		
		try {
			Object resp = userService.register( payload );

			HttpStatus httpStatus = response.validateService( resp, 
                "Registro ok",
                this.myClassName, 
                payload.toString(), 
                req
            );
            return new ResponseEntity( response, httpStatus );
		}
		catch ( Exception e ){
			response.setError( HttpStatus.BAD_REQUEST.value(), 
                "No se pudo registrar el usuario", 
                e.getMessage(),
                UtilsLocal.emptyErrorList(),
                this.myClassName, 
                "", 
                req
            );
            return new ResponseEntity<>( response, HttpStatus.BAD_REQUEST );
		}
	}

}
