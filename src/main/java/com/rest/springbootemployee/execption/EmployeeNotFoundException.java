package com.rest.springbootemployee.execption;

public class EmployeeNotFoundException extends RuntimeException{
    public EmployeeNotFoundException(){
        super("EmployeeNotFoundException");
    }

}
