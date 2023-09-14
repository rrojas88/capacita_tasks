package com.simple_apiX.tasks.api.v1.local.api_tasks.security.jwt;

import java.io.IOException;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import com.simple_apiX.tasks.api.v1.local.Utils.UtilsLocal;
import com.simple_apiX.tasks.api.v1.local.api_tasks.security.payloads.LoginDto;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	

	@Autowired
	JwtProvider jwtProvider;

	private String secret;
	private int expiration;
	private final AuthenticationManager authenticationManager;


	public JwtAuthenticationFilter(AuthenticationManager authManager, String secret, int expiration ) {
		this.authenticationManager = authManager;
		this.secret = secret;
		this.expiration = expiration;

		// Establece una URL para iniciar sesión [PERO bloquea el Controlador creado para ello ! ]
        //setFilterProcessesUrl("/auth/login");
    }  


	// Lógica para la autenticación basada en JWT.
	@Override
	public Authentication attemptAuthentication(
		HttpServletRequest request,
		HttpServletResponse response
	) throws AuthenticationException {

		LoginDto auth = new LoginDto();
		try {
			auth = new ObjectMapper().readValue( request.getReader(), LoginDto.class );
		}
		catch ( Exception e ) {
			UtilsLocal.log("..... Falla JwtAuthenticationFilter por Token ");
		}

		UsernamePasswordAuthenticationToken userPAT = new UsernamePasswordAuthenticationToken( 
			auth.getEmail(),
			auth.getPassword(), 
			Collections.emptyList()
		);
		
		String message = "Usuario o contraseña incorrecta";
		try {
			Authentication authResult = authenticationManager.authenticate( userPAT );
			return authResult;
		}
		catch ( BadCredentialsException ex ) {
			UtilsLocal.log(".............. Error - BadCredentialsException:");
			UtilsLocal.log( ex.getMessage() );
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			try {
				response.getWriter().write(message);
			} catch (IOException ioException ) {
				UtilsLocal.log( ioException.getMessage() );
			}
			throw ex;
		} 
		catch ( Exception ex2 ) {
			UtilsLocal.log( ex2.getMessage() );
			response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
			try {
				response.getWriter().write(message);
			} catch (IOException ioException ) {
				UtilsLocal.log( ioException.getMessage() );
			}
			throw ex2;
		} 
	}


	// Si NO se crea la Logica del AuthController/login, ejecutara esto...
	@Override
	protected void successfulAuthentication(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain chain,
		Authentication authResult
	) throws IOException, ServletException 
	{
		UtilsLocal.log("---- RES  Auth: "); UtilsLocal.log( authResult.getPrincipal() );
		if( jwtProvider == null ) jwtProvider = new JwtProvider();
		String token = jwtProvider.generateToken(authResult, secret, expiration);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		String message = "{\"token\": \""+ token +"\"}";
		response.addHeader("Authorization", "Bearer " + token );
		response.getWriter().write(message);
		
	}

}
