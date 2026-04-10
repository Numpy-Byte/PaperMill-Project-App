// This package contains all the classes for the paper mill application
package cse213.papermillapp;

// We need this to save objects to file
import java.io.Serializable;

// This class stores information about machine maintenance
// It keeps track of when a machine was fixed or serviced
public class MaintenanceLog implements Serializable {
    // The date when maintenance was done
    private String date;
    
    // The ID of the machine that was maintained
    private String machineId;
    
    // The type of maintenance done (like "repair", "oil change", etc)
    private String maintenanceType;
    
    // Any extra notes about the maintenance
    private String remarks;

    // This creates a new maintenance log entry
    public MaintenanceLog(String date, String machineId, String maintenanceType, String remarks) {
        // Save the date
        this.date = date;
        // Save the machine ID
        this.machineId = machineId;
        // Save the type of maintenance
        this.maintenanceType = maintenanceType;
        // Save any remarks
        this.remarks = remarks;
    }

    // Get the date
    public String getDate() { return date; }
    
    // Get the machine ID
    public String getMachineId() { return machineId; }
    
    // Get the type of maintenance
    public String getMaintenanceType() { return maintenanceType; }
    
    // Get any remarks
    public String getRemarks() { return remarks; }
}