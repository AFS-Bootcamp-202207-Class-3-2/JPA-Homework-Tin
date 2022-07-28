package com.rest.springbootemployee.service;

import com.rest.springbootemployee.pojo.Company;
import com.rest.springbootemployee.pojo.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
import com.rest.springbootemployee.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {

    @Mock
    CompanyRepository companyRepository;

    @InjectMocks
    CompanyService companyService;

    @Test
    void should_return_all_companies_when_find_all_given_employees() {
        //given
        ArrayList<Company> companies = new ArrayList<>();
        ArrayList<Employee> employees = new ArrayList<Employee>() {{
            add(new Employee(1, "Sally", 22, "female", 10000));
            add(new Employee(1, "Lily", 26, "female", 5000));
        }};
        Company ooclCompany = new Company(1, "OOCL", employees);
        companies.add(ooclCompany);
        given(companyRepository.findAll()).willReturn(companies);

        //when
        List<Company> allCompanies = companyService.findAll();

        //then
        assertEquals(1, allCompanies.size());
        assertEquals(ooclCompany, allCompanies.get(0));
    }

    @Test
    void should_a_company_when_find_by_id() {
        //given
        ArrayList<Employee> employees = new ArrayList<Employee>() {{
            add(new Employee(1, "Sally", 22, "female", 10000));
            add(new Employee(1, "Lily", 26, "female", 5000));
        }};
        Company ooclCompany = new Company(1, "OOCL", employees);
        given(companyRepository.findById(1)).willReturn(ooclCompany);

        //when
        Company company = companyService.findById(1);

        //then
        assertEquals(ooclCompany, company);
    }

    @Test
    void should_companies_when_find_by_page_and_page_size() {
        //given
        ArrayList<Company> companies = new ArrayList<>();
        ArrayList<Employee> ooclEmployees = new ArrayList<Employee>() {{
            add(new Employee(1, "Sally", 22, "female", 10000));
            add(new Employee(1, "Lily", 26, "female", 5000));
        }};
        Company ooclCompany = new Company(1, "OOCL", ooclEmployees);
        ArrayList<Employee> coscoEmployees = new ArrayList<Employee>() {{
            add(new Employee(1, "Sally", 22, "female", 10000));
            add(new Employee(1, "Lily", 26, "female", 5000));
        }};
        Company coscoCompany = new Company(1, "COSU", coscoEmployees);
        companies.add(ooclCompany);
        companies.add(coscoCompany);
        given(companyRepository.findByPage(1,1)).willReturn(Collections.singletonList(ooclCompany));

        //when
        List<Company> companiesByPage = companyService.findByPage(1, 1);

        //then
        assertEquals(ooclCompany, companiesByPage.get(0));
    }

    @Test
    void should_a_new_company_when_create_given_a_company() {
        //given
        ArrayList<Employee> ooclEmployees = new ArrayList<Employee>() {{
            add(new Employee(1, "Sally", 22, "female", 10000));
            add(new Employee(1, "Lily", 26, "female", 5000));
        }};
        Company companyToCreate = new Company(1, "OOCL", ooclEmployees);
        given(companyRepository.insert(companyToCreate)).willReturn(companyToCreate);

        //when
        Company company = companyService.create(companyToCreate);

        //then
        assertEquals(companyToCreate, company);
    }

    @Test
    void should_update_only_company_name_when_update_given_a_company() {
        //given
        ArrayList<Employee> employees = new ArrayList<Employee>() {{
            add(new Employee(1, "Sally", 22, "female", 10000));
            add(new Employee(1, "Lily", 26, "female", 5000));
        }};
        Company companyToUpdate = new Company(1, "OOCL", employees);

        Company updatedCompany = new Company(1,"COSU", employees);
        given(companyRepository.update(1,companyToUpdate)).willReturn(updatedCompany);

        //when
        Company company = companyService.update(1, companyToUpdate);

        //then
        assertEquals(updatedCompany, company);
    }

    @Test
    void should_delete_company_when_delete_given_id() {
        //given

        //when
        companyService.delete(1);

        //then
        verify(companyRepository,times(1)).delete(1);
    }

    @Test
    void should_get_employees_when_find_by_id() {
        //given
        ArrayList<Employee> employees = new ArrayList<Employee>() {{
            add(new Employee(1, "Sally", 22, "female", 10000));
            add(new Employee(1, "Lily", 26, "female", 5000));
        }};
        Company company = new Company(1, "OOCL", employees);
        given(companyRepository.findEmployeesById(1)).willReturn(employees);

        //when
        List<Employee> employeesById = companyService.findEmployeesById(1);

        //then
        assertEquals(employeesById, employees);
    }
}
