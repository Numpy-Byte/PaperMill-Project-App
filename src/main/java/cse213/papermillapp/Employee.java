// This package contains all the classes for the paper mill application
package cse213.papermillapp;

// Employee is a type of user that works for the company
// We use "abstract" because we don't create plain employees - we create specific types like workers or supervisors
public abstract class Employee extends User {
    // The department where this employee works
    private String dept;
    
    // The job title of this employee
    private String designation;
    
    // How much money this employee gets paid
    private float salary;

    // This creates a new employee with all their details
    public Employee(int id, String name, String pw, String dept, String designation, float salary) {
        // Call the parent User class to set id, name, and password
        super(id, name, pw);
        // Save the department
        this.dept = dept;
        // Save the designation (job title)
        this.designation = designation;
        // Save the salary
        this.salary = salary;
    }

    // This checks if the login details are correct for an employee
    public static boolean verifyLogin(int givenId, String givenPw, Employee emp) {
        // If the employee object is empty, login failed
        if (emp == null) return false;
        // Check if the ID matches AND the password matches, then return true
        return (emp.getId() == givenId && emp.getPw().equals(givenPw));
    }
}
