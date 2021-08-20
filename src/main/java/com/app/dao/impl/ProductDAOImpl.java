package com.app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.log4j.Logger;

import com.app.dao.ProductDAO;
import com.app.dao.dbutil.MySqlDBConnection;
import com.app.exception.BusinessException;
import com.app.model.Product;

public class ProductDAOImpl implements ProductDAO {
	private Logger log = Logger.getLogger(ProductDAOImpl.class);

	@Override
	public List<Product> getAllProducts() throws BusinessException {
		List<Product> productList = new ArrayList<Product>();
		try (Connection connection = MySqlDBConnection.getConnection()) {
			String sql = "select prod_id, prod_name, prod_category, prod_provider, prod_description, prod_price, prod_rating from product";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Product product = new Product();
				product.setProductID(resultSet.getInt("prod_id"));
				product.setProductName(resultSet.getString("prod_name"));
				product.setCategory(resultSet.getString("prod_category"));
				product.setPrice(resultSet.getDouble("prod_price"));
				product.setRating(resultSet.getDouble("prod_rating"));
				product.setDescription(resultSet.getString("prod_description"));
				product.setProvider(resultSet.getString("prod_provider"));
				productList.add(product);
			}
			if (productList.size() == 0) {
				throw new BusinessException("Store is empty. No products found!");
			}
		} catch (ClassNotFoundException | SQLException e) {
			log.error(e);
			throw new BusinessException("Getting products failed. Please contact sysadmin!");
		}
		return productList;
	}

	@Override
	public Product getProductById(int productID) throws BusinessException {
		Product product = new Product();
		try (Connection connection = MySqlDBConnection.getConnection()) {
			String sql = "select prod_id, prod_name, prod_category, prod_provider, prod_description, prod_price, prod_rating from product where prod_id=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, productID);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				product.setProductID(resultSet.getInt("prod_id"));
				product.setCategory(resultSet.getString("prod_category"));
				product.setProductName(resultSet.getString("prod_name"));
				product.setProvider(resultSet.getString("prod_provider"));
				product.setDescription(resultSet.getString("prod_description"));
				product.setPrice(resultSet.getDouble("prod_price"));
				product.setRating(resultSet.getDouble("prod_rating"));
				product.setCategory(resultSet.getString("prod_category"));
				product.setCategory(resultSet.getString("prod_category"));
			}
			if (Objects.isNull(product)) {
				log.info("No product with that id exists");
				throw new BusinessException("No product exist with that product id");
			}

			return product;
		} catch (ClassNotFoundException | SQLException e) {
			log.error(e);
			throw new BusinessException("Internal error occured contact sysadmin");
		}
	}

	@Override
	public boolean productAlreadyExists(String productName) throws BusinessException {
		boolean alreadyExists = false;
		try (Connection connection = MySqlDBConnection.getConnection()) {
			String sql = "select * from product where prod_name=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, productName);
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
