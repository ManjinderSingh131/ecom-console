package com.app.service.customerService.impl;

import java.util.List;

import com.app.businessException.BusinessException;
import com.app.dao.CustomerDAO;
import com.app.dao.impl.CustomerDAOImpl;
import com.app.model.Cart;
import com.app.model.Product;
import com.app.service.customerService.CustomerService;

public class CustomerServiceImpl implements CustomerService {

	private CustomerDAO customerDAO = new CustomerDAOImpl();

	@Override
	public boolean customerSignup(String email, String firstName, String lastName, String password)
			throws BusinessException {

		// 1. first validate user inputs
		if (validateEmail(email) && firstName.matches("[a-zA-Z]{2-15}") && lastName.matches("[a-zA-Z]{2-15}")) {
			// 2. check if user already Exists
			if (customerAlreadyExists(email)) {
				throw new BusinessException("Customer already exists!");
			} else {
				boolean signUpSuccess = customerDAO.customerSignup(email, firstName, lastName, password);
				return signUpSuccess;
			}
		}
		// User entered invalid data throw an exception
		else {
			throw new BusinessException("Enter valid data!");
		}
	}

	@Override
	public boolean customerLogin(String userEmail, String userPwd) throws BusinessException {
		boolean loginSuccess = false;
		// first validate email and then call DAO login.
		if (validateEmail(userEmail)) {
			loginSuccess = customerDAO.customerLogin(userEmail, userPwd);
		} else {
			throw new BusinessException("Please enter a valid email!");
		}
		return loginSuccess;
	}

	@Override
	public List<Product> getAllProducts() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cart addToCart(int productID) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cart viewCart() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean placeOrder(int productID) throws BusinessException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Product> getPlacedOrders() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean changeOrderStatusToRecieved(int productID) throws BusinessException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean logOut() throws BusinessException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean customerAlreadyExists(String userEmail) throws BusinessException {
		boolean cstAlreadyExists = false;
		if (validateEmail(userEmail)) {
			customerDAO.customerAlreadyExists(userEmail);
		} else {
			throw new BusinessException("Invalid email!");
		}
		return cstAlreadyExists;
	}

	public boolean validateEmail(String email) {
		boolean isValidEmail = false;
		if (email.matches("[a-zA-z0-9]{2-15}\\@.[a-zA-Z0-9]"))
			isValidEmail = true;
		return isValidEmail;
	}

}
