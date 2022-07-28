package com.rest.springbootemployee.service;


import com.rest.springbootemployee.pojo.Employee;
import com.rest.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.rest.springbootemployee.service.EmployeeService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeService employeeService;

    @Test
    void should_return_all_employees_when_find_all_given_employees() {
        //given
        ArrayList<Employee> preparedEmployees = new ArrayList<>();
        Employee firstEmployee = new Employee(1, "Susan", 23, "female", 10000);
        Employee secondEmployee = new Employee(1, "Mathew", 25, "male", 12000);
        preparedEmployees.add(firstEmployee);
        preparedEmployees.add(secondEmployee);
        given(employeeRepository.findAll()).willReturn(preparedEmployees);

        //when
        List<Employee> employees = employeeService.findAll();

        //then
        assertEquals(2, employees.size());
        assertEquals(firstEmployee, employees.get(0));
    }

    @Test
    void should_update_only_age_and_salary_when_update_given_employee() {
        //given
        Employee employeeToUpdate = new Employee(1, "Susan", 23, "female", 10000);
        Employee employeeInUpdateRequest = new Employee(2, "Mathew", 25, "male", 12000);
        Employee updatedEmployee = new Employee(1, "Susan", 25, "female", 12000);
        given(employeeRepository.update(1,employeeInUpdateRequest)).willReturn(updatedEmployee);

        //when
        Employee employee = employeeService.update(1, employeeInUpdateRequest);

        //then
        assertEquals(employee, updatedEmployee);
    }

    @Test
    void should_a_new_employee_when_create_given_employee() {
        //given
        Employee employeeToCreate = new Employee(1, "Susan", 23, "female", 10000);
        given(employeeRepository.insert(employeeToCreate)).willReturn(employeeToCreate);

        //when
        Employee employee = employeeService.create(employeeToCreate);

        //then
        assertEquals(employee, employeeToCreate);
    }

    @Test
    void should_a_employee_when_delete_given_id() {
        //given

        //when
        employeeService.delete(1);

        //then
        verify(employeeRepository,times(1)).delete(1);
    }


    @Test
    void should_get_a_employee_when_find_given_id() {
        //given
        Employee employee = new Employee(1, "Susan", 23, "female", 10000);
        given(employeeRepository.findById(1)).willReturn(employee);

        //when
        Employee employeeById = employeeService.findById(1);

        //then
        assertEquals(employeeById, employee);
    }

    @Test
    void should_get_employees_when_find_given_gender() {
        //given
        List<Employee> employees = new ArrayList<>();
        Employee FirstEmployee = new Employee(1, "Susan", 23, "female", 10000);
        Employee SecondEmployee = new Employee(2, "Mathew", 25, "female", 8000);
        employees.add(FirstEmployee);
        employees.add(SecondEmployee);
        given(employeeRepository.findByGender("female")).willReturn(employees);

        //when
        List<Employee> employeesByGender = employeeService.findByGender("female");

        //then
        assertEquals(employeesByGender.get(0), FirstEmployee);
        assertEquals(employeesByGender.get(1), SecondEmployee);
    }

    @Test
    void should_get_employees_when_find_given_page_and_pageSize() {
        //given
        List<Employee> employees = new ArrayList<>();
        Employee FirstEmployee = new Employee(1, "Susan", 23, "female", 10000);
        Employee SecondEmployee = new Employee(2, "Mathew", 25, "female", 8000);
        employees.add(FirstEmployee);
        employees.add(SecondEmployee);
        given(employeeRepository.findByPage(1,2)).willReturn(employees);

        //when
        List<Employee> employeeByPage = employeeService.findByPage(1, 2);

        //then
        assertEquals(employeeByPage.get(0), FirstEmployee);
        assertEquals(employeeByPage.get(1), SecondEmployee);
    }
}
