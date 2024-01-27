package com.example.springTesting.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.springTesting.model.Employee;
import com.example.springTesting.service.EmployeeService;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest2 {

	@Autowired
    private TestRestTemplate restTemplate;
	
	@MockBean
	private EmployeeService emlopoyeeServiceMocked;
	
	@Before
	public void setUp() {
		Mockito.when(emlopoyeeServiceMocked.getEmployeeByName("Alex")).thenReturn(new Employee(1l, "Mocked"));
	}
	
	@Test
	public void testGetEmployeeByName() {
		ResponseEntity<Employee> responseEntity =
	            restTemplate.getForEntity("/api/employees?name=Alex", Employee.class);
		
		Employee employee = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(employee);
        assertEquals("Mocked", employee.getName());
	}
}
