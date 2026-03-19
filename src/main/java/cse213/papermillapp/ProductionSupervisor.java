package cse213.papermillapp;

public class ProductionSupervisor extends Employee{

    private int[] dailyProductionArr;
    private float targetProduction;

    public ProductionSupervisor(int id, String name, String pw, String dept, String designation, float salary) {
        super(id, name, pw, dept, designation, salary);
        this.dailyProductionArr = new int[7];
        this.targetProduction = 1000.0f;
    }

    @Override
    public void updateProfile() {
        System.out.println("Production Supervisor Profile updated");
    }

    public boolean recordProduction(int dayIndex, int val) {
        if (val >= 0 && dayIndex >= 0 && dayIndex < dailyProductionArr.length) {
            dailyProductionArr[dayIndex] = val;
            return true;
        }
        return false;
    }


}
