package com.rest.springbootemployee.service;

import com.rest.springbootemployee.execption.EmployeeNotFoundException;
import com.rest.springbootemployee.pojo.Employee;
import com.rest.springbootemployee.repository.EmployeeJpaRepository;
import com.rest.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeJpaRepository employeeJpaRepository;

    public List<Employee> findAll() {
        List<Employee> employees = employeeJpaRepository.findAll();
        return employees;
    }

    public Employee update(int id, Employee toUpdate) {
        Employee employee = findById(id);
        if(toUpdate.getAge() != null){
            employee.setAge(toUpdate.getAge());
        }
        if(toUpdate.getSalary() != null){
            employee.setSalary(toUpdate.getSalary());
        }
        Employee saveEmployee = employeeJpaRepository.save(employee);
        return saveEmployee;
    }

    public Employee create(Employee employee) {
        return employeeJpaRepository.save(employee);
    }

    public void delete(int id) {
        boolean exists = employeeJpaRepository.existsById(id);
        if(!exists){
            throw new EmployeeNotFoundException();
        }
        employeeJpaRepository.deleteById(id);
    }

    public Employee findById(int id) {
        return employeeJpaRepository.findById(id)
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> findByGender(String gender) {
        return employeeJpaRepository.findAllByGender(gender);
    }

    public List<Employee> findByPage(int pageNumber, int pageSize) {
        return employeeJpaRepository.findAll(PageRequest.of(pageNumber, pageSize)).toList();
    }
}
