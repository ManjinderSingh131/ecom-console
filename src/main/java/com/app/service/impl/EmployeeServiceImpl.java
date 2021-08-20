package com.app.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.app.dao.EmployeeDAO;
import com.app.dao.impl.EmployeeDAOImpl;
import com.app.exception.BusinessException;
import com.app.model.Customer;
import com.app.service.EmployeeService;

public class EmployeeServiceImpl implements EmployeeService {
	private Logger log = Logger.getLogger(EmployeeServiceImpl.class);
	EmployeeDAO employeeDAO = new EmployeeDAOImpl();

	@Override
	public boolean addNewProduct(String prodName, double price, String category, String provider, String description,
			double prodRating) throws BusinessException {

		boolean productAdditionSuccess = false;

		if (productAlreadyExists(prodName)) {
			log.info("Product with that name already exists!");
		} else {
			productAdditionSuccess = employeeDAO.addNewProduct(prodName, price, category, provider, description,
					prodRating);
		}
		return productAdditionSuccess;
	}

	@Override
	public boolean login(String empName, String pwd) throws BusinessException {
		return employeeDAO.login(empName, pwd);
	}

	@Override
	public List<Customer> searchCustomerByEmail(String cstEmail) throws BusinessException {

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
	public boolean updateOrderStatusAsShipped(int orderId) throws BusinessException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean productAlreadyExists(String productName) throws BusinessException {
		boolean alreadyExists = employeeDAO.productAlreadyExists(productName);
		return alreadyExists;
	}

}
