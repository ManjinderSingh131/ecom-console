package com.app.dao;

import com.app.exception.BusinessException;
import com.app.model.Cart;

public interface CartDAO {
	public boolean addToCart(int productID, int customerID) throws BusinessException;

	public Cart viewCart(int customerID) throws BusinessException;

	public boolean emptyCart(int customerID) throws BusinessException;

}
