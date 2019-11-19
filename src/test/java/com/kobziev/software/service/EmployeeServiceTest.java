package com.kobziev.software.service;

import com.kobziev.software.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

class EmployeeServiceTest {

    private EmployeeService sut;

    private static final String NAME = "Test Name";

    @BeforeEach
    void setup() {
        sut = new EmployeeService();
    }

    @Test
    void create() {
        Employee employee = sut.create(NAME);

        assertThat(employee.getName(), is(NAME));
        assertThat(employee.getId(), notNullValue());
        assertThat(employee.getDepatmentId(), nullValue());
    }

    @Test
    void findById() {
        Employee employee = sut.create(NAME);
        Employee result = sut.findById(employee.getId());

        assertThat(result.getId(), is(employee.getId()));
        assertThat(result.getDepatmentId(), is(employee.getDepatmentId()));
        assertThat(result.getName(), is(employee.getName()));
    }

    @Test
    void getAll() {
        Employee employee = sut.create(NAME);
        List<Employee> employees = sut.getAll();

        assertThat(employees, hasSize(1));
        assertThat(employees.get(0).getId(), is(employee.getId()));
        assertThat(employees.get(0).getDepatmentId(), is(employee.getDepatmentId()));
        assertThat(employees.get(0).getName(), is(employee.getName()));
    }

    @Test
    void update() {
        Employee employee = sut.create(NAME);
        Employee updatedEmployee = new Employee();
        updatedEmployee.setId(employee.getId());
        updatedEmployee.setName(NAME+"1");
        updatedEmployee.setDepatmentId(15);

        sut.update(updatedEmployee);

        Employee result = sut.findById(updatedEmployee.getId());
        assertThat(result.getId(), is(updatedEmployee.getId()));
        assertThat(result.getDepatmentId(), is(updatedEmployee.getDepatmentId()));
        assertThat(result.getName(), is(updatedEmployee.getName()));
    }
}