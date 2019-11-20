package com.kobziev.software.service;

import com.kobziev.software.entity.Bug;
import com.kobziev.software.entity.Employee;

import java.util.ArrayList;
import java.util.List;

public class BugService {
    private List<Bug> bugStorage = new ArrayList<>();

    public Bug create(String description) {
        Bug bug = new Bug();
        bug.setId(bugStorage.size() + 1);
        bug.setDescription(description);
        bugStorage.add(bug);

        return bug;
    }

    public void update(Bug bug) {
        Bug toUpdate = findById(bug.getId());
        toUpdate.setDescription(bug.getDescription());
        toUpdate.setUserId(bug.getUserId());
        toUpdate.setId(bug.getId());
    }

    public void assignToEmployee(Bug bug, Employee employee) {
        bug.setUserId(employee.getId());
        update(bug);
    }

    public Bug findById(Integer id) {
        return bugStorage.stream()
                .filter(bug -> id.equals(bug.getId()))
                .findFirst()
                .orElse(null);
    }

    public List<Bug> getAll() {
        return new ArrayList<>(bugStorage);
    }
}
