package com.app.service.authPwdService.impl;

import com.app.service.authPwdService.AuthPwdService;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class AuthPwdSericeImpl implements AuthPwdService {

	@Override
	public String generatePwdHash(String candidatePassword) {
		String hashedPassword = BCrypt.withDefaults().hashToString(12, candidatePassword.toCharArray());
		System.out.println(hashedPassword);
		return hashedPassword;
	}

	@Override
	public boolean verifyPwd(String candidatePassword, String pwdInDB) {
		BCrypt.Result result = BCrypt.verifyer().verify(candidatePassword.toCharArray(), pwdInDB);
		return result.verified;
	}

}
