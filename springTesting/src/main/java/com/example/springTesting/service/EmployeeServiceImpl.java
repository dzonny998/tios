package com.example.springTesting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springTesting.model.Employee;
import com.example.springTesting.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public Employee getEmployeeByName(String name) {
		return employeeRepository.findByName(name);
	}
}
