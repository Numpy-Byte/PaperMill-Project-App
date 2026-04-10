// This package contains all the classes for the paper mill application
package cse213.papermillapp;

// We need this to save objects to file
import java.io.Serializable;

// This class stores information about a worker or machine operator
public class Worker implements Serializable {
    // The unique ID of this worker
    private String workerId;
    
    // The name of this worker
    private String name;
    
    // The machine this worker is assigned to operate
    private String assignedMachine;

    // This creates a new worker with their details
    public Worker(String workerId, String name, String assignedMachine) {
        // Save the worker ID
        this.workerId = workerId;
        // Save the name
        this.name = name;
        // Save the assigned machine
        this.assignedMachine = assignedMachine;
    }

    // Get the worker ID
    public String getWorkerId() { return workerId; }
    
    // Get the worker name
    public String getName() { return name; }
    
    // Get the assigned machine
    public String getAssignedMachine() { return assignedMachine; }

    // Update the machine this worker is assigned to
    public void setAssignedMachine(String assignedMachine) { 
        // Save the new assigned machine
        this.assignedMachine = assignedMachine; 
    }
}