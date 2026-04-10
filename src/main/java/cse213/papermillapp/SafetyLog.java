// This package contains all the classes for the paper mill application
package cse213.papermillapp;

// We need this to save objects to file
import java.io.Serializable;
// We need this to get the current date
import java.time.LocalDate;

// This class stores information about safety checks on machines
// It tracks whether a machine has passed its safety inspection
public class SafetyLog implements Serializable {
    // The ID of the machine that was checked
    private String machineId;
    
    // The date when the safety check was done
    private String date;
    
    // Whether the machine passed the safety check (true) or not (false)
    private boolean isApproved;

    // This creates a new safety log entry
    public SafetyLog(String machineId, boolean isApproved) {
        // Save the machine ID
        this.machineId = machineId;
        // Save whether it passed or failed
        this.isApproved = isApproved;
        // Automatically get today's date
        this.date = LocalDate.now().toString();
    }

    // Get the machine ID
    public String getMachineId() { return machineId; }
    
    // Get the date of the safety check
    public String getDate() { return date; }
    
    // Check if the machine passed the safety check
    public boolean isApproved() { return isApproved; }
}