package com.app.dao;

import com.app.businessException.BusinessException;

public interface CustomerLoginServiceDAO {
	public boolean cstLogin(String username, String Password) throws BusinessException;

	public boolean isValidUser(String username) throws BusinessException;

}
