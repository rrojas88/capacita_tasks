package com.simple_apiX.tasks.api.v1.local.api_tasks.security.userdetails;

import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.simple_apiX.tasks.api.v1.local.api_tasks.users.adapters.bd1.User;


public class UserDetailsImpl implements UserDetails {
	
	private final User user;

	public UserDetailsImpl( User user ){
		this.user = user;
	}

	public String getName () {
		return user.getName();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.emptyList();
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
