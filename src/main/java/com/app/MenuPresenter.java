package com.app;

import org.apache.log4j.Logger;

public class MenuPresenter {
	private Logger log = Logger.getLogger(MenuPresenter.class);

	///// Home menu
	public void getHomeMenu() {
		log.info("----------------------------------------");
		log.info("========================================");
		log.info("\tWelcome to Ecom-Console");
		log.info("========================================");
		log.info("----------------------------------------");
		log.info("\n1) Login as customer");
		log.info("2) Sign up as customer");
		log.info("3) Login as Employee");
		log.info("4) Exit");
		log.info("Please enter choice in number 1 to 4");
	}

	// customer dashboard
	public void getCustomerDashboard(String customerName) {
		log.info("\n=====================");
		log.info("Customer Dashboard");
		log.info("=====================");
		log.info("Welcome back " + customerName + "! We are glad to have you back!");
		log.info("1. List products: ");
		log.info("2. Add products to cart: ");
		log.info("3. View cart: ");
		log.info("4. Place Order: ");
		log.info("5. List My Orders: ");
		log.info("6. Mark order as shipped: ");
		log.info("7. logout: ");
	}

	// employee dashboard
	public void getEmployeeDashboard() {
		log.info("\n=====================");
		log.info("Employee Dashboard");
		log.info("=====================");
		log.info("Welcome");
		log.info("1. List products: ");
		log.info("2. Add new product:");
		log.info("3. Check customer's orders: ");
		log.info("4. Mark customer order as shipped: ");
		log.info("5. Exit");
		log.info("Enter your choice: ");
	}
}
