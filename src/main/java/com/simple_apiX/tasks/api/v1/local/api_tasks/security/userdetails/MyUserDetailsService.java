package com.simple_apiX.tasks.api.v1.local.api_tasks.security.userdetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.simple_apiX.tasks.api.v1.local.Utils.UtilsLocal;
import com.simple_apiX.tasks.api.v1.local.api_tasks.users.UserService;
import com.simple_apiX.tasks.api.v1.local.api_tasks.users.adapters.bd1.User;


@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserService userService;

	@Override
	public UserDetails loadUserByUsername( String email ) throws UsernameNotFoundException {

		//UtilsLocal.log("--- -- pasa loadUserByUsername() x 1 ");
		Object resp = userService.getByEmail(email);

		if( resp == null ){
			throw new UsernameNotFoundException("Usuario no encontrado!");
		}
		User user = ( User ) resp;
		return new UserDetailsImpl( user );
	}

}
