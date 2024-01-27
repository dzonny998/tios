package com.example.springTesting.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.springTesting.model.Employee;
import com.example.springTesting.repository.EmployeeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class EmployeeServiceTest {

	@Autowired
	private EmployeeService employeeService;

	@MockBean
	private EmployeeRepository employeeRepositoryMocked;

	@Before
	public void setUp() {
		Employee alex = new Employee("alex");
		Mockito.when(employeeRepositoryMocked.findByName(alex.getName())).thenReturn(alex);
	}

	@Test
	public void whenValidName_thenEmployeeShouldBeFound() {
		String name = "alex";
		Employee found = employeeService.getEmployeeByName(name);

		assertEquals(name, found.getName());
	}
}
