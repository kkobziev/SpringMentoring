package com.kobziev.software.service;

import com.kobziev.software.entity.Department;
import com.kobziev.software.entity.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DepartmentService {
    private List<Department> departmentStorage = new ArrayList<>();

    private EmployeeService employeeService;

    public DepartmentService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public Department create(String name) {
        Department department = new Department();
        department.setId(departmentStorage.size() + 1);
        department.setName(name);
        departmentStorage.add(department);

        return department;
    }

    public void update(Department department) {
        Department toUpdate = findById(department.getId());
        toUpdate.setName(department.getName());
    }

    public void assignToDepartment(Department department, Employee employee) {
        employee.setDepatmentId(department.getId());
        employeeService.update(employee);
    }

    public List<Employee> getAllEmployees(Department department) {
        return employeeService.getAll().stream()
                .filter(employee -> department.getId().equals(employee.getDepatmentId()))
                .collect(Collectors.toList());
    }

    public List<Department> getAll() {
        return new ArrayList<>(departmentStorage);
    }

    public Department findById(Integer id) {
        return departmentStorage.stream()
                .filter(department -> id.equals(department.getId()))
                .findFirst()
                .orElse(null);
    }
}
