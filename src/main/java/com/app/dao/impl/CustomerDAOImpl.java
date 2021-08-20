package com.app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.app.dao.CartDAO;
import com.app.dao.CustomerDAO;
import com.app.dao.OrderDAO;
import com.app.dao.ProductDAO;
import com.app.dao.dbutil.MySqlDBConnection;
import com.app.exception.BusinessException;
import com.app.model.Cart;
import com.app.model.Customer;
import com.app.model.Order;
import com.app.model.Product;
import com.app.service.AuthPwdService;
import com.app.service.impl.AuthPwdServiceImpl;

public class CustomerDAOImpl implements CustomerDAO {

	private static Logger log = Logger.getLogger(CustomerDAOImpl.class);
	AuthPwdService authenticator = new AuthPwdServiceImpl();
	ProductDAO productDAO = new ProductDAOImpl();
	CartDAO cartDAO = new CartDAOImpl();
	OrderDAO orderDAO = new OrderDAOImpl();

	// customer sign-up
	@Override
	public boolean customerSignup(String email, String firstName, String lastName, String password)
			throws BusinessException {
		boolean signUpSuccess = false;

		try (Connection connection = MySqlDBConnection.getConnection()) {
			String sql = "insert into customer(cst_email, cst_password, cst_firstName, cst_lastName) values(?,?,?,?)";
			// hash the customer password ->
			String password_hash = authenticator.generatePwdHash(password);
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password_hash);
			preparedStatement.setString(3, firstName);
			preparedStatement.setString(4, lastName);

			int rowsInserted = preparedStatement.executeUpdate();
			if (rowsInserted == 1) {
				signUpSuccess = true;
			}
		} catch (ClassNotFoundException | SQLException e) {
			log.error(e);
			throw new BusinessException("Sign up failed! please contact sysadmin or try again.");
		}
		return signUpSuccess;
	}

	// customer login
	@Override
	public Customer customerLogin(String userEmail, String userPwd) throws BusinessException {
		Customer customer = new Customer();
		boolean loginSuccess = false;
		String pwdInDB = "";
		try (Connection connection = MySqlDBConnection.getConnection()) {
			String sql = "select cst_id, cst_email, cst_firstname, cst_lastname, cst_password from customer where cst_email=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, userEmail);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				pwdInDB += resultSet.getString("cst_password");
				if (pwdInDB.equals("")) {
					log.error("Invalid user-name or password, please try again.");
					throw new BusinessException("Wrong password");
				} else {
					loginSuccess = authenticator.verifyPwd(userPwd, pwdInDB);
					// if logged in successfully
					if (loginSuccess) {
						customer.setFirstName(resultSet.getString("cst_firstname"));
						customer.setLastName(resultSet.getString("cst_lastname"));
						customer.setCustomerID(resultSet.getInt("cst_id"));
						customer.setCustomerEmail(resultSet.getString("cst_email"));
						customer.setLoggedIn(true);
					} else {
						log.info("Authentication failure, try again!");
						throw new BusinessException("Authentication error!");
					}
				}
			}

			return customer;
		} catch (ClassNotFoundException | SQLException e) {
			log.error(e);
			throw new BusinessException("Sign in failed! please contact sysadmin or try again.");
		}

	}

	@Override
	public boolean addToCart(int productID, int customerID) throws BusinessException {
		return cartDAO.addToCart(productID, customerID);
	}

	@Override
	public Cart viewCart(int customerID) throws BusinessException {
		return cartDAO.viewCart(customerID);
	}

	@Override
	public boolean placeOrder(Cart cart) throws BusinessException {
		return orderDAO.placeOrder(cart);
	}

	@Override
	public List<Order> getCustomerOrdersByID(int customerID) throws BusinessException {
		return orderDAO.getCustomerOrdersByID(customerID);
	}

	@Override
	public boolean changeOrderStatusToRecieved(int orderID) throws BusinessException {
		return orderDAO.markOrderAsRecieved(orderID);
	}

	@Override
	public boolean logOut() throws BusinessException {
		return true;
	}

	@Override
	public boolean customerAlreadyExists(String userEmail) throws BusinessException {
		boolean alreadyExists = false;
		try (Connection connection = MySqlDBConnection.getConnection()) {
			String sql = "select cst_id from customer where cst_email=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, userEmail);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				alreadyExists = true;
			} else {
				alreadyExists = false;
			}

		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error occured, please contact support");
		}
		return alreadyExists;
	}

	@Override
	public List<Product> getAllProducts() throws BusinessException {
		return productDAO.getAllProducts();
	}

	@Override
	public Product getProductById(int productID) throws BusinessException {
		return productDAO.getProductById(productID);
	}

	@Override
	public boolean emptyCart(int customerID) throws BusinessException {
		return cartDAO.emptyCart(customerID);
	}

}
