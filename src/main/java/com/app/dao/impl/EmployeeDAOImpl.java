package com.app.dao.impl;

import java.util.List;

import com.app.businessException.BusinessException;
import com.app.dao.EmployeeDAO;
import com.app.model.Customer;

public class EmployeeDAOImpl implements EmployeeDAO {

	@Override
	public boolean addNewProduct(String prodName, double price, String category, String provider, String description)
			throws BusinessException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean login(String empName, String pwd) throws BusinessException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Customer> searchCustomerByEmail(String cstEmail) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer searchCustomerById(int cstId) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer searchCustomerByName(String name) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer searchCustomrById(int id) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateOrderStatusAsShipped(int orderId) {
		// TODO Auto-generated method stub
		return false;
	}

}
