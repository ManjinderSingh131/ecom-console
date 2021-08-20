package com.app.service;

import java.util.List;

import com.app.exception.BusinessException;
import com.app.model.Cart;
import com.app.model.Customer;
import com.app.model.Order;
import com.app.model.Product;

public interface CustomerService {
	// 1. For new customer sign-up
	public boolean customerSignup(String email, String firstName, String lastName, String password)
			throws BusinessException;

	// 2. For new customer login
	public Customer customerLogin(String userEmail, String userPwd) throws BusinessException;

	// 3. For getting all products
	public List<Product> getAllProducts() throws BusinessException;

	// 4. For adding products to cart
	public boolean addToCart(int productID, int customerID) throws BusinessException;

	// 5. For checking cart
	public Cart viewCart(int customerID) throws BusinessException;

	// 5. For placing order
	public boolean placeOrder(Cart cart) throws BusinessException;

	// 6. For checking all orders made by customer
	public List<Order> getCustomerOrdersByID(int customerID) throws BusinessException;

	// 7. For changing status of order as received
	public boolean changeOrderStatusToRecieved(int productID) throws BusinessException;

	// 8. For logging out
	public boolean logOut() throws BusinessException;

	// 9. Check if customer already exists before signing up
	public boolean customerAlreadyExists(String userEmail) throws BusinessException;

	// 10. Empty cart
	public boolean emptyCart(int customerID) throws BusinessException;

}
