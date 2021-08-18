package com.app;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.app.businessException.BusinessException;
import com.app.service.customerService.CustomerService;
import com.app.service.customerService.impl.CustomerServiceImpl;

public class Main {
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		Logger log = Logger.getLogger(Main.class);

//		log.info("--------------------------------------");
//		log.info("Welcome To Console Based Ecommerce App");
//		log.info("--------------------------------------");
//
//		int ch = 0;
//		do {
//			log.info("1) Login as customer");
//			log.info("2) Login as Employee");
//			log.info("3) Register as Employee");
//			log.info("4) Exit");
//			log.info("Enter your choice between 1-4");
//			try {
//				ch = Integer.parseInt(scanner.nextLine());
//			} catch (NumberFormatException e) {
//				log.info("Entry is not appropriate. Please enter a valid choice\n");
//				continue;
//			}
//
//			switch (ch) {
//			case 1:
//				log.info("Work under construction");
//				break;
//			case 2:
//				log.info("Enter your employee id: ");
//				String emplID = scanner.nextLine();
//				log.info("Enter your password: ");
//				String password = scanner.nextLine();
//
//				if (emplID.equals("manjinder@ecomapp.com") && password.equals("manjinderpwd")) {
//					log.info("Login Successful!");
//					log.info("\n\n");
//					log.info("-------------------------------------------");
//					log.info("------Welcome to employee dashboard--------");
//					log.info("-------------------------------------------\n");
//					int choice = 0;
//					do {
//						log.info("1) Add new product");
//						log.info("2) Update product status as shipped");
//						log.info("3) Search customers");
//						log.info("4) Check all orders");
//						log.info("5) Logout");
//						log.info("Please enter your choice:");
//						try {
//							choice = Integer.parseInt(scanner.nextLine());
//						} catch (NumberFormatException e) {
//							log.warn("Please enter a valid number.");
//						}
//					} while (choice != 5);
//
//				} else {
//					log.info("Login Failed");
//					break;
//				}
//			case 3:
//				log.info("Work under construction");
//			}
//		} while (ch != 4);
//

		CustomerService customerService = new CustomerServiceImpl();
		try {
			boolean success = customerService.customerSignup("manjinder@mail.com", "Manjinder", "Singh",
					"This is a test");
			System.out.println(success);
		} catch (BusinessException e) {
			log.error("Sign up failed, please try again or contact sysadmin");
			log.error(e);
		}

		try {
			boolean login = customerService.customerLogin("manjinder@mail.com", "This is a tt");
			log.info(login);
		} catch (BusinessException e) {
			log.error("Login Failed!");
			log.error(e);
		}

	}
}
