package com.app.model;

import java.sql.Date;

public class Order {
	int orderId;
	Date orderedOn;
	String status;
	int customerID;
	int productID;
	double price;
	Date shippedOn;
	Date recievedOn;

	public int getOrderId() {
		return orderId;
	}

	public Date getShippedOn() {
		return shippedOn;
	}

	public void setShippedOn(Date shippedOn) {
		this.shippedOn = shippedOn;
	}

	public Date getRecievedOn() {
		return recievedOn;
	}

	public void setRecievedOn(Date recievedOn) {
		this.recievedOn = recievedOn;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Date getOrderedOn() {
		return orderedOn;
	}

	public void setOrderedOn(Date orderedOn) {
		this.orderedOn = orderedOn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderedOn=" + orderedOn + ", status=" + status + ", customerID="
				+ customerID + ", productID=" + productID + ", price=" + price + ", shippedOn=" + shippedOn
				+ ", recievedOn=" + recievedOn + "]";
	}

}
