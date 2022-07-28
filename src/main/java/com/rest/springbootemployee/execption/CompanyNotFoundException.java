package com.rest.springbootemployee.execption;


public class CompanyNotFoundException extends RuntimeException{

    public CompanyNotFoundException() {
        super("CompanyNotFoundException");
    }
}
