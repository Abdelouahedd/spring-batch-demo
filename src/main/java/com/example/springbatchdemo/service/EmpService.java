package com.example.springbatchdemo.service;

import com.example.springbatchdemo.domain.Employee;

import java.util.List;

public interface EmpService {
    Employee saveEmp(Employee employee);

    List<Employee> saveListOfEmployees(Iterable<Employee> employees);

    Employee getEmployee(Long id);

    List<Employee> getAllEmployees();
}
