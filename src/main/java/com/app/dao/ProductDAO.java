package com.app.dao;

import java.util.List;

import com.app.exception.BusinessException;
import com.app.model.Product;

public interface ProductDAO {
	// Get product by id
	public Product getProductById(int productID) throws BusinessException;

	// Get all products
	public List<Product> getAllProducts() throws BusinessException;

	// Product Already Exists
	public boolean productAlreadyExists(String productName) throws BusinessException;
}
