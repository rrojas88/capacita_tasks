package com.simple_apiX.tasks.api.v1.local.api_tasks.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.simple_apiX.tasks.api.v1.local.api_tasks.security.jwt.JwtAuthenticationFilter;
import com.simple_apiX.tasks.api.v1.local.api_tasks.security.jwt.JwtEndpointFilter;



@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Value("${jwt.secret}")
    private String secret;

	@Value("${jwt.expiration}")
    private int expiration;

	@Autowired
	private UserDetailsService userDetailsService;

	private JwtAuthenticationFilter jwtAuthenticationFilter;


	@Bean
	PasswordEncoder passwordEncoder (){
		return new BCryptPasswordEncoder( );
	}

	@Bean
    public JwtEndpointFilter jwtEndpointFilter(){
        return new JwtEndpointFilter();
    }


	@Bean
	SecurityFilterChain filterChain ( HttpSecurity http, AuthenticationManager authManager ) throws Exception
	{
		jwtAuthenticationFilter = new JwtAuthenticationFilter( authManager, secret, expiration );
		
		http
			.cors()
			.and().csrf().disable() // Inhabilitar cookies
			.authorizeRequests()
				.antMatchers( "/auth/**" ).permitAll() // Permitir las URLs que tengan: "auth"
			.anyRequest().authenticated() // Para el resto debe estar autenticado
			.and()
			.sessionManagement() // Politica de Session SIN estado:
				.sessionCreationPolicy( SessionCreationPolicy.STATELESS )
			.and()
			.addFilter( jwtAuthenticationFilter ); // Login
		
		// Por cada Peticion, comprueba el Token y pasa el User al Contexto de Autenticacion
		http.addFilterBefore( jwtEndpointFilter() , UsernamePasswordAuthenticationFilter.class );
	
		return http.build();
	}


	@Bean
	public AuthenticationManager authManager( HttpSecurity http ) throws Exception {
        return http.getSharedObject( AuthenticationManagerBuilder.class )
			.userDetailsService( userDetailsService )
			.passwordEncoder( passwordEncoder() )
			.and()
			.build();
    }


}
