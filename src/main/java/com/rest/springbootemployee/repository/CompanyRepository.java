package com.rest.springbootemployee.repository;

import com.rest.springbootemployee.execption.CompanyNotFoundException;
import com.rest.springbootemployee.pojo.Company;
import com.rest.springbootemployee.pojo.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {

    private static final int DEFAULT_ID_MINUS_1 = -1;
    private List<Company> companies;

    public CompanyRepository() {
        companies = new ArrayList<>();
        companies.add(new Company(1, "OOCL", new ArrayList<Employee>(){{
            add(new Employee(1,"AAA", 23, "male", 8000));
            add(new Employee(2,"BBB", 24, "female", 6000));
        }}));
        companies.add(new Company(2, "COSU", new ArrayList<Employee>(){{
            add(new Employee(1,"CCC", 25, "male", 8000));
            add(new Employee(2,"DDD", 26, "female", 5000));
        }}));
    }

    public List<Company> findAll() {
        return this.companies;
    }


    public Company findById(Integer id) {
        return companies.stream()
                .filter(company -> company.getId().equals(id))
                .findFirst()
                .orElseThrow(CompanyNotFoundException::new);
    }

    public List<Employee> findEmployeesById(Integer id) {
        Company company = findById(id);
        return company.getEmployeeList();
    }

    public List<Company> findByPage(int page, int pageSize) {
        return companies.stream()
                .skip((long)(page -1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Company insert(Company company) {
        company.setId(generateNewId());
        companies.add(company);
        return company;
    }

    private int generateNewId() {
        int maxExistingId = companies.stream()
                .mapToInt(Company::getId)
                .max().orElse(DEFAULT_ID_MINUS_1);
        return maxExistingId + 1;
    }

    public Company update(Integer id, Company company) {
        Company updatedCompany = findById(id);
        if (company.getCompanyName() != null) {
            updatedCompany.setCompanyName(company.getCompanyName());
        }
        return updatedCompany;
    }

    public void delete(Integer id) {
        Company company = findById(id);
        companies.remove(company);
    }

    public void clean() {
        companies.clear();
    }
}
