package com.fluxin.flux_in.repository;

import com.fluxin.flux_in.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
