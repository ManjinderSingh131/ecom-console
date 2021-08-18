package com.app.service.authPwdService;

public interface AuthPwdService {
	// Generate hash for password
	public String generatePwdHash(String candidatePassword);

	// verify password
	public boolean verifyPwd(String candidatePassword, String pwdInDB);
}
