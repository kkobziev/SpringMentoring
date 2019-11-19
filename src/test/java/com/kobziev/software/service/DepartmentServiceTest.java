package com.kobziev.software.service;

import com.kobziev.software.entity.Department;
import com.kobziev.software.entity.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @InjectMocks
    private DepartmentService sut;
    @Mock
    private EmployeeService employeeService;

    private static final String NAME = "test department";

    @Test
    void create() {
        Department department = sut.create(NAME);

        assertThat(department.getName(), is(NAME));
        assertThat(department.getId(), notNullValue());
    }

    @Test
    void findById() {
        Department department = sut.create(NAME);
        Department result = sut.findById(department.getId());

        assertThat(result.getId(), is(department.getId()));
        assertThat(result.getName(), is(department.getName()));
    }

    @Test
    void getAll() {
        Department department = sut.create(NAME);
        List<Department> departments = sut.getAll();

        assertThat(departments, hasSize(1));
        assertThat(departments.get(0).getId(), is(department.getId()));
        assertThat(departments.get(0).getName(), is(department.getName()));
    }

    @Test
    void update() {
        Department department = sut.create(NAME);
        Department updated = new Department();
        updated.setId(department.getId());
        updated.setName(NAME + " 1");
        sut.update(updated);
        Department result = sut.findById(department.getId());

        assertThat(result.getId(), is(updated.getId()));
        assertThat(result.getName(), is(updated.getName()));
    }

    @Test
    void assignToDepartment() {
        Department department = sut.create(NAME);
        Employee employee = new Employee();

        sut.assignToDepartment(department, employee);

        assertThat(employee.getDepatmentId(), is(department.getId()));
        verify(employeeService).update(employee);
    }

    @Test
    void getAllEmployees() {
        Department department = sut.create(NAME);
        Employee employee1 = new Employee();
        employee1.setDepatmentId(department.getId());
        Employee employee2 = new Employee();

        when(employeeService.getAll()).thenReturn(Arrays.asList(employee1, employee2));

        List<Employee> result = sut.getAllEmployees(department);

        assertThat(result, hasSize(1));
        assertThat(result, contains(employee1));
    }
}