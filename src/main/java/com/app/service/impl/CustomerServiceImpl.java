package com.app.service.impl;

import java.util.List;
import java.util.Objects;

import org.apache.log4j.Logger;

import com.app.dao.CustomerDAO;
import com.app.dao.impl.CustomerDAOImpl;
import com.app.exception.BusinessException;
import com.app.model.Cart;
import com.app.model.Customer;
import com.app.model.Order;
import com.app.model.Product;
import com.app.service.CustomerService;

public class CustomerServiceImpl implements CustomerService {
	Cart cart = new Cart();
	private CustomerDAO customerDAO = new CustomerDAOImpl();
	private Logger log = Logger.getLogger(CustomerServiceImpl.class);

	@Override
	public boolean customerSignup(String email, String firstName, String lastName, String password)
			throws BusinessException {

		boolean signUpSuccess = false;
		// 1. first validate user inputs
		if (validateEmail(email) && firstName.matches("[a-zA-Z]{2,15}") && lastName.matches("[a-zA-Z]{2,15}")) {
			// 2. check if user already Exists
			if (customerAlreadyExists(email)) {
				log.info("Customer already exists with that email! please check your email again.");
			} else {
				signUpSuccess = customerDAO.customerSignup(email, firstName, lastName, password);
			}
		}
		// User entered invalid data throw an exception
		else {
			throw new BusinessException("Enter valid data!");
		}
		return signUpSuccess;
	}

	@Override
	public Customer customerLogin(String userEmail, String userPwd) throws BusinessException {
		// first validate email and then call DAO login.
		if (validateEmail(userEmail)) {
			Customer customer = customerDAO.customerLogin(userEmail, userPwd);
			if (Objects.isNull(customer.getFirstName()))
				throw new BusinessException("Login Failed! Wrong credentials.");
			return customer;
		} else {
			throw new BusinessException("Please enter a valid email!");
		}

	}

	@Override
	public List<Product> getAllProducts() throws BusinessException {
		List<Product> productList = customerDAO.getAllProducts();
		return productList;
	}

	@Override
	public boolean addToCart(int productID, int customerID) throws BusinessException {
		boolean productAdded = false;
		if (productID >= 1) {
			productAdded = customerDAO.addToCart(productID, customerID);
		} else {
			throw new BusinessException("Invalid product ID");
		}
		return productAdded;
	}

	@Override
	public Cart viewCart(int customerID) throws BusinessException {
		Cart cart = customerDAO.viewCart(customerID);
		return cart;
	}

	@Override
	public boolean placeOrder(Cart cart) throws BusinessException {
		boolean orderPlaced = false;
		orderPlaced = customerDAO.placeOrder(cart);
		return orderPlaced;
	}

	@Override
	public List<Order> getCustomerOrdersByID(int customerID) throws BusinessException {
		return customerDAO.getCustomerOrdersByID(customerID);
	}

	@Override
	public boolean changeOrderStatusToRecieved(int orderID) throws BusinessException {
		boolean statusUpdated = false;
		if (orderID < 0) {
			throw new BusinessException("Invalid order id! please check again");
		} else {
			statusUpdated = customerDAO.changeOrderStatusToRecieved(orderID);
		}
		return statusUpdated;
	}

	@Override
	public boolean logOut() throws BusinessException {
		log.info("Goodbye! please visit again!");
		System.exit(1);
		return true;
	}

	@Override
	public boolean customerAlreadyExists(String userEmail) throws BusinessException {
		boolean cstAlreadyExists = false;
		if (validateEmail(userEmail)) {
			cstAlreadyExists = customerDAO.customerAlreadyExists(userEmail);
		} else {
			throw new BusinessException("Invalid email!");
		}
		return cstAlreadyExists;
	}

	public boolean validateEmail(String email) {
		boolean isValidEmail = true;
//		if (email.matches("[a-zA-Z0-9]{2,15}@[a-zA-Z]{5,11}/.[a-zA-Z]{2,7}"))
//			isValidEmail = true;
		return isValidEmail;
	}

	@Override
	public boolean emptyCart(int customerID) throws BusinessException {
		return customerDAO.emptyCart(customerID);
	}

}
