package com.example.springTesting.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;


import com.example.springTesting.model.Employee;
import com.example.springTesting.service.EmployeeService;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)

//@SpringBootTest
//@AutoConfigureMockMvc
public class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	//@Autowired
	private EmployeeService employeeServiceMocked;

	@Test
	public void testGetEmployeeByName() throws Exception {
		Mockito.when(employeeServiceMocked.getEmployeeByName("Alex")).thenReturn(new Employee(1l, "Alex"));

		mockMvc.perform(get("/api/employees?name=Alex")).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1l))
				.andExpect(jsonPath("$.name").value("Alex"));
	}

}
