package com.nitsoft.ecommerce.auth.login;

public interface Authenticator {
	AuthResponse authenticate(long teamId, String email, String password,boolean isKeepMeLogin);
}
