package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.controller.dto.EmployeeRequest;
import com.rest.springbootemployee.controller.dto.EmployeeResponse;
import com.rest.springbootemployee.controller.mapper.EmployeeMapper;
import com.rest.springbootemployee.repository.EmployeeRepository;
import com.rest.springbootemployee.pojo.Employee;
import com.rest.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeMapper employeeMapper;

    @GetMapping
    public List<EmployeeResponse> getEmployees(){
        return employeeService.findAll()
                .stream()
                .map(employee -> employeeMapper.toResponse(employee))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EmployeeResponse getEmployeeById(@PathVariable Integer id){
        return employeeMapper.toResponse(employeeService.findById(id));
    }

    @GetMapping(params = {"gender"})
    public List<EmployeeResponse> getEmployeeByGender(@RequestParam("gender")String gender){
        return employeeService.findByGender(gender)
                .stream()
                .map(employeeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping(params = {"page","pageSize"})
    public List<EmployeeResponse> findByPage(@RequestParam int page, @RequestParam int pageSize){
        return employeeService.findByPage(page, pageSize)
                 .stream()
                 .map(employee -> employeeMapper.toResponse(employee))
                 .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse create(@RequestBody EmployeeRequest employeeRequest){
        Employee employee = employeeService.create(employeeMapper.toEntity(employeeRequest));
        return employeeMapper.toResponse(employee);
    }

    @PutMapping("/{id}")
    public EmployeeResponse EmployeeResponse(@PathVariable int id, @RequestBody EmployeeRequest employeeRequest){
        Employee employee = employeeService.update(id, employeeMapper.toEntity(employeeRequest));
        return employeeMapper.toResponse(employee);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id){
        employeeService.delete(id);
    }
}
