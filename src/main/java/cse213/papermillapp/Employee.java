package cse213.papermillapp;

public abstract class Employee extends User {
    private String dept;
    private String designation;
    private float salary;

    public Employee(int id, String name, String pw, String dept, String designation, float salary) {
        super(id, name, pw);
        this.dept = dept;
        this.designation = designation;
        this.salary = salary;

    }

    public static boolean verifyLogin(int givenId, String givenPw, Employee emp) {
        if (emp == null) return false;
        return (emp.getId() == givenId && emp.getPw().equals(givenPw));
    }
}
