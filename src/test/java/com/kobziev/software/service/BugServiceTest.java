package com.kobziev.software.service;

import com.kobziev.software.entity.Bug;
import com.kobziev.software.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

class BugServiceTest {
    private BugService sut;

    private static final String DESCRIPTION = "Test description";

    @BeforeEach
    void setup() {
        sut = new BugService();
    }

    @Test
    void create() {
        Bug bug = sut.create(DESCRIPTION);

        assertThat(bug.getDescription(), is(DESCRIPTION));
        assertThat(bug.getId(), notNullValue());
        assertThat(bug.getUserId(), nullValue());
    }

    @Test
    void update() {
        Bug bug = sut.create(DESCRIPTION);
        Bug updatedBug = new Bug();
        updatedBug.setId(bug.getId());
        updatedBug.setDescription(DESCRIPTION +"1");
        updatedBug.setUserId(15);

        sut.update(updatedBug);

        Bug result = sut.findById(updatedBug.getId());
        assertThat(result.getId(), is(updatedBug.getId()));
        assertThat(result.getUserId(), is(updatedBug.getUserId()));
        assertThat(result.getDescription(), is(updatedBug.getDescription()));
    }

    @Test
    void assignToEmployee() {
        Bug bug = sut.create(DESCRIPTION);
        Employee employee = new Employee();
        employee.setId(2345);

        sut.assignToEmployee(bug, employee);

        assertThat(bug.getUserId(), is(employee.getId()));

        Bug result = sut.findById(bug.getId());
        assertThat(result.getId(), is(bug.getId()));
        assertThat(result.getUserId(), is(employee.getId()));
        assertThat(result.getDescription(), is(bug.getDescription()));
    }

    @Test
    void findById() {
        Bug bug = sut.create(DESCRIPTION);
        Bug result = sut.findById(bug.getId());

        assertThat(result.getId(), is(bug.getId()));
        assertThat(result.getDescription(), is(bug.getDescription()));
        assertThat(result.getUserId(), is(bug.getUserId()));
    }

    @Test
    void getAll() {
        Bug bug = sut.create(DESCRIPTION);
        List<Bug> bugs = sut.getAll();

        assertThat(bugs, hasSize(1));
        assertThat(bugs.get(0).getId(), is(bug.getId()));
        assertThat(bugs.get(0).getUserId(), is(bug.getUserId()));
        assertThat(bugs.get(0).getDescription(), is(bug.getDescription()));
    }
}