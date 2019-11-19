package com.kobziev.software.service;

import com.kobziev.software.entity.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeService {

    private List<Employee> employeeStorage = new ArrayList<>();

    public Employee create(String name){
        Employee employee = new Employee();
        employee.setId(employeeStorage.size() + 1);
        employee.setName(name);
        employeeStorage.add(employee);
        return employee;
    }

    public void update(Employee employee) {
        Employee toUpdate = findById(employee.getId());
        toUpdate.setName(employee.getName());
        toUpdate.setDepatmentId(employee.getDepatmentId());
    }

    public Employee findById(Integer id) {
        return employeeStorage.stream()
                .filter(employee -> id.equals(employee.getId()))
                .findFirst()
                .orElse(null);
    }

    public List<Employee> getAll() {
        return new ArrayList<>(employeeStorage);
    }
}
