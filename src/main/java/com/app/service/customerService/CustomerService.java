package com.app.service.customerService;

import java.util.List;

import com.app.businessException.BusinessException;
import com.app.model.Cart;
import com.app.model.Product;

public interface CustomerService {
	// 1. For new customer sign-up
	public boolean customerSignup(String email, String firstName, String lastName, String password)
			throws BusinessException;

	// 2. For new customer login
	public boolean customerLogin(String userEmail, String userPwd) throws BusinessException;

	// 3. For getting all products
	public List<Product> getAllProducts() throws BusinessException;

	// 4. For adding products to cart
	public Cart addToCart(int productID) throws BusinessException;

	// 5. For checking cart
	public Cart viewCart() throws BusinessException;

	// 5. For placing order
	public boolean placeOrder(int productID) throws BusinessException;

	// 6. For checking all orders
	public List<Product> getPlacedOrders() throws BusinessException;

	// 7. For changing status of order as received
	public boolean changeOrderStatusToRecieved(int productID) throws BusinessException;

	// 8. For logging out
	public boolean logOut() throws BusinessException;

	// 9. Check if customer already exists before signing up
	public boolean customerAlreadyExists(String userEmail) throws BusinessException;
}
