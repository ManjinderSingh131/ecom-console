package com.app.model;

import java.util.List;

public class Cart {
	List<Product> productList;
	int customerID;
	int cartID;

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public int getCartID() {
		return cartID;
	}

	public void setCartID(int cartID) {
		this.cartID = cartID;
	}

	@Override
	public String toString() {
		return "Cart [productList=" + productList + ", customerID=" + customerID + ", cartID=" + cartID + "]";
	}
}
