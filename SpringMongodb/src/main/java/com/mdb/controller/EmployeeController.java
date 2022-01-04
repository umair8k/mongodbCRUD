package com.mdb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mdb.model.Employee;
import com.mdb.repository.EmployeeRepository;
import com.mdb.service.SequenceGeneratorService;

@RestController
@RequestMapping("/api")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
	@GetMapping("/employees")
	public List<Employee> getAllEmployee(){
		return employeeRepository.findAll();
	}
	
	@GetMapping("/employee/{id}")
	public ResponseEntity<Employee> findEmployeeById(@PathVariable(value="id") Long EmployeeId){
		Employee employee= employeeRepository.findById(EmployeeId).orElseThrow();
		return ResponseEntity.ok().body(employee);
		
	}
	
	@PostMapping("/employee")
	public Employee createEmployee(@RequestBody Employee employee) {
		employee.setId(sequenceGeneratorService.generateSequence(Employee.SEQUENCE_NAME));
		return employeeRepository.save(employee);
	}
	
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmpolyee(@PathVariable(value="id") Long employeeId, @RequestBody Employee employeeDetails){
		Employee employee=employeeRepository.findById(employeeId).orElseThrow();
		employee.setEmailId(employeeDetails.getEmailId());
		employee.setFirtName(employeeDetails.getFirtName());
		employee.setLastName(employeeDetails.getLastName());
		
		Employee updateEmployee=employeeRepository.save(employee);
		return ResponseEntity.ok(updateEmployee);
	}
	
	@DeleteMapping("/employee/{id}")
	public Map<String ,Boolean> deleteEmployee(@PathVariable(value="id")Long employeeId){
		Employee employee=employeeRepository.findById(employeeId).orElseThrow();
		
		employeeRepository.delete(employee);
		Map<String,Boolean> response=new HashMap<>();
		response.put("deleted",Boolean.TRUE);
		return response;
	}

}
