package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.pojo.Company;
import com.rest.springbootemployee.pojo.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
class CompanyControllerTest {

    @Autowired
    MockMvc client;
    @Autowired
    private CompanyRepository companyRepository;

    @BeforeEach
    void clearEmployeeInRepository(){
        companyRepository.clean();
    }

    @Test
    void should_get_all_companies_when_perform_get_given_companies() throws Exception{
        //given
        ArrayList<Employee> employees = new ArrayList<Employee>() {{
            add(new Employee(1, "Sally", 22, "female", 10000));
            add(new Employee(1, "Lily", 26, "female", 5000));
        }};
        companyRepository.insert(new Company(1, "OOCL", employees));

        //when
        client.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyName").value("OOCL"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeList[*].name", containsInAnyOrder("Sally","Lily")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeList[*].age", containsInAnyOrder(22, 26)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeList[*].gender", everyItem(is("female"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeList[*].salary", containsInAnyOrder(10000, 5000)));

        //then
    }

    @Test
    void should_get_a_company_when_perform_get_given_id() throws Exception{
        //given
        ArrayList<Employee> employees = new ArrayList<Employee>() {{
            add(new Employee(1, "Sally", 22, "female", 10000));
            add(new Employee(1, "Lily", 26, "female", 5000));
        }};
        companyRepository.insert(new Company(1, "OOCL", employees));

        //when
        client.perform(MockMvcRequestBuilders.get("/companies/{id}",0))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("OOCL"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeList[*].name", containsInAnyOrder("Sally","Lily")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeList[*].age", containsInAnyOrder(22, 26)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeList[*].gender", everyItem(is("female"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeList[*].salary", containsInAnyOrder(10000, 5000)));

        //then
    }

    @Test
    void should_get_employee_list_when_perform_get_given_company_id() throws Exception{
        //given
        ArrayList<Employee> employees = new ArrayList<Employee>() {{
            add(new Employee(1, "Sally", 22, "female", 10000));
            add(new Employee(1, "Lily", 26, "female", 5000));
        }};
        companyRepository.insert(new Company(1, "OOCL", employees));

        //when
        client.perform(MockMvcRequestBuilders.get("/companies/{id}/employees", 0))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name", containsInAnyOrder("Sally","Lily")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].age", containsInAnyOrder(22, 26)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].gender", everyItem(is("female"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].salary", containsInAnyOrder(10000, 5000)));

        //then
    }

    @Test
    void should_get_companies_when_perform_get_given_page_and_page_size() throws Exception{
        //given
        ArrayList<Employee> firstCompanyEmployees = new ArrayList<Employee>() {{
            add(new Employee(1, "Sally", 22, "female", 10000));
            add(new Employee(1, "Lily", 26, "female", 5000));
        }};
        ArrayList<Employee> secondCompanyEmployees = new ArrayList<Employee>() {{
            add(new Employee(1, "Tom", 25, "male", 6000));
            add(new Employee(1, "Tony", 25, "male", 6000));
        }};
        companyRepository.insert(new Company(1, "OOCL", firstCompanyEmployees));
        companyRepository.insert(new Company(1, "COSU", secondCompanyEmployees));

        //when
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.put("page", Collections.singletonList("1"));
        paramsMap.put("pageSize", Collections.singletonList("1"));
        client.perform(MockMvcRequestBuilders.get("/companies")
        .params(paramsMap))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyName").value("OOCL"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeList[*].name", containsInAnyOrder("Sally","Lily")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeList[*].age", containsInAnyOrder(22, 26)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeList[*].gender", everyItem(is("female"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeList[*].salary", containsInAnyOrder(10000, 5000)));

        //then
    }

    @Test
    void should_create_a_new_company_when_perform_post_given_a_company() throws Exception{
        //given
        String newCompanyJson = "{\n" +
                "    \"id\": 1,\n" +
                "    \"companyName\": \"OOCL\",\n" +
                "    \"employeeList\": [\n" +
                "        {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"Tom\",\n" +
                "            \"age\": 23,\n" +
                "            \"gender\": \"male\",\n" +
                "            \"salary\": 8000\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 2,\n" +
                "            \"name\": \"Sally\",\n" +
                "            \"age\": 24,\n" +
                "            \"gender\": \"male\",\n" +
                "            \"salary\": 6000\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        //when
        client.perform(MockMvcRequestBuilders.post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newCompanyJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("OOCL"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeList[*].name", containsInAnyOrder("Tom","Sally")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeList[*].age", containsInAnyOrder(23, 24)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeList[*].gender", everyItem(is("male"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeList[*].salary", containsInAnyOrder(8000, 6000)));

        //then
        List<Company> companies = companyRepository.findAll();
        assertThat(companies, hasSize(1));
        assertThat(companies.get(0).getCompanyName(), equalTo("OOCL"));
        assertThat(companies.get(0).getEmployeeList().get(0).getName(), equalTo("Tom"));
        assertThat(companies.get(0).getEmployeeList().get(0).getAge(), equalTo(23));
        assertThat(companies.get(0).getEmployeeList().get(0).getGender(), equalTo("male"));
        assertThat(companies.get(0).getEmployeeList().get(0).getSalary(), equalTo(8000));
        assertThat(companies.get(0).getEmployeeList().get(1).getName(), equalTo("Sally"));
        assertThat(companies.get(0).getEmployeeList().get(1).getAge(), equalTo(24));
        assertThat(companies.get(0).getEmployeeList().get(1).getGender(), equalTo("male"));
        assertThat(companies.get(0).getEmployeeList().get(1).getSalary(), equalTo(6000));
    }

    @Test
    void should_update_company_when_perform_put_given_a_company() throws Exception{
        ArrayList<Employee> employees = new ArrayList<Employee>() {{
            add(new Employee(1, "Tom", 23, "male", 8000));
            add(new Employee(2, "Sally", 24, "male", 6000));
        }};
        companyRepository.insert(new Company(1, "OOCL", employees));

        //given
        String updatedCompanyJson = "{\n" +
                "    \"id\": 1,\n" +
                "    \"companyName\": \"COSCO\",\n" +
                "    \"employeeList\": [\n" +
                "        {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"Tom\",\n" +
                "            \"age\": 23,\n" +
                "            \"gender\": \"male\",\n" +
                "            \"salary\": 8000\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 2,\n" +
                "            \"name\": \"Sally\",\n" +
                "            \"age\": 24,\n" +
                "            \"gender\": \"male\",\n" +
                "            \"salary\": 6000\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        //when
        client.perform(MockMvcRequestBuilders.put("/companies/{id}",0)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedCompanyJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("COSCO"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeList[*].name", containsInAnyOrder("Tom","Sally")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeList[*].age", containsInAnyOrder(23, 24)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeList[*].gender", everyItem(is("male"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeList[*].salary", containsInAnyOrder(8000, 6000)));

        //then
        List<Company> companies = companyRepository.findAll();
        assertThat(companies, hasSize(1));
        assertThat(companies.get(0).getCompanyName(), equalTo("COSCO"));
        assertThat(companies.get(0).getEmployeeList().get(0).getName(), equalTo("Tom"));
        assertThat(companies.get(0).getEmployeeList().get(0).getAge(), equalTo(23));
        assertThat(companies.get(0).getEmployeeList().get(0).getGender(), equalTo("male"));
        assertThat(companies.get(0).getEmployeeList().get(0).getSalary(), equalTo(8000));
        assertThat(companies.get(0).getEmployeeList().get(1).getName(), equalTo("Sally"));
        assertThat(companies.get(0).getEmployeeList().get(1).getAge(), equalTo(24));
        assertThat(companies.get(0).getEmployeeList().get(1).getGender(), equalTo("male"));
        assertThat(companies.get(0).getEmployeeList().get(1).getSalary(), equalTo(6000));
    }

    @Test
    void should_delete_employee_when_perform_delete_given_a_employee() throws Exception {
        //given
        ArrayList<Employee> employees = new ArrayList<Employee>() {{
            add(new Employee(1, "Tom", 23, "male", 8000));
            add(new Employee(2, "Sally", 24, "male", 6000));
        }};
        companyRepository.insert(new Company(1, "OOCL", employees));

        //when
        client.perform(MockMvcRequestBuilders.delete("/companies/{id}",0))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        //then
        List<Company> companies = companyRepository.findAll();
        assertThat(companies, hasSize(0));
    }
}
