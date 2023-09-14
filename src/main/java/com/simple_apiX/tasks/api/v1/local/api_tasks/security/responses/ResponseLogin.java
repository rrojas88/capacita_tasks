package com.simple_apiX.tasks.api.v1.local.api_tasks.security.responses;



public class ResponseLogin {
	
	private String token;
	private String fullname;
	
	public ResponseLogin() {
	}

	public ResponseLogin(String token, String fullname) {
		this.token = token;
		this.fullname = fullname;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	@Override
	public String toString() {
		return "ResponseLogin [token=" + token + ", fullname=" + fullname + "]";
	}
}
