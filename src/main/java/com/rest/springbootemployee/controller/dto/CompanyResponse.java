package com.rest.springbootemployee.controller.dto;


import com.rest.springbootemployee.pojo.Employee;

import java.util.List;

public class CompanyResponse {

    private Integer id;

    private String companyName;

    private List<EmployeeResponse> employeeList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<EmployeeResponse> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<EmployeeResponse> employeeList) {
        this.employeeList = employeeList;
    }
}
