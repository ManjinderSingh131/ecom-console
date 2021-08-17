package com.app;

import java.util.Scanner;

import org.apache.log4j.Logger;

public class Main {
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		Logger log = Logger.getLogger(Main.class);

		log.info("--------------------------------------");
		log.info("Welcome To Console Based Ecommerce App");
		log.info("--------------------------------------");

		int ch = 0;
		do {
			log.info("1) Login as customer");
			log.info("2) Login as Employee");
			log.info("3) Register as Employee");
			log.info("4) Exit");

			log.info("Enter your choice between 1-4");
			try {
				ch = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				log.info("Entry is not appropriate. Please enter a valid choice\n");
				continue;
			}

			switch (ch) {
			case 1:
				log.info("Work under construction");
				break;
			case 2:
				log.info("Work under construction");
				break;
			case 3:
				log.info("Work under construction");

			}

		} while (ch != 4);

	}
}
