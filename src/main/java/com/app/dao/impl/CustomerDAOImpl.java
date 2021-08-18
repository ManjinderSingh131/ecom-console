package com.app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.app.businessException.BusinessException;
import com.app.dao.CustomerDAO;
import com.app.dao.dbutils.MySqlDBConnection;
import com.app.model.Cart;
import com.app.model.Product;
import com.app.service.authPwdService.AuthPwdService;
import com.app.service.authPwdService.impl.AuthPwdSericeImpl;

public class CustomerDAOImpl implements CustomerDAO {

	private static Logger log = Logger.getLogger(CustomerDAOImpl.class);
	AuthPwdService authenticator = new AuthPwdSericeImpl();

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
	public boolean customerLogin(String userEmail, String userPwd) throws BusinessException {
		boolean loginSuccess = false;
		String pwdInDB = "";
		try (Connection connection = MySqlDBConnection.getConnection()) {
			String sql = "select cst_password from customer where cst_email=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, userEmail);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				pwdInDB += resultSet.getString("cst_password");
				System.out.println(resultSet.getString("cst_password"));
			}
			log.info(pwdInDB);

			if (pwdInDB.equals("")) {
				log.error("Invalid password, please try again.");
				throw new BusinessException("Wrong password");
			} else {
				System.out.println(pwdInDB);
				log.info(pwdInDB);
				loginSuccess = authenticator.verifyPwd(userPwd, pwdInDB);
			}
			return loginSuccess;

		} catch (ClassNotFoundException | SQLException e) {
			log.error(e);
			throw new BusinessException("Sign up failed! please contact sysadmin or try again.");
		}

	}

	@Override
	public List<Product> getAllProducts() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cart addToCart(int productID) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cart viewCart() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean placeOrder(int productID) throws BusinessException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Product> getPlacedOrders() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean changeOrderStatusToRecieved(int productID) throws BusinessException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean logOut() throws BusinessException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean customerAlreadyExists(String userEmail) throws BusinessException {
		boolean alreadyExists = false;
		try (Connection connection = MySqlDBConnection.getConnection()) {
			String sql = "select * from customer where email=?";
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

}
