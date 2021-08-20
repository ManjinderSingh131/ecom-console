package com.app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.app.dao.EmployeeDAO;
import com.app.dao.OrderDAO;
import com.app.dao.ProductDAO;
import com.app.dao.dbutil.MySqlDBConnection;
import com.app.exception.BusinessException;
import com.app.model.Customer;

public class EmployeeDAOImpl implements EmployeeDAO {
	private Logger log = Logger.getLogger(EmployeeDAOImpl.class);
	ProductDAO productDAO = new ProductDAOImpl();
	OrderDAO orderDAO = new OrderDAOImpl();

	@Override
	public boolean addNewProduct(String prodName, double price, String category, String provider, String description,
			double prodRating) throws BusinessException {
		boolean productAdditionSuccess = false;
		try (Connection connection = MySqlDBConnection.getConnection()) {
			String sql = "insert into product(prod_name, prod_price, prod_category, prod_provider, prod_description, prod_rating) values(?,?,?,?,?,?)";
			// hash the customer password ->
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, prodName);
			preparedStatement.setDouble(2, price);
			preparedStatement.setString(3, category);
			preparedStatement.setString(4, provider);
			preparedStatement.setString(5, description);
			preparedStatement.setDouble(6, prodRating);

			int rowsInserted = preparedStatement.executeUpdate();
			if (rowsInserted == 1) {
				productAdditionSuccess = true;
			}
		} catch (ClassNotFoundException | SQLException e) {
			log.error(e);
			throw new BusinessException("Adding product failed! please contact sysadmin or try again.");
		}
		return productAdditionSuccess;
	}

	@Override
	public boolean login(String empId, String pwd) throws BusinessException {
		if (empId.equals("manjinder@mail.com") && pwd.equals("pass123")) {
			return true;
		}
		return false;
	}

	@Override
	public List<Customer> searchCustomerByEmail(String cstEmail) throws BusinessException {
		return null;
	}

	@Override
	public Customer searchCustomerByName(String name) throws BusinessException {
		return null;
	}

	@Override
	public Customer searchCustomerById(int id) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateOrderStatusAsShipped(int orderId) throws BusinessException {
		return orderDAO.markOrderAsShipped(orderId);
	}

	@Override
	public boolean productAlreadyExists(String productName) throws BusinessException {
		return productDAO.productAlreadyExists(productName);
	}

	@Override
	public Customer searchCustomerByOrderId(int ordId) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
