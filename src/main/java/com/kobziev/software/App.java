package com.kobziev.software;

import com.kobziev.software.entity.Bug;
import com.kobziev.software.entity.Department;
import com.kobziev.software.entity.Employee;
import com.kobziev.software.service.BugService;
import com.kobziev.software.service.DepartmentService;
import com.kobziev.software.service.EmployeeService;

import java.util.Scanner;

public class App {

    private static final String BACK_TO_PREVIOUS_MENU = "0 - back to previous menu";
    private static final String CHOOSE_AN_OPTION = "Choose an option: ";
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;
    private final BugService bugService;

    public App() {
        employeeService = new EmployeeService();
        departmentService = new DepartmentService(employeeService);
        bugService = new BugService();
    }

    public static void main(String... args) {
        App app = new App();
        app.startConversation();
    }

    private void startConversation() {
        int operation = -1;

        while (operation != 0) {
            println(CHOOSE_AN_OPTION);
            println("1 - operations with an employee");
            println("2 - operations with a department");
            println("3 - operations with a bug");
            println("0 - exit");

            operation = parseInteger(readLine());

            switch (operation) {
                case 1:
                    proceedWithEmployee();
                    break;
                case 2:
                    proceedWithDepartment();
                    break;
                case 3:
                    proceedWithBug();
                    break;
                default:
                    break;
            }
        }
    }

    private void proceedWithEmployee() {
        println(CHOOSE_AN_OPTION);
        println("1 - create an employee ");
        println("2 - update an employee");
        println("3 - list all employees");
        println(BACK_TO_PREVIOUS_MENU);

        Integer operation = parseInteger(readLine());

        switch (operation) {
            case 1:
                createEmployee();
                break;
            case 2:
                updateEmployee();
                break;
            case 3:
                listAllEmployees();
                break;
            case 0:
                return;
            default:
                proceedWithEmployee();
        }

        proceedWithEmployee();
    }

    private void createEmployee() {
        println("Enter employee name:");
        String s = readLine();

        Employee employee = employeeService.create(s);
        println("Employee is created:" + employee);
    }

    private void updateEmployee() {
        Employee employee = chooseEmployee();
        if (employee == null) return;

        println("Enter employee name:");
        String s = readLine();
        employee.setName(s);

        employeeService.update(employee);
    }

    private void listAllEmployees() {
        employeeService.getAll().forEach(emp -> println(emp.toString()));
    }

    private void proceedWithDepartment() {
        println(CHOOSE_AN_OPTION);
        println("1 - create a department");
        println("2 - update a department");
        println("3 - assign an employee to department");
        println("4 - list all employees of department");
        println("5 - list all departments");
        println(BACK_TO_PREVIOUS_MENU);

        Integer operation = parseInteger(readLine());

        switch (operation) {
            case 1:
                createDepartment();
                break;
            case 2:
                updateDepartment();
                break;
            case 3:
                assignDepartment();
                break;
            case 4:
                printAllEmployees();
                break;
            case 5:
                listAllDepartments();
                break;
            case 0:
                return;
            default:
                proceedWithDepartment();
        }
        proceedWithDepartment();
    }

    private void createDepartment() {
        println("Enter department name:");
        String s = readLine();

        Department department = departmentService.create(s);
        println("Department is created:" + department);
    }

    private void updateDepartment() {
        Department department = chooseDepartment();
        if (department == null) return;

        println("Enter department name:");
        String s = readLine();
        department.setName(s);

        departmentService.update(department);
    }

    private void assignDepartment() {
        Department department = chooseDepartment();
        if (department == null) return;

        Employee employee = chooseEmployee();
        if (employee == null) return;

        departmentService.assignToDepartment(department, employee);
    }

    private void printAllEmployees() {
        Department department = chooseDepartment();
        if (department == null) return;

        departmentService.getAllEmployees(department).forEach(dep -> println(dep.toString()));
    }

    private void listAllDepartments() {
        departmentService.getAll().forEach(dep -> println(dep.toString()));
    }

    private void proceedWithBug() {
        println(CHOOSE_AN_OPTION);
        println("1 - create a bug");
        println("2 - update a bug");
        println("3 - assign a bug to an employee");
        println("4 - list all bugs");
        println(BACK_TO_PREVIOUS_MENU);

        Integer operation = parseInteger(readLine());

        switch (operation) {
            case 1:
                createBug();
                break;
            case 2:
                updateBug();
                break;
            case 3:
                assignBugToEmployee();
                break;
            case 4:
                printAllBugs();
                break;
            case 0:
                return;
            default:
                proceedWithBug();
        }
        proceedWithBug();
    }

    private void createBug() {
        println("Enter bug description:");
        String s = readLine();

        Bug bug = bugService.create(s);
        println("Bug is created:" + bug);
    }

    private void updateBug() {
        Bug bug = chooseBug();
        if (bug == null) return;

        println("Enter bug description:");
        String s = readLine();
        bug.setDescription(s);

        bugService.update(bug);
    }

    private void assignBugToEmployee() {
        Bug bug = chooseBug();
        if (bug == null) return;

        Employee employee = chooseEmployee();
        if (employee == null) return;

        bugService.assignToEmployee(bug, employee);
    }

    private void printAllBugs() {
        bugService.getAll().forEach(bug -> println(bug.toString()));
    }

    private Department chooseDepartment() {
        Department department = null;
        while (department == null) {
            println(BACK_TO_PREVIOUS_MENU);
            println("Enter department id:");

            Integer id = parseInteger(readLine());
            if (id == 0) {
                return department;
            }

            department = departmentService.findById(id);

            if (department == null) {
                println("Department not found");
            }
        }
        return department;
    }

    private Employee chooseEmployee() {
        Employee employee = null;
        while (employee == null) {
            println(BACK_TO_PREVIOUS_MENU);
            println("Enter employee id:");

            Integer id = parseInteger(readLine());
            if (id == 0) {
                return null;
            }

            employee = employeeService.findById(id);

            if (employee == null) {
                println("Employee not found");
            }
        }
        return employee;
    }

    private Bug chooseBug() {
        Bug bug = null;
        while (bug == null) {
            println(BACK_TO_PREVIOUS_MENU);
            println("Enter bug id:");

            Integer id = parseInteger(readLine());
            if (id == 0) {
                return null;
            }

            bug = bugService.findById(id);

            if (bug == null) {
                println("Bug not found");
            }
        }
        return bug;
    }

    private Integer parseInteger(String value) {
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private String readLine() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    private void println(String line) {
        System.out.println(line);
    }
}
