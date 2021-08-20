package com.app.service.impl;

import com.app.service.AuthPwdService;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class AuthPwdServiceImpl implements AuthPwdService {

	public String generatePwdHash(String candidatePassword) {
		String hashedPassword = BCrypt.withDefaults().hashToString(12, candidatePassword.toCharArray());
		System.out.println(hashedPassword);
		return hashedPassword;
	}

	public boolean verifyPwd(String candidatePassword, String pwdInDB) {
		BCrypt.Result result = BCrypt.verifyer().verify(candidatePassword.toCharArray(), pwdInDB);
		return result.verified;
	}

}