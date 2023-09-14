package com.simple_apiX.tasks.api.v1.local.api_tasks.security;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simple_apiX.tasks.api.v1.local.Utils.ResponseLocal;
import com.simple_apiX.tasks.api.v1.local.Utils.UtilsLocal;
import com.simple_apiX.tasks.api.v1.local.api_tasks.security.payloads.LoginDto;


@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
	
	private String myClassName = AuthController.class.getName();

	@Autowired
	AuthService authService;


	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}



	@PostMapping("/login")
	public ResponseEntity<?> login(
		@Valid @RequestBody LoginDto payload,
		BindingResult bindingResult,
        HttpServletRequest req
	)
	{
		ResponseLocal response = new ResponseLocal();

		// Validación de Dto ---------------------------------------------
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
			Object resp = authService.login( payload );
			String pass = passwordEncoder().encode( payload.getPassword() ); UtilsLocal.log( pass );

			HttpStatus httpStatus = response.validateService( resp, 
                "Login ok",
                this.myClassName, 
                payload.toString(), 
                req
            );
            return new ResponseEntity( response, httpStatus );
		}
		catch ( Exception e ){
			response.setError( HttpStatus.INTERNAL_SERVER_ERROR.value(), 
                "No se pudo iniciar sesión", 
                e.getMessage(),
                UtilsLocal.emptyErrorList(),
                this.myClassName, 
                payload.toString(), 
                req
            );
            return new ResponseEntity<>( response, HttpStatus.INTERNAL_SERVER_ERROR );
		}
	}


}
