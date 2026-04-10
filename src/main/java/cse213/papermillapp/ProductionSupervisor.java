// This package contains all the classes for the paper mill application
package cse213.papermillapp;

// A Production Supervisor is a type of employee who manages production
public class ProductionSupervisor extends Employee{
    // Array to store production for each day of the week (7 days)
    private int[] dailyProductionArr;
    
    // The target amount of products to make
    private float targetProduction;

    // This creates a new Production Supervisor
    public ProductionSupervisor(int id, String name, String pw, String dept, String designation, float salary) {
        // Call the parent Employee class to set basic details
        super(id, name, pw, dept, designation, salary);
        // Create space for 7 days of production data
        this.dailyProductionArr = new int[7];
        // Set the default target to 1000 products
        this.targetProduction = 1000.0f;
    }

    // This updates the production supervisor's profile
    @Override
    public void updateProfile() {
        // Print a message to show the profile was updated
        System.out.println("Production Supervisor Profile updated");
    }

    // This records how much was produced on a specific day
    public boolean recordProduction(int dayIndex, int val) {
        // Check if the day index is valid (0-6 for 7 days) and value is positive
        if (val >= 0 && dayIndex >= 0 && dayIndex < dailyProductionArr.length) {
            // Save the production value for that day
            dailyProductionArr[dayIndex] = val;
            // Return true to show it was saved
            return true;
        }
        // Return false if the input was invalid
        return false;
    }
}
