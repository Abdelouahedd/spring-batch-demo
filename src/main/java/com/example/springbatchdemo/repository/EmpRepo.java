package com.example.springbatchdemo.repository;

import com.example.springbatchdemo.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpRepo extends JpaRepository<Employee, Long> {
}
