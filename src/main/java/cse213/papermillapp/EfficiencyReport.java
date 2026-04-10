// This package contains all the classes for the paper mill application
package cse213.papermillapp;

// We need this to save objects to file
import java.io.Serializable;

// This class stores information about worker efficiency
// It tracks how well a worker is performing their job
public class EfficiencyReport implements Serializable {
    // The ID of the worker
    private String workerId;
    
    // The machine this worker is assigned to
    private String machineAssigned;
    
    // How many tasks this worker has done
    private int tasksHandled;
    
    // How efficient this worker is (like "good", "average", "poor")
    private String efficiencyRating;

    // This creates a new efficiency report
    public EfficiencyReport(String workerId, String machineAssigned, int tasksHandled, String efficiencyRating) {
        // Save the worker ID
        this.workerId = workerId;
        // Save the machine assigned
        this.machineAssigned = machineAssigned;
        // Save the number of tasks handled
        this.tasksHandled = tasksHandled;
        // Save the efficiency rating
        this.efficiencyRating = efficiencyRating;
    }

    // Get the worker ID
    public String getWorkerId() { return workerId; }
    
    // Get the machine assigned
    public String getMachineAssigned() { return machineAssigned; }
    
    // Get the number of tasks handled
    public int getTasksHandled() { return tasksHandled; }
    
    // Get the efficiency rating
    public String getEfficiencyRating() { return efficiencyRating; }
}