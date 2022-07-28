package com.rest.springbootemployee.service;

import com.rest.springbootemployee.execption.CompanyNotFoundException;
import com.rest.springbootemployee.pojo.Company;
import com.rest.springbootemployee.pojo.Employee;
import com.rest.springbootemployee.repository.CompanyJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyJpaRepository companyJpaRepository;

    public List<Company> findAll() {
        return companyJpaRepository.findAll();
    }

    public Company findById(int id) {
        return companyJpaRepository.findById(id).orElseThrow(CompanyNotFoundException::new);
    }

    public List<Company> findByPage(int pageNumber, int pageSize) {
        return companyJpaRepository.findAll(PageRequest.of(pageNumber, pageSize)).toList();
    }

    public Company create(Company company) {
        return companyJpaRepository.save(company);
    }

    public Company update(int id, Company company) {
        Company updatedCompany = findById(id);
        if (company.getCompanyName() != null) {
            updatedCompany.setCompanyName(company.getCompanyName());
        }
        return companyJpaRepository.save(company);
    }

    public void delete(int id) {
        companyJpaRepository.deleteById(id);
    }

    public List<Employee> findEmployeesById(Integer id) {
        Company company = findById(id);
        return company.getEmployeeList();
    }
}
