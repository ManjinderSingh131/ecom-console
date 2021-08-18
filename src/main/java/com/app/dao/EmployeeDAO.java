package com.app.dao;

import java.util.List;

import com.app.businessException.BusinessException;
import com.app.model.Customer;

public interface EmployeeDAO {
	// 1. add new product
	public boolean addNewProduct(String prodName, double price, String category, String provider, String description)
			throws BusinessException;

	// 2. login
	public boolean login(String empName, String pwd) throws BusinessException;

	// 3. Search customers by email
	public List<Customer> searchCustomerByEmail(String cstEmail) throws BusinessException;

	// 3. b Search customers by id
	public Customer searchCustomerById(int cstId) throws BusinessException;

	// 3. c Search customers by name
	public Customer searchCustomerByName(String name) throws BusinessException;

	// 3. d Search customers by orderId
	public Customer searchCustomrById(int id) throws BusinessException;

	// 4. Mark order status as shipped
	public boolean updateOrderStatusAsShipped(int orderId);
}
