
package com.simple_apiX.tasks.api.v1.local.api_tasks.security.jwt;

import java.util.Date;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.simple_apiX.tasks.api.v1.local.Utils.UtilsLocal;
import com.simple_apiX.tasks.api.v1.local.api_tasks.security.userdetails.UserDetailsImpl;


@Component
public class JwtProvider {
	

	public String generateToken( Authentication auth, String secret, int expiration ){
		// Obtiene Usuario
		UserDetailsImpl authUserRoles = (UserDetailsImpl) auth.getPrincipal();
		// Crea el Token
		return Jwts.builder()
			.setSubject( authUserRoles.getUsername() )
			.setIssuer( authUserRoles.getName() )
			.setIssuedAt( new Date() )
			.setExpiration( new Date( new Date().getTime() + expiration * 1000 ) )
			.signWith( SignatureAlgorithm.HS512, secret )
			.compact();
	}


	public String getUserNameFromToken( String token, String secret ){
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
			.getBody().getSubject();
	}

	public String getUserEmailFromToken( String token, String secret ){
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
			.getBody().getIssuer();
	}

	public boolean validateToken( String token, String secret ){
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		}
		catch( MalformedJwtException e ){
			UtilsLocal.log("Token mal construido.");
		}
		catch( UnsupportedJwtException e ){
			UtilsLocal.log("Token no soportado.");
		}
		catch( ExpiredJwtException e ){
			UtilsLocal.log("Token expirado/caducado.");
		}
		catch( IllegalArgumentException e ){
			UtilsLocal.log("Token vacío.");
		}
		catch( SignatureException e ){
			UtilsLocal.log("Error en la firma del Token.");
		}
		return false;
	}
}
