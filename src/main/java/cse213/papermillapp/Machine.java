// This package contains all the classes for the paper mill application
package cse213.papermillapp;

// We need this to save objects to file
import java.io.Serializable;

// This class stores information about a machine in the paper mill
public class Machine implements Serializable {
    // The unique ID of this machine
    private String id;
    
    // The name of this machine
    private String name;
    
    // The current status of this machine (like "running", "stopped", "maintenance")
    private String status;

    // This creates a new machine with its details
    public Machine(String id, String name, String status) {
        // Save the ID
        this.id = id;
        // Save the name
        this.name = name;
        // Save the status
        this.status = status;
    }

    // Get the machine ID
    public String getId() { return id; }
    
    // Get the machine name
    public String getName() { return name; }
    
    // Get the current status
    public String getStatus() { return status; }

    // Update the status of this machine
    public void setStatus(String status) { 
        // Save the new status
        this.status = status; 
    }
}