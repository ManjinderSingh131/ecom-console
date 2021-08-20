package com.app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.app.dao.CartDAO;
import com.app.dao.dbutil.MySqlDBConnection;
import com.app.exception.BusinessException;
import com.app.model.Cart;
import com.app.model.Product;

public class CartDAOImpl implements CartDAO {
	private Logger log = Logger.getLogger(CartDAOImpl.class);

	@Override
	public boolean addToCart(int productID, int customerID) throws BusinessException {
		boolean productAdded = false;
		try (Connection connection = MySqlDBConnection.getConnection()) {
			String sql = "insert into cart(cart_prod_id, cart_cst_id) values(?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, productID);
			preparedStatement.setInt(2, customerID);
			int c = preparedStatement.executeUpdate();
			if (c == 1) {
				log.info("item added successfully");
				productAdded = true;
			} else {
				log.info("Adding item failed!");
			}
		} catch (ClassNotFoundException | SQLException e) {
			log.error(e);
			throw new BusinessException("Internal error occured contact sysadmin");
		}
		return productAdded;
	}

	@Override
	public Cart viewCart(int customerID) throws BusinessException {
		Cart cart = new Cart();
		try (Connection connection = MySqlDBConnection.getConnection()) {
			List<Product> productList = new ArrayList<>();
			String sql = "select cart_cst_id, cart_id, cart_prod_id, prod_id, prod_name, prod_price, prod_category, prod_provider, prod_description, prod_rating from cart join product on cart_prod_id = prod_id join customer on cart_cst_id = cst_id where cst_id=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, customerID);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				// Get customer id
				cart.setCartID(resultSet.getInt("cart_id"));
				// Get customer id
				cart.setCustomerID(resultSet.getInt("cart_cst_id"));
				// Get product and add it in product list
				Product product = new Product();
				product.setProductID(resultSet.getInt("prod_id"));
				product.setCategory(resultSet.getString("prod_category"));
				product.setPrice(resultSet.getDouble("prod_price"));
				product.setProductName(resultSet.getString("prod_name"));
				product.setDescription(resultSet.getString("prod_description"));
				product.setProvider(resultSet.getString("prod_provider"));
				product.setRating(resultSet.getDouble("prod_rating"));
				productList.add(product);
			}
			cart.setProductList(productList);

		} catch (ClassNotFoundException | SQLException e) {
			log.error(e);
			throw new BusinessException("Internal error occured contact sysadmin");
		}
		return cart;
	}

	@Override
	public boolean emptyCart(int customerID) throws BusinessException {
		boolean cartEmptied = false;
		try (Connection connection = MySqlDBConnection.getConnection()) {
			String sql = "delete from cart where cart_cst_id=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, customerID);
			preparedStatement.executeUpdate();
			cartEmptied = true;
		} catch (ClassNotFoundException | SQLException e) {
			log.error(e);
			throw new BusinessException("Internal error occured contact sysadmin");
		}
		return cartEmptied;
	}

}
