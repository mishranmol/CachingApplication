package com.Cache.cachingApp.services.impl;

import com.Cache.cachingApp.dto.EmployeeDto;
import com.Cache.cachingApp.entities.Employee;
import com.Cache.cachingApp.exceptions.ResourceNotFoundException;
import com.Cache.cachingApp.repositories.EmployeeRepository;
import com.Cache.cachingApp.services.EmployeeService;
import com.Cache.cachingApp.services.SalaryAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final SalaryAccountService salaryAccountService;
    private final ModelMapper modelMapper;
    private final String CACHE_NAME = "employees";


    @Override
//Make sure this @Cacheable is coming from "import org.springframework.cache.annotation.Cacheable;" and not from Jakarata.persistence
    // String CACHE_NAME = "employees";
    @Cacheable(cacheNames = CACHE_NAME, key = "#id")
    public EmployeeDto getEmployeeById(Long id) {
        log.info("Fetching employee with id: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Employee not found with id: {}", id);
                    return new ResourceNotFoundException("Employee not found with id: " + id);
                });
        log.info("Successfully fetched employee with id: {}", id);
        return modelMapper.map(employee, EmployeeDto.class);
    }

    @Override
    // String CACHE_NAME = "employees";
    // When we are creating an employee so we don't know the id for that employee so to define key, As this below method will
    // return something so whatever this method will return it will put it inside a reserved keyword="result" so from that
    // result we will be able to get the id like "result.id".
    @CachePut(cacheNames = CACHE_NAME, key = "#result.id")
    @Transactional
    public EmployeeDto createNewEmployee(EmployeeDto employeeDto) {
        log.info("Creating new employee with email: {}", employeeDto.getEmail());
        List<Employee> existingEmployees = employeeRepository.findByEmail(employeeDto.getEmail());

        if (!existingEmployees.isEmpty()) {
            log.error("Employee already exists with email: {}", employeeDto.getEmail());
            throw new RuntimeException("Employee already exists with email: " + employeeDto.getEmail());
        }
        Employee newEmployee = modelMapper.map(employeeDto, Employee.class);
        Employee savedEmployee = employeeRepository.save(newEmployee);

        salaryAccountService.createAccount(savedEmployee);

        log.info("Successfully created new employee with id: {}", savedEmployee.getId());
        return modelMapper.map(savedEmployee, EmployeeDto.class);
    }

    @Override
    // String CACHE_NAME = "employees";
    @CachePut(cacheNames = CACHE_NAME, key = "#id")
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        log.info("Updating employee with id: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Employee not found with id: {}", id);
                    return new ResourceNotFoundException("Employee not found with id: " + id);
                });

        if (!employee.getEmail().equals(employeeDto.getEmail())) {
            log.error("Attempted to update email for employee with id: {}", id);
            throw new RuntimeException("The email of the employee cannot be updated");
        }

        modelMapper.map(employeeDto, employee);
        employee.setId(id);

        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Successfully updated employee with id: {}", id);
        return modelMapper.map(savedEmployee, EmployeeDto.class);
    }

    @Override
    // String CACHE_NAME = "employees";
    @CacheEvict(cacheNames = CACHE_NAME, key = "#id")
    public void deleteEmployee(Long id) {
        log.info("Deleting employee with id: {}", id);
        boolean exists = employeeRepository.existsById(id);
        if (!exists) {
            log.error("Employee not found with id: {}", id);
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }

        employeeRepository.deleteById(id);
        log.info("Successfully deleted employee with id: {}", id);
    }
}