// This package contains all the classes for the paper mill application
package cse213.papermillapp;

// We need this to save objects to file
import java.io.Serializable;

// This class stores information about how much raw material was used
// It tracks material consumption by each machine
public class RawMaterialUsage implements Serializable {
    // The ID of the machine that used the material
    private String machineId;
    
    // The amount of material used
    private int amountUsed;

    // This creates a new raw material usage record
    public RawMaterialUsage(String machineId, int amountUsed) {
        // Save the machine ID
        this.machineId = machineId;
        // Save the amount used
        this.amountUsed = amountUsed;
    }

    // Get the machine ID
    public String getMachineId() { return machineId; }
    
    // Get the amount used
    public int getAmountUsed() { return amountUsed; }
}