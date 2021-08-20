package com.app.service;

public interface AuthPwdService {
	// Generate hash for password
	public String generatePwdHash(String candidatePassword);

	// verify password
	public boolean verifyPwd(String candidatePassword, String pwdInDB);
}
