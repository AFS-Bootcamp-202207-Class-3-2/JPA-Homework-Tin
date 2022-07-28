package com.rest.springbootemployee.service;

import com.rest.springbootemployee.pojo.Company;
import com.rest.springbootemployee.pojo.Employee;
import com.rest.springbootemployee.repository.CompanyJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {

    @Mock
    CompanyJpaRepository companyJpaRepository;

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
        given(companyJpaRepository.findAll()).willReturn(companies);

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
        Company company = new Company(1, "OOCL", employees);
        given(companyJpaRepository.findById(1)).willReturn(Optional.of(company));

        //when
        Company companyById = companyService.findById(1);

        //then
        assertEquals(companyById, company);
    }

    @Test
    void should_companies_when_find_by_page_and_page_size() {
        //given
        ArrayList<Company> companies = new ArrayList<>();
        ArrayList<Employee> ooclEmployees = new ArrayList<Employee>() {{
            add(new Employee(null, "Sally", 22, "female", 10000));
            add(new Employee(null, "Lily", 26, "female", 5000));
        }};
        Company firstCompany = new Company(1, "Candy", ooclEmployees);
        ArrayList<Employee> coscoEmployees = new ArrayList<Employee>() {{
            add(new Employee(null, "Sally", 22, "female", 10000));
            add(new Employee(null, "Lily", 26, "female", 5000));
        }};
        Company secondCompany = new Company(1, "Apple", coscoEmployees);
        companies.add(firstCompany);
        companies.add(secondCompany);
        PageImpl<Company> companyPage = new PageImpl<>(companies);
        given(companyJpaRepository.findAll(PageRequest.of(0,1))).willReturn(companyPage);

        //when
        List<Company> companiesByPage = companyService.findByPage(0, 1);

        //then
        assertEquals(firstCompany, companiesByPage.get(0));
    }

    @Test
    void should_a_new_company_when_create_given_a_company() {
        //given
        ArrayList<Employee> ooclEmployees = new ArrayList<Employee>() {{
            add(new Employee(1, "Sally", 22, "female", 10000));
            add(new Employee(1, "Lily", 26, "female", 5000));
        }};
        Company companyToCreate = new Company(1, "Apple", ooclEmployees);
        given(companyJpaRepository.save(companyToCreate)).willReturn(companyToCreate);

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
            add(new Employee(2, "Lily", 26, "female", 5000));
        }};
        Company companyToUpdate = new Company(1, "Apple", employees);

        Company updatedCompany = new Company(1,"Banana", employees);
        given(companyJpaRepository.findById(1)).willReturn(Optional.of(companyToUpdate));
        given(companyJpaRepository.save(companyToUpdate)).willReturn(updatedCompany);

        //when
        Company company = companyService.update(1, companyToUpdate);

        //then
        assertEquals(updatedCompany, company);
    }

    @Test
    void should_delete_company_when_delete_given_id() {
        //given
        given(companyJpaRepository.existsById(1)).willReturn(true);
        //when
        companyService.delete(1);

        //then
        verify(companyJpaRepository,times(1)).deleteById(1);
    }

    @Test
    void should_get_employees_when_find_by_id() {
        //given
        ArrayList<Employee> employees = new ArrayList<Employee>() {{
            add(new Employee(1, "Sally", 22, "female", 10000));
            add(new Employee(1, "Lily", 26, "female", 5000));
        }};
        Company company = new Company(1, "Apple", employees);
        given(companyJpaRepository.findById(1)).willReturn(Optional.of(company));

        //when
        List<Employee> employeesById = companyService.findEmployeesById(1);

        //then
        assertEquals(employeesById, employees);
    }
}
