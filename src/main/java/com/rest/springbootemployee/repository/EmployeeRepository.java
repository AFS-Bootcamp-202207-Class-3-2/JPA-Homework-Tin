package com.rest.springbootemployee.repository;

import com.rest.springbootemployee.execption.EmployeeNotFoundException;
import com.rest.springbootemployee.pojo.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    public static final int DEFAULT_ID_MINUS_1 = -1;
    private List<Employee> employees;

    public EmployeeRepository() {
        employees = new ArrayList<>();
        employees.add(new Employee(1,"AAA", 23, "male", 8000));
        employees.add(new Employee(2,"BBB", 23, "male", 8000));
        employees.add(new Employee(3,"CCC", 23, "male", 8000));
        employees.add(new Employee(4,"DDD", 23, "male", 8000));
    }

    public List<Employee> findAll() {
        return this.employees;
    }

    public Employee findById(Integer id) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(id)).findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> findByGender(String gender) {
        return employees.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public List<Employee> findByPage(int page, int pageSize) {
        return employees.stream()
                .skip((long)(page -1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Employee insert(Employee employee) {
        employee.setId(generateNewId());
        employees.add(employee);
        return employee;
    }

    private int generateNewId() {
        int maxExistingId = employees.stream()
                .mapToInt(Employee::getId)
                .max().orElse(DEFAULT_ID_MINUS_1);
        return maxExistingId + 1;
    }

    public Employee update(int id, Employee employee) {
        Employee updateEmployee = findById(id);
        if(employee.getAge() != null){
            updateEmployee.setAge(employee.getAge());
        }
        if(employee.getSalary() != null){
            updateEmployee.setSalary(employee.getSalary());
        }
        return updateEmployee;
    }

    public void delete(int id) {
        Employee employee = findById(id);
        employees.remove(employee);
    }

    public void clean() {
        employees.clear();
    }
}
