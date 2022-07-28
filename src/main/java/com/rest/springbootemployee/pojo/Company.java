package com.rest.springbootemployee.pojo;


import javax.persistence.*;
import java.util.List;
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String companyName;
    @OneToMany
    @JoinColumn(name = "companyId")
    private List<Employee> employeeList;

    public Company() {
    }

    public Company(Integer id, String companyName, List<Employee> employeeList) {
        this.id = id;
        this.companyName = companyName;
        this.employeeList = employeeList;
    }

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

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }
}
