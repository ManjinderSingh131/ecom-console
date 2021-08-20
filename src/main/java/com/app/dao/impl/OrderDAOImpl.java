package com.app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.app.dao.OrderDAO;
import com.app.dao.dbutil.MySqlDBConnection;
import com.app.exception.BusinessException;
import com.app.model.Cart;
import com.app.model.Order;
import com.app.model.Product;

public class OrderDAOImpl implements OrderDAO {
	private Logger log = Logger.getLogger(OrderDAOImpl.class);

	@Override
	public boolean placeOrder(Cart cart) throws BusinessException {
		boolean orderPlaced = false;
		try (Connection connection = MySqlDBConnection.getConnection()) {
			String sql = "insert into cst_orders(ord_cst_id, ord_prd_id, ord_price) values(?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			List<Product> orderList = cart.getProductList();
			int size = orderList.size();
			int sum = 0;
			int customerID = cart.getCustomerID();
			for (Product p : orderList) {
				int productID = p.getProductID();
				double productPrice = p.getPrice();
				preparedStatement.setInt(1, customerID);
				preparedStatement.setInt(2, productID);
				preparedStatement.setDouble(3, productPrice);
				int c = preparedStatement.executeUpdate();
				sum += c;
			}
			if (sum == size) {
				log.info("Order Successfully placed!");
				orderPlaced = true;
			} else {
				log.info("Some error occured! please try again");
			}
		} catch (ClassNotFoundException | SQLException e) {
			log.error(e);
			throw new BusinessException("Internal error occured contact sysadmin");
		}

		return orderPlaced;
	}

	@Override
	public List<Order> getCustomerOrdersByID(int customerID) throws BusinessException {
		List<Order> orderList = new ArrayList<>();
		try (Connection connection = MySqlDBConnection.getConnection()) {
			String sql = "select ord_id, ord_cst_id, ord_prd_id, ord_price, status, prod_name, prod_description, prod_provider from cst_orders join product on ord_prd_id=prod_id join customer on ord_cst_id=cst_id where cst_id=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, customerID);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Order order = new Order();
				order.setOrderId(resultSet.getInt("ord_id"));
				order.setCustomerID(resultSet.getInt("ord_cst_id"));
				order.setProductID(resultSet.getInt("ord_prd_id"));
				order.setPrice(resultSet.getDouble("ord_price"));
				order.setProductName(resultSet.getString("prod_name"));
				order.setProductDescription(resultSet.getString("prod_description"));
				order.setProvider(resultSet.getString("prod_provider"));
				order.setStatus(resultSet.getString("status"));
				orderList.add(order);
			}
			if (orderList.isEmpty()) {
				log.info("No orders done so far!");
			}

		} catch (ClassNotFoundException | SQLException e) {
			log.error(e);
			throw new BusinessException("Internal error occured contact sysadmin");
		}
		return orderList;
	}

	// Mark order as received
	@Override
	public boolean markOrderAsRecieved(int orderID) throws BusinessException {
		boolean markSuccess = false;
		try (Connection connection = MySqlDBConnection.getConnection()) {
			String sql = "update cst_orders set status=? where ord_id=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, "Received");
			preparedStatement.setInt(2, orderID);
			int c = preparedStatement.executeUpdate();
			if (c == 0) {
				throw new BusinessException("No such order found! please check again");
			} else {
				markSuccess = true;
			}

		} catch (ClassNotFoundException | SQLException e) {
			log.error(e);
			throw new BusinessException("Internal error occured contact sysadmin");
		}

		return markSuccess;
	}

	@Override
	public boolean markOrderAsShipped(int orderID) throws BusinessException {
		boolean markSuccess = false;
		try (Connection connection = MySqlDBConnection.getConnection()) {
			String sql = "update cst_orders set status=? where orderID=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, "Shipped");
			preparedStatement.setInt(2, orderID);
			int c = preparedStatement.executeUpdate();
			if (c == 0) {
				throw new BusinessException("No such order found! please check again");
			} else {
				markSuccess = true;
			}

		} catch (ClassNotFoundException | SQLException e) {
			log.error(e);
			throw new BusinessException("Internal error occured contact sysadmin");
		}

		return markSuccess;
	}

}
