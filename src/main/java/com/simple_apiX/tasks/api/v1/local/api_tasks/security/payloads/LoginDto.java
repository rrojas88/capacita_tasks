package com.simple_apiX.tasks.api.v1.local.api_tasks.security.payloads;



public class LoginDto {
	
	private String email;
	private String password;
	
	public LoginDto() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "AuthDto [email=" + email + ", password=" + password + "]";
	}

}
