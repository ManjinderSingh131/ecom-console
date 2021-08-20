package com.app;

import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.app.dao.EmployeeDAO;
import com.app.dao.impl.EmployeeDAOImpl;
import com.app.exception.BusinessException;
import com.app.model.Cart;
import com.app.model.Customer;
import com.app.model.Order;
import com.app.model.Product;
import com.app.service.CustomerService;
import com.app.service.EmployeeService;
import com.app.service.impl.CustomerServiceImpl;
import com.app.service.impl.EmployeeServiceImpl;

public class Main {

	public static void main(String[] args) {
		Customer customer = new Customer();
		Logger log = Logger.getLogger(Main.class);
		MenuPresenter menuPresenter = new MenuPresenter();
		Scanner scan = new Scanner(System.in);
		CustomerService customerService = new CustomerServiceImpl();
		EmployeeService employeeService = new EmployeeServiceImpl();
		boolean emplLoggedIn = false;
		/*
		 * Home menu reference 1) Login as customer 2) Sign up as customer 3) Login as
		 * Employee 4) Exit
		 */

		int ch = 0;
		menuPresenter.getHomeMenu();
		do {
			try {
				ch = Integer.parseInt(scan.nextLine());
			} catch (NumberFormatException e) {
				log.info("Please enter a valid number!");
				continue;
			}
			switch (ch) {
			case 1:
				log.info("---------------------");
				log.info("Welcome to login page");
				log.info("---------------------");
				log.info("Enter email:");
				String email = scan.nextLine();
				log.info("Enter password:");
				String password = scan.nextLine();
				try {
					customer = customerService.customerLogin(email, password);
				} catch (BusinessException e) {
					log.info(e.getMessage());
				}
				if (!customer.isLoggedIn()) {
					continue;
				} else {
					/*
					 * Customer Dashboard"); Welcome back " + customerName + "! We are glad to have
					 * you back! 1. List products: 2. Add products to cart: 3. View cart: 4. Place
					 * Order: 5. List Orders: 6. Mark order as shipped: 7. logout:
					 */
					int choiceCstDashboard = 0;
					do {
						menuPresenter.getCustomerDashboard(customer.getFirstName());
						choiceCstDashboard = Integer.parseInt(scan.nextLine());

						switch (choiceCstDashboard) {
						case 1:
							// list all products!
							try {
								List<Product> productList = customerService.getAllProducts();
								log.info("\n-------------------------------------------------------------------");
								log.info("-----------------------Available products---------------------------");
								log.info("-------------------------------------------------------------------\n");
								productList.stream()
										.forEach(p -> log.info("Product ID:\t" + p.getProductID() + "\nProduct Name:\t"
												+ p.getProductName() + "\n" + "Product Price:\t" + p.getPrice()
												+ "\nCategory:\t" + p.getCategory() + "\nProvider\t" + p.getProvider()
												+ "\nDescription:\t" + p.getDescription() + "\nRating\t\t"
												+ p.getRating() + "\n"));
								log.info("-----------------------------------------------------------------\n");
								continue;
							} catch (BusinessException e) {
								log.error(e);
							}
							break;

						case 2:
							// Add to cart
							log.info("Enter product id to add to cart:");
							int productIDforCart = -1;
							try {
								productIDforCart = Integer.parseInt(scan.nextLine());
							} catch (NumberFormatException e) {
								log.info("Please enter valid input");
							}

							boolean itemAddedToCart = false;
							try {
								itemAddedToCart = customerService.addToCart(productIDforCart, customer.getCustomerID());
							} catch (BusinessException e1) {
								log.info(e1.getMessage());
							}

							if (itemAddedToCart) {
								log.info("Items added in cart Successfully");
							}
							break;

						case 3:
							// View cart ->
							try {
								Cart cart = customerService.viewCart(customer.getCustomerID());
								customer.setCart(cart);
								List<Product> cartProductList = cart.getProductList();
								log.info("\n------------------------------------------------------------------");
								log.info("-----------------------Items in your cart---------------------------");
								log.info("-------------------------------------------------------------------\n");
								cartProductList.stream()
										.forEach(p -> log.info("Product ID:\t" + p.getProductID() + "\nProduct Name:\t"
												+ p.getProductName() + "\n" + "Product Price:\t" + p.getPrice()
												+ "\nCategory:\t" + p.getCategory() + "\nProvider\t" + p.getProvider()
												+ "\nRating\t\t" + p.getRating() + "\n"));
								log.info("-------------------------------------------------------------------");
								log.info("Cart total: " + customer.getCart().getCartTotal());
								log.info("-----------------------------------------------------------------\n");
								continue;

							} catch (BusinessException e) {
								log.info(e.getMessage());
							}
							break;

						case 4:
							// place order
							boolean orderPlaced = false;
							try {
								Cart cart = customerService.viewCart(customer.getCustomerID());
								customer.setCart(cart);
								orderPlaced = customerService.placeOrder(customer.getCart());
								if (orderPlaced)
									customerService.emptyCart(customer.getCustomerID());
							} catch (BusinessException e) {
								log.info(e.getMessage());
							}
							break;

						case 5:
							// List orders
							List<Order> orderList;
							try {
								orderList = customerService.getCustomerOrdersByID(customer.getCustomerID());
								log.info("\n------------------------------------------------------------------");
								log.info("-----------------------Your orders-----------------------------------");
								log.info("-------------------------------------------------------------------\n");
								orderList.stream()
										.forEach(o -> log.info("Order ID:\t" + o.getOrderId() + "\nProduct Name:\t"
												+ o.getProductName() + "\n" + "Product Price:\t" + o.getPrice()
												+ "\nDescription:\t" + o.getProductDescription() + "\nProvider\t"
												+ o.getProvider() + "\nStatus:\t\t" + o.getStatus() + "\n"));

							} catch (BusinessException e) {
								log.info(e.getMessage());
							}

							break;

						case 6:
							// Mark order as recieved
							log.info("Enter order id");
							int inputOrderID = Integer.parseInt(scan.nextLine());
							try {
								if (customerService.changeOrderStatusToRecieved(inputOrderID))
									log.info("Order updated as recieved!");
							} catch (BusinessException e) {
								log.info(e.getMessage());
							}
							break;
						}
					} while (choiceCstDashboard != 7);
				}
				break;

			case 2:
				log.info("---------------------");
				log.info("Welcome to Sign-up page");
				log.info("---------------------");

				log.info("\nEnter email:");
				String SignUpEmail = scan.nextLine();

				log.info("Enter password:");
				String SignUpPassword = scan.nextLine();

				log.info("Enter first name:");
				String firstName = scan.nextLine();

				log.info("Enter last name:");
				String lastName = scan.nextLine();
				boolean signUpSuccess = false;
				do {
					try {
						signUpSuccess = customerService.customerSignup(SignUpEmail, firstName, lastName,
								SignUpPassword);
						if (signUpSuccess) {
							log.info("Signed up successfully!");
							menuPresenter.getHomeMenu();
						} else
							continue;

					} catch (BusinessException e) {
						log.info(e.getMessage());
					}
				} while (signUpSuccess);
				break;

			case 3:
				// login as employee
				EmployeeDAO employeeDAO = new EmployeeDAOImpl();
				log.info("------------------------------");
				log.info("Welcome to employee login page");
				log.info("-------------------------------");
				log.info("Enter email:");
				String empEmail = scan.nextLine();
				log.info("Enter password:");
				String empPassword = scan.nextLine();
				try {
					boolean empLoginSuccess = employeeDAO.login(empEmail, empPassword);
					log.info(empLoginSuccess);
					emplLoggedIn = true;
					if (!emplLoggedIn) {
						continue;
					} else {
						menuPresenter.getEmployeeDashboard();
						int empDashboardChoice = 0;
						do {
							try {
								empDashboardChoice = Integer.parseInt(scan.nextLine());
							} catch (NumberFormatException e) {
								log.info(e.getMessage());
							}
							switch (empDashboardChoice) {
							case 1:
								// list all products!
								try {
									List<Product> productList = customerService.getAllProducts();
									log.info("\n-------------------------------------------------------------------");
									log.info("-----------------------Available products---------------------------");
									log.info("-------------------------------------------------------------------\n");
									productList.stream().forEach(p -> log.info("Product ID:\t" + p.getProductID()
											+ "\nProduct Name:\t" + p.getProductName() + "\n" + "Product Price:\t"
											+ p.getPrice() + "\nCategory:\t" + p.getCategory() + "\nProvider\t"
											+ p.getProvider() + "\nDescription:\t" + p.getDescription() + "\nRating\t\t"
											+ p.getRating() + "\n"));
									log.info("-----------------------------------------------------------------\n");
									continue;
								} catch (BusinessException e) {
									log.error(e);
								}
								break;

							case 2:
								log.info("Enter product name:");
								String productName = scan.nextLine();

								log.info("Enter price");
								double price = Double.parseDouble(scan.nextLine());

								log.info("Enter product category:");
								String productCategory = scan.nextLine();

								log.info("Enter product provider:");
								String productProvider = scan.nextLine();

								log.info("Enter product description:");
								String productDescription = scan.nextLine();

								log.info("Enter product rating");
								double rating = Double.parseDouble(scan.nextLine());

								boolean prodAdded = employeeService.addNewProduct(productName, price, productCategory,
										productProvider, productDescription, rating);
								if (!prodAdded) {
									log.info("Adding product failed");
								}
								break;
							}
						} while (empDashboardChoice != 5);
					}
				} catch (BusinessException e) {
					log.info(e.getMessage());
				}
				break;
			}
		} while (ch != 4);

		scan.close();
	}
}
