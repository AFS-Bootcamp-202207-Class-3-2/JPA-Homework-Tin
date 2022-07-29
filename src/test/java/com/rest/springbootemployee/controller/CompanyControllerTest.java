package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.pojo.Company;
import com.rest.springbootemployee.pojo.Employee;
import com.rest.springbootemployee.repository.CompanyJpaRepository;
import com.rest.springbootemployee.repository.EmployeeJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
class CompanyControllerTest {

    @Autowired
    MockMvc client;
    @Autowired
    private CompanyJpaRepository companyJpaRepository;

    @Autowired
    private EmployeeJpaRepository employeeJpaRepository;

    private Company preparedCompany;

    @BeforeEach
    void prepareData(){
        companyJpaRepository.deleteAll();
        Company company = new Company();
        company.setCompanyName("ABC");
        preparedCompany = companyJpaRepository.save(company);
        companyJpaRepository.flush();
    }

    Employee employeeSally(){
        Employee employee = new Employee(1, "Sally", 22, "Female", 10000, preparedCompany.getId());
        return employeeJpaRepository.save(employee);
    }

    Employee employeeLily(){
        Employee employee = new Employee(2, "Lily", 26, "Female", 5000, preparedCompany.getId());
        return employeeJpaRepository.save(employee);
    }

    Employee employeeTom(){
        Employee employee = new Employee(3, "Tom", 22, "Male", 8000, preparedCompany.getId());
        return employeeJpaRepository.save(employee);
    }
    @Test
    void should_get_all_companies_when_perform_get_given_companies() throws Exception{
        //given
        employeeSally();
        employeeLily();

        //when
        client.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyName").value("ABC"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeList[*].name", containsInAnyOrder("Sally","Lily")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeList[*].age", containsInAnyOrder(22, 26)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeList[*].gender", everyItem(is("Female"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeList[*].salary", containsInAnyOrder(10000, 5000)));

        //then
    }

    @Test
    void should_get_a_company_when_perform_get_given_id() throws Exception{
        //given
        employeeSally();
        employeeLily();

        //when
        client.perform(MockMvcRequestBuilders.get("/companies/{id}",preparedCompany.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("ABC"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeList[*].name", containsInAnyOrder("Sally","Lily")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeList[*].age", containsInAnyOrder(22, 26)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeList[*].gender", everyItem(is("Female"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeList[*].salary", containsInAnyOrder(10000, 5000)));

        //then
    }

    @Test
    void should_get_employee_list_when_perform_get_given_company_id() throws Exception{
        //given
        employeeSally();
        employeeLily();

        //when
        client.perform(MockMvcRequestBuilders.get("/companies/{id}/employees", preparedCompany.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name", containsInAnyOrder("Sally","Lily")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].age", containsInAnyOrder(22, 26)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].gender", everyItem(is("Female"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].salary", containsInAnyOrder(10000, 5000)));

        //then
    }

    @Test
    void should_get_companies_when_perform_get_given_page_and_page_size() throws Exception{
        //given
        employeeSally();
        employeeLily();

        //when
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.put("page", Collections.singletonList("0"));
        paramsMap.put("pageSize", Collections.singletonList("1"));
        client.perform(MockMvcRequestBuilders.get("/companies")
        .params(paramsMap))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyName").value("ABC"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeList[*].name", containsInAnyOrder("Sally","Lily")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeList[*].age", containsInAnyOrder(22, 26)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeList[*].gender", everyItem(is("Female"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeList[*].salary", containsInAnyOrder(10000, 5000)));

        //then
    }

    @Test
    void should_create_a_new_company_when_perform_post_given_a_company() throws Exception{
        //given
        String newCompanyJson = "{\n" +
                "    \"id\": null,\n" +
                "    \"companyName\": \"ABC\"" +
                "}";

        //when
        client.perform(MockMvcRequestBuilders.post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newCompanyJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("ABC"));


        //then
        List<Company> companies = companyJpaRepository.findAll();
        assertThat(companies, hasSize(2));
        assertThat(companies.get(1).getCompanyName(), equalTo("ABC"));
    }

    @Test
    void should_update_company_when_perform_put_given_a_company() throws Exception{
        //given
        String updatedCompanyJson = "{\n" +
                "    \"companyName\": \"DEF\"" +
                "}";

        //when
        client.perform(MockMvcRequestBuilders.put("/companies/{id}",preparedCompany.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedCompanyJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("DEF"));

        //then

        //then
        List<Company> companies = companyJpaRepository.findAll();
        assertThat(companies, hasSize(1));
        assertThat(companies.get(0).getCompanyName(), equalTo("DEF"));
    }

    @Test
    void should_delete_employee_when_perform_delete_given_a_employee() throws Exception {
        //given
        employeeTom();

        //when
        client.perform(MockMvcRequestBuilders.delete("/companies/{id}",preparedCompany.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        //then
        List<Company> companies = companyJpaRepository.findAll();
        assertThat(companies, hasSize(0));
    }
}
