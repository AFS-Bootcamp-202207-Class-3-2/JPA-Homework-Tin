package com.rest.springbootemployee.service;

import com.rest.springbootemployee.pojo.Employee;
import com.rest.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> findAll() {
        List<Employee> employees = employeeRepository.findAll();
        return employees;
    }

    public Employee update(int id, Employee employee) {
        Employee updatedEmployee = employeeRepository.update(id, employee);
        return updatedEmployee;
    }

    public Employee create(Employee employee) {
        return employeeRepository.insert(employee);
    }

    public void delete(int id) {
        employeeRepository.delete(id);
    }

    public Employee findById(int id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> findByGender(String gender) {
        return employeeRepository.findByGender(gender);
    }

    public List<Employee> findByPage(int page, int pageSize) {
        return employeeRepository.findByPage(page, pageSize);
    }
}
