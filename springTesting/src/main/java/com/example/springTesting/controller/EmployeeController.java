package com.example.springTesting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springTesting.model.Employee;
import com.example.springTesting.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping(value = "/employees")
	public ResponseEntity<Employee> getEmployeeByName(@RequestParam String name) {
		return new ResponseEntity<>(employeeService.getEmployeeByName(name), HttpStatus.OK);
	}
	
}
