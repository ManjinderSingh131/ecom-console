package com.app.dao;

import java.util.List;

import com.app.exception.BusinessException;
import com.app.model.Cart;
import com.app.model.Order;

public interface OrderDAO {
	public boolean placeOrder(Cart cart) throws BusinessException;

	public List<Order> getCustomerOrdersByID(int customerID) throws BusinessException;

	public boolean markOrderAsRecieved(int orderID) throws BusinessException;

	public boolean markOrderAsShipped(int orderID) throws BusinessException;
}
