package com.rest.springbootemployee.service;

import com.rest.springbootemployee.pojo.Company;
import com.rest.springbootemployee.pojo.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company findById(int id) {
        return companyRepository.findById(id);
    }

    public List<Company> findByPage(int page, int pageSize) {
        return companyRepository.findByPage(page, pageSize);
    }

    public Company create(Company company) {
        return companyRepository.insert(company);
    }

    public Company update(int id, Company company) {
        return companyRepository.update(id, company);
    }

    public void delete(int id) {
        companyRepository.delete(id);
    }

    public List<Employee> findEmployeesById(Integer id) {
        return companyRepository.findEmployeesById(id);
    }
}
