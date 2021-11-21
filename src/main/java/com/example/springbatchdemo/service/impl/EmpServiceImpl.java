package com.example.springbatchdemo.service.impl;

import com.example.springbatchdemo.domain.Employee;
import com.example.springbatchdemo.repository.EmpRepo;
import com.example.springbatchdemo.service.EmpService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmpServiceImpl implements EmpService {
    private final EmpRepo empRepo;

    @Override
    public Employee saveEmp(Employee employee) {
        return this.empRepo.saveAndFlush(employee);
    }

    @Override
    public List<Employee> saveListOfEmployees(Iterable<Employee> employees) {
        return this.empRepo.saveAllAndFlush(employees);
    }

    @Override
    public Employee getEmployee(Long id) {
        return this.empRepo.getById(id);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return this.empRepo.findAll();
    }
}
