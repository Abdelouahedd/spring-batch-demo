package com.example.springbatchdemo.web;

import com.example.springbatchdemo.service.EmpService;
import com.example.springbatchdemo.domain.Employee;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class EmployeeController {
    private final EmpService empService;


    @PostMapping("/employee")
    public Employee saveEmp(Employee employee) {
        return this.empService.saveEmp(employee);
    }

    @PostMapping("/employees")
    public List<Employee> saveListOfEmployees(Iterable<Employee> employees) {
        return this.empService.saveListOfEmployees(employees);
    }

    @GetMapping("/employee/{id}")
    public Employee getEmployee(@PathVariable Long id) {
        return this.empService.getEmployee(id);
    }

    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return this.empService.getAllEmployees();
    }

}
