package com.pasapalabra.game.service.auth;

import java.io.Serializable;

public class Token implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String token;
	
	public Token(String token){
		this.token = token;
	}
	
	public String getToken(){
		return this.token;
	}
	
	public void setToken(String token){
		this.token = token;	
	}

	public boolean equals(Token token) {
		return this.token.equals(token.getToken());
	}
	
	@Override
	public int hashCode() {
		return token.hashCode();
	}
	
}
