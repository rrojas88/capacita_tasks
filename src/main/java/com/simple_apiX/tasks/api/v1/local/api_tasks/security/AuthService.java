package com.simple_apiX.tasks.api.v1.local.api_tasks.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.simple_apiX.tasks.api.v1.local.Utils.ErrorService;
import com.simple_apiX.tasks.api.v1.local.Utils.UtilsLocal;
import com.simple_apiX.tasks.api.v1.local.Utils.UtilsService;
import com.simple_apiX.tasks.api.v1.local.api_tasks.security.jwt.JwtProvider;
import com.simple_apiX.tasks.api.v1.local.api_tasks.security.payloads.LoginDto;
import com.simple_apiX.tasks.api.v1.local.api_tasks.security.responses.ResponseLogin;
import com.simple_apiX.tasks.api.v1.local.api_tasks.security.userdetails.UserDetailsImpl;
import com.simple_apiX.tasks.api.v1.local.api_tasks.users.UserService;
import com.simple_apiX.tasks.api.v1.local.api_tasks.users.adapters.bd1.User;





@Service
public class AuthService {
	
	private String myClassName = AuthService.class.getName();


	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private int expiration;

	@Autowired
    AuthenticationManager authenticationManager;

	@Autowired
	UserService userService;

	@Autowired
    JwtProvider jwtProvider;


	public Object login( LoginDto login )
	{
		try {
			Object resp = userService.getByEmail( login.getEmail() );
			if( UtilsService.isErrorService( resp )) return resp;

			if( resp == null ){
				return new ErrorService(
					"Usuario o contraseña incorrecta", 
					"Usuario o contraseña incorrecta", 
					myClassName,
					400 // 401
				);
			}
		
			// User OK
			Authentication auth = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
					login.getEmail(), login.getPassword()
				)
			);

			// Se Autentica el usuario
			SecurityContextHolder.getContext().setAuthentication(auth);
			// Genera el Token a partir de la Autenticacion
			String jwt = jwtProvider.generateToken(auth, secret, expiration);

			UserDetailsImpl authUserRoles = (UserDetailsImpl) auth.getPrincipal();

			ResponseLogin respLogin = new ResponseLogin( jwt, authUserRoles.getUsername() );
			return respLogin;

		}
		catch ( Exception e ){
			return new ErrorService( 
				"Usuario o contraseña incorrecta", 
				e.getMessage(), 
				myClassName,
				501
			);
		}
	}

}
