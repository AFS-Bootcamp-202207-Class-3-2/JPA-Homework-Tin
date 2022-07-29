package com.rest.springbootemployee.controller.dto;

import org.springframework.stereotype.Component;

@Component
public class EmployeeRequest {

    private String name;
    private Integer age;
    private String gender;
    private Integer salary;
    private Integer companyId;
}
