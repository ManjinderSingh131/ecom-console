package com.app.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.app.exception.BusinessException;
import com.app.service.EmployeeService;
import com.app.service.impl.EmployeeServiceImpl;

class TestEmployeeServices {
	EmployeeService employeeService = new EmployeeServiceImpl();

	@Test
	void employeeLoginCheck() throws BusinessException {
		assertEquals(false, employeeService.login("manjinder@mail.com", "pass123"), "Valid employee");
	}

	@Test
	void addProduct() throws BusinessException {
		assertEquals(true,
				employeeService.addNewProduct("New product test", 300.00, "test", "test", "test description", 4.4));

	}

}
