package com.example.springbatchdemo.web;

import com.example.springbatchdemo.domain.Employee;
import com.example.springbatchdemo.service.EmpService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class EmployeeController {
    private final EmpService empService;
    private final JobLauncher jobLauncher;
    private final Job job;

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

    @GetMapping("/employees/job")
    @SneakyThrows
    public void runJob() {
        Map<String, JobParameter> params = new HashMap<>();
        params.put("saveDate", new JobParameter(new Date()));
        jobLauncher.run(job, new JobParameters(params));
    }

}
