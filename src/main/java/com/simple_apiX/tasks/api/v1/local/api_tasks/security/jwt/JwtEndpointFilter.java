package com.simple_apiX.tasks.api.v1.local.api_tasks.security.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.simple_apiX.tasks.api.v1.local.Utils.UtilsLocal;
import com.simple_apiX.tasks.api.v1.local.api_tasks.security.userdetails.MyUserDetailsService;


public class JwtEndpointFilter extends OncePerRequestFilter {
	
	@Value("${jwt.secret}")
	private String secret;

	@Autowired
	JwtProvider jwtProvider;

	@Autowired
	MyUserDetailsService myUserDetailsService;

	@Override
	protected void doFilterInternal( 
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain chain
	) throws IOException, ServletException
	{
		String uri = request.getRequestURI();
		UtilsLocal.log( "URI: " + uri );
		try {
			String bearerToken = request.getHeader( "Authorization" );

			if( bearerToken != null && bearerToken.startsWith("Bearer ") )
			{
				String token = bearerToken.replace("Bearer ", "");

				if( jwtProvider == null ) jwtProvider = new JwtProvider();

				if( token != null && jwtProvider.validateToken( token, secret ) ){
					
					String userEmail = jwtProvider.getUserNameFromToken(token, secret);
					
					UserDetails userDetails = myUserDetailsService.loadUserByUsername(userEmail);
					UtilsLocal.log("* * * Lista de Roles: " + userDetails.getAuthorities() );

					// Obtiene Autenticacion
					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken( 
						userDetails, 
						null, 
						userDetails.getAuthorities()
					); 
					SecurityContextHolder.getContext().setAuthentication( auth );
				}
			}
			else{
				UtilsLocal.log(".............. NO viene Token o es incorrecto ");
			}
		}
		catch (Exception e) {
			UtilsLocal.log(".............. Falla doFilterInternal por Token ");
		}

		chain.doFilter(request, response);
	}

}
