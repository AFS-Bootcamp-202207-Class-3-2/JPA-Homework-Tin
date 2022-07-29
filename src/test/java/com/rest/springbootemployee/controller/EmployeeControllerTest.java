package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.pojo.Employee;
import com.rest.springbootemployee.repository.EmployeeJpaRepository;
import org.hamcrest.Matchers;
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

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class EmployeeControllerTest {

    @Autowired
    MockMvc client;

    @Autowired
    EmployeeJpaRepository employeeJpaRepository;

    @BeforeEach
    void prepareData(){
        employeeJpaRepository.deleteAll();
    }

    @Test
    void should_get_all_employee_when_perform_get_given_employees() throws Exception{
        //given
        employeeJpaRepository.save(new Employee(1, "Sally", 22, "female", 10000));

        //when
        client.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Sally"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(22))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").doesNotExist());
        //then
    }

    @Test
    void should_create_a_new_employee_when_perform_post_given_a_new_employee() throws Exception {
        //given
        String newEmployeeJson ="{\n" +
                "        \"name\": \"Lisa\",\n" +
                "        \"age\": 21,\n" +
                "        \"gender\": \"female\",\n" +
                "        \"salary\": 2000\n" +
                "    }";

        //when
        client.perform(MockMvcRequestBuilders.post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newEmployeeJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Lisa"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(21))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").doesNotExist());

        //then
        List<Employee> employees = employeeJpaRepository.findAll();
        assertThat(employees, hasSize(1));
        assertThat(employees.get(0).getName(), equalTo("Lisa"));
        assertThat(employees.get(0).getAge(), equalTo(21));
        assertThat(employees.get(0).getGender(), equalTo("female"));
        assertThat(employees.get(0).getSalary(), equalTo(2000));
    }

    @Test
    void should_update_employee_when_perform_put_given_a_new_employee() throws Exception {
        //given
        Employee employee = employeeJpaRepository.save(new Employee(0, "Lisa", 22, "female", 10000));
        String updateEmployeeJson ="{\n" +
                "        \"name\": \"Lisa\",\n" +
                "        \"age\": 66,\n" +
                "        \"gender\": \"female\",\n" +
                "        \"salary\": 2000\n" +
                "    }";

        //when
        client.perform(MockMvcRequestBuilders.put("/employees/{id}",employee.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateEmployeeJson))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Lisa"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(66))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").doesNotExist());

        //then
        List<Employee> employees = employeeJpaRepository.findAll();
        assertThat(employees, hasSize(1));
        assertThat(employees.get(0).getName(), equalTo("Lisa"));
        assertThat(employees.get(0).getAge(), equalTo(66));
        assertThat(employees.get(0).getGender(), equalTo("female"));
        assertThat(employees.get(0).getSalary(), equalTo(2000));
    }

    @Test
    void should_delete_employee_when_perform_delete_given_a_employee() throws Exception {
        //given
        Employee employee = employeeJpaRepository.save(new Employee(0, "Lisa", 22, "female", 10000));

        //when
        client.perform(MockMvcRequestBuilders.delete("/employees/{id}",employee.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        //then
        List<Employee> employees = employeeJpaRepository.findAll();
        assertThat(employees, hasSize(0));
    }

    @Test
    void should_get_a_employee_when_perform_get_given_id() throws Exception{
        //given
        Employee employee = employeeJpaRepository.save(new Employee(0, "Sally", 22, "female", 10000));

        //when
        client.perform(MockMvcRequestBuilders.get("/employees/{id}",employee.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Sally"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(22))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").doesNotExist());
    }

    @Test
    void should_get_employees_when_perform_get_given_gender() throws Exception{
        //given
        employeeJpaRepository.save(new Employee(0, "Sally", 22, "female", 10000));

        //when
        client.perform(MockMvcRequestBuilders.get("/employees")
                        .param("gender","female"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Sally"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(22))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").doesNotExist());
    }

    @Test
    void should_get_employees_when_perform_get_given_page_and_page_size() throws Exception{
        //given
        employeeJpaRepository.save(new Employee(null, "Sally", 22, "female", 10000));
        employeeJpaRepository.save(new Employee(null, "Lily", 25, "female", 16000));
        employeeJpaRepository.save(new Employee(null, "Tom", 25, "male", 16000));

        //when
        client.perform(MockMvcRequestBuilders.get("/employees")
                        .param("page","0").param("pageSize","2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name", containsInAnyOrder("Sally","Lily")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].age", containsInAnyOrder(22, 25)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].gender", everyItem(is("female"))));
    }
}
