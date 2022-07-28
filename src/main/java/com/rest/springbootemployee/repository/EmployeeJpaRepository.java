package com.rest.springbootemployee.repository;

import com.rest.springbootemployee.pojo.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeJpaRepository extends JpaRepository<Employee, Integer> {
}
