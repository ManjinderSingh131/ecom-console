package com.app.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.app.exception.BusinessException;
import com.app.service.CustomerService;
import com.app.service.impl.CustomerServiceImpl;

public class TestCustomerServices {
	CustomerService customerService = new CustomerServiceImpl();

	@Test
	void customerLogin() throws BusinessException {
		assertEquals(true, customerService.customerLogin("smanjinder576@gmail.com", "test123@").isLoggedIn());
		assertEquals(true, customerService.customerLogin("smanjinder576@gmail.co", "test123@").isLoggedIn());
	}

}
